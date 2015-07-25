package graphicManaging;

import Icons.ImageRead;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class SpecializedPanel extends JPanel {

    private ArrayList<Integer> diceNumbersOnPlain = new ArrayList<>();
    private int diceInCup = 0;

    private void paintRectangle(int x, int y, int width, int height, Color color, Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    //angle
    private void paintTwistedRect(int x, int y, int width, int height, double angle, Color color, Graphics g) {
        g.setColor(color);
        Polygon turnedRect = new Polygon();
        turnedRect.addPoint(x, y);
        turnedRect.addPoint((int) (x + Math.cos((angle) * (Math.PI / 180)) * width + 0.5), (int) (y + Math.sin((angle) * (Math.PI / 180)) * width + 0.5));
        turnedRect.addPoint((int) ((x + Math.cos((angle) * (Math.PI / 180)) * width + 0.5) - (Math.cos((180 - 90 - angle) * (Math.PI / 180)) * height + 0.5)), (int) ((y + Math.sin((angle) * (Math.PI / 180)) * width + 0.5) + (Math.sin((180 - 90 - angle) * (Math.PI / 180)) * height + 0.5)));
        turnedRect.addPoint((int) (x - Math.cos((180 - 90 - angle) * (Math.PI / 180)) * height + 0.5), (int) (x + Math.sin((180 - 90 - angle) * (Math.PI / 180)) * height + 0.5));
        g.fillPolygon(turnedRect);
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
        drawCupComponent(g);
    }

    private void drawImages(Graphics g) {
        ImageRead imageRead = new ImageRead();
        switch (diceNumbersOnPlain.size()) {
            case 1:
                paintImage(30, 210, 200, 200, imageRead.getImage(diceNumbersOnPlain.get(0)), g);
                break;
            case 2:
                paintImage(30, 210, 150, 150, imageRead.getImage(diceNumbersOnPlain.get(0)), g);
                paintImage(190, 210, 150, 150, imageRead.getImage(diceNumbersOnPlain.get(1)), g);
                break;
            case 3:
                paintImage(30, 210, 130, 130, imageRead.getImage(diceNumbersOnPlain.get(0)), g);
                paintImage(30, 350, 130, 130, imageRead.getImage(diceNumbersOnPlain.get(1)), g);
                paintImage(170, 250, 170, 170, imageRead.getImage(diceNumbersOnPlain.get(2)), g);
                break;
            case 4:
                paintImage(30, 210, 130, 130, imageRead.getImage(diceNumbersOnPlain.get(0)), g);
                paintImage(30, 350, 130, 130, imageRead.getImage(diceNumbersOnPlain.get(1)), g);
                paintImage(170, 210, 130, 130, imageRead.getImage(diceNumbersOnPlain.get(2)), g);
                paintImage(170, 350, 130, 130, imageRead.getImage(diceNumbersOnPlain.get(3)), g);
                break;
            case 5:
                paintImage(30, 210, 110, 110, imageRead.getImage(diceNumbersOnPlain.get(0)), g);
                paintImage(150, 210, 110, 110, imageRead.getImage(diceNumbersOnPlain.get(1)), g);
                paintImage(270, 210, 110, 110, imageRead.getImage(diceNumbersOnPlain.get(2)), g);
                paintImage(30, 330, 110, 110, imageRead.getImage(diceNumbersOnPlain.get(3)), g);
                paintImage(150, 330, 110, 110, imageRead.getImage(diceNumbersOnPlain.get(4)), g);
                break;
            default:
                break;
        }
    }

    private void drawCupComponent(Graphics g) {
        switch (diceInCup) {
            case 1:
                paintTwistedRect(100, 100, 30, 30, 20, Color.white, g);
                break;
        }
    }

    public int getDiceInCup() {
        return diceInCup;
    }

    public void setDiceInCup(int diceInCup) {
        this.diceInCup = diceInCup;
    }

    public void addDiceToCup() {
        diceInCup++;
    }

    public void addDiceToPlain(int numberOnDice) {
        diceNumbersOnPlain.add(numberOnDice);
    }

    public void removeDiceFromPlane(int numberOnDice) {
        diceNumbersOnPlain.remove(diceNumbersOnPlain.lastIndexOf(numberOnDice));
    }
}
