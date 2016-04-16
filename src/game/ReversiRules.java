package game;

import board.Board;
import board.BoardField;
import java.util.Stack;
import board.Disk;

/**
 * Created by andrejchudy on 15/04/16.
 */

public class ReversiRules {

    private int size;
    private Board playBoard ;

    public ReversiRules(int size){
        this.size = size;
        playBoard = new Board(size);
        //starting position
        Board.field[ size/2 ][ size/2 ].putDisk(new Disk(true));
        Board.field[(size/2) + 1 ][(size/2) + 1 ].putDisk(new Disk(true));

        Board.field[ size/2 ][ (size/2) +1 ].putDisk(new Disk(false));
        Board.field[(size/2) + 1 ][ size/2 ].putDisk(new Disk(false));

    }

    public boolean canPutDisk(int x, int y, Player playerTurn){
        BoardField  field =  Board.field[x][y];
        BoardField tmp;
        for (BoardField.Direction way : BoardField.Direction.values()) {
            tmp = field.nextField(way);
            if(tmp.getDisk() != null && tmp.getDisk().isWhite() != playerTurn.isWhite()) {
                while (tmp.getDisk() != null) {
                    if (tmp.getDisk().isWhite() == playerTurn.isWhite()) {
                        return true;
                    }
                    tmp = tmp.nextField(way);
                }
            }
        }
        return false;
    }

    public boolean putDisk(int x, int y, Player playerTurn){
        BoardField  field =  Board.field[x][y];
        BoardField tmp;
        boolean success = false;
        for (BoardField.Direction way : BoardField.Direction.values()) {
            tmp = field.nextField(way);
            boolean validPosition = false;
            if(tmp.getDisk() != null && tmp.getDisk().isWhite() != playerTurn.isWhite()) {
                Stack st = new Stack();
                while (tmp.getDisk() != null) {
                    if (tmp.getDisk().isWhite() == playerTurn.isWhite()) {
                        while(!st.empty()){
                            tmp = (BoardField) st.pop();
                            tmp.getDisk().turn();
                            validPosition = true;
                            success = true;
                            break;
                        }
                        st.push(tmp);
                    }
                    tmp = tmp.nextField(way);
                }
                if (validPosition){
                    field.putDisk(new Disk(playerTurn.isWhite()));
                }
            }
        }
        return success;
    }

    public int getSize(){
        return size;
    }

}
