package com.example.app.service;

import com.example.app.annotation.MethodMetadata;
import com.example.app.dto.*;
import com.example.app.entity.*;
import com.example.app.exception.NotFoundException;
import com.example.app.repository.GrievanceAttachmentRepository;
import com.example.app.repository.GrievanceRepository;
import com.example.app.repository.GrievanceStatusHistoryRepository;
import com.example.app.repository.UserRepository;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service()
public class GrievanceService {

    @Autowired()
    private UserRepository userRepository;

    @MethodMetadata(irId = "create_grievance_svc_bhp", hash = "66cf02f6", zone = 1)
    public GrievanceCrudResponse createGrievance(CreateGrievanceCrudRequest request) {
        beforeCreateGrievance(request);
        try {
            User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new NotFoundException("User not found with id: " + request.getUserId()));
            Grievance grievance = new Grievance();
            grievance.setCategory(request.getCategory());
            grievance.setTitle(request.getTitle());
            grievance.setDescription(request.getDescription());
            grievance.setStatus(request.getStatus());
            grievance.setResolutionRemarks(request.getResolutionRemarks());
            grievance.setUser(user);
            Grievance saved = grievanceRepository.save(grievance);
            GrievanceCrudResponse response = new GrievanceCrudResponse();
            response.setId(saved.getId());
            response.setGrievanceId(saved.getGrievanceId());
            response.setCategory(saved.getCategory());
            response.setTitle(saved.getTitle());
            response.setDescription(saved.getDescription());
            response.setStatus(saved.getStatus());
            response.setSubmissionDate(saved.getSubmissionDate());
            response.setResolutionDate(saved.getResolutionDate());
            response.setResolutionRemarks(saved.getResolutionRemarks());
            if (saved.getUser() != null) {
                response.setUserId(saved.getUser().getId());
            }
            return response;
        } catch (Exception e) {
            throw e;
        } finally {
            afterCreateGrievance(request);
        }
    }

    @MethodMetadata(irId = "hook-before-create_grievance_svc_bhp", hash = "2f91294e", zone = 2)
    public void beforeCreateGrievance(CreateGrievanceCrudRequest request) {
    }

    @MethodMetadata(irId = "hook-after-create_grievance_svc_bhp", hash = "d178ac77", zone = 2)
    public void afterCreateGrievance(CreateGrievanceCrudRequest request) {
    }

    @MethodMetadata(irId = "get_grievance_by_id_svc_ee6", hash = "49d6a831", zone = 1)
    public GrievanceCrudResponse getGrievanceById(String id) {
        beforeGetGrievanceById(id);
        try {
            Grievance found = grievanceRepository.findById(id).orElseThrow(() -> new NotFoundException("Grievance not found with id: " + id));
            GrievanceCrudResponse response = new GrievanceCrudResponse();
            response.setId(found.getId());
            response.setGrievanceId(found.getGrievanceId());
            response.setCategory(found.getCategory());
            response.setTitle(found.getTitle());
            response.setDescription(found.getDescription());
            response.setStatus(found.getStatus());
            response.setSubmissionDate(found.getSubmissionDate());
            response.setResolutionDate(found.getResolutionDate());
            response.setResolutionRemarks(found.getResolutionRemarks());
            if (found.getUser() != null) {
                response.setUserId(found.getUser().getId());
            }
            return response;
        } catch (Exception e) {
            throw e;
        } finally {
            afterGetGrievanceById(id);
        }
    }

    @MethodMetadata(irId = "hook-before-get_grievance_by_id_svc_ee6", hash = "b63bafa7", zone = 2)
    public void beforeGetGrievanceById(String id) {
    }

    @MethodMetadata(irId = "hook-after-get_grievance_by_id_svc_ee6", hash = "fb325df7", zone = 2)
    public void afterGetGrievanceById(String id) {
    }

    @MethodMetadata(irId = "list_grievances_svc_cz9", hash = "5a0a6c05", zone = 1)
    public List<GrievanceCrudResponse> listGrievances() {
        beforeListGrievances();
        try {
            List<GrievanceCrudResponse> responses = new ArrayList<>();
            for (Grievance item : grievanceRepository.findAll()) {
                GrievanceCrudResponse response = new GrievanceCrudResponse();
                response.setId(item.getId());
                response.setGrievanceId(item.getGrievanceId());
                response.setCategory(item.getCategory());
                response.setTitle(item.getTitle());
                response.setDescription(item.getDescription());
                response.setStatus(item.getStatus());
                response.setSubmissionDate(item.getSubmissionDate());
                response.setResolutionDate(item.getResolutionDate());
                response.setResolutionRemarks(item.getResolutionRemarks());
                if (item.getUser() != null) {
                    response.setUserId(item.getUser().getId());
                }
                responses.add(response);
            }
            return responses;
        } catch (Exception e) {
            throw e;
        } finally {
            afterListGrievances();
        }
    }

    @MethodMetadata(irId = "hook-before-list_grievances_svc_cz9", hash = "19f80653", zone = 2)
    public void beforeListGrievances() {
    }

    @MethodMetadata(irId = "hook-after-list_grievances_svc_cz9", hash = "5b60d58a", zone = 2)
    public void afterListGrievances() {
    }

    @MethodMetadata(irId = "update_grievance_svc_px2", hash = "5f12bb84", zone = 1)
    public GrievanceCrudResponse updateGrievance(String id, UpdateGrievanceCrudRequest request) {
        beforeUpdateGrievance(id, request);
        try {
            Grievance existing = grievanceRepository.findById(id).orElseThrow(() -> new NotFoundException("Grievance not found with id: " + id));
            if (request.getUserId() != null) {
                User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new NotFoundException("User not found with id: " + request.getUserId()));
                existing.setUser(user);
            }
            if (request.getCategory() != null) {
                existing.setCategory(request.getCategory());
            }
            if (request.getTitle() != null) {
                existing.setTitle(request.getTitle());
            }
            if (request.getDescription() != null) {
                existing.setDescription(request.getDescription());
            }
            if (request.getStatus() != null) {
                existing.setStatus(request.getStatus());
            }
            if (request.getResolutionRemarks() != null) {
                existing.setResolutionRemarks(request.getResolutionRemarks());
            }
            Grievance saved = grievanceRepository.save(existing);
            GrievanceCrudResponse response = new GrievanceCrudResponse();
            response.setId(saved.getId());
            response.setGrievanceId(saved.getGrievanceId());
            response.setCategory(saved.getCategory());
            response.setTitle(saved.getTitle());
            response.setDescription(saved.getDescription());
            response.setStatus(saved.getStatus());
            response.setSubmissionDate(saved.getSubmissionDate());
            response.setResolutionDate(saved.getResolutionDate());
            response.setResolutionRemarks(saved.getResolutionRemarks());
            if (saved.getUser() != null) {
                response.setUserId(saved.getUser().getId());
            }
            return response;
        } catch (Exception e) {
            throw e;
        } finally {
            afterUpdateGrievance(id, request);
        }
    }

    @MethodMetadata(irId = "hook-before-update_grievance_svc_px2", hash = "01382447", zone = 2)
    public void beforeUpdateGrievance(String id, UpdateGrievanceCrudRequest request) {
    }

    @MethodMetadata(irId = "hook-after-update_grievance_svc_px2", hash = "2e01d180", zone = 2)
    public void afterUpdateGrievance(String id, UpdateGrievanceCrudRequest request) {
    }

    @MethodMetadata(irId = "delete_grievance_svc_hij", hash = "47aa94dc", zone = 1)
    public void deleteGrievance(String id) {
        beforeDeleteGrievance(id);
        try {
            grievanceRepository.findById(id).orElseThrow(() -> new NotFoundException("Grievance not found with id: " + id));
            grievanceRepository.deleteById(id);
        } catch (Exception e) {
            throw e;
        } finally {
            afterDeleteGrievance(id);
        }
    }

    @MethodMetadata(irId = "hook-before-delete_grievance_svc_hij", hash = "644757ec", zone = 2)
    public void beforeDeleteGrievance(String id) {
    }

    @MethodMetadata(irId = "hook-after-delete_grievance_svc_hij", hash = "638a5c63", zone = 2)
    public void afterDeleteGrievance(String id) {
    }

    @Autowired()
    private GrievanceRepository grievanceRepository;

    @Autowired()
    private GrievanceAttachmentRepository grievanceAttachmentRepository;

    @Autowired()
    private GrievanceStatusHistoryRepository grievanceStatusHistoryRepository;

    @Autowired()
    private KeycloakAuthService keycloakAuthService;

    /*
 * Operation    : Submit Grievance with Optional Attachments
 * Usecase ID   : submit_grievance
 * Usecase Name : Submit Grievance
 * Comment      : Submits a new grievance with optional file attachments. Validates inputs, generates unique grievance ID, creates grievance record linked to authenticated user, attaches files, and records initial status history.
 */
    @MethodMetadata(irId = "svc_submit_grievance_with_attachments_hv7", hash = "31a7096d", zone = 1)
    public SubmitGrievanceResponse submitGrievanceWithAttachments(SubmitGrievanceRequest request, Principal principal) {
        // Resolve authenticated user
        String userId = keycloakAuthService.getUserId(principal);
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        // Validate mandatory fields
        if (request.getCategory() == null || request.getCategory().trim().isEmpty()) {
            throw new IllegalArgumentException("Category is required");
        }
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (request.getDescription() == null || request.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Description is required");
        }
        // Validate file attachments if provided
        if (request.getAttachmentFileNames() != null && !request.getAttachmentFileNames().isEmpty()) {
            for (int i = 0; i < request.getAttachmentFileNames().size(); i++) {
                String fileType = request.getAttachmentFileTypes().get(i);
                Long fileSize = request.getAttachmentFileSizes().get(i);
                if (!fileType.equals("PDF") && !fileType.equals("JPG") && !fileType.equals("PNG")) {
                    throw new IllegalArgumentException("Invalid file type: " + fileType + ". Allowed types: PDF, JPG, PNG");
                }
                if (fileSize > 5242880L) {
                    throw new IllegalArgumentException("File size exceeds 5 MB limit: " + request.getAttachmentFileNames().get(i));
                }
            }
        }
        // Generate unique Grievance ID in format GRV-XXXXX
        String grievanceId = "GRV-" + System.currentTimeMillis();
        // Create Grievance record
        Grievance grievance = new Grievance();
        grievance.setId(java.util.UUID.randomUUID().toString());
        grievance.setGrievanceId(grievanceId);
        grievance.setCategory(request.getCategory());
        grievance.setTitle(request.getTitle());
        grievance.setDescription(request.getDescription());
        grievance.setStatus("Pending");
        grievance.setSubmissionDate(java.time.LocalDateTime.now());
        grievance.setUser(user);
        // Save grievance
        Grievance savedGrievance = grievanceRepository.save(grievance);
        // Attach uploaded files to the grievance
        int attachmentCount = 0;
        if (request.getAttachmentFileNames() != null && !request.getAttachmentFileNames().isEmpty()) {
            for (int i = 0; i < request.getAttachmentFileNames().size(); i++) {
                GrievanceAttachment attachment = new GrievanceAttachment();
                attachment.setId(java.util.UUID.randomUUID().toString());
                attachment.setFileName(request.getAttachmentFileNames().get(i));
                attachment.setFileType(request.getAttachmentFileTypes().get(i));
                attachment.setFilePath(request.getAttachmentFilePaths().get(i));
                attachment.setFileSize(request.getAttachmentFileSizes().get(i));
                attachment.setUploadedAt(java.time.LocalDateTime.now());
                attachment.setGrievance(savedGrievance);
                grievanceAttachmentRepository.save(attachment);
                attachmentCount++;
            }
        }
        // Create initial GrievanceStatusHistory entry
        GrievanceStatusHistory statusHistory = new GrievanceStatusHistory();
        statusHistory.setId(java.util.UUID.randomUUID().toString());
        statusHistory.setPreviousStatus(null);
        statusHistory.setNewStatus("Pending");
        statusHistory.setChangedAt(java.time.LocalDateTime.now());
        statusHistory.setGrievance(savedGrievance);
        grievanceStatusHistoryRepository.save(statusHistory);
        // Build and return response
        SubmitGrievanceResponse response = new SubmitGrievanceResponse();
        response.setGrievanceId(savedGrievance.getGrievanceId());
        response.setId(savedGrievance.getId());
        response.setCategory(savedGrievance.getCategory());
        response.setTitle(savedGrievance.getTitle());
        response.setDescription(savedGrievance.getDescription());
        response.setStatus(savedGrievance.getStatus());
        response.setSubmissionDate(savedGrievance.getSubmissionDate());
        response.setAttachmentCount(attachmentCount);
        return response;
    }

    /*
 * Operation    : List User's Own Grievances
 * Usecase ID   : view_my_grievances
 * Usecase Name : View My Grievances
 * Comment      : Retrieve all grievances for the authenticated user, mapped to response DTOs
 */
    @MethodMetadata(irId = "list_user_grievances_svc_9da", hash = "6e65567d", zone = 1)
    public List<GrievanceResponse> listUserGrievances(Principal principal) {
        String userId = keycloakAuthService.getUserId(principal);
        List<Grievance> grievances = grievanceRepository.findByUserId(userId);
        return // Collectors import required
        // Collectors import required
        grievances.stream().map(g -> new GrievanceResponse(g.getId(), g.getGrievanceId(), g.getTitle(), g.getCategory(), g.getStatus(), g.getSubmissionDate())).collect(Collectors.toList());
    }

    /*
 * Operation    : Retrieve Complete Grievance Details with Status History
 * Usecase ID   : view_grievance_details
 * Usecase Name : View Grievance Details
 * Comment      : Retrieves complete grievance details including attachments and full status history timeline. Note: Collectors is used for stream operations.
 */
    @MethodMetadata(irId = "grievance_retrieve_details_with_history_xbo", hash = "7547bde5", zone = 1)
    public GrievanceDetailsResponse retrieveGrievanceDetailsWithHistory(String grievanceId) {
        Grievance grievance = grievanceRepository.findById(grievanceId).orElseThrow(() -> new RuntimeException("Grievance not found with ID: " + grievanceId));
        List<GrievanceAttachment> allAttachments = grievanceAttachmentRepository.findAll();
        List<GrievanceAttachment> grievanceAttachments = allAttachments.stream().filter(attachment -> attachment.getGrievance() != null && attachment.getGrievance().getId().equals(grievanceId)).collect(Collectors.toList());
        List<GrievanceStatusHistory> allStatusHistory = grievanceStatusHistoryRepository.findAll();
        List<GrievanceStatusHistory> grievanceStatusHistory = allStatusHistory.stream().filter(history -> history.getGrievance() != null && history.getGrievance().getId().equals(grievanceId)).sorted((h1, h2) -> h1.getChangedAt().compareTo(h2.getChangedAt())).collect(Collectors.toList());
        List<GrievanceAttachmentResponse> attachmentResponses = grievanceAttachments.stream().map(attachment -> new GrievanceAttachmentResponse(attachment.getId(), attachment.getFileName(), attachment.getFileType(), attachment.getFilePath(), attachment.getFileSize(), attachment.getUploadedAt())).collect(Collectors.toList());
        List<GrievanceStatusHistoryResponse> statusHistoryResponses = grievanceStatusHistory.stream().map(history -> new GrievanceStatusHistoryResponse(history.getId(), history.getPreviousStatus(), history.getNewStatus(), history.getChangedAt())).collect(Collectors.toList());
        GrievanceDetailsResponse response = new GrievanceDetailsResponse(grievance.getId(), grievance.getGrievanceId(), grievance.getCategory(), grievance.getTitle(), grievance.getDescription(), grievance.getStatus(), grievance.getSubmissionDate(), grievance.getResolutionDate(), grievance.getResolutionRemarks(), attachmentResponses, statusHistoryResponses);
        return response;
    }

    /*
 * Operation    : Search and Filter Grievances
 * Usecase ID   : view_all_grievances
 * Usecase Name : View All Grievances
 * Comment      : Retrieves all grievances and applies client-side filtering based on provided criteria. Returns a list of GrievanceFilterResponse objects containing grievance ID, user name, category, title, submission date, and status.
 */
    @MethodMetadata(irId = "svc_search_filter_grievances_001_t7k", hash = "20cbce98", zone = 1)
    public List<GrievanceFilterResponse> searchAndFilterGrievances(GrievanceFilterRequest filterRequest) {
        List<Grievance> allGrievances = grievanceRepository.findAll();
        List<GrievanceFilterResponse> results = allGrievances.stream().filter(grievance -> {
            if (filterRequest.getGrievanceId() != null && !filterRequest.getGrievanceId().isEmpty()) {
                if (!grievance.getGrievanceId().contains(filterRequest.getGrievanceId())) {
                    return false;
                }
            }
            if (filterRequest.getUserName() != null && !filterRequest.getUserName().isEmpty()) {
                if (grievance.getUser() == null || !grievance.getUser().getName().toLowerCase().contains(filterRequest.getUserName().toLowerCase())) {
                    return false;
                }
            }
            if (filterRequest.getTitle() != null && !filterRequest.getTitle().isEmpty()) {
                if (!grievance.getTitle().toLowerCase().contains(filterRequest.getTitle().toLowerCase())) {
                    return false;
                }
            }
            if (filterRequest.getCategory() != null && !filterRequest.getCategory().isEmpty()) {
                if (!grievance.getCategory().equals(filterRequest.getCategory())) {
                    return false;
                }
            }
            if (filterRequest.getStatus() != null && !filterRequest.getStatus().isEmpty()) {
                if (!grievance.getStatus().equals(filterRequest.getStatus())) {
                    return false;
                }
            }
            if (filterRequest.getStartDate() != null && grievance.getSubmissionDate().isBefore(filterRequest.getStartDate())) {
                return false;
            }
            if (filterRequest.getEndDate() != null && grievance.getSubmissionDate().isAfter(filterRequest.getEndDate())) {
                return false;
            }
            return true;
        }).map(grievance -> {
            GrievanceFilterResponse response = new GrievanceFilterResponse();
            response.setGrievanceId(grievance.getGrievanceId());
            response.setUserName(grievance.getUser() != null ? grievance.getUser().getName() : "");
            response.setCategory(grievance.getCategory());
            response.setTitle(grievance.getTitle());
            response.setSubmissionDate(grievance.getSubmissionDate());
            response.setStatus(grievance.getStatus());
            return response;
        }).// Collectors import required
        collect(Collectors.toList());
        return results;
    }

    /*
 * Operation    : Update Grievance Status with Resolution Remarks
 * Usecase ID   : update_grievance_status
 * Usecase Name : Update Grievance Status
 * Comment      : Updates grievance status with validation for mandatory resolution remarks on Resolved/Rejected status, records resolution date, and creates audit trail entry.
 */
    @MethodMetadata(irId = "svc_update_grievance_with_resolution_9fq", hash = "5343e928", zone = 1)
    public UpdateGrievanceResponse updateGrievanceWithResolution(String grievanceId, UpdateGrievanceRequest request) {
        // Validate that the grievance exists
        Grievance grievance = grievanceRepository.findById(grievanceId).orElseThrow(() -> new RuntimeException("Grievance not found"));
        // Validate that resolution remarks are provided if status is Resolved or Rejected
        String newStatus = request.getStatus();
        if (("Resolved".equals(newStatus) || "Rejected".equals(newStatus)) && (request.getResolutionRemarks() == null || request.getResolutionRemarks().trim().isEmpty())) {
            throw new RuntimeException("Resolution remarks are mandatory for Resolved or Rejected status");
        }
        // Store the previous status for history
        String previousStatus = grievance.getStatus();
        // Update the grievance status
        grievance.setStatus(newStatus);
        // Store the resolution remarks if provided
        if (request.getResolutionRemarks() != null && !request.getResolutionRemarks().trim().isEmpty()) {
            grievance.setResolutionRemarks(request.getResolutionRemarks());
        }
        // Record the resolution date if status is Resolved or Rejected
        if ("Resolved".equals(newStatus) || "Rejected".equals(newStatus)) {
            grievance.setResolutionDate(LocalDateTime.now());
        }
        // Save the updated grievance
        grievanceRepository.save(grievance);
        // Create a status history entry documenting the status change
        GrievanceStatusHistory history = new GrievanceStatusHistory();
        history.setPreviousStatus(previousStatus);
        history.setNewStatus(newStatus);
        history.setChangedAt(LocalDateTime.now());
        history.setGrievance(grievance);
        grievanceStatusHistoryRepository.save(history);
        // Build and return the response
        UpdateGrievanceResponse response = new UpdateGrievanceResponse();
        response.setId(grievance.getId());
        response.setGrievanceId(grievance.getGrievanceId());
        response.setCategory(grievance.getCategory());
        response.setTitle(grievance.getTitle());
        response.setDescription(grievance.getDescription());
        response.setStatus(grievance.getStatus());
        response.setSubmissionDate(grievance.getSubmissionDate());
        response.setResolutionDate(grievance.getResolutionDate());
        response.setResolutionRemarks(grievance.getResolutionRemarks());
        return response;
    }

    /*
 * Operation    : Retrieve Grievance Dashboard Statistics
 * Usecase ID   : view_admin_dashboard
 * Usecase Name : View Admin Dashboard
 * Comment      : Retrieves aggregated grievance statistics grouped by status for dashboard display
 */
    @MethodMetadata(irId = "get_grievance_dashboard_statistics_ik4", hash = "e07e12e6", zone = 1)
    public GrievanceDashboardStatisticsResponse getGrievanceDashboardStatistics() {
        Long totalGrievances = grievanceRepository.count();
        Long pendingCount = grievanceRepository.countByStatus("Pending");
        Long inProgressCount = grievanceRepository.countByStatus("In Progress");
        Long resolvedCount = grievanceRepository.countByStatus("Resolved");
        Long rejectedCount = grievanceRepository.countByStatus("Rejected");
        GrievanceDashboardStatisticsResponse response = new GrievanceDashboardStatisticsResponse();
        response.setTotalGrievances(totalGrievances);
        response.setPendingCount(pendingCount);
        response.setInProgressCount(inProgressCount);
        response.setResolvedCount(resolvedCount);
        response.setRejectedCount(rejectedCount);
        return response;
    }

    /*
 * Operation    : Retrieve User Dashboard Summary
 * Usecase ID   : view_user_dashboard
 * Usecase Name : View User Dashboard
 * Comment      : Aggregate grievance counts by status for the authenticated user's dashboard
 */
    @MethodMetadata(irId = "svc_get_dashboard_summary_t91", hash = "934a51c2", zone = 1)
    public DashboardSummaryResponse getDashboardSummary(Principal principal) {
        String userId = keycloakAuthService.getUserId(principal);
        List<Grievance> userGrievances = grievanceRepository.findByUserId(userId);
        int totalGrievances = userGrievances.size();
        int pendingCount = (int) userGrievances.stream().filter(g -> "Pending".equals(g.getStatus())).count();
        int inProgressCount = (int) userGrievances.stream().filter(g -> "In Progress".equals(g.getStatus())).count();
        int resolvedCount = (int) userGrievances.stream().filter(g -> "Resolved".equals(g.getStatus())).count();
        int rejectedCount = (int) userGrievances.stream().filter(g -> "Rejected".equals(g.getStatus())).count();
        DashboardSummaryResponse response = new DashboardSummaryResponse();
        response.setTotalGrievances(totalGrievances);
        response.setPendingCount(pendingCount);
        response.setInProgressCount(inProgressCount);
        response.setResolvedCount(resolvedCount);
        response.setRejectedCount(rejectedCount);
        return response;
    }
}
