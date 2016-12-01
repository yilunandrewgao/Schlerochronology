import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

	    ArrayList<Double> alignment = new ArrayList<>();

        Random ran = new Random();

        int numSamples = ran.nextInt(25) + 50;


        for (int i=0 ; i<100 ; i++){
            alignment.add((Math.random() * 20));
        }

        System.out.println(alignment);

        for (int j=0 ; j<numSamples ; j++){
            System.out.print("sample"+j);
            int start = ran.nextInt(85);
            int end = start+ran.nextInt(8)+7;
            List<Double> reads = alignment.subList(start, end);

            for(int x=0 ; x<reads.size() ; x++){
                System.out.print(","+(reads.get(x)*factor(x)));
            }
            System.out.println();
        }

    }

    public static double factor(int x){
        int a = 5;
        int b = 3;
        int c = 8;
        x = x*(-1);

        double res = a*(Math.pow(Math.E, (x*b)))+c;

        return res;
    }
}
