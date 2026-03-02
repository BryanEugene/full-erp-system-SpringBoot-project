package com.ERP.repository;

import com.ERP.entity.LeaveRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    @Query("SELECT l FROM LeaveRequest l WHERE " +
           "(:status IS NULL OR l.status = :status) AND " +
           "(:employeeId IS NULL OR l.employee.id = :employeeId)")
    Page<LeaveRequest> filter(String status, Long employeeId, Pageable pageable);
}
