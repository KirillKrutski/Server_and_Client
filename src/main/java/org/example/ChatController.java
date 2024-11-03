package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ChatController {
    @FXML
    private ListView<String> messagesList;
    @FXML
    private TextField messageField;

    private Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    private void handleSend() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            client.sendMessage(message);
            messageField.clear();
        }
    }

    @FXML
    private void handleEdit() {
        String selectedMessage = messagesList.getSelectionModel().getSelectedItem();
        if (selectedMessage != null) {
            // Запрос на изменение сообщения через client.editMessage
        }
    }

    @FXML
    private void handleDelete() {
        String selectedMessage = messagesList.getSelectionModel().getSelectedItem();
        if (selectedMessage != null) {
            // Запрос на удаление сообщения через client.deleteMessage
        }
    }
}

