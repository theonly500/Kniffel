
import Exceptions.IntegerFoundException;
import Exceptions.IntegerNotFoundException;
import TableComponents.TableData;
import UI.ImageHandeling.ImageIconManager;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Locale;

class UI {

    //Directly Visible UI Elements
    //Buttons which contain the dices
    private JButton[] diceButtons;
    //Class Table Data which handels the dataflow of the shown JTable
    private TableData tableData;

    //all background elements
    //Calc class which runs all the checks for Results
    private final Calc calc;
    //Integer which contains the Number of Players playing
    private int playerCount;
    //Boolean Array which contains information for each dice to check if they get rerolled
    private final boolean[] rerollDice;
    //Integer Array which contains information of the side of each dice
    private final int[] diceResult;
    //Integer which shows the amount of rerolls a Player has done
    private int rerollCounter;
    //Integer which shows which player is active right now
    private int playerNumber;
    //Boolean which is used in the checking of the reroll
    private boolean selectionConfirmed;
    //Boolean which is used in the checking of the reroll
    private boolean isSelectionConfirmed;
    //String ArrayList which contains the name of every player
    private ArrayList<String> names;
    //ImageIconManager class which handles all Images
    private ImageIconManager imageIconManager;

    //Constructor of Class UI Creates all needed Objects, gets Calc for reference
    public UI(Calc calc){
        //Sets the Language for the JOptionPane Class
        JOptionPane.setDefaultLocale(Locale.ENGLISH);
        //Sets the Reference of the Calc Class Object
        this.calc=calc;
        //Init the Boolean Array rerollDice with null Value
        rerollDice=new boolean[5];
        //Init the Integer Array DiceResult with null Value
        diceResult=new int[5];
    }

    //Method start which starts the UI Processing, gets the ArrayList names containing all Playernames
    public void start(ArrayList<String> names){
        //Construct an Object of Class ImageIconManager
        imageIconManager=new ImageIconManager();
        //Init Integer rerollCounter with value 1
        rerollCounter=1;
        //Init Integer playerNumber with value 1
        playerNumber=1;
        //Init Boolean isSelectionConfirmed with value false
        isSelectionConfirmed=false;
        //Init Integer playerCount with the length of the names ArrayList
        playerCount=names.size();
        //Init Objects names ArrayList with the Method names ArrayList content
        this.names=names;
        //Construct an Object of Class TableData
        tableData=new TableData(playerCount,names);
        //Loop through all 5 dices
        for (int i=0;i<5;i++){
            //Init the Boolean Array entry at i with value false
            rerollDice[i]=false;
            //Init the Integer Array entry at i with random value, token from the Method rerollDice out of Calc class
            diceResult[i]=calc.rollDice();
        }
        createUI();
        setupBackgroundColor();
        totalSum();
    }

    private void createUI(){
        //create JFrame to contain every element
        JFrame frame = new JFrame("Yahtzee");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //add the base panel to the frame
        JPanel base = new JPanel();
        base.setLayout(new BoxLayout(base, BoxLayout.X_AXIS));
        frame.setContentPane(base);
        //add the panel for all dices
        JPanel diceBase = new JPanel();
        diceBase.setLayout(new BoxLayout(diceBase, BoxLayout.X_AXIS));
        base.add(diceBase);
        //create and add the button for every singular dice
        diceButtons=new JButton[5];
        for (int i=0;i<5;i++){
            //declare a final int for the ActionListener
            final int tempNr=i;
            //create a new Button
            diceButtons[i]=new JButton();
            //set the Button to correct Size, here it is 100px by 100px
            diceButtons[i].setMaximumSize(new Dimension(100,100));
            //loads the ImageIcon into Button with index i out of class imageIconManager through Method getImageIcons whit giving it the Number which needs to be shown
            diceButtons[i].setIcon(imageIconManager.getImageIcons(diceResult[i]));
            //updates the graphical Output of Button with index i to show the Image
            diceButtons[i].updateUI();
            //add the Dice to the Panel witch is nesting all Buttons for the Dices
            diceBase.add(diceButtons[i]);
            //Add the ActionListener to the Button with index i, using Lambda Syntax
            diceButtons[i].addActionListener(ae ->{
                //swaps the Pictures corespondingly to the Number
                //If clause checks whether the rerollcondition is given
                //If reacts while the rerollDice Array entry at tempNr is false
                if(!rerollDice[tempNr]){
                    //loads the ImageIcon into Button with index tempNr out of class imageIconManager through Method getImageIcons whit giving it the Number which needs to be shown
                    //method adds 6 to the integer value of that button to load the red picture
                    diceButtons[tempNr].setIcon(imageIconManager.getImageIcons(diceResult[tempNr]+6));
                    rerollDice[tempNr]=true;
                }
                //Else reacts while the rerollDice Array entry at tempNr is true
                else {
                    diceButtons[tempNr].setIcon(imageIconManager.getImageIcons(diceResult[tempNr]));
                    rerollDice[tempNr]=false;
                }
            });
        }
        //create and add a Button for rerolling
        JButton rerollButton = new JButton("Reroll!");
        //add ActionListener with Lambda
        rerollButton.addActionListener(ae -> rerollButtonEvent());
        diceBase.add(rerollButton);
        //create and add a Panel for showing the results
        JPanel tableBase = new JPanel();
        tableBase.setLayout(new BoxLayout(tableBase, BoxLayout.Y_AXIS));
        base.add(tableBase);
        //create and add the JTable for showing the results
        tableBase.add(tableData.returnTable());
        //create and add a Button for the confirmation of the result
        JButton confirmSelection = new JButton("Confirm Selection");
        //add ActionListener with Lambda
        confirmSelection.addActionListener(ae -> confirmSelectionButtonEvent());
        tableBase.add(confirmSelection);
        //sets the size of the Frame to the smallest size possible
        frame.pack();
        //set frame visible
        frame.setVisible(true);
        //mark the possible options
        visualizeOptions();
    }

    //Function is reRollClear returns a boolean based on the different states possible
    private boolean isRerollClear(){
        if(selectionConfirmed) {
            return true;
        }else if(rerollCounter<3){
            return true;
        }else {
            return false;
        }
    }

    //Function getRerollType returns a integer based on different states possible
    //returns 1 for full reroll
    //returns 2 for partial reroll
    //returns 0 for no reroll
    private int getRerollType(){
        if(selectionConfirmed){
            return 1;
        }else if(rerollCounter < 3){
            return 2;
        }else {
            return 0;
        }
    }

    //Function rerollType1 rerolls all Buttons and sets the correct number of current player
    private void rerollType1(){
        //for loop rerolls all dices
        for (int i = 0; i < 5; i++) {
            diceResult[i] = calc.rollDice();
            diceButtons[i].setIcon(imageIconManager.getImageIcons(diceResult[i]));
            rerollDice[i] = false;
        }
        //if cause checks for current player and if current player in list is not last player in list ads 1 to the number else sets last player in list is active to first player in list
        if (playerNumber < playerCount) {
            playerNumber++;
        } else {
            playerNumber = 1;
        }
        rerollCounter = 1;
        selectionConfirmed = false;
    }

    //Function rerollType2 rerolls checked Buttons
    private void rerollType2(){
        //loops through all buttons and rerolls those with the reroll boolean == true
        for (int i = 0; i < 5; i++) {
            if (rerollDice[i]) {
                diceResult[i] = calc.rollDice();
                diceButtons[i].setIcon(imageIconManager.getImageIcons(diceResult[i]));
                rerollDice[i] = false;
            }
        }
        rerollCounter++;
    }

    //Function rerollType0 is used as a error catcher
    private void rerollType0(){}

    //Function rerollButtonEvent is called when the rerollEvent is called
    private void rerollButtonEvent(){
        //create a new thread in order to keep the UI running
        Thread rerollButtonEventThread = new Thread(new Runnable() {
            @Override
            public void run() {
                //tries to reroll, checks if reroll is possible, then gets the type of reroll,
                try {
                    if(isRerollClear()){
                        int tempInt = getRerollType();
                        switch (tempInt) {
                            case 1:rerollType1();
                                break;
                            case 2:rerollType2();
                                break;
                            default: rerollType0();
                        }
                    }
                    //if reroll is not clear show a
                    else {
                        JOptionPane.showMessageDialog(null, "Please confirm your selection before proceeding","Selection Confirmation Needed",JOptionPane.WARNING_MESSAGE);
                    }
                }
                //catches Exceptions which might occur
                catch (Exception e){

                }
                //always does mark possible options
                finally {
                    visualizeOptions();
                }
            }
        });
        //starts the thread with the actions in it
        rerollButtonEventThread.start();
    }

    //Function confirmSelectionButtonEvent is called when the confirmSelection Button was pressed.
    private void confirmSelectionButtonEvent(){
        //Thread as above
        Thread confirmSelectionButtonEventThread = new Thread(new Runnable() {
            @Override
            public void run() {
                inputDataIntoTableModel();
                //resets the markings and flags
                if(isSelectionConfirmed){
                    resetMarkings();
                    isSelectionConfirmed=false;
                    selectionConfirmed=true;
                }
                //calculates the sum for your character
                totalSum();
            }
        });
        //starts thread
        confirmSelectionButtonEventThread.start();
    }

    //Function visualizeOptions gets called whenever it is needed to
    private void visualizeOptions() {
        Thread visualizeOptionsThread=new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Integer> temp;
                //get
                temp = calc.check(diceResult);
                for (int i = 0; i < 17; i++) {
                    int tempInt = 0;
                    if (i < 7) {
                        tempInt = i;
                    } else {
                        tempInt = i + 1;
                    }
                    if (tableData.returnValueAt(i, playerNumber) != null) {
                        if (tableData.returnValueAt(i, playerNumber).equals(" ") && temp.contains(tempInt)) {
                            tableData.setValueAt("  ", i, playerNumber);
                        }
                    } else if (temp.contains(tempInt)) {
                        tableData.setValueAt("  ", i, playerNumber);
                    }
                }
                for (int i = 0; i < 16; i++) {
                    int tempInt = 0;
                    if (i < 7) {
                        tempInt = i;
                    } else {
                        tempInt = i + 1;
                    }
                    if (tableData.returnValueAt(i, playerNumber) != null) {
                        if (tableData.returnValueAt(i, playerNumber).equals("  ") && !temp.contains(tempInt)) {
                            tableData.setValueAt(" ", i, playerNumber);
                        }
                    }
                }
                if (tableData.returnValueAt(15, playerNumber) == null || tableData.returnValueAt(15, playerNumber).contentEquals(" ")) {
                    tableData.setValueAt("  ", 15, playerNumber);
                }
            }
        });
        visualizeOptionsThread.start();
    }

    private void resetMarkings(){
        for(int y=0;y<18;y++){
            for(int x=0;x<=playerCount;x++){
                if(tableData.returnValueAt(y,x)!=null){
                    if(tableData.returnValueAt(y,x).equals("  ")){
                        tableData.setValueAt(" ",y,x);
                    }
                }
                else {
                    tableData.setValueAt(" ",y,x);
                }
            }
        }
    }

    private void inputDataIntoTableModel(){
        boolean exceptionThrown=false;
        int tempInt = tableData.returnTable().getSelectedRow();
        if (1 <= tempInt && tempInt <= 6) {
            try {
                tableData.preSetValueAt(tempInt, playerNumber);
            }catch (IntegerFoundException e){
                exceptionThrown=true;
                selectProperLineMessageDialog();
            }catch (IntegerNotFoundException e){
                if(tableData.returnValueAt(tempInt,playerNumber).equals(" ")){
                    if(crossOutMessageDialog()==JOptionPane.YES_OPTION){
                        tableData.setValueAt(calc.points(tempInt),tempInt,playerNumber);
                        isSelectionConfirmed = true;
                    }else exceptionThrown=true;
                }
            }finally {
                if(!exceptionThrown){
                    tableData.setValueAt(calc.points(tempInt),tempInt,playerNumber);
                    isSelectionConfirmed = true;
                }
            }

        } else if (9 <= tempInt && tempInt <= 16) {
            try {
                tableData.preSetValueAt(tempInt, playerNumber);
            }catch (IntegerFoundException e){
                exceptionThrown=true;
                selectProperLineMessageDialog();
            }catch (IntegerNotFoundException e){
                if(tableData.returnValueAt(tempInt,playerNumber).equals(" ")){
                    if(crossOutMessageDialog()==JOptionPane.YES_OPTION){
                        tableData.setValueAt(calc.points(tempInt+1),tempInt,playerNumber);
                        isSelectionConfirmed = true;
                    }else exceptionThrown=true;
                }
            }finally {
                if(!exceptionThrown){
                    tableData.setValueAt(calc.points(tempInt+1),tempInt,playerNumber);
                    isSelectionConfirmed = true;
                }
            }
        } else {
            selectProperLineMessageDialog();
            isSelectionConfirmed = false;
        }
    }

    private int crossOutMessageDialog(){
        return JOptionPane.showConfirmDialog(null, "Do you want to cross out the result?","Cross-Out Confirmation",JOptionPane.YES_NO_OPTION);
    }

    private void selectProperLineMessageDialog(){
        JOptionPane.showMessageDialog(null, "Please choose a proper Line","Wrong Line Selected",JOptionPane.WARNING_MESSAGE);
    }

    private void setupBackgroundColor(){
        for (int y=0;y<tableData.returnTable().getRowCount();y++){
            for(int x=0;x<tableData.returnTable().getColumnCount();x++){
                if(tableData.returnValueAt(y,x)==null){
                    tableData.setValueAt(" ",y,x);
                }
            }
        }
    }

    private void topSumAndBonus(){
        for(int i=1;i<=playerCount;i++){
             int tempIntForSum=0;
             for(int y=1;y<=6;y++){
                 try {
                     tempIntForSum=tempIntForSum+Integer.parseInt(tableData.returnValueAt(y, i).toString());
                 }catch (Exception e){
                     tableData.setValueAt(0,7,i);
                     tableData.setValueAt(0,8,i);
                 }
                 if(tempIntForSum>=63){
                     tableData.setValueAt(35,8,i);
                 }
                 else {
                    tableData.setValueAt(0,8,i);
                 }
                 tableData.setValueAt(tempIntForSum,7,i);
             }
        }
    }

    private void bottomSum(){
        for(int i=1;i<=playerCount;i++){
            int tempIntForSum=0;
            for(int y=9;y<=15;y++){
                try {
                    tempIntForSum = tempIntForSum + Integer.parseInt(tableData.returnValueAt(y, i).toString());
                }
                catch (Exception e){
                    tableData.setValueAt(0,16,i);
                }
            }
            tableData.setValueAt(tempIntForSum,16,i);
        }
    }

    private void totalSum(){
        topSumAndBonus();
        bottomSum();
        for(int i=1;i<=playerCount;i++){
            try {
                tableData.setValueAt(Integer.parseInt(tableData.returnValueAt(16, i).toString()) + Integer.parseInt(tableData.returnValueAt(7, i).toString()) + Integer.parseInt(tableData.returnValueAt(8, i).toString()), 17, i);
            }catch (Exception e){
                tableData.setValueAt(0,17,i);
            }
        }
    }
}

