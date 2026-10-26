package com.example.app.service;

import com.example.app.annotation.MethodMetadata;
import com.example.app.dto.AdminCrudResponse;
import com.example.app.dto.CreateAdminCrudRequest;
import com.example.app.dto.UpdateAdminCrudRequest;
import com.example.app.entity.Admin;
import com.example.app.exception.AlreadyExistsException;
import com.example.app.exception.NotFoundException;
import com.example.app.repository.AdminRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service()
public class AdminService {

    @Autowired()
    private AdminRepository adminRepository;

    @MethodMetadata(irId = "create_admin_svc_or7", hash = "3e22e30e", zone = 1)
    public AdminCrudResponse createAdmin(CreateAdminCrudRequest request) {
        beforeCreateAdmin(request);
        try {
            if (adminRepository.existsByEmail(request.getEmail())) {
                throw new AlreadyExistsException("Admin with email already exists");
            }
            Admin admin = new Admin();
            admin.setEmail(request.getEmail());
            Admin saved = adminRepository.save(admin);
            AdminCrudResponse response = new AdminCrudResponse();
            response.setId(saved.getId());
            response.setEmail(saved.getEmail());
            response.setCreatedAt(saved.getCreatedAt());
            return response;
        } catch (Exception e) {
            throw e;
        } finally {
            afterCreateAdmin(request);
        }
    }

    @MethodMetadata(irId = "hook-before-create_admin_svc_or7", hash = "62948dac", zone = 2)
    public void beforeCreateAdmin(CreateAdminCrudRequest request) {
    }

    @MethodMetadata(irId = "hook-after-create_admin_svc_or7", hash = "afc40a43", zone = 2)
    public void afterCreateAdmin(CreateAdminCrudRequest request) {
    }

    @MethodMetadata(irId = "get_admin_by_id_svc_rpv", hash = "0d04e968", zone = 1)
    public AdminCrudResponse getAdminById(String id) {
        beforeGetAdminById(id);
        try {
            Admin found = adminRepository.findById(id).orElseThrow(() -> new NotFoundException("Admin not found with id: " + id));
            AdminCrudResponse response = new AdminCrudResponse();
            response.setId(found.getId());
            response.setEmail(found.getEmail());
            response.setCreatedAt(found.getCreatedAt());
            return response;
        } catch (Exception e) {
            throw e;
        } finally {
            afterGetAdminById(id);
        }
    }

    @MethodMetadata(irId = "hook-before-get_admin_by_id_svc_rpv", hash = "60c771c4", zone = 2)
    public void beforeGetAdminById(String id) {
    }

    @MethodMetadata(irId = "hook-after-get_admin_by_id_svc_rpv", hash = "3fa7bba4", zone = 2)
    public void afterGetAdminById(String id) {
    }

    @MethodMetadata(irId = "list_admins_svc_41m", hash = "81a05b18", zone = 1)
    public List<AdminCrudResponse> listAdmins() {
        beforeListAdmins();
        try {
            List<AdminCrudResponse> responses = new ArrayList<>();
            for (Admin item : adminRepository.findAll()) {
                AdminCrudResponse response = new AdminCrudResponse();
                response.setId(item.getId());
                response.setEmail(item.getEmail());
                response.setCreatedAt(item.getCreatedAt());
                responses.add(response);
            }
            return responses;
        } catch (Exception e) {
            throw e;
        } finally {
            afterListAdmins();
        }
    }

    @MethodMetadata(irId = "hook-before-list_admins_svc_41m", hash = "06ffee6e", zone = 2)
    public void beforeListAdmins() {
    }

    @MethodMetadata(irId = "hook-after-list_admins_svc_41m", hash = "fb1034cd", zone = 2)
    public void afterListAdmins() {
    }

    @MethodMetadata(irId = "update_admin_svc_mr4", hash = "ee84d551", zone = 1)
    public AdminCrudResponse updateAdmin(String id, UpdateAdminCrudRequest request) {
        beforeUpdateAdmin(id, request);
        try {
            Admin existing = adminRepository.findById(id).orElseThrow(() -> new NotFoundException("Admin not found with id: " + id));
            if (request.getEmail() != null && !request.getEmail().equals(existing.getEmail()) && adminRepository.existsByEmail(request.getEmail())) {
                throw new AlreadyExistsException("Admin with email already exists");
            }
            if (request.getEmail() != null) {
                existing.setEmail(request.getEmail());
            }
            Admin saved = adminRepository.save(existing);
            AdminCrudResponse response = new AdminCrudResponse();
            response.setId(saved.getId());
            response.setEmail(saved.getEmail());
            response.setCreatedAt(saved.getCreatedAt());
            return response;
        } catch (Exception e) {
            throw e;
        } finally {
            afterUpdateAdmin(id, request);
        }
    }

    @MethodMetadata(irId = "hook-before-update_admin_svc_mr4", hash = "d8497c41", zone = 2)
    public void beforeUpdateAdmin(String id, UpdateAdminCrudRequest request) {
    }

    @MethodMetadata(irId = "hook-after-update_admin_svc_mr4", hash = "7ca1aeb2", zone = 2)
    public void afterUpdateAdmin(String id, UpdateAdminCrudRequest request) {
    }

    @MethodMetadata(irId = "delete_admin_svc_82v", hash = "c5c594af", zone = 1)
    public void deleteAdmin(String id) {
        beforeDeleteAdmin(id);
        try {
            adminRepository.findById(id).orElseThrow(() -> new NotFoundException("Admin not found with id: " + id));
            adminRepository.deleteById(id);
        } catch (Exception e) {
            throw e;
        } finally {
            afterDeleteAdmin(id);
        }
    }

    @MethodMetadata(irId = "hook-before-delete_admin_svc_82v", hash = "412fc9ad", zone = 2)
    public void beforeDeleteAdmin(String id) {
    }

    @MethodMetadata(irId = "hook-after-delete_admin_svc_82v", hash = "22e0f98e", zone = 2)
    public void afterDeleteAdmin(String id) {
    }
}
