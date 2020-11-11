package ex1;
import ex1.WGraph_DS.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph graph=new WGraph_DS();


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

    /**
     * This method compute a deep copy of this weighted graph
     * @return copy of this graph
     */
    @Override
    public weighted_graph copy() {
        WGraph_DS copy=new WGraph_DS();
        Iterator<node_info> it1 =this.graph.getV().iterator();
        while(it1.hasNext()) {
            node_info pointer = it1.next();
            copy.addNode(pointer.getKey());
            copy.getVertices().get(pointer.getKey()).setTag(pointer.getTag());
            copy.getVertices().get(pointer.getKey()).setInfo(pointer.getInfo());
        }
            Iterator<node_info> it2=this.graph.getV().iterator();
            while(it2.hasNext()) {
                node_info pointer2 = it2.next();
                Iterator<node_info> it3 = this.graph.getV(pointer2.getKey()).iterator();
                while (it3.hasNext()) {
                    node_info pointer3 = it3.next();
                    double weight = this.graph.getEdge(pointer2.getKey(), pointer3.getKey());
                    copy.connect(pointer2.getKey(), pointer3.getKey(), weight);
                }
            }
            return copy;
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
