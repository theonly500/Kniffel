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
    private boolean selectionConfirmed;
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
        rerollButton.addActionListener(ae -> buttonEvent());
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
        confirmSelection.addActionListener(ae ->{selectionConfirmed=true;});
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

    private void buttonEvent(){
        System.out.println("Beginning: "+"rerollCounter: "+rerollCounter+" playerNumber: "+playerNumber+" playerCount: "+playerCount+" selection Confirmed: "+selectionConfirmed);

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
        System.out.println("Ending: "+"rerollCounter: "+rerollCounter+" playerNumber: "+playerNumber+" playerCount: "+playerCount+" selection Confirmed: "+selectionConfirmed);
        visualizeOptions();

    }

    private void visualizeOptions() {
        ArrayList<Integer> temp;
        temp = calc.check(diceResult);
        //System.out.println(temp);
        for(int i=0;i<temp.size();i++)
        {
            int tempInt = temp.get(i);
            switch(tempInt)
            {
                case 1:
                    if (resultTableModel.getValueAt(1,playerNumber)==null||resultTableModel.getValueAt(1,playerNumber).toString().equals(" ")){
                    resultTableModel.setValueAt("  ",1,playerNumber);
                }
                    break;
                case 2:
                    if (resultTableModel.getValueAt(2,playerNumber)==null||resultTableModel.getValueAt(2,playerNumber).toString().contentEquals(" ")){
                    resultTableModel.setValueAt("  ",2,playerNumber);
                }
                    break;
                case 3:
                    if (resultTableModel.getValueAt(3,playerNumber)==null||resultTableModel.getValueAt(3,playerNumber).toString().contentEquals(" ")){
                        resultTableModel.setValueAt("  ", 3, playerNumber);
                    }
                    break;
                case 4:
                    if (resultTableModel.getValueAt(4,playerNumber)==null||resultTableModel.getValueAt(4,playerNumber).toString().contentEquals(" ")){
                        resultTableModel.setValueAt("  ", 4, playerNumber);
                    }
                    break;
                case 5:
                    if (resultTableModel.getValueAt(5,playerNumber)==null||resultTableModel.getValueAt(5,playerNumber).toString().contentEquals(" ")){
                        resultTableModel.setValueAt("  ",5,playerNumber);
                    }
                    break;
                case 6:
                    if (resultTableModel.getValueAt(6,playerNumber)==null||resultTableModel.getValueAt(6,playerNumber).toString().contentEquals(" ")){
                        resultTableModel.setValueAt("  ",6,playerNumber);
                    }
                    break;
                case 10:
                    if (resultTableModel.getValueAt(9,playerNumber)==null||resultTableModel.getValueAt(9,playerNumber).toString().equals(" ")){
                        resultTableModel.setValueAt("  ",9,playerNumber);
                    }
                    break;
                case 11:
                    if (resultTableModel.getValueAt(10,playerNumber)==null||resultTableModel.getValueAt(10,playerNumber).toString().contentEquals(" ")){
                        resultTableModel.setValueAt("  ",10,playerNumber);
                    }
                    break;
                case 12:
                    if (resultTableModel.getValueAt(11,playerNumber)==null||resultTableModel.getValueAt(11,playerNumber).toString().contentEquals(" ")){
                        resultTableModel.setValueAt("  ", 11, playerNumber);
                    }
                    break;
                case 13:
                    if (resultTableModel.getValueAt(12,playerNumber)==null||resultTableModel.getValueAt(12,playerNumber).toString().contentEquals(" ")){
                        resultTableModel.setValueAt("  ", 12, playerNumber);
                    }
                    break;
                case 14:
                    if (resultTableModel.getValueAt(13,playerNumber)==null||resultTableModel.getValueAt(13,playerNumber).toString().contentEquals(" ")){
                        resultTableModel.setValueAt("  ",13,playerNumber);
                    }
                    break;
                case 15:
                    if (resultTableModel.getValueAt(14,playerNumber)==null||resultTableModel.getValueAt(14,playerNumber).toString().contentEquals(" ")){
                        resultTableModel.setValueAt("  ",14,playerNumber);
                    }
                    break;
                case 16:
                    if (resultTableModel.getValueAt(15,playerNumber)==null||resultTableModel.getValueAt(15,playerNumber).toString().contentEquals(" ")){
                        resultTableModel.setValueAt("  ",15, playerNumber);
                    }
                    break;
                default:

            }
        }
        for(int i=0;i<16;i++) {
            if (resultTableModel.getValueAt(i+1, playerNumber) != null) {
                if (resultTableModel.getValueAt(i+1, playerNumber).toString().equals("  ") && !temp.contains(i)) {
                    resultTableModel.setValueAt(" ", i+1, playerNumber);
                }
            }
        }
        if (resultTableModel.getValueAt(15,playerNumber)==null||resultTableModel.getValueAt(15,playerNumber).toString().contentEquals(" ")){
            resultTableModel.setValueAt("  ",15, playerNumber);
        }
    }
}

