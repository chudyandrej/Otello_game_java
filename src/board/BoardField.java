/**
 * This class 
 * 
 * @author  Andrej Chudý
 * @author  Martin Kopec
 * @date 15.04.2016
 */

package board;

import GUI.BoardGUI;

import java.util.concurrent.TimeUnit;


public class BoardField implements java.io.Serializable {

    public int row;
    public int col;
    public int size;
    public boolean isFreeze;

    private boolean freezeEnd;
    private Disk disk;


    public BoardField(int row, int col, int size) {
        this.row = row;
        this.col = col;
        this.size = size;
        this.disk = null;
        isFreeze = false;
    }

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


    public boolean putDisk(Disk disk) {
        Boolean ret_val = false;
        if (this.disk == null){
            disk.setY(row,col);
            this.disk = disk;
            ret_val = true;
        }
        return ret_val;
    }

    public void deleteDisk(){
        if(disk != null){
            disk.delete();
        }
        this.disk = null;
    }

    public Disk getDisk(){
        return disk;
    }

    public void freezeField(final int time){

        freezeEnd = false;
        isFreeze = true;
        BoardGUI.freezeField(row,col);

        new Thread()
        {
            public void run() {
                try {
                    int timeFreeze = (int) (Math.random() * time);
                    TimeUnit.SECONDS.sleep(timeFreeze);
                } catch (InterruptedException e) {
                    System.out.println("Exception thrown  :" + e);
                }
                freezeEnd = true;
            }
        }.start();
    }

    public boolean getFreezeEnd() {
        return freezeEnd;
    }


    public enum Direction{D, L, LD, LU, R, RD, RU, U}

    public void setFreeze(){
        isFreeze = true;
        freezeEnd = true;
        BoardGUI.freezeField(row,col);


    }
    
}
