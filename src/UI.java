import javax.imageio.ImageIO;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Felix on 11.06.2015.
 */
public class UI {

    //all visible GUI display elements
    private JFrame frame;
    private JButton[] diceButtons;
    private JButton rerollButton;
    private JTable resultTable;
    private JButton confirmSelection;

    //all invisible GUI display elements
    private JPanel base;
    private JPanel diceBase;
    private JPanel tableBase;

    //all background elements
    private Calc calc;
    private int playerCount;
    private Image[] imageNr;
    private boolean[] rerollDice;
    private int[] diceResult;
    private int rerollCounter;
    private int playerNumber;
    private int selectedRow;
    private boolean selectionConfirmed;
    private boolean isSelectionConfirmed;
    private boolean hasSelectedNewRow;
    private ArrayList<String> names;
    private TableRenderer tableRenderer;
    private DefaultTableModel resultTableModel;

    public UI(Calc calc){
        //implement calc
        this.calc=calc;
        //create the arrays for the dices
        rerollDice=new boolean[5];
        diceResult=new int[5];
        rerollCounter=1;
        playerNumber=1;
        selectedRow=0;
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
    }

    private void createUI(){
        //create JFrame to contain every element
        frame=new JFrame("Yahtzee");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //add the base panel to the frame
        base=new JPanel();
        base.setLayout(new BoxLayout(base, BoxLayout.X_AXIS));
        frame.setContentPane(base);
        //add the panel for all dices
        diceBase=new JPanel();
        diceBase.setLayout(new BoxLayout(diceBase, BoxLayout.X_AXIS));
        base.add(diceBase);
        //load all Images
        imageNr=new Image[12];
        for (int i=0;i<12;i++){
            int tmpNr=i+1;
            //create Temp filepath
            String tempFilepath=new String("Icons/"+tmpNr+".png");
            try {
                Image imageTemp = ImageIO.read(getClass().getResource(tempFilepath));
                imageNr[i]=imageTemp.getScaledInstance(100,100,Image.SCALE_FAST);
            }
            catch (IOException ioE){

            }
        }
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
            diceButtons[i].setIcon(giveImageIcon(diceResult[i]));
            diceButtons[i].updateUI();
            //add the Dice to the Panel witch is nesting all Buttons for the Dices
            diceBase.add(diceButtons[i]);
            diceButtons[i].addActionListener(ae ->{
                //swaps the Pictures corespondingly to the Number
                if(!rerollDice[tempNr]){
                    diceButtons[tempNr].setIcon(giveImageIcon(diceResult[tempNr]+6));
                    rerollDice[tempNr]=true;
                }
                else {
                    diceButtons[tempNr].setIcon(giveImageIcon(diceResult[tempNr]));
                    rerollDice[tempNr]=false;
                }
            });
        }
        diceBase.setSize(500,150);
        //create and add a Button for rerolling
        rerollButton =new JButton("Reroll!");
        rerollButton.addActionListener(ae -> {
            rerollButtonEvent();
        });
        diceBase.add(rerollButton);
        //create and add a Panel for showing the results
        tableBase=new JPanel();
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
        tableRenderer=new TableRenderer();
        resultTable.setDefaultRenderer(String.class,tableRenderer);
        setValues();
        tableBase.add(resultTable);
        //create and add a Button for the confirmation of the result
        confirmSelection=new JButton("Confirm Selection");
        confirmSelection.addActionListener(ae ->{
            confirmSelectionButtonEvent();
        });
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

    private ImageIcon giveImageIcon(int imageNumber){
        return new ImageIcon(imageNr[imageNumber]);
    }

    private void rerollButtonEvent(){
        //System.out.println("Beginning: "+"rerollCounter: "+rerollCounter+" playerNumber: "+playerNumber+" playerCount: "+playerCount+" selection Confirmed: "+selectionConfirmed);
        if(selectionConfirmed==true&&rerollCounter<3) {
            for (int i = 0; i < 5; i++) {
                diceResult[i] = calc.rollDice();
                diceButtons[i].setIcon(giveImageIcon(diceResult[i]));
                rerollDice[i] = false;
            }
            if(playerNumber < playerCount)
            {
                playerNumber++;
            }
            else
            {
                playerNumber = 1;
            }
            rerollCounter = 1;
            selectionConfirmed = false;
        }
        else if(rerollCounter<3)
        {
            for (int i=0;i<5;i++){
                if(rerollDice[i]){
                    diceResult[i]=calc.rollDice();
                    diceButtons[i].setIcon(giveImageIcon(diceResult[i]));
                    rerollDice[i]=false;
                }
            }
            rerollCounter++;
        }
        else
        {
            if(selectionConfirmed)
            {
                for(int i=0; i<5; i++)
                {
                    diceResult[i]=calc.rollDice();
                    diceButtons[i].setIcon(giveImageIcon(diceResult[i]));
                    rerollDice[i]=false;
                }
                if(playerNumber < playerCount)
                {
                    playerNumber++;
                }
                else
                {
                    playerNumber = 1;
                }
                rerollCounter=1;
                selectionConfirmed=false;
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Please confirm your selection before proceeding");
            }
        }
        //System.out.println("Ending: "+"rerollCounter: "+rerollCounter+" playerNumber: "+playerNumber+" playerCount: "+playerCount+" selection Confirmed: "+selectionConfirmed);
        visualizeOptions();

    }

    private void confirmSelectionButtonEvent(){
        inputDataIntoTableModel();
        if(isSelectionConfirmed){
            resetMarkings();
            isSelectionConfirmed=false;
            selectionConfirmed=true;
        }else{
            confirmSelectionButtonEvent();
        }
    }

    private void visualizeOptions() {
        ArrayList<Integer> temp;
        temp = calc.check(diceResult);
        System.out.println(temp);
        for(int i=0;i<17;i++)
        {
            int tempInt=0;
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
            int tempInt=0;
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
        if(selectedRow!=tempInt) {
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
        selectedRow=tempInt;
    }
}

