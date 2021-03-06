package main.java.com.brmrodrigues.graph.core;

import java.util.*;

public class Graph {

    private int vertexMaxNumber;
    private boolean hasVertexMaxNumber;
    private int vertexCurrentNumber = 0;
    private Map<String, Integer> labelsWithIndex = new HashMap<>();
    private List<Vertex> vertexes = new ArrayList<>();
    private AdjacencyMatrix adjacencyMatrix;
    private boolean isDirected;

    public Graph() {
        vertexMaxNumber = 10;
    }

    public Graph(boolean isDirected) {
        vertexMaxNumber = 10;
        this.isDirected = isDirected;
    }

    public Graph(int vertexMaxNumber, boolean isDirected) throws Exception {
        if (vertexMaxNumber < 1) {
            throw new IllegalArgumentException("The number of vertexes must be greater than zero");
        }
        this.vertexMaxNumber = vertexMaxNumber;
        hasVertexMaxNumber = true;
        this.isDirected = isDirected;
    }

    boolean existsVertex(String vertexLabel) {
        int index = this.labelsWithIndex.get(vertexLabel);
        return this.vertexes.get(index) != null;
    }

    private boolean existsVertexOrThrow(String vertex) {
        if (!existsVertex(vertex)) {
            throw new IllegalArgumentException("The vertex " + vertex + "doesn't exist");
        }
        return true;
    }

    void createAdjacencyMatrix() throws Exception {
        if (this.adjacencyMatrix == null) {
            this.adjacencyMatrix = new AdjacencyMatrix(new ArrayList<>(this.vertexes));
        } else {
            int matrixNumberOfVertex = this.adjacencyMatrix.getNumberOfVertexes();
            if (this.vertexes.size() != matrixNumberOfVertex) {
                AdjacencyMatrix tmpAdjMatrix = new AdjacencyMatrix(this.vertexes);
                this.adjacencyMatrix.copyValuesTo(tmpAdjMatrix);
                this.adjacencyMatrix = tmpAdjMatrix;
            }
        }
    }

    public void addVertex(String label) throws Exception {
        if (vertexCurrentNumber < vertexMaxNumber - 1) {
            Vertex newVertex = new Vertex(label);
            this.vertexes.add(newVertex);
            this.labelsWithIndex.put(label, vertexCurrentNumber);
            vertexCurrentNumber++;
        } else {
            throw new Exception("The maximum capacity of " + vertexMaxNumber + " vertexes has reached");
        }
    }

    public void linkVertexes(String sourceVertexLabel, String targetVertexLabel, Integer weight) throws Exception {
        if (!this.existsVertex(sourceVertexLabel) ||
                !this.existsVertex(targetVertexLabel)) {
            throw new Exception("Both source and target vetexes must exist in order to add an edge");
        }
        createAdjacencyMatrix();
        int sourceVertexIndex = this.labelsWithIndex.get(sourceVertexLabel);
        int targetVertexIndex = this.labelsWithIndex.get(targetVertexLabel);
        if (isDirected) {
            this.adjacencyMatrix.addDirectedEdge(sourceVertexIndex, targetVertexIndex, weight);
        } else {
            this.adjacencyMatrix.addEdge(sourceVertexIndex, targetVertexIndex, weight);
        }
    }

    public List<Vertex> getAdjacencies(String vertex) {
        this.existsVertexOrThrow(vertex);
        int vertexIndex = this.labelsWithIndex.get(vertex);
        return this.adjacencyMatrix.getAdjacencies(vertexIndex);
    }

    private Vertex getNextVertex(Vertex vertex, LinkedHashSet visitedVertexes) {
        List<Vertex> adjacencies = getAdjacencies(vertex.getLabel());

        for(int i = 0; i < adjacencies.size(); i++) {
            Vertex adjacency = adjacencies.get(i);
            if (!visitedVertexes.contains(adjacency.getLabel())) { // not visited yet
                return adjacency;
            }
        }
        return null;
    }

    public Graph depthGeneratedTree() throws Exception {
        Graph tree = new Graph();
        Stack<Vertex> currentPath = new Stack<>();
        LinkedHashSet<String> visitedVertexes = new LinkedHashSet<>();
        List<Vertex> vertexes = getVertexes();

        for(Vertex v : vertexes) {
            tree.addVertex(v.getLabel());
        }

        Vertex sourceVertex = vertexes.get(0);
        visitedVertexes.add(sourceVertex.getLabel());
        currentPath.push(sourceVertex);

        while(!currentPath.empty()) {
            Vertex currentVertex = currentPath.peek();
            Vertex nextVertex = getNextVertex(currentVertex, visitedVertexes);

            if (nextVertex == null) {
                currentPath.pop();
            } else {
                String label = nextVertex.getLabel();
                visitedVertexes.add(label);
                currentPath.push(nextVertex);
                tree.linkVertexes(currentVertex.getLabel(), nextVertex.getLabel(), null);
            }
        }

        return tree;
    }

    public int getWeight(String sourceVertexLabel, String targetVertexLabel) {
        int sourceVertexIndex = labelsWithIndex.get(sourceVertexLabel);
        int targetVertexIndex = labelsWithIndex.get(targetVertexLabel);

        return adjacencyMatrix.getWeight(sourceVertexIndex, targetVertexIndex);
    }

    public Vertex getVertex(String label) {
        this.existsVertexOrThrow(label);
        int index = this.labelsWithIndex.get(label);
        return this.vertexes.get(index);
    }

    Map<String, Integer> getLabelsWithIndex() {
        return labelsWithIndex;
    }

    AdjacencyMatrix getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public List<Vertex> getVertexes() {
        return this.vertexes;
    }

}

