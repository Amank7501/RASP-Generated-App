package com.example.app.service;

import com.example.app.annotation.MethodMetadata;
import com.example.app.dto.*;
import com.example.app.entity.*;
import com.example.app.exception.AlreadyExistsException;
import com.example.app.exception.NotFoundException;
import com.example.app.repository.UserRepository;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service()
public class UserService {

    @Autowired()
    private UserRepository userRepository;

    @MethodMetadata(irId = "create_user_svc_rh4", hash = "5c74fa9c", zone = 1)
    public UserCrudResponse createUser(CreateUserCrudRequest request) {
        beforeCreateUser(request);
        try {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new AlreadyExistsException("User with email already exists");
            }
            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setMobileNumber(request.getMobileNumber());
            User saved = userRepository.save(user);
            UserCrudResponse response = new UserCrudResponse();
            response.setId(saved.getId());
            response.setName(saved.getName());
            response.setEmail(saved.getEmail());
            response.setMobileNumber(saved.getMobileNumber());
            response.setCreatedAt(saved.getCreatedAt());
            return response;
        } catch (Exception e) {
            throw e;
        } finally {
            afterCreateUser(request);
        }
    }

    @MethodMetadata(irId = "hook-before-create_user_svc_rh4", hash = "66fe3e01", zone = 2)
    public void beforeCreateUser(CreateUserCrudRequest request) {
    }

    @MethodMetadata(irId = "hook-after-create_user_svc_rh4", hash = "154ef650", zone = 2)
    public void afterCreateUser(CreateUserCrudRequest request) {
    }

    @MethodMetadata(irId = "get_user_by_id_svc_426", hash = "bbf84213", zone = 1)
    public UserCrudResponse getUserById(String id) {
        beforeGetUserById(id);
        try {
            User found = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id));
            UserCrudResponse response = new UserCrudResponse();
            response.setId(found.getId());
            response.setName(found.getName());
            response.setEmail(found.getEmail());
            response.setMobileNumber(found.getMobileNumber());
            response.setCreatedAt(found.getCreatedAt());
            return response;
        } catch (Exception e) {
            throw e;
        } finally {
            afterGetUserById(id);
        }
    }

    @MethodMetadata(irId = "hook-before-get_user_by_id_svc_426", hash = "5f3ff602", zone = 2)
    public void beforeGetUserById(String id) {
    }

    @MethodMetadata(irId = "hook-after-get_user_by_id_svc_426", hash = "20272a4d", zone = 2)
    public void afterGetUserById(String id) {
    }

    @MethodMetadata(irId = "list_users_svc_s6j", hash = "51b49a1d", zone = 1)
    public List<UserCrudResponse> listUsers() {
        beforeListUsers();
        try {
            List<UserCrudResponse> responses = new ArrayList<>();
            for (User item : userRepository.findAll()) {
                UserCrudResponse response = new UserCrudResponse();
                response.setId(item.getId());
                response.setName(item.getName());
                response.setEmail(item.getEmail());
                response.setMobileNumber(item.getMobileNumber());
                response.setCreatedAt(item.getCreatedAt());
                responses.add(response);
            }
            return responses;
        } catch (Exception e) {
            throw e;
        } finally {
            afterListUsers();
        }
    }

    @MethodMetadata(irId = "hook-before-list_users_svc_s6j", hash = "1cc83613", zone = 2)
    public void beforeListUsers() {
    }

    @MethodMetadata(irId = "hook-after-list_users_svc_s6j", hash = "b734b73a", zone = 2)
    public void afterListUsers() {
    }

    @MethodMetadata(irId = "update_user_svc_e00", hash = "3fbd068a", zone = 1)
    public UserCrudResponse updateUser(String id, UpdateUserCrudRequest request) {
        beforeUpdateUser(id, request);
        try {
            User existing = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id));
            if (request.getEmail() != null && !request.getEmail().equals(existing.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
                throw new AlreadyExistsException("User with email already exists");
            }
            if (request.getName() != null) {
                existing.setName(request.getName());
            }
            if (request.getEmail() != null) {
                existing.setEmail(request.getEmail());
            }
            if (request.getMobileNumber() != null) {
                existing.setMobileNumber(request.getMobileNumber());
            }
            User saved = userRepository.save(existing);
            UserCrudResponse response = new UserCrudResponse();
            response.setId(saved.getId());
            response.setName(saved.getName());
            response.setEmail(saved.getEmail());
            response.setMobileNumber(saved.getMobileNumber());
            response.setCreatedAt(saved.getCreatedAt());
            return response;
        } catch (Exception e) {
            throw e;
        } finally {
            afterUpdateUser(id, request);
        }
    }

    @MethodMetadata(irId = "hook-before-update_user_svc_e00", hash = "c0a24101", zone = 2)
    public void beforeUpdateUser(String id, UpdateUserCrudRequest request) {
    }

    @MethodMetadata(irId = "hook-after-update_user_svc_e00", hash = "0a78104c", zone = 2)
    public void afterUpdateUser(String id, UpdateUserCrudRequest request) {
    }

    @MethodMetadata(irId = "delete_user_svc_m56", hash = "57387f77", zone = 1)
    public void deleteUser(String id) {
        beforeDeleteUser(id);
        try {
            userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id));
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw e;
        } finally {
            afterDeleteUser(id);
        }
    }

    @MethodMetadata(irId = "hook-before-delete_user_svc_m56", hash = "5ef88533", zone = 2)
    public void beforeDeleteUser(String id) {
    }

    @MethodMetadata(irId = "hook-after-delete_user_svc_m56", hash = "7f8f45f2", zone = 2)
    public void afterDeleteUser(String id) {
    }

    /*
 * Operation    : Update User Profile with Validation
 * Usecase ID   : update_user_profile
 * Usecase Name : Update User Profile
 * Comment      : Updates user profile with validation of email format, mobile number format, and email uniqueness
 */
    @MethodMetadata(irId = "svc_update_user_profile_validation_790", hash = "f3e16bd0", zone = 1)
    public UpdateUserProfileResponse updateUserProfile(String userId, UpdateUserProfileRequest request, Principal principal) {
        // Validate email format using regex pattern
        String emailPattern = "^[^@]+@[^@]+\\.[^@]+$";
        if (!request.getEmail().matches(emailPattern)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        // Validate mobile number format (10 digits)
        String mobilePattern = "^[0-9]{10}$";
        if (!request.getMobileNumber().matches(mobilePattern)) {
            throw new IllegalArgumentException("Mobile number must be exactly 10 digits");
        }
        // Retrieve current user
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        // Check if new email is already registered by another user
        if (!user.getEmail().equals(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email is already registered by another user");
        }
        // Update user record with validated information
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getMobileNumber());
        // Persist changes
        User updatedUser = userRepository.save(user);
        // Map to response DTO
        UpdateUserProfileResponse response = new UpdateUserProfileResponse();
        response.setId(updatedUser.getId());
        response.setName(updatedUser.getName());
        response.setEmail(updatedUser.getEmail());
        response.setMobileNumber(updatedUser.getMobileNumber());
        response.setCreatedAt(updatedUser.getCreatedAt());
        return response;
    }
}
