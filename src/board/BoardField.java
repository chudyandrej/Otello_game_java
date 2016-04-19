package board;

/**
 * Created by andrejchudy on 15/04/16.
 */
public class BoardField {

    public int row;
    public int col;
    public int size;
    private Disk disk;

    public BoardField(int row, int col, int size) {
        this.row = row;
        this.col = col;
        this.size = size;
        this.disk = null;
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

    public enum Direction{D, L, LD, LU, R, RD, RU, U}
    
}
