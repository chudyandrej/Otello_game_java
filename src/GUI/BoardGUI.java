package GUI;

import game.Game;
import game.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
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
    private JPanel board;
    private int boardSize;
    private Game game;

    static BoardFieldLabel[][] fields;

    BoardGUI(JFrame frame, int boardSize, Player player1, Player player2){
        this.frame = frame;
        this.boardSize = boardSize;
        createBoard();

        frame.setContentPane(board);
        frame.validate();
        frame.repaint();

        game = new Game(boardSize);
        game.addPlayer(player1);
        game.addPlayer(player2);
    }

    static public void changeDisc(int x, int y, boolean isWhite){
        String imageName = (isWhite) ? "lib/white.png" : "lib/black.png";
        System.out.format("%d %d\n", x, y);
        ImageIcon imageIcon = new ImageIcon(resizeImage(imageName, fields[x][y].getWidth(), fields[x][y].getHeight()));
        fields[x][y].setIcon(imageIcon);
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
        board = new JPanel();
        board.setLayout(new BorderLayout());

        JToolBar menuBar = new JToolBar();
        menuBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        menuBar.setBackground(Color.decode("#54AFE8"));
        board.add(menuBar, BorderLayout.NORTH);

        JButton undo = new JButton("UNDO");
        menuBar.add(undo);

        Box playAreaContent = new Box(BoxLayout.Y_AXIS);
        playAreaContent.setBackground(Color.red);
        playAreaContent.add(Box.createVerticalGlue());
        Box playAreaContent2 = new Box(BoxLayout.X_AXIS);
        playAreaContent2.setBackground(Color.cyan);
        playAreaContent2.add(Box.createHorizontalGlue());

        board.add(playAreaContent);
        playAreaContent.add(playAreaContent2);

        JPanel playArea = new JPanel();
        playAreaContent2.add(playArea);

        playAreaContent.add(Box.createVerticalGlue());
        playAreaContent2.add(Box.createHorizontalGlue());

        playArea.setLayout(new GridLayout(boardSize, boardSize));
        // playArea.addComponentListener(new resize());
        //playArea.setMinimumSize(new Dimension(300, 300));
        playArea.setPreferredSize(new Dimension(400, 400));
        //playArea.setMaximumSize(new Dimension(800, 800));

        fields = new BoardFieldLabel[boardSize][boardSize];

        for(int row=0; row < boardSize; row++){
            for(int col=0; col < boardSize; col++){
                fields[row][col] = new BoardFieldLabel(row, col);
                fields[row][col].setBorder(BorderFactory.createLineBorder(Color.black));
                fields[row][col].setBackground(Color.decode("#06943C")); //green
                fields[row][col].setOpaque(true);
                fields[row][col].addMouseListener(new boardFieldClicked( fields[row][col]));
                playArea.add(fields[row][col]);
                System.out.format("INIT: %d %d\n", row,col);
            }
        }
    }

    private class boardFieldClicked implements MouseListener {
        private BoardFieldLabel panel;

        boardFieldClicked(BoardFieldLabel panel){
            this.panel = panel;
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            if(!panel.pressed) {
                if (game.currentPlayer().canPutDisk(panel.row,panel.col)) {
                    panel.setBackground(Color.red);
                }
            }
        }
        @Override
        public void mouseClicked(MouseEvent e) {
        }
        @Override
        public void mouseExited(MouseEvent e) {
            if(!panel.pressed) {
                panel.setBackground(Color.decode("#06943C")); //green
            }
        }
        @Override
        public void mouseReleased(MouseEvent e) {
        }
        @Override
        public void mousePressed(MouseEvent e) {
            Player tmp  = game.currentPlayer();
            System.out.format("%d:%d %s\n", panel.row, panel.col, tmp.isWhite());
            if (tmp.putDisk(panel.row,panel.col)) {
                changeDisc(panel.row, panel.col, tmp.isWhite());
                panel.pressed = true;

                 game.nextPlayer();
            }
        }
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
}
