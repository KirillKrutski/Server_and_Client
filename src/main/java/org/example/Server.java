package org.example;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 8080;
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Listening on port " + port);

            // client connect
            while(true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected " + clientSocket.getInetAddress());

                //reading data from client
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputeLine;

                //while client send exit, server is continued reading
                while((inputeLine = in.readLine()) != null) {
                    if ("exit".equals(inputeLine)) {
                        System.out.println("Client disconnected");
                        break;
                    }
                    System.out.println("Client: " + inputeLine);
                }
                //close socket
                clientSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
