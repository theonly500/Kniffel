import javax.swing.*;

public class Window {
    JFrame windowFrame;

    public Window(){
       createWindow();
    }


    private void createWindow(){
        windowFrame=new JFrame("Kniffel");
        windowFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        windowFrame.setVisible(true);
    }
}
