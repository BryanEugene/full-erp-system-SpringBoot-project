package com.ERP.service;

import com.ERP.entity.Vendor;
import com.ERP.exception.BadRequestException;
import com.ERP.exception.ResourceNotFoundException;
import com.ERP.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VendorService {

    private final VendorRepository vendorRepository;

    public Page<Vendor> getAll(String search, Pageable pageable) {
        if (search != null && !search.isBlank()) {
            return vendorRepository.search(search, pageable);
        }
        return vendorRepository.findAll(pageable);
    }

    public Vendor getById(Long id) {
        return vendorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found: " + id));
    }

    @Transactional
    public Vendor create(Vendor vendor) {
        if (vendorRepository.existsByCode(vendor.getCode())) {
            throw new BadRequestException("Vendor code already exists: " + vendor.getCode());
        }
        return vendorRepository.save(vendor);
    }

    @Transactional
    public Vendor update(Long id, Vendor update) {
        Vendor existing = getById(id);
        existing.setName(update.getName());
        existing.setEmail(update.getEmail());
        existing.setPhone(update.getPhone());
        existing.setAddress(update.getAddress());
        existing.setCity(update.getCity());
        existing.setPaymentTerms(update.getPaymentTerms());
        existing.setIsActive(update.getIsActive());
        return vendorRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Vendor v = getById(id);
        v.setIsActive(false);
        vendorRepository.save(v);
    }

    public long count() { return vendorRepository.count(); }
}
