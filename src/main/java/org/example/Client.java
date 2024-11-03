package org.example;

import java.io.*;
import java.net.*;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public Client(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }

    // Метод для отправки сообщений на сервер
    public void sendMessage(String message) {
        out.println("message:" + message);
    }

    // Метод для редактирования сообщения
    public void editMessage(int messageId, String newText) {
        out.println("edit:" + messageId + ":" + newText);
    }

    // Метод для удаления сообщения
    public void deleteMessage(int messageId) {
        out.println("delete:" + messageId);
    }

    // Метод для регистрации нового пользователя
    public boolean register(String username, String password) {
        out.println("register:" + username + ":" + password);
        return waitForResponse("register_success");
    }

    // Метод для входа в систему
    public boolean login(String username, String password) {
        out.println("login:" + username + ":" + password);
        return waitForResponse("login_success");
    }

    // Ожидание ответа от сервера
    private boolean waitForResponse(String expectedResponse) {
        try {
            String response = in.readLine();
            return expectedResponse.equals(response);
        } catch (IOException e) {
            System.out.println("Error reading response: " + e.getMessage());
            return false;
        }
    }

    // Метод для закрытия соединения с сервером
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}
