import TableComponents.TableRenderer;
import UI.ImageHandeling.ImageIconManager;

import javax.imageio.ImageIO;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;

class UI {

    //
    private JButton[] diceButtons;
    private JTable resultTable;

    //all background elements
    private final Calc calc;
    private int playerCount;
    private Image[] imageNr;
    private ImageIcon[] imageIcons;
    private final boolean[] rerollDice;
    private final int[] diceResult;
    private int rerollCounter;
    private int playerNumber;
    private boolean selectionConfirmed;
    private boolean isSelectionConfirmed;
    private ArrayList<String> names;
    private DefaultTableModel resultTableModel;
    private ImageIconManager imageIconManager;

    public UI(Calc calc){
        //implement calc
        this.calc=calc;
        imageIconManager=new ImageIconManager();
        //create the arrays for the dices
        rerollDice=new boolean[5];
        diceResult=new int[5];
        rerollCounter=1;
        playerNumber=1;
        isSelectionConfirmed=false;
    }

    public void start(ArrayList<String> names){
        playerCount=names.size();
        this.names=names;
        for (int i=0;i<5;i++){
            rerollDice[i]=false;
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
            //set the Button to correct Size
            diceButtons[i].setMaximumSize(new Dimension(100,100));
            //load in the first image
            diceButtons[i].setIcon(imageIconManager.getImageIcons(diceResult[i]));
            diceButtons[i].updateUI();
            //add the Dice to the Panel witch is nesting all Buttons for the Dices
            diceBase.add(diceButtons[i]);
            diceButtons[i].addActionListener(ae ->{
                //swaps the Pictures corespondingly to the Number
                if(!rerollDice[tempNr]){
                    diceButtons[tempNr].setIcon(imageIconManager.getImageIcons(diceResult[tempNr]+6));
                    rerollDice[tempNr]=true;
                }
                else {
                    diceButtons[tempNr].setIcon(imageIconManager.getImageIcons(diceResult[tempNr]));
                    rerollDice[tempNr]=false;
                }
            });
        }
        diceBase.setSize(500, 150);
        //create and add a Button for rerolling
        JButton rerollButton = new JButton("Reroll!");
        rerollButton.addActionListener(ae -> rerollButtonEvent());
        diceBase.add(rerollButton);
        //create and add a Panel for showing the results
        JPanel tableBase = new JPanel();
        tableBase.setLayout(new BoxLayout(tableBase, BoxLayout.Y_AXIS));
        base.add(tableBase);
        //create and add the JTable for showing the results
        resultTableModel = new DefaultTableModel(18,playerCount+1) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }
        };
        resultTable=new JTable(resultTableModel);
        TableRenderer tableRenderer = new TableRenderer();
        resultTable.setDefaultRenderer(String.class, tableRenderer);
        setValues();
        TableCellEditor tableCellEditor=new TableCellEditor() {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                return null;
            }

            @Override
            public Object getCellEditorValue() {
                return null;
            }

            @Override
            public boolean isCellEditable(EventObject anEvent) {
                return false;
            }

            @Override
            public boolean shouldSelectCell(EventObject anEvent) {
                return false;
            }

            @Override
            public boolean stopCellEditing() {
                return false;
            }

            @Override
            public void cancelCellEditing() {

            }

            @Override
            public void addCellEditorListener(CellEditorListener l) {

            }

            @Override
            public void removeCellEditorListener(CellEditorListener l) {

            }
        };
        resultTable.setDefaultEditor(String.class,tableCellEditor);
        tableBase.add(resultTable);
        //create and add a Button for the confirmation of the result
        JButton confirmSelection = new JButton("Confirm Selection");
        confirmSelection.addActionListener(ae -> confirmSelectionButtonEvent());
        tableBase.add(confirmSelection);
        frame.pack();
        frame.setVisible(true);
        visualizeOptions();
    }

    private void setValues() {
        resultTableModel.setValueAt("Players", 0, 0);
        resultTableModel.setValueAt("Ones", 1, 0);
        resultTableModel.setValueAt("Twos", 2, 0);
        resultTableModel.setValueAt("Threes", 3, 0);
        resultTableModel.setValueAt("Fours", 4, 0);
        resultTableModel.setValueAt("Fives", 5, 0);
        resultTableModel.setValueAt("Sixes", 6, 0);
        resultTableModel.setValueAt("Sum top", 7, 0);
        resultTableModel.setValueAt("Bonus", 8, 0);
        resultTableModel.setValueAt("Three of a kind", 9, 0);
        resultTableModel.setValueAt("Four of a kind", 10, 0);
        resultTableModel.setValueAt("Full House", 11, 0);
        resultTableModel.setValueAt("Small Straight", 12, 0);
        resultTableModel.setValueAt("Large Straight", 13, 0);
        resultTableModel.setValueAt("Yahtzee", 14, 0);
        resultTableModel.setValueAt("Chance", 15, 0);
        resultTableModel.setValueAt("Sum bottom", 16, 0);
        resultTableModel.setValueAt("Total", 17, 0);
        for(int i=0; i<playerCount; i++)
        {
            resultTableModel.setValueAt(" "+names.get(i)+" ", 0, i+1);
        }
    }

    private void setUpImageIcons(){
        imageIcons=new ImageIcon[12];
        for(int i=0;i<imageNr.length;i++){
            imageIcons[i]=new ImageIcon(imageNr[i]);
        }
    }

    private boolean isRerollClear(){
        if(selectionConfirmed) {
            return true;
        }else if(rerollCounter<3){
            return true;
        }else {
            return false;
        }
    }

    private int getRerollType(){
        if(selectionConfirmed){
            return 1;
        }else if(rerollCounter < 3){
            return 2;
        }else {
            return 0;
        }
    }

    private void rerollType1(){
        for (int i = 0; i < 5; i++) {
            diceResult[i] = calc.rollDice();
            diceButtons[i].setIcon(imageIconManager.getImageIcons(diceResult[i]));
            rerollDice[i] = false;
        }
        if (playerNumber < playerCount) {
            playerNumber++;
        } else {
            playerNumber = 1;
        }
        rerollCounter = 1;
        selectionConfirmed = false;
    }

    private void rerollType2(){
        for (int i = 0; i < 5; i++) {
            if (rerollDice[i]) {
                diceResult[i] = calc.rollDice();
                diceButtons[i].setIcon(imageIconManager.getImageIcons(diceResult[i]));
                rerollDice[i] = false;
            }
        }
        rerollCounter++;
    }

    private void rerollType0(){}

    private void rerollButtonEvent(){
        Thread rerollButtonEventThread = new Thread(new Runnable() {
            @Override
            public void run() {
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
                    } else {
                        JOptionPane.showMessageDialog(null, "Please confirm your selection before proceeding");
                    }
                    visualizeOptions();
                }
                catch (Exception e){

                }
            }
        });
        rerollButtonEventThread.start();
    }

    private void confirmSelectionButtonEvent(){
        inputDataIntoTableModel();
        if(isSelectionConfirmed){
            resetMarkings();
            isSelectionConfirmed=false;
            selectionConfirmed=true;
        }
        totalSum();
    }

    private void visualizeOptions() {
        ArrayList<Integer> temp;
        temp = calc.check(diceResult);
        System.out.println(temp);
        for(int i=0;i<17;i++)
        {
            int tempInt;
            if(i<7){
                tempInt=i;
            }
            else {
                tempInt=i+1;
            }
            if (resultTableModel.getValueAt(i, playerNumber) != null) {
                if (resultTableModel.getValueAt(i, playerNumber).toString().equals(" ") && temp.contains(tempInt)) {
                    resultTableModel.setValueAt("  ", i, playerNumber);
                }
            }else if(temp.contains(tempInt)){
                resultTableModel.setValueAt("  ",i,playerNumber);
            }
        }
        for(int i=0;i<16;i++) {
            int tempInt;
            if(i<7){
                tempInt=i;
            }
            else {
                tempInt=i+1;
            }
            if (resultTableModel.getValueAt(i, playerNumber) != null) {
                if (resultTableModel.getValueAt(i, playerNumber).toString().equals("  ") && !temp.contains(tempInt)) {
                    resultTableModel.setValueAt(" ", i, playerNumber);
                }
            }
        }
        if (resultTableModel.getValueAt(15,playerNumber)==null||resultTableModel.getValueAt(15,playerNumber).toString().contentEquals(" ")){
            resultTableModel.setValueAt("  ",15, playerNumber);
        }
    }

    private void resetMarkings(){
        for(int y=0;y<18;y++){
            for(int x=0;x<=playerCount;x++){
                if(resultTableModel.getValueAt(y,x)!=null){
                    if(resultTableModel.getValueAt(y,x).equals("  ")){
                        resultTableModel.setValueAt(" ",y,x);
                    }
                }
                else {
                    resultTableModel.setValueAt(" ",y,x);
                }
            }
        }
    }

    private void inputDataIntoTableModel(){
        int tempInt = resultTable.getSelectedRow();
            if (1 <= tempInt && tempInt <= 6) {
                resultTableModel.setValueAt(calc.points(tempInt), tempInt, playerNumber);
                isSelectionConfirmed = true;
            } else if (9 <= tempInt && tempInt <= 16) {
                resultTableModel.setValueAt(calc.points(tempInt + 1), tempInt, playerNumber);
                isSelectionConfirmed = true;
            } else {
                JOptionPane.showMessageDialog(null, "Please choose a proper Line");
                isSelectionConfirmed = false;
            }
    }

    private void setupBackgroundColor(){
        for (int y=0;y<resultTableModel.getRowCount();y++){
            for(int x=0;x<resultTableModel.getColumnCount();x++){
                if(resultTableModel.getValueAt(y,x)==null){
                    resultTableModel.setValueAt(" ",y,x);
                }
            }
        }
    }

    private void topSumAndBonus(){
        for(int i=1;i<=playerCount;i++){
             int tempIntForSum=0;
             for(int y=1;y<=6;y++){
                 try {
                     tempIntForSum=tempIntForSum+Integer.parseInt(resultTableModel.getValueAt(y,i).toString());
                 }catch (Exception e){
                     resultTableModel.setValueAt(0,7,i);
                     resultTableModel.setValueAt(0,8,i);
                 }
                 if(tempIntForSum>=63){
                     resultTableModel.setValueAt(35,8,i);
                 }
                 else {
                    resultTableModel.setValueAt(0,8,i);
                 }
                 resultTableModel.setValueAt(tempIntForSum,7,i);
             }
        }
    }

    private void bottomSum(){
        for(int i=1;i<=playerCount;i++){
            int tempIntForSum=0;
            for(int y=9;y<=15;y++){
                try {
                    tempIntForSum = tempIntForSum + Integer.parseInt(resultTableModel.getValueAt(y, i).toString());
                }
                catch (Exception e){
                    resultTableModel.setValueAt(0,16,i);
                }
            }
            resultTableModel.setValueAt(tempIntForSum,16,i);
        }
    }

    private void totalSum(){
        topSumAndBonus();
        bottomSum();
        for(int i=1;i<=playerCount;i++){
            try {
                resultTableModel.setValueAt(Integer.parseInt(resultTableModel.getValueAt(16, i).toString()) + Integer.parseInt(resultTableModel.getValueAt(7, i).toString()) + Integer.parseInt(resultTableModel.getValueAt(8, i).toString()), 17, i);
            }catch (Exception e){
                resultTableModel.setValueAt("0",17,i);
            }
        }
    }
}

