package TCPServer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chat  extends UnicastRemoteObject implements ChatInterface  {

    private List<String> messages;
    private Map<String, String> clientsConnected;

    protected Chat() throws RemoteException {
        messages = new ArrayList<>();
        clientsConnected = new HashMap<>();
    }

    protected Chat(int port) throws RemoteException {
        super(port);
    }

    protected Chat(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    @Override
    public void sendMessage(String message, String ipClient) throws Exception {
        recordThisMessage(ipClient, clientsConnected.get(ipClient), message);
    }

    @Override
    public void joinChat(String ipAddress, String clientName) throws Exception {
        clientsConnected.put(ipAddress, clientName);
    }

    @Override
    public void leaveChat(String ipAddress) throws Exception {
        clientsConnected.remove(ipAddress);
    }

    @Override
    public List<String> getChat(int index) throws Exception {
        return messages.subList(index+1, messages.size());
    }

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

            messages.add(clientName + ": " + message);

            FileWriter fw = new FileWriter(serverFile, true);
            fw.append(textToLog);
            fw.append('\n');
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
