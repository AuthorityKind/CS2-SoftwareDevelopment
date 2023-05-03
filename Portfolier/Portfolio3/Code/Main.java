package code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

//majority of the codebase taken from lecture examples

public class Main {
    static Graph g = new EdgeGraph();

    public static void main(String[] args) {
        readFile();
        g.visitDepthFirst(g.vertex("Hong Kong"), new HashSet<Vertex>());
        //System.out.println();
        //g.visitBreadthFirst(g.vertex("Singapore"));
        System.out.println();
        minimumSpanningTree();
    }

    //taken from lecture example, though modified a bit to take a .txt instead of a .csv
    //method that reads a file
    private static void readFile() {
        //part 1
        try (BufferedReader in = new BufferedReader(new FileReader("network.txt"))) {
            while (true) {
                String s = in.readLine();
                if (s == null)
                    break;
                String a[] = s.split(",");
                g.insertEdge(a[0], a[1], Integer.parseInt(a[2]));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void minimumSpanningTree(){
        //part 3
        int totalLength = 0;
        for (Edge e : g.minimumSpanningTree()){
            System.out.println(e);
            totalLength += e.weight;
        }  
        System.out.println("Total Length of the mst is: " + totalLength);
    }   
}

//taken from lecture example
abstract class Graph {
    abstract void insertEdge(String v, String u, int w);

    abstract void printGraph();

    static HashMap<String, Vertex> vertex = new HashMap<>();

    Vertex vertex(String s) {
        if (!vertex.containsKey(s))
            vertex.put(s, new Vertex(s));
        return vertex.get(s);
    }

    Collection<Vertex> vertices() {
        return vertex.values();
    }

    abstract Collection<Edge> edges();

    abstract Collection<Edge> outEdge(Vertex v);

    HashSet<Vertex> getLocations(){
        HashSet<Vertex> locations = new HashSet<>();
        for (Edge e: edges()){
            if(!locations.contains(e.to))
                locations.add(e.to);
            if(!locations.contains(e.from))
                locations.add(e.from);
        }
        return locations;
    }

    boolean checkRemainingLocations(HashSet<Vertex> visitedVertices){
        //part 2
        return getLocations().equals(visitedVertices);
    }

    //taken from lecture example
    void visitDepthFirst(Vertex v, HashSet<Vertex> visited) {
        if (visited.contains(v))
            return;
        System.out.println("Visited " + v);
        visited.add(v);
        for (Edge e : outEdge(v)) visitDepthFirst(e.to, visited);
        if(checkRemainingLocations(visited)) System.out.println("All locations visited!");
    }

    //taken from lecture example
    void visitBreadthFirst(Vertex v) {
        HashSet<Vertex> thisLevel = new HashSet<>();
        HashSet<Vertex> nextLevel = new HashSet<>();
        HashSet<Vertex> visited = new HashSet<>();
        thisLevel.add(v);
        while (thisLevel.size() > 0) {
            System.out.println("level " + thisLevel);
            for (Vertex w : thisLevel) {
                System.out.println("visited " + w);
                visited.add(w);
                for (Edge e : outEdge(w)) {
                    if (visited.contains(e.to))
                        continue;
                    if (thisLevel.contains(e.to))
                        continue;
                    nextLevel.add(e.to);
                }
            }
            thisLevel = nextLevel;
            nextLevel = new HashSet<Vertex>();
        }
    }

    //method that returns a Prim's minimum spanning tree of Graph class it adheres to
    Set<Edge> minimumSpanningTree() {
        Collection<Edge> edges = edges();
        ArrayList<Edge> checkedEdges = new ArrayList<>();
        HashSet<Edge> mst = new HashSet<>();
        HashSet<Vertex> frontier = new HashSet<>();
        for (Edge e : edges) {
            frontier.add(e.from);
            break;
        }
        while (true) {
            Edge nearest = null;
            for (Edge e : edges) {
                if (checkedEdges.size() > 0)
                    for (Edge ce : checkedEdges)
                        if (ce == e)
                            break;
                if (!frontier.contains(e.from))
                    continue;
                if (frontier.contains(e.to))
                    continue;
                if (nearest == null || nearest.weight > e.weight) {
                    nearest = e;
                    checkedEdges.add(e);
                }
            }

            if (nearest == null)
                break;
            mst.add(nearest);
            frontier.add(nearest.to);
        }
        return mst;
    }
}

//taken from lecture example
//class to create a vertex object, only has a name
class Vertex {
    String name;
    Vertex(String s) {name = s;}
    public String toString() {return name;}
}

//taken from lecture example
//class to create object, has two verticies and a weight
class Edge {
    Vertex from, to;
    int weight;

    Edge(Vertex from, Vertex to, int w) {
        this.from = from;
        this.to = to;
        weight = w;
    }

    public String toString() {
        return from.name + " - " + weight + " -> " + to.name;
    }
}

//taken from lecture example
//class that extends off of the Graph class, to be able to graph out the edges in it
//no reason for the abstraction, as the parent class is only used once, but it was like this in the
//examples from the lectures I used them from, and if it works, it works
class EdgeGraph extends Graph {
    HashSet<Edge> edges = new HashSet<>();

    void insertEdge(String u, String v, int w) {
        edges.add(new Edge(vertex(u), vertex(v), w));
    }

    void printGraph() {
        for (Edge e : edges)
            System.out.println(e);
    }

    Collection<Edge> edges() {
        return edges;
    }

    Collection<Edge> outEdge(Vertex v) {
        ArrayList<Edge> outEdge = new ArrayList<>();
        for (Edge e : edges)
            if (e.from == v)
                outEdge.add(e);
        return outEdge;
    }
}