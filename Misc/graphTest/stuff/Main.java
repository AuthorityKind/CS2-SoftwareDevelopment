package stuff;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.*;

public class Main {
    static Graph g = new EdgeGraph();

    public static void main(String[] args) {
        readFile();
        // g.printGraph();
        // for (Edge e : g.edges()) System.out.println(e);
        // g.visitDepthFirst(g.vertex("Hong Kong"), new HashSet<Vertex>());
        // g.visitBreadthFirst(g.vertex("Singapore"));
        // System.out.println(g.minimumSpanningTree());
        for (Edge e : g.minimumSpanningTree())
            System.out.println(e);
    }

    public static void readFile() {
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
}

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

    void visitDepthFirst(Vertex v, Set<Vertex> visited) {
        if (visited.contains(v))
            return;
        System.out.println("visited " + v);
        visited.add(v);
        for (Edge e : outEdge(v))
            visitDepthFirst(e.to, visited);
    }

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

class Vertex {
    String name;

    Vertex(String s) {
        name = s;
    }

    public String toString() {
        return name;
    }
}

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

/*
 * class AdjListGraph extends Graph {
 * HashMap<Vertex, Set<Edge>> outEdge = new HashMap<>();
 * 
 * void insertEdge(String u, String v, int w) {
 * Edge e = new Edge(vertex(u), vertex(v), w);
 * if (!outEdge.containsKey(e.from))
 * outEdge.put(e.from, new HashSet<Edge>());
 * outEdge.get(e.from).add(e);
 * }
 * 
 * Collection<Edge> edges() {
 * Set<Edge> edges = new HashSet<>();
 * for (Vertex v : outEdge.keySet())
 * edges.addAll(outEdge.get(v));
 * return edges;
 * }
 * 
 * void printGraph() {
 * for (Vertex v : outEdge.keySet()) {
 * System.out.println("vertex " + v);
 * for (Edge e : outEdge.get(v))
 * System.out.println(" " + e);
 * }
 * }
 * 
 * Collection<Edge> outEdge(Vertex v) {
 * return outEdge.get(v);
 * }
 * }
 * 
 * 
 * class AdjMapGraph extends Graph {
 * HashMap<Vertex, Map<Vertex, Edge>> outEdge = new HashMap<>();
 * 
 * void insertEdge(String u, String v, int w) {
 * Edge e = new Edge(vertex(u), vertex(v), w);
 * if (!outEdge.containsKey(e.from))
 * outEdge.put(e.from, new HashMap<Vertex, Edge>());
 * outEdge.get(e.from).put(e.to, e);
 * }
 * 
 * Collection<Edge> edges() {
 * Set<Edge> edges = new HashSet<>();
 * for (Vertex v : outEdge.keySet())
 * for (Vertex w : outEdge.get(v).keySet())
 * edges.add(outEdge.get(v).get(w));
 * return edges;
 * }
 * 
 * void printGraph() {
 * for (Vertex v : outEdge.keySet()) {
 * System.out.println("vertex " + v);
 * for (Vertex w : outEdge.get(v).keySet())
 * System.out.println(" " + outEdge.get(v).get(w));
 * }
 * }
 * 
 * Collection<Edge> outEdge(Vertex v) {
 * return outEdge.get(v).values();
 * }
 * }
 * 
 * class MatrixGraph extends Graph {
 * ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
 * ArrayList<Vertex> vertex = new ArrayList<>();
 * HashMap<Vertex, Integer> vertexIndex = new HashMap<>();
 * 
 * void insertEdge(String u, String v, int w) {
 * Vertex u1 = vertex(u);
 * Vertex v1 = vertex(v);
 * if (!vertexIndex.containsKey(u1)) {
 * vertexIndex.put(u1, vertexIndex.size());
 * vertex.add(u1);
 * }
 * if (!vertexIndex.containsKey(v1)) {
 * vertexIndex.put(v1, vertexIndex.size());
 * vertex.add(v1);
 * }
 * setMatrix(vertexIndex.get(u1), vertexIndex.get(v1), w);
 * }
 * 
 * Collection<Edge> edges() {
 * HashSet<Edge> edges = new HashSet<>();
 * for (int i = 0; i < matrix.size(); i++) {
 * ArrayList<Integer> row = matrix.get(i);
 * for (int j = 0; j < row.size(); j++) {
 * if (row.get(j) == null)
 * continue;
 * edges.add(new Edge(vertex.get(i), vertex.get(j), row.get(j)));
 * }
 * }
 * return edges;
 * }
 * 
 * Collection<Edge> outEdge(Vertex v) {
 * ArrayList<Integer> row = matrix.get(vertexIndex.get(v));
 * HashSet<Edge> edges = new HashSet<>();
 * for (int j = 0; j < row.size(); j++) {
 * if (row.get(j) == null)
 * continue;
 * edges.add(new Edge(v, vertex.get(j), row.get(j)));
 * }
 * return edges;
 * }
 * 
 * void printGraph() {
 * System.out.print(" ");
 * for (int i = 0; i < matrix.size(); i++)
 * System.out.print(" " + vertex.get(i));
 * System.out.println();
 * for (int i = 0; i < matrix.size(); i++) {
 * System.out.print(vertex.get(i));
 * ArrayList<Integer> row = matrix.get(i);
 * for (int j = 0; j < row.size(); j++) {
 * if (row.get(j) == null)
 * System.out.print("  ");
 * else
 * System.out.print(" " + row.get(j));
 * }
 * System.out.println();
 * }
 * 
 * }
 * 
 * void setMatrix(int i, int j, int w) {
 * while (matrix.size() <= i)
 * matrix.add(new ArrayList<Integer>());
 * ArrayList<Integer> row = matrix.get(i);
 * while (row.size() <= j)
 * row.add(null);
 * row.set(j, w);
 * }
 * }
 */