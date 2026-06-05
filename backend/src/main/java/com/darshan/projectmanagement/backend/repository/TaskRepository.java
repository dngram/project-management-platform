package com.darshan.projectmanagement.backend.repository;

import com.darshan.projectmanagement.backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}