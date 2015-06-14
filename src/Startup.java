/**
 * Created by Felix on 11.06.2015.
 */

public class Startup
{

    public Startup()
    {
    }

    public static void main(String[] args) {
        Calc calc=new Calc();
        UI ui=new UI(calc);
        FirstFrame firstFrame=new FirstFrame(ui);
    }

}