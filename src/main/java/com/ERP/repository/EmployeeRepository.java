package com.ERP.repository;

import com.ERP.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmployeeId(String employeeId);
    Optional<Employee> findByEmail(String email);
    boolean existsByEmployeeId(String employeeId);
    boolean existsByEmail(String email);
    long countByStatus(String status);

    @Query("SELECT e FROM Employee e WHERE " +
           "LOWER(e.fullName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(e.employeeId) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(e.position) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Employee> search(String search, Pageable pageable);

    @Query("SELECT e FROM Employee e WHERE (:status IS NULL OR e.status = :status) AND " +
           "(:deptId IS NULL OR e.department.id = :deptId)")
    Page<Employee> filter(String status, Long deptId, Pageable pageable);
}
