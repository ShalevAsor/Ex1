package ex1;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;


public class WGraph_DS implements weighted_graph{
    private HashMap<Integer,node_info> vertices=new HashMap<Integer, node_info>();
    private HashMap<Integer,Edge> edges=new HashMap<Integer, Edge>();
    private HashMap<Integer,HashMap<Integer,node_info>> neighbors=new HashMap<Integer,HashMap<Integer,node_info>>();
    private int ModeCount=0;

    /*
    This private class represent an edge in the graph . every edge have a Svertex(the node thats the edge is start from)
    and Evertex(the node destination), every edge have a weight.
     */

    private class Edge{
        private double _weight;
        private node_info Svertex;//represent the start vertex of the edge
        private node_info Evertex;//represent the destination vertex of the edge
        //-----------constructor---------//
        public Edge(){//empty constructor
            this._weight=0;
        }

        public Edge(node_info start,node_info dis,double weight){
            this._weight=weight;
            this.Evertex=dis;
            this.Svertex=start;
        }
    }
    //-------------------NodeInfo-----------------//
    static class NodeInfo implements node_info{
        private int _key=0;
        private String _info;
        private double _tag;
        private static int id = 1;

        //--------constructors---------//
        public NodeInfo() {  //empty constructor- the first vertex key will be 1
            this._key = id++;
        }
        public NodeInfo(int key){
            this._key=key;
        }

        /**
         * This method return this node key
         * @return this node key
         */
        @Override
        public int getKey() {
            return this._key;
        }

        /**
         * This method return this node info
         * @return this node info
         */
        @Override
        public String getInfo() {
            return this._info;
        }

        /**
         * This method allow to set this node info
         * @param s
         */
        @Override
        public void setInfo(String s) {
            this._info=s;
        }

        /**
         * This method return this node tag
         * @return this node tag
         */
        @Override
        public double getTag() {
            return this._tag;
        }

        /**
         * This method allow to set this node tag
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            this._tag=t;
        }

    }
    //--------------------WGraph_DS-------------------------------------//
    //--------constructor-----------//
    public WGraph_DS(){
    }

    /**
     * This method return the node_info by the given key
     * @param key - the node_id
     * @return node_info
     */
    @Override
    public node_info getNode(int key) {
        return this.vertices.get(key);
    }

    /**
     * This method return true iff there is an edge between node1 and node2
     * @param node1
     * @param node2
     * @return true if this nodes is connected, else return false
     */

    @Override
    public boolean hasEdge(int node1, int node2) {
        boolean ans=false;
        if(!this.vertices.containsKey(node1)||!this.vertices.containsKey(node2))return ans;
        node_info pointer1=this.vertices.get(node1);
        node_info pointer2=this.vertices.get(node2);
        int hcode1=this.hashCode(pointer1,pointer2);
        int hcode2=this.hashCode(pointer2,pointer1);
        if((this.edges.containsKey(hcode1))){
            if(this.edges.get(hcode1)._weight!=0)return ans=true;}
        if(this.edges.containsKey(hcode2)){
            if(this.edges.get(hcode2)._weight!=0)return ans=true;}
        return ans;
    }

    /**
     * This method return the weight of the edge between node1 and node2
     * @param node1
     * @param node2
     * @return weight,if the nodes are disconnected return -1
     */

    @Override
    public double getEdge(int node1, int node2) {
        if(this.vertices.containsKey(node1)&&this.vertices.containsKey(node2)){
            if(this.hasEdge(node1,node2)){
                node_info pointer1=this.vertices.get(node1);
                node_info pointer2=this.vertices.get(node2);
                int hcode1=this.hashCode(pointer1,pointer2);
                int hcode2=this.hashCode(pointer2,pointer1);
                if(this.edges.containsKey(hcode1)){
                    return this.edges.get(hcode1)._weight;}
                else{
                    return this.edges.get(hcode2)._weight;
                }
            }}
        return -1;
    }

    /**
     * This method add a new node into this weighted graph .
     * @param key
     */
    @Override
    public void addNode(int key) {
        if(!this.vertices.containsKey(key)){
            this.vertices.putIfAbsent(key,new NodeInfo(key));
            HashMap<Integer,node_info> ni=new HashMap<Integer,node_info>();
            this.neighbors.putIfAbsent(key,ni);
            ModeCount++;}
    }


    /**
     * This method connecting node1 and node2 with an edge.
     * the edge weight will be w.
     * @param node1
     * @param node2
     * @param w
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (this.vertices.containsKey(node1) && this.vertices.containsKey(node2)) {
            if (w != 0&&node1!=node2) {
                node_info pointer1 = this.vertices.get(node1);
                node_info pointer2 = this.vertices.get(node2);
                this.edges.putIfAbsent(this.hashCode(pointer1, pointer2), new Edge(pointer1, pointer2, w));
                this.neighbors.get(node1).putIfAbsent(node2, pointer2);
                this.neighbors.get(node2).putIfAbsent(node1, pointer1);
                ModeCount++;
            }
        }
    }

    /**
     * This method return a Collection of all the vertices in this weighted graph
     * @return vertices Collection
     */
    @Override
    public Collection<node_info> getV() {
        return this.vertices.values();
    }

    /**
     * This method return a Collection of all this node_id neighbors
     * @param node_id
     * @return neighbors Collection
     */

    @Override
    public Collection<node_info> getV(int node_id) {
        return this.neighbors.get(node_id).values();
    }

    /**
     * This method remove the node with the given key from this weighted graph and from his neighbors
     * @param key
     * @return the node_info
     */

    @Override
    public node_info removeNode(int key) {
        if(this.vertices.containsKey(key)){
            Iterator<node_info> it =this.getV(key).iterator();
            while(it.hasNext()){
                node_info pointer = it.next();
                this.neighbors.get(pointer.getKey()).remove(key);
                this.edges.remove(hashCode(this.vertices.get(key),pointer));
                this.edges.remove(hashCode(pointer,this.vertices.get(key)));
                ModeCount++;
            }
            this.neighbors.remove(key);
            ModeCount++;
            return this.vertices.remove(key);
        }
        return null;
    }

    /**
     * This method remove node1 and node2 edge
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if(node1!=node2){
            this.edges.remove(hashCode(this.vertices.get(node1),this.vertices.get(node2)));
            this.edges.remove(hashCode(this.vertices.get(node2),this.vertices.get(node1)));
            this.neighbors.get(node1).remove(node2);
            this.neighbors.get(node2).remove(node1);
            ModeCount++;
        }

    }

    /**
     * This method return the number of the vertices in this weighted graph
     * @return number of vertices
     */
    @Override
    public int nodeSize() {
        return this.vertices.size();
    }

    /**
     * This method return the number of edges in this weighted graph
     * @return number of edges
     */
    @Override
    public int edgeSize() {
        return this.edges.size();
    }

    /**
     * This method return the Mode Count
     * @return
     */
    @Override
    public int getMC() {
        return this.ModeCount;
    }

    public HashMap<Integer, Edge> getEdges() {
        return this.edges;
    }

    public HashMap<Integer, HashMap<Integer, node_info>> getNeighbors() {
        return this.neighbors;
    }

    public HashMap<Integer, node_info> getVertices() {
        return this.vertices;
    }

    public boolean equals(int node_key, Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeInfo)) return false;
        NodeInfo nodeInfo = (NodeInfo) o;
        return node_key == nodeInfo._key &&
                Double.compare(nodeInfo._tag, this.vertices.get(node_key).getTag()) == 0 &&
                Objects.equals(this.vertices.get(node_key).getInfo(), nodeInfo._info);
    }

    public int hashCode(node_info n1, node_info n2) {
        return Objects.hash(n1, n2);
    }

    public static void main(String[] args) {
        node_info n1=new NodeInfo();
        node_info n2=new NodeInfo();

        WGraph_DS g1=new WGraph_DS();
        g1.addNode(1);
        g1.addNode(2);
        System.out.println(g1.hashCode(n1,n2));
        System.out.println(g1.hashCode(n2,n1));
        System.out.println(g1.hashCode(n2,n1));


    }
}
