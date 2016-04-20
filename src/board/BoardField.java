package board;

import GUI.BoardGUI;

import java.util.concurrent.TimeUnit;

/**
 * Created by andrejchudy on 15/04/16.
 */
public class BoardField implements java.io.Serializable {

    public int row;
    public int col;
    public int size;
    public boolean isFreez;

    private boolean freezEnd;
    private Disk disk;


    public BoardField(int row, int col, int size) {
        this.row = row;
        this.col = col;
        this.size = size;
        this.disk = null;
        isFreez = false;
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

    public void freezField(final int time){

        freezEnd = false;
        isFreez = true;
        BoardGUI.freezeField(row,col);

        new Thread()
        {
            public void run() {
                try {
                    int timeFreez = (int) (Math.random() * time);
                    TimeUnit.SECONDS.sleep(timeFreez);
                } catch (InterruptedException e) {
                    System.out.println("Exception thrown  :" + e);
                }
                freezEnd = true;
            }
        }.start();
    }

    public boolean getfreezEnd() {
        return freezEnd;
    }


    public enum Direction{D, L, LD, LU, R, RD, RU, U}
    
}
