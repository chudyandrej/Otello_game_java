/**
 * This class implements rules of the Othello game (a.k.a. Reversi)
 *
 * @author  Andrej Chudý
 * @author  Martin Kopec
 * @date 15.04.2016
 */

package game;

import GUI.BoardGUI;
import board.Board;
import board.BoardField;

import board.Disc;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements rules of the Othello game (a.k.a. Reversi)
 */
public class ReversiRules {

    private int size;
    private Board playBoard;
    private Backup backupGame;

    /**
     * Constructor, initializes Reversi rules
     * @param size Size of board
     * @param backupGame Backup of game
     */
    public ReversiRules(int size, Backup backupGame){
        this.size = size;
        this.backupGame = backupGame;
        this.playBoard = new Board(size);

        //starting positions
        Board.field[ (size/2)-1][(size/2)-1].putDisk(new Disc(true));
        Board.field[(size/2)][(size/2)].putDisk(new Disc(true));
        Board.field[(size/2)-1][(size/2)].putDisk(new Disc(false));
        Board.field[(size/2)][(size/2)-1].putDisk(new Disc(false));
    }

    /**
     * Method decides if the player on turn can put disc to the place.
     * @param x x-coordinate of the field
     * @param y y-coordinate of the field
     * @param playerTurn instance of player on turn
     * @return true if the player can put disc, else false
     */
    public boolean canPutDisk(int x, int y, Player playerTurn){
        BoardField  field =  Board.field[x][y];
        if(field.getDisc() == null && !field.isFreeze) {
            BoardField tmp;
            for (BoardField.Direction way : BoardField.Direction.values()) {
                tmp = field.nextField(way);
                if (tmp != null && !tmp.isFreeze && tmp.getDisc() != null && tmp.getDisc().isWhite() != playerTurn.isWhite()) {
                    while (tmp != null && tmp.getDisc() != null && !tmp.isFreeze) {
                        if (tmp.getDisc().isWhite() == playerTurn.isWhite() ) {
                            return true;
                        }
                        tmp = tmp.nextField(way);
                    }
                }
            }
        }
        return false;
    }

    /**
     * Method puts disc to the field.
     * @param x x-coordinate of the field
     * @param y y-coordinate of the field
     * @param playerTurn instance of player on turn
     * @return true if success, false otherwise
     */
    public boolean putDisk(int x, int y, Player playerTurn){
        BoardField  field =  Board.field[x][y];
        List<BoardField> disks_for_turn;
        boolean success = false;
        if(field.getDisc() == null && !field.isFreeze) {
            for (BoardField.Direction way : BoardField.Direction.values()) {
                disks_for_turn = check_IN_direct(field, way,playerTurn );
                if (disks_for_turn != null){
                    if(!success){backupGame.createNewTurn(field,playerTurn);}
                    backupGame.addTurnedDiscs(disks_for_turn);
                    turn_discs(disks_for_turn);
                    success = true;
                }
            }
            if (success){
                field.putDisk(new Disc(playerTurn.isWhite()));
            }
        }
        return success;
    }

    /**
     * Checks color of discs in a direction.
     * @param field Start field
     * @param way Direction of checking
     * @param playerTurn Player on turn
     * @return List of turned fields
     */
    private List<BoardField> check_IN_direct(BoardField  field, BoardField.Direction way, Player playerTurn){
        field = field.nextField(way);
        if (field != null && !field.isFreeze && field.getDisc() != null  && field.getDisc().isWhite() != playerTurn.isWhite()) {
            List <BoardField> candidate_turn = new ArrayList<BoardField>();
            while (field != null && field.getDisc() != null && !field.isFreeze) {
                if (field.getDisc().isWhite() == playerTurn.isWhite()) {
                    return candidate_turn ;
                }
                candidate_turn.add(field);
                field = field.nextField(way);
            }
        }
        return null;
    }

    /**
     *
     * @param st List of fileds for turnd.
     */
    public void turn_discs(List<BoardField> st){
        BoardField tmp;
        while (!st.isEmpty()){
            tmp = st.get(st.size()-1);
            tmp.getDisc().turn();
            st.remove(tmp);
        }
    }

    /**
     * @return size of board
     */
    public int getSize(){
        return size;
    }

    /**
     * Method implements AI algorithm of level 1 (beginner)
     * @param UI instance of AI player
     */
    public void uiAlgorithmLevel1(Player UI){
        int size = Game.rules.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (canPutDisk(i,j,UI)){
                    putDisk(i,j,UI);
                    return;
                }
            }
        }
    }

    /**
     * Method implements AI algorithm of level 2 (advanced)
     * @param UI instance of AI player
     */
    public void uiAlgorithmLevel2(Player UI){
        List<BoardField> disksForTurn;
        List<BoardField> maxTurns = new ArrayList<BoardField>();
        BoardField  best_field = null;
        int size = Game.rules.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (canPutDisk(i,j,UI)){
                    BoardField  field =  Board.field[i][j];
                    for (BoardField.Direction way : BoardField.Direction.values()) {
                        disksForTurn =  check_IN_direct(field, way,UI );
                        if (disksForTurn != null && disksForTurn.size() > maxTurns.size()){
                            maxTurns = disksForTurn;
                            best_field = field;
                        }
                    }
                }
            }
        }
        if (!maxTurns.isEmpty()) {
            backupGame.createNewTurn(best_field,UI);
            backupGame.addTurnedDiscs(maxTurns);
            assert best_field != null;
            best_field.putDisk(new Disc(UI.isWhite()));
            turn_discs(maxTurns);
        }
    }

    /**
     * Method calculates score of current player.
     * @param currentPlayer instance of current player
     */
    public void calcScore(Player currentPlayer){
        int whiteDisc = 0;
        int blackDisc = 0;
        int size = Game.rules.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Disc tmp = Board.field[i][j].getDisc();
                if (tmp == null){
                    continue;
                }
                else if(tmp.isWhite()){ whiteDisc++; }
                else if(!tmp.isWhite()){ blackDisc++; }
            }
        }
        BoardGUI.setGameState(whiteDisc, blackDisc, currentPlayer.isWhite());
    }

    /**
     * Method finds out if current player has possibility to place disc somewhere,
     * otherwise it's game over.
     * @param currentPlayer instance of current player
     * @return true if exists, else false
     */
    public boolean isExistingTurn(Player currentPlayer) {
        int size = Game.rules.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (currentPlayer.canPutDisk(i,j)){
                    return true;
                }
            }
        }
        return false;
    }

}

