package org.apache.storm.multicast.dynamicswitch;

import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.HashMap;

/**
 * locate org.apache.storm.multicast.dynamicswitch
 * Created by master on 2019/10/31.
 */
public class GraphAndChanges {
    private DirectedAcyclicGraph<String, DefaultEdge> graph;
    private HashMap<String, ChangeMsg> changes;

    public GraphAndChanges(DirectedAcyclicGraph<String, DefaultEdge> graph, HashMap<String, ChangeMsg> changes) {
        this.graph = graph;
        this.changes = changes;
    }

    public DirectedAcyclicGraph<String, DefaultEdge> getGraph() {
        return graph;
    }

    public HashMap<String, ChangeMsg> getChanges() {
        return changes;
    }
}

