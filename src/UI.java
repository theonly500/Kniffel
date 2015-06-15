import javax.swing.*;
import java.awt.*;
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
    private ImageIcon[] diceSides;

    //all invisible GUI display elements
    private JPanel base;
    private JPanel diceBase;
    private JPanel tableBase;

    //all background elements
    private Calc calc;
    private int playerCount;

    public UI(Calc calc){
        this.calc=calc;
    }

    public void start(ArrayList<String> names){
        playerCount=names.size();
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
        //create and add the button for every singular dice
        diceButtons=new JButton[5];
        for (int i=0;i<4;i++){
            diceButtons[i]=new JButton();
            diceButtons[i].setIcon(diceSides[1]);
            diceBase.add(diceButtons[i]);
            diceButtons[i].addActionListener(ae ->{
                //still need to add function
            });
        }
        //load the Textures for the dice sides
        diceSides=new ImageIcon[12];
        for(int i=0;i<12;i++){
            String tempString="D:\\Schule\\Info\\Kniffel\\src\\Icons"+i+".png";
            diceSides[i]=new ImageIcon(tempString,"this is a test");
        }
        //create and add a Button for rerolling
        rerollButton=new JButton("Reroll!");
        rerollButton.addActionListener(ae -> {
            //still to add function here
        });
        diceBase.add(rerollButton);
        //create and add a Panel for showing the results
        tableBase=new JPanel();
        tableBase.setLayout(new BoxLayout(tableBase, BoxLayout.Y_AXIS));
        base.add(tableBase);
        //create and add the JTable for showing the results
        resultTable=new JTable(18,playerCount+1);
        tableBase.add(resultTable);
        //create and add a Button for the confirmation of the result
        confirmSelection=new JButton("Confirm Selection");
        tableBase.add(confirmSelection);
        frame.pack();
        frame.setVisible(true);
    }
}
