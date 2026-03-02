package com.ERP.repository;

import com.ERP.entity.StockLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockLevelRepository extends JpaRepository<StockLevel, Long> {
    Optional<StockLevel> findByProductIdAndWarehouseId(Long productId, Long warehouseId);

    @Query("SELECT s FROM StockLevel s WHERE s.product.id = :productId")
    List<StockLevel> findByProductId(Long productId);

    @Query("SELECT s FROM StockLevel s WHERE s.warehouse.id = :warehouseId")
    List<StockLevel> findByWarehouseId(Long warehouseId);
}
