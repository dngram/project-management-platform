package com.darshan.projectmanagement.backend.repository;

import com.darshan.projectmanagement.backend.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}