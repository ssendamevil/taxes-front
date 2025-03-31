package com.course.taxesfront.controllers;

import com.course.taxesfront.HelloApplication;
import com.course.taxesfront.dtos.*;
import com.course.taxesfront.services.AppService;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DashboardController {
    private UserDto user;
    private final AppService appService = new AppService();

    @FXML private TableView<IncomeDto> incomeTable;
    @FXML private TableView<PropertyDto> propertyTable;
    @FXML private TableColumn<IncomeDto, Long> idColumn;
    @FXML private TableColumn<IncomeDto, String> typeColumn;
    @FXML private TableColumn<IncomeDto, String> amountColumn;
    @FXML private TableColumn<IncomeDto, String> dateColumn;
    @FXML private TableColumn<IncomeDto, String> sourceColumn;
    @FXML private TableColumn<PropertyDto, Long> propertyIdColumn;
    @FXML private TableColumn<PropertyDto, String> propertyPriceColumn;
    @FXML private TableColumn<PropertyDto, String> propertyDescriptionColumn;
    @FXML private TableColumn<PropertyDto, String> propertyDateColumn;
    @FXML private TableColumn<PropertyDto, String> propertyTypeColumn;
    @FXML private ComboBox<Integer> yearFilter;
    @FXML private ComboBox<IncomeType> incomeTypeFilter;
    @FXML private ComboBox<IncomeType> newIncomeType;
    @FXML private Button applyFiltersBtn;
    @FXML private Button refreshBtn;
    @FXML private Button addIncomeBtn;
    @FXML private Label totalIncomesLabel;
    @FXML private Label totalPropertiesLabel;
    @FXML private TextField newIncomeAmount;
    @FXML private TextField newIncomeSource;
    @FXML private DatePicker newIncomeDate;
    @FXML private Label usernameLabel;


    public void setUser(UserDto user) {
        this.user = user;
        usernameLabel.setText(user.getUsername());
    }

    @FXML
    private void initialize(){
        int currentYear = LocalDate.now().getYear();
        for (int year = 2000; year <= currentYear; year++) {
            yearFilter.getItems().add(year);
        }
        incomeTypeFilter.getItems().addAll(IncomeType.values());
        newIncomeType.getItems().addAll(IncomeType.values());
        yearFilter.getItems().add(null); // "Все года"
        incomeTypeFilter.getItems().add(null); // "Все типы"
        yearFilter.setValue(null);
        incomeTypeFilter.setValue(null);
        idColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        typeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getType().toString()));
        amountColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAmount()).asString().concat(" ₸"));
        dateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDate().toString()));
        sourceColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getSource()));
        propertyIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        propertyPriceColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrice()).asString().concat(" ₸"));
        propertyDescriptionColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDescription()));
        propertyDateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDate().toString()));
        propertyTypeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getType().toString()));
        applyFiltersBtn.setOnAction(event -> applyFilters());
        refreshBtn.setOnAction(event -> applyFilters());
        addIncomeBtn.setOnAction(event -> addNewIncome());
    }

    public void loadIncomes() {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            System.out.println("Ошибка: username не установлен!");
            return;
        }

        appService.loadIncomes(user.getUsername(), response -> Platform.runLater(() -> {
            incomeTable.getItems().setAll(response);
            totalIncomesLabel.setText(response.stream().mapToDouble(IncomeDto::getAmount).sum() +" ₸");
        }));

        appService.loadProperties(user.getUsername(), response -> Platform.runLater(() -> {
            propertyTable.getItems().setAll(response);
            totalPropertiesLabel.setText(response.stream().mapToDouble(PropertyDto::getPrice).sum() +" ₸");
        }));
    }

    private void applyFilters() {
        Integer selectedYear = yearFilter.getValue();
        IncomeType selectedType = incomeTypeFilter.getValue();

        // Фильтрация списка доходов
        appService.loadIncomes(user.getUsername(), response -> Platform.runLater(() -> {
            incomeTable.getItems().setAll(response.stream()
                    .filter(income -> selectedYear == null || income.getDate().getYear() == selectedYear)
                    .filter(income -> selectedType == null || income.getType() == selectedType)
                    .toList());
        }));
    }

    private void addNewIncome(){
        String incomeSource = newIncomeSource.getText();
        Double incomeAmount = Double.valueOf(newIncomeAmount.getText());
        LocalDateTime dateTime = newIncomeDate.getValue().atTime(LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(), LocalDateTime.now().getSecond());
        IncomeType type = newIncomeType.getValue();

        IncomeDto dto = new IncomeDto(type, incomeAmount, dateTime, incomeSource);
        appService.addNewIncome(user.getUsername(), dto, response -> {
            if(response.equals("200")){
                applyFilters();
            }
        });
    }

    @FXML
    public void handleCalculateTaxes(){
        try {
            HelloApplication.setIncomes(incomeTable.getItems());
            HelloApplication.setProperties(propertyTable.getItems());
            HelloApplication.switchScene("taxes.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void handleDownloadPdf(){
        appService.downloadPdf(user.getUsername(), response -> {
            Platform.runLater(() -> System.out.println("Status: " + response.get("200")));
        });
    }

    @FXML
    public void handleLogout(){

    }
}
