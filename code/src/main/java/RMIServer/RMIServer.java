package RMIServer;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RMIServer {

    /**
     * creates a Registry instance that accepts requests on a fixed port,
     * creates a chat instance and binds a name to it.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(7896);
            Chat chat = new Chat();
            Naming.rebind("rmi://localhost:7896/chat", chat);
            System.out.println("RMI Server is ready");
            while(true) {} //this line keeps the server on until wanted to
        } catch(IOException e){
            System.out.println("RMI Server failed: " + e);
            e.printStackTrace();
        }
    }
}
