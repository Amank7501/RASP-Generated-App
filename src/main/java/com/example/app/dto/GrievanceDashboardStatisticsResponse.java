package com.example.app.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data()
@Builder()
@NoArgsConstructor()
@AllArgsConstructor()
public class GrievanceDashboardStatisticsResponse {

    @NotNull
    private Long totalGrievances;

    public Long getTotalGrievances() {
        return this.totalGrievances;
    }

    public void setTotalGrievances(Long totalGrievances) {
        this.totalGrievances = totalGrievances;
    }

    @NotNull
    private Long pendingCount;

    public Long getPendingCount() {
        return this.pendingCount;
    }

    public void setPendingCount(Long pendingCount) {
        this.pendingCount = pendingCount;
    }

    @NotNull
    private Long inProgressCount;

    public Long getInProgressCount() {
        return this.inProgressCount;
    }

    public void setInProgressCount(Long inProgressCount) {
        this.inProgressCount = inProgressCount;
    }

    @NotNull
    private Long resolvedCount;

    public Long getResolvedCount() {
        return this.resolvedCount;
    }

    public void setResolvedCount(Long resolvedCount) {
        this.resolvedCount = resolvedCount;
    }

    @NotNull
    private Long rejectedCount;

    public Long getRejectedCount() {
        return this.rejectedCount;
    }

    public void setRejectedCount(Long rejectedCount) {
        this.rejectedCount = rejectedCount;
    }
}
