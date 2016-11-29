import java.util.ArrayList;
import java.util.Scanner;

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
        //buildKmers(shortestRead-2); //FIXME can't use k=shortest read because then shortest read has to be completely contained
        buildKmers(5); //4 is the shortest size that this algorithm works with but 5 is better

        DBGraph dbg = new DBGraph(kmers);

        /*
         * This conditional + loop makes sure a euler path can be made,
         * then finds the indices of each sample in the alignment
         */
        if (dbg.makeCircular()){
            //Now we can find a euler circuit
            System.out.println("Graph has been made circular");
            System.out.println(dbg.getAlignment());

            for(Read r: reads){
                System.out.println(r.getID() + " Offset: " + dbg.getOffset(r));
            }

        }
        else {
            //TODO: this method only works under perfect conditions (a single alignment exists in the data)
            System.out.println("No complete alignment could be made with this dataset");
        }

    }

    private static void getReads(){
        /*
         * This while loop reads through the input and turns each line into a Read object
         * It also tracks the shortest read, which is later used as the length of K for the Kmers
         * Note: most of the length-1 are because the first index is the sample name not a segment
         */
        int sampleNumber = 0;
        while(in.hasNext()){
            String line = in.nextLine();
            String[] parts = line.split(",");

            //Samples have to have at least 5 segments for the algorithm to work
            if (parts.length-1 < 5) {
                //System.out.println("Sample " + sampleNumber + " was too short, skipping");
                return;
            }
            if((parts.length-1) < shortestRead) shortestRead = (parts.length-1); //used to determine what K is

            Read currentRead = new Read(parts[0]);
            for(int i=1 ; i<parts.length ; i++){
                currentRead.addSegment(Double.parseDouble(parts[i]));
            }

            reads.add(currentRead);
            sampleNumber++;
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
                    //FIXME: not sure the multi-edge is actually useful for our case; repeats should be exceedingly rare
                }
                else{
                    kmers.add(currentKmer);
                }
                i++;
            }
        }
    }

}
