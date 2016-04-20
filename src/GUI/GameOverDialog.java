package GUI;

import game.Player;

/**
 * Created by martin on 20/04/16.
 */
public class GameOverDialog {

    private int score1;
    private int score2;
    private Player player1;
    private Player player2;

    public String getMsg(int score1, int score2, Player player1, Player player2){

        this.score1 = score1;
        this.score2 = score2;
        this.player1 = player1;
        this.player2 = player2;

        String msg = "";
        if (score1 == score2){      //stalemate
            msg = msg + "Stalemate! Winners:\n  -"+player1.name+"\n  -"+player2.name+"\nScore: "+score1;
        }
        else if(player1.is_pc() || player2.is_pc()){
            msg = msg + createSinglePlayerGameOverMsg();
        }else{
            msg = msg + createMultiPlayerGameOverMsg();
        }

        msg = msg + "\nWould you like to play again?";

        return msg;
    }

    private String createMultiPlayerGameOverMsg(){
        String msg;
        if(score1 > score2){        //player1 won
            msg = player1.name + " won with score: " + score1;
        }else{                      //player2 won
            msg = player2.name + " won with score: " + score2;
        }
        return msg;
    }

    private String createSinglePlayerGameOverMsg(){
        String msg;
        if(player1.is_pc()) {
            msg = (score1 > score2) ? "You LOST. Computer won.\n" : "Congratulation!\nYou WON.\n";
            msg = msg + "Your score: " + score2 + "\n" +player1.name+": "+score1;
        }
        else{
            msg = (score1 < score2) ? "You LOST. Computer won.\n" : "Congratulation!\nYou WON.\n";
            msg = msg + "Your score: " + score1 + "\n" +player2.name+": "+score2;
        }
        return msg;
    }
}
