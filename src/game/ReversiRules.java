package game;

import board.Board;
import board.BoardField;

import board.Disk;


import javax.swing.*;
import java.util.Random;
import java.util.Stack;

/**
 * Created by andrejchudy on 15/04/16.
 */

public class ReversiRules {

    private int size;
    private Board playBoard ;


    public ReversiRules(int size){
        this.size = size;
        playBoard = new Board(size);

        //starting position
        Board.field[ (size/2)-1][(size/2)-1].putDisk(new Disk(true));
        Board.field[(size/2)][(size/2)].putDisk(new Disk(true));

        Board.field[(size/2)-1][(size/2)].putDisk(new Disk(false));
        Board.field[(size/2)][(size/2)-1].putDisk(new Disk(false));

    }

    public boolean canPutDisk(int x, int y, Player playerTurn){
        BoardField  field =  Board.field[x][y];
        if(field.getDisk() == null) {
            BoardField tmp;
            for (BoardField.Direction way : BoardField.Direction.values()) {
                tmp = field.nextField(way);
                if (tmp != null && tmp.getDisk() != null && tmp.getDisk().isWhite() != playerTurn.isWhite()) {
                    while (tmp != null && tmp.getDisk() != null) {
                        if (tmp.getDisk().isWhite() == playerTurn.isWhite()) {
                            return true;
                        }
                        tmp = tmp.nextField(way);
                    }

                }
            }
        }
        return false;
    }

    public boolean putDisk(int x, int y, Player playerTurn){
        BoardField  field =  Board.field[x][y];
        Stack<BoardField> disks_for_turn;
        boolean success = false;
        if(field.getDisk() == null) {
            for (BoardField.Direction way : BoardField.Direction.values()) {
                disks_for_turn = chack_IN_direct(field, way,playerTurn );
                if (disks_for_turn != null){
                    if(success != true){Game.backupGame.create_NewTurn(field);}
                    Game.backupGame.add_TurnedDisks(disks_for_turn);
                    turn_disks(disks_for_turn);
                    success = true;
                }
            }
            if (success){
                field.putDisk(new Disk(playerTurn.isWhite()));
                Game.backupGame.save_BackupRecord();
            }
        }
        return success;
    }

    private  Stack<BoardField> chack_IN_direct(BoardField  field, BoardField.Direction way, Player playerTurn){
        field = field.nextField(way);
        if (field != null && field.getDisk() != null && field.getDisk().isWhite() != playerTurn.isWhite()) {
            Stack<BoardField> candidate_turn = new Stack<BoardField>();
            while (field != null && field.getDisk() != null) {
                if (field.getDisk().isWhite() == playerTurn.isWhite()) {
                    return candidate_turn ;
                }
                candidate_turn.push(field);
                field = field.nextField(way);
            }
        }
        return null;
    }

    private void turn_disks(Stack<BoardField> st){
        BoardField tmp;
        while (!st.empty()) {
            tmp = st.pop();
            tmp.getDisk().turn();
        }

    }

    public int getSize(){
        return size;
    }

    public void uiAlgorithmLevel1(Player UI ){
        int size = Game.rules.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (canPutDisk(i,j,UI)){
                    putDisk(i,j,UI);
                    return;
                }
            }
        }
    }

    public void uiAlgorithmLevel2(Player UI){
        Stack<BoardField> disks_for_turn;
        Stack<BoardField> max_stack = new Stack<BoardField>();
        BoardField  best_field = null;
        int size = Game.rules.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (canPutDisk(i,j,UI)){
                    BoardField  field =  Board.field[i][j];
                    for (BoardField.Direction way : BoardField.Direction.values()) {
                        disks_for_turn = chack_IN_direct(field, way,UI );
                        if (disks_for_turn != null && disks_for_turn.size() > max_stack.size()){
                            max_stack = disks_for_turn;
                            best_field = field;
                        }
                    }
                }
            }
        }
        turn_disks(max_stack);
        best_field.putDisk(new Disk(UI.isWhite()));
    }

    public void freezing_disks(){
        int conunt_disks = 2;
        Random time = new Random();
        int from = 10;
        int to = 100;
        int random_time = time.nextInt(to-from) + to;



    }


}

