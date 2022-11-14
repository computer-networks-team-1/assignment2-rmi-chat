package TCPServer;

import java.rmi.Remote;
import java.util.List;

public interface ChatInterface extends Remote {

    public void sendMessage(String message, String s) throws Exception;

    public int joinChat(String ipAddress, String clientName) throws Exception;

    public void leaveChat(String ipAddress) throws Exception;

    public List<String> getChat(int index) throws Exception;

}
