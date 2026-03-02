package com.ERP.repository;

import com.ERP.entity.Payroll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PayrollRepository extends JpaRepository<Payroll, Long> {
    Optional<Payroll> findByEmployeeIdAndPeriodMonthAndPeriodYear(Long employeeId, Integer month, Integer year);

    @Query("SELECT p FROM Payroll p WHERE " +
           "(:year IS NULL OR p.periodYear = :year) AND " +
           "(:month IS NULL OR p.periodMonth = :month) AND " +
           "(:status IS NULL OR p.status = :status)")
    Page<Payroll> filter(Integer year, Integer month, String status, Pageable pageable);
}
