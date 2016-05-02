/**
 * This class creates board and implements methods over it 
 * 
 * @author  Andrej Chudý
 * @author  Martin Kopec
 * @date 15.04.2016
 */

package board;

/**
 * This class creates board and implements methods over it
 */
public class Board {

    static public  BoardField[][] field;

    /**
     * Created array of BoardField objects
     * @param size size of board
     */
    public Board(int size) {

        field = new BoardField[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                field[i][j] = new BoardField(i,j,size);
            }
        }
    }
}
