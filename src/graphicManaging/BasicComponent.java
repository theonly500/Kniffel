package graphicManaging;

//test

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

