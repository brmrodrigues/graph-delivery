package main.java.com.brmrodrigues.graph.util;

import main.java.com.brmrodrigues.graph.core.Digraph;
import main.java.com.brmrodrigues.graph.core.Vertex;

import java.util.HashMap;
import java.util.Map;

public class FloydMarshall {
    private static FloydMarshall floydMarshall;

    private FloydMarshall() { }

    public static FloydMarshall getInstance() {
        if (floydMarshall == null) {
            return new FloydMarshall();
        }
        return floydMarshall;
    }

    public Map<String, Map<String, Info>> process(Digraph digraph) {
        Map<String, Map<String, Info>> matrix = new HashMap<>();
        for(Vertex u : digraph.getVertexes()) {
            Map<String, Info> row = new HashMap<>();
            matrix.put(u.getLabel(), row);
            for(Vertex v : digraph.getVertexes()) {
                int weight = digraph.getWeight(u.getLabel(), v.getLabel());
                int value = weight == 0 ? Integer.MAX_VALUE : weight;
                Info info = new Info();
                info.throughVertex = v;
                info.distance = u.getLabel().equalsIgnoreCase(v.getLabel()) ? 0 : value;
                row.put(v.getLabel(), info);
            }
        }

        for(Vertex k : digraph.getVertexes()) {
            Map<String, Info> rowK = matrix.get(k.getLabel());
            for(Vertex u : digraph.getVertexes()) {
                Map<String, Info> rowU = matrix.get(u.getLabel());
                Info uk = rowU.get(k.getLabel());
                for(Vertex v : digraph.getVertexes()) {
                    Info kv = rowK.get(v.getLabel());
                    int sum = uk.distance == Integer.MAX_VALUE || kv.distance == Integer.MAX_VALUE ? Integer.MAX_VALUE :
                            uk.distance + kv.distance;
                    if (sum < rowU.get(v.getLabel()).distance) {
                        Info info = new Info();
                        info.throughVertex = uk.throughVertex;
                        info.distance = sum;
                        rowU.put(v.getLabel(), info);
                    }
                }
            }
        }

        return matrix;
    }

    public class Info {
        public int distance;
        public Vertex throughVertex;
    }
}
