package com.darshan.projectmanagement.backend.service;

import com.darshan.projectmanagement.backend.dto.TaskRequest;
import com.darshan.projectmanagement.backend.dto.TaskResponse;
import com.darshan.projectmanagement.backend.entity.Project;
import com.darshan.projectmanagement.backend.entity.Task;
import com.darshan.projectmanagement.backend.exception.ProjectNotFoundException;
import com.darshan.projectmanagement.backend.exception.TaskNotFoundException;
import com.darshan.projectmanagement.backend.repository.ProjectRepository;
import com.darshan.projectmanagement.backend.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public TaskResponse createTask(TaskRequest request) {

        Project project = projectRepository.findById(
                        request.getProjectId())
                .orElseThrow(() ->
                        new ProjectNotFoundException(
                                request.getProjectId()));

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .project(project)
                .build();

        task = taskRepository.save(task);

        return mapToResponse(task);
    }

    public List<TaskResponse> getAllTasks() {

        return taskRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public Task getTaskById(Long id) {

        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task updateTask(Long id, Task updatedTask) {

        Task existingTask = getTaskById(id);

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());

        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long id) {

        Task task = getTaskById(id);

        taskRepository.delete(task);
    }

    private TaskResponse mapToResponse(Task task) {

        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .projectId(task.getProject().getId())
                .projectName(task.getProject().getName())
                .build();
    }
}