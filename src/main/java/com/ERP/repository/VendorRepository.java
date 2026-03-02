package com.ERP.repository;

import com.ERP.entity.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    boolean existsByCode(String code);

    @Query("SELECT v FROM Vendor v WHERE " +
           "LOWER(v.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(v.code) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(v.email) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Vendor> search(String search, Pageable pageable);
}
