package game;

import board.BoardField;


/**
 * Created by andrejchudy on 15/04/16.
 */
public class Player {

    private boolean isWhite;
    private int pool;
    private ReversiRules rules;

    public Player(ReversiRules rules,boolean isWhite) {
        this.rules = rules;
        this.isWhite = isWhite;
    }

    public String toString() {
        return (isWhite)?"white":"black";
    }

    public boolean isWhite(){
        return isWhite;
    }

    public boolean canPutDisk(int x, int y){
        return rules.canPutDisk(x, y, this);
    }

    public boolean putDisk(int x, int y){
        return rules.putDisk(x, y, this);
    }

}
