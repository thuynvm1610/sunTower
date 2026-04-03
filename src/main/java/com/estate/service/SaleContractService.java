package com.estate.service;

import com.estate.dto.SaleContractDetailDTO;
import com.estate.dto.SaleContractFilterDTO;
import com.estate.dto.SaleContractListDTO;
import org.springframework.data.domain.Page;

public interface SaleContractService {
    Long saleContractCnt(Long id);
    Page<SaleContractListDTO> getSaleContracts(int page, int size);
    Page<SaleContractListDTO> search(SaleContractFilterDTO filter, int page, int size);
    SaleContractDetailDTO viewById(Long id);
    void delete(Long id);
}
