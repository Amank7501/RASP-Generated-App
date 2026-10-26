package com.example.app.controller;

import com.example.app.annotation.MethodMetadata;
import com.example.app.dto.AdminCrudResponse;
import com.example.app.dto.CreateAdminCrudRequest;
import com.example.app.dto.UpdateAdminCrudRequest;
import com.example.app.entity.Admin;
import com.example.app.service.AdminService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired()
    private AdminService adminService;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @MethodMetadata(irId = "create_admin_ctrl_i3a", hash = "2d0c42cb", zone = 1)
    public ResponseEntity<AdminCrudResponse> createAdmin(@Valid @RequestBody CreateAdminCrudRequest request) {
        beforeCreateAdmin(request);
        try {
            return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(adminService.createAdmin(request));
        } catch (Exception e) {
            throw e;
        } finally {
            afterCreateAdmin(request);
        }
    }

    @MethodMetadata(irId = "hook-before-create_admin_ctrl_i3a", hash = "ae877b42", zone = 2)
    public void beforeCreateAdmin(@Valid @RequestBody CreateAdminCrudRequest request) {
    }

    @MethodMetadata(irId = "hook-after-create_admin_ctrl_i3a", hash = "4e5c2992", zone = 2)
    public void afterCreateAdmin(@Valid @RequestBody CreateAdminCrudRequest request) {
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @MethodMetadata(irId = "get_admin_by_id_ctrl_fs7", hash = "205344e6", zone = 1)
    public ResponseEntity<AdminCrudResponse> getAdminById(@PathVariable String id) {
        beforeGetAdminById(id);
        try {
            return ResponseEntity.ok(adminService.getAdminById(id));
        } catch (Exception e) {
            throw e;
        } finally {
            afterGetAdminById(id);
        }
    }

    @MethodMetadata(irId = "hook-before-get_admin_by_id_ctrl_fs7", hash = "3cbf1423", zone = 2)
    public void beforeGetAdminById(@PathVariable String id) {
    }

    @MethodMetadata(irId = "hook-after-get_admin_by_id_ctrl_fs7", hash = "8c25e6c1", zone = 2)
    public void afterGetAdminById(@PathVariable String id) {
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @MethodMetadata(irId = "list_admins_ctrl_vc2", hash = "98ea9f89", zone = 1)
    public ResponseEntity<List<AdminCrudResponse>> listAdmins() {
        beforeListAdmins();
        try {
            return ResponseEntity.ok(adminService.listAdmins());
        } catch (Exception e) {
            throw e;
        } finally {
            afterListAdmins();
        }
    }

    @MethodMetadata(irId = "hook-before-list_admins_ctrl_vc2", hash = "06ffee6e", zone = 2)
    public void beforeListAdmins() {
    }

    @MethodMetadata(irId = "hook-after-list_admins_ctrl_vc2", hash = "fb1034cd", zone = 2)
    public void afterListAdmins() {
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @MethodMetadata(irId = "update_admin_ctrl_hhs", hash = "53e2a3f6", zone = 1)
    public ResponseEntity<AdminCrudResponse> updateAdmin(@PathVariable String id, @Valid @RequestBody UpdateAdminCrudRequest request) {
        beforeUpdateAdmin(id, request);
        try {
            return ResponseEntity.ok(adminService.updateAdmin(id, request));
        } catch (Exception e) {
            throw e;
        } finally {
            afterUpdateAdmin(id, request);
        }
    }

    @MethodMetadata(irId = "hook-before-update_admin_ctrl_hhs", hash = "da40b527", zone = 2)
    public void beforeUpdateAdmin(@PathVariable String id, @Valid @RequestBody UpdateAdminCrudRequest request) {
    }

    @MethodMetadata(irId = "hook-after-update_admin_ctrl_hhs", hash = "7d5af502", zone = 2)
    public void afterUpdateAdmin(@PathVariable String id, @Valid @RequestBody UpdateAdminCrudRequest request) {
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @MethodMetadata(irId = "delete_admin_ctrl_p9z", hash = "b4cc6ede", zone = 1)
    public ResponseEntity<Void> deleteAdmin(@PathVariable String id) {
        beforeDeleteAdmin(id);
        try {
            adminService.deleteAdmin(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw e;
        } finally {
            afterDeleteAdmin(id);
        }
    }

    @MethodMetadata(irId = "hook-before-delete_admin_ctrl_p9z", hash = "87e69644", zone = 2)
    public void beforeDeleteAdmin(@PathVariable String id) {
    }

    @MethodMetadata(irId = "hook-after-delete_admin_ctrl_p9z", hash = "41f3e7cc", zone = 2)
    public void afterDeleteAdmin(@PathVariable String id) {
    }
}
