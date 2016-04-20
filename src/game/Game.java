package game;


import GUI.BoardGUI;
import board.Board;
import board.BoardField;
import board.Disk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by andrejchudy on 15/04/16.
 */
public class Game {
    private int sizeBoard;
    private Player currentPlayer;
    private Player black;
    private Player white;
    public boolean gameOver;
    public Backup backupGame;
    static public ReversiRules rules;
    AtomicBoolean freezRunning;
    private List<BoardField> frozen;

    public Game(int size){
        backupGame = new Backup();
        backupGame.boardSize = size;
        sizeBoard = size;
        rules = new ReversiRules(size,backupGame);
        gameOver = false;
        currentPlayer = white;
        freezRunning = new AtomicBoolean(false);
        frozen = new ArrayList<BoardField>();

    }

    public boolean addPlayer(Player newPlayer){

        if(newPlayer.isWhite() && white == null){
            white = newPlayer;
            currentPlayer = newPlayer;
            backupGame.player1 = white;
            return true;

        }else if (!newPlayer.isWhite() && black == null){
            black = newPlayer;
            backupGame.player2 = black;
            return true;
        }

        return false;
    }

    public Player currentPlayer(){
        return currentPlayer;
    }

    public void nextPlayer(){
        freezFields(2,10,20);
        changeScore();
        currentPlayer = (currentPlayer == black) ? white : black;
        if (currentPlayer.is_pc()) {

            currentPlayer.uiTurn(this);
            return;

        }
        else if(exitsingTurn(currentPlayer)) {
            return;
        }
        else if (!exitsingTurn(white) && !exitsingTurn(black)){
            changeScore();
            gameOver = true;
            return;
        }
        currentPlayer = (currentPlayer == black) ? white : black;
        if (currentPlayer.is_pc()) {
            currentPlayer.uiTurn(this);
        }




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
                else if(!tmp.isWhite()){ blac_Dsik++; }
            }
        }
        BoardGUI.setGameState(white_Disk, blac_Dsik, currentPlayer.isWhite());
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

    public void undo(){
        gameOver = false;
        Backup.TurnBackUp lastTurn;
        if (backupGame.backupTurns.size() > 0) {
            lastTurn = backupGame.backupTurns.get(backupGame.backupTurns.size() - 1);
            lastTurn.base_Point.deleteDisk();
            rules.turn_disks(lastTurn.turned);
            backupGame.backupTurns.remove(lastTurn);
            currentPlayer = lastTurn.turn_player;
            if (currentPlayer.is_pc()){
                undo();
            }
            changeScore();
        }
    }

    public void freezFields(final int count, final int max_timeFreez, final int max_timeChange){

        for (Iterator<BoardField> iterator = frozen.iterator(); iterator.hasNext();) {
            BoardField field = iterator.next();
            if (field.getfreezEnd()) {
                field.isFreez = false;
                BoardGUI.unFreezeField(field.row,field.col);
                iterator.remove();
            }
        }

        if(!freezRunning.get()) {

            freezRunning.set(true);
            new Thread() {
                public void run() {

                    int timeChange = (int) (Math.random() * max_timeChange);

                    for (int x = 0; x < count; x++) {

                        int randomX = (int) (Math.random() * (sizeBoard - 1));
                        int randomY = (int) (Math.random() * (sizeBoard - 1));
                        Board.field[randomX][randomY].freezField(max_timeFreez);
                        frozen.add(Board.field[randomX][randomY]);
                    }
                    try {
                        TimeUnit.SECONDS.sleep(timeChange);
                    }catch (InterruptedException e) {
                        System.out.println("Exception thrown  :" + e);
                    }
                    freezRunning.set(false);


                }
            }.start();
        }

    }



}