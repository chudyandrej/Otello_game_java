package game;

import board.BoardField;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by andrejchudy on 19/04/16.
 */
public class backup {
    private turn new_turn;
    private List backupTurns;

    backup(){
        backupTurns = new ArrayList();
    }

    public void create_NewTurn(BoardField base_Point){
        new_turn = new turn(base_Point);

    }

    public void add_TurnedDisks(Stack<BoardField> turnndDisk){
        new_turn.add_Stack_turned(turnndDisk);

    }

    public void undo(){
        turn lastTurn;
        lastTurn = (turn) backupTurns.get(backupTurns.size()-1);
        lastTurn.base_Point.deleteDisk();
        turn_disks(lastTurn.turned);
        backupTurns.remove(lastTurn);
    }

    private void turn_disks(List st){
        System.out.printf("SIZE    %d    \n\n",st.size());
        BoardField tmp;
        while (!st.isEmpty()){
            tmp = (BoardField) st.get(st.size()-1);
            tmp.getDisk().turn();
            st.remove(tmp);
        }

    }

    public void save_BackupRecord(){
        backupTurns.add(new_turn);
    }

    private class turn {
        private BoardField base_Point;
        private List turned;
        turn(BoardField base_Point){
            turned = new ArrayList();
            this.base_Point = base_Point;
        }
        public void add_Stack_turned(Stack<BoardField> turnndDisk){
            turned.addAll(turnndDisk);
        }
    }
}
