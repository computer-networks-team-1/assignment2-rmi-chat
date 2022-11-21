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
    public void sendMessage(String message, String clientName) throws RemoteException {
        messages.add(clientName + ": " + message);
        recordThisMessage(getIpOf(clientName), clientName, message);
    }

    @Override
    public void joinChat(String clientName, String ipAddress) throws RemoteException {
        clientsConnected.put(clientName, ipAddress);
        messages.add(clientName + " has joined the chat.");
        recordThisMessage(getIpOf(clientName), clientName + " has joined the chat");
    }

    @Override
    public int getCurrentChatIndex() {
        return messages.size();
    }

    @Override
    public void leaveChat(String clientName) throws RemoteException {
        messages.add(clientName + " has left the chat");
        recordThisMessage(getIpOf(clientName), clientName + " has left the chat");
        removeClient(clientName);
    }

    @Override
    public List<String> getConversationFromOffset(int offset) throws RemoteException {
        return new ArrayList<>(messages.subList(offset, messages.size()));
    }

    /**
     * saves the message in conjunction with the clients IP address and its name
     * in the log file which contains all messages sent so far.
     *
     * @param ipAddress IP address of client
     * @param clientName nickname of client
     * @param message message sent by client
     */
    private void recordThisMessage(String ipAddress, String clientName, String message) {
        String textToLog = getTimeString() + " - " + ipAddress + " --> " + clientName + ": " + message;
        writeToLogFile(textToLog);
    }

    /**
     * saves the message in conjunction with the clients IP address but without its name
     * in the log file which contains all messages sent so far.
     *
     * @param ipAddress IP address of client
     * @param message message sent by client
     */
    private void recordThisMessage(String ipAddress, String message) {
        String textToLog = getTimeString() + " - " + ipAddress + " --> " + message;
        writeToLogFile(textToLog);
    }

    /**
     * Lookup IP address of client by nickname.
     *
     * Note: Currently new clients with the same nickname overwrite previous
     * clients.
     *
     * @param clientName nickname of client
     * @return IP address of client
     */
    private String getIpOf(String clientName) {
        return clientsConnected.get(clientName);
    }

    /**
     * Remove client from internal representation of clients.
     * @param clientName nickname of client
     */
    private void removeClient(String clientName) {
        clientsConnected.remove(clientName);
    }

    /**
     * returns a string representing the current time point.
     *
     * @return current hour, minute and second formatted as single string
     */
    private String getTimeString() {
        LocalDateTime now = LocalDateTime.now();
        return now.getHour() + ":" + now.getMinute() + ":" + now.getSecond();
    }

    /**
     * returns a new logfile with specific fixed path if it did not exist.
     * Otherwise the already existing logfile is returned.
     *
     * @return logfile in which chat messages are stored
     * @throws IOException
     */
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

    /**
     * appends a message to the logfile.
     *
     * @param textToLog message string to be recorded
     */
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

}
