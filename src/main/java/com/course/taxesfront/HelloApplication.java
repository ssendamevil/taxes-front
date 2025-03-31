package com.course.taxesfront;

import com.course.taxesfront.controllers.DashboardController;
import com.course.taxesfront.controllers.TaxController;
import com.course.taxesfront.dtos.IncomeDto;
import com.course.taxesfront.dtos.PropertyDto;
import com.course.taxesfront.dtos.UserDto;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HelloApplication extends Application {
    private static Stage primaryStage;
    private static UserDto user;

    private static List<IncomeDto> incomes;
    private static List<PropertyDto> properties;

    public static void setProperties(List<PropertyDto> properties) {
        HelloApplication.properties = properties;
    }

    public static void setIncomes(List<IncomeDto> incomes) {
        HelloApplication.incomes = incomes;
    }

    public static void setUser(UserDto userDto) {
        HelloApplication.user = userDto;
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("login2.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Налоги");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void switchScene(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(HelloApplication.class.getResource(fxml)));
        Parent root = loader.load();
        if (fxml.equals("dashboard2.fxml")) {
            DashboardController controller = loader.getController();
            controller.setUser(user);
            controller.loadIncomes();
        }else if(fxml.equals("taxes.fxml")) {
            TaxController controller = loader.getController();
            controller.setUser(user);
            controller.setIncomes(incomes);
            controller.setProperties(properties);
            controller.loadTaxes();
        }

        primaryStage.setScene(new Scene(root));
    }
}