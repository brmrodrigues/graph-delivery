package main.java.com.brmrodrigues.graph.core;

public class Vertex {
    private String label;
    private int degree;

    public Vertex(String label) throws Exception {
        boolean isLabelNullOrBlank = label == null || label != null && "".equals(label.trim());

        if (isLabelNullOrBlank) {
            throw new Exception("Vertex cannot be null or blank");
        }
        this.label = label;
    }

    void addDegree() {
        degree++;
    }

    public int getDegree() {
        return degree;
    }

    public String getLabel() {
        return this.label;
    }
}
