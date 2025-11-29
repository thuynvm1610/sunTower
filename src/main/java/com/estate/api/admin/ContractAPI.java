package com.estate.api.admin;

import com.estate.dto.ContractFilterDTO;
import com.estate.dto.ContractFormDTO;
import com.estate.dto.ContractListDTO;
import com.estate.dto.StaffFormDTO;
import com.estate.exception.InputValidationException;
import com.estate.service.ContractService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/search/page")
    public Page<ContractListDTO> getContractsSearchPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            ContractFilterDTO filter
    ) {
        Page<ContractListDTO> result = contractService.search(filter, page - 1, size);
        return result;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addContract(@Valid @RequestBody ContractFormDTO dto,
                                      BindingResult result) {
        if (result.hasErrors()) {
            String message;

            if (!result.getFieldErrors().isEmpty()) {
                message = result.getFieldErrors().get(0).getDefaultMessage();
            } else {
                message = result.getAllErrors().get(0).getDefaultMessage();
            }

            throw new InputValidationException(message);
        }

        contractService.save(dto);
        return ResponseEntity.ok("Thêm hợp đồng thành công");
    }
}
