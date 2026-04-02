package com.estate.security;

import com.estate.repository.StaffRepository;
import com.estate.repository.CustomerRepository;
import com.estate.repository.entity.CustomerEntity;
import com.estate.repository.entity.StaffEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private CustomerRepository customerRepository;

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
