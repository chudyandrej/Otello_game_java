/**
 * This class implements player, his color, his UI level (in case it's computer),
 * and his name.
 *
 * @author  Andrej Chudý
 * @author  Martin Kopec
 * @date 15.04.2016
 */

package game;

/**
 * This class implements player, his color, his AI level (in case it's computer),
 * and his name.
 */
public class Player  implements java.io.Serializable{

    private boolean isWhite;
    private byte level;
    private boolean is_pc = false;

    public String name;

    /**
     * Constructor method, initializes player, color and name.
     * @param isWhite boolean color of player
     * @param name player's name
     */
    public Player(boolean isWhite, String name) {
        this.isWhite = isWhite;
        this.name = name;
    }

    /**
     * The second constructor method, which initializes AI player (bot)
     * @param isWhite color (true if white)
     * @param level level of AI
     * @param name player's name
     */
    public Player(boolean isWhite, byte level, String name){
        is_pc = true;
        this.level = level;
        this.isWhite = isWhite;
        this.name = name;
    }

    /**
     * @return string black of white according to the color
     */
    public String toString() {
        return (isWhite) ? "white" : "black";
    }

    /**
     * @return true if player is white, false otherwise
     */
    public boolean isWhite(){
        return isWhite;
    }

    /**
     * Method finds out if player can put disc to the field
     * @param x x-coordinate of field
     * @param y y-coordinate of field
     * @return true if success, else false
     */
    public boolean canPutDisk(int x, int y){
        return Game.rules.canPutDisk(x, y, this);
    }

    /**
     * Methods put disc to the field.
     * @param x x-coordinate of field
     * @param y y-coordinate of field
     * @return true if success, else false
     */
    public boolean putDisk(int x, int y){
        return Game.rules.putDisk(x, y, this);
    }

    /**
     * Methods runs AI algorithm depends on level.
     */
    public void uiTurn() {
        if (level == 1) {
            Game.rules.uiAlgorithmLevel1(this);
        }
        else {
            Game.rules.uiAlgorithmLevel2(this);
        }
    }

    /**
     * @return true if the player is bot
     */
    public boolean is_pc() {
        return is_pc;
    }
}
