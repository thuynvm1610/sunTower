package com.estate.service.impl;

import com.estate.repository.PasswordResetTokenRepository;
import com.estate.repository.entity.PasswordResetTokenEntity;
import com.estate.service.PasswordResetTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
    private final PasswordResetTokenRepository tokenRepo;

    @Override
    public PasswordResetTokenEntity createToken(String userType, Long userId) {
        PasswordResetTokenEntity token = new PasswordResetTokenEntity();
        token.setToken(UUID.randomUUID().toString());
        token.setUserType(userType);
        token.setUserId(userId);
        token.setExpiresAt(LocalDateTime.now().plusMinutes(30));
        token.setUsed(false);

        return tokenRepo.save(token);
    }

    @Override
    public PasswordResetTokenEntity validate(String tokenValue) {
        PasswordResetTokenEntity token = tokenRepo.findByToken(tokenValue)
                .orElseThrow(() -> new RuntimeException("Token không hợp lệ"));

        if (token.isUsed()) throw new RuntimeException("Token đã dùng");
        if (token.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new RuntimeException("Token hết hạn");

        return token;
    }

    @Override
    public void markUsed(PasswordResetTokenEntity token) {
        token.setUsed(true);
        tokenRepo.save(token);
    }
}
