package com.ERP.repository;
import com.ERP.entity.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {}
