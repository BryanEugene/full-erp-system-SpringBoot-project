package com.ERP.service;

import com.ERP.entity.Payroll;
import com.ERP.exception.BadRequestException;
import com.ERP.exception.ResourceNotFoundException;
import com.ERP.repository.EmployeeRepository;
import com.ERP.repository.PayrollRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PayrollService {

    private final PayrollRepository payrollRepository;
    private final EmployeeRepository employeeRepository;

    public Page<Payroll> getAll(Integer year, Integer month, String status, Pageable pageable) {
        return payrollRepository.filter(year, month, status, pageable);
    }

    public Payroll getById(Long id) {
        return payrollRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payroll not found: " + id));
    }

    @Transactional
    public Payroll create(Payroll payroll) {
        if (payroll.getEmployee() == null || payroll.getEmployee().getId() == null) {
            throw new BadRequestException("Employee is required");
        }
        payroll.setEmployee(employeeRepository.findById(payroll.getEmployee().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found")));

        if (payrollRepository.findByEmployeeIdAndPeriodMonthAndPeriodYear(
                payroll.getEmployee().getId(), payroll.getPeriodMonth(), payroll.getPeriodYear()).isPresent()) {
            throw new BadRequestException("Payroll already exists for this period");
        }
        payroll.setStatus("Draft");
        return payrollRepository.save(payroll);
    }

    @Transactional
    public Payroll updateStatus(Long id, String status) {
        Payroll payroll = getById(id);
        payroll.setStatus(status);
        return payrollRepository.save(payroll);
    }
}
