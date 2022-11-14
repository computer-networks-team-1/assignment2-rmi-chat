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

        try {
//            System.out.println(chatInterface.getChat(indexChat));
            newMessagePart = chatInterface.getChat(indexChat);
            chatMessages.addAll(newMessagePart);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        if(newMessagePart.size() > 0) {
            indexChat = chatMessages.size() - 1;
            return String.join("\n", newMessagePart);
        }

        return "";
    }

    /**
     * Closes the communication
     */
    public void closeCommunication () throws Exception {
        chatInterface.leaveChat(thisIp);
    }
}
