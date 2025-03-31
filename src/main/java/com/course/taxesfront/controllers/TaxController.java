package com.course.taxesfront.controllers;

import com.course.taxesfront.HelloApplication;
import com.course.taxesfront.dtos.*;
import com.course.taxesfront.services.AppService;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class TaxController {
    private UserDto user;
    private List<IncomeDto> incomes;
    private List<TaxDto> taxes;
    private List<PropertyDto> properties;

    private double totalTaxes;
    private final AppService appService = new AppService();
    @FXML private TableView<TaxDto> taxTable;
    @FXML private TableColumn<TaxDto, Long> idColumn;
    @FXML private TableColumn<TaxDto, String> typeColumn;
    @FXML private TableColumn<TaxDto, String> taxAmountColumn;
    @FXML private TableColumn<TaxDto, String> dateColumn;
    @FXML private TableColumn<TaxDto, String> taxRatesColumn;
    @FXML private TableColumn<TaxDto, String> descriptionColumn;
    @FXML private Label totalTaxesLabel;
    @FXML private Label totalIncomeLabel;
    @FXML private Label totalPropertyLabel;
    @FXML private Button applyFiltersBtn;
    @FXML private ComboBox<Integer> yearFilter;
    @FXML private ComboBox<TaxType> taxTypeFilter;
    @FXML private TextField minAmountFilter;
    @FXML private TextField maxAmountFilter;

    public void setUser(UserDto user) {
        this.user = user;
    }

    public void setTaxes(List<TaxDto> taxes) {
        this.taxes = taxes;
    }

    @FXML
    private void initialize(){
        yearFilter.getItems().add(null); // "Все года"
        taxTypeFilter.getItems().add(null); // "Все типы"
        yearFilter.setValue(null);
        taxTypeFilter.setValue(null);
        int currentYear = LocalDate.now().getYear();
        for (int year = 2000; year <= currentYear; year++) {
            yearFilter.getItems().add(year);
        }
        taxTypeFilter.getItems().addAll(TaxType.values());
        idColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        typeColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getType().toString()));
        taxAmountColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAmount()).asString().concat(" ₸"));
        dateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDate().toString()));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDescription()));
        taxRatesColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getType().getRate()).asString().concat(" %"));
        applyFiltersBtn.setOnAction(actionEvent -> loadTaxesWithFilters());
    }

    @FXML
    public void openAnalysisWindow() throws IOException {
        Stage secondaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("analysis.fxml"));
        Parent root = loader.load();
        AnalysisController analysisController = loader.getController();
        analysisController.setTotalTaxes(totalTaxes);
        analysisController.setTaxes(taxes);
        secondaryStage.setTitle("Налоги");
        secondaryStage.setScene(new Scene(root));
        secondaryStage.show();
    }

    @FXML
    public void handleBack() throws IOException {
        HelloApplication.switchScene("dashboard2.fxml");
    }

    public void loadTaxes(){
        if (Objects.isNull(user) || user.getUsername() == null || user.getUsername().isEmpty()) {
            System.out.println("Ошибка: username не установлен!");
            return;
        }

        appService.calculateTaxes(user.getUsername(), response -> Platform.runLater(() -> {
            taxTable.getItems().setAll(response);
            setTaxes(response);
            setTotalTaxes(response.stream().mapToDouble(TaxDto::getAmount).sum());
            totalTaxesLabel.setText(totalTaxes + " ₸");
            totalIncomeLabel.setText(incomes.stream().mapToDouble(IncomeDto::getAmount).sum() + " ₸");
        }));
    }

    private void loadTaxesWithFilters(){
        LocalDateTime from = null;
        LocalDateTime to = null;
        TaxType selectedType = null;
        Double min = null;
        Double max = null;
        if(Objects.nonNull(yearFilter.getValue())) {
            from = LocalDateTime.of(yearFilter.getValue(), 1, 1, 0, 0, 0);
            to = LocalDateTime.of(yearFilter.getValue(), 11, 30, 23, 59, 59);
        }
        if(Objects.nonNull(taxTypeFilter.getValue())) selectedType = taxTypeFilter.getValue();
        if(Objects.nonNull(minAmountFilter.getText()) && Objects.nonNull(maxAmountFilter.getText())
        && !minAmountFilter.getText().isEmpty() && !maxAmountFilter.getText().isEmpty()){
            min = Double.valueOf(minAmountFilter.getText());
            max = Double.valueOf(maxAmountFilter.getText());
        }
        TaxFilter taxFilter = new TaxFilter(selectedType,from, to, min, max);

        if (Objects.isNull(user) || user.getUsername() == null || user.getUsername().isEmpty()) {
            System.out.println("Ошибка: username не установлен!");
            return;
        }

        appService.loadTaxesWithFilter(user.getUsername(), taxFilter, response -> {
            taxTable.getItems().setAll(response);
            setTaxes(response);
            setTotalTaxes(response.stream().mapToDouble(TaxDto::getAmount).sum());
            totalTaxesLabel.setText(totalTaxes + " ₸");
            totalIncomeLabel.setText(incomes.stream().filter(incomeDto -> response.stream().anyMatch(taxDto -> taxDto.getIncomeId().equals(incomeDto.getId()))).mapToDouble(IncomeDto::getAmount).sum() + " ₸");
            totalPropertyLabel.setText(properties.stream().filter(
                    propertyDto ->
                            response.stream().anyMatch(taxDto ->
                                    Objects.nonNull(taxDto.getPropertyId()) && taxDto.getPropertyId().equals(propertyDto.getId()))).mapToDouble(PropertyDto::getPrice).sum() + " ₸");
        });
    }

    public void setProperties(List<PropertyDto> properties) {
        this.properties = properties;
        totalPropertyLabel.setText(properties.stream().mapToDouble(PropertyDto::getPrice).sum() + " ₸");
    }

    public void setIncomes(List<IncomeDto> incomes) {
        this.incomes = incomes;
        totalIncomeLabel.setText(incomes.stream().mapToDouble(IncomeDto::getAmount).sum() + " ₸");
    }

    public void setTotalTaxes(double totalTaxes) {
        this.totalTaxes = totalTaxes;
    }
}
