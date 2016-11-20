import java.util.ArrayList;

/**
 * Created by Davis on 11/17/2016.
 */
public class Read {
    private String ID;
    private ArrayList<Double> segments;

    public Read(String ID){
        this.ID = ID;
        segments = new ArrayList<>();
    }

    public void addSegment(Double d){
        segments.add(d);
    }

    public ArrayList<Double> getSegments(){
        return segments;
    }

    public String getID(){
        return ID;
    }

    public String toString(){
        String res = "ID: " + ID + " Segments: ";
        for(Double d: segments){
            res += " " + Double.toString(d);
        }
        return res;
    }
}

