package graphicManaging;

import Icons.ImageRead;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class SpecializedPanel extends JPanel {

    private ArrayList<Integer> diceNumbersOnPlain = new ArrayList<>();

    private void paintRectangle(int x, int y, int width, int height, Color color, Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    private void paintCircle(int x, int y, int radius, Color color, Graphics g) {
        g.setColor(color);
        g.fillOval(x - radius / 2, y - radius / 2, radius, radius);
    }

    private void paintImage(int x, int y, int width, int height, Image img, Graphics g) {
        g.drawImage(img, x, y, width, height, new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                return false;
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintRectangle(0, 0, getWidth(), getHeight(), new Color(0, 120, 0, 255), g);
        paintCircle(90, 90, 160, new Color(67, 54, 49, 255), g);
        paintCircle(90, 90, 140, new Color(48, 37, 33, 255), g);
        paintRectangle(20, 200, 370, 300, new Color(120, 0, 0, 255), g);
        drawImages(g);
    }

    private void drawImages(Graphics g) {
        ImageRead imageRead = new ImageRead();
        switch (diceNumbersOnPlain.size()) {
            case 1:
                paintImage(30, 210, 200, 200, imageRead.getImage(diceNumbersOnPlain.get(0)), g);
                break;
            case 2:
                paintImage(30,210,150,150,imageRead.getImage(diceNumbersOnPlain.get(0)),g);
                paintImage(190,210,150,150,imageRead.getImage(diceNumbersOnPlain.get(1)),g);
                break;
            case 3:
                paintImage(30,210,130,130,imageRead.getImage(diceNumbersOnPlain.get(0)),g);
                paintImage(30,350,130,130,imageRead.getImage(diceNumbersOnPlain.get(1)),g);
                paintImage(170,250,170,170,imageRead.getImage(diceNumbersOnPlain.get(2)),g);
                break;
            case 4:
                paintImage(30,210,130,130,imageRead.getImage(diceNumbersOnPlain.get(0)),g);
                paintImage(30,350,130,130,imageRead.getImage(diceNumbersOnPlain.get(1)),g);
                paintImage(170,210,130,130,imageRead.getImage(diceNumbersOnPlain.get(2)),g);
                paintImage(170,350,130,130,imageRead.getImage(diceNumbersOnPlain.get(3)),g);
        }
    }

    public void addDiceToPlain(int numberOnDice) {
        diceNumbersOnPlain.add(numberOnDice);
    }

    public void removeDiceFromPlane(int numberOnDice) {
        diceNumbersOnPlain.remove(diceNumbersOnPlain.lastIndexOf(numberOnDice));
    }
}
