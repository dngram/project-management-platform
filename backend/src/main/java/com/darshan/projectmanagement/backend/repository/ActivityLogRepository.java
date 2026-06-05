package com.darshan.projectmanagement.backend.repository;

import com.darshan.projectmanagement.backend.entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityLogRepository
        extends JpaRepository<ActivityLog, Long> {

    List<ActivityLog> findByTaskIdOrderByCreatedAtDesc(
            Long taskId);
}