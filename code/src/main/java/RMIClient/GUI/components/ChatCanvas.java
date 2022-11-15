package RMIClient.GUI.components;

import RMIClient.GUI.tasks.MessageInboxTask;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 * It represents the view of the user to see the chat
 */
public class ChatCanvas extends VBox {

    private TextArea chat;

    public ChatCanvas() {
        chat = new TextArea("");
        chat.getStyleClass().add("chat");
        chat.setEditable(false);
        this.getStyleClass().add("chat-canvas");

        MessageInboxTask messageInboxTask = new MessageInboxTask();
        chat.textProperty().bind(messageInboxTask.messageProperty());

        Thread th = new Thread(messageInboxTask);
        th.setDaemon(true);
        th.start();

        this.getChildren().add(chat);
    }

    /**
     * @return the current state of the whole chat as a string
     */
    public String getChat() {
        return ((TextArea) this.getChildren().get(0)).getText();
    }
}
