package com.example.collectivite.service;

import com.example.collectivite.dto.CollectivityInformationDto;
import com.example.collectivite.dto.OverallStatisticsItemDto;
import com.example.collectivite.repository.OverallStatisticsRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OverallStatisticsService {

    private final OverallStatisticsRepository repository;

    public OverallStatisticsService(OverallStatisticsRepository repository) {
        this.repository = repository;
    }

    public List<OverallStatisticsItemDto> getOverallStatistics(LocalDate from, LocalDate to) {
        List<Map<String, Object>> rows = repository.getOverallStats(from, to);
        List<OverallStatisticsItemDto> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            CollectivityInformationDto info = new CollectivityInformationDto(
                    (String) row.get("name"),
                    (int) row.get("number")
            );
            int newMembers = (int) row.get("newMembers");
            double duePercentage = (double) row.get("duePercentage");
            result.add(new OverallStatisticsItemDto(info, newMembers, duePercentage));
        }
        return result;
    }
}