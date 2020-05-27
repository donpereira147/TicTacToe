package tictactoe;

public class SharedData 
{
    private final String portNumber;
    private String otherPlayerPort, chosenSymbol, currentPlay;
    private CommunicatingServerInterface csi;
    private StateEnum.STATE state = StateEnum.STATE.l;

    public SharedData(String portNumber, String otherPlayerPort, String chosenSymbol, String currentPlay, CommunicatingServerInterface csi) 
    {
        this.portNumber = portNumber;
        this.otherPlayerPort = otherPlayerPort;
        this.chosenSymbol = chosenSymbol;
        this.currentPlay = currentPlay;
        this.csi = csi;
    }

    public SharedData(String portNumber) 
    {
        this.portNumber = portNumber;
    }

    
    public synchronized String getPortNumber() {
        return portNumber;
    }

    public synchronized String getOtherPlayerPort() {
        return otherPlayerPort;
    }

    public synchronized String getChosenSymbol() {
        return chosenSymbol;
    }

    public synchronized String getCurrentPlay() {
        return currentPlay;
    }

    public synchronized CommunicatingServerInterface getCsi() {
        return csi;
    }

    public synchronized StateEnum.STATE getState() {
        return state;
    }

    public synchronized void setOtherPlayerPort(String otherPlayerPort) {
        this.otherPlayerPort = otherPlayerPort;
    }

    public synchronized void setChosenSymbol(String chosenSymbol) {
        this.chosenSymbol = chosenSymbol;
    }

    public synchronized void setCurrentPlay(String currentPlay) {
        this.currentPlay = currentPlay;
    }

    public synchronized void setCsi(CommunicatingServerInterface csi) {
        this.csi = csi;
    }

    public synchronized void setState(StateEnum.STATE state) {
        this.state = state;
    }
    
    
    
}
