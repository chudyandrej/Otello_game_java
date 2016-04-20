package board;

import GUI.BoardGUI;

/**
 * Created by andrejchudy on 15/04/16.
 */
public class Disk implements java.io.Serializable {
    private boolean isWhite;
    private int x, y;

    public Disk(boolean isWhite){
        this.isWhite = isWhite;
    }

    public void turn(){
        this.isWhite = !isWhite;
        BoardGUI.changeDisc(x,y, isWhite);
    }



    public boolean isWhite(){
        return isWhite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Disk disk = (Disk) o;

        return isWhite == disk.isWhite;

    }

    @Override
    public int hashCode() {
        return (isWhite ? 1 : 0);
    }

    public void setY(int x , int y) {
        this.x = x;
        this.y = y;
        BoardGUI.changeDisc(x,y, isWhite);
    }
    public void delete(){
        BoardGUI.deleteDisc(x,y);

    }


}
