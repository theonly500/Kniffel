@SuppressWarnings("ALL")
class Startup
{

    public static void main(String[] args) {
        Calc calc=new Calc();
        UI ui=new UI(calc);
        FirstFrame firstFrame=new FirstFrame(ui);
    }

}