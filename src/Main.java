import board.BoardField;
import game.Game;

public class Main {

    public static void main(String[] args) {
        Game new_game = new Game(10);
        new_game.addPlayer(true);
        new_game.addPlayer(false);
        System.out.printf("ide hrac: %s\n",new_game.nextPlayer());
        System.out.printf("ide hrac: %s\n",new_game.nextPlayer());
        System.out.printf("ide hrac: %s\n",new_game.nextPlayer());
        System.out.printf("%s",new_game.nextPlayer().canPutDisk(1,1));



    }
}
