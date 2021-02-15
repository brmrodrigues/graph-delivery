package main.java.com.brmrodrigues.application;

import main.java.com.brmrodrigues.graph.core.Graph;
import main.java.com.brmrodrigues.graph.core.Vertex;
import main.java.com.brmrodrigues.graph.search.BreadthFirstSearch;
import main.java.com.brmrodrigues.graph.search.DepthFirstSearch;

import java.util.List;

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

        Graph graphLetters = new Graph();

        graphLetters.addVertex("A");
        graphLetters.addVertex("B");
        graphLetters.addVertex("C");
        graphLetters.addVertex("D");

        graphLetters.linkVertexes("A", "B");
        graphLetters.linkVertexes("A", "C");
        graphLetters.linkVertexes("A", "D");

        System.out.println("Vertex A degree: " + graphLetters.getVertex("A").getDegree());
        System.out.println("Vertex C degree: " + graphLetters.getVertex("C").getDegree());
        System.out.println("Vertex D degree: " + graphLetters.getVertex("D").getDegree());
        printVertexAdjacencies(graphLetters, "A");
        printVertexAdjacencies(graphLetters, "B");
        printVertexAdjacencies(graphLetters, "C");

        Graph searchGraph = new Graph();

        searchGraph.addVertex("A");
        searchGraph.addVertex("B");
        searchGraph.addVertex("C");
        searchGraph.addVertex("D");
        searchGraph.addVertex("E");
        searchGraph.addVertex("F");
        searchGraph.addVertex("G");
        searchGraph.addVertex("H");
        searchGraph.addVertex("I");

        searchGraph.linkVertexes("A", "B");
        searchGraph.linkVertexes("A", "C");
        searchGraph.linkVertexes("A", "D");
        searchGraph.linkVertexes("B", "F");
        searchGraph.linkVertexes("B", "I");
        searchGraph.linkVertexes("D", "E");
        searchGraph.linkVertexes("D", "I");
        searchGraph.linkVertexes("D", "G");
        searchGraph.linkVertexes("I", "A");
        searchGraph.linkVertexes("I", "D");
        searchGraph.linkVertexes("I", "C");
        searchGraph.linkVertexes("I", "H");
        searchGraph.linkVertexes("E", "A");

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