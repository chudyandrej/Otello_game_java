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

    public boolean isWhite(){
        return isWhite;
    }

    public boolean canPutDisk(BoardField field){
        return rules.canPutDisk(field, this);
    }

    public boolean putDisk(BoardField field){
        return rules.putDisk(field, this);
    }


}
