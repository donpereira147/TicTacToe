package tictactoe;

public class TicTacToeUtils 
{
    public static void printMenu()
    {
        System.out.println("+++++++++Menu+++++++++");
        System.out.println("1-Começar jogo");
        System.out.println("2-Receber convite");
        System.out.println("3-Sair");
        System.out.print("Opção desejada:");
    }
    
    
    public static void transformStringToBoardOutput(String board)
    {
        System.out.println("    a   b   c");
        System.out.println("1   " + board.substring(0,1) + " | " + board.substring(1,2) + " | " + board.substring(2,3));
        System.out.println("   -----------");
        System.out.println("2   " + board.substring(3,4) + " | " + board.substring(4,5) + " | " + board.substring(5,6));
        System.out.println("   -----------");
        System.out.println("3   " + board.substring(6,7) + " | " + board.substring(7,8) + " | " + board.substring(8,9));
    }
    
    public static String associateTypeToBoardState(String board, String chosenSymbol)
    {
        String type = "n";
        
        String otherSymbol;
        
        if(chosenSymbol.equals("x"))
            otherSymbol = "o";
        else
            otherSymbol = "x";
        
        if(board.substring(0,1).equals(chosenSymbol) && board.substring(1,2).equals(chosenSymbol) && board.substring(2,3).equals(chosenSymbol))
            type = "w";
        else if(board.substring(3,4).equals(chosenSymbol) && board.substring(4,5).equals(chosenSymbol) && board.substring(5,6).equals(chosenSymbol))
            type = "w";
        else if(board.substring(6,7).equals(chosenSymbol) && board.substring(7,8).equals(chosenSymbol) && board.substring(8,9).equals(chosenSymbol))
            type = "w";
        else if(board.substring(0,1).equals(chosenSymbol) && board.substring(3,4).equals(chosenSymbol) && board.substring(6,7).equals(chosenSymbol))
            type = "w";
        else if(board.substring(1,2).equals(chosenSymbol) && board.substring(4,5).equals(chosenSymbol) && board.substring(7,8).equals(chosenSymbol))
            type = "w";
        else if(board.substring(2,3).equals(chosenSymbol) && board.substring(5,6).equals(chosenSymbol) && board.substring(8,9).equals(chosenSymbol))
            type = "w";
        else if(board.substring(0,1).equals(chosenSymbol) && board.substring(4,5).equals(chosenSymbol) && board.substring(8,9).equals(chosenSymbol))
            type = "w";
        else if(board.substring(2,3).equals(chosenSymbol) && board.substring(4,5).equals(chosenSymbol) && board.substring(6,7).equals(chosenSymbol))
            type = "w";
        
        
        if (type.equals("n"))
        {
            if(board.substring(0,1).equals(otherSymbol) && board.substring(1,2).equals(otherSymbol) && board.substring(2,3).equals(otherSymbol))
                type = "l";
            else if(board.substring(3,4).equals(otherSymbol) && board.substring(4,5).equals(otherSymbol) && board.substring(5,6).equals(otherSymbol))
                type = "l";
            else if(board.substring(6,7).equals(otherSymbol) && board.substring(7,8).equals(otherSymbol) && board.substring(8,9).equals(otherSymbol))
                type = "l";
            else if(board.substring(0,1).equals(otherSymbol) && board.substring(3,4).equals(otherSymbol) && board.substring(6,7).equals(otherSymbol))
                type = "l";
            else if(board.substring(1,2).equals(otherSymbol) && board.substring(4,5).equals(otherSymbol) && board.substring(7,8).equals(otherSymbol))
                type = "l";
            else if(board.substring(2,3).equals(otherSymbol) && board.substring(5,6).equals(otherSymbol) && board.substring(8,9).equals(otherSymbol))
                type = "l";
            else if(board.substring(0,1).equals(otherSymbol) && board.substring(4,5).equals(otherSymbol) && board.substring(8,9).equals(otherSymbol))
                type = "l";
            else if(board.substring(2,3).equals(otherSymbol) && board.substring(4,5).equals(otherSymbol) && board.substring(6,7).equals(otherSymbol))
                type = "l";
        }
        if(type.equals("n") && !board.contains(" "))
            type = "d";
        
        return type;
    }
 
    public static String applyMoveToBoard(String board, String move, String chosenSymbol)
    {
        StringBuilder sb = new StringBuilder(board);
        switch(move)
        {
            case "a1": sb.setCharAt(0, chosenSymbol.charAt(0));
                break;
            case "a2": sb.setCharAt(3, chosenSymbol.charAt(0));
                break;
            case "a3": sb.setCharAt(6, chosenSymbol.charAt(0));
                break;
            case "b1": sb.setCharAt(1, chosenSymbol.charAt(0));
                break;
            case "b2": sb.setCharAt(4, chosenSymbol.charAt(0));
                break;
            case "b3": sb.setCharAt(7, chosenSymbol.charAt(0)); 
                break;
            case "c1": sb.setCharAt(2, chosenSymbol.charAt(0)); 
                break;
            case "c2": sb.setCharAt(5, chosenSymbol.charAt(0)); 
                break;
            case "c3": sb.setCharAt(8, chosenSymbol.charAt(0));
                break;
        }
        return sb.toString();
     
    }
}
