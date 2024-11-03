package org.example;

import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 8080;

        // Создаем клиента и подключаемся к серверу
        Client client = new Client(serverAddress, port);

        // Ожидаем ввода пользователя
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username:");
        String username = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        // Регистрация или вход
        System.out.println("Enter 'register' to sign up or 'login' to sign in:");
        String action = scanner.nextLine();

        boolean success;
        if (action.equalsIgnoreCase("register")) {
            success = client.register(username, password);
            if (success) {
                System.out.println("Registration successful! You can now log in.");
            } else {
                System.out.println("Registration failed.");
                return;
            }
        } else if (action.equalsIgnoreCase("login")) {
            success = client.login(username, password);
            if (!success) {
                System.out.println("Login failed.");
                return;
            }
            System.out.println("Login successful!");
        }

        // Начало отправки сообщений
        System.out.println("Enter messages to send to the server. Type 'exit' to quit.");
        while (true) {
            String message = scanner.nextLine();
            if (message.equalsIgnoreCase("exit")) {
                break;
            }
            client.sendMessage(message);
        }

        // Закрытие соединения
        client.close();
        System.out.println("Disconnected from server.");
        scanner.close();
    }
}
