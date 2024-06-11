package com.serviceapp.serviceapp.company.Dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.serviceapp.serviceapp.company.DayOfWeek;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class BusinessHourDto {
    private Long companyId;

    @Enumerated(EnumType.STRING)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private DayOfWeek dayOfWeek;

    private String openTime;
    private String closeTime;
    private Boolean isClosed;

    // Getters and setters
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }
}
