package com.estate.api.customer;

import com.estate.dto.ContractDetailDTO;
import com.estate.security.CustomUserDetails;
import com.estate.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer/contract")
public class CustomerContractAPI {
    @Autowired
    ContractService contractService;

    @GetMapping("/search")
    public List<ContractDetailDTO> getContractsSearch(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam (required = false) Long buildingId,
            @RequestParam (required = false) String status
    ) {
        Long customerId = user.getCustomerId();

        List<ContractDetailDTO> res = contractService.getContractsByFilter(customerId, buildingId, status);

        System.out.println("Số phần tử: " + res.size());
        System.out.println("Mã tòa nhà: " + buildingId);
        System.out.println("Trạng thái: " + status);

        return res;
    }
}
