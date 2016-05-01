/**
 * This class creates board and implements methods over it 
 * 
 * @author  Andrej Chudý
 * @author  Martin Kopec
 * @date 15.04.2016
 */

package board;

public class Board {

    private int size;
    static public  BoardField[][] field;

    /**
     * Created array of BoardField objects
     * @param  int size - size of board
     */
    public Board(int size) {
        this.size = size;
        field = new BoardField[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                field[i][j] = new BoardField(i,j,size);
            }
        }
    }
    /**
     * Returns field specified by row and col
     * @param int row - number of row where field is placed
     * @param int col - number of col where field is placed
     * @return BoardField - wanted field
     */
    public BoardField getField(int row, int col){
        return field[row][col];
    }
    /**
     * Method puts field to wanted coordinates
     * @param int row - row coordinate
     * @param int col - column coordinate
     * @param BoardField field - field to be inserted to field array
     */
    public void putField(int row, int col, BoardField field){
        this.field[row][col] = field;
    }

    /**
     * @return int size - size of board
     */
    public int getSize(){
        return size;
    }
}
