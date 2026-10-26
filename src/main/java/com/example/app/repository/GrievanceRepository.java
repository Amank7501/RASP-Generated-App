package com.example.app.repository;

import com.example.app.annotation.MethodMetadata;
import com.example.app.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface GrievanceRepository extends JpaRepository<Grievance, String> {

    // repo_method_id: g_find_by_user_id_list_qwf | Retrieve all grievances for a specific user
    @MethodMetadata(irId = "g_find_by_user_id_list_qwf", hash = "43fc2e61", zone = 1)
    public List<Grievance> findByUserId(String userId);

    // repo_method_id: grievance_count_by_status_74a | Count grievances by status
    @MethodMetadata(irId = "grievance_count_by_status_74a", hash = "d3bd5558", zone = 1)
    public Long countByStatus(String status);
}
