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
        indexChat = chatInterface.joinChat(thisIp, clientName);
        chatMessages = new ArrayList<>();
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
            System.out.println(indexChat);
            System.out.println(chatInterface.getChat(indexChat));
            newMessagePart = chatInterface.getChat(indexChat);
            System.out.println("e' arrivato " + newMessagePart);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        if(newMessagePart != null || newMessagePart.size() > 0) {
//            chatMessages.addAll(newMessagePart);
//            indexChat = chatMessages.size() - 1;
//        } else
//            return "";
//
//        return String.join("\n", chatMessages);

        if(newMessagePart != null && newMessagePart.size() > 0)
            return String.join("\n", newMessagePart);

        return "";
    }

    /**
     * Closes the communication
     */
    public void closeCommunication () throws Exception {
        chatInterface.leaveChat(thisIp);
    }
}
