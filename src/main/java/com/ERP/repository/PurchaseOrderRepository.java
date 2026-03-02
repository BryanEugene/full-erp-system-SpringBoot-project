package com.ERP.repository;

import com.ERP.entity.PurchaseOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    boolean existsByPoNumber(String poNumber);

    @Query("SELECT p FROM PurchaseOrder p WHERE " +
           "(:status IS NULL OR p.status = :status) AND " +
           "(:vendorId IS NULL OR p.vendor.id = :vendorId)")
    Page<PurchaseOrder> filter(String status, Long vendorId, Pageable pageable);
}
