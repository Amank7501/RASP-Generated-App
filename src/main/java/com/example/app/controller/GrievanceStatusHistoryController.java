package com.example.app.controller;

import com.example.app.annotation.MethodMetadata;
import com.example.app.dto.CreateGrievanceStatusHistoryCrudRequest;
import com.example.app.dto.GrievanceStatusHistoryCrudResponse;
import com.example.app.dto.UpdateGrievanceStatusHistoryCrudRequest;
import com.example.app.entity.GrievanceStatusHistory;
import com.example.app.service.GrievanceStatusHistoryService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/grievance-status-histories")
public class GrievanceStatusHistoryController {

    @Autowired()
    private GrievanceStatusHistoryService grievanceStatusHistoryService;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @MethodMetadata(irId = "create_grievance_status_history_ctrl_n7s", hash = "6192a62c", zone = 1)
    public ResponseEntity<GrievanceStatusHistoryCrudResponse> createGrievanceStatusHistory(@Valid @RequestBody CreateGrievanceStatusHistoryCrudRequest request) {
        beforeCreateGrievanceStatusHistory(request);
        try {
            return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(grievanceStatusHistoryService.createGrievanceStatusHistory(request));
        } catch (Exception e) {
            throw e;
        } finally {
            afterCreateGrievanceStatusHistory(request);
        }
    }

    @MethodMetadata(irId = "hook-before-create_grievance_status_history_ctrl_n7s", hash = "0463baea", zone = 2)
    public void beforeCreateGrievanceStatusHistory(@Valid @RequestBody CreateGrievanceStatusHistoryCrudRequest request) {
    }

    @MethodMetadata(irId = "hook-after-create_grievance_status_history_ctrl_n7s", hash = "673a65d6", zone = 2)
    public void afterCreateGrievanceStatusHistory(@Valid @RequestBody CreateGrievanceStatusHistoryCrudRequest request) {
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @MethodMetadata(irId = "get_grievance_status_history_by_id_ctrl_qvt", hash = "19680595", zone = 1)
    public ResponseEntity<GrievanceStatusHistoryCrudResponse> getGrievanceStatusHistoryById(@PathVariable String id) {
        beforeGetGrievanceStatusHistoryById(id);
        try {
            return ResponseEntity.ok(grievanceStatusHistoryService.getGrievanceStatusHistoryById(id));
        } catch (Exception e) {
            throw e;
        } finally {
            afterGetGrievanceStatusHistoryById(id);
        }
    }

    @MethodMetadata(irId = "hook-before-get_grievance_status_history_by_id_ctrl_qvt", hash = "d9747ef8", zone = 2)
    public void beforeGetGrievanceStatusHistoryById(@PathVariable String id) {
    }

    @MethodMetadata(irId = "hook-after-get_grievance_status_history_by_id_ctrl_qvt", hash = "aaa1a83c", zone = 2)
    public void afterGetGrievanceStatusHistoryById(@PathVariable String id) {
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @MethodMetadata(irId = "list_grievance_status_historys_ctrl_3jt", hash = "9e3432bf", zone = 1)
    public ResponseEntity<List<GrievanceStatusHistoryCrudResponse>> listGrievanceStatusHistorys() {
        beforeListGrievanceStatusHistorys();
        try {
            return ResponseEntity.ok(grievanceStatusHistoryService.listGrievanceStatusHistorys());
        } catch (Exception e) {
            throw e;
        } finally {
            afterListGrievanceStatusHistorys();
        }
    }

    @MethodMetadata(irId = "hook-before-list_grievance_status_historys_ctrl_3jt", hash = "8089db19", zone = 2)
    public void beforeListGrievanceStatusHistorys() {
    }

    @MethodMetadata(irId = "hook-after-list_grievance_status_historys_ctrl_3jt", hash = "d8af28c1", zone = 2)
    public void afterListGrievanceStatusHistorys() {
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @MethodMetadata(irId = "update_grievance_status_history_ctrl_p87", hash = "70bc6312", zone = 1)
    public ResponseEntity<GrievanceStatusHistoryCrudResponse> updateGrievanceStatusHistory(@PathVariable String id, @Valid @RequestBody UpdateGrievanceStatusHistoryCrudRequest request) {
        beforeUpdateGrievanceStatusHistory(id, request);
        try {
            return ResponseEntity.ok(grievanceStatusHistoryService.updateGrievanceStatusHistory(id, request));
        } catch (Exception e) {
            throw e;
        } finally {
            afterUpdateGrievanceStatusHistory(id, request);
        }
    }

    @MethodMetadata(irId = "hook-before-update_grievance_status_history_ctrl_p87", hash = "8b45fde2", zone = 2)
    public void beforeUpdateGrievanceStatusHistory(@PathVariable String id, @Valid @RequestBody UpdateGrievanceStatusHistoryCrudRequest request) {
    }

    @MethodMetadata(irId = "hook-after-update_grievance_status_history_ctrl_p87", hash = "211ff1fa", zone = 2)
    public void afterUpdateGrievanceStatusHistory(@PathVariable String id, @Valid @RequestBody UpdateGrievanceStatusHistoryCrudRequest request) {
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @MethodMetadata(irId = "delete_grievance_status_history_ctrl_a74", hash = "8974ee66", zone = 1)
    public ResponseEntity<Void> deleteGrievanceStatusHistory(@PathVariable String id) {
        beforeDeleteGrievanceStatusHistory(id);
        try {
            grievanceStatusHistoryService.deleteGrievanceStatusHistory(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw e;
        } finally {
            afterDeleteGrievanceStatusHistory(id);
        }
    }

    @MethodMetadata(irId = "hook-before-delete_grievance_status_history_ctrl_a74", hash = "637318e6", zone = 2)
    public void beforeDeleteGrievanceStatusHistory(@PathVariable String id) {
    }

    @MethodMetadata(irId = "hook-after-delete_grievance_status_history_ctrl_a74", hash = "f1ebaf66", zone = 2)
    public void afterDeleteGrievanceStatusHistory(@PathVariable String id) {
    }
}
