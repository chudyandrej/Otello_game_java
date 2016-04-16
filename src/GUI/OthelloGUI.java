package GUI;

import game.Game;
import game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by martin on 16/04/16.
 */
public class OthelloGUI {

    boolean singlePlayer = false;
    byte mode;
    public int boardSize;
    private Player player1;
    private Player player2;
    JFrame frame;

    Box mainMenu;
    Box chooseMode;
    Box chooseBoardSize;
    Box previousPage = null;

    Dimension buttonDimension = new Dimension(150, 40);

    JButton singlePlayerBtn = new JButton("Single Player");
    JButton multiPlayerBtn = new JButton("Multiplayer");
    JButton exitBtn = new JButton("Exit");
    JButton firstModeBtn = new JButton("First Mode");
    JButton secondModeBtn = new JButton("Second Mode");
    JButton size6x6Btn = new JButton("6x6");
    JButton size8x8Btn = new JButton("8x8");
    JButton size10x10Btn = new JButton("10x10");
    JButton size12x12Btn = new JButton("12x12");
    JButton backBtn = new JButton("Back");

    public OthelloGUI(){
        frame = new JFrame("Othello");
        createMainPage();
        createChooseGameModePage();
        createChooseSizeOfBoardPage();

        frame.add(mainMenu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(610, 500));
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void changeScene(Box fromScene, Box toScene){
        frame.remove(fromScene);
        frame.setContentPane(toScene);
        frame.validate();
        frame.repaint();
        previousPage = fromScene;
    }

    private JPanel createPanelBtn(){
        JPanel panelBtn = new JPanel();
        panelBtn.setPreferredSize(buttonDimension);
        panelBtn.setMaximumSize(buttonDimension);
        panelBtn.setMinimumSize(buttonDimension);
        panelBtn.setLayout(new GridLayout(1,1));
        return panelBtn;
    }

    private void createMainPage(){
        mainMenu = new Box(BoxLayout.Y_AXIS);
        mainMenu.add(Box.createVerticalGlue());

        JPanel panelBtnSingleP = createPanelBtn();
        panelBtnSingleP.add(singlePlayerBtn);
        mainMenu.add(panelBtnSingleP);

        JPanel panelBtnMultiP = createPanelBtn();
        panelBtnMultiP.add(multiPlayerBtn);
        mainMenu.add(panelBtnMultiP);

        JPanel panelBtnExit = createPanelBtn();
        panelBtnExit.add(exitBtn);
        mainMenu.add(panelBtnExit);

        mainMenu.add(Box.createVerticalGlue());

        //set listeners
        singlePlayerBtn.addActionListener(new buttonClicked(singlePlayerBtn));
        multiPlayerBtn.addActionListener(new buttonClicked(multiPlayerBtn));
        exitBtn.addActionListener(new backExitBtnClicked(mainMenu));
    }

    private void createChooseGameModePage(){
        chooseMode = new Box(BoxLayout.Y_AXIS);
        chooseMode.add(Box.createVerticalGlue());

        JPanel panelFirstModeBtn = createPanelBtn();
        panelFirstModeBtn.add(firstModeBtn);
        chooseMode.add(panelFirstModeBtn);

        JPanel panelSecondModeBtn = createPanelBtn();
        panelSecondModeBtn.add(secondModeBtn);
        chooseMode.add(panelSecondModeBtn);

        JPanel panelBackBtn = createPanelBtn();
        JButton backBtn = new JButton("Back");
        panelBackBtn.add(backBtn);
        chooseMode.add(panelBackBtn);

        chooseMode.add(Box.createVerticalGlue());

        //set listeners
        firstModeBtn.addActionListener(new buttonClicked(firstModeBtn));
        secondModeBtn.addActionListener(new buttonClicked(secondModeBtn));
        backBtn.addActionListener(new backExitBtnClicked(chooseMode));
    }
    private void createChooseSizeOfBoardPage(){
        //set choose size of board page
        chooseBoardSize = new Box(BoxLayout.Y_AXIS);
        chooseBoardSize.add(Box.createVerticalGlue());

        JPanel panel6x6Btn = createPanelBtn();
        panel6x6Btn.add(size6x6Btn);
        chooseBoardSize.add(panel6x6Btn);

        JPanel panel8x8Btn = createPanelBtn();
        panel8x8Btn.add(size8x8Btn);
        chooseBoardSize.add(panel8x8Btn);

        JPanel panel10x10Btn = createPanelBtn();
        panel10x10Btn.add(size10x10Btn);
        panel6x6Btn.add(panel10x10Btn);

        JPanel panel12x12Btn = createPanelBtn();
        panel12x12Btn.add(size12x12Btn);
        chooseBoardSize.add(panel12x12Btn);

        JPanel panelBackBtn = createPanelBtn();
        panelBackBtn.add(backBtn);
        chooseBoardSize.add(panelBackBtn);

        chooseBoardSize.add(Box.createVerticalGlue());

        size6x6Btn.addActionListener(new boardSizeBtnClicked(size6x6Btn));
        size8x8Btn.addActionListener(new boardSizeBtnClicked(size8x8Btn));
        size10x10Btn.addActionListener(new boardSizeBtnClicked(size10x10Btn));
        size12x12Btn.addActionListener(new boardSizeBtnClicked(size12x12Btn));
        backBtn.addActionListener(new backExitBtnClicked(chooseBoardSize));
    }

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

            player1 = new Player(true);
            if(singlePlayer){player2 = new Player(false, mode);}
            else{player2 = new Player(false);}

            BoardGUI boardGUI = new BoardGUI(frame, boardSize,player1, player2);
            frame.remove(chooseBoardSize);
            //initUIBoard();

            previousPage = chooseBoardSize;
        }
    }
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
                changeScene(mainMenu, chooseBoardSize);
            }
            else if(button == firstModeBtn){
                mode = 1;
                changeScene(chooseMode, chooseBoardSize);
            }
            else if(button == secondModeBtn){
                mode = 2;
                changeScene(chooseMode, chooseBoardSize);
            }
        }
    }
    // Back - exit button
    private class backExitBtnClicked implements ActionListener {
        private Box currentPage;

        backExitBtnClicked(Box currentPage){
            this.currentPage = currentPage;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(currentPage == mainMenu){ System.exit(0); }

            frame.remove(currentPage);
            if(currentPage == chooseMode){  frame.setContentPane(mainMenu); }
            else if(currentPage == chooseBoardSize) { frame.setContentPane(previousPage); }

            frame.validate();
            frame.repaint();
        }
    }
}
