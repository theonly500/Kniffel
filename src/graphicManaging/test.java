package graphicManaging;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class test {

    private SpecializedPanel basePanel;

    public test() {
        basePanel = new SpecializedPanel();

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