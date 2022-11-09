package TCPClient;
import TCPServer.ChatInterface;
import java.io.IOException;
import java.net.InetAddress;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientConnection {

    ChatInterface a;
    List<String> chatMessages;

    int indexChat;

    public ClientConnection(String clientName, String ipServer, String thisIp) throws Exception {
        a = (ChatInterface) Naming.lookup("rmi://localhost/" + ipServer + "/chat");
        a.joinChat(thisIp, clientName);
        chatMessages = new ArrayList<>();
        indexChat = -1;
    }

    public void sendMessage (String message) {
        try {
            a.sendMessage(message, InetAddress.getLocalHost().toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getMessage () {

        List<String> newMessagePart = null;

        try {
            newMessagePart = a.getChat(indexChat);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if(newMessagePart != null) {
            chatMessages.addAll(newMessagePart);
            indexChat = chatMessages.size()-1;
        }

        String message = String.join("\n", chatMessages);

        return message;
    }

    /**
     * Closes the communication
     */
    public void closeCommunication () throws Exception {
        a.leaveChat();
    }
}
