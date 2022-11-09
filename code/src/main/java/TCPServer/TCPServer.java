package TCPServer;

import java.net.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TCPServer {

    /**
     * Contains a list of the clients connected
     */
    public static List<Connection> clientsConnected;

    /**
     * Manages the handshake with the clients that want to join the chat. Then it redirect their connection on
     * another thread
     *
     * @param args
     * @throws IOException
     * @throws URISyntaxException
     */
    public static void main (String args[]) throws IOException, URISyntaxException {

        clientsConnected = new ArrayList<Connection>();

        try{

            int serverPort = 7896;
            ServerSocket listenSocket = new ServerSocket(serverPort);

            System.out.println("Server is running on port " + serverPort);

            while(true) { // it keeps listening for requests
                Socket clientSocket = listenSocket.accept();  // it receives request from the client and accepts it
                // --> handshake
                Connection c = new Connection(clientSocket); // it establishes a connection with the client
                clientsConnected.add(c);
            }

        } catch(IOException e) {System.out.println("Listen: " + e.getMessage());}
    }
}

