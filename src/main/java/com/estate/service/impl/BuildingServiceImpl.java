package com.estate.service.impl;

import com.estate.converter.BuildingDetailConverter;
import com.estate.converter.BuildingFormConverter;
import com.estate.converter.BuildingListConverter;
import com.estate.dto.BuildingDetailDTO;
import com.estate.dto.BuildingFilterDTO;
import com.estate.dto.BuildingFormDTO;
import com.estate.dto.BuildingListDTO;
import com.estate.enums.Direction;
import com.estate.enums.Level;
import com.estate.exception.BusinessException;
import com.estate.repository.*;
import com.estate.repository.entity.BuildingEntity;
import com.estate.repository.entity.DistrictEntity;
import com.estate.repository.entity.RentAreaEntity;
import com.estate.repository.entity.StaffEntity;
import com.estate.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private RentAreaRepository rentAreaRepository;

    @Autowired
    private BuildingListConverter buildingListConverter;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private BuildingFormConverter buildingFormConverter;

    @Autowired
    private BuildingDetailConverter buildingDetailConverter;

    @Override
    public long countAll() {
        return buildingRepository.count();
    }

    @Override
    public List<BuildingListDTO> findRecent() {
        List<BuildingEntity> buildingEntities = buildingRepository.findRecentBuildings(PageRequest.of(0, 5));
        List<BuildingListDTO> result = new ArrayList<>();

        for (BuildingEntity b : buildingEntities) {
            // Lấy danh sách tên nhân viên quản lý
            List<String> staffNames = staffRepository.findStaffNamesByBuildingId(b.getId());
            String staffNamesStr = String.join(" - ", staffNames);

            // Map sang DTO
            BuildingListDTO dto = new BuildingListDTO();
            dto.setName(b.getName());
            dto.setManagerName(staffNamesStr);

            result.add(dto);
        }

        return result;
    }

    @Override
    public Map<String, Long> getBuildingCountByDistrict() {
        List<Object[]> result = buildingRepository.countBuildingsByDistrict();
        Map<String, Long> map = new LinkedHashMap<>();
        for (Object[] row : result) {
            map.put((String) row[0], (Long) row[1]);
        }
        return map;
    }

    @Override
    public Page<BuildingListDTO> getBuildings(int page, int size) {
        Page<BuildingEntity> buildingPage = buildingRepository.findAll(PageRequest.of(page, size));

        // Tạo list chứa DTO
        List<BuildingListDTO> dtoList = new ArrayList<>();

        // Duyệt qua từng BuildingEntity
        for (BuildingEntity b : buildingPage) {
            List<String> managersName = staffRepository.findStaffNamesByBuildingId(b.getId());
            String managerNameStr = String.join(" - ", managersName);

            // Convert entity sang DTO, có thêm managerName
            BuildingListDTO dto = buildingListConverter.toDto(b, managerNameStr);

            dtoList.add(dto);
        }

        // Tạo PageImpl giữ nguyên thông tin phân trang gốc
        Page<BuildingListDTO> result = new PageImpl<>(
                dtoList,
                buildingPage.getPageable(),
                buildingPage.getTotalElements()
        );

        return result;
    }

    @Override
    public Page<BuildingListDTO> search(BuildingFilterDTO filter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BuildingEntity> buildingPage = buildingRepository.searchBuildings(filter, pageable);

        // Tạo list chứa DTO
        List<BuildingListDTO> dtoList = new ArrayList<>();

        // Duyệt qua từng BuildingEntity
        for (BuildingEntity b : buildingPage) {
            List<String> managersName = staffRepository.findStaffNamesByBuildingId(b.getId());
            String managerNameStr = String.join(" - ", managersName);

            // Convert entity sang DTO, có thêm managerName
            BuildingListDTO dto = buildingListConverter.toDto(b, managerNameStr);

            dtoList.add(dto);
        }

        // Tạo PageImpl giữ nguyên thông tin phân trang gốc
        Page<BuildingListDTO> result = new PageImpl<>(
                dtoList,
                buildingPage.getPageable(),
                buildingPage.getTotalElements()
        );

        return result;
    }

    @Override
    public List<String> getWardName() {
        return buildingRepository.getWardName();
    }

    @Override
    public List<String> getStreetName() {
        return buildingRepository.getStreetName();
    }

    @Override
    public List<String> getDirectionName() {
        List<String> directions = buildingRepository.getDirectionName();
        Map<String, String> enumMap = Arrays.stream(Direction.values())
                .collect(Collectors.toMap(Direction::name, Direction::getLabel));

        return directions.stream()
                .map(enumMap::get) // lấy label từ tên enum
                .filter(Objects::nonNull) // loại bỏ giá trị không khớp
                .toList();
    }

    @Override
    public List<String> getLevelName() {
        return buildingRepository.getLevelName();
    }

    @Override
    public void save(BuildingFormDTO dto) {

        BuildingEntity entity;

        if (dto.getId() != null) {
            entity = buildingRepository.findById(dto.getId())
                    .orElseThrow(() -> new BusinessException("Không tìm thấy tòa nhà để sửa"));
        } else {
            entity = new BuildingEntity();
        }

        buildingFormConverter.toEntity(entity, dto);

        buildingRepository.save(entity);
    }


    @Override
    public BuildingFormDTO findById(Long id) {
        BuildingEntity buildingEntity = buildingRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Không tìm thấy tòa nhà"));
        BuildingFormDTO buildingFormDTO = buildingFormConverter.toDTO(buildingEntity);
        return buildingFormDTO;
    }

    @Override
    public void delete(Long id) {
        if (!buildingRepository.existsById(id)) {
            throw new BusinessException("Không tìm thấy tòa nhà để xóa");
        }
        long count = contractRepository.countByBuildingId(id);
        if (count > 0) {
            throw new BusinessException("Không thể xóa! Tòa nhà đang có hợp đồng liên quan.");
        }
        buildingRepository.deleteById(id);
    }

    @Override
    public BuildingDetailDTO viewById(Long id) {
        BuildingEntity buildingEntity = buildingRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Không tìm thấy tòa nhà"));
        BuildingDetailDTO buildingDetailDTO = buildingDetailConverter.toDTO(buildingEntity);
        return buildingDetailDTO;
    }

    @Override
    public Map<String, Long> getBuildingsName() {
        List<BuildingEntity> buildingEntities = buildingRepository.findAll();
        Map<String, Long> result = new HashMap<>();
        for (BuildingEntity b : buildingEntities) {
            result.put(b.getName(), b.getId());
        }
        return result;
    }

}
