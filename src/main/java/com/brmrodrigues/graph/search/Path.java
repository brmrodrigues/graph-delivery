package main.java.com.brmrodrigues.graph.search;

import java.util.*;

public class Path {

    private Map<String, String> path;

    public Path() {
        this.path = new HashMap<>();
    }

    void link(String before, String after) {
        this.path.put(before, after);
    }

    public List<String> generate(String source, String target) {
        List<String> result = new ArrayList<>();
        String node = target;

        while (node != source && this.path.containsKey(node)) {
            result.add(node);
            node = this.path.get(node);
        }
        result.add(node);
        Collections.reverse(result);
        return result;
    }
}
