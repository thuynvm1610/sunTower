package com.estate.service.impl;

import com.estate.repository.SaleContractRepository;
import com.estate.service.SaleContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleContractServiceImpl implements SaleContractService {
    @Autowired
    SaleContractRepository saleContractRepository;

    @Override
    public Long saleContractCnt(Long id) {
        return saleContractRepository.saleContractCnt(id);
    }
}
