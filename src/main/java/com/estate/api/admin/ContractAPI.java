package com.estate.api.admin;

import com.estate.dto.ContractListDTO;
import com.estate.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/contract")
public class ContractAPI {
    @Autowired
    ContractService contractService;

    @GetMapping("/list/page")
    public Page<ContractListDTO> getContractsPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return contractService.getContracts(page - 1, size);
    }
}
