package com.example.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import com.example.app.annotation.MethodMetadata;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Value("${keycloak.client-id}")
    private String clientId;

    // Comma-separated list of origins / origin patterns, e.g. http://localhost:3000,https://*.example.com
    @Value("${spring.security.corse.allowed-origins}")
    private String allowedOrigins;

    /**
     * Main security filter chain.
     *  - CORS is enabled so the browser preflight (OPTIONS) is answered before authentication
     *  - /auth/register and /auth/login are open (no token required)
     *  - Everything else requires a valid Keycloak JWT
     *  - Session is STATELESS — no server-side session, safe for REST APIs
     */
    @Bean
    @MethodMetadata(irId = "custom:config:SecurityConfig:securityFilterChain(HttpSecurity)", hash = "2649c092", zone = 1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource())).csrf(AbstractHttpConfigurer::disable).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll().requestMatchers("/auth/register", "/auth/login", "/auth/assign-role").permitAll().anyRequest().authenticated()).oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder()).jwtAuthenticationConverter(jwtAuthenticationConverter())));
        return http.build();
    }

    /**
     * Validates tokens locally (signature via the realm JWKS, issuer, expiry) instead of
     * calling Keycloak's introspection endpoint on every request. Introspection returns
     * {"active":false} for valid tokens when the token's audience does not match the
     * introspecting client, which makes every protected call 401.
     */
    @Bean
    @MethodMetadata(irId = "custom:config:SecurityConfig:jwtDecoder()", hash = "17b8f772", zone = 1)
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
        OAuth2TokenValidator<Jwt> validator = new DelegatingOAuth2TokenValidator<>(JwtValidators.createDefaultWithIssuer(issuerUri));
        decoder.setJwtValidator(validator);
        return decoder;
    }

    /**
     * Maps Keycloak client roles (resource_access.<client-id>.roles) into Spring Security
     * authorities (e.g., ROLE_ADMIN).
     */
    @Bean
    @MethodMetadata(irId = "custom:config:SecurityConfig:jwtAuthenticationConverter()", hash = "c56778ef", zone = 1)
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(this::extractAuthorities);
        return converter;
    }

    @SuppressWarnings("unchecked")
    @MethodMetadata(irId = "custom:config:SecurityConfig:extractAuthorities(Jwt)", hash = "02d834de", zone = 1)
    private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Map<String, Object> resourceAccess = jwt.getClaimAsMap("resource_access");
        if (resourceAccess != null && resourceAccess.get(clientId) instanceof Map<?, ?> client) {
            Object roles = ((Map<String, Object>) client).get("roles");
            if (roles instanceof Collection<?> roleList) {
                for (Object role : roleList) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                }
            }
        }
        return authorities;
    }

    @Bean
    @MethodMetadata(irId = "custom:config:SecurityConfig:corsConfigurationSource()", hash = "f266793e", zone = 1)
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Patterns (not plain origins): "*" is invalid together with allowCredentials(true)
        // when set via setAllowedOrigins, but is accepted as a pattern.
        List<String> origins = Arrays.stream(allowedOrigins.split(",")).map(String::trim).filter(o -> !o.isEmpty()).toList();
        configuration.setAllowedOriginPatterns(origins);
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
