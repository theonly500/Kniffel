package graphicManaging;

//test

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class BasicComponent{

    private SpecializedPanel basePanel;

    public BasicComponent() {
        basePanel = new SpecializedPanel();
        prepareJPanel();
    }

    public JPanel getBasePanel() {
        return basePanel;
    }

    private void prepareJPanel() {
        basePanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton()==1){
                    Random r=new Random();
                    basePanel.addDiceToPlain(r.nextInt(5)+1);
                    basePanel.repaint();
                }else if(e.getButton()==3){
                    try {
                        System.out.println(basePanel.getDiceAt(new Point(e.getX(), e.getY())));
                    }catch (Exception ex){
                        System.out.println(ex.getCause());
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

        });
    }
}

