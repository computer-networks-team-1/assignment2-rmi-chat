package TCPServer;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class Chat  extends UnicastRemoteObject implements ChatInterface  {


    protected Chat() throws RemoteException {
    }

    protected Chat(int port) throws RemoteException {
        super(port);
    }

    protected Chat(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    @Override
    public void sendMessage(String message) throws Exception {

    }

    @Override
    public void joinChat(String ipAddress, int port, String clientName) throws Exception {

    }

    @Override
    public void leaveChat() throws Exception {

    }
}
