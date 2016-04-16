package board;

/**
 * Created by andrejchudy on 15/04/16.
 */
public class Board {

    private int size;
    static public  BoardField[][] field;

    public Board(int size) {
        this.size = size;
        field = new BoardField[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size ; j++) {
                field[i][j] = new BoardField(i,j);

            }
        }
    }

    public BoardField getField(int row, int col){
        return field[row][col];
    }

    public void putField(int row, int col, BoardField field){
        this.field[row][col] = field;
    }

    public int getSize(){
        return size;
    }
}
