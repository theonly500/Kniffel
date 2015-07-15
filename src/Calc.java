import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

class Calc {

    private final Random random;
    private final ArrayList<Integer> wuerfel;
    private final ArrayList<Integer> table;

    public Calc(){
        random=new Random();
        table = new ArrayList<>();
        wuerfel = new ArrayList<>();
    }

    public int rollDice(){
        return random.nextInt(6);
    }

    public ArrayList<Integer> check(int[] results){
        System.out.print('\u000c');
        wuerfel.clear();
        table.clear();
        for(int i = 0; i<5; i++)
        {
            wuerfel.add(results[i]+1);
        }

        //Bubblesort
        for (int i2 = 0; i2<4; i2++){
            for( int i =0; i<4; i++){
                if(wuerfel.get(i)>wuerfel.get(i+1)){
                    int temp = wuerfel.get(i + 1);
                    //System.out.println("Temp" + i + ":" +temp);
                    wuerfel.set(i+1, wuerfel.get(i));
                    wuerfel.set(i, temp);
                }
            }
        }
        //Einer
        if(wuerfel.contains(1)){
            table.add(1);
        }
        //Zweier
        if(wuerfel.contains(2)){
            table.add(2);
        }
        //Dreier
        if(wuerfel.contains(3)){
            table.add(3);
        }
        //Vierer
        if(wuerfel.contains(4)) {
            table.add(4);
        }
        //Fünfer
        if(wuerfel.contains(5)) {
            table.add(5);
        }
        //Sechser
        if(wuerfel.contains(6)) {
            table.add(6);
        }

        //Dreierpasch
        for(int i= 0; i<=6; i++){
            if(wuerfel.lastIndexOf(i)-wuerfel.indexOf(i)+1 >=3 && !table.contains(10)){
                table.add(10);
            }
            else if(table.contains(10)){
                i = 5;
            }
        }

        //Viererpasch
        for(int i= 0; i<=6; i++){
            if(wuerfel.lastIndexOf(i)-wuerfel.indexOf(i)+1 >=4 && !table.contains(10)){
                table.add(11);
            }
            else if(table.contains(10)){
                i = 5;
            }
        }

        //Full House
        /**for(int i= 0; i<=6; i++){
            //System.out.println("i: "+ i);
            for(int i2=0; i2<=6; i2++){
                //System.out.println("i2: "+ i2);

                if(wuerfel.lastIndexOf(i)-wuerfel.indexOf(i)+1 ==3 && wuerfel.lastIndexOf(i2)-wuerfel.indexOf(i2)+1 == 2 && !table.contains(12) && i2!=i){
                    table.add(12);
                }

                else if(table.contains(12)){
                    i = 5;
                    i2 = 5;
                }
            }
        }*/
        ArrayList<Integer> possibleNumbers=new ArrayList<>();
        int numberOfAppearance=1;
        for (int i=0;i<5;i++){
            if(!possibleNumbers.contains(wuerfel.get(i))){
                possibleNumbers.add(wuerfel.get(i));
            }else if(possibleNumbers.get(0)==wuerfel.get(i)){
                numberOfAppearance++;
                System.out.println(numberOfAppearance);
            }
        }
        if(possibleNumbers.size()==2 && (numberOfAppearance==2||numberOfAppearance==3)){
            table.add(12);
        }
        possibleNumbers.clear();
        //Straßen
            if(wuerfel.contains(1)&&wuerfel.contains(2)&&wuerfel.contains(3)&&wuerfel.contains(4)&&wuerfel.contains(5)||
                    wuerfel.contains(6)&&wuerfel.contains(2)&&wuerfel.contains(3)&&wuerfel.contains(4)&&wuerfel.contains(5)){
                    table.add(14);
            }
            if(wuerfel.contains(1)&&wuerfel.contains(2)&&wuerfel.contains(3)
                    ||wuerfel.contains(4)&&wuerfel.contains(2)&&wuerfel.contains(3)
                    ||wuerfel.contains(5)&&wuerfel.contains(4)&&wuerfel.contains(3)
                    ||wuerfel.contains(3)&&wuerfel.contains(4)&&wuerfel.contains(5)){
                    table.add(13);
            }

        //}

        //Kniffel
        if(Objects.equals(wuerfel.get(0), wuerfel.get(4)) && !table.contains(15)){
            table.add(15);
        }

        //Chance
        if(!table.contains(16)){
            table.add(16);
        }

        //Testausgabe

        //System.out.println("Diese Würfel" + wuerfel);
        //System.out.println("Diese Werte" + table);

        return table;
    }

    public int points(int rt){
        int wert = 0;
        //System.out.print('\u000c');
        switch(rt){
            //Einser
            case 1:
                if(table.contains(1)) {
                    wert = (wuerfel.lastIndexOf(1) - wuerfel.indexOf(1) + 1);
                }else{
                    wert =0;}
                break;
            //Zweier
            case 2:
                if(table.contains(2)) {
                wert =(wuerfel.lastIndexOf(2)-wuerfel.indexOf(2)+1)*2;
                }else{
                    wert =0;}
                break;
            //Dreier
            case 3:
                if(table.contains(3)) {
                wert =(wuerfel.lastIndexOf(3)-wuerfel.indexOf(3)+1)*3;
                }else{
                    wert =0;}
                break;
            //Vierer
            case 4:
                if(table.contains(4)) {
                wert =(wuerfel.lastIndexOf(4)-wuerfel.indexOf(4)+1)*4;
                }else{
                    wert =0;}
                break;
            //Fünfer
            case 5:
                if(table.contains(5)) {
                wert =(wuerfel.lastIndexOf(5)-wuerfel.indexOf(5)+1)*5;
                }else{
                    wert =0;}
                break;
            //Sechser
            case 6:
                if(table.contains(6)) {
                wert =(wuerfel.lastIndexOf(6)-wuerfel.indexOf(6)+1)*6;
                }else{
                    wert =0;}
                break;
            //Dreierpasch
            case 10:
                if(table.contains(10)) {
                for(int i=0; i<5; i++){
                    wert =wuerfel.get(i)+ wert;
                }
                }else{
                    wert =0;}
                break;
            //Viererpasch
            case 11:
                if(table.contains(11)) {
                for(int i=0; i<5; i++){
                    wert =wuerfel.get(i)+ wert;
                }
                }else{
                    wert =0;}
                break;
            //Full House
            case 12:
                if(table.contains(12)) {
                wert =25;
                }else{
                    wert =0;}
                break;
            //Kleine Straße
            case 13:
                if(table.contains(13)) {
                wert =30;
                }else{
                    wert =0;}
                break;
            //Große Straße
            case 14:
                if(table.contains(14)) {
                wert =40;
                }else{
                    wert =0;}
                break;
            //Kniffel
            case 15:
                if(table.contains(15)) {
                wert =50;
                }else{
                    wert =0;}
                break;
            //Chance
            case 16:
                if(table.contains(16)) {
                for(int i=0; i<5; i++){
                    wert =wuerfel.get(i)+ wert;
                }
                }else{
                    wert =0;}
                break;
            //Default
            default:
                break;
        }
        return wert;
        //System.out.println(wert);
    }

}
