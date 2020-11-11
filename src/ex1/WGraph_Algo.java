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
//        WGraph_DS copy=new WGraph_DS();
//        Iterator<node_info> it1 =this.graph.getV().iterator();
//        while(it1.hasNext()){
//        node_info pointer = it1.next();
//        copy.getVertices().putIfAbsent(pointer.getKey(),new WGraph_DS.NodeInfo(pointer));
//        }
//        Iterator<Edge> it2= this.graph.getEdges().values().iterator();
//        while(it2.hasNext()){
//            Edge pointer=it2.next();
//          int key=  copy.hashCode(pointer.getSvertex(),pointer.getEvertex());
//          node_info Snode=copy.getVertices().get(pointer.getSvertex().getKey());
//          node_info Enode=copy.getVertices().get(pointer.getEvertex().getKey());
//            copy.getEdges().putIfAbsent(key,new Edge(Snode,Enode,pointer.getW()));
//        }
//        Iterator<HashMap<Integer,node_info>> it3=this.graph.getNeighbors().values().iterator();
//       // int i=0,size=this.graph.nodeSize();
//        while(it3.hasNext()){
//            HashMap<Integer,node_info> pointer=it3.next();
//            Iterator<node_info> it4=pointer.values().iterator();
//            HashMap<Integer,node_info> mapCopy=new HashMap<Integer, node_info>();
//            while(it4.hasNext()){
//                node_info pointer2=it4.next();
//                int key2=pointer2.getKey();
//                mapCopy.putIfAbsent(key2,copy.getNode(key2));
//            }
//            Iterator<node_info> it5=this.graph.getV().iterator();
//            copy.getNeighbors().putIfAbsent(it5.next().getKey(),mapCopy);
//        }
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
