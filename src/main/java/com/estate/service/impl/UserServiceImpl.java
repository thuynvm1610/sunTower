package com.estate.service.impl;

import com.estate.repository.UserRepository;
import com.estate.repository.entity.UserEntity;
import com.estate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository staffRepository;

    @Override
    public Long countAllStaffs() {
        return staffRepository.countByRole("STAFF");
    }

    @Override
    public List<UserEntity> getStaffName() {
        return staffRepository.findByRole("STAFF");
    }
}
