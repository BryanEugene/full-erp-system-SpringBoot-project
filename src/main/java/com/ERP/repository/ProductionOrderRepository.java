package com.ERP.repository;
import com.ERP.entity.ProductionOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ProductionOrderRepository extends JpaRepository<ProductionOrder, Long> {}
