package game;

import board.Board;
import board.BoardField;
import board.Disk;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by andrejchudy on 19/04/16.
 */

public class Backup implements java.io.Serializable{

    private TurnBackUp new_turn;
    public List<TurnBackUp> backupTurns;
    public Player player1;
    public Player player2;
    public int boardSize;


    public Backup(){
        backupTurns = new ArrayList<TurnBackUp>();

    }

    public void create_NewTurn(BoardField base_Point, Player turn_player){
        new_turn = new TurnBackUp(base_Point, turn_player);
    }

    public void add_TurnedDisks(List<BoardField> turnedDisk){
        new_turn.add_Stack_turned(turnedDisk);

    }

    public void save_BackupRecord(){
        backupTurns.add(new_turn);
    }

    public void load(){
        for(TurnBackUp turn  : backupTurns) {
            turn.turn_player.putDisk(turn.base_Point.row,turn.base_Point.col);
        }
    }

    public class TurnBackUp implements java.io.Serializable{
        public BoardField base_Point;
        public List<BoardField> turned;
        public Player turn_player;

        TurnBackUp(BoardField base_Point, Player turn_palyer){
            turned = new ArrayList<BoardField>();
            this.base_Point = base_Point;
            this.turn_player = turn_palyer;
        }
        
        public void add_Stack_turned(List<BoardField> turnndDisk){
            turned.addAll(turnndDisk);
        }
    }
}