package com.ERP.repository;
import com.ERP.entity.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {}
