package com.estate.service.impl;

import com.estate.converter.SaleContractDetailConverter;
import com.estate.converter.SaleContractListConverter;
import com.estate.dto.SaleContractDetailDTO;
import com.estate.dto.SaleContractFilterDTO;
import com.estate.dto.SaleContractListDTO;
import com.estate.repository.SaleContractRepository;
import com.estate.repository.entity.SaleContractEntity;
import com.estate.service.SaleContractService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleContractServiceImpl implements SaleContractService {
    @Autowired
    SaleContractRepository saleContractRepository;

    @Autowired
    private SaleContractListConverter saleContractListConverter;

    @Autowired
    private SaleContractDetailConverter saleContractDetailConverter;

    @Override
    public Long saleContractCnt(Long id) {
        return saleContractRepository.saleContractCnt(id);
    }

    @Override
    public Page<SaleContractListDTO> getSaleContracts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SaleContractEntity> entityPage = saleContractRepository.findAll(pageable);
        return toPageDTO(entityPage);
    }

    @Override
    public Page<SaleContractListDTO> search(SaleContractFilterDTO filter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SaleContractEntity> entityPage = saleContractRepository.searchSaleContracts(filter, pageable);
        return toPageDTO(entityPage);
    }

    @Override
    public SaleContractDetailDTO viewById(Long id) {
        SaleContractEntity entity = saleContractRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy hợp đồng mua bán với id: " + id));
        return saleContractDetailConverter.toDto(entity);
    }

    @Override
    public void delete(Long id) {
        saleContractRepository.deleteById(id);
    }

    private Page<SaleContractListDTO> toPageDTO(Page<SaleContractEntity> entityPage) {
        List<SaleContractListDTO> dtoList = new ArrayList<>();
        for (SaleContractEntity sc : entityPage) {
            dtoList.add(saleContractListConverter.toDto(sc));
        }
        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
