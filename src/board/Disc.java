/**
 * This class implements disc, which will be placed on board fields.
 * The class also implements methods over the disc.
 *
 * @author  Andrej Chudý
 * @author  Martin Kopec
 * @date 15.04.2016
 */

package board;

import GUI.BoardGUI;

/**
 * This class implements disc, which will be placed on board fields.
 * The class also implements methods over the disc.
 */
public class Disc implements java.io.Serializable {
    private boolean isWhite;
    private int x, y;

    /**
     * Constructor method, initializes the disc.
     * @param isWhite boolean value, color of the disc
     */
    public Disc(boolean isWhite){
        this.isWhite = isWhite;
    }

    /**
     * Method turns the disc, changes the color to the other one.
     */
    public void turn(){
        this.isWhite = !isWhite;
        BoardGUI.changeDisc(x,y, isWhite); //turn also GUI disc
    }

    /**
     * @return true if the disc is white, false otherwise
     */
    public boolean isWhite(){
        return isWhite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Disc disc = (Disc) o;

        return isWhite == disc.isWhite;
    }

    @Override
    public int hashCode() {
        return (isWhite ? 1 : 0);
    }

    /**
     * Method places the disc.
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public void setY(int x , int y) {
        this.x = x;
        this.y = y;
        BoardGUI.changeDisc(x,y, isWhite);
    }

    /**
     * Method deletes the GUI disc.
     */
    public void delete(){
        BoardGUI.deleteDisc(x,y);
    }
}
