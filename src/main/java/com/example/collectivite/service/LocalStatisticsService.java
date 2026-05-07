package com.example.collectivite.service;

import com.example.collectivite.dto.LocalStatisticsItemDto;
import com.example.collectivite.dto.MemberDescriptionDto;
import com.example.collectivite.repository.LocalStatisticsRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LocalStatisticsService {

    private final LocalStatisticsRepository repository;

    public LocalStatisticsService(LocalStatisticsRepository repository) {
        this.repository = repository;
    }

    public List<LocalStatisticsItemDto> getLocalStatistics(String collectivityId, LocalDate from, LocalDate to) {
        List<Map<String, Object>> rows = repository.getMemberStats(collectivityId, from, to);
        List<LocalStatisticsItemDto> result = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            MemberDescriptionDto memberDesc = new MemberDescriptionDto(
                    (String) row.get("memberId"),
                    (String) row.get("firstName"),
                    (String) row.get("lastName"),
                    (String) row.get("email"),
                    (String) row.get("occupation")
            );
            double earned = ((Number) row.get("earnedAmount")).doubleValue();
            double unpaid = ((Number) row.get("unpaidAmount")).doubleValue();
            result.add(new LocalStatisticsItemDto(memberDesc, earned, unpaid));
        }
        return result;
    }
}