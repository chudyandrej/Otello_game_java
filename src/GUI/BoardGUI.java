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
    private JFrame frame;
    private JLabel board;
    private int boardSize;
    private Game game;
    static JLabel scoreLabel1;
    static JLabel scoreLabel2;
    static JLabel onTurnLabel;

    static ImageIcon whitePlayerDisc;
    static ImageIcon blackPlayerDisc;
    static ImageIcon fieldBackground;
    static ImageIcon fieldCanPutDisc;

    static BoardFieldLabel[][] fields;

    BoardGUI(JFrame frame, int boardSize, Player player1, Player player2){
        this.frame = frame;
        this.boardSize = boardSize;
        initImages();
        createBoard();

        frame.setContentPane(board);
        frame.validate();
        frame.repaint();

        System.out.format("%d %d \n", fields[0][0].getWidth(),fields[0][0].getHeight());

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
                w = 49;
                h = 47;
                frame.setMinimumSize(new Dimension(500, 480));
                break;
            case 10:
                w = 49;
                h = 46;
                frame.setMinimumSize(new Dimension(600, 600));
                break;
            case 12:
                w = 47;
                h = 44;
                frame.setMinimumSize(new Dimension(700, 700));
        }
        whitePlayerDisc = new ImageIcon(resizeImage("lib/white2.png", w, h));
        blackPlayerDisc = new ImageIcon(resizeImage("lib/black2.png", w, h));
        fieldBackground = new ImageIcon(resizeImage("lib/field.png", w, h));
        fieldCanPutDisc = new ImageIcon(resizeImage("lib/fieldCanPut.png", w, h));
    }

    static public void changeDisc(int x, int y, boolean isWhite){
        fields[x][y].setIcon( (isWhite) ? whitePlayerDisc : blackPlayerDisc );
        if(!fields[x][y].pressed){ fields[x][y].pressed = true; }
    }

    static private Image resizeImage(String imgName, int w, int h){
        BufferedImage resizedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
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
        board.setIcon(new ImageIcon(resizeImage("lib/background.jpg", frame.getWidth(), frame.getHeight()+70)));

        JToolBar topBar = new JToolBar();
        board.add(topBar);
        topBar.setBorder(BorderFactory.createEmptyBorder());
        topBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        topBar.setFloatable(false);
        topBar.setOpaque(false);
        topBar.setPreferredSize(new Dimension(frame.getWidth(), 38));
        board.add(topBar, BorderLayout.NORTH);

        JButton exitBtn = new JButton("Exit Game");
        topBar.add(exitBtn);

        JButton undoBtn = new JButton("Undo");
        topBar.add(undoBtn);

        JButton newGameBtn = new JButton("New Game");
        topBar.add(newGameBtn);

        Box playAreaContent = new Box(BoxLayout.Y_AXIS);
        playAreaContent.add(Box.createVerticalGlue());

        Box playAreaContent2 = new Box(BoxLayout.X_AXIS);
        playAreaContent2.add(Box.createHorizontalGlue());

        board.add(playAreaContent,BorderLayout.CENTER);
        playAreaContent.add(playAreaContent2);

        JPanel playArea = new JPanel();
        playAreaContent2.add(playArea);
        playArea.setBorder(new LineBorder(Color.black, 5));

        playAreaContent.add(Box.createVerticalGlue());
        playAreaContent2.add(Box.createHorizontalGlue());

        playArea.setLayout(new GridLayout(boardSize, boardSize));
        // playArea.addComponentListener(new resize());
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

    }

    static public void setGameState(int score1, int score2, String onTurn){
        onTurnLabel.setText("<html><font size='6' color='red' face='League Gothic'><b>"+onTurn+"</b></font></html>");
        scoreLabel1.setText("<html><font size='6' color='white' face='League Gothic'><b>"+score1+"</b></font></html>");
        scoreLabel2.setText("<html><font size='6' color='black' face='League Gothic'><b>"+score2+"</b></font></html>");
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
        public void mouseClicked(MouseEvent e) {
        }
        @Override
        public void mouseExited(MouseEvent e) {
            if(!label.pressed) {    //if there is no disc
                fields[label.row][label.col].setIcon(fieldBackground);
            }
        }
        @Override
        public void mouseReleased(MouseEvent e) {
        }
        @Override
        public void mousePressed(MouseEvent e) {
            Player tmp  = game.currentPlayer();
            System.out.format("%d:%d %s\n", label.row, label.col, tmp.isWhite());
            if (tmp.putDisk(label.row, label.col)) {
                changeDisc(label.row, label.col, tmp.isWhite());
                label.pressed = true;

                game.nextPlayer();
            }
        }
    }
}
