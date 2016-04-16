package game;

import board.BoardField;


/**
 * Created by andrejchudy on 15/04/16.
 */
public class Player {

    private boolean isWhite;

    private byte level;
    private boolean is_pc = false;
    public String name;

    public Player(boolean isWhite, String name) {
        this.isWhite = isWhite;
        this.name = name;
    }

    public Player(boolean isWhite, byte level, String name){
        is_pc = true;
        this.level = level;
        this.isWhite = isWhite;
        this.name = name;
    }

    public String toString() {
        return (isWhite)?"white":"black";
    }

    public boolean isWhite(){
        return isWhite;
    }

    public boolean canPutDisk(int x, int y){
        return Game.rules.canPutDisk(x, y, this);
    }

    public boolean putDisk(int x, int y){
        return Game.rules.putDisk(x, y, this);
    }





    public void uiTurn(Game game) {
        int size = Game.rules.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (canPutDisk(i,j)){
                    putDisk(i,j);
                    game.nextPlayer();
                    return;
                }
            }

        }

    }

    public boolean is_pc() {
        return is_pc;
    }
}
