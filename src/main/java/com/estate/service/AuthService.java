package com.estate.service;

public interface AuthService {
    void forgotPassword(String email);
    void resetPassword(String tokenValue, String newPassword);
    void sendResetEmail(String toEmail, String token);
}
