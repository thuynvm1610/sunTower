package com.estate.controller.customer;

import com.estate.dto.ContractDetailDTO;
import com.estate.security.CustomUserDetails;
import com.estate.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customer/contract")
public class CustomerContractController {
    @Autowired
    ContractService contractService;

    @GetMapping("/list")
    public String listContracts (
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long customerId = user.getCustomerId();

        Long totalContracts = contractService.getContractCountByCustomer(customerId);
        model.addAttribute("totalContracts", totalContracts);

        Long activeContracts = contractService.getActiveContractsCount(customerId);
        model.addAttribute("activeContracts", activeContracts);

        Long expiredContracts = contractService.getExpiredContractsCount(customerId);
        model.addAttribute("expiredContracts", expiredContracts);

        List<ContractDetailDTO> contracts = new ArrayList<>();
        model.addAttribute("contracts", contracts);

        return "customer/contract-list";
    }
}
