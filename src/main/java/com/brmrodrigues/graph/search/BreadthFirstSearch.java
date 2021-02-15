package main.java.com.brmrodrigues.graph.search;

import main.java.com.brmrodrigues.graph.core.Graph;
import main.java.com.brmrodrigues.graph.core.Vertex;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BreadthFirstSearch {

    private static BreadthFirstSearch instance;

    public static BreadthFirstSearch getInstance() {
        if (instance == null) {
            return new BreadthFirstSearch();
        }
        return instance;
    }

    public List<String> search(Graph graph, String source, String target) {
        Queue<String> currentPath = new LinkedList<>();
        LinkedHashSet<String> visitedVertexes = new LinkedHashSet<>();
        Path path = new Path();

        currentPath.add(source);

        while(!currentPath.isEmpty()) {
            String v = currentPath.poll();
            if (v.equals(target)) {
                break;
            }
            for (Vertex u : graph.getAdjacencies(v)) {
                String label = u.getLabel();
                if (!visitedVertexes.contains(label)) {
                    visitedVertexes.add(label);
                    path.link(label, v);
                    currentPath.add(label);
                }
            }
        }
        return path.generate(source, target);
    }
}
