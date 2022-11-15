package RMIClient;
import RMIServer.ChatInterface;
import java.rmi.Naming;
import java.util.List;

public class ClientConnection {

    ChatInterface chatInterface;
    String thisIp;
    String clientName;

    int indexChat;

    public ClientConnection(String clientName, String ipServer, String thisIp) throws Exception {
        this.clientName = clientName;
        this.thisIp = thisIp;
        chatInterface = (ChatInterface) Naming.lookup("rmi://" + ipServer + ":7896/chat");//TODO: make port not hard coded
        chatInterface.joinChat(clientName, thisIp);
        indexChat = chatInterface.getCurrentChatIndex();
    }

    public void sendMessage (String message) {
        try {
            chatInterface.sendMessage(message, clientName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getMessage () {

        try {
            List<String> newMessagePart = chatInterface.getConversationFromOffset(indexChat);
            if(newMessagePart.size() > 0) {
                indexChat = indexChat + newMessagePart.size();
                return String.join("\n", newMessagePart);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    public void closeCommunication () throws Exception {
        chatInterface.leaveChat(clientName);
    }
}
