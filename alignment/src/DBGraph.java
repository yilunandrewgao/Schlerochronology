import java.util.*;

import org.jgrapht.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;

public class DBGraph {
    private DirectedMultigraph<List<Double>, DefaultEdge> graph;
    private List<Double> alignment; //When an alignment is found store it instead of make it again
    private List<Double> startVertex;
    private List<Double> endVertex;
    private int k; //This is the length of kmers, used later for alignment

    public DBGraph(ArrayList<Kmer> kmers){
        graph = new DirectedMultigraph<>(DefaultEdge.class);
        ArrayList<List<Double>> verticesToAdd = new ArrayList<>();
        alignment = null;
        k = kmers.get(0).length;
        for(Kmer k: kmers){
            List<Double> prefix = k.segments.subList(0, k.segments.size()-1);
            List<Double> suffix = k.segments.subList(1, k.segments.size()); //FIXME?
            verticesToAdd.add(prefix);
            verticesToAdd.add(suffix); //FIXME?
            graph.addVertex(prefix);
            graph.addVertex(suffix); //FIXME?

        }

        //At this point we have a list of all the vertices, now want to add edges
        for(Kmer k: kmers){
            //Edges exist between prefixes and suffixes of a kmer
            List<Double> prefix = k.segments.subList(0, k.segments.size()-1);
            List<Double> suffix = k.segments.subList(1, k.segments.size());

            if(graph.containsVertex(prefix) && graph.containsVertex(suffix)){
                graph.addEdge(prefix, suffix);
            }

        }

    }

    /*
     * This function searches for a start vertex and an end vertex, then links them so that a eulerian circuit exists
     * if more than 1 start/end vertex is found returns false
     * if exactly 1 of each is found adds an edge between them and then returns true
     * 0 if ok
     * 1 if too many starts
     * 2 if too many ends
     * 3 if node is too unbalanced
     * 4 if no start
     * 5 if no end
     * 6 if other
     */
    public int makeCircular(){
        int startVerticesCount = 0;
        int endVerticesCount = 0;

        Set<List<Double>> vertices = graph.vertexSet();

        for(List<Double> v: vertices){
            int inDeg = graph.inDegreeOf(v);
            int outDeg = graph.outDegreeOf(v);
            if (inDeg != outDeg){
                //vertex with more out than in has to be a start point
                if (outDeg-inDeg == 1){
                    startVerticesCount++;
                    startVertex = v;
                    //Found too many possible starts
                    if (startVerticesCount > 1) return 1;
                }
                //vertex with more in than out has to be an end point
                else if(inDeg-outDeg ==1){
                    endVerticesCount++;
                    endVertex = v;
                    //Found too many possible ends
                    if (endVerticesCount > 1) return 2;
                }
                //If a vertex has 2 more in or out then there cannot be a euler path
                else {
                    return 3;
                }
            }
        }

        /*
         * If we didn't find any start/end then there was already a cycle
         * If we didn't find the same number of start/end then there cannot be a path
         */
        if ((startVerticesCount == 1 && endVerticesCount == 1)){
            //Add an edge going back from the end to the start so that there's a loop
            graph.addEdge(endVertex, startVertex);
            return 0;
        }
        else if (startVerticesCount == 0){
            return 4;
        }
        else if (endVerticesCount == 0){
            return 5;
        }
        else{
            return 6;
        }

    }

    /*
     * Used internally for the alignment generation,
     * once a path is found it needs to be parsed to form a meaningful alignment
     */
    private List<List<Double>> getEulerPath(){
        List<List<Double>> circuit = new ArrayList<>();
        Stack<List<Double>> stack = new Stack<>();

        //List<List<Double>> succesors = getSuccesors(graph.outgoingEdgesOf(startVertex));
        List<List<Double>> result = eulerRecurse(circuit, stack, startVertex);
        Collections.reverse(result);
        return result;
    }

    /*
     * Get list of successors for current node
     * If list empty:
     *      If stack empty terminate
     *      Else add current to circuit
     *      Pop node from stack, make current
     * Else
     *      choose successor from list
     *      Remove edge current -> successor
     *      Push current to stack
     *      make successor current
     */
    private List<List<Double>> eulerRecurse(List<List<Double>> circuit, Stack<List<Double>> stack, List<Double> current){
        List<List<Double>> succesors = getSuccesors(graph.outgoingEdgesOf(current));

        if (succesors.size() == 0){
            if (stack.isEmpty()){
                return circuit;
            }
            else {
                circuit.add(current);
                current = stack.pop();
                return eulerRecurse(circuit, stack, current);
            }
        }
        else{
            List<Double> next = succesors.get(0);
            graph.removeEdge(current, next);
            stack.push(current);
            current = next;
            return eulerRecurse(circuit, stack, current);
        }
    }

    /*
     * Converts from a set of edges to a list of vertices the edges point to
     */
    private List<List<Double>> getSuccesors(Set<DefaultEdge> edges){
        List<List<Double>> result = new ArrayList<>();

        for(DefaultEdge e: edges){
            result.add(graph.getEdgeTarget(e));
        }

        return result;
    }

    /*
     * Returns an alignment from the dataset
     * Note: this might not be the only possible alignment,
     * this is a problem with the algorithm not the implementation
     */
    public List<Double> getAlignment(){

        if (alignment != null) return alignment; //we already found an alignment

        List<List<Double>> verticesList = getEulerPath();
        List<Double> result = new ArrayList<>();

        for(Double d: verticesList.get(verticesList.size()-1)){
            result.add(d);
        }
        for (int i=0 ; i<verticesList.size()-1 ; i++){
            result.add(verticesList.get(i).get(k-2));
        }

        alignment = result;
        return result;
    }

    /*
     * Returns the starting index of a given read
     * Note: the way Collections does this is a brute force approach so for a very large alignment this might be slow
     */
    public int getOffset(Read r){
        ArrayList<Double> segments = r.getSegments();
        return Collections.indexOfSubList(this.getAlignment(), segments);
    }

    public String toString(){
        return graph.toString();
    }

}
