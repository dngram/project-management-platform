package com.darshan.projectmanagement.backend.repository;

import com.darshan.projectmanagement.backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedUserEmail(String email);
}