import java.util.ArrayList;

/**
 * Created by Davis on 11/13/2016.
 */
public class Node {
    Kmer segment;
    ArrayList<Node> connectedNodes;

    public Node(Kmer k){
        segment = k;
    }
}
