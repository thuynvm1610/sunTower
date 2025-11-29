package com.estate.service;

import com.estate.dto.ContractFilterDTO;
import com.estate.dto.ContractFormDTO;
import com.estate.dto.ContractListDTO;
import com.estate.dto.StaffPerformanceDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ContractService {
    Long countAll();
    List<StaffPerformanceDTO> getTopStaffs();
    List<BigDecimal> getMonthlyRevenue(int year);
    List<BigDecimal> getYearlyRevenue(int yearBeforeLast, int lastYear, int currentYear);
    Map<String, Long> getContractCountByBuilding();
    Map<Long, Long> getContractCountByYear();
    Page<ContractListDTO> getContracts(int page, int size);
    Page<ContractListDTO> search(ContractFilterDTO filter, int page, int size);
    void save(ContractFormDTO dto);
    void delete(Long id);
}
