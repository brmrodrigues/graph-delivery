package main.java.com.brmrodrigues.graph.search;

import main.java.com.brmrodrigues.graph.core.Graph;
import main.java.com.brmrodrigues.graph.core.Vertex;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Stack;

public class DepthFirstSearch {

    private static DepthFirstSearch instance;

    private DepthFirstSearch() { }

    public static DepthFirstSearch getInstance() {
        if (instance == null) {
            return new DepthFirstSearch();
        }
        return instance;
    }

    public List<String> search(Graph graph, String source, String target) {
        Stack<String> currentPath = new Stack<>();
        LinkedHashSet<String> visitedVertexes = new LinkedHashSet<>();
        Path path = new Path();

        currentPath.push(source);

        while(!currentPath.empty()) {
            String v = currentPath.pop();
            if (v.equals(target)) {
                break;
            }
            for (Vertex u : graph.getAdjacencies(v)) {
                String label = u.getLabel();
                if (!visitedVertexes.contains(label)) {
                    visitedVertexes.add(label);
                    path.link(label, v);
                    currentPath.push(label);
                }
            }
        }
        return path.generate(source, target);
    }
}
