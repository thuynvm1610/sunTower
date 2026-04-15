package com.estate.repository;

import com.estate.repository.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByTokenHashAndRevokedFalseAndExpiresAtAfter(String tokenHash, LocalDateTime now);

    @Modifying
    @Query("""
            UPDATE RefreshTokenEntity r
            SET r.revoked = true
            WHERE r.userType = :userType
              AND r.userId = :userId
              AND r.revoked = false
            """)
    void revokeAllActiveForUser(
            @Param("userType") String userType,
            @Param("userId") Long userId);

    @Modifying
    @Query("""
            UPDATE RefreshTokenEntity r
            SET r.revoked = true
            WHERE r.tokenHash = :tokenHash
            """)
    void revokeByTokenHash(@Param("tokenHash") String tokenHash);
}
