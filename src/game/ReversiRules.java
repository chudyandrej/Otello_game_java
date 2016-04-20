package game;

import board.Board;
import board.BoardField;

import board.Disk;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by andrejchudy on 15/04/16.
 */

public class ReversiRules {

    private int size;
    private Board playBoard;
    private Backup backupGame;


    public ReversiRules(int size, Backup backupGame){
        this.size = size;
        this.backupGame = backupGame;
        playBoard = new Board(size);

        //starting position
        Board.field[ (size/2)-1][(size/2)-1].putDisk(new Disk(true));

        Board.field[(size/2)][(size/2)].putDisk(new Disk(true));

        Board.field[(size/2)-1][(size/2)].putDisk(new Disk(false));

        Board.field[(size/2)][(size/2)-1].putDisk(new Disk(false));


    }

    public boolean canPutDisk(int x, int y, Player playerTurn){
        BoardField  field =  Board.field[x][y];
        if(field.getDisk() == null && !field.isFreez) {
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
        List disks_for_turn;
        boolean success = false;
        if(field.getDisk() == null && !field.isFreez) {
            for (BoardField.Direction way : BoardField.Direction.values()) {
                disks_for_turn = chack_IN_direct(field, way,playerTurn );
                if (disks_for_turn != null){
                    if(!success){backupGame.create_NewTurn(field,playerTurn);}
                    backupGame.add_TurnedDisks(disks_for_turn);
                    turn_disks(disks_for_turn);
                    success = true;
                }
            }
            if (success){
                field.putDisk(new Disk(playerTurn.isWhite()));
                backupGame.save_BackupRecord();
            }
        }
        return success;
    }

    private List chack_IN_direct(BoardField  field, BoardField.Direction way, Player playerTurn){
        field = field.nextField(way);
        if (field != null && !field.isFreez && field.getDisk() != null  && field.getDisk().isWhite() != playerTurn.isWhite()) {
            List <BoardField> candidate_turn = new ArrayList<BoardField>();
            while (field != null && field.getDisk() != null) {
                if (field.getDisk().isWhite() == playerTurn.isWhite()) {
                    return candidate_turn ;
                }
                candidate_turn.add(field);
                field = field.nextField(way);
            }
        }
        return null;
    }

    public void turn_disks(List st){
        BoardField tmp;
       // System.out.printf("turnDisk : %d\n",st.size());
        while (!st.isEmpty()){
            tmp = (BoardField) st.get(st.size()-1);
            tmp.getDisk().turn();
            st.remove(tmp);
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
        List disksForTurn;
        List maxTurns = new ArrayList<BoardField>();
        BoardField  best_field = null;
        int size = Game.rules.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (canPutDisk(i,j,UI)){
                    BoardField  field =  Board.field[i][j];
                    for (BoardField.Direction way : BoardField.Direction.values()) {
                        disksForTurn =  chack_IN_direct(field, way,UI );
                        if (disksForTurn != null && disksForTurn.size() > maxTurns.size()){
                            maxTurns = disksForTurn;
                            best_field = field;
                        }
                    }
                }
            }
        }
        if (!maxTurns.isEmpty()) {
            backupGame.create_NewTurn(best_field,UI);
            backupGame.add_TurnedDisks(maxTurns);
            assert best_field != null;
            best_field.putDisk(new Disk(UI.isWhite()));
            turn_disks(maxTurns);
            backupGame.save_BackupRecord();
        }
    }



}

