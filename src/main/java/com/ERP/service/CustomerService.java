package com.ERP.service;

import com.ERP.entity.Customer;
import com.ERP.exception.BadRequestException;
import com.ERP.exception.ResourceNotFoundException;
import com.ERP.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Page<Customer> getAll(String search, Pageable pageable) {
        if (search != null && !search.isBlank()) {
            return customerRepository.search(search, pageable);
        }
        return customerRepository.findAll(pageable);
    }

    public Customer getById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
    }

    @Transactional
    public Customer create(Customer customer) {
        if (customerRepository.existsByCode(customer.getCode())) {
            throw new BadRequestException("Customer code already exists: " + customer.getCode());
        }
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer update(Long id, Customer update) {
        Customer existing = getById(id);
        existing.setName(update.getName());
        existing.setEmail(update.getEmail());
        existing.setPhone(update.getPhone());
        existing.setAddress(update.getAddress());
        existing.setCity(update.getCity());
        existing.setCountry(update.getCountry());
        existing.setCreditLimit(update.getCreditLimit());
        existing.setIsActive(update.getIsActive());
        return customerRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Customer c = getById(id);
        c.setIsActive(false);
        customerRepository.save(c);
    }

    public long count() { return customerRepository.count(); }
}
