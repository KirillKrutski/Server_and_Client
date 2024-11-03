package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    private Client client; // Подключение к серверу

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (client.login(username, password)) {
            // Открытие чата
        } else {
            System.out.println("Login failed");
        }
    }

    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (client.register(username, password)) {
            System.out.println("Registration successful");
        } else {
            System.out.println("Registration failed");
        }
    }
}

