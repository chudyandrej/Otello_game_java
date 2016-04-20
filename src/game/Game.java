package game;


import GUI.BoardGUI;
import board.Board;
import board.Disk;

/**
 * Created by andrejchudy on 15/04/16.
 */
public class Game {

    private Player currentPlayer;
    private Player black;
    private Player white;
    public boolean gameOver;
    public Backup backupGame;

    static public ReversiRules rules;

    public Game(int size){
        backupGame = new Backup();
        backupGame.sizeBoard = size;
        rules = new ReversiRules(size,backupGame);
        gameOver = false;
        currentPlayer = white;

    }

    public boolean addPlayer(Player newPlayer){

        if(newPlayer.isWhite() && white == null){
            white = newPlayer;
            currentPlayer = newPlayer;
            backupGame.player1 = white;
            return true;

        }else if (!newPlayer.isWhite() && black == null){
            black = newPlayer;
            backupGame.player2 = black;
            return true;
        }

        return false;
    }

    public Player currentPlayer(){
        return currentPlayer;
    }

    public void nextPlayer(){
        changeScore();
        currentPlayer = (currentPlayer == black) ? white : black;
        if (currentPlayer.is_pc()) {
            System.out.printf("Bot: %s\n",currentPlayer.isWhite());
            currentPlayer.uiTurn(this);
            return;

        }
        else if(exitsingTurn(currentPlayer)) {
            System.out.printf("Regular Player: %s\n",currentPlayer.isWhite());
            return;
        }
        else if (!exitsingTurn(white) && !exitsingTurn(black)){
            System.out.printf("Game over: %s",currentPlayer.isWhite());
            changeScore();
            gameOver = true;
            return;

        }
        System.out.printf("else pred: %s\n",currentPlayer.isWhite());
        currentPlayer = (currentPlayer == black) ? white : black;
        if (currentPlayer.is_pc()) {
            currentPlayer.uiTurn(this);
        }

        System.out.printf("else po: %s\n",currentPlayer.isWhite());


    }

    private void changeScore(){
        int white_Disk = 0;
        int blac_Dsik = 0;
        int size = Game.rules.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Disk tmp = Board.field[i][j].getDisk();
                if (tmp == null){
                    continue;
                }
                else if(tmp.isWhite()){ white_Disk++; }
                else if(!tmp.isWhite()){ blac_Dsik++; }
            }
        }
        BoardGUI.setGameState(white_Disk, blac_Dsik, currentPlayer.isWhite());
    }

    public boolean exitsingTurn(Player player_on_turn) {
        int size = Game.rules.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (player_on_turn.canPutDisk(i,j)){
                    return true;
                }
            }
        }
        return false;
    }

    public void undo(){
        gameOver = false;
        Backup.TurnBackUp lastTurn;
        if (backupGame.backupTurns.size() > 4 ) {
            lastTurn = backupGame.backupTurns.get(backupGame.backupTurns.size() - 1);
            lastTurn.base_Point.deleteDisk();
            rules.turn_disks(lastTurn.turned);
            backupGame.backupTurns.remove(lastTurn);
            currentPlayer = lastTurn.turn_player;
            if (currentPlayer.is_pc()){
                undo();
            }
            changeScore();
        }
    }





}