/**
 * This class is responsible for saving information about players, game, each turns
 * and about positions of placed discs.
 *
 * @author  Andrej Chudý
 * @author  Martin Kopec
 * @date 20.04.2016
 */

package game;

import board.BoardField;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for saving information about players, game, each turns
 * and about positions of placed discs.
 */
public class Backup implements java.io.Serializable{

    public List<TurnBackUp> backupTurns;

    private TurnBackUp newTurn;
    private Player player1;
    private Player player2;
    private int boardSize;

    /**
     * Constructor method, initializes the backup class.
     * @param size board size
     */
    public Backup(int size){
        boardSize = size;
        backupTurns = new ArrayList<TurnBackUp>();
    }

    /**
     *
     * @param basePoint
     * @param turnPlayer
     */
    public void createNewTurn(BoardField basePoint, Player turnPlayer){
        newTurn = new TurnBackUp(basePoint, turnPlayer);
    }

    /**
     *
     * @param turnedDisc
     */
    public void addTurnedDiscs(List<BoardField> turnedDisc){
        newTurn.add_Stack_turned(turnedDisc);
    }

    /**
     *
     * @param frozenDisc
     */
    public void addFrozenDiscs(List<BoardField> frozenDisc){
        newTurn.add_Stack_freeze(frozenDisc);
    }

    /**
     *
     */
    public void saveBackupRecord(){
        backupTurns.add(newTurn);
    }

    /**
     *
     */
    public void load(){
        for(TurnBackUp turn  : backupTurns) {
            turn.turn_player.putDisk(turn.base_Point.row,turn.base_Point.col);
        }
    }

    /**
     * Getter of player1.
     * @return player1
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Setter of player1.
     * @param player1 player1 instance
     */
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    /**
     * Getter of player2.
     * @return player2
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * Setter of player2.
     * @param player2 player2 instance
     */
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    /**
     * Getter of board size.
     * @return board size
     */
    public int getBoardSize() {
        return boardSize;
    }

    /**
     * This class is backup for each turn. Remembers where the disc are placed,
     * which fields are frozen and which player is on turn.
     */
    public class TurnBackUp implements java.io.Serializable{
        public BoardField base_Point;
        public List<BoardField> turned;
        public List<BoardField> freeze;
        public Player turn_player;

        /**
         *
         * @param basePoint
         * @param playerOnTurn
         */
        public TurnBackUp(BoardField basePoint, Player playerOnTurn){
            turned = new ArrayList<BoardField>();
            freeze = new ArrayList<BoardField>();
            this.base_Point = basePoint;
            this.turn_player = playerOnTurn;
        }

        /**
         *
         * @param frozenDisc
         */
        public void add_Stack_freeze(List<BoardField> frozenDisc){
            freeze.addAll(frozenDisc);
        }

        /**
         *
         * @param turnedDisc
         */
        public void add_Stack_turned(List<BoardField> turnedDisc){
            turned.addAll(turnedDisc);
        }
    }
}