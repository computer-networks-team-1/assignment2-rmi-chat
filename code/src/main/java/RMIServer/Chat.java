package RMIServer;

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
    File logFile;

    protected Chat() throws IOException {
        messages = new ArrayList<>();
        clientsConnected = new HashMap<>();
        logFile = getLogFile();
    }

    protected Chat(int port) throws RemoteException {
        super(port);
    }

    protected Chat(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    @Override
    public void sendMessage(String message, String clientName) throws Exception {
        messages.add(clientName + ": " + message);
        recordThisMessage(getIpOf(clientName), clientName, message);
    }

    @Override
    public void joinChat(String clientName, String ipAddress) throws Exception {
        clientsConnected.put(clientName, ipAddress);
        messages.add(clientName + " has joined the chat.");
        recordThisMessage(getIpOf(clientName), clientName + " has joined the chat");
    }

    @Override
    public int getCurrentChatIndex() {
        return messages.size();
    }

    @Override
    public void leaveChat(String clientName) throws Exception {
        messages.add(clientName + " has left the chat");
        recordThisMessage(getIpOf(clientName), clientName + " has left the chat");
        removeClient(clientName);
    }

    @Override
    public List<String> getConversationFromOffset(int offset) throws Exception {
        return new ArrayList<>(messages.subList(offset, messages.size()));
    }

    public void recordThisMessage(String ipAddress, String clientName, String message) {
        String textToLog = getTimeString() + " - " + ipAddress + " --> " + clientName + ": " + message;
        writeToLogFile(textToLog);
    }

    public void recordThisMessage(String ipAddress, String message) {
        String textToLog = getTimeString() + " - " + ipAddress + " --> " + message;
        writeToLogFile(textToLog);
    }

    private String getIpOf(String clientName) {
        return clientsConnected.get(clientName);
    }

    private void removeClient(String clientName) {
        clientsConnected.remove(clientName);
    }

    private String getTimeString() {
        LocalDateTime now = LocalDateTime.now();
        return now.getHour() + ":" + now.getMinute() + ":" + now.getSecond();
    }

    private void writeToLogFile(String textToLog) {
        try {
            FileWriter fw = new FileWriter(logFile, true);
            fw.append(textToLog);
            fw.append('\n');
            fw.close();
        } catch (IOException e) {
            System.out.println("Could not write to logFile.");
            e.printStackTrace();
        }
    }

    private File getLogFile() throws IOException {
        String fileName = "src\\main\\resources\\logServer.txt";
        File logFile = new File(fileName);
        if (logFile.createNewFile()) {
            System.out.println("Logfile created: " + logFile.getName());
        } else {
            System.out.println("Logfile already exists");
        }
        return logFile;
    }
}
