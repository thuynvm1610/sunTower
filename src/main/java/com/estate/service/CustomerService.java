package com.estate.service;

import com.estate.dto.PotentialCustomersDTO;

import java.util.List;

public interface CustomerService {
    long countAll();
    List<PotentialCustomersDTO>getTopCustomers();
}
