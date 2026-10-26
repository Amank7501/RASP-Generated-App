package com.example.app.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data()
@Builder()
@NoArgsConstructor()
@AllArgsConstructor()
public class GrievanceDetailsResponse {

    private String id;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotBlank
    private String grievanceId;

    public String getGrievanceId() {
        return this.grievanceId;
    }

    public void setGrievanceId(String grievanceId) {
        this.grievanceId = grievanceId;
    }

    @NotBlank
    private String category;

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @NotBlank
    private String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotBlank
    private String description;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotBlank
    private String status;

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @NotNull
    private LocalDateTime submissionDate;

    public LocalDateTime getSubmissionDate() {
        return this.submissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

    @NotNull
    private LocalDateTime resolutionDate;

    public LocalDateTime getResolutionDate() {
        return this.resolutionDate;
    }

    public void setResolutionDate(LocalDateTime resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    @NotBlank
    private String resolutionRemarks;

    public String getResolutionRemarks() {
        return this.resolutionRemarks;
    }

    public void setResolutionRemarks(String resolutionRemarks) {
        this.resolutionRemarks = resolutionRemarks;
    }

    @NotNull
    private List<GrievanceAttachmentResponse> attachments;

    public List<GrievanceAttachmentResponse> getAttachments() {
        return this.attachments;
    }

    public void setAttachments(List<GrievanceAttachmentResponse> attachments) {
        this.attachments = attachments;
    }

    @NotNull
    private List<GrievanceStatusHistoryResponse> statusHistory;

    public List<GrievanceStatusHistoryResponse> getStatusHistory() {
        return this.statusHistory;
    }

    public void setStatusHistory(List<GrievanceStatusHistoryResponse> statusHistory) {
        this.statusHistory = statusHistory;
    }
}
