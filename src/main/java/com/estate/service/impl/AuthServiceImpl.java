package com.estate.service.impl;

import com.estate.exception.BusinessException;
import com.estate.repository.CustomerRepository;
import com.estate.repository.StaffRepository;
import com.estate.repository.entity.CustomerEntity;
import com.estate.repository.entity.PasswordResetTokenEntity;
import com.estate.repository.entity.StaffEntity;
import com.estate.service.AuthService;
import com.estate.service.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    @Autowired
    StaffRepository staffRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PasswordResetTokenService tokenService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendResetEmail(String toEmail, String token) {

        String link = "http://localhost:8080/auth/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Đặt lại mật khẩu");
        message.setText(
                "Chào bạn,\n\n" +
                        "Vui lòng nhấp vào link dưới đây để đặt lại mật khẩu:\n" +
                        link + "\n\n" +
                        "Link có hiệu lực trong 30 phút."
        );

        mailSender.send(message);
    }

    @Override
    public void forgotPassword(String email) {
        String userType = null;
        Long userId = null;

        Optional<StaffEntity> staff = staffRepository.findByEmail(email);
        if (staff.isPresent()) {
            userType = "STAFF";
            userId = staff.get().getId();
        } else {
            Optional<CustomerEntity> customer = customerRepository.findByEmail(email);
            if (customer.isPresent()) {
                userType = "CUSTOMER";
                userId = customer.get().getId();
            }
        }

        if (userType == null) throw new BusinessException("Email không hợp lệ");

        PasswordResetTokenEntity token = tokenService.createToken(userType, userId);

        sendResetEmail(email, token.getToken());
    }

    @Override
    public void resetPassword(String tokenValue, String newPassword) {
        var token = tokenService.validate(tokenValue);

        if (token.getUserType().equals("STAFF")) {
            var staff = staffRepository.findById(token.getUserId())
                    .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại"));
            staff.setPassword(passwordEncoder.encode(newPassword));
            staffRepository.save(staff);
        } else {
            var customer = customerRepository.findById(token.getUserId())
                    .orElseThrow(() -> new RuntimeException("Khách hàng không tồn tại"));
            customer.setPassword(passwordEncoder.encode(newPassword));
            customerRepository.save(customer);
        }

        tokenService.markUsed(token);
    }
}
