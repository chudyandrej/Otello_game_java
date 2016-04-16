package board;

/**
 * Created by andrejchudy on 15/04/16.
 */
public class BoardField {

    private int row;
    private int col;
    private Disk disk;

    public BoardField(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void addNextField(Direction dirs, BoardField field) {
        switch (dirs) {
            case D:
                Board.field[row + 1] [col] = field;
                break;
            case L:
                Board.field[row] [col - 1] = field;
                break;
            case LD:
                Board.field[row + 1] [col - 1] = field;
                break;
            case LU:
                Board.field[row - 1] [col - 1] = field;
                break;
            case R:
                Board.field[row] [col + 1] = field;
                break;
            case RD:
                Board.field[row + 1] [col + 1] = field;
                break;
            case RU:
                Board.field[row - 1] [col + 1] = field;
                break;
            case U:
                Board.field[row - 1][col] = field;
                break;
        }
    }

    public BoardField nextField(Direction dirs) {

        switch (dirs) {
            case D:
                return Board.field[row + 1][col];
            case L:
                return Board.field[row][col - 1];
            case LD:
                return Board.field[row + 1][col - 1];
            case LU:
                return  Board.field[row - 1][col - 1];
            case R:
                return  Board.field[row][col + 1];
            case RD:
                return  Board.field[row + 1][col + 1];
            case RU:
                return  Board.field[row - 1][col + 1];
            case U:
                return  Board.field[row - 1][col];

        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoardField that = (BoardField) o;

        if (row != that.row) return false;
        if (col != that.col) return false;
        return disk != null ? disk.equals(that.disk) : that.disk == null;

    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + col;
        result = 31 * result + (disk != null ? disk.hashCode() : 0);
        return result;
    }

    public boolean putDisk(Disk disk) {
        Boolean ret_val = false;
        if (this.disk == null){
            this.disk = disk;
            ret_val = true;
        }
        return ret_val;
    }

    public Disk getDisk(){
        return this.disk;
    }

    public enum Direction{D, L, LD, LU, R, RD, RU, U}
    
}
