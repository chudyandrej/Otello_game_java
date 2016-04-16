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
        Board.field[ (size/2)-1][(size/2)-1].putDisk(new Disk(true));
        Board.field[(size/2)][(size/2)].putDisk(new Disk(true));

        Board.field[(size/2)-1][(size/2)].putDisk(new Disk(false));
        Board.field[(size/2)][(size/2)-1].putDisk(new Disk(false));

    }

    public boolean canPutDisk(int x, int y, Player playerTurn){
        BoardField  field =  Board.field[x][y];
        if(field.getDisk() == null) {
            BoardField tmp;
            for (BoardField.Direction way : BoardField.Direction.values()) {
                tmp = field.nextField(way);
                if (tmp != null && tmp.getDisk() != null && tmp.getDisk().isWhite() != playerTurn.isWhite()) {
                    while (tmp != null && tmp.getDisk() != null) {
                        if (tmp.getDisk().isWhite() == playerTurn.isWhite()) {
                            return true;
                        }
                        tmp = tmp.nextField(way);
                    }

                }
            }
        }
        return false;
    }

    public boolean putDisk(int x, int y, Player playerTurn){
        BoardField  field =  Board.field[x][y];
        BoardField tmp;
        boolean success = false;
        if(field.getDisk() == null) {
            for (BoardField.Direction way : BoardField.Direction.values()) {
                tmp = field.nextField(way);
                if (tmp != null && tmp.getDisk() != null && tmp.getDisk().isWhite() != playerTurn.isWhite()) {
                    Stack<BoardField> st = new Stack<BoardField>();
                    while (tmp != null && tmp.getDisk() != null) {
                        if (tmp.getDisk().isWhite() == playerTurn.isWhite()) {
                            turn_disks(st);
                            success = true;
                            break;
                        }
                        st.push(tmp);
                        tmp = tmp.nextField(way);
                    }
                }
            }
            if (success){ field.putDisk(new Disk(playerTurn.isWhite())); }
        }
        return success;
    }

    public int getSize(){
        return size;
    }

    private void turn_disks(Stack<BoardField> st){
        boolean success = false;
        BoardField tmp;
        while (!st.empty()) {
            tmp = st.pop();
            tmp.getDisk().turn();
            tmp = null;
        }
    }
}

