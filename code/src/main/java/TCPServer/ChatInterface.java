package TCPServer;

import java.rmi.Remote;

public interface ChatInterface extends Remote {

    public void sendMessage(String message) throws Exception;

    public void joinChat(String ipAddress, int port, String clientName) throws Exception;

    public void leaveChat() throws Exception;

}
