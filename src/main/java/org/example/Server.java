package org.example;

import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) {
        int port = 8080;  // Порт для подключения

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту: " + port);

            // Ожидание подключения клиента в бесконечном цикле
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Клиент подключен: " + clientSocket.getInetAddress());

                // Создание нового потока для обработки клиента
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.out.println("Ошибка в работе сервера: " + e.getMessage());
        }
    }
}

// Класс для обработки клиентов в отдельных потоках
class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try {
            // Поток для чтения данных от клиента
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                // Если клиент прислал "exit", завершаем его обработку
                if ("exit".equalsIgnoreCase(inputLine)) {
                    System.out.println("Клиент отключился: " + clientSocket.getInetAddress());
                    break;
                }
                // Выводим сообщение клиента на сервере
                System.out.println("Сообщение от клиента: " + inputLine);
            }

            // Закрытие сокета клиента
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Ошибка в обработке клиента: " + e.getMessage());
        }
    }
}
