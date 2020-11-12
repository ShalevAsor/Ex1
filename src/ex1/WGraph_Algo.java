package ex1;
import ex1.WGraph_DS.*;

import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph graph = new WGraph_DS();

    //-------------------subNode class------------------//

    /**
     * This class used in Dijkstra's Algorithm
     */
    private class subNode implements Comparable<subNode>{
        private double _weight = Double.POSITIVE_INFINITY;
        private int _parent;//represent the node thats this subNode came from
        private final int _currentKey;
        boolean visited=false;

        public subNode(double weight, int parent, int key) {
            this._parent = parent;
            this._weight = weight;
            this._currentKey = key;

        }

        public void setWeight(double w) {
            this._weight = w;
        }

        public double getWeight() {
            return this._weight;
        }

        public void setParent(int parentKey) {
            this._parent = parentKey;
        }

        public int getParent() {
            return this._parent;
        }

        public int getCurrentKey() {
            return this._currentKey;
        }
        public boolean getVisited(){
            return this.visited;
        }
        public void setVisited(boolean v){
            this.visited=v;
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

    //------------------subNode comparator--------------//
    class subNodeComperator implements Comparator<subNode> {


        @Override
        public int compare(subNode o1, subNode o2) {
            if (o1._weight > o2._weight) {
                return 1;
            } else if (o2._weight > o1._weight) {
                return -1;
            } else {
                return 0;
            }
        }

        @Override
        public Comparator<subNode> reversed() {
            return null;
        }
    }


    /**
     * This method initialization this graph to given graph
     *
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        this.graph = g;
    }

    /**
     * This method return the weighted graph that this class works on
     *
     * @return this class graph
     */
    @Override
    public weighted_graph getGraph() {
        return this.graph;
    }

    /**
     * This method compute a deep copy of this weighted graph
     *
     * @return copy of this graph
     */
    @Override
    public weighted_graph copy() {
        WGraph_DS copy = new WGraph_DS();
        Iterator<node_info> it1 = this.graph.getV().iterator();
        while (it1.hasNext()) {
            node_info pointer = it1.next();
            copy.addNode(pointer.getKey());
            copy.getVertices().get(pointer.getKey()).setTag(pointer.getTag());
            copy.getVertices().get(pointer.getKey()).setInfo(pointer.getInfo());
        }
        Iterator<node_info> it2 = this.graph.getV().iterator();
        while (it2.hasNext()) {
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
        if (this.graph.nodeSize() == 0) return true;//empty graph is connected
        if (this.Bfs(this.graph.getV().iterator().next().getKey()).size() == this.graph.nodeSize()) {
            return true;
        }//if the list size thats returned from the Bfs=this graph size>>>its true
        else {
            return false;
        }
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        double ans=-1;
        List<subNode> myList=this.Dijkstras(this.graph.getNode(src),this.graph.getNode(dest));
        int i=0,size=myList.size();
        while(i<size){
            if(dest==myList.get(i)._currentKey){
             ans=myList.get(i)._weight;
            }
            i++;
        }
        return ans;
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

    //---------------BFS ALGO-------------//
    public List<node_info> Bfs(int start) {
        weighted_graph g = new WGraph_DS();
        g = this.graph;
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
                if (node.getInfo() == "white") {//if the first nei isnt visited
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
//    public void Dijkstras(node_info sNode, node_info dNode) {
//        Set<node_info> solutionSet = new HashSet<>();
//        Queue<subNode> pq = new PriorityQueue<>();
//        List<node_info> solutionList=new LinkedList<node_info>();
//
////        Set<node_info> =new HashSet<>();
//        PriorityQueue<subNode> myPriorityqueue = new PriorityQueue<subNode>(new subNodeComperator());//this queue is gonna keep all the vertices in this graph
//        //sNode.setTag(0);//set the tag of the src node to zero (the distance from node to himself is zero)
//        myPriorityqueue.add(new subNode(0, sNode.getKey(), sNode.getKey()));//add the src subNode to the queue
//        solutionSet.add(sNode);
//        while (!myPriorityqueue.isEmpty()) {//as long as this queue isnt empty(thats meant there is still vertices in the graph thats hasnt visited)
//            if (dNode.getKey() != myPriorityqueue.peek().getCurrentKey()) {
//                Iterator<node_info> it = this.graph.getV(myPriorityqueue.peek().getCurrentKey()).iterator();
//                while (it.hasNext()) {
//                    node_info pointer = it.next();
//                    int parentKey = myPriorityqueue.peek().getCurrentKey();
//                    double weight = this.graph.getEdge(parentKey, pointer.getKey()) + myPriorityqueue.peek()._weight;
//                    myPriorityqueue.add(new subNode(weight, parentKey, pointer.getKey()));
//                }
//                myPriorityqueue.poll();
//
//
//            }
//        }
//    }
//    public List<subNode> Dijkstras(node_info sNode, node_info dNode) {
//        Set<subNode> solution = new HashSet<>();
//        // Queue<subNode> pq = new PriorityQueue<>();
//        List<subNode> solutionList = new LinkedList<subNode>();
////        Set<node_info> =new HashSet<>();
//        PriorityQueue<subNode> myPriorityqueue = new PriorityQueue<subNode>(new subNodeComperator());//this queue is gonna keep all the vertices in this graph
//        //sNode.setTag(0);//set the tag of the src node to zero (the distance from node to himself is zero)
//        myPriorityqueue.add(new subNode(0, sNode.getKey(), sNode.getKey()));//add the src subNode to the queue
//        //solutionSet.add(sNode);
//        while (!myPriorityqueue.isEmpty()) {//as long as this queue isnt empty(thats meant there is still vertices in the graph thats hasnt visited)
//            subNode pointer = myPriorityqueue.poll();
//            if (pointer.getCurrentKey() == dNode.getKey()) {
//                solutionList.add(pointer);
//                break;
//            }
//          //  if (pointer.getVisited() == false) {
//                pointer.setVisited(true);
//                solutionList.add(pointer);
//                Iterator<node_info> it = this.graph.getV(pointer.getCurrentKey()).iterator();
//                while (it.hasNext()) {
//                    node_info pointer2 = it.next();
//                    if(pointer2.getKey()!= pointer._parent){
//                    int parentKey = pointer.getCurrentKey();
//                    double weight = (this.graph.getEdge(parentKey, pointer2.getKey())) + pointer._weight;
//                    myPriorityqueue.add(new subNode(weight, parentKey, pointer2.getKey()));
//                }//}
//            }
//        }
//        return solutionList;
//    }
    public List<subNode> Dijkstras(node_info sNode, node_info dNode) {
        double weight=-1;
        List<subNode> solutionList = new LinkedList<subNode>();//at this list will be all the weights of the subNode's
        PriorityQueue<subNode> myPriorityqueue = new PriorityQueue<subNode>(new subNodeComperator());//this queue is gonna keep all the vertices in this graph
        myPriorityqueue.add(new subNode(0, sNode.getKey(), sNode.getKey()));//add the src subNode to the queue
        while (!myPriorityqueue.isEmpty()) {//as long as this queue is not empty(that is mean there is still vertices in the graph that is not visited)
            subNode pointer = myPriorityqueue.poll();//pointer to the subNode that removed from the queue
            if (pointer.getCurrentKey() == dNode.getKey()) {//if this is the dest node so the shortest path was found
                solutionList.add(pointer);//add the subNode to the solution list and break
                break;
            }
            //  if (pointer.getVisited() == false) {
            pointer.setVisited(true);//mark this subNode as visited
            solutionList.add(pointer);
            Iterator<node_info> it = this.graph.getV(pointer.getCurrentKey()).iterator();//creating iterator to get all the pointer neighbors
            while (it.hasNext()) {//as long as the pointer have neighbors
                node_info pointer2 = it.next();//create pointer to the first neighbor
                if (pointer2.getKey() != pointer._parent) {//because pointer already visited
                    int parentKey = pointer.getCurrentKey();//pointer is the "parent" of pointer2
                    weight = ((this.graph.getEdge(parentKey, pointer2.getKey())) + (pointer._weight));//the sum of the path to pointer2
                    myPriorityqueue.add(new subNode(weight, parentKey, pointer2.getKey()));//add the new subNode to the queue
                }//}
            }
        }
        return solutionList;
    }



}
