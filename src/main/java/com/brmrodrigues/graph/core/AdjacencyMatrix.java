package main.java.com.brmrodrigues.graph.core;

import java.util.*;

public class AdjacencyMatrix {

    private int[][] matrix;
    private List<Vertex> vertexes;
    private int numberOfVertexes;
    private Map<Integer, List<Vertex>> ancestors;

    public AdjacencyMatrix(List<Vertex> vertexes) {
        this.vertexes = vertexes;
        this.numberOfVertexes = vertexes.size();
        this.matrix = new int[numberOfVertexes][numberOfVertexes];
        this.ancestors = new HashMap<>();
        initMatrix();
    }

    private void initMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    int getWeight(int sourceVertexIndex, int targetVertexIndex) {
        return this.matrix[sourceVertexIndex][targetVertexIndex];
    }

    public int getNumberOfVertexes() {
        return numberOfVertexes;
    }

    private void writeInCell(int row, int column, int value) {
        this.matrix[row][column] = value;
    }

    void copyValuesTo(AdjacencyMatrix targetMatrix) throws Exception {
        if (targetMatrix.getNumberOfVertexes() < this.numberOfVertexes) {
            throw new Exception("Target matrix must be equal or greater in size");
        }

        for(int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                targetMatrix.writeInCell(i, j, matrix[i][j]);
            }
        }
    }

    private void addAncestor(int vertexIndex, Vertex ancestor) {
        if (this.ancestors.get(vertexIndex) == null) {
            List<Vertex> ancestors = new ArrayList<>();
            ancestors.add(ancestor);
            this.ancestors.put(vertexIndex, ancestors);
        } else {
            this.ancestors.get(vertexIndex).add(ancestor);
        }
    }

    public void addDirectedEdge(int sourceVertexIndex, int targetVertexIndex, Integer weight) {
        weight = weight == null ? 1 : weight;
        Vertex sourceVertex = vertexes.get(sourceVertexIndex);

        if (sourceVertexIndex == targetVertexIndex) {
            matrix[sourceVertexIndex][sourceVertexIndex] = weight;
            sourceVertex.addDegree();
        } else {
            matrix[sourceVertexIndex][targetVertexIndex] = weight;
            Vertex targetVertex = vertexes.get(targetVertexIndex);
            targetVertex.addDegree();
        }
        this.addAncestor(targetVertexIndex, sourceVertex);
    }

    List<Vertex> getAncestors(int vertexIndex) {
        if (this.ancestors.get(vertexIndex) == null) {
            return Collections.emptyList();
        }
        return this.ancestors.get(vertexIndex);
    }

    boolean hasAncestors(int vertexIndex) {
        return this.ancestors.containsKey(vertexIndex);
    }

    public void addEdge(int sourceVertexIndex, int targetVertexIndex, Integer weight) {
        weight = weight == null ? 1 : weight;
        Vertex sourceVertex = vertexes.get(sourceVertexIndex);
        Vertex targetVertex = vertexes.get(targetVertexIndex);

        if (sourceVertexIndex == targetVertexIndex) {
            matrix[sourceVertexIndex][sourceVertexIndex] = weight;
            sourceVertex.addDegree();
        } else {
            matrix[sourceVertexIndex][targetVertexIndex] = weight;
            sourceVertex.addDegree();
            matrix[targetVertexIndex][sourceVertexIndex] = weight;
            targetVertex.addDegree();
        }
    }

    public List<Vertex> getAdjacencies(int vertexIndex) {
        int row = vertexIndex;
        List<Vertex> adjacencies = new ArrayList<>();

        for (int j = 0; j < numberOfVertexes; j++) {
            if (matrix[row][j] != 0) {
                Vertex vertex = vertexes.get(j);
                adjacencies.add(vertex);
            }
        }
        return adjacencies;
    }
}
