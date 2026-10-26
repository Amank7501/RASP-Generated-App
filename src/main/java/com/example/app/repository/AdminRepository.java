package com.example.app.repository;

import com.example.app.annotation.MethodMetadata;
import com.example.app.entity.Admin;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface AdminRepository extends JpaRepository<Admin, String> {

    @MethodMetadata(irId = "a_exists_by_email_0rr", hash = "3880e5d4", zone = 1)
    public boolean existsByEmail(String email);
}
