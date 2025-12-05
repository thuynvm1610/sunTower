package com.estate.service;

import com.estate.repository.entity.UtilityMeterEntity;

public interface UtilityMeterService {
    UtilityMeterEntity getByContractIdAndMonthAndYear(Long contractId, Integer month, Integer year);
}
