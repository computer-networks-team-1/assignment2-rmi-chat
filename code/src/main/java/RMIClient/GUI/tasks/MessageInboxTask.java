package RMIClient.GUI.tasks;

import RMIClient.GUI.GUIRunner;
import RMIClient.GUI.views.MainView;
import javafx.concurrent.Task;

/**
 * It represents the UI thread that checks if the user has received new messages from the server updating its UI
 */
public class MessageInboxTask extends Task<Void> {

    private final int REFRESH_INTERVAL = 1;


    /**
     * It is called by the Task when the thread that contains this task is started.
     * Every 1ms it checks if the server has sent new messages to this client. If so, it updates its view
     * of messages by calling updateProgress
     *
     * @return Void placeholder for void.
     * @throws Exception if the thread was stopped.
     */
    @Override
    protected Void call() throws Exception {
        while (true) {
            Thread.sleep(REFRESH_INTERVAL);
            String message = MainView.clientConnection.getMessage();

            if(!message.equals(""))
                updateProgress(message);
        }
    }

    /**
     * It is called by the Task when there is a new message from the server. It updates the view of messages
     *
     * @param v message to add to the view
     */
    protected void updateProgress(String v) {
        updateMessage(GUIRunner.mainView.chatCanvas.getChat() + "\n" + v);
        super.updateProgress(0,0); //standard method to be called in this type of threads
    }

}
