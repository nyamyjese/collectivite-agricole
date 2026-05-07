package com.example.collectivite.service;

import com.example.collectivite.config.DBConnection;
import com.example.collectivite.dto.*;
import com.example.collectivite.exception.BadRequestException;
import com.example.collectivite.repository.ActivityRepository;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.*;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<CollectivityActivityResponse> createActivities(String collectivityId,
                                                               List<CreateCollectivityActivityRequest> requests) {
        List<CollectivityActivityResponse> responses = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            for (CreateCollectivityActivityRequest req : requests) {
                if (req.getRecurrenceRule() != null && req.getExecutiveDate() != null)
                    throw new BadRequestException("INVALID_ACTIVITY", "Cannot have both recurrence rule and executive date.");

                String id = UUID.randomUUID().toString();
                String ruleJson = null;
                if (req.getRecurrenceRule() != null) {
                    MonthlyRecurrenceRuleDto rule = req.getRecurrenceRule();
                    ruleJson = rule.getWeekOrdinal() + ":" + rule.getDayOfWeek();
                }
                activityRepository.insertActivity(conn, id, collectivityId, req.getLabel(), req.getActivityType(),
                        req.getMemberOccupationConcerned(), req.getExecutiveDate(), ruleJson);

                CollectivityActivityResponse resp = new CollectivityActivityResponse();
                resp.setId(id);
                resp.setLabel(req.getLabel());
                resp.setActivityType(req.getActivityType());
                resp.setMemberOccupationConcerned(req.getMemberOccupationConcerned());
                resp.setExecutiveDate(req.getExecutiveDate());
                resp.setRecurrenceRule(req.getRecurrenceRule());
                responses.add(resp);
            }
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException("Erreur création activités", e);
        }
        return responses;
    }

    public List<CollectivityActivityResponse> getActivities(String collectivityId) {
        List<Map<String, Object>> rows = activityRepository.findByCollectivity(collectivityId);
        List<CollectivityActivityResponse> res = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            CollectivityActivityResponse dto = new CollectivityActivityResponse();
            dto.setId((String) row.get("id"));
            dto.setLabel((String) row.get("label"));
            dto.setActivityType((String) row.get("activityType"));
            dto.setExecutiveDate((LocalDate) row.get("executiveDate"));

            String ruleJson = (String) row.get("recurrenceRule");
            if (ruleJson != null && !ruleJson.isEmpty()) {
                String[] parts = ruleJson.split(":");
                if (parts.length == 2) {
                    try {
                        int weekOrdinal = Integer.parseInt(parts[0]);
                        String dayOfWeek = parts[1];
                        dto.setRecurrenceRule(new MonthlyRecurrenceRuleDto(weekOrdinal, dayOfWeek));
                    } catch (NumberFormatException ignored) {}
                }
            }

            Object occupationsObj = row.get("occupations");
            if (occupationsObj instanceof List) {
                dto.setMemberOccupationConcerned((List<String>) occupationsObj);
            } else {
                dto.setMemberOccupationConcerned(new ArrayList<>());
            }
            res.add(dto);
        }
        return res;
    }
}