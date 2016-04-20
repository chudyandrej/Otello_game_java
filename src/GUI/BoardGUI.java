package GUI;

import game.Game;
import game.Player;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


/**
 * Created by martin on 16/04/16.
 */
public class BoardGUI {
    private static JFrame frame;
    private static JLabel board;
    private int boardSize;
    private Game game;
    static JLabel scoreLabel1;
    static JLabel scoreLabel2;
    static JLabel onTurnLabel;

    private Player player1;
    private Player player2;
    private static int score1;
    private static int score2;

    JLabel newGameBtn;
    JLabel exitGameBtn;
    JLabel undoBtn;
    JLabel saveBtn;

    static BoardFieldLabel[][] fields;

    static Images I;

    BoardGUI(JFrame frame, int boardSize, Player player1, Player player2){
        BoardGUI.frame = frame;
        this.boardSize = boardSize;
        this.player1 = player1;
        this.player2 = player2;

        I = new Images(frame, boardSize);

        initNewGame();

        System.out.format("%d %d \n", fields[0][0].getWidth(),fields[0][0].getHeight()); //debug
    }

    private void initNewGame(){
        createBoard();

        frame.setContentPane(board);
        frame.validate();
        frame.repaint();

        game = new Game(boardSize);
        game.addPlayer(player1);
        game.addPlayer(player2);
    }

    static public void deleteDisc(int x, int y){
        fields[x][y].pressed = false;
        fields[x][y].setIcon(I.fieldBackground);
    }

    static public void changeDisc(int x, int y, boolean isWhite){

        fields[x][y].setIcon((isWhite) ? I.whitePlayerFieldDisc : I.blackPlayerFieldDisc );
        if(!fields[x][y].pressed){ fields[x][y].pressed = true; }
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

    private void showGameOverDialog(){
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

        int result = JOptionPane.showConfirmDialog(frame, msg, "Game Over", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if(result == JOptionPane.YES_OPTION){       //start new game
            frame.remove(board);
            initNewGame();
        }else if(result == JOptionPane.NO_OPTION){  //go to main menu
            frame.remove(board);
            OthelloGUI.initMenuAgain(frame);
        }
    }

    public class BoardFieldLabel extends JLabel {
        public int row;
        public int col;
        public boolean pressed = false;

        BoardFieldLabel(int row, int col){
            this.row = row;
            this.col = col;
        }
    }

    private void createBoard(){
        board = new JLabel();
        board.setLayout(new BorderLayout());
        board.setIcon(new ImageIcon(Images.resizeImage("lib/background.jpg", frame.getWidth(), frame.getHeight()+70)));

        JToolBar topBar = new JToolBar();
        board.add(topBar);
        topBar.setBorder(BorderFactory.createEmptyBorder());
        topBar.setLayout(new FlowLayout(FlowLayout.RIGHT,10,15));
        topBar.setFloatable(false);
        topBar.setOpaque(false);
        topBar.setPreferredSize(new Dimension(frame.getWidth(), 40));
        board.add(topBar, BorderLayout.NORTH);

        newGameBtn = new JLabel(); // new game button
        //newGameBtn.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));
        newGameBtn.setSize(25, 25);
        newGameBtn.setToolTipText("Reload game");
        newGameBtn.addMouseListener(new boardButtonClicked(newGameBtn));
        newGameBtn.setIcon(new ImageIcon(Images.resizeImage("lib/icons/playAgain.png", 25, 25)));
        topBar.add(newGameBtn);

        exitGameBtn = new JLabel(); //exit game button
        exitGameBtn.setSize(25, 25);
        exitGameBtn.setToolTipText("Home");
        exitGameBtn.addMouseListener(new boardButtonClicked(exitGameBtn));
        exitGameBtn.setIcon(new ImageIcon(Images.resizeImage("lib/icons/home.png", 25, 25)));
        topBar.add(exitGameBtn);

        undoBtn = new JLabel(); //undo button
        //undoBtn.addActionListener(new undoBtnClicked());
        undoBtn.setSize(25, 25);
        undoBtn.setToolTipText("Undo");
        undoBtn.addMouseListener(new boardButtonClicked(undoBtn));
        undoBtn.setIcon(new ImageIcon(Images.resizeImage("lib/icons/undo.png", 25, 25)));
        topBar.add(undoBtn);

        saveBtn = new JLabel();//save button
        saveBtn.setSize(25, 25);
        saveBtn.setToolTipText("Save game");
        saveBtn.addMouseListener(new boardButtonClicked(saveBtn));
        saveBtn.setIcon(new ImageIcon(Images.resizeImage("lib/icons/saveGame.png", 25, 25)));
        topBar.add(saveBtn);


        Box playAreaContent = new Box(BoxLayout.Y_AXIS);
        //playAreaContent.setBorder(BorderFactory.createEmptyBorder());
        playAreaContent.add(Box.createVerticalGlue());

        Box playAreaContent2 = new Box(BoxLayout.X_AXIS);
        //playAreaContent2.setBorder(BorderFactory.createEmptyBorder());
        playAreaContent2.add(Box.createHorizontalGlue());

        board.add(playAreaContent,BorderLayout.CENTER);
        playAreaContent.add(playAreaContent2);

        JPanel playArea = new JPanel();
        //playArea.setBorder(BorderFactory.createEmptyBorder());
        playAreaContent2.add(playArea);
        playArea.setBorder(new LineBorder(Color.black, 5));

        playAreaContent.add(Box.createVerticalGlue());
        playAreaContent2.add(Box.createHorizontalGlue());

        playArea.setLayout(new GridLayout(boardSize, boardSize));

        switch (boardSize){
            case 6:
                playArea.setPreferredSize(new Dimension(250, 250));
                break;
            case 8:
                playArea.setPreferredSize(new Dimension(350, 350));
                break;
            case 10:
                playArea.setPreferredSize(new Dimension(450, 450));
                break;
            case 12:
                playArea.setPreferredSize(new Dimension(500, 500));
        }

        fields = new BoardFieldLabel[boardSize][boardSize];

        for(int row=0; row < boardSize; row++){
            for(int col=0; col < boardSize; col++){
                fields[row][col] = new BoardFieldLabel(row, col);
                fields[row][col].setBorder(BorderFactory.createLineBorder(Color.black));
                fields[row][col].setIcon(I.fieldBackground);
                fields[row][col].setOpaque(true);
                fields[row][col].addMouseListener(new boardFieldClicked( fields[row][col]));
                playArea.add(fields[row][col]);
            }
        }

        JToolBar menuBar = new JToolBar();
        menuBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        menuBar.setBorder(BorderFactory.createEmptyBorder());
        //menuBar.setBorder(new LineBorder(Color.black, 5));
        menuBar.setFloatable(false);
        menuBar.setOpaque(false);
        menuBar.setPreferredSize(new Dimension(frame.getWidth(), 60));
        board.add(menuBar, BorderLayout.SOUTH);

        //score
        JLabel player1Disc = new JLabel();
        player1Disc.setIcon(I.whiteDisc);
        menuBar.add(player1Disc);

        JLabel player1Image = new JLabel(player1.name);
        player1Image.setForeground(Color.white);
        menuBar.add(player1Image);

        scoreLabel1 = new JLabel();
        scoreLabel1.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 15));
        menuBar.add(scoreLabel1);

        onTurnLabel = new JLabel();
        onTurnLabel.setOpaque(false);
        menuBar.add(onTurnLabel);

        scoreLabel2 = new JLabel();
        scoreLabel2.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 10));
        menuBar.add(scoreLabel2);

        JLabel player2Image = new JLabel(player2.name);
        player2Image.setForeground(Color.white);
        menuBar.add(player2Image);

        JLabel player2Disc = new JLabel();
        player2Disc.setIcon(I.blackDisc);
        menuBar.add(player2Disc);

        setGameState(2, 2, false); //initialize
    }

    static public void setGameState(int score1, int score2, boolean isWhite){
        onTurnLabel.setIcon( (isWhite)? I.arrowR : I.arrowL);
        scoreLabel1.setText("<html><font size='6' color='white' face='League Gothic'><b>"+score1+"</b></font></html>");
        scoreLabel2.setText("<html><font size='6' color='white' face='League Gothic'><b>"+score2+"</b></font></html>");
        BoardGUI.score1 = score1;
        BoardGUI.score2 = score2;
    }

    private class boardFieldClicked implements MouseListener {
        private BoardFieldLabel label;

        boardFieldClicked(BoardFieldLabel label){
            this.label = label;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if(!label.pressed) {
                if (game.currentPlayer().canPutDisk(label.row, label.col)) {
                    fields[label.row][label.col].setIcon(I.fieldCanPutDisc);
                }
            }
        }
        @Override
        public void mouseExited(MouseEvent e) {
            if(!label.pressed) {    //if there is no disc
                fields[label.row][label.col].setIcon(I.fieldBackground);
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {
            Player tmp  = game.currentPlayer();

            if (tmp.putDisk(label.row, label.col)) {
                label.pressed = true;
                game.nextPlayer();

                if(game.gameOver){
                    showGameOverDialog();
                }
            }
        }
        @Override
        public void mouseReleased(MouseEvent e) {
        }
        @Override
        public void mouseClicked(MouseEvent e) {
        }
    }

    public class boardButtonClicked implements MouseListener {
        private JLabel button;

        boardButtonClicked(JLabel button){
            this.button = button;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if(button == newGameBtn){
                newGameBtn.setIcon(I.newGameBtnImage);
            }else if (button == exitGameBtn){
                exitGameBtn.setIcon(I.homeBtnImage);
            }else if(button == undoBtn){
                undoBtn.setIcon(I.undoBtnImage);
            }else if(button == saveBtn){
                saveBtn.setIcon(I.saveBtnImage);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if(button == newGameBtn){
                newGameBtn.setIcon(I.newGameBtnImageE);
            }else if (button == exitGameBtn){
                exitGameBtn.setIcon(I.homeBtnImageE);
            }else if(button == undoBtn){
                undoBtn.setIcon(I.undoBtnImageE);
            }else if(button == saveBtn){
                saveBtn.setIcon(I.saveBtnImageE);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if(button == newGameBtn){
                newGameBtn.setIcon(I.newGameBtnImageP);
            }else if (button == exitGameBtn){
                exitGameBtn.setIcon(I.homeBtnImageP);
            }else if(button == undoBtn){
                undoBtn.setIcon(I.undoBtnImageP);
            }else if(button == saveBtn){
                saveBtn.setIcon(I.saveBtnImageP);
            }
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            mouseEntered(e);
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            if(button == newGameBtn){
                int option = JOptionPane.showConfirmDialog(frame, "Would you like to reload game?", "Warning",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if(option == JOptionPane.YES_OPTION){
                    frame.remove(board);
                    initNewGame();
                }
            }else if (button == exitGameBtn){
                int option = JOptionPane.showConfirmDialog(frame, "Would you like to quit the game and enter the menu?",
                        "Warning", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if(option == JOptionPane.YES_OPTION){
                    frame.remove(board);
                    OthelloGUI.initMenuAgain(frame);
                }
            }else if(button == undoBtn){
                game.undo();
            }else if(button == saveBtn){
                try {
                    FileOutputStream fileOut = new FileOutputStream("employee.ser");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(game.backupGame);
                    out.close();
                    fileOut.close();

                }
                catch(IOException i) {
                    i.printStackTrace();
                }

                JOptionPane.showMessageDialog(frame, "Game was successfully saved", "Game Saved",
                        JOptionPane.INFORMATION_MESSAGE, I.whiteDisc);
            }

        }
    }
}
