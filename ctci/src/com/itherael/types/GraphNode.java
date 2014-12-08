package com.itherael.types;

import java.util.List;

public class GraphNode {
    public List<GraphNode> adjacent;
    public String val;
    public boolean visited;
    
    public GraphNode(String v) {
        this.val = v;
    }
}
