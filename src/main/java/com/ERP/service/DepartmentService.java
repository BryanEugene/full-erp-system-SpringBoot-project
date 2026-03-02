package com.ERP.service;

import com.ERP.entity.Department;
import com.ERP.exception.BadRequestException;
import com.ERP.exception.ResourceNotFoundException;
import com.ERP.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    public Department getById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found: " + id));
    }

    @Transactional
    public Department create(Department dept) {
        if (departmentRepository.existsByCode(dept.getCode())) {
            throw new BadRequestException("Department code already exists: " + dept.getCode());
        }
        return departmentRepository.save(dept);
    }

    @Transactional
    public Department update(Long id, Department update) {
        Department existing = getById(id);
        existing.setName(update.getName());
        existing.setDescription(update.getDescription());
        existing.setBudget(update.getBudget());
        existing.setIsActive(update.getIsActive());
        return departmentRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Department dept = getById(id);
        dept.setIsActive(false);
        departmentRepository.save(dept);
    }
}
