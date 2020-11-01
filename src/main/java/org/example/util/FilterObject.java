package org.example.util;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class FilterObject {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDate = LocalDate.MIN;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate toDate = LocalDate.MAX;
    private LocalTime fromTime = LocalTime.MIN;
    private LocalTime toTime = LocalTime.MAX;
    private boolean indexAll = false;

    public FilterObject() {
    }

    public boolean isIndexAll() {
        return indexAll;
    }

    public void setIndexAll(boolean indexAll) {
        this.indexAll = indexAll;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public LocalTime getFromTime() {
        return fromTime;
    }

    public void setFromTime(LocalTime fromTime) {
        this.fromTime = fromTime;
    }

    public LocalTime getToTime() {
        return toTime;
    }

    public void setToTime(LocalTime toTime) {
        this.toTime = toTime;
    }

    @Override
    public String toString() {
        return "FilterObject{" +
                "fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", fromTime=" + fromTime +
                ", toTime=" + toTime +
                '}';
    }
}
