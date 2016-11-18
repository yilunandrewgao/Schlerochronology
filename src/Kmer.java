import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davis on 11/13/2016.
 */
public class Kmer {
    public int count; //Times Kmer has been counted
    public int multiplicity; //Calculated after all Kmers are set
    public List<Double> segments; //List of segment lengths in the Kmer

    public Kmer(List<Double> s){
        count = 1;
        multiplicity = 1;
        segments = s;
    }

    public String toString(){
        String res = "Segments: ";

        for (Double d: segments){
            res += d.toString() + " ";
        }

        res += "Count: " + count + " Multiplicity: " + multiplicity;

        return res;
    }
}
