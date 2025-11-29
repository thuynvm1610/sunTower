package com.estate.converter;

import com.estate.dto.ContractFormDTO;
import com.estate.exception.BusinessException;
import com.estate.repository.BuildingRepository;
import com.estate.repository.CustomerRepository;
import com.estate.repository.StaffRepository;
import com.estate.repository.entity.BuildingEntity;
import com.estate.repository.entity.ContractEntity;
import com.estate.repository.entity.CustomerEntity;
import com.estate.repository.entity.StaffEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContractFormConverter {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public ContractEntity toEntity(ContractFormDTO dto) {
        ContractEntity entity = modelMapper.map(dto, ContractEntity.class);
        BuildingEntity building = buildingRepository.findById(dto.getBuildingId())
                .orElseThrow(() -> new BusinessException("Không tìm thấy tòa nhà"));
        entity.setBuilding(building);
        StaffEntity staff = staffRepository.findById(dto.getStaffId())
                .orElseThrow(() -> new BusinessException("Không tìm thấy nhân viên"));
        entity.setStaff(staff);
        CustomerEntity customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new BusinessException("Không tìm thấy khách hàng"));
        entity.setCustomer(customer);
        entity.setStartDate(dto.getStartDate().atStartOfDay());
        entity.setEndDate(dto.getEndDate().atTime(23,59,59));
        return entity;
    }
}
