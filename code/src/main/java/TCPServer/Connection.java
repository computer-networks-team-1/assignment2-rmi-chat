package TCPServer;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;

/**
 * Thread that is used for each connection
 */
public class Connection extends Thread {

    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    private String clientName;

    public Connection (Socket aClientSocket) {

        try {
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream()); //inward connection
            out = new DataOutputStream( clientSocket.getOutputStream()); //outward connection
            this.start(); // it executes the run()
        }
        catch(IOException e)
        {System.out.println("Connection: "+e.getMessage());}

    }

    /**
     * Checks if the client name is available and returns it
     * @return client name
     */
    public String getClientName(){
        if (clientName == null || clientName.isEmpty())
            throw new IllegalStateException("No nickname set for: " + clientSocket.getInetAddress().toString());
        else
            return this.clientName;

    }

    /**
     * It broadcasts a message sent by a client to the other connected clients
     * after having called the method to save it in the log file
     *
     * @param ipAddress IP Address of the client who sent the message
     * @param clientName Name of the client who sent the message
     * @param message Message sent by the client
     */
    private void broadCast(String ipAddress, String clientName, String message){
        recordThisMessage(ipAddress, clientName, message);
        TCPServer.clientsConnected.forEach((Connection c)-> {
            try {
                c.out.writeUTF(getClientName() + ": " + message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * It manages the connection with the client by getting its messages and checking if it has left the chat (in that
     * case it broadcasts its disconnection and removes it from the clients connected)
     */
    public void run(){
        try {
            clientName = in.readUTF(); //First message of the client is its name

            String clientSocketIp = clientSocket.getInetAddress().toString();
            broadCast(clientSocketIp, clientName, "Hey there, I joined the chat.");

            while(true) {
                String data = in.readUTF();
                broadCast(clientSocketIp, clientName, data);
            }
        }
        catch(EOFException e) { //Client has disconnected. Either closed window or typed /quit message
            TCPServer.clientsConnected.remove(this);
            broadCast(
                    this.clientSocket.getInetAddress().toString(),
                    this.getClientName(),
                    "Has left the chat."
            );
        }
        catch(IOException e) {System.out.println("IO:s a"+e.getMessage());
        } finally {
            try {clientSocket.close(); }
            catch (IOException e) {
                /*close failed*/
                e.printStackTrace();
            }
        }
    }


    /**
     * It has the job to record every message sent on the connection between the client and the server. It therefore
     * saves it in the file, appending the new message, this file is resources/logServer.txt  and it has everything
     * that happened in the chat, at what time and from which ip address
     *
     * @param ipAddress IP Address of the client who sent the message
     * @param clientName Name of the client who sent the message
     * @param message Message sent by the client
     */
    public void recordThisMessage(String ipAddress, String clientName, String message)
    {
        LocalDateTime now = LocalDateTime.now();
        String time =now.getHour() + ":" + now.getMinute() + ":" + now.getSecond();
        String textToLog;

        if (message.endsWith("left the chat."))
            textToLog = time +" - " + ipAddress.substring(1) + " --> " + message;
        else
            textToLog = time + " - " + ipAddress.substring(1) + " --> " + clientName + ": " + message;

        try {
            String fileName = "src\\main\\resources\\logServer.txt";
            File serverFile = new File(fileName);
            if (serverFile.createNewFile()) {
                System.out.println("Logfile created: " + serverFile.getName());
            } else {
                System.out.println("Logfile already exists");
            }

            FileWriter fw = new FileWriter(serverFile, true);
            fw.append(textToLog);
            fw.append('\n');
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
