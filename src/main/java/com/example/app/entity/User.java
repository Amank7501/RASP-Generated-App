package com.example.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

@Entity()
@Builder()
@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Getter()
@Setter()
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })
public class User {

    @Id()
    @GeneratedValue()
    @UuidGenerator()
    @NotNull()
    private String id;

    @Column(nullable = false)
    @NotNull()
    @Size(min = 1, max = 200)
    private String name;

    @Column(nullable = false, unique = true)
    @NotNull()
    @Pattern(regexp = "^[^@]+@[^@]+\\.[^@]+$")
    @Size(max = 255)
    private String email;

    @Column(nullable = false)
    @NotNull()
    @Pattern(regexp = "^[0-9]{10}$")
    @Size(max = 20)
    private String mobileNumber;

    @CreationTimestamp()
    @Column(nullable = false)
    @NotNull()
    private LocalDateTime createdAt;
}
