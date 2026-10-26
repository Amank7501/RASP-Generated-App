package com.example.app.repository;

import com.example.app.annotation.MethodMetadata;
import com.example.app.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface UserRepository extends JpaRepository<User, String> {

    // repo_method_id: u_exists_by_email_check_chi | Check if email already exists in the system
    @MethodMetadata(irId = "u_exists_by_email_check_chi", hash = "5ab59ec5", zone = 1)
    public Boolean existsByEmail(String email);
}
