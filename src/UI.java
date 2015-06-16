import javax.imageio.ImageIO;
import javax.swing.*;
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

    public UI(Calc calc){
        //implement calc
        this.calc=calc;
        //create the arrays for the dices
        rerollDice=new boolean[5];
        diceResult=new int[5];
        for (int i=0;i<5;i++){
            rerollDice[i]=false;
            diceResult[i]=calc.rollDice();
        }
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
        //load all Images
        imageNr=new Image[12];
        for (int i=0;i<12;i++){
            int tmpNr=i+1;
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
                    diceButtons[tempNr].setIcon(giveImageIcon(diceResult[tempNr]-6));
                    rerollDice[tempNr]=false;
                }
            });
        }
        diceBase.setSize(500,150);
        //create and add a Button for rerolling
        rerollButton=new JButton("Reroll!");
        rerollButton.addActionListener(ae -> {
            for (int i=0;i<5;i++){
                if(rerollDice[i]){
                    diceResult[i]=calc.rollDice();
                    diceButtons[i].setIcon(giveImageIcon(diceResult[i]));
                    rerollDice[i]=false;
                }
            }
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

    private ImageIcon giveImageIcon(int imageNumber){
        return new ImageIcon(imageNr[imageNumber]);
    }
}
