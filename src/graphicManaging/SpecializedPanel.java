package graphicManaging;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class SpecializedPanel extends JPanel {

    public static int DICEINCUP=1;
    public static int DICEINTEMP=2;
    public static int DICEINSAFSIDE=3;

    Graphics graphics;

    private void paintRectangle(int x,int y, int width, int height,Color color,Graphics g){
        g.setColor(color);
        g.fillRect(x,y,width,height);
        }

    private void paintCircle(int x, int y, int radius, Color color,Graphics g){
        g.setColor(color);
        g.fillOval(x-radius/2,y-radius/2,radius,radius);
    }

    private void paintImage(int y, int x, Image img){
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
        paintCircle(90, 90, 160, new Color(67, 54, 49, 255), g);
        paintCircle(90, 90, 140, new Color(48, 37, 33, 255), g);
        paintRectangle(20, 200, 400, 300, new Color(120, 0, 0, 255), g);
    }

    public void addDiceToPanel(int numberShown, int location){

    }

}
