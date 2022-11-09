package TCPClient;


import TCPClient.GUI.GUIRunner;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * It represents the connection of the client. It manages the approach to the server, from creating the socket to provide
 * methods for the communication itself
 */
public class ClientConnection {

    private DataOutputStream out;
    private DataInputStream in;
    private Socket s;

    /**
     * It creates the socket of the user
     *
     * @param clientName nickname used by the client during the communication
     * @param ipServer ip of the server, the user wants to connect to
     * @param portServer port of the server, the user wants to connect to
     */
    public ClientConnection(String clientName, String ipServer, int portServer) throws IOException {

        s = new Socket(ipServer, portServer);

        in = new DataInputStream(s.getInputStream()); //inward connection
        out = new DataOutputStream( s.getOutputStream()); //outward connection

        out.writeUTF(clientName); //First message is clients name
    }

    /**
     *  Allows to send messages on the connection between the user and the server
     * @param message message to send to the server
     */
    public void sendMessage (String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            System.out.println("IO: "+e.getMessage());
        }
    }

    /**
     * Allows to get messages from the connection between the user and the server
     * @return message received by the server
     */
    public String getMessage () {
        String message = "";
        try {
            message = in.readUTF();
        } catch (IOException e) {
            System.out.println("IO: "+e.getMessage());
        }
        return message;
    }

    /**
     * Closes the communication
     */
    public void closeCommunication () {
        if(s != null)
        try {
            s.close();
        } catch (IOException e) {}
        System.out.println("Closed");
    }
}
