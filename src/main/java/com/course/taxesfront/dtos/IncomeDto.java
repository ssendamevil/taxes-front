package com.course.taxesfront.dtos;

import java.time.LocalDateTime;

public class IncomeDto {
    private Long id;
    private IncomeType type;
    private Double amount;
    private LocalDateTime date;
    private String source;

    public IncomeDto(Long id, IncomeType type, Double amount, LocalDateTime date, String source) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.source = source;
    }

    public IncomeDto(IncomeType type, Double amount, LocalDateTime date, String source) {
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.source = source;
    }

    public Long getId() {
        return id;
    }

    public IncomeType getType() {
        return type;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getSource() {
        return source;
    }
}
