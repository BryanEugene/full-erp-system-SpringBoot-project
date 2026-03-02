package com.ERP.repository;
import com.ERP.entity.SalesQuotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SalesQuotationRepository extends JpaRepository<SalesQuotation, Long> {}
