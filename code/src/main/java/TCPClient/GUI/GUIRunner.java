package TCPClient.GUI;

import TCPClient.GUI.views.MainView;
import com.sun.tools.javac.Main;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GUIRunner {

    public static MainView mainView;

    private GUIRunner() {}

    /**
     * Initializes JavaFX scene
     *
     * @param primaryStage JavaFX primary stage.
     * @throws IOException when graphic.fxml is not found.
     */
    public static void run(Stage primaryStage) throws IOException {

        primaryStage.setTitle("TCPClient - Online Chat");

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
    public static void stop() {
        if(MainView.clientConnection != null)
            MainView.clientConnection.closeCommunication();
        Platform.exit();
    }

}
