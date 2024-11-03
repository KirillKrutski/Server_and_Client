package org.example;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private String clientName = "Anonymous";

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.equals("exit")) {
                    System.out.println(clientName + " disconnected " + clientSocket.getInetAddress());
                    break;
                }
                if (inputLine.startsWith("@")) {
                    clientName = inputLine.substring(1);
                    continue;
                }
                System.out.println(clientName + ": " + inputLine);
                // Пример использования методов в Server
                if (inputLine.startsWith("register")) {
                    String[] parts = inputLine.split(":");
                    String username = parts[1];
                    String password = parts[2];
                    if (Server.registerUser(username, password)) {
                        out.println("register_success");
                    } else {
                        out.println("register_failure");
                    }
                }
                if (inputLine.startsWith("login")) {
                    String[] parts = inputLine.split(":");
                    String username = parts[1];
                    String password = parts[2];
                    if (Server.validateUser(username, password)) {
                        out.println("login_success");
                    } else {
                        out.println("login_failure");
                    }
                }
            }
        } catch (IOException | SQLException e) {
            System.out.println("Error in ClientHandler: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}

