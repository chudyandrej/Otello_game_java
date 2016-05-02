/**
 * This class implements the current game, initializes rules, implements
 * methods for adding players to the game, operation undo and control of 
 * frozen disc.
 *
 * @author  Andrej Chudý
 * @author  Martin Kopec
 * @date 15.04.2016
 */

package game;

import GUI.BoardGUI;
import board.Board;
import board.BoardField;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class implements the current game, initializes rules, implements
 * methods for adding players to the game, operation undo and control of 
 * frozen disc.
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

    /**
     * Constructor, initializes the game.
     * @param size board size
     * @param discsToFreeze number of disc, which can be made frozen
     * @param CHTime time after which can be discs made unfrozen
     * @param FTime time for which are the discs hold frozen
     */
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

    /**
     * Methods adds player to the game.
     * @param newPlayer instance of a new player
     * @return true if success, else false (f.e. player already exists)
     */
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

    /**
     * @return instance of player on turn
     */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * @return game backup
     */
    public Backup getBackupGame() {
        return backupGame;
    }

    /**
     * Method sets current player and checks if the player has any possible move, 
     * if not, other player is sets or game over is raised.
     */
    public void nextPlayer(){

        backupGame.addFrozenDiscs(frozen);
        backupGame.saveBackupRecord();
        rules.calcScore(currentPlayer);
        frozenFields(discsToFreeze,CHTime,FTime);

        currentPlayer = (currentPlayer == black) ? white : black;
        if (currentPlayer.is_pc()) {
            currentPlayer.uiTurn(this);
            return;
        }
        else if(rules.isExistingTurn(currentPlayer)) {
            return;
        }
        else if (!rules.isExistingTurn(white) && !rules.isExistingTurn(black)){
            rules.calcScore(currentPlayer);
            gameOver = true;
            return;
        }
        currentPlayer = (currentPlayer == black) ? white : black;
        if (currentPlayer.is_pc()) {
            currentPlayer.uiTurn(this);
        }
    }

    /**
     * Method provides undo operation.
     */
    public void undo(){

        unFreezeAll();
        gameOver = false;
        Backup.TurnBackUp lastTurn;
        if (backupGame.backupTurns.size() > 0) {
            lastTurn = backupGame.backupTurns.get(backupGame.backupTurns.size() - 1);
            lastTurn.base_Point.deleteDisk();
            rules.turn_discs(lastTurn.turned);
            loadFrozen(lastTurn.freeze);
            backupGame.backupTurns.remove(lastTurn);
            currentPlayer = lastTurn.turn_player;

            if (currentPlayer.is_pc()){
                undo();
            }
            rules.calcScore(currentPlayer);
        }
    }

    /**
     * 
     * @param frozen
     */
    private void loadFrozen(List <BoardField> frozen){
        for(BoardField field :frozen ){
            field.setFreeze();
            this.frozen.add(field);
        }
    }

    /**
     * 
     * @param count
     * @param max_timeFreeze
     * @param max_timeChange
     */
    public void frozenFields(final int count, final int max_timeFreeze, final int max_timeChange){
        unFreezeWhoCan();
        if(!freezeRunning.get()) {
            freezeRunning.set(true);
            new Thread() {
                public void run() {
                    int timeChange = (int) (Math.random() * max_timeChange);
                    for (int x = 0; x < count; x++) {
                        int randomX = (int) (Math.random() * (sizeBoard - 1));
                        int randomY = (int) (Math.random() * (sizeBoard - 1));
                        Board.field[randomX][randomY].freezeField(max_timeFreeze);
                        frozen.add(Board.field[randomX][randomY]);
                    }
                    try {
                        TimeUnit.SECONDS.sleep(timeChange);
                    }catch (InterruptedException e) {
                        System.err.println("Exception thrown  :" + e);
                    }
                    freezeRunning.set(false);
                }
            }.start();
        }
    }

    /**
     * Method makes all fields unfrozen.
     */
    private void unFreezeAll(){
        while(!frozen.isEmpty() ){
            BoardField tmp =  frozen.get(frozen.size()-1);
            tmp.isFreeze = false;
            BoardGUI.unFreezeField(tmp.row,tmp.col);
            frozen.remove(tmp);
        }
    }

    /**
     * 
     */
    private void unFreezeWhoCan(){

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