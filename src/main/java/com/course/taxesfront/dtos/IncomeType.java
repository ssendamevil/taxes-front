package com.course.taxesfront.dtos;

public enum IncomeType {
    MAIN_JOB(0.10),        // Основная работа
    SIDE_JOB(0.15),        // Дополнительная работа
    AUTHOR_FEES(0.13),     // Авторские вознаграждения
    PROPERTY_SALE(0.05),   // Продажа имущества
    GIFTS(0.20),           // Подарки (деньги, имущество)
    FOREIGN_TRANSFERS(0.20); // Переводы из-за границы

    private final double taxRate;

    IncomeType(double taxRate){
        this.taxRate = taxRate;
    }
}
