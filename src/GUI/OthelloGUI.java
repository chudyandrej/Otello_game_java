/**
 * This class creates main window, menu and controls switching of menu pages,
 * contains listeners for each button in menu and provides actions when
 * event occurs.
 * 
 * @author  Andrej Chudý
 * @author  Martin Kopec
 * @date 01.05.2016
 */

package GUI;

import game.Backup;
import game.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * This class creates main window, menu and controls switching of menu pages,
 * contains listeners for each button in menu and provides actions when
 * event occurs.
 */
public class OthelloGUI {

    private boolean singlePlayer = false;
    private byte mode;
    public int boardSize;
    private String player1Name = "Player1";
    private String player2Name = "Player2";
    private String computerName = "Computer";
    private int discsToFreeze = 0;
    private int CHTime = 0;
    private int FTime = 0;

    public JFrame frame;
    public settingsMenu settings = null;

    public static Box mainMenu;
    public static Box chooseMode;
    public static Box chooseBoardSize;
    public static Box previousPage = null;
    public static BackgroundPane bg;

    Dimension buttonDimension = new Dimension(150, 40);

    JButton singlePlayerBtn = new JButton("Single Player");
    JButton multiPlayerBtn = new JButton("Multiplayer");
    JButton loadGameBtn = new JButton("Load Game");
    JButton settingsBtn = new JButton("Settings");
    JButton exitBtn = new JButton("Exit");
    JButton beginnerModeBtn = new JButton("Beginner");
    JButton advancedModeBtn = new JButton("Advanced");
    JButton size6x6Btn = new JButton("6x6");
    JButton size8x8Btn = new JButton("8x8");
    JButton size10x10Btn = new JButton("10x10");
    JButton size12x12Btn = new JButton("12x12");
    JButton backBtn = new JButton("Back");

    ImageIcon background;
    ImageIcon buttonImage;

    /**
     * GUI constructor, crates main frame and initializes menu.
     */
    public OthelloGUI(){
        frame = new JFrame("Othello");

        background = new ImageIcon(Images.resizeImage("/background.jpg", 500, 550));
        buttonImage = new ImageIcon(Images.resizeImage("/button.jpg", 170, 40));

        createMainPage();
        createChooseGameModePage();
        createChooseSizeOfBoardPage();

        bg = new BackgroundPane();
        bg.setLayout(new GridLayout());
        bg.add(mainMenu);

        frame.add(bg);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 550));
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    /**
     * Method removes everything on frame and
     * initializes menu.
     * @param frame - main window
     */
    public static void initMenuAgain(JFrame frame){
        frame.setMinimumSize(new Dimension(500, 550));
        bg.removeAll();
        bg.add(mainMenu);
        frame.setContentPane(bg);
        frame.validate();
        frame.repaint();
        frame.pack();
        previousPage = null;
    }

    /**
     * Method switches one menu page to other
     * @param fromScene current scene
     * @param toScene next scene
     */
    private void changeScene(Box fromScene, Box toScene){
        bg.remove(fromScene);
        bg.add(toScene);
        bg.validate();
        bg.repaint();
        previousPage = fromScene;
    }

    /**
     * Methods creates panel, sets its size and layout
     * @return JPanel created panel
     */
    private JPanel createPanelBtn(){
        JPanel panelBtn = new JPanel();
        panelBtn.setPreferredSize(buttonDimension);
        panelBtn.setMaximumSize(buttonDimension);
        panelBtn.setMinimumSize(buttonDimension);
        panelBtn.setLayout(new GridLayout(1,1));
        return panelBtn;
    }

    /**
     * Method sets background image of given button, position
     * and color of button text
     * @param button button to be set up
     */
    private void setButton(JButton button){
        button.setIcon(buttonImage);
        button.setForeground(Color.white);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
    }

    /**
     * Method creates main page into global attribute,
     * sets dimensions of containing elements and sets
     * buttons
     */
    private void createMainPage(){
        mainMenu = new Box(BoxLayout.Y_AXIS);
        mainMenu.add(Box.createVerticalGlue());

        JPanel panelBtnSingleP = createPanelBtn();
        panelBtnSingleP.add(singlePlayerBtn);
        setButton(singlePlayerBtn);
        mainMenu.add(panelBtnSingleP);

        JPanel panelBtnMultiP = createPanelBtn();
        panelBtnMultiP.add(multiPlayerBtn);
        setButton(multiPlayerBtn);
        mainMenu.add(panelBtnMultiP);

        JPanel panelBtnLoad = createPanelBtn();
        panelBtnLoad.add(loadGameBtn);
        setButton(loadGameBtn);
        mainMenu.add(panelBtnLoad);

        JPanel panelBtnSettings = createPanelBtn();
        panelBtnSettings.add(settingsBtn);
        setButton(settingsBtn);
        mainMenu.add(panelBtnSettings);

        JPanel panelBtnExit = createPanelBtn();
        panelBtnExit.add(exitBtn);
        setButton(exitBtn);
        mainMenu.add(panelBtnExit);

        mainMenu.add(Box.createVerticalGlue());

        //set listeners
        singlePlayerBtn.addActionListener(new buttonClicked(singlePlayerBtn));
        multiPlayerBtn.addActionListener(new buttonClicked(multiPlayerBtn));
        loadGameBtn.addActionListener(new buttonClicked(loadGameBtn));
        settingsBtn.addActionListener(new buttonClicked(settingsBtn));
        exitBtn.addActionListener(new backExitBtnClicked(mainMenu));
    }

    /**
     * Method creates page, where user can choose
     * game mode, sets elements and buttons
     */
    private void createChooseGameModePage(){
        chooseMode = new Box(BoxLayout.Y_AXIS);
        chooseMode.add(Box.createVerticalGlue());

        JPanel panelFirstModeBtn = createPanelBtn();
        panelFirstModeBtn.add(beginnerModeBtn);
        setButton(beginnerModeBtn);
        chooseMode.add(panelFirstModeBtn);

        JPanel panelSecondModeBtn = createPanelBtn();
        panelSecondModeBtn.add(advancedModeBtn);
        setButton(advancedModeBtn);
        chooseMode.add(panelSecondModeBtn);

        JPanel panelBackBtn = createPanelBtn();
        JButton backBtn = new JButton("Back");
        setButton(backBtn);
        panelBackBtn.add(backBtn);
        chooseMode.add(panelBackBtn);

        chooseMode.add(Box.createVerticalGlue());

        //set listeners
        beginnerModeBtn.addActionListener(new buttonClicked(beginnerModeBtn));
        advancedModeBtn.addActionListener(new buttonClicked(advancedModeBtn));
        backBtn.addActionListener(new backExitBtnClicked(chooseMode));
    }

    /**
     * Method creates page, where user can choose
     * size of board, sets elements and buttons
     */
    private void createChooseSizeOfBoardPage(){
        //set choose size of board page
        chooseBoardSize = new Box(BoxLayout.Y_AXIS);
        chooseBoardSize.add(Box.createVerticalGlue());

        JPanel panel6x6Btn = createPanelBtn();
        panel6x6Btn.add(size6x6Btn);
        setButton(size6x6Btn);
        chooseBoardSize.add(panel6x6Btn);

        JPanel panel8x8Btn = createPanelBtn();
        panel8x8Btn.add(size8x8Btn);
        setButton(size8x8Btn);
        chooseBoardSize.add(panel8x8Btn);

        JPanel panel10x10Btn = createPanelBtn();
        panel10x10Btn.add(size10x10Btn);
        setButton(size10x10Btn);
        panel6x6Btn.add(panel10x10Btn);

        JPanel panel12x12Btn = createPanelBtn();
        panel12x12Btn.add(size12x12Btn);
        setButton(size12x12Btn);
        chooseBoardSize.add(panel12x12Btn);

        JPanel panelBackBtn = createPanelBtn();
        panelBackBtn.add(backBtn);
        setButton(backBtn);
        chooseBoardSize.add(panelBackBtn);

        chooseBoardSize.add(Box.createVerticalGlue());

        size6x6Btn.addActionListener(new boardSizeBtnClicked(size6x6Btn));
        size8x8Btn.addActionListener(new boardSizeBtnClicked(size8x8Btn));
        size10x10Btn.addActionListener(new boardSizeBtnClicked(size10x10Btn));
        size12x12Btn.addActionListener(new boardSizeBtnClicked(size12x12Btn));
        backBtn.addActionListener(new backExitBtnClicked(chooseBoardSize));
    }

    /**
     * Method updates default values important for game
     * (players' names,..) obtained in settings menu
     */
    private void updateSettings(){
        player1Name = settings.player1Name;
        player2Name = settings.player2Name;
        computerName = settings.computerName;
        discsToFreeze = (int) ((settings.FDisc/100) *boardSize*boardSize);
        CHTime = settings.CHTime;
        FTime = settings.FTime;
    }

    /**
     * Method loads game from a backup and replays whole game to restores
     * the game state.
     */
    private void loadGame(){
        Backup backup_game = null;
        try {
            FileInputStream fileIn = new FileInputStream("./examples/backupGame.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            backup_game = (Backup) in.readObject();
            in.close();
            fileIn.close();
        }
        catch(IOException i) {
            i.printStackTrace();

        }catch(ClassNotFoundException c) {
            System.err.println("backupGame class not found");
            c.printStackTrace();
        }
        if (backup_game != null){
            if(settings != null) { updateSettings(); }
            BoardGUI boardGUI = new BoardGUI(frame, backup_game.getBoardSize(), backup_game.getPlayer1(),
                    backup_game.getPlayer2(), discsToFreeze ,CHTime ,FTime );
            frame.remove(mainMenu);

            for(Backup.TurnBackUp turn  : backup_game.backupTurns) {
                turn.turn_player.putDisk(turn.base_Point.row,turn.base_Point.col);
                if(!turn.turn_player.is_pc()) {
                    boardGUI.game.nextPlayer();
                }
            }
        }else{
            JOptionPane.showMessageDialog(frame, "Couldn't find any saved game.");
        }
    }

    /**
     * Background panel class, sets background image,
     * all menu pages are set on the panel.
     */
    public class BackgroundPane extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            BufferedImage bg=null;
            try {
                bg = ImageIO.read(Images.class.getResource("/background.jpg"));
            }catch(IOException ex){
                System.err.println(ex.getMessage());
                ex.printStackTrace();
            }
            if (bg != null) {
                int x = (getWidth() - bg.getWidth()) / 2;
                int y = (getHeight() - bg.getHeight()) / 2;
                g.drawImage(bg, x, y, this);
            }
        }
    }

    /**
     * Class implements action listener for buttons in choose-game-mode page
     * and provides actions when action event occurs
     */
    private class boardSizeBtnClicked implements ActionListener {
        private JButton button;

        boardSizeBtnClicked(JButton button){
            this.button = button;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if (this.button == size6x6Btn) { boardSize = 6; }
            else if(this.button == size8x8Btn) { boardSize = 8; }
            else if(this.button == size10x10Btn) { boardSize = 10; }
            else if(this.button == size12x12Btn) { boardSize = 12; }

            if(settings != null){           //default settings has been changed
                updateSettings();
            }

            Player player1 = new Player(true,player1Name);
            Player player2;
            if(singlePlayer){player2 = new Player(false, mode, computerName);}
            else{player2 = new Player(false, player2Name);}

            BoardGUI boardGUI = new BoardGUI(frame, boardSize,player1, player2,(int) discsToFreeze ,CHTime ,FTime);
            frame.remove(chooseBoardSize);

            previousPage = chooseBoardSize;
        }
    }

    /**
     * Class implements action listener for buttons in main menu page
     * and choose-game-mode page and also provides actions when event occurs
     */
    private class buttonClicked implements ActionListener {
        private JButton button;

        buttonClicked(JButton button){
            this.button = button;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(button == singlePlayerBtn){
                singlePlayer = true;
                changeScene(mainMenu, chooseMode);
            }
            else if(button == multiPlayerBtn){
                singlePlayer = false;
                changeScene(mainMenu, chooseBoardSize);
            }
            else if(button == settingsBtn){
                bg.remove(mainMenu);
                settings = new settingsMenu();
                bg.add(settings.getMenu(frame, bg, mainMenu));
                bg.validate();
                bg.repaint();
                previousPage = mainMenu;
            }
            else if(button == beginnerModeBtn){
                mode = 1;
                changeScene(chooseMode, chooseBoardSize);
            }
            else if(button == advancedModeBtn){
                mode = 2;
                changeScene(chooseMode, chooseBoardSize);
            }
            else if(button == loadGameBtn){
                loadGame();
            }
        }
    }

    /**
     * Class implements action listener for back/exit buttons
     * and provides actions when event occurs
     */
    private class backExitBtnClicked implements ActionListener {
        private Box currentPage;

        backExitBtnClicked(Box currentPage){
            this.currentPage = currentPage;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(currentPage == mainMenu){ System.exit(0); }

            bg.remove(currentPage);
            if(currentPage == chooseMode){
                bg.add(mainMenu);
                singlePlayer = false;
            }
            else if(currentPage == chooseBoardSize) { bg.add(previousPage); }

            frame.validate();
            frame.repaint();
        }
    }
}
