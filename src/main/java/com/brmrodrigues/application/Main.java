package main.java.com.brmrodrigues.application;

import main.java.com.brmrodrigues.graph.core.Digraph;
import main.java.com.brmrodrigues.graph.core.Graph;
import main.java.com.brmrodrigues.graph.core.Vertex;
import main.java.com.brmrodrigues.graph.search.BreadthFirstSearch;
import main.java.com.brmrodrigues.graph.search.DepthFirstSearch;
import main.java.com.brmrodrigues.graph.util.Dijkstra;
import main.java.com.brmrodrigues.graph.util.FloydMarshall;
import main.java.com.brmrodrigues.graph.util.Prim;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws Exception{
        Graph graph = new Graph();

        graph.addVertex("RJ");
        graph.addVertex("SP");
        graph.addVertex("BH");
        graph.addVertex("PT");
        graph.addVertex("OS");
        graph.addVertex("SV");
        graph.addVertex("CR");
        graph.addVertex("PA");

        for (Vertex v : graph.getVertexes()) {
            System.out.println("- Vertex " + v.getLabel());
        }

        // Adjacency Matrix
        Graph graphLetters = new Graph(true);

        graphLetters.addVertex("A");
        graphLetters.addVertex("B");
        graphLetters.addVertex("C");
        graphLetters.addVertex("D");

        graphLetters.linkVertexes("A", "B", null);
        graphLetters.linkVertexes("A", "C", null);
        graphLetters.linkVertexes("A", "D", null);

        System.out.println("Vertex A degree: " + graphLetters.getVertex("A").getDegree());
        System.out.println("Vertex C degree: " + graphLetters.getVertex("C").getDegree());
        System.out.println("Vertex D degree: " + graphLetters.getVertex("D").getDegree());
        printVertexAdjacencies(graphLetters, "A");
        printVertexAdjacencies(graphLetters, "B");
        printVertexAdjacencies(graphLetters, "C");

        // SEARCH
        Graph searchGraph = new Graph(true);

        searchGraph.addVertex("A");
        searchGraph.addVertex("B");
        searchGraph.addVertex("C");
        searchGraph.addVertex("D");
        searchGraph.addVertex("E");
        searchGraph.addVertex("F");
        searchGraph.addVertex("G");
        searchGraph.addVertex("H");
        searchGraph.addVertex("I");

        searchGraph.linkVertexes("A", "B", null);
        searchGraph.linkVertexes("A", "C", null);
        searchGraph.linkVertexes("A", "D", null);
        searchGraph.linkVertexes("B", "F", null);
        searchGraph.linkVertexes("B", "I", null);
        searchGraph.linkVertexes("D", "E", null);
        searchGraph.linkVertexes("D", "I", null);
        searchGraph.linkVertexes("D", "G", null);
        searchGraph.linkVertexes("I", "A", null);
        searchGraph.linkVertexes("I", "D", null);
        searchGraph.linkVertexes("I", "C", null);
        searchGraph.linkVertexes("I", "H", null);
        searchGraph.linkVertexes("E", "A", null);

        List<String> dfsPath = DepthFirstSearch.getInstance().search(searchGraph, "B", "G");
        System.out.println("Path by DFS:");
        for(String step : dfsPath) {
            System.out.print(step + " ");
        }
        System.out.println();

        List<String> bfsPath = BreadthFirstSearch.getInstance().search(searchGraph, "B", "G");
        System.out.println("Path by BFS:");
        for(String step : bfsPath) {
            System.out.print(step + " ");
        }

        // Tree Generated by DFS
        Graph tree = searchGraph.depthGeneratedTree();
        System.out.println();
        System.out.println("Vertexes:");
        for(Vertex v : tree.getVertexes()) {
            System.out.println("\t" + v.getLabel());
        }
        System.out.println("Edges:");
        for(Vertex v : tree.getVertexes()) {
            for(Vertex adj : tree.getAdjacencies(v.getLabel())) {
                System.out.println("\t" + v.getLabel() + "--" + adj.getLabel());
            }
        }

        // Weighted Graph
        Graph weightedGraph = new Graph(true);

        weightedGraph.addVertex("A");
        weightedGraph.addVertex("B");
        weightedGraph.addVertex("C");
        weightedGraph.addVertex("D");
        weightedGraph.addVertex("E");

        weightedGraph.linkVertexes("A", "B", 12);
        weightedGraph.linkVertexes("C", "E", 10);
        weightedGraph.linkVertexes("B", "D", 5);
        weightedGraph.linkVertexes("D", "A", 2);
        weightedGraph.linkVertexes("B", "E", 1);
        weightedGraph.linkVertexes("A", "C", 7);

        System.out.println("AC weight: " + weightedGraph.getWeight("A", "C"));
        System.out.println("BE weight: " + weightedGraph.getWeight("B", "E"));

        // Digraph
        Digraph digraph = new Digraph();
        digraph.addVertex("RJ");
        digraph.addVertex("SP");
        digraph.addVertex("BH");
        digraph.addVertex("PT");
        digraph.addVertex("OS");
        digraph.addVertex("SV");
        digraph.addVertex("CR");
        digraph.addVertex("PA");

        digraph.linkVertexes("RJ", "SP", null);
        digraph.linkVertexes("RJ", "BH", null);
        digraph.linkVertexes("RJ", "PT", null);
        digraph.linkVertexes("RJ", "PA", null);
        digraph.linkVertexes("SP", "BH", null);
        digraph.linkVertexes("SP", "OS", null);
        digraph.linkVertexes("SP", "SV", null);
        digraph.linkVertexes("SP", "CR", null);
        digraph.linkVertexes("SP", "PA", null);
        digraph.linkVertexes("SV", "PA", null);
        digraph.linkVertexes("CR", "PA", null);

        Graph digraphTree = digraph.depthGeneratedTree("PT");
        System.out.println("\nDigraph Tree");
        printGraph(digraphTree);

        Digraph weightedDigraph = new Digraph();
        weightedDigraph.addVertex("X");
        weightedDigraph.addVertex("Y");
        weightedDigraph.addVertex("Z");
        weightedDigraph.addVertex("W");
        weightedDigraph.addVertex("V");

        weightedDigraph.linkVertexes("X", "V", 44);
        weightedDigraph.linkVertexes("Y", "W", 37);
        weightedDigraph.linkVertexes("W", "Z", 38);
        weightedDigraph.linkVertexes("X", "V", 16);
        weightedDigraph.linkVertexes("V", "X", 22);
        weightedDigraph.linkVertexes("V", "Y", 57);

        System.out.println("\nWeighted Digraph");
        printGraph(weightedDigraph);

        // Dijkstra
        Map<String, Dijkstra.Info> shortestPaths = Dijkstra.getInstance().process("X", "Y", weightedDigraph);
        Set<String> keys = shortestPaths.keySet();
        for(String key : keys) {
            Dijkstra.Info info = shortestPaths.get(key);
            String ancestor = info.ancestor == null ? "" : info.ancestor.getLabel();
            System.out.println(key + " : " + info.distance + " - " + ancestor);
        }

        System.out.println();

        // FloydMarshall
        Map<String, Map<String, FloydMarshall.Info>> matrix = FloydMarshall.getInstance().process(weightedDigraph);
        for(String v : matrix.keySet()) {
            System.out.println("Vertex " + v);
            Map<String, FloydMarshall.Info> row = matrix.get(v);
            for (String u : row.keySet()) {
                FloydMarshall.Info info = row.get(u);
                System.out.println(u + " with distance " + info.distance + " through " + info.throughVertex.getLabel());
            }
            System.out.println();
        }

        // Prim
        System.out.println("PRIM");
        Graph primGraph = new Graph();
        primGraph.addVertex("A");
        primGraph.addVertex("B");
        primGraph.addVertex("C");
        primGraph.addVertex("D");
        primGraph.addVertex("E");
        primGraph.addVertex("F");

        primGraph.linkVertexes("A", "B", 6);
        primGraph.linkVertexes("A", "C", 1);
        primGraph.linkVertexes("A", "D", 5);
        primGraph.linkVertexes("B", "C", 2);
        primGraph.linkVertexes("B", "E", 5);
        primGraph.linkVertexes("C", "E", 6);
        primGraph.linkVertexes("C", "F", 4);
        primGraph.linkVertexes("C", "D", 2);
        primGraph.linkVertexes("D", "F", 4);
        primGraph.linkVertexes("E", "F", 3);

        String root = "A";
        Digraph mst = Prim.getInstance().process(root, primGraph);
        printGraph(mst);

    }

    public static void printGraph(Graph graph) {
        for (Vertex v : graph.getVertexes()) {
            System.out.print("Vertex " + v.getLabel() + ":");
            List<Vertex> adjacencies = graph.getAdjacencies(v.getLabel());
            if (!adjacencies.isEmpty()) {
                for (Vertex adj : adjacencies) {
                    System.out.print(adj.getLabel() + "(" + graph.getWeight(v.getLabel(), adj.getLabel()) + "), ");
                }
            } else {
                System.out.print("-");
            }
            System.out.println();
        }
    }

    public static void printVertexAdjacencies(Graph graph, String vertexLabel) {
        List<Vertex> adjacencies = graph.getAdjacencies(vertexLabel);
        System.out.println();
        System.out.println("Adjacencies of Vertex " + vertexLabel + ": ");

        for(Vertex vertex : adjacencies) {
            System.out.print(vertex.getLabel() + " ");
        }
    }

}
