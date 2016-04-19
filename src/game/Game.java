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
    public Backup backupGame;
    static public ReversiRules rules;

    public Game(int size){
        backupGame = new Backup();
        rules = new ReversiRules(size,backupGame);


    }

    public boolean addPlayer(Player newPlayer){

        if(newPlayer.isWhite() && white == null){
            white = newPlayer;
            currentPlayer = newPlayer;
            return true;

        }else if (!newPlayer.isWhite() && black == null){
            black = newPlayer;
            return true;
        }

        return false;
    }



    public Player currentPlayer(){
        return currentPlayer;
    }

    public Player nextPlayer(){
        changeScore();
        currentPlayer = (currentPlayer == black) ? white : black;
        if (currentPlayer.is_pc() &&  exitsingTurn(currentPlayer)) {
            currentPlayer.uiTurn(this);


            return null;

        }
        else if(exitsingTurn(currentPlayer)) {
            return currentPlayer;
        }
        else if (!exitsingTurn(white) && !exitsingTurn(black)){
            System.out.printf("Game end!!!!\n");
            return white; ///// !!!!!!Game Over
        }
        currentPlayer = (currentPlayer == black) ? white : black;
        System.out.printf("Skip TurnBackUp!!!!\n");
        return currentPlayer;

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
                else if(!tmp.isWhite()){blac_Dsik++; }
            }
        }
        BoardGUI.setGameState(blac_Dsik, white_Disk, currentPlayer.isWhite());
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
        Backup.TurnBackUp lastTurn;
        if (backupGame.backupTurns.size() > 4 ) {
            lastTurn = (Backup.TurnBackUp) backupGame.backupTurns.get(backupGame.backupTurns.size() - 1);
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
