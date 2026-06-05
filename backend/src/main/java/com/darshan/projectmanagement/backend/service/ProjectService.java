package com.darshan.projectmanagement.backend.service;

import com.darshan.projectmanagement.backend.dto.ProjectRequest;
import com.darshan.projectmanagement.backend.dto.ProjectResponse;
import com.darshan.projectmanagement.backend.entity.Project;
import com.darshan.projectmanagement.backend.exception.ProjectNotFoundException;
import com.darshan.projectmanagement.backend.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ProjectResponse createProject(ProjectRequest request) {

        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .createdAt(LocalDateTime.now())
                .build();

        project = projectRepository.save(project);

        return mapToResponse(project);
    }

    public List<ProjectResponse> getAllProjects() {

        return projectRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
    }

    public Project updateProject(Long id, Project updatedProject) {

        Project existingProject = getProjectById(id);

        existingProject.setName(updatedProject.getName());
        existingProject.setDescription(updatedProject.getDescription());

        return projectRepository.save(existingProject);
    }

    public void deleteProject(Long id) {

        Project project = getProjectById(id);

        projectRepository.delete(project);
    }

    private ProjectResponse mapToResponse(Project project) {

        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .createdAt(project.getCreatedAt())
                .build();
    }
}