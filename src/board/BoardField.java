/**
 * This class creates board field and implements methods over them.
 * 
 * @author  Andrej Chudý
 * @author  Martin Kopec
 * @date 15.04.2016
 */

package board;

import GUI.BoardGUI;
import java.util.concurrent.TimeUnit;

/**
 * This class creates board field and implements methods over them.
 */
public class BoardField implements java.io.Serializable {

    public int row;
    public int col;
    public int size;
    public boolean isFreeze;

    private boolean freezeEnd;
    private Disc disc;

    /**
     * Constructor method for board field initialization.
     * @param row number of row where is object placed
     * @param col number of col where is object placed
     * @param size board size
     */
    public BoardField(int row, int col, int size) {
        this.row = row;
        this.col = col;
        this.size = size;
        this.disc = null;
        isFreeze = false;
    }

    /**
     * Method knows about surrounding fields.
     * @param dirs enum, direction
     * @return field in given direction
     */
    public BoardField nextField(Direction dirs) {
        switch (dirs) {
            case D:
                if (row != size -1 ){return Board.field[row + 1][col];}
                break;
            case L:
                if (col != 0 ){return Board.field[row][col - 1];}
                break;
            case LD:
                if (row != size-1 && col != 0){return Board.field[row + 1][col - 1];}
                break;
            case LU:
                if (row != 0 && col != 0){return  Board.field[row - 1][col - 1];}
                break;
            case R:
                if (col != size-1 ){return  Board.field[row][col + 1];}
                break;
            case RD:
                if (row != size-1  && col != size-1 ){return  Board.field[row + 1][col + 1];}
                break;
            case RU:
                if (row != 0 && col != size-1 ){return  Board.field[row - 1][col + 1];}
                break;
            case U:
                if (row != 0){return  Board.field[row - 1][col];}
                break;
        }
        return null;
    }

    /**
     * Methods puts given disc to the field.
     * @param disc disc to be put on field
     * @return true if operation is successful
     */
    public boolean putDisk(Disc disc) {
        Boolean ret_val = false;
        if (this.disc == null){
            disc.setY(row,col);
            this.disc = disc;
            ret_val = true;
        }
        return ret_val;
    }

    /**
     * Method deletes disc from the field.
     */
    public void deleteDisk(){
        if(disc != null){
            disc.delete();
        }
        this.disc = null;
    }

    /**
     * Method returns object placed on the field.
     * @return disc or null
     */
    public Disc getDisc(){
        return disc;
    }

    /**
     * Method makes the disc frozen for random time from time interval.
     * @param time time interval
     */
    public void freezeField(final int time){

        freezeEnd = false;
        isFreeze = true;
        BoardGUI.freezeField(row,col);

        new Thread(){

            public void run() {
                try {
                    int timeFreeze = (int) (Math.random() * time);
                    TimeUnit.SECONDS.sleep(timeFreeze);
                } catch (InterruptedException e) {
                    System.err.println("Exception thrown  :" + e);
                }
                freezeEnd = true;
            }
        }.start();
    }

    /**
     * @return true or false
     */
    public boolean getFreezeEnd() {
        return freezeEnd;
    }

    /**
     * Direction enum
     */
    public enum Direction{D, L, LD, LU, R, RD, RU, U}

    /**
     * Method makes the field frozen.
     */
    public void setFreeze(){
        isFreeze = true;
        freezeEnd = true;
        BoardGUI.freezeField(row,col);
    }
}
