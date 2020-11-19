package ex1.src;

import java.io.*;
import java.util.*;

/**
 * This class represent weighted graph algorithms, it support basic methods like init,copy and getGraph.
 * it also support two algorithms : BFS and Dijkstra's algorithm that used for methods like isConnected
 * and shortestPath.
 * each method in this class is attached with explanations.
 */

public class WGraph_Algo implements weighted_graph_algorithms,java.io.Serializable {
    private weighted_graph graph = new WGraph_DS();

    //-------------------subNode class------------------//

    /**
     * This class used in Dijkstra's Algorithm
     */
    private static class subNode implements Comparable<subNode>{
        private double _weight = Double.POSITIVE_INFINITY;
        private final int _parent;//represent the node thats this subNode came from
        private final int _currentKey;

        public subNode(double weight, int parent, int key) {
            this._parent = parent;
            this._weight = weight;
            this._currentKey = key;
        }
        public subNode(int parentKey ,int key) {
            this._currentKey = key;
            this._parent=parentKey;
        }
        public void setWeight(double w) {
            this._weight = w;
        }

        public double getWeight() {
            return this._weight;
        }

        public int getParent() {
            return this._parent;
        }

        public int getCurrentKey() {
            return this._currentKey;
        }
        @Override
        public int compareTo(subNode o) {
            int ans=0;
            if(this._weight>o.getWeight()){
                ans=1;
            }
            else if(this._weight<o.getWeight()){
                ans=-1;
            }
            return ans;
        }
    }
//--------------------------WGraph_Algo-------------------------------//
    /**
     * This method initialization this graph to given graph
     * Complexity-O(1)
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        this.graph = g;
    }

    /**
     * This method return the weighted graph that this class works on
     * Complexity-O(1)
     * @return this class graph
     */
    @Override
    public weighted_graph getGraph() {
        return this.graph;
    }

    /**
     * This method compute a deep copy of this weighted graph
     *Complexity-if n is the sum of the vertices and V is the sum of the edges -->O(v*n)
     * @return copy of this graph
     */
    @Override
    public weighted_graph copy() {
        WGraph_DS copy = new WGraph_DS();
        Iterator<node_info> it1 = this.graph.getV().iterator();
        while (it1.hasNext()) {
            node_info pointer = it1.next();//pointer to this graph vertices
            copy.addNode(pointer.getKey());//add vertex to copy
            copy.getVertices().get(pointer.getKey()).setTag(pointer.getTag());//set the original tag of this node
            copy.getVertices().get(pointer.getKey()).setInfo(pointer.getInfo());//set the original info of this node
        }
        Iterator<node_info> it2 = this.graph.getV().iterator();
        while (it2.hasNext()) {
            node_info pointer2 = it2.next();
            Iterator<node_info> it3 = this.graph.getV(pointer2.getKey()).iterator();//pointer2 neighbors
            while (it3.hasNext()) {
                node_info pointer3 = it3.next();//
                double weight = this.graph.getEdge(pointer2.getKey(), pointer3.getKey());//get the original weight
                copy.connect(pointer2.getKey(), pointer3.getKey(), weight);//connect on the copy.
            }
        }
        return copy;
    }

    /**
     * This method return true if this weighted graph is connected , else return false.
     * this method based on Breadth first search algorithm
     * Complexity-if v is the sum of vertices in this graph and e is the sum of edges--> O(v+e)
     * @return true if connected else return false
     */
    @Override
    public boolean isConnected() {
        if (this.graph.nodeSize() == 0) return true;//empty graph is connected
        //if the list size that was returned from the Bfs=this graph size>>>this graph is connected
        return this.Bfs(this.graph.getV().iterator().next().getKey()).size() == this.graph.nodeSize();
    }

    /**
     * This method return the length of the shortest path from the source vertex to the destination vertex
     * this method based on Dijkstra's algorithm.
     * Complexity- O(v^2) (v=vertices)
     * @param src - start node
     * @param dest - end (target) node
     * @return the length of the shortest path
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        double ans=-1;
        if(this.graph.getNode(src)==null||this.graph.getNode(dest)==null){//if one of this nodes is not in this graph there is no path.
            return ans;
        }
        else{
        List<subNode> myList=this.Dijkstras(this.graph.getNode(src),this.graph.getNode(dest));
        if(myList==null){return -1;}//if the list is null there is no path
        int i=0,size=myList.size();
        while(i<size){//looking for dest weight
            if(dest==myList.get(i)._currentKey){
             ans=myList.get(i)._weight;
            }
            i++;
        }
        return ans;
    }}

    /**
     * This method return list represent the shortest path from the source vertex to the destination vertex
     * src--->node1---->node2----->...----->dest
     * this method based on Dijkstra's algorithm.
     * Complexity- O(v^2) (v=vertices)
     * @param src - start node
     * @param dest - end (target) node
     * @return the list represent the shortest path if none then return null
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if(this.graph.getNode(src)==null||this.graph.getNode(dest)==null)return null;//if on of this nodes are not in the graph there is no path
        List<node_info> myList=new LinkedList<node_info>();//create list that will return with the shortest path
        //myList.add(0,this.graph.getNode(src));//add the src node to the first index of the list
       List<subNode> list= this.Dijkstras(this.graph.getNode(src),this.graph.getNode(dest));//pointer to the list that returned from Dijkstras algo
        if(list==null){return null;}
       int i=0,size=list.size(),thereIsNoPath=0;//init the size

       subNode pointer=this.getSubNode(list,dest);//looking for the subNode with the dest key
        if(pointer==null){return null;}
       myList.add(this.graph.getNode(pointer._currentKey));
               while(pointer._currentKey!=src){
                   myList.add(this.graph.getNode(pointer.getParent()));
                   pointer=this.getSubNode(list, pointer.getParent());
                   if(thereIsNoPath==size-1){
                       return null;
                   }
                   thereIsNoPath++;
               }
        return this.reverseList(myList);
    }

    @Override
    public boolean save(String file) {
        boolean saved=false;
        try {
            FileOutputStream myFile = new FileOutputStream(file);
            ObjectOutputStream out=new ObjectOutputStream(myFile);
            out.writeObject(this.graph);
            out.close();
            myFile.close();
            return saved=true;
        }
        catch(IOException ex) {
            System.out.println("ioException catch");
            return saved;
            }
    }

    @Override
    public boolean load(String file) {
        boolean loaded=false;
        try {
            FileInputStream myFile = new FileInputStream(file);
            ObjectInputStream input = new ObjectInputStream(myFile);
            weighted_graph loadedGraph= (weighted_graph)input.readObject();

            myFile.close();
            input.close();
            this.graph=loadedGraph;
            loaded=true;
            return loaded;
        }
        catch(IOException | ClassNotFoundException ex){
            ex.printStackTrace();
            return loaded;
            }
    }

    //---------------BFS ALGO-------------//
    public List<node_info> Bfs(int start) {
        weighted_graph g = this.graph;
        Queue<node_info> queue = new LinkedList<node_info>();
        List<node_info> list = new ArrayList<node_info>();
        g.getNode(start).setInfo("grey");//-mark as "grey" (visited)
        g.getNode(start).setTag(0);//first node tag as 0
        queue.add(g.getNode(start));//add the start node to the queue
        list.add(g.getNode(start));//add the first node to the list
        while (!queue.isEmpty()) {
            Collection<node_info> listOfNei = g.getV(queue.peek().getKey());//pointer to start node neighbors
            Iterator<node_info> it = listOfNei.iterator();
            while (it.hasNext()) {
                node_info node = it.next();
                if (node.getInfo().equals("white")) {//if the first nei isnt visited
                    queue.add(node);//add him to the queue
                    list.add(node);//add him to the list
                    node.setInfo("grey");//mark him as visited
                    if (list.size() == 1) {//if this is the fist nei
                        node.setTag(1);
                    } else {
                        node.setTag(queue.peek().getTag() + 1);
                    }
                }
            }
            queue.remove();
        }
        //remark all the vertices in the graph for the next use in this function
        Iterator<node_info> it = this.graph.getV().iterator();
        while (it.hasNext()) {
            node_info pointer = it.next();
            pointer.setInfo("white");
            pointer.setTag(-1);
        }
        return list;
    }

    //---------------------Dijkstra's Algorithm-----------------------//
    public List<subNode> Dijkstras(node_info sNode, node_info dNode) {
        List<subNode> solutionList = new LinkedList<subNode>();
        HashSet<Integer> visited =new HashSet<>();//hashset of all the visited nodes keys
        PriorityQueue<subNode> myPriorityqueue = new PriorityQueue<subNode>();//this queue is gonna keep all the vertices in this graph
        ////set the tag of the src node to zero (the distance from node to himself is zero)
        myPriorityqueue.add(new subNode(0, sNode.getKey(), sNode.getKey()));//add the src subNode to the queue
        while (!myPriorityqueue.isEmpty()) {//as long as this queue is not empty(the meaning is there are still vertices in the graph that has not visited)
            subNode subNodePointer = myPriorityqueue.poll();// pointer to the subNode with the lowest weight
            if (subNodePointer.getCurrentKey() == dNode.getKey()) {//the shortest path was found
                solutionList.add(subNodePointer);
                return solutionList;
            }
            if (!visited.contains(subNodePointer._currentKey)) {
                visited.add(subNodePointer._currentKey);//mark him as visited
                solutionList.add(subNodePointer);//add him to the list
                Iterator<node_info> it = this.graph.getV(subNodePointer.getCurrentKey()).iterator();//iterate all his neighbors
                while (it.hasNext()) {
                    node_info neiPointer = it.next();//pointer to each of the subNodePointer neighbors
                    if (!visited.contains(neiPointer.getKey())) {//if the neighbor has not visited
                        subNode subNodeNeiPointer=new subNode(subNodePointer._currentKey,neiPointer.getKey());//crete a subNode for him
                        double weight = (this.graph.getEdge(subNodePointer._currentKey, neiPointer.getKey()))+subNodePointer._weight;//the weight of the node
                        if (weight<subNodeNeiPointer._weight){//if the path is shorter
                            subNodeNeiPointer.setWeight(weight);
                            myPriorityqueue.add(subNodeNeiPointer);
                        }
                    }
                }
            }
        }
        return solutionList;
    }

    //i used this method at shortestPath
    public subNode getSubNode(List<subNode> list,int key) {
        int i = 0, size = list.size();
        while (i < size) {//looking for the subNode with the dest key
            if (list.get(i)._currentKey == key) {
                return list.get(i);}
            i++;
        }
        return null;
    }
    //if this method input is (a3,a2,a1) the  output is (a1,a2,a3)
    public List<node_info> reverseList(List<node_info> list){
        List<node_info> reversed =new LinkedList<node_info>();
        int i=0,size=list.size(),indexOf=size-1;
        while(i<size){
            reversed.add(list.get(indexOf));
            indexOf--;
            i++;
        }
        return reversed;
    }


}
