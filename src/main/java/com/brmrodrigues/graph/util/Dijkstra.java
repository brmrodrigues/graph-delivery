package main.java.com.brmrodrigues.graph.util;

import main.java.com.brmrodrigues.graph.core.Graph;
import main.java.com.brmrodrigues.graph.core.Vertex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Dijkstra {

    private static Dijkstra dijkstra;

    private Dijkstra(){ }

    public static Dijkstra getInstance() {
        if (dijkstra == null) {
            return new Dijkstra();
        }

        return dijkstra;
    }

    public Map<String, Info> process(String source, String target, Graph graph) {
        try {
            graph.getVertex(source);
            graph.getVertex(target);
        } catch (Exception e) {
            throw e;
        }

        Map<String, Info> infoVertex = new HashMap<>();
        infoVertex.put(source, new Info(0, null));

        Set<String> toBeVisited = new HashSet<>();
        toBeVisited.add(source);

        while(toBeVisited.size() > 0) {
            String bestVertex = null;
            int shortestDistance = Integer.MAX_VALUE;

            for(String v : toBeVisited) {
                Info info = infoVertex.get(v);
                if (info.distance < shortestDistance) {
                    bestVertex = v;
                    shortestDistance = info.distance;
                }
            }

            if (bestVertex.equals(target)) {
                break;
            }

            toBeVisited.remove(bestVertex);

            for(Vertex neighbour : graph.getAdjacencies(bestVertex)) {
                String label = neighbour.getLabel();
                int distance = shortestDistance + graph.getWeight(bestVertex, label);
                if (infoVertex.containsKey(label)) {
                    Info info = infoVertex.get(label);
                    if (distance < info.distance) {
                        info.distance = distance;
                        info.ancestor = graph.getVertex(bestVertex);
                    }
                } else {
                    infoVertex.put(label, new Info(distance, graph.getVertex(bestVertex)));
                    toBeVisited.add(label);
                }
            }
        }

        return infoVertex;
    }

    public class Info {
        public int distance;
        public Vertex ancestor;

        Info(int distance, Vertex ancestor) {
            this.distance = distance;
            this.ancestor = ancestor;
        }
    }
}
