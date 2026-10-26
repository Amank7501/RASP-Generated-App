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
public class DashboardSummaryResponse {

    @NotNull
    private Integer totalGrievances;

    public Integer getTotalGrievances() {
        return this.totalGrievances;
    }

    public void setTotalGrievances(Integer totalGrievances) {
        this.totalGrievances = totalGrievances;
    }

    @NotNull
    private Integer pendingCount;

    public Integer getPendingCount() {
        return this.pendingCount;
    }

    public void setPendingCount(Integer pendingCount) {
        this.pendingCount = pendingCount;
    }

    @NotNull
    private Integer inProgressCount;

    public Integer getInProgressCount() {
        return this.inProgressCount;
    }

    public void setInProgressCount(Integer inProgressCount) {
        this.inProgressCount = inProgressCount;
    }

    @NotNull
    private Integer resolvedCount;

    public Integer getResolvedCount() {
        return this.resolvedCount;
    }

    public void setResolvedCount(Integer resolvedCount) {
        this.resolvedCount = resolvedCount;
    }

    @NotNull
    private Integer rejectedCount;

    public Integer getRejectedCount() {
        return this.rejectedCount;
    }

    public void setRejectedCount(Integer rejectedCount) {
        this.rejectedCount = rejectedCount;
    }
}
