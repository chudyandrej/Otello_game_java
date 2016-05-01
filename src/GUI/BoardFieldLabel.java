/**
 * This class implements methods of board field such as:
 * set or delete disc, froze or unfroze field according to its
 * content
 * The class also provides board field features such as changing background
 * of field, when mouse hovers and player can put disc to the field 
 * 
 * @author  Andrej Chudý
 * @author  Martin Kopec
 * @date 01.05.2016
 */

package GUI;

import game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class BoardFieldLabel extends JLabel implements MouseListener {
    private int row;
    private int col;
    private boolean pressed = false;
    private boolean frozen = false;
    private Icon beforeFroze;
    private Images I;
    private BoardGUI boardGUI;

    /**
     * Constructor method initialize one board field
     * @param int row - number of row, where field is placed
     * @param int col - number of col, where field is placed
     * @param Images I - instance of Images class containg all images needed for field
     * @param BoardGUI boardGUI - instance of BoardGUI for access to game and Game method
     */
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
        if(frozen){ return; }
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
        if (boardGUI.game.getCurrentPlayer().canPutDisk(row, col)) {
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
        Player tmp  = boardGUI.game.getCurrentPlayer();

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