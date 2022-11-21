package RMIClient.GUI.views;

import RMIClient.ClientConnection;
import RMIClient.GUI.components.ChatCanvas;
import RMIClient.GUI.components.InputUser;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Optional;

public class MainView extends VBox {

    public static ClientConnection clientConnection;
    public ChatCanvas chatCanvas;

    /**
     * It's the component that first asks the user through 3 dialogs the information needed for connecting to the
     * server and second calls the setup method to create the UI of the chat
     */
    public MainView() {

        TextInputDialog dialog = new TextInputDialog("");

        setDialog(dialog, "Welcome", "Choose your nickname", "Nickname:");

        String clientName = inputLoop(dialog, "Client name not provided");

        connectionLoop(dialog, clientName);

        setUp();
    }

    /**
     * requests the server IP from the user in a loop until connection has been established successfully.
     *
     * @param dialog input dialog for IP address
     * @param clientName nickname chosen by the client
     */
    private void connectionLoop(TextInputDialog dialog, String clientName) {
        setDialog(dialog, "Welcome", "Say the IP of the server, you want to connect to", "IP:");

        boolean success = false; //connection not established
        while (!success) {

            String ipServer = inputLoop(dialog, "IP Server not provided");

            try {
                showInformationAlert(ipServer);
                clientConnection = new ClientConnection(clientName, ipServer, InetAddress.getLocalHost().toString());
                success = true;
            } catch (IOException e) {
                dialog.getEditor().clear(); //Remove input
                setDialog(dialog, "Oops!", "An IOException occurred! Try again", "IP:");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * notifies the user, that the connection is being established which can take a bit of time.
     *
     * @param ipServer IP address of the server
     */
    private void showInformationAlert(String ipServer) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        setDialog(alert, "Connection to " + ipServer,
                        "Connecting!",
                        "Connection is being established. This may take a bit...Just confirm and wait.");
        alert.showAndWait();
    }

    /**
     * asks the user for an input continuously until input is provided.
     *
     * @param dialog input dialog
     * @param exceptionString message to be passed to the exception
     * @return input string typed by user
     */
    private String inputLoop(TextInputDialog dialog, String exceptionString) {
        String inputString = "";
        do {
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                inputString = result.get();
            } else
                throw new IllegalStateException(exceptionString);
        } while(inputString.equals(""));
        dialog.getEditor().clear();
        return inputString;
    }

    /**
     * sets head, title and content of a Dialog object.
     *
     * @param dialog the dialog pane
     * @param title the specified title for the dialog
     * @param header the specified header for the dialog
     * @param content the specified content for the dialog
     */
    private void setDialog(Dialog<?> dialog, String title, String header, String content) {
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);
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
