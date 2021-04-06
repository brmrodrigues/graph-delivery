package main.java.com.brmrodrigues.graph.util;

import main.java.com.brmrodrigues.graph.core.Digraph;
import main.java.com.brmrodrigues.graph.core.Graph;
import main.java.com.brmrodrigues.graph.core.Vertex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Prim {
    private static Prim prim;
    private Map<String, String> candidates;
    private Set<String> toLink;

    private Prim(){}

    public static Prim getInstance() {
        if (prim == null) {
            return new Prim();
        }
        return prim;
    }

    public Digraph process(String root, Graph graph) throws Exception {
        toLink = new HashSet<>();
        for (Vertex vertex : graph.getVertexes()) {
            toLink.add(vertex.getLabel());
        }

        Digraph mst = new Digraph();
        mst.addVertex(root);
        toLink.remove(root);

        candidates = new HashMap<>();
        updateCandidates(graph, root);

        while (toLink.size() > 0) {
            String bestU = null;
            String bestV = null;
            int shortestDistance = Integer.MAX_VALUE;
            for (String u : candidates.keySet()) {
                String v = candidates.get(u);
                int weight = graph.getWeight(u, v);
                if (weight < shortestDistance) {
                    bestU = u;
                    bestV = v;
                    shortestDistance = weight;
                }
            }

            if (shortestDistance == Integer.MAX_VALUE)
                break;

            mst.addVertex(bestV);
            mst.linkVertexes(bestU, bestV, shortestDistance);

            toLink.remove(bestV);
            updateCandidates(graph, bestU);
            updateCandidates(graph, bestV);
        }

        return mst;
    }

    private void updateCandidates(Graph graph, String vertex) {
        int shortestDistance = Integer.MAX_VALUE;
        String closest = null;
        for (Vertex adj : graph.getAdjacencies(vertex)) {
            int weight = graph.getWeight(vertex, adj.getLabel());
            if (toLink.contains(adj.getLabel()) && weight < shortestDistance) {
                shortestDistance = weight;
                closest = adj.getLabel();
            }
        }
        if (closest != null) {
            candidates.put(vertex, closest);
        } else {
            candidates.remove(vertex);
        }
    }
}
