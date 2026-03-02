package com.ERP.service;

import com.ERP.entity.PurchaseOrder;
import com.ERP.exception.BadRequestException;
import com.ERP.exception.ResourceNotFoundException;
import com.ERP.repository.PurchaseOrderRepository;
import com.ERP.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final VendorRepository vendorRepository;

    public Page<PurchaseOrder> getAll(String status, Long vendorId, Pageable pageable) {
        return purchaseOrderRepository.filter(status, vendorId, pageable);
    }

    public PurchaseOrder getById(Long id) {
        return purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase order not found: " + id));
    }

    @Transactional
    public PurchaseOrder create(PurchaseOrder order) {
        if (purchaseOrderRepository.existsByPoNumber(order.getPoNumber())) {
            throw new BadRequestException("PO number already exists: " + order.getPoNumber());
        }
        if (order.getVendor() != null && order.getVendor().getId() != null) {
            order.setVendor(vendorRepository.findById(order.getVendor().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Vendor not found")));
        }
        if (order.getItems() != null) {
            order.getItems().forEach(item -> item.setPurchaseOrder(order));
        }
        return purchaseOrderRepository.save(order);
    }

    @Transactional
    public PurchaseOrder updateStatus(Long id, String status) {
        PurchaseOrder order = getById(id);
        order.setStatus(status);
        return purchaseOrderRepository.save(order);
    }

    public long count() { return purchaseOrderRepository.count(); }
}
