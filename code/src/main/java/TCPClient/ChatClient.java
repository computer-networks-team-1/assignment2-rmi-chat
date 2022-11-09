package TCPClient;
import TCPServer.ChatInterface;

import java.rmi.Naming;

public class ChatClient {
    public static void main (String[] args) {
        ChatInterface a;
        try {
            a = (ChatInterface) Naming.lookup("rmi://localhost/ABC");

            //here something happens

            System.out.println("Result is: ");

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}