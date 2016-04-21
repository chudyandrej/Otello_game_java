package GUI;

import game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by martin on 20/04/16.
 */
public class BoardFieldLabel extends JLabel implements MouseListener {
    private int row;
    private int col;
    private boolean pressed = false;
    private boolean frozen = false;
    private Icon beforeFroze;
    private Images I;
    private BoardGUI boardGUI;

    BoardFieldLabel(int row, int col, Images I, BoardGUI boardGUI){
        this.row = row;
        this.col = col;
        this.I = I;
        this.boardGUI = boardGUI;

        setBorder(BorderFactory.createLineBorder(Color.black));
        setIcon(I.fieldBackground);
        setOpaque(true);
        addMouseListener(this);
    }

    public void setDisc(boolean isWhite){
        pressed = true;
        setIcon( (isWhite) ? I.whitePlayerFieldDisc : I.blackPlayerFieldDisc );
    }

    public void deleteDisc(){
        pressed = false;
        setIcon(I.fieldBackground);
    }

    public void freeze(){
        frozen = true;
        beforeFroze = getIcon();
        if (beforeFroze == I.fieldBackground){
            setIcon(I.fieldBackgroundFrozen);
        }
        else if(beforeFroze == I.whitePlayerFieldDisc){
            setIcon(I.whitePlayerFieldDiscFrozen);
        }
        else{
            setIcon(I.blackPlayerFieldDiscFrozen);
        }
    }

    public void unFreeze(){
        frozen = false;
        setIcon(beforeFroze);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (boardGUI.game.currentPlayer().canPutDisk(row, col)) {
            setIcon(I.fieldCanPutDisc);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(!pressed && !frozen) {    //if there is no disc and it's not frozen
            setIcon(I.fieldBackground);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Player tmp  = boardGUI.game.currentPlayer();

        if (tmp.putDisk(row, col)) {
            pressed = true;
            boardGUI.game.nextPlayer();

            if(boardGUI.game.gameOver){
                boardGUI.showGameOverDialog();
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