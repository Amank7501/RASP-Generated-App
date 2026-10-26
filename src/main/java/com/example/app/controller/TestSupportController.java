package com.example.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import com.example.app.annotation.MethodMetadata;

/**
 * Test-only support endpoint: persists a raw entity directly via its repository,
 * bypassing controller-level DTO validation. Used by the generated Karate test
 * suite to seed prerequisite entities (e.g. Student, Hostel) that FK-dependent
 * scenarios need to already exist, without requiring every test fixture to
 * satisfy whatever a specific Create*RequestDto happens to require.
 *
 * <p>Incoming field values are coerced to each entity field's real type before
 * binding (e.g. a placeholder string landing on a Double/LocalDateTime field),
 * so mismatched test-fixture data degrades to a sensible default instead of
 * failing the whole entity creation.
 *
 * <p>{@code relatedEntityIds} additionally wires up JPA relationship fields
 * (e.g. StudentStageRecord.student / .onboardingStage) to entities already
 * created earlier in the same prerequisite chain — the raw fixture "fields"
 * never carry FK references at all, so business logic that looks an entity up
 * by its relationships (not just its own id) would otherwise never find it.
 *
 * <p>Not intended for production traffic — consider profile-gating or removing
 * this controller for real deployments.
 */
@Slf4j
@RestController
@RequestMapping("/test-support")
@RequiredArgsConstructor
public class TestSupportController {

    private final ApplicationContext applicationContext;

    private final ObjectMapper objectMapper;

    @PersistenceContext
    private EntityManager entityManager;

    private static final int MAX_ATTEMPTS = 4;

    @PostMapping("/seed/{entityName}")
    @SuppressWarnings("unchecked")
    @MethodMetadata(irId = "custom:controller:TestSupportController:seed(String,Map<String,Object>)", hash = "8f8da49d", zone = 1)
    public ResponseEntity<?> seed(@PathVariable String entityName, @RequestBody Map<String, Object> body) {
        String entityClassName = "com.example.app.entity." + entityName;
        Map<String, Object> fields = body.containsKey("fields") ? new HashMap<>((Map<String, Object>) body.get("fields")) : new HashMap<>(body);
        Map<String, Object> relatedEntityIds = (Map<String, Object>) body.getOrDefault("relatedEntityIds", Map.of());
        String lastMessage = null;
        Class<?> entityClass;
        try {
            entityClass = Class.forName(entityClassName);
        } catch (ClassNotFoundException e) {
            return ResponseEntity.status(500).body(Map.of("error", "Unknown entity: " + entityName));
        }
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            try {
                Map<String, Object> coerced = new HashMap<>();
                for (Map.Entry<String, Object> e : fields.entrySet()) {
                    coerced.put(e.getKey(), coerceToFieldType(entityClass, e.getKey(), e.getValue()));
                }
                Object entity = objectMapper.convertValue(coerced, entityClass);
                wireRelatedEntities(entity, entityClass, relatedEntityIds);
                String repositoryBeanName = Character.toLowerCase(entityName.charAt(0)) + entityName.substring(1) + "Repository";
                Object repository = applicationContext.getBean(repositoryBeanName);
                Method saveMethod = repository.getClass().getMethod("save", Object.class);
                Object saved = saveMethod.invoke(repository, entity);
                Method getIdMethod = saved.getClass().getMethod("getId");
                Object id = getIdMethod.invoke(saved);
                return ResponseEntity.ok(Map.of("id", id));
            } catch (Exception e) {
                // Reflective invoke() wraps the real failure in InvocationTargetException
                // (and Spring Data often wraps that again, e.g. DataIntegrityViolationException
                // wrapping a raw SQL constraint error) — unwrap to the innermost cause so the
                // actual problem is visible instead of a useless "InvocationTargetException".
                Throwable root = e;
                while (root.getCause() != null && root.getCause() != root) {
                    root = root.getCause();
                }
                lastMessage = root.getMessage() != null ? root.getMessage() : root.toString();
                boolean isDuplicateKey = lastMessage.toLowerCase().contains("duplicate entry") || lastMessage.toLowerCase().contains("unique constraint") || lastMessage.toLowerCase().contains("unique index");
                if (!isDuplicateKey || attempt == MAX_ATTEMPTS) {
                    log.warn("Test-support seed failed for entity '{}' (attempt {}/{}): {}", entityName, attempt, MAX_ATTEMPTS, lastMessage);
                    return ResponseEntity.status(500).body(Map.of("error", lastMessage));
                }
                // Test fixtures reuse the exact same literal values on every run (e.g.
                // tokenNumber: 10), which collides with whatever a unique-constrained
                // column already holds from a previous run against a shared dev DB.
                // Perturb ONLY the field the DB actually complained about — parsed from
                // the duplicate-key error's column/key name. Randomizing every field
                // (including ones with no uniqueness constraint at all, like admissionYear
                // or email) risks pushing a perfectly fine value out of its own @Max/@Size/
                // @Pattern bounds, which then fails Bean Validation on the retry and masks
                // the original (harmless) collision behind an unrelated validation error.
                java.util.Set<String> conflictingFields = resolveConflictingFields(entityClass, fields.keySet());
                // A conflicting field that carries @Pattern (e.g. email) is never
                // perturbed by randomizeForRetry — a free-form suffix would almost
                // certainly break the regex. So re-sending the identical payload would
                // just collide again every attempt. Instead of exhausting retries and
                // failing, treat this exactly like the same logical fixture being
                // seeded twice (e.g. the same static test fixture reused across usecases
                // against a non-reset dev DB) and hand back the EXISTING row's id.
                for (String fieldName : conflictingFields) {
                    Field f = findFieldByName(entityClass, fieldName);
                    if (f != null && f.getAnnotation(jakarta.validation.constraints.Pattern.class) != null) {
                        Object existingId = findExistingId(entityClass, fieldName, fields.get(fieldName));
                        if (existingId != null) {
                            log.info("Duplicate key on attempt {}/{} for entity '{}' ({}) — field '{}' is pattern-constrained " + "so it can't be perturbed; reusing existing row with id {}", attempt, MAX_ATTEMPTS, entityName, lastMessage, fieldName, existingId);
                            return ResponseEntity.ok(Map.of("id", existingId));
                        }
                    }
                }
                if (!conflictingFields.isEmpty()) {
                    log.info("Duplicate key on attempt {}/{} for entity '{}' ({}), retrying with field(s) {} randomized", attempt, MAX_ATTEMPTS, entityName, lastMessage, conflictingFields);
                    for (String key : conflictingFields) {
                        fields.put(key, randomizeForRetry(entityClass, key, fields.get(key)));
                    }
                } else {
                    // The entity declares no @Column(unique = true) field at all (or
                    // Hibernate's own hashed constraint name gave us nothing to go on
                    // and reflection found no unique columns either) — fall back to the
                    // old randomize-everything behavior rather than retrying with an
                    // unchanged payload that would just collide again identically.
                    log.info("Duplicate key on attempt {}/{} for entity '{}' ({}), could not isolate the conflicting field — retrying with all fields randomized", attempt, MAX_ATTEMPTS, entityName, lastMessage);
                    for (String key : new HashMap<>(fields).keySet()) {
                        fields.put(key, randomizeForRetry(entityClass, key, fields.get(key)));
                    }
                }
            }
        }
        return ResponseEntity.status(500).body(Map.of("error", lastMessage));
    }

    /**
     * Looks up an existing row of {@code entityClass} whose {@code fieldName}
     * equals {@code value}, returning its id, or null if none exists / the
     * lookup itself fails (e.g. the field isn't a simple scalar JPQL can
     * compare directly). Used only for idempotent reuse of a row that's a
     * genuine duplicate of a fixture already seeded earlier in the same run.
     */
    @MethodMetadata(irId = "custom:controller:TestSupportController:findExistingId(Class<?>,String,Object)", hash = "3b12212f", zone = 1)
    private Object findExistingId(Class<?> entityClass, String fieldName, Object value) {
        try {
            String jpql = "SELECT e.id FROM " + entityClass.getSimpleName() + " e WHERE e." + fieldName + " = :val";
            return entityManager.createQuery(jpql, Object.class).setParameter("val", value).setMaxResults(1).getSingleResult();
        } catch (NoResultException notFound) {
            return null;
        } catch (Exception e) {
            log.warn("Could not look up existing {} by {}={}: {}", entityClass.getSimpleName(), fieldName, value, e.getMessage());
            return null;
        }
    }

    /**
     * Finds which of the fixture's own fields are declared {@code @Column(unique
     * = true)} on the entity, so the retry can perturb just those instead of
     * every field. Deliberately does NOT try to parse the DB's error message for
     * the conflicting column/key name — Hibernate's default naming strategy
     * names unique constraints with an opaque hash (e.g.
     * {@code UK_e2rndfrsx22acpq2ty1caeuyw}), which carries no information about
     * which column it's actually on, so that approach can never work reliably.
     * Reflection on the entity's own annotations is the only reliable source.
     */
    @MethodMetadata(irId = "custom:controller:TestSupportController:resolveConflictingFields(Class<?>,java.util.Set<String>)", hash = "f5c5ef65", zone = 1)
    private java.util.Set<String> resolveConflictingFields(Class<?> entityClass, java.util.Set<String> fieldNames) {
        java.util.Set<String> result = new java.util.HashSet<>();
        for (String fieldName : fieldNames) {
            Field field = findFieldByName(entityClass, fieldName);
            if (field == null)
                continue;
            jakarta.persistence.Column col = field.getAnnotation(jakarta.persistence.Column.class);
            if (col != null && col.unique()) {
                result.add(fieldName);
            }
        }
        return result;
    }

    /**
     * Perturbs a fixture value so a retried save no longer collides with whatever
     * unique-constrained value the previous attempt (or a prior test run) already
     * persisted. Numbers get a random offset added; strings get a random suffix
     * appended — UNLESS the field carries a {@code @Pattern} constraint (e.g. an
     * email regex), in which case appending an arbitrary suffix would almost
     * certainly break the pattern and turn a harmless duplicate-key collision
     * into a confusing Bean Validation failure instead. Such a field is left
     * untouched; if it's genuinely the conflicting one, the retry will just
     * collide again and the loop will eventually report the original,
     * accurate duplicate-key error rather than a misleading one.
     */
    @MethodMetadata(irId = "custom:controller:TestSupportController:randomizeForRetry(Class<?>,String,Object)", hash = "93971e6d", zone = 1)
    private Object randomizeForRetry(Class<?> entityClass, String fieldName, Object value) {
        if (value instanceof String) {
            Field field = findFieldByName(entityClass, fieldName);
            if (field != null && field.getAnnotation(jakarta.validation.constraints.Pattern.class) != null) {
                return value;
            }
        }
        long salt = System.nanoTime() % 1_000_000;
        if (value instanceof Integer i)
            return i + (int) salt % 100_000;
        if (value instanceof Long l)
            return l + salt;
        if (value instanceof Double d)
            return d + (salt % 1000) / 100.0;
        if (value instanceof Float f)
            return f + (salt % 1000) / 100.0f;
        if (value instanceof String s)
            return s + "_" + salt;
        return value;
    }

    /**
     * For each already-created prerequisite entity (keyed by its simple class name,
     * e.g. "Student" -> its real id), finds a declared field on this entity whose
     * TYPE is that same class — a JPA relationship field — and sets it to a minimal
     * stub instance carrying just that id. Fields with no matching relationship, or
     * relationships this entity doesn't declare, are silently skipped.
     */
    @MethodMetadata(irId = "custom:controller:TestSupportController:wireRelatedEntities(Object,Class<?>,Map<String,Object>)", hash = "57ada661", zone = 1)
    private void wireRelatedEntities(Object entity, Class<?> entityClass, Map<String, Object> relatedEntityIds) {
        for (Map.Entry<String, Object> related : relatedEntityIds.entrySet()) {
            String relatedEntityName = related.getKey();
            Object relatedId = related.getValue();
            if (relatedId == null)
                continue;
            Field field = findFieldByTypeSimpleName(entityClass, relatedEntityName);
            if (field == null)
                continue;
            try {
                Class<?> relatedType = field.getType();
                Object stub = relatedType.getDeclaredConstructor().newInstance();
                Field idField = findFieldByName(relatedType, "id");
                if (idField == null)
                    continue;
                idField.setAccessible(true);
                idField.set(stub, coerceToFieldType(relatedType, "id", relatedId));
                field.setAccessible(true);
                field.set(entity, stub);
            } catch (Exception e) {
                log.warn("Could not wire relationship '{}' ({}) on {}: {}", field.getName(), relatedEntityName, entityClass.getSimpleName(), e.getMessage());
            }
        }
    }

    @MethodMetadata(irId = "custom:controller:TestSupportController:findFieldByTypeSimpleName(Class<?>,String)", hash = "838b0dda", zone = 1)
    private Field findFieldByTypeSimpleName(Class<?> entityClass, String typeSimpleName) {
        for (Class<?> c = entityClass; c != null; c = c.getSuperclass()) {
            for (Field f : c.getDeclaredFields()) {
                if (f.getType().getSimpleName().equals(typeSimpleName)) {
                    return f;
                }
            }
        }
        return null;
    }

    @MethodMetadata(irId = "custom:controller:TestSupportController:findFieldByName(Class<?>,String)", hash = "0070f76d", zone = 1)
    private Field findFieldByName(Class<?> entityClass, String fieldName) {
        for (Class<?> c = entityClass; c != null; c = c.getSuperclass()) {
            try {
                return c.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ignored) {
                // keep walking up the hierarchy
            }
        }
        return null;
    }

    /**
     * Finds the named field anywhere in the entity's class hierarchy (fields can be
     * declared on a mapped superclass) and coerces the raw JSON value to that
     * field's declared type. Unknown fields, or values that already match, pass
     * through untouched — Jackson binds those as usual.
     */
    @MethodMetadata(irId = "custom:controller:TestSupportController:coerceToFieldType(Class<?>,String,Object)", hash = "e0b4af7c", zone = 1)
    private Object coerceToFieldType(Class<?> entityClass, String fieldName, Object rawValue) {
        if (rawValue == null)
            return null;
        Field field = findFieldByName(entityClass, fieldName);
        if (field == null)
            return rawValue;
        Class<?> targetType = field.getType();
        // Test fixtures reuse the same literal "sample_*" placeholder values on every
        // run; append a short unique suffix so repeated runs against a shared dev DB
        // don't collide with a leftover row's unique constraint. Values that don't
        // follow the placeholder convention (e.g. real enum codes like "MALE") are
        // left untouched since they may be validated against a fixed set.
        if (targetType == String.class && rawValue instanceof String && ((String) rawValue).startsWith("sample_")) {
            return rawValue + "_" + System.nanoTime();
        }
        if (targetType.isInstance(rawValue))
            return rawValue;
        String asString = String.valueOf(rawValue);
        try {
            if (targetType == Double.class || targetType == double.class)
                return Double.parseDouble(asString);
            if (targetType == Float.class || targetType == float.class)
                return Float.parseFloat(asString);
            if (targetType == Long.class || targetType == long.class)
                return Long.parseLong(asString);
            if (targetType == Integer.class || targetType == int.class)
                return Integer.parseInt(asString);
            if (targetType == Boolean.class || targetType == boolean.class)
                return Boolean.parseBoolean(asString);
            if (targetType == LocalDateTime.class)
                return LocalDateTime.parse(asString);
            if (targetType == LocalDate.class)
                return LocalDate.parse(asString);
            if (targetType == String.class)
                return asString;
        } catch (Exception parseFailed) {
            log.warn("Could not parse '{}' as {} for field '{}', using a default value instead", asString, targetType.getSimpleName(), fieldName);
        }
        // Value couldn't be parsed as the target type — fall back to a type-appropriate
        // default rather than failing the whole entity creation over one bad field.
        if (targetType == Double.class || targetType == double.class)
            return 0.0;
        if (targetType == Float.class || targetType == float.class)
            return 0.0f;
        if (targetType == Long.class || targetType == long.class)
            return 0L;
        if (targetType == Integer.class || targetType == int.class)
            return 0;
        if (targetType == Boolean.class || targetType == boolean.class)
            return false;
        if (targetType == LocalDateTime.class)
            return LocalDateTime.now();
        if (targetType == LocalDate.class)
            return LocalDate.now();
        if (targetType == String.class)
            return asString;
        return rawValue;
    }
}
