package org.example;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        String hostname = "localhost"; // address server
        int port = 8080;

        try (Socket socket = new Socket(hostname, port)) {
            // Current send data to server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String userInput;
            System.out.println("Enter(send exit if u want): ");

            //Reading data from console and send to server
            while ((userInput = in.readLine()) != null) {
                if ("exit".equalsIgnoreCase(userInput)) {
                    break;
                }
                out.println(userInput);
            }
        } catch (UnknownHostException e) {
            System.out.println("Error of connecting to " + hostname + ":" + port);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
