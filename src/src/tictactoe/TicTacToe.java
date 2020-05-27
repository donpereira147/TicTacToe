package tictactoe;


import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TicTacToe 
{
    private SharedData shared;
    private Scanner scanner; 
    private CommunicatingServerInterface csi;
    
    public TicTacToe(SharedData sd, CommunicatingServerInterface csi)
    {
        shared = sd;
        scanner = new Scanner(System.in);
        this.csi = csi;
    }
    
    private void printMenu()
    {
        System.out.println("+++++++++Menu+++++++++");
        System.out.println("1-Começar jogo");
        System.out.println("2-Receber convite");
        System.out.println("3-Sair");
        System.out.print("Opção desejada:");
    }
    
    public void startProgram()
    {
        printMenu();
        String inputString = scanner.nextLine();
        switch (inputString) {
            case "1":
                String auxString;
                System.out.print("Insira o IP do jogador com o qual deseja comunicar:");
                auxString = scanner.nextLine();
        {
            try {
                csi.sendInvite(shared.getPortNumber(), auxString,"i");
                //sendIvite
                shared.setState(StateEnum.STATE.ecc);
                
                
            } catch (RemoteException ex) {
                Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                
                break;
            case "2":
                shared.setState(StateEnum.STATE.rc);
               // receiveInvite(myIP, otherPlayerIP, "r");
                break;
            case "3":
                System.out.println("O programa foi encerrado.");
                System.exit(0);
            default:
                System.out.println("Opcção inválida! Insira uma das opções válidas: 1, 2, ou 3!");
                printMenu();
                break;
        }
    }
}
