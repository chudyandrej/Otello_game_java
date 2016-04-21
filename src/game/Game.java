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
    private AtomicBoolean freezeRunning;
    private List<BoardField> frozen;
    private int discsToFreeze;
    private int CHTime;
    private int FTime;

    public Game(int size, int discsToFreeze, int CHTime, int FTime){
        backupGame = new Backup(size);
        rules = new ReversiRules(size,backupGame);
        sizeBoard = size;

        freezeRunning = new AtomicBoolean(false);
        frozen = new ArrayList<BoardField>();
        gameOver = false;
        currentPlayer = white;
        this.discsToFreeze = discsToFreeze;
        this.CHTime = CHTime;
        this.FTime = FTime;


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
        freezFields(discsToFreeze,CHTime,FTime);

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
            loadFrezed(lastTurn.freeze);
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
            field.setFreeze();
            this.frozen.add(field);
        }
    }

    public void freezFields(final int count, final int max_timeFreez, final int max_timeChange){
        ubFreezWhoCan();
        if(!freezeRunning.get()) {
            freezeRunning.set(true);
            new Thread() {
                public void run() {
                    int timeChange = (int) (Math.random() * max_timeChange);
                    for (int x = 0; x < count; x++) {
                        int randomX = (int) (Math.random() * (sizeBoard - 1));
                        int randomY = (int) (Math.random() * (sizeBoard - 1));
                        Board.field[randomX][randomY].freezeField(max_timeFreez);
                        frozen.add(Board.field[randomX][randomY]);
                    }
                    try {
                        TimeUnit.SECONDS.sleep(timeChange);
                    }catch (InterruptedException e) {
                        System.out.println("Exception thrown  :" + e);
                    }
                    freezeRunning.set(false);
                }
            }.start();
        }
    }

    private void unFreezAll(){
        while(!frozen.isEmpty() ){
            BoardField tmp =  frozen.get(frozen.size()-1);
            tmp.isFreeze = false;
            BoardGUI.unFreezeField(tmp.row,tmp.col);
            frozen.remove(tmp);
        }
    }

    private void ubFreezWhoCan(){

        Iterator<BoardField> iter = frozen.iterator();
        while (iter.hasNext()) {
            BoardField now = iter.next();
            if (now.getFreezeEnd()) {
                now.isFreeze = false;
                BoardGUI.unFreezeField(now.row,now.col);
                iter.remove();
            }
        }
    }


}