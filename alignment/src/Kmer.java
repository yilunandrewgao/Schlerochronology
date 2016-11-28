import java.util.List;

public class Kmer {
    public int count; //Times Kmer has been counted
    public int multiplicity; //Calculated after all Kmers are set
    public List<Double> segments; //List of segment lengths in the Kmer
    public int length;

    public Kmer(List<Double> s){
        count = 1;
        multiplicity = 1;
        segments = s;
        length = segments.size();
    }

    public String toString(){
        String res = "Segments: ";

        for (Double d: segments){
            res += d.toString() + " ";
        }

        res += "Count: " + count + " Multiplicity: " + multiplicity;

        return res;
    }

    @Override
    public boolean equals(Object o){
        Kmer k = (Kmer) o;
        return segments.equals(k.segments);
    }
}
