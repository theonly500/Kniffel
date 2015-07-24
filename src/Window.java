import graphicManaging.BasicComponent;

import javax.swing.*;

public class Window {
    JFrame windowFrame;
    BasicComponent basicComponent;

    public Window(){
        basicComponent=new BasicComponent();
        createWindow();
        windowFrame.add(basicComponent.getBasePanel());
    }


    private void createWindow(){
        windowFrame=new JFrame("Kniffel");
        windowFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        windowFrame.setVisible(true);
        windowFrame.setSize(1024,600);
    }
}
