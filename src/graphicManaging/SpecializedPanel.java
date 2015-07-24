package graphicManaging;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

public class SpecializedPanel extends JPanel {

    public static int DICEINCUP=1;
    public static int DICEINTEMP=2;
    public static int DICEINSAFSIDE=3;

    Graphics graphics;

    protected void paintRectangle(int y,int x, int width, int height,Color color,Graphics blah){
        Graphics g=blah;
        g.setColor(color);
        g.fillRect(y,x,width,height);
        }

    protected void paintImage(int y, int x, Image img){
        Graphics g=getGraphics();
        g.drawImage(img, x, y, new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        });
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        paintRectangle(0, 0, getWidth(), getHeight(), new Color(0, 120, 0, 255), g);
    }

    public void addDiceToPanel(int numberShown, int location){}

}
