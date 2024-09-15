package org.example;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) {
        String hostname = "localhost";  // Адрес сервера
        int port = 8080;  // Порт, на котором запущен сервер

        try (Socket socket = new Socket(hostname, port)) {
            // Поток для отправки данных на сервер
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Поток для чтения данных с консоли
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            String userInput;
            System.out.println("Введите данные для отправки на сервер (для выхода введите 'exit'):");

            // Чтение данных из консоли и отправка на сервер
            while ((userInput = consoleInput.readLine()) != null) {
                out.println(userInput);  // Отправляем сообщение на сервер

                // Завершаем соединение, если введено "exit"
                if ("exit".equalsIgnoreCase(userInput)) {
                    break;
                }
            }
        } catch (UnknownHostException e) {
            System.out.println("Не удается подключиться к серверу: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e.getMessage());
        }
    }
}
