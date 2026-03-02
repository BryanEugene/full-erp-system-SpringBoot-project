package com.ERP.repository;

import com.ERP.entity.SalesOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
    boolean existsByOrderNumber(String orderNumber);

    @Query("SELECT s FROM SalesOrder s WHERE " +
           "(:status IS NULL OR s.status = :status) AND " +
           "(:customerId IS NULL OR s.customer.id = :customerId)")
    Page<SalesOrder> filter(String status, Long customerId, Pageable pageable);
}
