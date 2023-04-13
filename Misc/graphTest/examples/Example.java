package examples;
import java.util.*;

public class Example {
  public static void main(String[] args) {
    Graph g = new EdgeGraph();
    // Graph g=new AdjListGraph();
    // Graph g=new AdjMapGraph();
    // Graph g=new MatrixGraph();
    g.insertEdge("a", "b", 1);
    g.insertEdge("b", "c", 3);
    g.insertEdge("c", "e", 4);
    g.insertEdge("e", "f", 3);
    g.insertEdge("g", "e", 1);
    g.insertEdge("d", "a", 2);
    g.insertEdge("b", "e", 1);
    g.insertEdge("b", "d", 2);
    g.insertEdge("d", "e", 2);
    g.insertEdge("c", "d", 1);
    /* 
    g.insertEdge("u", "v", 2);
    g.insertEdge("v", "u", 2);
    g.insertEdge("u", "w", 4);
    g.insertEdge("w", "u", 4);
    g.insertEdge("v", "w", 6);
    g.insertEdge("w", "v", 6);
    g.insertEdge("w", "z", 8);
    g.insertEdge("z", "w", 8);
    */
    g.printGraph();
    System.out.println();
    for (Edge e : g.edges())
      System.out.println(e);
    g.visitDepthFirst(g.vertex("a"), new HashSet<Vertex>());
    g.visitBreadthFirst(g.vertex("u"));
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

class AdjListGraph extends Graph {
  HashMap<Vertex, Set<Edge>> outEdge = new HashMap<>();

  void insertEdge(String u, String v, int w) {
    Edge e = new Edge(vertex(u), vertex(v), w);
    if (!outEdge.containsKey(e.from))
      outEdge.put(e.from, new HashSet<Edge>());
    outEdge.get(e.from).add(e);
  }

  Collection<Edge> edges() {
    Set<Edge> edges = new HashSet<>();
    for (Vertex v : outEdge.keySet())
      edges.addAll(outEdge.get(v));
    return edges;
  }

  void printGraph() {
    for (Vertex v : outEdge.keySet()) {
      System.out.println("vertex " + v);
      for (Edge e : outEdge.get(v))
        System.out.println(" " + e);
    }
  }

  Collection<Edge> outEdge(Vertex v) {
    return outEdge.get(v);
  }
}

class AdjMapGraph extends Graph {
  HashMap<Vertex, Map<Vertex, Edge>> outEdge = new HashMap<>();

  void insertEdge(String u, String v, int w) {
    Edge e = new Edge(vertex(u), vertex(v), w);
    if (!outEdge.containsKey(e.from))
      outEdge.put(e.from, new HashMap<Vertex, Edge>());
    outEdge.get(e.from).put(e.to, e);
  }

  Collection<Edge> edges() {
    Set<Edge> edges = new HashSet<>();
    for (Vertex v : outEdge.keySet())
      for (Vertex w : outEdge.get(v).keySet())
        edges.add(outEdge.get(v).get(w));
    return edges;
  }

  void printGraph() {
    for (Vertex v : outEdge.keySet()) {
      System.out.println("vertex " + v);
      for (Vertex w : outEdge.get(v).keySet())
        System.out.println(" " + outEdge.get(v).get(w));
    }
  }

  Collection<Edge> outEdge(Vertex v) {
    return outEdge.get(v).values();
  }
}

class MatrixGraph extends Graph {
  ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();
  ArrayList<Vertex> vertex = new ArrayList<>();
  HashMap<Vertex, Integer> vertexIndex = new HashMap<>();

  void insertEdge(String u, String v, int w) {
    Vertex u1 = vertex(u);
    Vertex v1 = vertex(v);
    if (!vertexIndex.containsKey(u1)) {
      vertexIndex.put(u1, vertexIndex.size());
      vertex.add(u1);
    }
    if (!vertexIndex.containsKey(v1)) {
      vertexIndex.put(v1, vertexIndex.size());
      vertex.add(v1);
    }
    setMatrix(vertexIndex.get(u1), vertexIndex.get(v1), w);
  }

  Collection<Edge> edges() {
    HashSet<Edge> edges = new HashSet<>();
    for (int i = 0; i < matrix.size(); i++) {
      ArrayList<Integer> row = matrix.get(i);
      for (int j = 0; j < row.size(); j++) {
        if (row.get(j) == null)
          continue;
        edges.add(new Edge(vertex.get(i), vertex.get(j), row.get(j)));
      }
    }
    return edges;
  }

  Collection<Edge> outEdge(Vertex v) {
    ArrayList<Integer> row = matrix.get(vertexIndex.get(v));
    HashSet<Edge> edges = new HashSet<>();
    for (int j = 0; j < row.size(); j++) {
      if (row.get(j) == null)
        continue;
      edges.add(new Edge(v, vertex.get(j), row.get(j)));
    }
    return edges;
  }

  void printGraph() {
    System.out.print(" ");
    for (int i = 0; i < matrix.size(); i++)
      System.out.print(" " + vertex.get(i));
    System.out.println();
    for (int i = 0; i < matrix.size(); i++) {
      System.out.print(vertex.get(i));
      ArrayList<Integer> row = matrix.get(i);
      for (int j = 0; j < row.size(); j++) {
        if (row.get(j) == null)
          System.out.print("  ");
        else
          System.out.print(" " + row.get(j));
      }
      System.out.println();
    }

  }

  void setMatrix(int i, int j, int w) {
    while (matrix.size() <= i)
      matrix.add(new ArrayList<Integer>());
    ArrayList<Integer> row = matrix.get(i);
    while (row.size() <= j)
      row.add(null);
    row.set(j, w);
  }
}