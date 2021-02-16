package main.java.com.brmrodrigues.graph.core;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class Digraph extends Graph {

    public void linkVertexes(String sourceVertexLabel, String targetVertexLabel, Integer weight) throws Exception {
        if (!super.existsVertex(sourceVertexLabel) || !super.existsVertex(targetVertexLabel)) {
            throw new Exception("Both vertexes must exist to add an edge");
        }
        Map<String, Integer> labelsWithIndex = super.getLabelsWithIndex();
        super.createAdjacencyMatrix();
        AdjacencyMatrix adjacencyMatrix = getAdjacencyMatrix();
        int sourceVertexIndex = labelsWithIndex.get(sourceVertexLabel);
        int targetVertexIndex = labelsWithIndex.get(targetVertexLabel);

        adjacencyMatrix.addDirectedEdge(sourceVertexIndex, targetVertexIndex, weight);
    }

    private void visit(String current, LinkedHashSet<String> toBeVisited, Digraph tree) throws Exception {
        for(Vertex neighbor : super.getAdjacencies(current)) {
            String label = neighbor.getLabel();
            if(!toBeVisited.contains(label)) {
                continue;
            }
            tree.addVertex(label);
            tree.linkVertexes(current, label, null);
            toBeVisited.remove(label);
            visit(label, toBeVisited, tree);
        }
    }

    public Graph depthGeneratedTree(String root) throws Exception {
        LinkedHashSet<String> toBeVisited = new LinkedHashSet<>();
        Digraph tree = new Digraph();
        List<Vertex> vertexes = super.getVertexes();
        AdjacencyMatrix adjacencyMatrix = super.getAdjacencyMatrix();
        Map<String, Integer> labelsWithIndex = super.getLabelsWithIndex();
        int rootIndex = labelsWithIndex.get(root);

        for(Vertex v : vertexes) {
            toBeVisited.add(v.getLabel());
        }

        if (root == null) {
            root = vertexes.get(0).getLabel();
        }

        toBeVisited.remove(root);
        tree.addVertex(root);
        this.visit(root, toBeVisited, tree);

        while(toBeVisited.size() > 0) {
            if (!adjacencyMatrix.hasAncestors(rootIndex)) {
                break;
            }
            String ancestor = null;
            for(Vertex a : adjacencyMatrix.getAncestors(rootIndex)) {
                if (toBeVisited.contains(a.getLabel())) {
                    ancestor = a.getLabel();
                    break;
                }
            }
            if (ancestor == null) {
                throw new Exception("All ancestors have already been visited. Digraph is not fully connected");
            }

            toBeVisited.remove(ancestor);
            tree.addVertex(ancestor);
            tree.linkVertexes(ancestor, root, null);
            root = ancestor;
            rootIndex = labelsWithIndex.get(root);
            this.visit(root, toBeVisited, tree);
        }

        return tree;
    }

    public Graph depthGeneratedTree() throws Exception {
        String root = super.getVertexes().get(0).getLabel();
        return this.depthGeneratedTree(root);
    }
}
