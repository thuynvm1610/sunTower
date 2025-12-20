package com.estate.repository;

import com.estate.repository.entity.UtilityMeterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilityMeterRepository extends JpaRepository<UtilityMeterEntity, Long> {
    Optional<UtilityMeterEntity> findByContractIdAndMonthAndYear(Long contractId, Integer month, Integer year);
}
