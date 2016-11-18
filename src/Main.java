import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<Read> reads = new ArrayList<>();
        ArrayList<Kmer> kmers = new ArrayList<>();
        //HashMap<ArrayList<Double>, ArrayList<Double>> kmers = new HashMap<>();
        String header = in.nextLine();
        int shortestRead = (int) Double.POSITIVE_INFINITY;

        /*
         * This while loop reads through the input and turns each line into a Read object
         * It also tracks the shortest read, which is later used as the length of K for the Kmers
         */
        while(in.hasNext()){
            String line = in.nextLine();
            String[] parts = line.split(",");

            if((parts.length-1) < shortestRead) shortestRead = (parts.length-1); //used to determine what K is

            Read currentRead = new Read(parts[0]);
            for(int i=1 ; i<parts.length ; i++){
                currentRead.addSegment(Double.parseDouble(parts[i]));
            }

            reads.add(currentRead);
        }

        /*//DEBUG
        for(Read r: reads){
            System.out.println(r.toString());
        }*/

        System.out.println(shortestRead);

        /*
         * This for loop goes over each read and builds all kmers from it
         * it then checks if the kmer has already been found, and increases its count if it has been
         * otherwise the kmer is added to the list of kmers generated thus far
         */
        for(Read r: reads){
            ArrayList<Double> segments = r.getSegments();
            //System.out.println(segments.toString());
            int i=0;
            while((i+shortestRead) <= segments.size()){
                Kmer currentKmer = new Kmer(segments.subList(i, (i+shortestRead)));
                //System.out.println(segments.subList(i, i+shortestRead).toString());
                int index = kmers.indexOf(currentKmer);
                if(index != -1){
                    //System.out.println("Kmer was previously generated");
                    kmers.get(index).count++;
                    //TODO: calculate multiplicity
                }
                else{
                    //System.out.println("Kmer was not previously generated");
                    kmers.add(currentKmer);
                }
                i++;
            }
        }

        //DEBUG
        for(Kmer k: kmers){
            System.out.println(k.toString());
        }

    }
}
