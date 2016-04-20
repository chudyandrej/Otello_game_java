package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by martin on 20/04/16.
 */
public class Images {

    ImageIcon whitePlayerFieldDisc;
    ImageIcon blackPlayerFieldDisc;
    ImageIcon fieldBackground;
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
                w = 46;
                h = 43;
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
        whiteDisc = new ImageIcon(resizeImage("lib/whiteDisc.png", 25, 25));
        blackDisc = new ImageIcon(resizeImage("lib/blackDisc.png", 25, 25));

        newGameBtnImage = new ImageIcon(Images.resizeImage("lib/icons/playAgain.png", 25, 25));
        newGameBtnImageE = new ImageIcon(Images.resizeImage("lib/icons/playAgainEntered.png", 25, 25));
        newGameBtnImageP = new ImageIcon(Images.resizeImage("lib/icons/playAgainPressed.png", 25, 25));
        homeBtnImage = new ImageIcon(Images.resizeImage("lib/icons/home.png", 25, 25));
        homeBtnImageE = new ImageIcon(Images.resizeImage("lib/icons/homeEntered.png", 25, 25));
        homeBtnImageP = new ImageIcon(Images.resizeImage("lib/icons/homePressed.png", 25, 25));
        undoBtnImage = new ImageIcon(Images.resizeImage("lib/icons/undo.png", 25, 25));
        undoBtnImageE = new ImageIcon(Images.resizeImage("lib/icons/undoEntered.png", 25, 25));
        undoBtnImageP = new ImageIcon(Images.resizeImage("lib/icons/undoPressed.png", 25, 25));
        saveBtnImage = new ImageIcon(Images.resizeImage("lib/icons/saveGame.png", 25, 25));
        saveBtnImageE = new ImageIcon(Images.resizeImage("lib/icons/saveGameEntered.png", 25, 25));
        saveBtnImageP = new ImageIcon(Images.resizeImage("lib/icons/saveGamePressed.png", 25, 25));

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
}
