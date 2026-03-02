package com.ERP.repository;
import com.ERP.entity.BillOfMaterials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BillOfMaterialsRepository extends JpaRepository<BillOfMaterials, Long> {}
