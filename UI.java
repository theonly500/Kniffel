import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Felix on 11.06.2015.
 */
public class UI {

    //all visible GUI display elements
    JFrame frame;

    //all invisible GUI display elements
    JPanel base;
    JPanel diceBase;
    JPanel[] individualDiceBase;

    //all background elements
    Calc calc;
    int playerCount;

    public UI(Calc calc,ArrayList<String> names){
        this.calc=calc;
        playerCount=names.size();
    }

    private void createUI(){
        //create JFrame to contain every element
        frame=new JFrame("Yahtzee");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        base=new JPanel();
        base.setLayout(new BoxLayout(base, BoxLayout.X_AXIS));
        frame.add(base);
        diceBase=new JPanel();
        base.add(diceBase);
    }
}
