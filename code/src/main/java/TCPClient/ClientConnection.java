package TCPClient;
import TCPServer.ChatInterface;
import java.io.IOException;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientConnection {

    ChatInterface a;
    List<String> chatMessages;

    int indexChat;

    public ClientConnection(String clientName, String ipServer, int portServer) throws Exception {
        a = (ChatInterface) Naming.lookup("rmi://localhost/ABC");
        a.joinChat(ipServer, portServer, clientName);
        chatMessages = new ArrayList<>();
        indexChat = 0;
    }

    public void sendMessage (String message) {
        try {
            a.sendMessage(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getMessage () {

        try {
            chatMessages = a.getChat(indexChat);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String message = String.join("\n", chatMessages);
        indexChat = chatMessages.size();

        return message;
    }

    /**
     * Closes the communication
     */
    public void closeCommunication () throws Exception {
        a.leaveChat();
    }
}
