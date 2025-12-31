package com.estate.api.customer;

import com.estate.dto.UsernameChangeDTO;
import com.estate.repository.CustomerRepository;
import com.estate.security.CustomUserDetails;
import com.estate.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer/profile")
public class CustomerProfileAPI {
    @Autowired
    CustomerService customerService;

    @PutMapping("/username")
    public ResponseEntity<?> usernameUpdate(
            @RequestBody UsernameChangeDTO dto,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long customerId = user.getCustomerId();
        customerService.usernameUpdate(dto, customerId);
        return ResponseEntity.ok().build();
    }

}
