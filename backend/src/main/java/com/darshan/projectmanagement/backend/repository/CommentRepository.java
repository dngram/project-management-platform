package com.darshan.projectmanagement.backend.repository;

import com.darshan.projectmanagement.backend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository
        extends JpaRepository<Comment, Long> {

    List<Comment> findByTaskId(Long taskId);
}