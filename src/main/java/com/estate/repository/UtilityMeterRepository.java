package com.estate.repository;

import com.estate.repository.entity.UtilityMeterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilityMeterRepository extends JpaRepository<UtilityMeterEntity, Long> {
    UtilityMeterEntity getByContractIdAndMonthAndYear(Long contractId, Integer month, Integer year);
}
