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
    public Game game;
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

    static public void freezeField(int x, int y){
        fields[x][y].freeze();
    }

    static public void unFreezeField(int x, int y){
        fields[x][y].unFreeze();
    }

    static public void deleteDisc(int x, int y){
        fields[x][y].deleteDisc();
    }

    static public void changeDisc(int x, int y, boolean isWhite){
        fields[x][y].setDisc(isWhite);
    }

    public void showGameOverDialog(){

        String msg  = GameOverDialog.getMsg(score1, score2, player1, player2);

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

    private void setTopBar(JToolBar topBar){
        topBar.setBorder(BorderFactory.createEmptyBorder());
        topBar.setLayout(new FlowLayout(FlowLayout.RIGHT,10,15));
        topBar.setFloatable(false);
        topBar.setOpaque(false);
        topBar.setPreferredSize(new Dimension(frame.getWidth(), 40));

        newGameBtn = new JLabel(); // new game button
        newGameBtn.setSize(25, 25);
        newGameBtn.setToolTipText("Reload game");
        newGameBtn.addMouseListener(new boardButtonClicked(newGameBtn));
        newGameBtn.setIcon(I.newGameBtnImage);
        topBar.add(newGameBtn);

        exitGameBtn = new JLabel(); //exit game button
        exitGameBtn.setSize(25, 25);
        exitGameBtn.setToolTipText("Home");
        exitGameBtn.addMouseListener(new boardButtonClicked(exitGameBtn));
        exitGameBtn.setIcon(I.homeBtnImage);
        topBar.add(exitGameBtn);

        undoBtn = new JLabel(); //undo button
        undoBtn.setSize(25, 25);
        undoBtn.setToolTipText("Undo");
        undoBtn.addMouseListener(new boardButtonClicked(undoBtn));
        undoBtn.setIcon(I.undoBtnImage);
        topBar.add(undoBtn);

        saveBtn = new JLabel();//save button
        saveBtn.setSize(25, 25);
        saveBtn.setToolTipText("Save game");
        saveBtn.addMouseListener(new boardButtonClicked(saveBtn));
        saveBtn.setIcon(I.saveBtnImage);
        topBar.add(saveBtn);
    }

    private void setBottomBar(JToolBar bottomBar){
        bottomBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomBar.setBorder(BorderFactory.createEmptyBorder());
        //bottomBar.setBorder(new LineBorder(Color.black, 5));
        bottomBar.setFloatable(false);
        bottomBar.setOpaque(false);
        bottomBar.setPreferredSize(new Dimension(frame.getWidth(), 60));

        //score
        JLabel player1Disc = new JLabel();
        player1Disc.setIcon(I.whiteDisc);
        bottomBar.add(player1Disc);

        JLabel player1Image = new JLabel(player1.name);
        player1Image.setForeground(Color.white);
        bottomBar.add(player1Image);

        scoreLabel1 = new JLabel();
        scoreLabel1.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 15));
        bottomBar.add(scoreLabel1);

        onTurnLabel = new JLabel();
        onTurnLabel.setOpaque(false);
        bottomBar.add(onTurnLabel);

        scoreLabel2 = new JLabel();
        scoreLabel2.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 10));
        bottomBar.add(scoreLabel2);

        JLabel player2Image = new JLabel(player2.name);
        player2Image.setForeground(Color.white);
        bottomBar.add(player2Image);

        JLabel player2Disc = new JLabel();
        player2Disc.setIcon(I.blackDisc);
        bottomBar.add(player2Disc);
    }

    private void setPlayArea(JPanel playArea){
        playArea.setBorder(new LineBorder(Color.black, 5));
        playArea.setBorder(null);

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
                fields[row][col] = new BoardFieldLabel(row, col, I, this);
                playArea.add(fields[row][col]);
            }
        }
    }

    private void createBoard(){
        board = new JLabel();
        board.setLayout(new BorderLayout());
        board.setIcon(new ImageIcon(Images.resizeImage("lib/background.jpg", frame.getWidth(), frame.getHeight()+70)));


        JToolBar topBar = new JToolBar();
        board.add(topBar, BorderLayout.NORTH);


        Box playAreaContent = new Box(BoxLayout.Y_AXIS);
        Box playAreaContent2 = new Box(BoxLayout.X_AXIS);
        //playAreaContent.setBorder(BorderFactory.createEmptyBorder());
        playAreaContent.add(Box.createVerticalGlue());
        playAreaContent2.add(Box.createHorizontalGlue());

        board.add(playAreaContent, BorderLayout.CENTER);
        playAreaContent.add(playAreaContent2);

        JPanel playArea = new JPanel();
        playArea.setOpaque(false);
        playAreaContent2.add(playArea);

        playAreaContent.add(Box.createVerticalGlue());
        playAreaContent2.add(Box.createHorizontalGlue());


        JToolBar bottomBar = new JToolBar();
        board.add(bottomBar, BorderLayout.SOUTH);


        setTopBar(topBar);
        setPlayArea(playArea);
        setBottomBar(bottomBar);


        setGameState(2, 2, false); //initialize
    }

    static public void setGameState(int score1, int score2, boolean isWhite){
        onTurnLabel.setIcon( (isWhite)? I.arrowR : I.arrowL);
        scoreLabel1.setText("<html><font size='6' color='white' face='League Gothic'><b>"+score1+"</b></font></html>");
        scoreLabel2.setText("<html><font size='6' color='white' face='League Gothic'><b>"+score2+"</b></font></html>");
        BoardGUI.score1 = score1;
        BoardGUI.score2 = score2;
    }

    public class boardButtonClicked implements MouseListener {
        private JLabel button;

        boardButtonClicked(JLabel button){
            this.button = button;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
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
        public void mouseExited(MouseEvent e) {
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
