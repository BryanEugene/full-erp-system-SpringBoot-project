package com.ERP.service;

import com.ERP.entity.Employee;
import com.ERP.exception.BadRequestException;
import com.ERP.exception.ResourceNotFoundException;
import com.ERP.repository.DepartmentRepository;
import com.ERP.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public Page<Employee> getAll(String search, Pageable pageable) {
        if (search != null && !search.isBlank()) {
            return employeeRepository.search(search, pageable);
        }
        return employeeRepository.findAll(pageable);
    }

    public Employee getById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
    }

    @Transactional
    public Employee create(Employee emp) {
        if (employeeRepository.existsByEmployeeId(emp.getEmployeeId())) {
            throw new BadRequestException("Employee ID already exists: " + emp.getEmployeeId());
        }
        if (employeeRepository.existsByEmail(emp.getEmail())) {
            throw new BadRequestException("Email already in use: " + emp.getEmail());
        }
        if (emp.getDepartment() != null && emp.getDepartment().getId() != null) {
            emp.setDepartment(departmentRepository.findById(emp.getDepartment().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found")));
        }
        return employeeRepository.save(emp);
    }

    @Transactional
    public Employee update(Long id, Employee update) {
        Employee existing = getById(id);
        existing.setFullName(update.getFullName());
        existing.setPhone(update.getPhone());
        existing.setPosition(update.getPosition());
        existing.setSalary(update.getSalary());
        existing.setStatus(update.getStatus());
        existing.setAddress(update.getAddress());
        if (update.getDepartment() != null && update.getDepartment().getId() != null) {
            existing.setDepartment(departmentRepository.findById(update.getDepartment().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found")));
        }
        return employeeRepository.save(existing);
    }

    @Transactional
    public void terminate(Long id) {
        Employee emp = getById(id);
        emp.setStatus("Inactive");
        employeeRepository.save(emp);
    }

    public long count() { return employeeRepository.count(); }
    public long countActive() { return employeeRepository.countByStatus("Active"); }
}
