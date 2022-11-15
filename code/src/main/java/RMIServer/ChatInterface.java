package RMIServer;

import java.rmi.Remote;
import java.util.List;

public interface ChatInterface extends Remote {

    void sendMessage(String message, String clientName) throws Exception;

    void joinChat(String clientName, String ipAddress) throws Exception;

    int getCurrentChatIndex() throws Exception;

    void leaveChat(String clientName) throws Exception;

    List<String> getConversationFromOffset(int offset) throws Exception;

}
