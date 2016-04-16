package game;

import GUI.BoardGUI;
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
        BoardField tmp;
        for (BoardField.Direction way : BoardField.Direction.values()) {
            tmp = field.nextField(way);
            if (tmp != null) {
                if (tmp.getDisk() != null && tmp.getDisk().isWhite() != playerTurn.isWhite()) {
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
        System.out.printf("vypis---------------------------\n",x, y );
        BoardField tmp;
        boolean success = false;

        for (BoardField.Direction way : BoardField.Direction.values()) {
            System.out.printf("Way : %s\n",way);

            tmp = field.nextField(way);
            if (tmp != null) {
                if (tmp.getDisk() != null && tmp.getDisk().isWhite() != playerTurn.isWhite()) {
                    System.out.printf("next : %d %d  %s  %s\n",tmp.row, tmp.col,way, tmp.getDisk().isWhite());
                    Stack st = new Stack();
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
        }
        if (success){
            field.putDisk(new Disk(playerTurn.isWhite()));
        }
        return success;
    }

    public int getSize(){
        return size;
    }

    private void turn_disks(Stack st){
        boolean success = false;
        BoardField tmp;
        while (!st.empty()) {
            tmp = (BoardField) st.pop();
            tmp.getDisk().turn();
            tmp = null;
        }
    }
}

