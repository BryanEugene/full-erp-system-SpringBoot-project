package com.ERP.repository;
import com.ERP.entity.QualityControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface QualityControlRepository extends JpaRepository<QualityControl, Long> {}
