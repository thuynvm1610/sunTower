package com.estate.service;

import com.estate.repository.entity.UtilityMeterEntity;

public interface UtilityMeterService {
    UtilityMeterEntity findByContractIdAndMonthAndYear(Long contractId, Integer month, Integer year);
}
