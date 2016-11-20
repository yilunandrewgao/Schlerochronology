import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

public class Main {

    private static Scanner in;
    private static ArrayList<Read> reads;
    private static ArrayList<Kmer> kmers;
    private static String header;
    private static int shortestRead;

    public static void main(String[] args) {
        in = new Scanner(System.in);
        reads = new ArrayList<>();
        kmers = new ArrayList<>();
        header = in.nextLine();
        shortestRead = (int) Double.POSITIVE_INFINITY;

        getReads();
        buildKmers(shortestRead);

        //DEBUG
        for(Kmer k: kmers){
            System.out.println(k.toString());
        }

    }

    private static void getReads(){
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
    }

    private static void buildKmers(int k){
        /*
         * This for loop goes over each read and builds all kmers from it
         * it then checks if the kmer has already been found, and increases its count if it has been
         * otherwise the kmer is added to the list of kmers generated thus far
         */
        for(Read r: reads){
            ArrayList<Double> segments = r.getSegments();
            int i=0;
            while((i+k) <= segments.size()){
                Kmer currentKmer = new Kmer(segments.subList(i, (i+k)));
                int index = kmers.indexOf(currentKmer);
                if(index != -1){
                    kmers.get(index).count++;
                    //TODO: calculate multiplicity
                }
                else{
                    kmers.add(currentKmer);
                }
                i++;
            }
        }
    }
}