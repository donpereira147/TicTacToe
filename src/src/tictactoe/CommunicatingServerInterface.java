package tictactoe;

import java.rmi.*;

public interface CommunicatingServerInterface extends Remote
{
    void sendInvite(String ipSender, String otherIP, String msgType) throws RemoteException;
    String receiveInvite(String ipSender, String ipReceiver, String msgType) throws RemoteException;
    String makeMove(String ipSender, String ipReceiver, String board) throws RemoteException;
}
