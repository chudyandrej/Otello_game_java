package GUI;

import game.Player;

/**
 * Created by martin on 20/04/16.
 */
public final class GameOverDialog {

    public static String getMsg(int score1, int score2, Player player1, Player player2){

        String msg;
        if (score1 == score2){      //stalemate
            msg = "Stalemate! Winners:\n  -"+player1.name+"\n  -"+player2.name+"\nScore: "+score1;
        }
        else if(player1.is_pc() || player2.is_pc()){
            msg = createSinglePlayerGameOverMsg(score1, score2, player1, player2);
        }else{
            msg = createMultiPlayerGameOverMsg(score1, score2, player1, player2);
        }

        msg = msg + "\nWould you like to play again?";

        return msg;
    }

    public static String createMultiPlayerGameOverMsg(int score1, int score2, Player player1, Player player2){
        String msg;
        if(score1 > score2){        //player1 won
            msg = player1.name + " won with score: " + score1;
        }else{                      //player2 won
            msg = player2.name + " won with score: " + score2;
        }
        return msg;
    }

    public static String createSinglePlayerGameOverMsg(int score1, int score2, Player player1, Player player2){
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
