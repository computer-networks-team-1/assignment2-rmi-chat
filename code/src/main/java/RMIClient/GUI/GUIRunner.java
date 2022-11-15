package RMIClient.GUI;

import RMIClient.GUI.views.MainView;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class GUIRunner {

    public static MainView mainView;

    private GUIRunner() {}

    /**
     * Initializes JavaFX scene
     *
     * @param primaryStage JavaFX primary stage.
     * @throws IOException when graphic.fxml is not found.
     */
    public static void run(Stage primaryStage) throws Exception {

        primaryStage.setTitle("RMIClient - Online Chat");

        try {
            mainView = new MainView();
        } catch (IllegalStateException e) {
            System.out.println("Closed. " + e.getMessage());
            stop();
        }

        Scene scene = new Scene(mainView, 850, 478);
        scene.getStylesheets().add("style.css");

        primaryStage.setScene(scene);
        primaryStage
                .getIcons()
                .add(new Image(new File("src/main/resources/assets/logo.PNG").toURI().toString()));

        primaryStage.setResizable(false);

        primaryStage.setMaximized(false);
        primaryStage.setFullScreen(false);
        primaryStage.show();

    }

    /** Stops the GUI and the threads. */
    public static void stop() throws Exception {
        if(MainView.clientConnection != null)
            MainView.clientConnection.closeCommunication();
        Platform.exit();
    }

}
