package TCPClient.GUI.views;

import TCPClient.ClientConnection;
import TCPClient.GUI.components.ChatCanvas;
import TCPClient.GUI.components.InputUser;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Optional;

public class MainView extends VBox {

    public static ClientConnection clientConnection;
    public ChatCanvas chatCanvas;

    /**
     * It's the component that first asks the user through 3 dialogs the information neeeded for connecting to the
     * server and second calls the setup method to create the UI of the cat
     */
    public MainView() {

        TextInputDialog dialog = new TextInputDialog("");

        dialog.setTitle("Welcome!");
        dialog.setHeaderText("Choose your nickname");
        dialog.setContentText("Name:");

        String clientName = "";

        do {
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                clientName = result.get();
            } else
                throw new IllegalStateException("Client name not provided");
        } while(clientName.equals(""));
        dialog.getEditor().clear(); //Remove input after name has been set

        String ipServer = "";
        dialog.setHeaderText("Choose the IP of the server, you want to connect to");
        dialog.setContentText("IP:");

        boolean not_success = true;
        while (not_success) { //loop until connection successfully established or program terminated
            do {
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    ipServer = result.get();
                } else
                    throw new IllegalStateException("IP Server not provided");
            } while (ipServer.equals(""));
            dialog.getEditor().clear(); //Remove input after IP has been set


            int portServer = -1;
            dialog.setHeaderText("Connection to " + ipServer + ". Which port?");
            dialog.setContentText("Port:");

            do {
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    try {
                        portServer = Integer.parseInt(result.get());
                    } catch (NumberFormatException e) {
                        portServer = -1;
                    }
                } else
                    throw new IllegalStateException("Port not provided");
            } while (portServer == -1);

            try {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Connecting");
                alert.setContentText("Connection is being established...Just confirm and wait.");
                alert.showAndWait();
                clientConnection = new ClientConnection(clientName, ipServer, portServer);

                not_success = false;
            } catch (IOException e) {
                dialog.getEditor().clear(); //Remove input
                dialog.setHeaderText("An IOException occured! Try again");
                dialog.setContentText("IP:");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        setUp();
    }

    /**
     * Set up the UI of the chat
     */
    public void setUp() {

        chatCanvas = new ChatCanvas();
        InputUser inputUser = new InputUser();

        getChildren().addAll(chatCanvas, inputUser);
    }
}
