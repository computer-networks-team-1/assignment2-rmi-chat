package TCPClient;
import TCPServer.ChatInterface;
import java.io.IOException;
import java.rmi.Naming;

public class ClientConnection {

    ChatInterface a;

    public ClientConnection(String clientName, String ipServer, int portServer) throws Exception {
        a = (ChatInterface) Naming.lookup("rmi://localhost/ABC");
        a.joinChat(ipServer, portServer, clientName);
    }

    public void sendMessage (String message) {
        try {
            a.sendMessage(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getMessage () {
        /*
        String message = "";
        try {
            message = in.readUTF();
        } catch (IOException e) {
            System.out.println("IO: "+e.getMessage());
        } */
        return null;
    }

    /**
     * Closes the communication
     */
    public void closeCommunication () throws Exception {
        a.leaveChat();
    }
}
