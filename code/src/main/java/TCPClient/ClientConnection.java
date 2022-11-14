package TCPClient;
import TCPServer.ChatInterface;

import java.net.InetAddress;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;

public class ClientConnection {

    ChatInterface chatInterface;
    List<String> chatMessages;
    String thisIp;

    int indexChat;

    public ClientConnection(String clientName, String ipServer, String thisIp) throws Exception {
        this.thisIp = thisIp;
        chatInterface = (ChatInterface) Naming.lookup("rmi://" + ipServer + ":7896/chat");//TODO: make port not hard coded
        chatInterface.joinChat(thisIp, clientName);
        chatMessages = new ArrayList<>();
        indexChat = -1;
    }

    public void sendMessage (String message) {
        try {
            chatInterface.sendMessage(message, InetAddress.getLocalHost().toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getMessage () {

        List<String> newMessagePart = null;

        System.out.println("sono qui");

        try {
            newMessagePart = chatInterface.getChat(indexChat);
            System.out.println("e' arrivato " + newMessagePart);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if(newMessagePart != null) {
            chatMessages.addAll(newMessagePart);
            indexChat = chatMessages.size()-1;
        }

        return String.join("\n", chatMessages);
    }

    /**
     * Closes the communication
     */
    public void closeCommunication () throws Exception {
        chatInterface.leaveChat(thisIp);
    }
}
