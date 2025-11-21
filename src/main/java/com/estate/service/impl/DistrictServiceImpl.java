package com.estate.service.impl;

import com.estate.repository.DistrictRepository;
import com.estate.repository.entity.DistrictEntity;
import com.estate.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DistrictServiceImpl implements DistrictService {

    @Autowired
    DistrictRepository districtRepository;

    @Override
    public Map<String, Long> findAll() {
        List<DistrictEntity> districtEntities = districtRepository.findAll();
        Map<String, Long> result = new HashMap<>();
        for (DistrictEntity d : districtEntities) {
            result.put(d.getName(), d.getId());
        }
        return result;
    }
}
