package com.course.taxesfront.controllers;

import com.course.taxesfront.HelloApplication;
import com.course.taxesfront.dtos.UserCreate;
import com.course.taxesfront.services.AppService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class RegisterController {
    private final AppService appService = new AppService();

    @FXML
    private TextField username;

    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;

    @FXML
    private TextField middlename;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Label registerError;

    @FXML
    private VBox registrationForm;

    @FXML
    private VBox successMessage;

    @FXML
    private void handleBackToLogin() throws IOException {
        HelloApplication.switchScene("login2.fxml");
    }

    @FXML
    private void handleRegister() {
        if (isInputValid()) {
            UserCreate userDto = new UserCreate(
                    username.getText().trim(),
                    firstname.getText().trim(),
                    lastname.getText().trim(),
                    middlename.getText().trim(),
                    email.getText().trim(),
                    password.getText()
            );
            appService.register(userDto, response -> {
                showSuccessMessage();
            });
        } else {
            registerError.setText("Все поля должны быть заполнены!");
            registerError.setVisible(true);
        }
    }

    private boolean isInputValid() {
        return !username.getText().trim().isEmpty()
                && !firstname.getText().trim().isEmpty()
                && !lastname.getText().trim().isEmpty()
                && !email.getText().trim().isEmpty()
                && !password.getText().isEmpty();
    }
    private void showSuccessMessage() {
        registrationForm.setVisible(false);
        successMessage.setVisible(true);
    }
}
