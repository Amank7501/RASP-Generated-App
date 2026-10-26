package com.example.app.controller;

import com.example.app.annotation.MethodMetadata;
import com.example.app.dto.*;
import com.example.app.entity.*;
import com.example.app.service.UserService;
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
@RequestMapping("/api/users")
public class UserController {

    @Autowired()
    private UserService userService;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @MethodMetadata(irId = "create_user_ctrl_ja4", hash = "47094ca2", zone = 1)
    public ResponseEntity<UserCrudResponse> createUser(@Valid @RequestBody CreateUserCrudRequest request) {
        beforeCreateUser(request);
        try {
            return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(userService.createUser(request));
        } catch (Exception e) {
            throw e;
        } finally {
            afterCreateUser(request);
        }
    }

    @MethodMetadata(irId = "hook-before-create_user_ctrl_ja4", hash = "bee234fc", zone = 2)
    public void beforeCreateUser(@Valid @RequestBody CreateUserCrudRequest request) {
    }

    @MethodMetadata(irId = "hook-after-create_user_ctrl_ja4", hash = "f999a4d2", zone = 2)
    public void afterCreateUser(@Valid @RequestBody CreateUserCrudRequest request) {
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @MethodMetadata(irId = "get_user_by_id_ctrl_6pf", hash = "af8d52d1", zone = 1)
    public ResponseEntity<UserCrudResponse> getUserById(@PathVariable String id) {
        beforeGetUserById(id);
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (Exception e) {
            throw e;
        } finally {
            afterGetUserById(id);
        }
    }

    @MethodMetadata(irId = "hook-before-get_user_by_id_ctrl_6pf", hash = "e5ebbb62", zone = 2)
    public void beforeGetUserById(@PathVariable String id) {
    }

    @MethodMetadata(irId = "hook-after-get_user_by_id_ctrl_6pf", hash = "a50908aa", zone = 2)
    public void afterGetUserById(@PathVariable String id) {
    }

    @GetMapping("")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @MethodMetadata(irId = "list_users_ctrl_5bh", hash = "1f454934", zone = 1)
    public ResponseEntity<List<UserCrudResponse>> listUsers() {
        beforeListUsers();
        try {
            return ResponseEntity.ok(userService.listUsers());
        } catch (Exception e) {
            throw e;
        } finally {
            afterListUsers();
        }
    }

    @MethodMetadata(irId = "hook-before-list_users_ctrl_5bh", hash = "1cc83613", zone = 2)
    public void beforeListUsers() {
    }

    @MethodMetadata(irId = "hook-after-list_users_ctrl_5bh", hash = "b734b73a", zone = 2)
    public void afterListUsers() {
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @MethodMetadata(irId = "update_user_ctrl_068", hash = "18e68ece", zone = 1)
    public ResponseEntity<UserCrudResponse> updateUser(@PathVariable String id, @Valid @RequestBody UpdateUserCrudRequest request) {
        beforeUpdateUser(id, request);
        try {
            return ResponseEntity.ok(userService.updateUser(id, request));
        } catch (Exception e) {
            throw e;
        } finally {
            afterUpdateUser(id, request);
        }
    }

    @MethodMetadata(irId = "hook-before-update_user_ctrl_068", hash = "8dd8d0db", zone = 2)
    public void beforeUpdateUser(@PathVariable String id, @Valid @RequestBody UpdateUserCrudRequest request) {
    }

    @MethodMetadata(irId = "hook-after-update_user_ctrl_068", hash = "2a9e094d", zone = 2)
    public void afterUpdateUser(@PathVariable String id, @Valid @RequestBody UpdateUserCrudRequest request) {
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @MethodMetadata(irId = "delete_user_ctrl_ea2", hash = "1e15fa78", zone = 1)
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        beforeDeleteUser(id);
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw e;
        } finally {
            afterDeleteUser(id);
        }
    }

    @MethodMetadata(irId = "hook-before-delete_user_ctrl_ea2", hash = "aebc8d08", zone = 2)
    public void beforeDeleteUser(@PathVariable String id) {
    }

    @MethodMetadata(irId = "hook-after-delete_user_ctrl_ea2", hash = "6b1b0b4c", zone = 2)
    public void afterDeleteUser(@PathVariable String id) {
    }

    /*
 * Operation    : Update User Profile with Validation
 * Usecase ID   : update_user_profile
 * Usecase Name : Update User Profile
 * Comment      : Endpoint to update user profile with validation
 */
    @PutMapping(value = "/{userId}")
    @PreAuthorize("hasRole('USER')")
    @MethodMetadata(irId = "ctrl_update_user_profile_validation_wes", hash = "35f3397a", zone = 1)
    public ResponseEntity<UpdateUserProfileResponse> updateUserProfile(@PathVariable String userId, @RequestBody @Valid UpdateUserProfileRequest request, Principal principal) {
        return ResponseEntity.ok(userService.updateUserProfile(userId, request, principal));
    }
}
