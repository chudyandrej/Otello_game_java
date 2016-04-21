package game;


import board.BoardField;


import java.util.ArrayList;
import java.util.List;



/**
 * Created by andrejchudy on 19/04/16.
 */

public class Backup implements java.io.Serializable{

    public List<TurnBackUp> backupTurns;

    private TurnBackUp new_turn;
    private Player player1;
    private Player player2;


    private int boardSize;


    public Backup(int size){
        boardSize = size;
        backupTurns = new ArrayList<TurnBackUp>();

    }

    public void create_NewTurn(BoardField base_Point, Player turn_player){
        new_turn = new TurnBackUp(base_Point, turn_player);
    }

    public void add_TurnedDisks(List<BoardField> turnedDisk){
        new_turn.add_Stack_turned(turnedDisk);

    }

    public void add_FreezdDisks(List<BoardField> freezeDisk){
        new_turn.add_Stack_freez(freezeDisk);
    }

    public void save_BackupRecord(){
        backupTurns.add(new_turn);
    }

    public void load(){
        for(TurnBackUp turn  : backupTurns) {
            turn.turn_player.putDisk(turn.base_Point.row,turn.base_Point.col);
        }
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }


    public int getBoardSize() {
        return boardSize;
    }


    public class TurnBackUp implements java.io.Serializable{
        public BoardField base_Point;
        public List<BoardField> turned;
        public List<BoardField> freeze;
        public Player turn_player;

        TurnBackUp(BoardField base_Point, Player turn_palyer){
            turned = new ArrayList<BoardField>();
            freeze = new ArrayList<BoardField>();
            this.base_Point = base_Point;
            this.turn_player = turn_palyer;
        }

        public void add_Stack_freez(List<BoardField> freezedDisk){
            freeze.addAll(freezedDisk);
        }

        public void add_Stack_turned(List<BoardField> turnndDisk){
            turned.addAll(turnndDisk);
        }
    }
}