package tictactoe;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.Scanner;

public class Peer 
{
    public static void main(String args[])
    {
        String myIP, myPort, otherPlayerIP;
        try 
        {
            
            System.out.print("Insira o IP que deseja usar:");
            Scanner scanner = new Scanner(System.in);
            myIP = scanner.nextLine();
            System.out.print("Insira a porta que deseja usar:");
            myPort = scanner.nextLine();
            SharedData sd =  new SharedData(myIP+":"+myPort);
            CommunicatingServerInterface csi = new CommunicatingServer(sd);
            //preparação do server
            LocateRegistry.createRegistry(Integer.parseInt(myPort));//porta
            Naming.rebind("rmi://"+ myIP + ":" +myPort +"/peer", csi);
            TicTacToe ttt = new TicTacToe(sd, csi);
            
            ttt.startProgram();
        } 
        catch (RemoteException | MalformedURLException e) 
        {
            System.out.println("ERRO: não foi possível comunicar ao servidor! Terminando o programa...");
            System.exit(0);
        }
    }
}
