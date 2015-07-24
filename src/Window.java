import graphicManaging.BasicComponentManaging;

import javax.swing.*;

public class Window {
    JFrame windowFrame;
    BasicComponentManaging basicComponentManaging;

    public Window(){
        basicComponentManaging=new BasicComponentManaging();
        createWindow();
        windowFrame.add(basicComponentManaging.getBasePanel());
    }


    private void createWindow(){
        windowFrame=new JFrame("Kniffel");
        windowFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        windowFrame.setVisible(true);
        windowFrame.setSize(1024,600);
    }
}
