package com.course.taxesfront.dtos;

import java.time.LocalDateTime;

public class TaxDto {
    private Long id;
    private Double amount;
    private Long incomeId;
    private String description;
    private LocalDateTime date;
    private TaxType type;
    private Long propertyId;

    public TaxDto(Long id, Double amount, Long incomeId, String description, LocalDateTime date, TaxType type, Long propertyId) {
        this.id = id;
        this.amount = amount;
        this.incomeId = incomeId;
        this.description = description;
        this.date = date;
        this.type = type;
        this.propertyId = propertyId;
    }

    public TaxType getType() {
        return type;
    }

    public void setType(TaxType type) {
        this.type = type;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(Long incomeId) {
        this.incomeId = incomeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
