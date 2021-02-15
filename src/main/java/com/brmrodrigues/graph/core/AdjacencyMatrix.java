package main.java.com.brmrodrigues.graph.core;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyMatrix {

    private int[][] matrix;
    private List<Vertex> vertexes;
    private int numberOfVertexes;

    public AdjacencyMatrix(List<Vertex> vertexes) {
        this.vertexes = vertexes;
        this.numberOfVertexes = vertexes.size();
        this.matrix = new int[numberOfVertexes][numberOfVertexes];
        initMatrix();
    }

    private void initMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    public void addEdge(int sourceVertexIndex, int targetVertexIndex) {
        Vertex sourceVertex = vertexes.get(sourceVertexIndex);
        Vertex targetVertex = vertexes.get(targetVertexIndex);

        if (sourceVertexIndex == targetVertexIndex) {
            matrix[sourceVertexIndex][sourceVertexIndex] = 1;
            sourceVertex.addDegree();
        } else {
            matrix[sourceVertexIndex][targetVertexIndex] = 1;
            sourceVertex.addDegree();
            matrix[targetVertexIndex][sourceVertexIndex] = 1;
            targetVertex.addDegree();
        }
    }

    public List<Vertex> getAdjacencies(int vertexIndex) {
        int row = vertexIndex;
        List<Vertex> adjacencies = new ArrayList<>();

        for (int j = 0; j < vertexes.size(); j++) {
            if (matrix[row][j] == 1) {
                Vertex vertex = vertexes.get(j);
                adjacencies.add(vertex);
            }
        }

        return adjacencies;
    }
}
