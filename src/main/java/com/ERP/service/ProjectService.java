package com.ERP.service;

import com.ERP.entity.Project;
import com.ERP.exception.BadRequestException;
import com.ERP.exception.ResourceNotFoundException;
import com.ERP.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Page<Project> getAll(String search, String status, Pageable pageable) {
        return projectRepository.search(search != null ? search : "", status, pageable);
    }

    public Project getById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + id));
    }

    @Transactional
    public Project create(Project project) {
        if (projectRepository.existsByProjectCode(project.getProjectCode())) {
            throw new BadRequestException("Project code already exists: " + project.getProjectCode());
        }
        return projectRepository.save(project);
    }

    @Transactional
    public Project update(Long id, Project update) {
        Project existing = getById(id);
        existing.setName(update.getName());
        existing.setDescription(update.getDescription());
        existing.setClientName(update.getClientName());
        existing.setStartDate(update.getStartDate());
        existing.setEndDate(update.getEndDate());
        existing.setBudget(update.getBudget());
        existing.setProgress(update.getProgress());
        existing.setStatus(update.getStatus());
        existing.setPriority(update.getPriority());
        return projectRepository.save(existing);
    }

    @Transactional
    public Project updateStatus(Long id, String status) {
        Project existing = getById(id);
        existing.setStatus(status);
        return projectRepository.save(existing);
    }

    public long count() { return projectRepository.count(); }
}
