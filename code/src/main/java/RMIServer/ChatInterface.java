package RMIServer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatInterface extends Remote {

    /**
     * takes the string sent by the client, formats it for display and
     * records the message for later retrieval by clients.
     *
     * @param message string sent by chat client to server
     * @param clientName the nickname chosen by the user
     * @throws RemoteException if there is any communication-related exception during remote method call
     */
    void sendMessage(String message, String clientName) throws RemoteException;


    /**
     * stores the clients name and IP address, broadcasts a message to
     * announce that the client has joined the chat and records the message.
     *
     * @param clientName nickname chosen by user
     * @param ipAddress clients IP address as string
     * @throws RemoteException if there is any communication-related exception during remote method call
     */
    void joinChat(String clientName, String ipAddress) throws RemoteException;

    /**
     * gets the current size of the chat log. Index is used to retrieve any
     * new messages.
     *
     * @return size of the current chat log
     * @throws RemoteException if there is any communication-related exception during remote method call
     */
    int getCurrentChatIndex() throws RemoteException;

    /**
     * removes the clients data from internal representation of clients on server,
     * broadcasts and records a message to announce that client has left the chat.
     *
     * @param clientName nickname chosen by the user
     * @throws RemoteException if there is any communication-related exception during remote method call
     */
    void leaveChat(String clientName) throws RemoteException;

    /**
     * @param offset size of chat log already known to client.
     *
     * @return sublist holding unseen messages
     * @throws RemoteException if there is any communication-related exception during remote method call
     */
    List<String> getConversationFromOffset(int offset) throws RemoteException;

}
