package com.ERP.repository;

import com.ERP.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByProjectCode(String projectCode);

    @Query("SELECT p FROM Project p WHERE " +
           "(:status IS NULL OR p.status = :status) AND " +
           "(LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Project> search(String search, String status, Pageable pageable);
}
