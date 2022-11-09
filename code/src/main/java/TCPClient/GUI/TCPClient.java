package TCPClient.GUI;

import javafx.application.Application;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class TCPClient extends Application {

    /**
     * Starts the UI process and set the possibility that when the user clicks to close the UI window, also the process
     * stops itself
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        GUIRunner.run(primaryStage);

        primaryStage.setOnCloseRequest(e -> {
            try {
                GUIRunner.stop();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
