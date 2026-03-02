package com.ERP.service;

import com.ERP.entity.LeaveRequest;
import com.ERP.exception.ResourceNotFoundException;
import com.ERP.repository.EmployeeRepository;
import com.ERP.repository.LeaveRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;

    public Page<LeaveRequest> getAll(String status, Long employeeId, Pageable pageable) {
        return leaveRequestRepository.filter(status, employeeId, pageable);
    }

    public LeaveRequest getById(Long id) {
        return leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found: " + id));
    }

    @Transactional
    public LeaveRequest create(LeaveRequest request) {
        if (request.getEmployee() != null && request.getEmployee().getId() != null) {
            request.setEmployee(employeeRepository.findById(request.getEmployee().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found")));
        }
        request.setStatus("Pending");
        return leaveRequestRepository.save(request);
    }

    @Transactional
    public LeaveRequest approve(Long id, String status) {
        LeaveRequest req = getById(id);
        req.setStatus(status);
        return leaveRequestRepository.save(req);
    }
}
