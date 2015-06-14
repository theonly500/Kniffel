import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Felix on 11.06.2015.
 */
public class UI {

    //all visible GUI display elements
    JFrame frame;
    JButton rerollButton;
    JTable resultTable;
    JButton confirmSelection;

    //all invisible GUI display elements
    JPanel base;
    JPanel diceBase;
    JPanel[] individualDiceBase;
    JPanel tableBase;

    //all background elements
    Calc calc;
    int playerCount;

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
        //create and add the panel for every singular dice
        individualDiceBase = new  JPanel[5];
        for (int i=0;i<5;i++){
            individualDiceBase[i]=new JPanel();
            individualDiceBase[i].setLayout(new BoxLayout(individualDiceBase[i],BoxLayout.Y_AXIS));
            base.add(individualDiceBase[i]);
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
