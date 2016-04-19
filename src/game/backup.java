package game;

import board.BoardField;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by andrejchudy on 19/04/16.
 */

public class Backup {
    private TurnBackUp new_turn;
    public List backupTurns;

    Backup(){
        backupTurns = new ArrayList();

    }

    public void create_NewTurn(BoardField base_Point, Player turn_player){
        new_turn = new TurnBackUp(base_Point, turn_player);
    }

    public void add_TurnedDisks(List turnedDisk){
        new_turn.add_Stack_turned(turnedDisk);

    }

    public void save_BackupRecord(){
        backupTurns.add(new_turn);
    }

    public class TurnBackUp {
        public BoardField base_Point;
        public List turned;
        public Player turn_player;
        TurnBackUp(BoardField base_Point, Player turn_palyer){
            turned = new ArrayList();
            this.base_Point = base_Point;
            this.turn_player = turn_palyer;
        }
        public void add_Stack_turned(List turnndDisk){
            turned.addAll(turnndDisk);
        }
    }
}
