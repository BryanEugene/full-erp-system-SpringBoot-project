package com.ERP.service;

import com.ERP.entity.Product;
import com.ERP.exception.BadRequestException;
import com.ERP.exception.ResourceNotFoundException;
import com.ERP.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<Product> getAll(String search, Pageable pageable) {
        if (search != null && !search.isBlank()) {
            return productRepository.searchActive(search, pageable);
        }
        return productRepository.findAll(pageable);
    }

    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }

    @Transactional
    public Product create(Product product) {
        if (productRepository.existsBySku(product.getSku())) {
            throw new BadRequestException("SKU already exists: " + product.getSku());
        }
        return productRepository.save(product);
    }

    @Transactional
    public Product update(Long id, Product update) {
        Product existing = getById(id);
        existing.setName(update.getName());
        existing.setDescription(update.getDescription());
        existing.setCategory(update.getCategory());
        existing.setUnit(update.getUnit());
        existing.setUnitPrice(update.getUnitPrice());
        existing.setCostPrice(update.getCostPrice());
        existing.setReorderLevel(update.getReorderLevel());
        existing.setIsActive(update.getIsActive());
        return productRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Product p = getById(id);
        p.setIsActive(false);
        productRepository.save(p);
    }

    public long count() { return productRepository.count(); }
}
