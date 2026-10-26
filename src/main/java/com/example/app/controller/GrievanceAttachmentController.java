package com.example.app.controller;

import com.example.app.annotation.MethodMetadata;
import com.example.app.dto.CreateGrievanceAttachmentCrudRequest;
import com.example.app.dto.GrievanceAttachmentCrudResponse;
import com.example.app.dto.UpdateGrievanceAttachmentCrudRequest;
import com.example.app.entity.GrievanceAttachment;
import com.example.app.service.GrievanceAttachmentService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/grievance-attachments")
public class GrievanceAttachmentController {

    @Autowired()
    private GrievanceAttachmentService grievanceAttachmentService;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @MethodMetadata(irId = "create_grievance_attachment_ctrl_0h9", hash = "abdfc02e", zone = 1)
    public ResponseEntity<GrievanceAttachmentCrudResponse> createGrievanceAttachment(@Valid @RequestBody CreateGrievanceAttachmentCrudRequest request) {
        beforeCreateGrievanceAttachment(request);
        try {
            return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(grievanceAttachmentService.createGrievanceAttachment(request));
        } catch (Exception e) {
            throw e;
        } finally {
            afterCreateGrievanceAttachment(request);
        }
    }

    @MethodMetadata(irId = "hook-before-create_grievance_attachment_ctrl_0h9", hash = "894da01e", zone = 2)
    public void beforeCreateGrievanceAttachment(@Valid @RequestBody CreateGrievanceAttachmentCrudRequest request) {
    }

    @MethodMetadata(irId = "hook-after-create_grievance_attachment_ctrl_0h9", hash = "07d2d580", zone = 2)
    public void afterCreateGrievanceAttachment(@Valid @RequestBody CreateGrievanceAttachmentCrudRequest request) {
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @MethodMetadata(irId = "get_grievance_attachment_by_id_ctrl_dl4", hash = "0956536e", zone = 1)
    public ResponseEntity<GrievanceAttachmentCrudResponse> getGrievanceAttachmentById(@PathVariable String id) {
        beforeGetGrievanceAttachmentById(id);
        try {
            return ResponseEntity.ok(grievanceAttachmentService.getGrievanceAttachmentById(id));
        } catch (Exception e) {
            throw e;
        } finally {
            afterGetGrievanceAttachmentById(id);
        }
    }

    @MethodMetadata(irId = "hook-before-get_grievance_attachment_by_id_ctrl_dl4", hash = "de778699", zone = 2)
    public void beforeGetGrievanceAttachmentById(@PathVariable String id) {
    }

    @MethodMetadata(irId = "hook-after-get_grievance_attachment_by_id_ctrl_dl4", hash = "38957bff", zone = 2)
    public void afterGetGrievanceAttachmentById(@PathVariable String id) {
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @MethodMetadata(irId = "list_grievance_attachments_ctrl_zhe", hash = "20dc482e", zone = 1)
    public ResponseEntity<List<GrievanceAttachmentCrudResponse>> listGrievanceAttachments() {
        beforeListGrievanceAttachments();
        try {
            return ResponseEntity.ok(grievanceAttachmentService.listGrievanceAttachments());
        } catch (Exception e) {
            throw e;
        } finally {
            afterListGrievanceAttachments();
        }
    }

    @MethodMetadata(irId = "hook-before-list_grievance_attachments_ctrl_zhe", hash = "67b2b052", zone = 2)
    public void beforeListGrievanceAttachments() {
    }

    @MethodMetadata(irId = "hook-after-list_grievance_attachments_ctrl_zhe", hash = "8397d1a0", zone = 2)
    public void afterListGrievanceAttachments() {
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @MethodMetadata(irId = "update_grievance_attachment_ctrl_z1l", hash = "7fccfd2d", zone = 1)
    public ResponseEntity<GrievanceAttachmentCrudResponse> updateGrievanceAttachment(@PathVariable String id, @Valid @RequestBody UpdateGrievanceAttachmentCrudRequest request) {
        beforeUpdateGrievanceAttachment(id, request);
        try {
            return ResponseEntity.ok(grievanceAttachmentService.updateGrievanceAttachment(id, request));
        } catch (Exception e) {
            throw e;
        } finally {
            afterUpdateGrievanceAttachment(id, request);
        }
    }

    @MethodMetadata(irId = "hook-before-update_grievance_attachment_ctrl_z1l", hash = "db7e8697", zone = 2)
    public void beforeUpdateGrievanceAttachment(@PathVariable String id, @Valid @RequestBody UpdateGrievanceAttachmentCrudRequest request) {
    }

    @MethodMetadata(irId = "hook-after-update_grievance_attachment_ctrl_z1l", hash = "c2125133", zone = 2)
    public void afterUpdateGrievanceAttachment(@PathVariable String id, @Valid @RequestBody UpdateGrievanceAttachmentCrudRequest request) {
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @MethodMetadata(irId = "delete_grievance_attachment_ctrl_muc", hash = "791b2d37", zone = 1)
    public ResponseEntity<Void> deleteGrievanceAttachment(@PathVariable String id) {
        beforeDeleteGrievanceAttachment(id);
        try {
            grievanceAttachmentService.deleteGrievanceAttachment(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw e;
        } finally {
            afterDeleteGrievanceAttachment(id);
        }
    }

    @MethodMetadata(irId = "hook-before-delete_grievance_attachment_ctrl_muc", hash = "7b6328d6", zone = 2)
    public void beforeDeleteGrievanceAttachment(@PathVariable String id) {
    }

    @MethodMetadata(irId = "hook-after-delete_grievance_attachment_ctrl_muc", hash = "3e18bae7", zone = 2)
    public void afterDeleteGrievanceAttachment(@PathVariable String id) {
    }
}
