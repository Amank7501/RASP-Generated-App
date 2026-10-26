package com.example.app.controller;

import com.example.app.annotation.MethodMetadata;
import com.example.app.dto.*;
import com.example.app.entity.*;
import com.example.app.service.GrievanceService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/grievances")
public class GrievanceController {

    @Autowired()
    private GrievanceService grievanceService;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @MethodMetadata(irId = "create_grievance_ctrl_sbv", hash = "7f4f89eb", zone = 1)
    public ResponseEntity<GrievanceCrudResponse> createGrievance(@Valid @RequestBody CreateGrievanceCrudRequest request) {
        beforeCreateGrievance(request);
        try {
            return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(grievanceService.createGrievance(request));
        } catch (Exception e) {
            throw e;
        } finally {
            afterCreateGrievance(request);
        }
    }

    @MethodMetadata(irId = "hook-before-create_grievance_ctrl_sbv", hash = "c5c6ea07", zone = 2)
    public void beforeCreateGrievance(@Valid @RequestBody CreateGrievanceCrudRequest request) {
    }

    @MethodMetadata(irId = "hook-after-create_grievance_ctrl_sbv", hash = "238b5b49", zone = 2)
    public void afterCreateGrievance(@Valid @RequestBody CreateGrievanceCrudRequest request) {
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @MethodMetadata(irId = "get_grievance_by_id_ctrl_8ra", hash = "1829bc20", zone = 1)
    public ResponseEntity<GrievanceCrudResponse> getGrievanceById(@PathVariable String id) {
        beforeGetGrievanceById(id);
        try {
            return ResponseEntity.ok(grievanceService.getGrievanceById(id));
        } catch (Exception e) {
            throw e;
        } finally {
            afterGetGrievanceById(id);
        }
    }

    @MethodMetadata(irId = "hook-before-get_grievance_by_id_ctrl_8ra", hash = "3f93daef", zone = 2)
    public void beforeGetGrievanceById(@PathVariable String id) {
    }

    @MethodMetadata(irId = "hook-after-get_grievance_by_id_ctrl_8ra", hash = "46c2bed0", zone = 2)
    public void afterGetGrievanceById(@PathVariable String id) {
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @MethodMetadata(irId = "list_grievances_ctrl_tyj", hash = "6ce84d23", zone = 1)
    public ResponseEntity<List<GrievanceCrudResponse>> listGrievances() {
        beforeListGrievances();
        try {
            return ResponseEntity.ok(grievanceService.listGrievances());
        } catch (Exception e) {
            throw e;
        } finally {
            afterListGrievances();
        }
    }

    @MethodMetadata(irId = "hook-before-list_grievances_ctrl_tyj", hash = "19f80653", zone = 2)
    public void beforeListGrievances() {
    }

    @MethodMetadata(irId = "hook-after-list_grievances_ctrl_tyj", hash = "5b60d58a", zone = 2)
    public void afterListGrievances() {
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @MethodMetadata(irId = "update_grievance_ctrl_ujs", hash = "b5843bd9", zone = 1)
    public ResponseEntity<GrievanceCrudResponse> updateGrievance(@PathVariable String id, @Valid @RequestBody UpdateGrievanceCrudRequest request) {
        beforeUpdateGrievance(id, request);
        try {
            return ResponseEntity.ok(grievanceService.updateGrievance(id, request));
        } catch (Exception e) {
            throw e;
        } finally {
            afterUpdateGrievance(id, request);
        }
    }

    @MethodMetadata(irId = "hook-before-update_grievance_ctrl_ujs", hash = "20477212", zone = 2)
    public void beforeUpdateGrievance(@PathVariable String id, @Valid @RequestBody UpdateGrievanceCrudRequest request) {
    }

    @MethodMetadata(irId = "hook-after-update_grievance_ctrl_ujs", hash = "24dea919", zone = 2)
    public void afterUpdateGrievance(@PathVariable String id, @Valid @RequestBody UpdateGrievanceCrudRequest request) {
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @MethodMetadata(irId = "delete_grievance_ctrl_v02", hash = "dd0e859f", zone = 1)
    public ResponseEntity<Void> deleteGrievance(@PathVariable String id) {
        beforeDeleteGrievance(id);
        try {
            grievanceService.deleteGrievance(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw e;
        } finally {
            afterDeleteGrievance(id);
        }
    }

    @MethodMetadata(irId = "hook-before-delete_grievance_ctrl_v02", hash = "8d402032", zone = 2)
    public void beforeDeleteGrievance(@PathVariable String id) {
    }

    @MethodMetadata(irId = "hook-after-delete_grievance_ctrl_v02", hash = "4d7aa0e9", zone = 2)
    public void afterDeleteGrievance(@PathVariable String id) {
    }

    /*
 * Operation    : Submit Grievance with Optional Attachments
 * Usecase ID   : submit_grievance
 * Usecase Name : Submit Grievance
 * Comment      : Endpoint for authenticated users to submit a new grievance with optional attachments. Returns confirmation with generated Grievance ID.
 */
    @PostMapping(value = "/submit")
    @PreAuthorize("hasRole('USER')")
    @MethodMetadata(irId = "ctrl_submit_grievance_with_attachments_f53", hash = "3c5bad98", zone = 1)
    public ResponseEntity<SubmitGrievanceResponse> submitGrievanceWithAttachments(@RequestBody @Valid SubmitGrievanceRequest request, Principal principal) {
        return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(grievanceService.submitGrievanceWithAttachments(request, principal));
    }

    /*
 * Operation    : List User's Own Grievances
 * Usecase ID   : view_my_grievances
 * Usecase Name : View My Grievances
 * Comment      : Retrieve all grievances submitted by the authenticated user
 */
    @GetMapping(value = "/my-grievances")
    @PreAuthorize("hasRole('USER')")
    @MethodMetadata(irId = "list_user_grievances_ctrl_37y", hash = "0af38e28", zone = 1)
    public ResponseEntity<List<GrievanceResponse>> listMyGrievances(Principal principal) {
        return ResponseEntity.ok(grievanceService.listUserGrievances(principal));
    }

    /*
 * Operation    : Retrieve Complete Grievance Details with Status History
 * Usecase ID   : view_grievance_details
 * Usecase Name : View Grievance Details
 * Comment      : Endpoint to retrieve complete grievance details with attachments and status history
 */
    @GetMapping(value = "/{grievanceId}/details")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @MethodMetadata(irId = "grievance_get_details_with_history_4zz", hash = "297886c5", zone = 1)
    public ResponseEntity<GrievanceDetailsResponse> getGrievanceDetailsWithHistory(@PathVariable String grievanceId) {
        return ResponseEntity.ok(grievanceService.retrieveGrievanceDetailsWithHistory(grievanceId));
    }

    /*
 * Operation    : Search and Filter Grievances
 * Usecase ID   : view_all_grievances
 * Usecase Name : View All Grievances
 * Comment      : Admin endpoint to search and filter grievances by multiple criteria. Accepts filter parameters and returns a list of matching grievances with key details.
 */
    @PostMapping(value = "/search")
    @PreAuthorize("hasRole('ADMIN')")
    @MethodMetadata(irId = "ctrl_search_filter_grievances_001_35s", hash = "94f469c3", zone = 1)
    public ResponseEntity<List<GrievanceFilterResponse>> searchAndFilterGrievances(@RequestBody @Valid GrievanceFilterRequest filterRequest) {
        return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(grievanceService.searchAndFilterGrievances(filterRequest));
    }

    /*
 * Operation    : Update Grievance Status with Resolution Remarks
 * Usecase ID   : update_grievance_status
 * Usecase Name : Update Grievance Status
 * Comment      : Admin endpoint to update grievance status with resolution remarks and audit trail
 */
    @PutMapping(value = "/{grievanceId}")
    @PreAuthorize("hasRole('ADMIN')")
    @MethodMetadata(irId = "ctrl_update_grievance_with_resolution_mwl", hash = "23c9f6f0", zone = 1)
    public ResponseEntity<UpdateGrievanceResponse> updateGrievanceWithResolution(@PathVariable String grievanceId, @RequestBody @Valid UpdateGrievanceRequest request) {
        return ResponseEntity.ok(grievanceService.updateGrievanceWithResolution(grievanceId, request));
    }

    /*
 * Operation    : Retrieve Grievance Dashboard Statistics
 * Usecase ID   : view_admin_dashboard
 * Usecase Name : View Admin Dashboard
 * Comment      : Admin endpoint to retrieve aggregated grievance statistics for dashboard display
 */
    @GetMapping(value = "/dashboard/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    @MethodMetadata(irId = "retrieve_grievance_dashboard_statistics_i49", hash = "72218d33", zone = 1)
    public ResponseEntity<GrievanceDashboardStatisticsResponse> getGrievanceDashboardStatistics() {
        return ResponseEntity.ok(grievanceService.getGrievanceDashboardStatistics());
    }

    /*
 * Operation    : Retrieve User Dashboard Summary
 * Usecase ID   : view_user_dashboard
 * Usecase Name : View User Dashboard
 * Comment      : Retrieve the logged-in user's grievance dashboard summary
 */
    @GetMapping(value = "/dashboard/summary")
    @PreAuthorize("hasRole('USER')")
    @MethodMetadata(irId = "ctrl_get_dashboard_summary_2a3", hash = "16b4432e", zone = 1)
    public ResponseEntity<DashboardSummaryResponse> getDashboardSummary(Principal principal) {
        return ResponseEntity.ok(grievanceService.getDashboardSummary(principal));
    }
}
