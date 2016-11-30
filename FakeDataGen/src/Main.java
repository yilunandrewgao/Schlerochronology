import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
	    ArrayList<Double> alignment = new ArrayList<>();

        Random ran = new Random();

        int numSamples = ran.nextInt(25) + 35;


        for (int i=0 ; i<100 ; i++){
            alignment.add((Math.random() * 10));
        }

        System.out.println(alignment);

        for (int j=0 ; j<numSamples ; j++){
            System.out.print("sample"+j);
            int start = ran.nextInt(85);
            int end = start+ran.nextInt(8)+7;
            List<Double> reads = alignment.subList(start, end);

            for(Double d: reads){
                System.out.print(","+d);
            }
            System.out.println();
        }

    }
}
