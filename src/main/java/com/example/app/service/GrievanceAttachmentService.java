package com.example.app.service;

import com.example.app.annotation.MethodMetadata;
import com.example.app.dto.CreateGrievanceAttachmentCrudRequest;
import com.example.app.dto.GrievanceAttachmentCrudResponse;
import com.example.app.dto.UpdateGrievanceAttachmentCrudRequest;
import com.example.app.entity.GrievanceAttachment;
import com.example.app.exception.NotFoundException;
import com.example.app.repository.GrievanceRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class GrievanceAttachmentService {

    @Autowired()
    private GrievanceRepository grievanceRepository;

    @MethodMetadata(irId = "create_grievance_attachment_svc_51f", hash = "9f5b2fa3", zone = 1)
    public GrievanceAttachmentCrudResponse createGrievanceAttachment(CreateGrievanceAttachmentCrudRequest request) {
        beforeCreateGrievanceAttachment(request);
        try {
            Grievance grievance = grievanceRepository.findById(request.getGrievanceId()).orElseThrow(() -> new NotFoundException("Grievance not found with id: " + request.getGrievanceId()));
            GrievanceAttachment grievanceAttachment = new GrievanceAttachment();
            grievanceAttachment.setFileName(request.getFileName());
            grievanceAttachment.setFileType(request.getFileType());
            grievanceAttachment.setFilePath(request.getFilePath());
            grievanceAttachment.setFileSize(request.getFileSize());
            grievanceAttachment.setGrievance(grievance);
            GrievanceAttachment saved = grievanceAttachmentRepository.save(grievanceAttachment);
            GrievanceAttachmentCrudResponse response = new GrievanceAttachmentCrudResponse();
            response.setId(saved.getId());
            response.setFileName(saved.getFileName());
            response.setFileType(saved.getFileType());
            response.setFilePath(saved.getFilePath());
            response.setFileSize(saved.getFileSize());
            response.setUploadedAt(saved.getUploadedAt());
            if (saved.getGrievance() != null) {
                response.setGrievanceId(saved.getGrievance().getId());
            }
            return response;
        } catch (Exception e) {
            throw e;
        } finally {
            afterCreateGrievanceAttachment(request);
        }
    }

    @MethodMetadata(irId = "hook-before-create_grievance_attachment_svc_51f", hash = "e36436c3", zone = 2)
    public void beforeCreateGrievanceAttachment(CreateGrievanceAttachmentCrudRequest request) {
    }

    @MethodMetadata(irId = "hook-after-create_grievance_attachment_svc_51f", hash = "1917e3ab", zone = 2)
    public void afterCreateGrievanceAttachment(CreateGrievanceAttachmentCrudRequest request) {
    }

    @MethodMetadata(irId = "get_grievance_attachment_by_id_svc_sk8", hash = "b80c7c4c", zone = 1)
    public GrievanceAttachmentCrudResponse getGrievanceAttachmentById(String id) {
        beforeGetGrievanceAttachmentById(id);
        try {
            GrievanceAttachment found = grievanceAttachmentRepository.findById(id).orElseThrow(() -> new NotFoundException("GrievanceAttachment not found with id: " + id));
            GrievanceAttachmentCrudResponse response = new GrievanceAttachmentCrudResponse();
            response.setId(found.getId());
            response.setFileName(found.getFileName());
            response.setFileType(found.getFileType());
            response.setFilePath(found.getFilePath());
            response.setFileSize(found.getFileSize());
            response.setUploadedAt(found.getUploadedAt());
            if (found.getGrievance() != null) {
                response.setGrievanceId(found.getGrievance().getId());
            }
            return response;
        } catch (Exception e) {
            throw e;
        } finally {
            afterGetGrievanceAttachmentById(id);
        }
    }

    @MethodMetadata(irId = "hook-before-get_grievance_attachment_by_id_svc_sk8", hash = "efa09fd5", zone = 2)
    public void beforeGetGrievanceAttachmentById(String id) {
    }

    @MethodMetadata(irId = "hook-after-get_grievance_attachment_by_id_svc_sk8", hash = "469e9d83", zone = 2)
    public void afterGetGrievanceAttachmentById(String id) {
    }

    @MethodMetadata(irId = "list_grievance_attachments_svc_is4", hash = "e55352b9", zone = 1)
    public List<GrievanceAttachmentCrudResponse> listGrievanceAttachments() {
        beforeListGrievanceAttachments();
        try {
            List<GrievanceAttachmentCrudResponse> responses = new ArrayList<>();
            for (GrievanceAttachment item : grievanceAttachmentRepository.findAll()) {
                GrievanceAttachmentCrudResponse response = new GrievanceAttachmentCrudResponse();
                response.setId(item.getId());
                response.setFileName(item.getFileName());
                response.setFileType(item.getFileType());
                response.setFilePath(item.getFilePath());
                response.setFileSize(item.getFileSize());
                response.setUploadedAt(item.getUploadedAt());
                if (item.getGrievance() != null) {
                    response.setGrievanceId(item.getGrievance().getId());
                }
                responses.add(response);
            }
            return responses;
        } catch (Exception e) {
            throw e;
        } finally {
            afterListGrievanceAttachments();
        }
    }

    @MethodMetadata(irId = "hook-before-list_grievance_attachments_svc_is4", hash = "67b2b052", zone = 2)
    public void beforeListGrievanceAttachments() {
    }

    @MethodMetadata(irId = "hook-after-list_grievance_attachments_svc_is4", hash = "8397d1a0", zone = 2)
    public void afterListGrievanceAttachments() {
    }

    @MethodMetadata(irId = "update_grievance_attachment_svc_980", hash = "92a7606e", zone = 1)
    public GrievanceAttachmentCrudResponse updateGrievanceAttachment(String id, UpdateGrievanceAttachmentCrudRequest request) {
        beforeUpdateGrievanceAttachment(id, request);
        try {
            GrievanceAttachment existing = grievanceAttachmentRepository.findById(id).orElseThrow(() -> new NotFoundException("GrievanceAttachment not found with id: " + id));
            if (request.getGrievanceId() != null) {
                Grievance grievance = grievanceRepository.findById(request.getGrievanceId()).orElseThrow(() -> new NotFoundException("Grievance not found with id: " + request.getGrievanceId()));
                existing.setGrievance(grievance);
            }
            if (request.getFileName() != null) {
                existing.setFileName(request.getFileName());
            }
            if (request.getFileType() != null) {
                existing.setFileType(request.getFileType());
            }
            if (request.getFilePath() != null) {
                existing.setFilePath(request.getFilePath());
            }
            if (request.getFileSize() != null) {
                existing.setFileSize(request.getFileSize());
            }
            GrievanceAttachment saved = grievanceAttachmentRepository.save(existing);
            GrievanceAttachmentCrudResponse response = new GrievanceAttachmentCrudResponse();
            response.setId(saved.getId());
            response.setFileName(saved.getFileName());
            response.setFileType(saved.getFileType());
            response.setFilePath(saved.getFilePath());
            response.setFileSize(saved.getFileSize());
            response.setUploadedAt(saved.getUploadedAt());
            if (saved.getGrievance() != null) {
                response.setGrievanceId(saved.getGrievance().getId());
            }
            return response;
        } catch (Exception e) {
            throw e;
        } finally {
            afterUpdateGrievanceAttachment(id, request);
        }
    }

    @MethodMetadata(irId = "hook-before-update_grievance_attachment_svc_980", hash = "d01be15a", zone = 2)
    public void beforeUpdateGrievanceAttachment(String id, UpdateGrievanceAttachmentCrudRequest request) {
    }

    @MethodMetadata(irId = "hook-after-update_grievance_attachment_svc_980", hash = "7bcf62c6", zone = 2)
    public void afterUpdateGrievanceAttachment(String id, UpdateGrievanceAttachmentCrudRequest request) {
    }

    @MethodMetadata(irId = "delete_grievance_attachment_svc_3ps", hash = "16b3902d", zone = 1)
    public void deleteGrievanceAttachment(String id) {
        beforeDeleteGrievanceAttachment(id);
        try {
            grievanceAttachmentRepository.findById(id).orElseThrow(() -> new NotFoundException("GrievanceAttachment not found with id: " + id));
            grievanceAttachmentRepository.deleteById(id);
        } catch (Exception e) {
            throw e;
        } finally {
            afterDeleteGrievanceAttachment(id);
        }
    }

    @MethodMetadata(irId = "hook-before-delete_grievance_attachment_svc_3ps", hash = "d5d388e2", zone = 2)
    public void beforeDeleteGrievanceAttachment(String id) {
    }

    @MethodMetadata(irId = "hook-after-delete_grievance_attachment_svc_3ps", hash = "812334ca", zone = 2)
    public void afterDeleteGrievanceAttachment(String id) {
    }
}
