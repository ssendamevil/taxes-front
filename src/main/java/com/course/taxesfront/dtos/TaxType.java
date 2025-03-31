package com.course.taxesfront.dtos;

public enum TaxType {
    INDIVIDUAL_INCOME_TAX("Индивидуальный подоходный налог", 10),
    PROPERTY_TAX("Налог на имущество", 0.05),
    TRANSPORT_TAX("Транспортный налог", 1),
    LAND_TAX("Земельный налог", 0.3),
    PENSION_CONTRIBUTION("Пенсионные отчисления", 10),
    MEDICAL_INSURANCE("Обязательное медицинское страхование", 2),
    SOCIAL_CONTRIBUTION("Социальные отчисления", 3.5);

    private final String description;
    private final double rate; // В процентах или фиксированных ставках

    TaxType(String description, double rate) {
        this.description = description;
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public double getRate() {
        return rate;
    }
}
