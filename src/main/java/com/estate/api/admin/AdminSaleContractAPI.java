package com.estate.api.admin;

import com.estate.dto.SaleContractFilterDTO;
import com.estate.dto.SaleContractListDTO;
import com.estate.service.SaleContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/sale-contract")
public class AdminSaleContractAPI {

    @Autowired
    private SaleContractService saleContractService;

    @GetMapping("/list/page")
    public Page<SaleContractListDTO> getSaleContractsPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return saleContractService.getSaleContracts(page - 1, size);
    }

    @GetMapping("/search/page")
    public Page<SaleContractListDTO> getSaleContractsSearchPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            SaleContractFilterDTO filter
    ) {
        return saleContractService.search(filter, page - 1, size);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSaleContract(@PathVariable Long id) {
        saleContractService.delete(id);
        return ResponseEntity.ok().build();
    }
}