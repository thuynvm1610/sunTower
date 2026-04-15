package com.estate.service.impl;

import com.estate.repository.RentAreaRepository;
import com.estate.service.RentAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RentAreaServiceImpl implements RentAreaService {
    private final RentAreaRepository rentAreaRepository;

    @Override
    public Map<Long, List<Integer>> getAllRentAreas() {
        List<Object[]> raw = rentAreaRepository.getAllRentAreasRaw();

        Map<Long, List<Integer>> result = new HashMap<>();

        for (Object[] row : raw) {
            Long buildingId = (Long) row[0];
            Integer value = (Integer) row[1];

            result.computeIfAbsent(buildingId, k -> new ArrayList<>()).add(value);
        }

        return result;
    }
}
