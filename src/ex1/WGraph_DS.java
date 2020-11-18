package ex1;

import java.util.*;

/**
 * This class implements weighted_graph and supports basic methods like addNode, removeNode , addEdge
 * and more, each method in this class attached with detailed explanations about how it works and Complexity.
 * the data structure of the vertices and edges is HashMap, the neighbors represented with HashMap ass well
 * but there is neighbors HashMap for each vertex in this graph and all those HashMap contained in another HashMap
 */

public class WGraph_DS implements weighted_graph,java.io.Serializable{
    private HashMap<Integer,node_info> vertices=new HashMap<Integer,node_info>();
    private HashMap<Integer,Double> edges=new HashMap<Integer, Double>();
    private HashMap<Integer,HashMap<Integer,node_info>> neighbors=new HashMap<Integer,HashMap<Integer,node_info>>();
    private int ModeCount=0;

    /**
     * This class represent vertex in the graph , each node has a unique key, info and tag that used in Bfs algorithm.
     */
    //-------------------NodeInfo-----------------//
    private static class NodeInfo implements node_info,java.io.Serializable{
        private int _key;
        private String _info="white";
        private double _tag;// the tag represent the edge weight
        private static int id = 1;

        //--------constructor---------//
        //insert only key
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
    //--------------------WGraph_DS--------------------------//
    //--------constructor-----------//
    public WGraph_DS(){
    }

    /**
     * This method return the node_info by the given key, if this graph is not contains this key
     * return null
     * runtime- O(1)
     * @param key - the node_id
     * @return node_info else null
     */
    @Override
    public node_info getNode(int key) {
        return this.vertices.get(key);
    }

    /**
     * This method return true iff there is an edge between node1 and node2
     * else return false.
     * runtime - O(1)
     * @param node1
     * @param node2
     * @return true if this nodes is connected, else return false
     */

    @Override
    public boolean hasEdge(int node1, int node2) {
        boolean ans=false;
        if(!this.vertices.containsKey(node1)||!this.vertices.containsKey(node2))return ans;//if the vertices isnt in the graph the ans is false
        if(this.neighbors.get(node1).containsKey(node2))ans=true;//check if node2 in the node1 neighbors
        return ans;
    }

    /**
     * This method return the weight of the edge between node1 and node2
     * The edge key is hashcode of node1 and node2
     * runtime-O(1)
     * @param node1
     * @param node2
     * @return weight,if the nodes are disconnected return -1
     */

    @Override
    public double getEdge(int node1, int node2) {
        if(node1==node2)return 0;
        if(this.hasEdge(node1,node2)) {//if there is an edge between node1 and node2
            int hcode1 = this.hashCode(node1, node2);
            int hcode2 = this.hashCode(node2, node1);
            if (this.edges.containsKey(hcode1)) {
                return this.edges.get(hcode1);
            } else {
                return this.edges.get(hcode2);
            }
        }
        return -1;
    }

    /**
     * This method add a new node into this weighted graph.
     * runtime-O(1)
     * @param key
     */
    @Override
    public void addNode(int key) {
        //if(!this.vertices.containsKey(key)){//if this key isnt in the graph
            this.vertices.putIfAbsent(key,new NodeInfo(key));//put a new node into the graph
            HashMap<Integer,node_info> ni=new HashMap<Integer,node_info>();//create HashMap to represent his neighbors
            this.neighbors.putIfAbsent(key,ni);//put it in the neighbors hashmap
            ModeCount++;}//increase the ModeCount
   // }

    /**
     * This method connecting node1 and node2 with an edge.
     * if node1 and node2 has edge then the new weight will be update.
     * the edge weight will be w.
     * runtime-O(1)
     * @param node1
     * @param node2
     * @param w
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (this.vertices.containsKey(node1) && this.vertices.containsKey(node2)) {//if this vertices is in the graph
           // if (!this.hasEdge(node1, node2)) {//and there is no edge between this vertices
                if (w >= 0 && node1 != node2) {//and node1 (key) and node2(key) is different(and the weight is un negative
                   if(this.hasEdge(node1,node2)){
                      this.edges.replace(hashCode(node1,node2),w);
                        ModeCount++;
                   }
                   else{
                    node_info pointer1 = this.vertices.get(node1);//create pointer for the hashcode
                    node_info pointer2 = this.vertices.get(node2);//so it will be easier to access
                    this.edges.put(this.hashCode(node1, node2), w);//add them to edges
                    this.neighbors.get(node1).put(node2, pointer2);//add node2 to node1 neighbors
                    this.neighbors.get(node2).put(node1, pointer1);//add node1 to node2 neighbors
                    ModeCount++;}
            }
        }
    }
    /**
     * This method return a Collection of all the vertices in this weighted graph
     * runtime-O(1)
     * @return vertices Collection
     */
    @Override
    public Collection<node_info> getV() {
        return this.vertices.values();
    }
    /**
     * This method return a Collection of all this node_id neighbors
     * runtime-O(1)
     * @param node_id
     * @return neighbors Collection
     */

    @Override
    public Collection<node_info> getV(int node_id) {
        if(this.vertices.containsKey(node_id)){
        return this.neighbors.get(node_id).values();}
        else{
            List<node_info> emptyCollection=new LinkedList<>();
            return emptyCollection;
        }
    }

    /**
     * This method remove the node with the given key from this weighted graph and from his neighbors
     * Complexity-if V is the sum of this node neighbors than O(v)
     * @param key
     * @return the node_info
     */

    @Override
    public node_info removeNode(int key) {
        if(this.vertices.containsKey(key)){//if this node is in the graph
            Iterator<node_info> it =this.getV(key).iterator();//makeing iterator to gain access to all this node neighbors
            while(it.hasNext()){//while this node has neighbors
                node_info pointer = it.next();//makeing a pointer for easier access
                this.neighbors.get(pointer.getKey()).remove(key);//remove this node from each of his neighbors
                this.edges.remove(hashCode(key,pointer.getKey()));//remove the edge
                this.edges.remove(hashCode(pointer.getKey(),key));//remove the edge
                ModeCount++;// increase the ModeCount every neighbor thats removed
            }
            this.neighbors.remove(key);//remove him from the HashMap thats keeps the neighbors HashMap
            ModeCount++;//increase the ModeCount again for removing this node from the graph
            return this.vertices.remove(key);
        }
        return null;//if this node is not in the graph return null
    }

    /**
     * This method removes the edge between node1 and node2.
     *
     * Complexity-O(1)
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
       if(node1!=node2){//if this is not the same vertex
            /*
            note:i dont need to make sure that there is an edge between this
             vertices because if there is node edge between them nothing will happen
             */
            this.edges.remove(hashCode(node1,node2));
            this.edges.remove(hashCode(node2,node1));
            this.neighbors.get(node1).remove(node2);
            this.neighbors.get(node2).remove(node1);
            ModeCount++;
        }
   }

    /**
     * This method return the number of the vertices in this weighted graph
     * Complexity-O(1)
     * @return number of vertices
     */
    @Override
    public int nodeSize() {
        return this.vertices.size();
    }

    /**
     * This method return the number of edges in this weighted graph
     * Complexity-O(1)
     * @return number of edges
     */
    @Override
    public int edgeSize() {
        return this.edges.size();
    }

    /**
     * This method return the Mode Count
     *Complexity-O(1)
     * @return
     */
    @Override
    public int getMC() {
        return this.ModeCount;
    }

    public HashMap<Integer, Double> getEdges() {
        return this.edges;
    }

    public HashMap<Integer, HashMap<Integer, node_info>> getNeighbors() {
        return this.neighbors;
    }

    public HashMap<Integer, node_info> getVertices() {
        return this.vertices;
    }

    /**
     * node_info equals,generate by java , i did a few changes
     * @param node_key
     * @param o
     * @return true if node1 equal to o
     */
    public boolean equals(int node_key, Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeInfo)) return false;
        NodeInfo nodeInfo = (NodeInfo) o;
        return node_key == nodeInfo._key &&
                Double.compare(nodeInfo._tag, this.vertices.get(node_key).getTag()) == 0 &&
                Objects.equals(this.vertices.get(node_key).getInfo(), nodeInfo._info);
    }

    /**
     * this method compare two weighted graphs.
     * @param o
     * @return true if this graph is equal to o
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WGraph_DS)) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        if(this.ModeCount!=((WGraph_DS) o).ModeCount||this.nodeSize()!=((WGraph_DS) o).nodeSize()||this.edgeSize()!=((WGraph_DS) o).edgeSize())return false;
        for (node_info n : this.vertices.values()) {
            if (!((WGraph_DS) o).vertices.containsKey(n.getKey())) return false;
        }
        Iterator<node_info> it1=this.getV().iterator();
        while(it1.hasNext()) {
            node_info pointer = it1.next();
            Iterator<node_info> it2 = this.getV(pointer.getKey()).iterator();
            while (it2.hasNext()) {
                if (!((WGraph_DS) o).neighbors.get(pointer.getKey()).containsKey(it2.next().getKey())) return false;
            }
        }
        return true;
    }

    /**
     * hashcode of two Integers , node1 key and node2 key
     * @param n1
     * @param n2
     * @return hashcode of two keys
     */
    public int hashCode(int n1, int n2) {
        return Objects.hash(n1, n2);
    }


}
