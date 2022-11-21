package RMIClient;
import RMIServer.ChatInterface;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

public class ClientConnection {

    ChatInterface chatInterface;
    String thisIp;
    String clientName;

    int indexChat;

    public ClientConnection(String clientName, String ipServer, String thisIp) throws Exception {
        this.clientName = clientName;
        this.thisIp = thisIp;
        chatInterface = (ChatInterface) Naming.lookup("rmi://" + ipServer + ":7896/chat");
        chatInterface.joinChat(clientName, thisIp);
        indexChat = chatInterface.getCurrentChatIndex();
    }

    /**
     * invokes the remote method to send a message.
     *
     * @param message message sent by the user
     */
    public void sendMessage (String message) {
        try {
            chatInterface.sendMessage(message, clientName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * retrieves new messages that were recorded since last invocation and
     * updates index to the size of the updated chat.
     *
     * @return sublist of new messages added since last invocation
     */
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

    /**
     * invokes the remote method to leave the chat.
     *
     * @throws RemoteException if any exception occurs during remote method call
     */
    public void closeCommunication () throws RemoteException {
        chatInterface.leaveChat(clientName);
    }
}
