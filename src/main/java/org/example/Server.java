package org.example;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    public static void main(String[] args) {
        int port = 8080;
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Listening on port " + port);

            // client connect
            while(true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected " + clientSocket.getInetAddress());
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

class ClientHandler extends Thread {
    private Socket clientSocket;
    public String clientName = "Anonymous";
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            while((inputLine = in.readLine()) != null) {
                if(inputLine.equals("exit")) {
                    System.out.println(clientName + " disconnected " + clientSocket.getInetAddress());
                    break;
                }
                if (inputLine.startsWith("@")) {
                    clientName = inputLine.substring(1);
                    continue;
                }
                System.out.println(clientName + ": " + inputLine);
            }
            clientSocket.close();
        } catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
