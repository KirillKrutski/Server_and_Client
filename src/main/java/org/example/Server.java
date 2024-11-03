package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class Server {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/chat_app";
    private static final String USER = "user_admin";
    private static final String PASS = "user";

    public static void main(String[] args) {
        int port = 8080;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected " + clientSocket.getInetAddress());
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    protected static Connection connectToDatabase() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    protected static boolean validateUser(String username, String password) throws SQLException {
        try (Connection conn = connectToDatabase();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    protected static boolean registerUser(String username, String password) throws SQLException {
        try (Connection conn = connectToDatabase()) {
            conn.setAutoCommit(false); // Начало транзакции

            // Вставка пользователя в таблицу users
            String userQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
            try (PreparedStatement userStmt = conn.prepareStatement(userQuery)) {
                userStmt.setString(1, username);
                userStmt.setString(2, password);
                userStmt.executeUpdate();
                System.out.println("User successfully inserted into users.");
            }

            // Вставка имени пользователя в таблицу messages
            String messageQuery = "INSERT INTO messages (username) VALUES (?)";
            try (PreparedStatement messageStmt = conn.prepareStatement(messageQuery)) {
                messageStmt.setString(1, username);
                messageStmt.executeUpdate();
                System.out.println("Username successfully inserted into messages.");
            }

            conn.commit(); // Подтверждаем транзакцию
            return true;
        } catch (SQLException e) {
            System.out.println("User registration failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


}
