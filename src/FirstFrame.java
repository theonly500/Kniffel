import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class FirstFrame {

    private final UI userinterface;

    public FirstFrame(UI userinterface){
        this.userinterface=userinterface;
        //create and set the basics of the JFrame
        JFrame selectionFrame = new JFrame("Player Selection");
        selectionFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //create and set the basics of the JPanel which contains all other GUI Elements
        JPanel base=new JPanel();
        base.setLayout(new BoxLayout(base, BoxLayout.Y_AXIS));
        //creates and add a JLabel which contains a brief explanation of the Name System
        JLabel label=new JLabel("Only Fields that have been edited will be used in the Game");
        base.add(label);
        //create, set basic properties and add a JPanel which contains the JFormattedTextFields which are used to input the names
        JPanel textAreasBase=new JPanel();
        base.add(textAreasBase, BorderLayout.NORTH);
        textAreasBase.setLayout(new BoxLayout(textAreasBase,BoxLayout.X_AXIS));
        //create a Array of JFormattedTextFields
        JFormattedTextField[] textFields=new JFormattedTextField[4];
        //initialize all JFormattedTextFields
        for(int i=0;i<4;i++){
            final int finalTempNr=i;
            final int finalTempContentNumber=4-i;
            textFields[i]=new JFormattedTextField();
            //add a FocusListener to the JFormattedTextField to make the input of text easier
            textFields[i].addFocusListener(new FocusListener() {
                String temp;

                @Override
                public void focusGained(FocusEvent e) {
                    temp = textFields[finalTempNr].getText();
                    textFields[finalTempNr].setText("");
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (textFields[finalTempNr].getText().isEmpty() && temp.contains("Player " + finalTempContentNumber)) {
                        textFields[finalTempNr].setText("Player " + finalTempContentNumber);
                    } else {
                        if (!temp.contains("Player " + finalTempContentNumber)){
                            textFields[finalTempNr].setText(temp);
                        }
                    }
                }
            });
            //add the JFormattedTextField to the textAreasBase JPanel
            textFields[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            textAreasBase.add(textFields[i], BoxLayout.X_AXIS);
            textFields[i].setText("Player "+finalTempContentNumber);
        }
        //create, set attributes and add a JButton for the confirmation of Usernames
        JButton confirm=new JButton("Confirm Selection");
        confirm.setAlignmentX(Component.RIGHT_ALIGNMENT);
        base.add(confirm, BorderLayout.SOUTH);
        confirm.addActionListener(ae -> {
                                        selectionFrame.dispose();
                                        startGame(textFields[3].getText(), textFields[2].getText(), textFields[1].getText(), textFields[0].getText());
                                  }
        );
        //make the JFrame the right size, non resizable and make it visible
        selectionFrame.setContentPane(base);
        selectionFrame.pack();
        selectionFrame.setResizable(false);
        selectionFrame.setLocationRelativeTo(null);
        selectionFrame.setVisible(true);
    }

    //start up the game
    private void startGame(String playerOne, String playerTwo, String playerThree, String playerFour){
        ArrayList<String> names=new ArrayList<>();
        //check if there was a player entered
        if(!playerOne.contentEquals("Player 1")){
            names.add(playerOne);
        }
        if(!playerTwo.contentEquals("Player 2")){
            names.add(playerTwo);
        }
        if(!playerThree.contentEquals("Player 3")){
            names.add(playerThree);
        }
        if(!playerFour.contentEquals("Player 4")){
            names.add(playerFour);
        }
        userinterface.start(names);
    }
}