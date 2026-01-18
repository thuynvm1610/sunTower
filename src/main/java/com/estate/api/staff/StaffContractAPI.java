package com.estate.api.staff;

import com.estate.dto.*;
import com.estate.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/staff/contracts")
public class StaffContractAPI {
    @Autowired
    ContractService contractService;

    @GetMapping("/search")
    public Page<ContractDetailDTO> getContractsSearchPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "3") int size,
            ContractFilterDTO filter
    ) {
        return contractService.searchByStaff(filter, page - 1, size);
    }
}
