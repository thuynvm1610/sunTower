package com.estate.service.impl;

import com.estate.repository.UtilityMeterRepository;
import com.estate.repository.entity.UtilityMeterEntity;
import com.estate.service.UtilityMeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilityMeterServiceImpl implements UtilityMeterService {
    @Autowired
    UtilityMeterRepository utilityMeterRepository;

    @Override
    public UtilityMeterEntity getByContractIdAndMonthAndYear(Long contractId, Integer month, Integer year) {
        return utilityMeterRepository.getByContractIdAndMonthAndYear(contractId, month, year);
    }
}
