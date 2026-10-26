package com.example.app.service;

import com.example.app.annotation.MethodMetadata;
import com.example.app.dto.CreateGrievanceStatusHistoryCrudRequest;
import com.example.app.dto.GrievanceStatusHistoryCrudResponse;
import com.example.app.dto.UpdateGrievanceStatusHistoryCrudRequest;
import com.example.app.entity.GrievanceStatusHistory;
import com.example.app.exception.NotFoundException;
import com.example.app.repository.AdminRepository;
import com.example.app.repository.GrievanceRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class GrievanceStatusHistoryService {

    @Autowired()
    private GrievanceRepository grievanceRepository;

    @Autowired()
    private AdminRepository adminRepository;

    @MethodMetadata(irId = "create_grievance_status_history_svc_rny", hash = "a7b30b7d", zone = 1)
    public GrievanceStatusHistoryCrudResponse createGrievanceStatusHistory(CreateGrievanceStatusHistoryCrudRequest request) {
        beforeCreateGrievanceStatusHistory(request);
        try {
            Grievance grievance = grievanceRepository.findById(request.getGrievanceId()).orElseThrow(() -> new NotFoundException("Grievance not found with id: " + request.getGrievanceId()));
            Admin admin = adminRepository.findById(request.getAdminId()).orElseThrow(() -> new NotFoundException("Admin not found with id: " + request.getAdminId()));
            GrievanceStatusHistory grievanceStatusHistory = new GrievanceStatusHistory();
            grievanceStatusHistory.setPreviousStatus(request.getPreviousStatus());
            grievanceStatusHistory.setNewStatus(request.getNewStatus());
            grievanceStatusHistory.setGrievance(grievance);
            grievanceStatusHistory.setAdmin(admin);
            GrievanceStatusHistory saved = grievanceStatusHistoryRepository.save(grievanceStatusHistory);
            GrievanceStatusHistoryCrudResponse response = new GrievanceStatusHistoryCrudResponse();
            response.setId(saved.getId());
            response.setPreviousStatus(saved.getPreviousStatus());
            response.setNewStatus(saved.getNewStatus());
            response.setChangedAt(saved.getChangedAt());
            if (saved.getGrievance() != null) {
                response.setGrievanceId(saved.getGrievance().getId());
            }
            if (saved.getAdmin() != null) {
                response.setAdminId(saved.getAdmin().getId());
            }
            return response;
        } catch (Exception e) {
            throw e;
        } finally {
            afterCreateGrievanceStatusHistory(request);
        }
    }

    @MethodMetadata(irId = "hook-before-create_grievance_status_history_svc_rny", hash = "2cb24da9", zone = 2)
    public void beforeCreateGrievanceStatusHistory(CreateGrievanceStatusHistoryCrudRequest request) {
    }

    @MethodMetadata(irId = "hook-after-create_grievance_status_history_svc_rny", hash = "0ce9b5cf", zone = 2)
    public void afterCreateGrievanceStatusHistory(CreateGrievanceStatusHistoryCrudRequest request) {
    }

    @MethodMetadata(irId = "get_grievance_status_history_by_id_svc_ycy", hash = "b2462017", zone = 1)
    public GrievanceStatusHistoryCrudResponse getGrievanceStatusHistoryById(String id) {
        beforeGetGrievanceStatusHistoryById(id);
        try {
            GrievanceStatusHistory found = grievanceStatusHistoryRepository.findById(id).orElseThrow(() -> new NotFoundException("GrievanceStatusHistory not found with id: " + id));
            GrievanceStatusHistoryCrudResponse response = new GrievanceStatusHistoryCrudResponse();
            response.setId(found.getId());
            response.setPreviousStatus(found.getPreviousStatus());
            response.setNewStatus(found.getNewStatus());
            response.setChangedAt(found.getChangedAt());
            if (found.getGrievance() != null) {
                response.setGrievanceId(found.getGrievance().getId());
            }
            if (found.getAdmin() != null) {
                response.setAdminId(found.getAdmin().getId());
            }
            return response;
        } catch (Exception e) {
            throw e;
        } finally {
            afterGetGrievanceStatusHistoryById(id);
        }
    }

    @MethodMetadata(irId = "hook-before-get_grievance_status_history_by_id_svc_ycy", hash = "6b928bea", zone = 2)
    public void beforeGetGrievanceStatusHistoryById(String id) {
    }

    @MethodMetadata(irId = "hook-after-get_grievance_status_history_by_id_svc_ycy", hash = "0efc164e", zone = 2)
    public void afterGetGrievanceStatusHistoryById(String id) {
    }

    @MethodMetadata(irId = "list_grievance_status_historys_svc_lzt", hash = "08b2b029", zone = 1)
    public List<GrievanceStatusHistoryCrudResponse> listGrievanceStatusHistorys() {
        beforeListGrievanceStatusHistorys();
        try {
            List<GrievanceStatusHistoryCrudResponse> responses = new ArrayList<>();
            for (GrievanceStatusHistory item : grievanceStatusHistoryRepository.findAll()) {
                GrievanceStatusHistoryCrudResponse response = new GrievanceStatusHistoryCrudResponse();
                response.setId(item.getId());
                response.setPreviousStatus(item.getPreviousStatus());
                response.setNewStatus(item.getNewStatus());
                response.setChangedAt(item.getChangedAt());
                if (item.getGrievance() != null) {
                    response.setGrievanceId(item.getGrievance().getId());
                }
                if (item.getAdmin() != null) {
                    response.setAdminId(item.getAdmin().getId());
                }
                responses.add(response);
            }
            return responses;
        } catch (Exception e) {
            throw e;
        } finally {
            afterListGrievanceStatusHistorys();
        }
    }

    @MethodMetadata(irId = "hook-before-list_grievance_status_historys_svc_lzt", hash = "8089db19", zone = 2)
    public void beforeListGrievanceStatusHistorys() {
    }

    @MethodMetadata(irId = "hook-after-list_grievance_status_historys_svc_lzt", hash = "d8af28c1", zone = 2)
    public void afterListGrievanceStatusHistorys() {
    }

    @MethodMetadata(irId = "update_grievance_status_history_svc_noo", hash = "715873b9", zone = 1)
    public GrievanceStatusHistoryCrudResponse updateGrievanceStatusHistory(String id, UpdateGrievanceStatusHistoryCrudRequest request) {
        beforeUpdateGrievanceStatusHistory(id, request);
        try {
            GrievanceStatusHistory existing = grievanceStatusHistoryRepository.findById(id).orElseThrow(() -> new NotFoundException("GrievanceStatusHistory not found with id: " + id));
            if (request.getGrievanceId() != null) {
                Grievance grievance = grievanceRepository.findById(request.getGrievanceId()).orElseThrow(() -> new NotFoundException("Grievance not found with id: " + request.getGrievanceId()));
                existing.setGrievance(grievance);
            }
            if (request.getAdminId() != null) {
                Admin admin = adminRepository.findById(request.getAdminId()).orElseThrow(() -> new NotFoundException("Admin not found with id: " + request.getAdminId()));
                existing.setAdmin(admin);
            }
            if (request.getPreviousStatus() != null) {
                existing.setPreviousStatus(request.getPreviousStatus());
            }
            if (request.getNewStatus() != null) {
                existing.setNewStatus(request.getNewStatus());
            }
            GrievanceStatusHistory saved = grievanceStatusHistoryRepository.save(existing);
            GrievanceStatusHistoryCrudResponse response = new GrievanceStatusHistoryCrudResponse();
            response.setId(saved.getId());
            response.setPreviousStatus(saved.getPreviousStatus());
            response.setNewStatus(saved.getNewStatus());
            response.setChangedAt(saved.getChangedAt());
            if (saved.getGrievance() != null) {
                response.setGrievanceId(saved.getGrievance().getId());
            }
            if (saved.getAdmin() != null) {
                response.setAdminId(saved.getAdmin().getId());
            }
            return response;
        } catch (Exception e) {
            throw e;
        } finally {
            afterUpdateGrievanceStatusHistory(id, request);
        }
    }

    @MethodMetadata(irId = "hook-before-update_grievance_status_history_svc_noo", hash = "c64896ab", zone = 2)
    public void beforeUpdateGrievanceStatusHistory(String id, UpdateGrievanceStatusHistoryCrudRequest request) {
    }

    @MethodMetadata(irId = "hook-after-update_grievance_status_history_svc_noo", hash = "f1435c82", zone = 2)
    public void afterUpdateGrievanceStatusHistory(String id, UpdateGrievanceStatusHistoryCrudRequest request) {
    }

    @MethodMetadata(irId = "delete_grievance_status_history_svc_77d", hash = "5fb12f12", zone = 1)
    public void deleteGrievanceStatusHistory(String id) {
        beforeDeleteGrievanceStatusHistory(id);
        try {
            grievanceStatusHistoryRepository.findById(id).orElseThrow(() -> new NotFoundException("GrievanceStatusHistory not found with id: " + id));
            grievanceStatusHistoryRepository.deleteById(id);
        } catch (Exception e) {
            throw e;
        } finally {
            afterDeleteGrievanceStatusHistory(id);
        }
    }

    @MethodMetadata(irId = "hook-before-delete_grievance_status_history_svc_77d", hash = "421f5cfc", zone = 2)
    public void beforeDeleteGrievanceStatusHistory(String id) {
    }

    @MethodMetadata(irId = "hook-after-delete_grievance_status_history_svc_77d", hash = "10ed90f5", zone = 2)
    public void afterDeleteGrievanceStatusHistory(String id) {
    }
}
