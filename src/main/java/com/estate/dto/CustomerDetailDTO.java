package com.estate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetailDTO {
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private String phone;
    private String email;
    private List<String> staffs = new ArrayList<>();
    private List<CustomerContractDTO> customerContracts = new ArrayList<>();
}
