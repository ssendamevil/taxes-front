package com.course.taxesfront.controllers;

import com.course.taxesfront.dtos.TaxDto;
import com.course.taxesfront.dtos.TaxType;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

import java.util.Arrays;
import java.util.List;

public class AnalysisController {
    private List<TaxDto> taxes;
    private Double totalTaxes;
    @FXML
    private PieChart taxPieChart;

    @FXML
    public void initialize() {
    }

    private double calculatePercentage(TaxType type){
        double typeTaxAmount = taxes.stream().filter(taxDto -> taxDto.getType().equals(type)).mapToDouble(TaxDto::getAmount).sum();
        double percentage = (typeTaxAmount * 100) / totalTaxes;
        return Math.floor(percentage * 100) / 100.0;
    }

    public void setTotalTaxes(Double totalTaxes) {
        this.totalTaxes = totalTaxes;
    }

    public void setTaxes(List<TaxDto> taxes) {
        this.taxes = taxes;
        taxPieChart.getData().clear(); // Clear previous data

        taxPieChart.getData().addAll(
                Arrays.stream(TaxType.values()).map(taxType -> {
                    double percentage = calculatePercentage(taxType);
                    return new PieChart.Data(taxType.getDescription() + " (" + String.format("%.2f", percentage) + "%)", percentage);
                }).toList()
        );
    }
}
