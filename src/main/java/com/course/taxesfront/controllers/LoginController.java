package com.course.taxesfront.controllers;

import com.course.taxesfront.HelloApplication;
import com.course.taxesfront.services.AppService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label loginError;

    private final AppService appService = new AppService();

    @FXML
    public void handleLogin() {
        System.out.println("Login button clicked!");
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Введите username и пароль!");
            return;
        }

        appService.login(username, password, response -> {
            if(response.containsKey("200")) {
                try {
                    HelloApplication.setUser(response.get("200"));
                    HelloApplication.switchScene("dashboard2.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else if (response.containsKey("401")) {
                showError("Неверный логин или пароль.");
            } else if (response.containsKey("500")) {
                showError("Ошибка сервера. Попробуйте позже.");
            } else {
                showError("Неизвестная ошибка. Код: " + response.keySet().iterator().next());
            }
        });
    }

    @FXML
    public void openRegisterWindow() throws IOException {
        HelloApplication.switchScene("register.fxml");
    }

    private void showError(String message) {
        loginError.setText(message);
        loginError.setVisible(true);
    }
}
