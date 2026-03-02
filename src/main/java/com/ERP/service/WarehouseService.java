package com.ERP.service;

import com.ERP.entity.StockLevel;
import com.ERP.entity.Warehouse;
import com.ERP.exception.ResourceNotFoundException;
import com.ERP.repository.StockLevelRepository;
import com.ERP.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final StockLevelRepository stockLevelRepository;

    public Page<Warehouse> getAll(Pageable pageable) {
        return warehouseRepository.findAll(pageable);
    }

    public Warehouse getById(Long id) {
        return warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found: " + id));
    }

    public List<StockLevel> getStock(Long warehouseId) {
        return stockLevelRepository.findByWarehouseId(warehouseId);
    }
}
