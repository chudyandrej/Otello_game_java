/**
 * The class loads, resized images and sets dimensions of 
 * main window depending on size of board
 * 
 * @author  Andrej Chudý
 * @author  Martin Kopec
 * @date 01.05.2016
 */

package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Images {

    private int iconSize = 25;

    ImageIcon whitePlayerFieldDisc;
    ImageIcon blackPlayerFieldDisc;
    ImageIcon fieldBackground;
    ImageIcon whitePlayerFieldDiscFrozen;
    ImageIcon blackPlayerFieldDiscFrozen;
    ImageIcon fieldBackgroundFrozen;
    ImageIcon fieldCanPutDisc;
    ImageIcon whiteDisc;
    ImageIcon blackDisc;

    ImageIcon newGameBtnImage;
    ImageIcon newGameBtnImageE;
    ImageIcon newGameBtnImageP;
    ImageIcon homeBtnImage;
    ImageIcon homeBtnImageE;
    ImageIcon homeBtnImageP;
    ImageIcon undoBtnImage;
    ImageIcon undoBtnImageE;
    ImageIcon undoBtnImageP;
    ImageIcon saveBtnImage;
    ImageIcon saveBtnImageE;
    ImageIcon saveBtnImageP;

    ImageIcon arrowL;
    ImageIcon arrowR;

    /**
     * Method sets dimensions of window (frame) according to board size
     * @param  JFrame frame - window to sets the dimensions of
     * @param  int boardSize - size of board
     */
    public Images(JFrame frame, int boardSize){

        int w = 0;
        int h = 0;
        switch (boardSize){
            case 6:
                w = 55;
                h = 52;
                frame.setMinimumSize(new Dimension(500, 480));
                break;
            case 8:
                w = 49;
                h = 46;
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
        whitePlayerFieldDiscFrozen = new ImageIcon(resizeImage("lib/white2frozen.png", w, h));
        blackPlayerFieldDiscFrozen = new ImageIcon(resizeImage("lib/black2frozen.png", w, h));
        fieldBackgroundFrozen = new ImageIcon(resizeImage("lib/fieldFrozen.png", w, h));
        arrowL = new ImageIcon(resizeImage("lib/arrow_l.png", iconSize, iconSize));
        arrowR = new ImageIcon(resizeImage("lib/arrow_r.png", iconSize, iconSize));
        whiteDisc = new ImageIcon(resizeImage("lib/whiteDisc.png", iconSize, iconSize));
        blackDisc = new ImageIcon(resizeImage("lib/blackDisc.png", iconSize, iconSize));

        newGameBtnImage = new ImageIcon(Images.resizeImage("lib/icons/playAgain.png", iconSize, iconSize));
        newGameBtnImageE = new ImageIcon(Images.resizeImage("lib/icons/playAgainEntered.png", iconSize, iconSize));
        newGameBtnImageP = new ImageIcon(Images.resizeImage("lib/icons/playAgainPressed.png", iconSize, iconSize));
        homeBtnImage = new ImageIcon(Images.resizeImage("lib/icons/home.png", iconSize, iconSize));
        homeBtnImageE = new ImageIcon(Images.resizeImage("lib/icons/homeEntered.png", iconSize, iconSize));
        homeBtnImageP = new ImageIcon(Images.resizeImage("lib/icons/homePressed.png", iconSize, iconSize));
        undoBtnImage = new ImageIcon(Images.resizeImage("lib/icons/undo.png", iconSize, iconSize));
        undoBtnImageE = new ImageIcon(Images.resizeImage("lib/icons/undoEntered.png", iconSize, iconSize));
        undoBtnImageP = new ImageIcon(Images.resizeImage("lib/icons/undoPressed.png", iconSize, iconSize));
        saveBtnImage = new ImageIcon(Images.resizeImage("lib/icons/saveGame.png", iconSize, iconSize));
        saveBtnImageE = new ImageIcon(Images.resizeImage("lib/icons/saveGameEntered.png", iconSize, iconSize));
        saveBtnImageP = new ImageIcon(Images.resizeImage("lib/icons/saveGamePressed.png", iconSize, iconSize));
    }

    /**
     * Static function, which resizes an image
     * @param  String imgName - path to image
     * @param  int w - wanted width
     * @param  int h - wanted height
     * @return  Image - resized image
     */
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
}
