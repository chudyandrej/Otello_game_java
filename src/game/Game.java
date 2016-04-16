package game;

/**
 * Created by andrejchudy on 15/04/16.
 */
public class Game {

    private Player currentPlayer;
    private Player black;
    private Player white;
    private ReversiRules rules;

    public Game(int size){
        rules = new ReversiRules(size);

    }

    public boolean addPlayer(boolean isWhite){
        Player newPlayer = new Player(rules,isWhite);

        if(currentPlayer == null){
            currentPlayer = newPlayer;
        }
        if(newPlayer.isWhite() && white == null){
            white = newPlayer;
            return true;

        }else if (!newPlayer.isWhite() && black == null){
            black = newPlayer;
            return true;
        }

        return false;
    }

    public Player currentPlayer(){
        return currentPlayer;
    }

    public Player nextPlayer(){
        currentPlayer = (currentPlayer == black) ? white : black;
        return currentPlayer;
    }

}
