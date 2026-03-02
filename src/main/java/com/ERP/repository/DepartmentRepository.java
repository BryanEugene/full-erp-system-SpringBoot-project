package com.ERP.repository;

import com.ERP.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findByIsActiveTrue();
    boolean existsByCode(String code);

    @Query("SELECT d FROM Department d WHERE d.isActive = true AND " +
           "(LOWER(d.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(d.code) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Department> searchActive(String search, Pageable pageable);
}
