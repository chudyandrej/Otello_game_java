package game;


import GUI.BoardGUI;
import board.Board;
import board.Disk;

import java.util.concurrent.TimeUnit;

/**
 * Created by andrejchudy on 15/04/16.
 */
public class Game {

    private Player currentPlayer;
    private Player black;
    private Player white;
    static public ReversiRules rules;

    public Game(int size){
        rules = new ReversiRules(size);

    }

    public boolean addPlayer(Player newPlayer){

        if(newPlayer.isWhite() && white == null){
            white = newPlayer;
            currentPlayer = newPlayer;
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
        changeScore();
        currentPlayer = (currentPlayer == black) ? white : black;
        if (currentPlayer.is_pc() &&  exitsingTurn(currentPlayer)) {
            currentPlayer.uiTurn(this);


            return null;

        }
        else if(exitsingTurn(currentPlayer)) {
            System.out.printf("Turn PC!!!!\n");
            return currentPlayer;
        }
        else if (!exitsingTurn(white) && !exitsingTurn(black)){
            System.out.printf("Game end!!!!\n");
            return white; ///// !!!!!!Game Over
        }
        currentPlayer = (currentPlayer == black) ? white : black;
        System.out.printf("Skip turn!!!!\n");
        return currentPlayer;

    }

    private void changeScore(){
        int white_Disk = 0;
        int blac_Dsik = 0;
        int size = Game.rules.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Disk tmp = Board.field[i][j].getDisk();
                if (tmp == null){
                    continue;
                }
                else if(tmp.isWhite()){ white_Disk++; }
                else if(!tmp.isWhite()){blac_Dsik++; }
            }
        }
        BoardGUI.setGameState(blac_Dsik, white_Disk, currentPlayer.isWhite());
    }

    public boolean exitsingTurn(Player player_on_turn) {
        int size = Game.rules.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (player_on_turn.canPutDisk(i,j)){
                    return true;
                }
            }
        }
        return false;
    }



}
