package ex1;

import java.util.List;

public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph graph;

    /**
     * This method initialization this graph to given graph
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        this.graph=g;
    }

    /**
     * This method return the weighted graph that this class works on
     * @return this class graph
     */
    @Override
    public weighted_graph getGraph() {
        return this.graph;
    }

    @Override
    public weighted_graph copy() {
        return null;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }
}
