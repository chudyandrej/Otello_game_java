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
import java.io.IOException;

/**
 * The class loads, resized images and sets dimensions of
 * main window depending on size of board
 */
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
     * @param  frame window to sets the dimensions of
     * @param  boardSize size of board
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

        whitePlayerFieldDisc = new ImageIcon(resizeImage("/white2.png", w, h));
        blackPlayerFieldDisc = new ImageIcon(resizeImage("/black2.png", w, h));
        fieldBackground = new ImageIcon(resizeImage("/field.png", w, h));
        fieldCanPutDisc = new ImageIcon(resizeImage("/fieldCanPut.png", w, h));
        whitePlayerFieldDiscFrozen = new ImageIcon(resizeImage("/white2frozen.png", w, h));
        blackPlayerFieldDiscFrozen = new ImageIcon(resizeImage("/black2frozen.png", w, h));
        fieldBackgroundFrozen = new ImageIcon(resizeImage("/fieldFrozen.png", w, h));
        whiteDisc = new ImageIcon(resizeImage("/whiteDisc.png", iconSize, iconSize));
        blackDisc = new ImageIcon(resizeImage("/blackDisc.png", iconSize, iconSize));
        arrowL = new ImageIcon(Images.openImage("/arrow_l.png"));
        arrowR = new ImageIcon(Images.openImage("/arrow_r.png"));

        newGameBtnImage = new ImageIcon(Images.openImage("/icons/playAgain.png"));
        newGameBtnImageE = new ImageIcon(Images.openImage("/icons/playAgainEntered.png"));
        newGameBtnImageP = new ImageIcon(Images.openImage("/icons/playAgainPressed.png"));
        homeBtnImage = new ImageIcon(Images.openImage("/icons/home.png"));
        homeBtnImageE = new ImageIcon(Images.openImage("/icons/homeEntered.png"));
        homeBtnImageP = new ImageIcon(Images.openImage("/icons/homePressed.png"));
        undoBtnImage = new ImageIcon(Images.openImage("/icons/undo.png"));
        undoBtnImageE = new ImageIcon(Images.openImage("/icons/undoEntered.png"));
        undoBtnImageP = new ImageIcon(Images.openImage("/icons/undoPressed.png"));
        saveBtnImage = new ImageIcon(Images.openImage("/icons/saveGame.png"));
        saveBtnImageE = new ImageIcon(Images.openImage("/icons/saveGameEntered.png"));
        saveBtnImageP = new ImageIcon(Images.openImage("/icons/saveGamePressed.png"));
    }

    /**
     * Static method, which resizes an image
     * @param  imgName path to image
     * @param  w wanted width
     * @param  h wanted height
     * @return  Image - resized image
     */
    static public Image resizeImage(String imgName, int w, int h){
        BufferedImage resizedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB); //TYPE_INT_ARGB makes parts of transparent image be transparent
        Graphics2D g = resizedImage.createGraphics();
        //g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(Images.openImage(imgName), 0, 0, w, h, null);
        g.dispose();
        return resizedImage;
    }

    /**
     * Static method which reads the image from the hard disc.
     * @param imgName path to the image
     * @return image
     */
    static public Image openImage(String imgName){
        Image img = null;
        try{
            img = ImageIO.read(Images.class.getResource(imgName));
        }catch(IOException ex){
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
        return img;
    }
}
