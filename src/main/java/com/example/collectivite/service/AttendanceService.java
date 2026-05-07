package com.example.collectivite.service;

import com.example.collectivite.config.DBConnection;
import com.example.collectivite.dto.*;
import com.example.collectivite.exception.BadRequestException;
import com.example.collectivite.exception.ResourceNotFoundException;
import com.example.collectivite.repository.*;
import org.springframework.stereotype.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepo;
    private final ActivityRepository activityRepo;

    public AttendanceService(AttendanceRepository attendanceRepo, ActivityRepository activityRepo) {
        this.attendanceRepo = attendanceRepo;
        this.activityRepo = activityRepo;
    }

    public List<ActivityMemberAttendanceResponse> recordAttendance(String collectivityId, String activityId,
                                                                   List<CreateActivityMemberAttendanceRequest> requests) {
        if (!activityRepo.existsInCollectivity(activityId, collectivityId))
            throw new ResourceNotFoundException("Activity not found");

        List<ActivityMemberAttendanceResponse> responses = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            for (CreateActivityMemberAttendanceRequest req : requests) {
                String memberId = req.getMemberIdentifier();
                String status = req.getAttendanceStatus();

                Optional<String> current = attendanceRepo.getCurrentStatus(conn, activityId, memberId);
                if (current.isPresent()) {
                    if (!current.get().equals("UNDEFINED"))
                        throw new BadRequestException("ATTENDANCE_CONFIRMED", "Attendance already confirmed.");
                    attendanceRepo.updateStatus(conn, activityId, memberId, status);
                } else {
                    boolean isVisitor = !isMemberOfCollectivity(conn, memberId, collectivityId);
                    if (isVisitor && !"ATTENDED".equals(status))
                        throw new BadRequestException("VISITOR_INVALID", "Visitors can only be ATTENDED.");
                    String id = UUID.randomUUID().toString();
                    attendanceRepo.insert(conn, id, activityId, memberId, status, isVisitor);
                }

                ActivityMemberAttendanceResponse resp = new ActivityMemberAttendanceResponse();
                resp.setId(UUID.randomUUID().toString());
                resp.setMemberDescription(getMemberDescription(conn, memberId));
                resp.setAttendanceStatus(status);
                responses.add(resp);
            }
            conn.commit();
        } catch (Exception e) {
            throw new RuntimeException("Error recording attendance", e);
        }
        return responses;
    }

    public List<ActivityMemberAttendanceResponse> getAttendance(String collectivityId, String activityId) {
        if (!activityRepo.existsInCollectivity(activityId, collectivityId))
            throw new ResourceNotFoundException("Activity not found");

        List<Map<String, Object>> rows = attendanceRepo.findByActivity(activityId);
        List<ActivityMemberAttendanceResponse> list = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            ActivityMemberAttendanceResponse dto = new ActivityMemberAttendanceResponse();
            dto.setId((String) row.get("id"));
            dto.setAttendanceStatus((String) row.get("status"));
            dto.setMemberDescription(new MemberDescriptionDto(
                    (String) row.get("memberId"),
                    (String) row.get("firstName"),
                    (String) row.get("lastName"),
                    (String) row.get("email"),
                    (String) row.get("occupation")
            ));
            list.add(dto);
        }
        return list;
    }

    private boolean isMemberOfCollectivity(Connection conn, String memberId, String collectivityId) throws Exception {
        String sql = "SELECT COUNT(*) FROM member WHERE id = ? AND collectivity_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, memberId);
            stmt.setString(2, collectivityId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    private MemberDescriptionDto getMemberDescription(Connection conn, String memberId) throws Exception {
        String sql = "SELECT id, first_names, last_name, email, occupation FROM member WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, memberId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return new MemberDescriptionDto(
                    rs.getString("id"), rs.getString("first_names"), rs.getString("last_name"),
                    rs.getString("email"), rs.getString("occupation")
            );
        }
        return null;
    }
}
