package graphicManaging;

import Exceptions.NoDiceFoundException;
import Exceptions.OtherExFoundException;
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

    private void paintTwistedRect(int x, int y, int width, int height, double angle, Color color, Graphics g) {
        g.setColor(color);
        Polygon turnedRect = new Polygon();
        turnedRect.addPoint(x, y);
        turnedRect.addPoint((int) (x + Math.cos((angle) * (Math.PI / 180)) * width + 0.5), (int) (y + Math.sin((angle) * (Math.PI / 180)) * width + 0.5));
        turnedRect.addPoint((int) ((x + Math.cos((angle) * (Math.PI / 180)) * width + 0.5) - (Math.cos((180 - 90 - angle) * (Math.PI / 180)) * height + 0.5)), (int) ((y + Math.sin((angle) * (Math.PI / 180)) * width + 0.5) + (Math.sin((180 - 90 - angle) * (Math.PI / 180)) * height + 0.5)));
        turnedRect.addPoint((int) (x - Math.cos((180 - 90 - angle) * (Math.PI / 180)) * height + 0.5), (int) (y + Math.sin((180 - 90 - angle) * (Math.PI / 180)) * height + 0.5));
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
                paintImage(170, 210, 130, 130, imageRead.getImage(diceNumbersOnPlain.get(1)), g);
                paintImage(30, 350, 130, 130, imageRead.getImage(diceNumbersOnPlain.get(2)), g);
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
                paintTwistedRect(50, 50, 30, 30, 10, Color.white, g);
                break;
            case 2:
                paintTwistedRect(50, 45, 30, 30, 10, Color.white, g);
                paintTwistedRect(60, 90, 30, 30, 30, Color.white, g);
                break;
            case 3:
                paintTwistedRect(50, 45, 30, 30, 10, Color.white, g);
                paintTwistedRect(60, 90, 30, 30, 45, Color.white, g);
                paintTwistedRect(90, 75, 30, 30, 22, Color.white, g);
                break;
            case 4:
                paintTwistedRect(50, 45, 30, 30, 10, Color.white, g);
                paintTwistedRect(60, 90, 30, 30, 45, Color.white, g);
                paintTwistedRect(90, 75, 30, 30, 22, Color.white, g);
                paintTwistedRect(120, 115, 30, 30, 78, Color.white, g);
                break;
            case 5:
                paintTwistedRect(50, 45, 30, 30, 10, Color.white, g);
                paintTwistedRect(60, 90, 30, 30, 45, Color.white, g);
                paintTwistedRect(90, 75, 30, 30, 22, Color.white, g);
                paintTwistedRect(120, 115, 30, 30, 78, Color.white, g);
                paintTwistedRect(120, 40, 30, 30, 30, Color.white, g);
                break;
            default:
                break;
        }
    }

    public void fillCup() {
        diceInCup=5;
    }

    public void addDiceToCup() {
        diceInCup++;
    }

    public void emptyCup() {
        diceInCup=0;
    }

    public void addDiceToPlain(int numberOnDice) {
        diceNumbersOnPlain.add(numberOnDice);
    }

    public void removeDiceFromPlane(int numberOnDice) {
        diceNumbersOnPlain.remove(diceNumbersOnPlain.lastIndexOf(numberOnDice));
    }

    public void clearPlain(){
        diceNumbersOnPlain.clear();
    }

    public int getDiceAt(Point point) throws NoDiceFoundException {
        switch (diceNumbersOnPlain.size()) {
            case 1:
                if (point.getX() >= 30 && point.getX() <= 230 && point.getY() >= 210 && point.getY() <= 410) {
                    return diceNumbersOnPlain.get(0);
                } else {
                    try {
                        throw new NoDiceFoundException();
                    } catch (NoDiceFoundException e) {

                    } catch (Exception e) {
                        try {
                            throw new OtherExFoundException(e);
                        } catch (Exception ex) {

                        }
                    }

                }
                break;
            case 2:
                if (point.getX() >= 30 && point.getX() <= 180 && point.getY() >= 210 && point.getY() <= 360) {
                    return diceNumbersOnPlain.get(0);
                } else if (point.getX() >= 190 && point.getX() <= 340 && point.getY() >= 210 && point.getY() <= 360) {
                    return diceNumbersOnPlain.get(1);
                } else {
                    try {
                        throw new NoDiceFoundException();
                    } catch (NoDiceFoundException e) {

                    } catch (Exception e) {
                        try {
                            throw new OtherExFoundException(e);
                        } catch (Exception ex) {

                        }
                    }
                }
                break;
            case 3:
                if (point.getX() >= 30 && point.getX() <= 160 && point.getY() >= 210 && point.getY() <= 340) {
                    return diceNumbersOnPlain.get(0);
                } else if (point.getX() >= 30 && point.getX() <= 160 && point.getY() >= 350 && point.getY() <= 480) {
                    return diceNumbersOnPlain.get(1);
                } else if (point.getX() >= 170 && point.getX() <= 340 && point.getY() >= 250 && point.getY() <= 420) {
                    return diceNumbersOnPlain.get(2);
                } else {
                    try {
                        throw new NoDiceFoundException();
                    } catch (NoDiceFoundException e) {

                    } catch (Exception e) {
                        try {
                            throw new OtherExFoundException(e);
                        } catch (Exception ex) {

                        }
                    }
                }
                break;
            case 4:
                if (point.getX() >= 30 && point.getX() <= 160 && point.getY() >= 210 && point.getY() <= 340) {
                    return diceNumbersOnPlain.get(0);
                } else if (point.getX() >= 170 && point.getX() <= 300 && point.getY() >= 210 && point.getY() <= 340) {
                    return diceNumbersOnPlain.get(1);
                } else if (point.getX() >= 30 && point.getX() <= 160 && point.getY() >= 350 && point.getY() <= 480) {
                    return diceNumbersOnPlain.get(2);
                } else if (point.getX() >= 170 && point.getX() <= 300 && point.getY() >= 350 && point.getY() <= 480) {
                    return diceNumbersOnPlain.get(3);
                } else {
                    try {
                        throw new NoDiceFoundException();
                    } catch (NoDiceFoundException e) {

                    } catch (Exception e) {
                        try {
                            throw new OtherExFoundException(e);
                        } catch (Exception ex) {

                        }
                    }
                }
                break;
            case 5:
                if (point.getX() >= 30 && point.getX() <= 140 && point.getY() >= 210 && point.getY() <= 320) {
                    return diceNumbersOnPlain.get(0);
                } else if (point.getX() >= 150 && point.getX() <= 260 && point.getY() >= 210 && point.getY() <= 320) {
                    return diceNumbersOnPlain.get(1);
                } else if (point.getX() >= 270 && point.getX() <= 380 && point.getY() >= 210 && point.getY() <= 320) {
                    return diceNumbersOnPlain.get(2);
                } else if (point.getX() >= 30 && point.getX() <= 140 && point.getY() >= 350 && point.getY() <= 460) {
                    return diceNumbersOnPlain.get(3);
                } else if (point.getX() >= 150 && point.getX() <= 260 && point.getY() >= 350 && point.getY() <= 460) {
                    return diceNumbersOnPlain.get(4);
                } else {
                    try {
                        throw new NoDiceFoundException();
                    } catch (NoDiceFoundException e) {

                    } catch (Exception e) {
                        try {
                            throw new OtherExFoundException(e);
                        } catch (Exception ex) {

                        }
                    }
                }
                break;
            default:
                return 0;

        }

        return 0;
    }
}
