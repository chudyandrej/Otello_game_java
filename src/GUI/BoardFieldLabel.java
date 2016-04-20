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
    public int col;
    private boolean pressed = false;
    private boolean frozen = false;
    private Icon beforeFroze;
    private Images I;
    private BoardGUI instance;

    BoardFieldLabel(int row, int col, Images I, BoardGUI instance){
        this.row = row;
        this.col = col;
        this.I = I;
        this.instance = instance;

        setBorder(BorderFactory.createLineBorder(Color.black));
        setIcon(I.fieldBackground);
        setOpaque(true);
        addMouseListener(this);
    }

    public void setDisc(boolean isWhite){
        setIcon( (isWhite) ? I.whitePlayerFieldDisc : I.blackPlayerFieldDisc );
        if(!pressed){ pressed = true; }
    }

    public void deleteDisc(){
        pressed = false;
        setIcon(I.fieldBackground);
    }

    public void freeze(){
        beforeFroze = getIcon();
        frozen = true;
        setIcon(I.saveBtnImage);        //just temporary, new image is needed to create
    }

    public void unFreeze(){
        setIcon(beforeFroze);
        frozen = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (BoardGUI.game.currentPlayer().canPutDisk(row, col)) {
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
        Player tmp  = BoardGUI.game.currentPlayer();

        if (tmp.putDisk(row, col)) {
            pressed = true;
            BoardGUI.game.nextPlayer();

            if(BoardGUI.game.gameOver){
                instance.showGameOverDialog();
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