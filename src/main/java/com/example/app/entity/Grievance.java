package com.example.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
import org.hibernate.annotations.UuidGenerator;

@Entity()
@Builder()
@Data()
@NoArgsConstructor()
@AllArgsConstructor()
@Getter()
@Setter()
@Table(name = "grievances")
public class Grievance {

    @Id()
    @GeneratedValue()
    @UuidGenerator()
    @NotNull()
    private String id;

    @Column(nullable = false, unique = true)
    @NotNull()
    @Pattern(regexp = "^GRV-[0-9]+$")
    private String grievanceId;

    @Column(nullable = false)
    @NotNull()
    private String category;

    @Column(nullable = false)
    @NotNull()
    @Size(min = 1, max = 200)
    private String title;

    @Column(nullable = false)
    @NotNull()
    @Size(min = 1, max = 2000)
    private String description;

    @Column(nullable = false)
    @NotNull()
    private String status;

    @Column(nullable = false)
    @NotNull()
    private LocalDateTime submissionDate;

    private LocalDateTime resolutionDate;

    @Size(max = 2000)
    private String resolutionRemarks;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;
}
