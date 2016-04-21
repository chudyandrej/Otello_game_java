package game;


import GUI.BoardGUI;
import board.Board;
import board.BoardField;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Created by andrejchudy on 15/04/16.
 */
public class Game {

    static public ReversiRules rules;
    private int sizeBoard;
    private Player currentPlayer;
    private Player black;
    private Player white;
    public boolean gameOver;
    private Backup backupGame;
    private AtomicBoolean freezRunning;
    private List<BoardField> frozen;

    public Game(int size){
        backupGame = new Backup(size);
        rules = new ReversiRules(size,backupGame);
        sizeBoard = size;

        freezRunning = new AtomicBoolean(false);
        frozen = new ArrayList<BoardField>();
        gameOver = false;
        currentPlayer = white;
    }

    public boolean addPlayer(Player newPlayer){

        if(newPlayer.isWhite() && white == null){
            white = newPlayer;
            currentPlayer = newPlayer;
            backupGame.setPlayer1(white);
            return true;

        }else if (!newPlayer.isWhite() && black == null){
            black = newPlayer;
            backupGame.setPlayer2(black);
            return true;
        }
        return false;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public Backup getBackupGame() {
        return backupGame;
    }

    public void nextPlayer(){

        backupGame.add_FreezdDisks(frozen);
        backupGame.save_BackupRecord();
        rules.calcScore(currentPlayer);
        freezFields(2,10,20);

        currentPlayer = (currentPlayer == black) ? white : black;
        if (currentPlayer.is_pc()) {
            currentPlayer.uiTurn(this);
            return;
        }
        else if(rules.isExitsingTurn(currentPlayer)) {
            return;
        }
        else if (!rules.isExitsingTurn(white) && !rules.isExitsingTurn(black)){
            rules.calcScore(currentPlayer);
            gameOver = true;
            return;
        }
        currentPlayer = (currentPlayer == black) ? white : black;
        if (currentPlayer.is_pc()) {
            currentPlayer.uiTurn(this);
        }
    }

    public void undo(){

        unFreezAll();
        gameOver = false;
        Backup.TurnBackUp lastTurn;
        if (backupGame.backupTurns.size() > 0) {
            lastTurn = backupGame.backupTurns.get(backupGame.backupTurns.size() - 1);
            lastTurn.base_Point.deleteDisk();
            rules.turn_disks(lastTurn.turned);
            loadFrezed(lastTurn.freezed);
            backupGame.backupTurns.remove(lastTurn);
            currentPlayer = lastTurn.turn_player;

            if (currentPlayer.is_pc()){
                undo();
            }
            rules.calcScore(currentPlayer);
        }
    }

    private void loadFrezed(List <BoardField> frozen){
        for(BoardField field :frozen ){
            field.setFreez();
            this.frozen.add(field);
        }
    }

    public void freezFields(final int count, final int max_timeFreez, final int max_timeChange){
        ubFreezWhoCan();
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

    private void unFreezAll(){
        while(!frozen.isEmpty() ){
            BoardField tmp =  frozen.get(frozen.size()-1);
            tmp.isFreez = false;
            BoardGUI.unFreezeField(tmp.row,tmp.col);
            frozen.remove(tmp);
        }
    }

    private void ubFreezWhoCan(){
        for (Iterator<BoardField> iterator = frozen.iterator(); iterator.hasNext();) {
            BoardField field = iterator.next();
            if (field.getfreezEnd()) {
                field.isFreez = false;
                BoardGUI.unFreezeField(field.row,field.col);
                iterator.remove();
            }
        }
    }


}