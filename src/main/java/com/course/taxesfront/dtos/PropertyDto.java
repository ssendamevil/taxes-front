package com.course.taxesfront.dtos;

import java.time.LocalDateTime;

public class PropertyDto {
    private Long id;
    private Double price;
    private String description;
    private LocalDateTime date;
    private PropertyType type;

    public PropertyDto(Long id, Double price, String description, LocalDateTime date, PropertyType type) {
        this.id = id;
        this.price = price;
        this.description = description;
        this.date = date;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public PropertyType getType() {
        return type;
    }

    public void setType(PropertyType type) {
        this.type = type;
    }
}
