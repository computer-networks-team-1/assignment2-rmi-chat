package TCPServer;

import java.rmi.Remote;
import java.util.List;

public interface ChatInterface extends Remote {

    public void sendMessage(String message) throws Exception;

    public void joinChat(String ipAddress, int port, String clientName) throws Exception;

    public void leaveChat() throws Exception;

    public List<String> getChat(int index) throws Exception;

}
