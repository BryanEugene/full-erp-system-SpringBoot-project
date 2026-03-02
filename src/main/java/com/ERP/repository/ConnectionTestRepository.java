package com.ERP.repository;

import com.ERP.entity.ConnectionTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionTestRepository extends JpaRepository<ConnectionTest, Long> {
}