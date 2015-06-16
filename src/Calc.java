import java.util.Random;

/**
 * Created by Felix on 11.06.2015.
 */
public class Calc {

    private Random random;
    public Calc(){
        random=new Random();
    }

    public int rollDice(){
        return random.nextInt(5)+1;
    }

}
