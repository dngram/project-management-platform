package com.darshan.projectmanagement.backend.repository;

import com.darshan.projectmanagement.backend.entity.PasswordResetToken;
import com.darshan.projectmanagement.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository
        extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    Optional<PasswordResetToken> findByUser(User user);

    void deleteByUser(User user);

    void deleteByToken(String token);
}