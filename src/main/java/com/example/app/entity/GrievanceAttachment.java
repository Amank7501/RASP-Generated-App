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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "grievance_attachments")
public class GrievanceAttachment {

    @Id()
    @GeneratedValue()
    @UuidGenerator()
    @NotNull()
    private String id;

    @Column(nullable = false)
    @NotNull()
    @Size(max = 255)
    private String fileName;

    @Column(nullable = false)
    @NotNull()
    private String fileType;

    @Column(nullable = false)
    @NotNull()
    @Size(max = 500)
    private String filePath;

    @Column(nullable = false)
    @NotNull()
    @Min(value = 1)
    @Max(value = 5242880)
    private Long fileSize;

    @Column(nullable = false)
    @NotNull()
    private LocalDateTime uploadedAt;

    @ManyToOne()
    @JoinColumn(name = "grievance_id")
    private Grievance grievance;
}
