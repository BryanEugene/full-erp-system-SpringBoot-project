package com.ERP.repository;

import com.ERP.entity.ChartOfAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChartOfAccountRepository extends JpaRepository<ChartOfAccount, Long> {
    Optional<ChartOfAccount> findByAccountCode(String accountCode);
    boolean existsByAccountCode(String accountCode);
    List<ChartOfAccount> findByIsActiveTrueOrderByAccountCode();
    List<ChartOfAccount> findByAccountTypeAndIsActiveTrue(String accountType);
}
