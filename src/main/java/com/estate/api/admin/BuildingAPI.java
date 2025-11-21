package com.estate.api.admin;

import com.estate.dto.BuildingFilterDTO;
import com.estate.dto.BuildingFormDTO;
import com.estate.dto.BuildingListDTO;
import com.estate.exception.InputValidationException;
import com.estate.service.BuildingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/building")
public class BuildingAPI {
    @Autowired
    private BuildingService buildingService;

    @GetMapping("/list/page")
    public Page<BuildingListDTO> getBuildingsPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {

        return buildingService.getBuildings(page - 1, size);
    }

    @GetMapping("/search/page")
    public Page<BuildingListDTO> getBuildingsSearchPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            BuildingFilterDTO filter
    ) {
        Page<BuildingListDTO> result = buildingService.search(filter, page - 1, size);
        return result;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBuilding(@Valid @RequestBody BuildingFormDTO dto,
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

        buildingService.save(dto);
        return ResponseEntity.ok("Thêm tòa nhà thành công");
    }

    @PutMapping ("/edit")
    public ResponseEntity<?> editBuilding(@Valid @RequestBody BuildingFormDTO dto,
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

        buildingService.save(dto);
        return ResponseEntity.ok("Sửa tòa nhà thành công");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBuilding(@PathVariable Long id) {
        buildingService.delete(id);
        return ResponseEntity.ok().build();
    }

}
