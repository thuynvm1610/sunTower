package com.estate.security;

import com.estate.repository.CustomerRepository;
import com.estate.repository.StaffRepository;
import com.estate.repository.entity.CustomerEntity;
import com.estate.repository.entity.StaffEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final StaffRepository staffRepository;
    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        // 1. STAFF (ADMIN / STAFF)
        Optional<StaffEntity> staff = staffRepository.findByUsername(username);
        if (staff.isPresent()) {
            return new CustomUserDetails(
                    staff.get().getId(),
                    staff.get().getUsername(),
                    staff.get().getPassword(),
                    staff.get().getRole()
            );
        }

        // 2. CUSTOMER
        CustomerEntity customer = customerRepository.findByUsername(username);
        if (customer != null) {
            return new CustomUserDetails(
                    customer.getId(),
                    customer.getUsername(),
                    customer.getPassword(),
                    "CUSTOMER"
            );
        }

        throw new UsernameNotFoundException("User not found");
    }
}
