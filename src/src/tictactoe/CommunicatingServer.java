package tictactoe;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import static tictactoe.TicTacToeUtils.printMenu;
import static tictactoe.TicTacToeUtils.applyMoveToBoard;
import static tictactoe.TicTacToeUtils.associateTypeToBoardState;
import static tictactoe.TicTacToeUtils.transformStringToBoardOutput;

public class CommunicatingServer extends UnicastRemoteObject implements CommunicatingServerInterface
{
    private final SharedData shared;
    Scanner scanner;
    
    public CommunicatingServer(SharedData sd) throws RemoteException
    {
        super();
        shared = sd;
        scanner =  new Scanner(System.in);
    }
    
    private void initNewPeer(String ip) throws Exception
    {
           shared.setCsi((CommunicatingServerInterface)Naming.lookup("rmi://" + ip +"/peer"));   
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
                sendInvite(shared.getPortNumber(), auxString,"i");
                //sendIvite
                shared.setState(StateEnum.STATE.ecc);
                
                
            } catch (RemoteException ex) {
                ex.printStackTrace();
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
    
    
    @Override
    public String makeMove(String ipSender, String ipReceiver, String board) throws RemoteException
    {
        String type;
        if(ipSender.equals(shared.getOtherPlayerPort()) && ipReceiver.equals(shared.getPortNumber()) /*&& shared.getState() == StateEnum.STATE.erj*/)
        {
            determineSymbol(board);
            transformStringToBoardOutput(board);
            type = associateTypeToBoardState(board, shared.getChosenSymbol());
            switch (type) 
            {
                case "n":
                    //verificar se a jogada é válida
                    while(!checkIfPlayIsValid(board)){}
                    //aplicar a jogada
                    board = applyMoveToBoard(board, shared.getCurrentPlay(), shared.getChosenSymbol());
                    transformStringToBoardOutput(board);
                    //verificar se o jogo acabou
                    type = associateTypeToBoardState(board, shared.getChosenSymbol());
                    switch (type)
                    {
                        case "w":
                            System.out.println("Você ganhou o jogo!\nFeche o programa para jogar novamente!");
                            return board;
                        case "l":
                            System.out.println("Você perdeu o jogo!\nFeche o programa para jogar novamente!");
                            return board;
                        case "d":
                            System.out.println("O jogo terminou empatado!\nFeche o programa para jogar novamente!");
                            break;
                        default: 
                            System.out.println("Aguardando jogada do parceiro...");
                            break;
                    }
                    break;
                case "w":
                    System.out.println("->Você venceu o jogo!\nFeche o programa para jogar novamente!");
                    return board;
                case "l":
                    System.out.println("->Você perdeu o jogo!\nFeche o programa para jogar novamente!");
                    break;
                case "d":
                    System.out.println("->O jogo terminou empatado!\nFeche o programa para jogar novamente!");
                    break;
                default:
                    break;
            }
        }
        return board;
        
    }
    
    @Override
    public void sendInvite(String ipSender, String otherIP,  String msgType) throws RemoteException
    {
        shared.setOtherPlayerPort(otherIP);
        
        boolean accepted = true;
        try 
        {
            initNewPeer(shared.getOtherPlayerPort());

        } catch (Exception ex) 
        {
            System.out.println("ERRO: Comunicação indisponível, pois parceiro não existe!");
            startProgram();
            accepted = false;
        }
        if(accepted)
        {
            System.out.println("Convite enviado com sucesso, aguardando resposta...");
            String result = shared.getCsi().receiveInvite(ipSender, otherIP, msgType);
            switch (result) 
            {
                case "s":
                    gameStart();
                    break;
                case "n":
                    System.out.println("ERRO: Convite rejeitado|!");
                    startProgram();
                    break;
                default:
                    System.out.println("ERRO: Jogador " + otherIP + " não está disponível para receber convites.");
                    startProgram();
                    //se calhar, caso não haja confirmação em 5s, regressar ao menu principal
                    break;
            }
        }
        
    }
    
    @Override
    public String receiveInvite(String ipSender, String ipReceiver, String msgType) throws RemoteException
    {
        if(shared.getState() != StateEnum.STATE.rc)
        {
            return "u";//unavailable to receive invites
        }
        else
        {
            System.out.print("\nFoi recebida uma mensagem do utilizador "+ ipSender +" que deseja começar um jogo consigo.\nQuer aceitar o convite? s para aceitar, n para rejeitar:");
            scanner = new Scanner(System.in);
            String answer = scanner.nextLine();
            switch (answer) 
            {
                case "n":
                    return "n";//not accepted
                    
                case "s":
                    shared.setOtherPlayerPort(ipSender);
                    System.out.println("Esperando pela primeira jogada do parceiro...");
                    return "s";//sim accepted
                    
                    
                default:
                    System.out.println("Resposta inválida!");
//                    receiveInvite(ipSender, ipReceiver, msgType);
                    break;
            }
        } 
        return "n";
    }
    
    private void gameStart()
    {
        boolean over = false;
        //escolher o símbolo
        System.out.print("Escreva o símbolo que deseja usar (x ou o):");
        String symbol = scanner.nextLine();
        if(!symbol.equals("o") && !symbol.equals("x"))
        {
            while(!symbol.equals("o") && !symbol.equals("x"))
            {
                System.out.print("Símbolo inválido. Escreva x ou o!");
                symbol = scanner.nextLine();
            }
        }
        shared.setChosenSymbol(symbol);
        
        //apresentar o tabuleiro
        String board = "         "; //tabuleiro vazio
        //transformStringToBoardOutput(board);
        
        try 
        {
            while(!over)
            {
                board = makeMove(shared.getOtherPlayerPort(), shared.getPortNumber(), board);
                if(!associateTypeToBoardState(board, shared.getChosenSymbol()).equals("n"))
                    over = true; //n->normal
                board = shared.getCsi().makeMove(shared.getPortNumber(), shared.getOtherPlayerPort(), board);
                if(!associateTypeToBoardState(board, shared.getChosenSymbol()).equals("n"))
                    over = true; //n->normal
            }  
        } catch (Exception ex) 
        {
            ex.printStackTrace();
        }
        shared.setState(StateEnum.STATE.erj);
    }
 
    private boolean checkIfPlayIsValid(String board)
    {
        boolean isValid = true;
        System.out.print("\n\nEm que posição deseja jogar? Para jogar, insira a letra seguida do número:");
        String play = scanner.nextLine();
                
        String letter = play.substring(0,1);
        String number = play.substring(1, 2);
        
        //verificar se usa o formato correto
        if(!((letter.equals("a") || letter.equals("b") || letter.equals("c")) 
                        && ( number.equals("1") || number.equals("2") || number.equals("3"))))
        {
            System.out.println("ERRO: Parâmetro inválido!");
            return false;
        }
        else
        {
            //verificar se usa a casa certa
            switch(play)
            {
                case "a1": if(!board.substring(0,1).equals(" "))
                                isValid = false;
                    break;
                case "a2": if(!board.substring(3,4).equals(" "))
                                isValid = false;
                    break;
                case "a3": if(!board.substring(6,7).equals(" "))
                                isValid = false;
                    break;
                case "b1": if(!board.substring(1,2).equals(" "))
                                isValid = false;
                    break;
                case "b2": if(!board.substring(4,5).equals(" "))
                                isValid = false;
                    break;
                case "b3": if(!board.substring(7,8).equals(" "))
                                isValid = false;
                    break;
                case "c1": if(!board.substring(2,3).equals(" "))
                                isValid = false;
                    break;
                case "c2": if(!board.substring(5,6).equals(" "))
                                isValid = false;
                    break;
                case "c3": if(!board.substring(8,9).equals(" "))
                                isValid = false;
                    break;
            }
        }
        
        if(isValid)
            shared.setCurrentPlay(play);
        else
            System.out.println("ERRO: Casa já foi escolhida!");
        
        return isValid;
    }
    
    private void determineSymbol(String board)
    {
        int countX = board.length() - board.replace("x", "").length();
        int countO = board.length() - board.replace("o", "").length();
        
        if(countX == 1 && countO == 0)
            shared.setChosenSymbol("o");
        else if(countO == 1 && countX == 0)
            shared.setChosenSymbol("x");
    }
            
}
