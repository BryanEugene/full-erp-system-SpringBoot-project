package com.ERP.service;

import com.ERP.entity.SalesOrder;
import com.ERP.exception.BadRequestException;
import com.ERP.exception.ResourceNotFoundException;
import com.ERP.repository.CustomerRepository;
import com.ERP.repository.SalesOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;
    private final CustomerRepository customerRepository;

    public Page<SalesOrder> getAll(String status, Long customerId, Pageable pageable) {
        return salesOrderRepository.filter(status, customerId, pageable);
    }

    public SalesOrder getById(Long id) {
        return salesOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sales order not found: " + id));
    }

    @Transactional
    public SalesOrder create(SalesOrder order) {
        if (salesOrderRepository.existsByOrderNumber(order.getOrderNumber())) {
            throw new BadRequestException("Order number already exists: " + order.getOrderNumber());
        }
        if (order.getCustomer() != null && order.getCustomer().getId() != null) {
            order.setCustomer(customerRepository.findById(order.getCustomer().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Customer not found")));
        }
        if (order.getItems() != null) {
            order.getItems().forEach(item -> item.setOrder(order));
        }
        return salesOrderRepository.save(order);
    }

    @Transactional
    public SalesOrder updateStatus(Long id, String status) {
        SalesOrder order = getById(id);
        order.setStatus(status);
        return salesOrderRepository.save(order);
    }

    public long count() { return salesOrderRepository.count(); }
}
