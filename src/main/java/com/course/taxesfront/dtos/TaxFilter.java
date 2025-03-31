package com.course.taxesfront.dtos;

import java.time.LocalDateTime;

public class TaxFilter {
    private TaxType type;
    private LocalDateTime from;

    public TaxFilter(TaxType type, LocalDateTime from, LocalDateTime to, Double min, Double max) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.min = min;
        this.max = max;
    }

    private LocalDateTime to;
    private Double min;
    private Double max;

    public TaxType getType() {
        return type;
    }

    public void setType(TaxType type) {
        this.type = type;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }
}