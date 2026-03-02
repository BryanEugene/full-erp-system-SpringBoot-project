package com.ERP.service;

import com.ERP.entity.ChartOfAccount;
import com.ERP.exception.BadRequestException;
import com.ERP.exception.ResourceNotFoundException;
import com.ERP.repository.ChartOfAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChartOfAccountService {

    private final ChartOfAccountRepository coaRepository;

    public List<ChartOfAccount> getAll() {
        return coaRepository.findByIsActiveTrueOrderByAccountCode();
    }

    public ChartOfAccount getById(Long id) {
        return coaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + id));
    }

    @Transactional
    public ChartOfAccount create(ChartOfAccount coa) {
        if (coaRepository.existsByAccountCode(coa.getAccountCode())) {
            throw new BadRequestException("Account code already exists: " + coa.getAccountCode());
        }
        return coaRepository.save(coa);
    }

    @Transactional
    public ChartOfAccount update(Long id, ChartOfAccount update) {
        ChartOfAccount existing = getById(id);
        existing.setAccountName(update.getAccountName());
        existing.setDescription(update.getDescription());
        existing.setIsActive(update.getIsActive());
        return coaRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        if (!coaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Account not found: " + id);
        }
        coaRepository.deleteById(id);
    }
}
