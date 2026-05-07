package com.example.collectivite.dto;

public class ActivityMemberAttendanceResponse {
    private String id;
    private MemberDescriptionDto memberDescription;
    private String attendanceStatus; // ATTENDED, MISSING, UNDEFINED

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public MemberDescriptionDto getMemberDescription() { return memberDescription; }
    public void setMemberDescription(MemberDescriptionDto memberDescription) { this.memberDescription = memberDescription; }
    public String getAttendanceStatus() { return attendanceStatus; }
    public void setAttendanceStatus(String attendanceStatus) { this.attendanceStatus = attendanceStatus; }
}