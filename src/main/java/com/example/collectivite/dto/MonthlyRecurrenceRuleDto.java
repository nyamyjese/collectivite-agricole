package com.example.collectivite.dto;

public class MonthlyRecurrenceRuleDto {
    private int weekOrdinal;
    private String dayOfWeek; // MO, TU, WE, TH, FR, SA, SU

    public MonthlyRecurrenceRuleDto() {}
    public MonthlyRecurrenceRuleDto(int weekOrdinal, String dayOfWeek) {
        this.weekOrdinal = weekOrdinal;
        this.dayOfWeek = dayOfWeek;
    }
    public int getWeekOrdinal() { return weekOrdinal; }
    public void setWeekOrdinal(int weekOrdinal) { this.weekOrdinal = weekOrdinal; }
    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }
}