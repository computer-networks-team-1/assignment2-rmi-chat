package TCPServer;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RMIServer {
    public static void main(String[] args) throws RemoteException {
        try {
            LocateRegistry.createRegistry(7896);
            Chat chat = new Chat();
            Naming.rebind("rmi://localhost/127.0.0.1/chat", chat);
            System.out.println("RMI Server is ready");
        } catch(Exception e){
            System.out.println("RMI Server failed: " + e);
            e.printStackTrace();
        }
    }
}
