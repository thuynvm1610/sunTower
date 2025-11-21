package com.estate.service;

import com.estate.repository.entity.UserEntity;

import java.util.List;

public interface UserService {
    Long countAllStaffs();
    List<UserEntity> getStaffName();
}
