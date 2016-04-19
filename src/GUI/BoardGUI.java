package GUI;

import game.Game;
import game.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

    static ImageIcon whitePlayerFieldDisc;
    static ImageIcon blackPlayerFieldDisc;
    static Image whiteTest;
    static Image blackTest;
    static ImageIcon fieldBackground;
    static ImageIcon fieldCanPutDisc;
    static ImageIcon arrowL;
    static ImageIcon arrowR;

    JLabel newGameBtn;
    JLabel exitGameBtn;
    JLabel undoBtn;
    JLabel saveBtn;

    static BoardFieldLabel[][] fields;

    BoardGUI(JFrame frame, int boardSize, Player player1, Player player2){
        this.frame = frame;
        this.boardSize = boardSize;
        this.player1 = player1;
        this.player2 = player2;
        initImages();
        initNewGame();

        //JOptionPane.showConfirmDialog(frame, "hello world, you FAILED");
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

    private void initImages(){
        int w = 0;
        int h = 0;
        switch (this.boardSize){
            case 6:
                w = 55;
                h = 52;
                frame.setMinimumSize(new Dimension(500, 480));
                break;
            case 8:
                w = 48;
                h = 45;
                frame.setMinimumSize(new Dimension(500, 480));
                break;
            case 10:
                w = 48;
                h = 44;
                frame.setMinimumSize(new Dimension(600, 600));
                break;
            case 12:
                w = 47;
                h = 44;
                frame.setMinimumSize(new Dimension(700, 700));
        }
        whitePlayerFieldDisc = new ImageIcon(resizeImage("lib/white2.png", w, h));
        blackPlayerFieldDisc = new ImageIcon(resizeImage("lib/black2.png", w, h));
        fieldBackground = new ImageIcon(resizeImage("lib/field.png", w, h));
        fieldCanPutDisc = new ImageIcon(resizeImage("lib/fieldCanPut.png", w, h));
        arrowL = new ImageIcon(resizeImage("lib/arrow_l.png", 25, 25));
        arrowR = new ImageIcon(resizeImage("lib/arrow_r.png", 25, 25));
        whiteTest = resizeImage("lib/white2.png", w, h);
        blackTest = resizeImage("lib/black2.png", w, h);
    }

    static public void deleteDisc(int x, int y){
        fields[x][y].pressed = false;
        fields[x][y].setIcon(fieldBackground);
    }

    static public void changeDisc(int x, int y, boolean isWhite){

        fields[x][y].setIcon((isWhite) ? whitePlayerFieldDisc : blackPlayerFieldDisc );
        if(!fields[x][y].pressed){ fields[x][y].pressed = true; }
    }

    static public Image resizeImage(String imgName, int w, int h){
        BufferedImage resizedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB); //TYPE_INT_ARGB makes parts of transparent image be transparent
        Graphics2D g = resizedImage.createGraphics();
        //g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Image img = null;
        try{
            img = ImageIO.read(new File(imgName));
        }catch(IOException ex){
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
        g.drawImage(img, 0, 0, w, h, null);
        g.dispose();
        return resizedImage;
    }

    private void showGameOverDialog(){
        String msg = "Congratulation!\n";
        if(score1 > score2){ //player1 won
            msg = msg + player1.name + " won with score: " + score1;
        }else if(score1 < score2){ //player2 won
            msg = msg + player2.name + " won with score: " + score2;
        }else{      //stalemate
            msg = "Stalemate! Winners:\n  -"+player1.name+"\n  -"+player2.name+"\nScore: "+score1;
        }
        msg = msg + "\nWould you like to play again?";

        int result = JOptionPane.showConfirmDialog(frame, msg);
        if(result == JOptionPane.YES_OPTION){
            frame.remove(board);
            initNewGame();
        }
    }

    public class BoardFieldLabel extends JLabel {
        public int row;
        public int col;
        public boolean pressed = false;
        private boolean state;

        BoardFieldLabel(int row, int col){
            this.row = row;
            this.col = col;
        }
    }

    private void createBoard(){
        board = new JLabel();
        board.setLayout(new BorderLayout());
        board.setIcon(new ImageIcon(resizeImage("lib/background.jpg", frame.getWidth(), frame.getHeight()+70)));

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
        newGameBtn.setIcon(new ImageIcon(resizeImage("lib/icons/playAgain.png", 25, 25)));
        topBar.add(newGameBtn);

        exitGameBtn = new JLabel(); //exit game button
        exitGameBtn.setSize(25, 25);
        exitGameBtn.setToolTipText("Home");
        exitGameBtn.addMouseListener(new boardButtonClicked(exitGameBtn));
        exitGameBtn.setIcon(new ImageIcon(resizeImage("lib/icons/home.png", 25, 25)));
        topBar.add(exitGameBtn);

        undoBtn = new JLabel(); //undo button
        //undoBtn.addActionListener(new undoBtnClicked());
        undoBtn.setSize(25, 25);
        undoBtn.setToolTipText("Undo");
        undoBtn.addMouseListener(new boardButtonClicked(undoBtn));
        undoBtn.setIcon(new ImageIcon(resizeImage("lib/icons/undo.png", 25, 25)));
        topBar.add(undoBtn);

        saveBtn = new JLabel();//save button
        saveBtn.setSize(25, 25);
        saveBtn.setToolTipText("Save game");
        saveBtn.addMouseListener(new boardButtonClicked(saveBtn));
        saveBtn.setIcon(new ImageIcon(resizeImage("lib/icons/saveGame.png", 25, 25)));
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
                fields[row][col].setIcon(fieldBackground);
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
        JLabel player1Label = new JLabel();
        player1Label.setOpaque(true);
        player1Label.setBackground(Color.white);
        //menuBar.add(player1Label);

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

        setGameState(2, 2, false);
    }

    static public void setGameState(int score1, int score2, boolean isWhite){
        onTurnLabel.setIcon( (isWhite)? arrowR : arrowL);
        System.out.format("set: %s\n", isWhite);
        scoreLabel1.setText("<html><font size='6' color='white' face='League Gothic'><b>"+score1+"</b></font></html>");
        scoreLabel2.setText("<html><font size='6' color='black' face='League Gothic'><b>"+score2+"</b></font></html>");
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
                    fields[label.row][label.col].setIcon(fieldCanPutDisc);
                }
            }
        }
        @Override
        public void mouseExited(MouseEvent e) {
            if(!label.pressed) {    //if there is no disc
                fields[label.row][label.col].setIcon(fieldBackground);
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {
            Player tmp  = game.currentPlayer();
            System.out.format("%s\n", tmp.isWhite());

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
                newGameBtn.setIcon(new ImageIcon(resizeImage("lib/icons/playAgainEntered.png", 25, 25)));
            }else if (button == exitGameBtn){
                exitGameBtn.setIcon(new ImageIcon(resizeImage("lib/icons/homeEntered.png", 25, 25)));
            }else if(button == undoBtn){
                undoBtn.setIcon(new ImageIcon(resizeImage("lib/icons/undoEntered.png", 25, 25)));
            }else if(button == saveBtn){
                saveBtn.setIcon(new ImageIcon(resizeImage("lib/icons/saveGameEntered.png", 25, 25)));
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if(button == newGameBtn){
                newGameBtn.setIcon(new ImageIcon(resizeImage("lib/icons/playAgain.png", 25, 25)));
            }else if (button == exitGameBtn){
                exitGameBtn.setIcon(new ImageIcon(resizeImage("lib/icons/home.png", 25, 25)));
            }else if(button == undoBtn){
                undoBtn.setIcon(new ImageIcon(resizeImage("lib/icons/undo.png", 25, 25)));
            }else if(button == saveBtn){
                saveBtn.setIcon(new ImageIcon(resizeImage("lib/icons/saveGame.png", 25, 25)));
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if(button == newGameBtn){
                newGameBtn.setIcon(new ImageIcon(resizeImage("lib/icons/playAgainPressed.png", 25, 25)));
            }else if (button == exitGameBtn){
                exitGameBtn.setIcon(new ImageIcon(resizeImage("lib/icons/homePressed.png", 25, 25)));
            }else if(button == undoBtn){
                undoBtn.setIcon(new ImageIcon(resizeImage("lib/icons/undoPressed.png", 25, 25)));
            }else if(button == saveBtn){
                saveBtn.setIcon(new ImageIcon(resizeImage("lib/icons/saveGamePressed.png", 25, 25)));
            }
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            mouseEntered(e);
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            if(button == newGameBtn){
                int option = JOptionPane.showConfirmDialog(frame, "Do you want reload game?");
                if(option == JOptionPane.YES_OPTION){
                    frame.remove(board);
                    initNewGame();
                }
            }else if (button == exitGameBtn){
                int option = JOptionPane.showConfirmDialog(frame, "Would you like to quit the game and enter the menu?");
                if(option == JOptionPane.YES_OPTION){
                    frame.remove(board);
                    OthelloGUI.initMenuAgain(frame);
                }
            }else if(button == undoBtn){
                game.undo();
            }else if(button == saveBtn){
            }
        }

    }
}
