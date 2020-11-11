package ex1;
import ex1.WGraph_DS.*;

import java.util.*;

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
        if(this.graph.nodeSize()==0)return true;//empty graph is connected
        if(this.Bfs(this.graph.getV().iterator().next().getKey()).size()==this.graph.nodeSize()){
            return true;}//if the list size thats returned from the Bfs=this graph size>>>its true
        else{
            return false;}
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
    //---------------BFS ALGO-------------//
    public List<node_info> Bfs(int start){
        weighted_graph g=new WGraph_DS();
        g=this.graph;
        Queue<node_info> queue=new LinkedList<node_info>();
        List<node_info> list=new ArrayList<node_info>();
        g.getNode(start).setInfo("grey");//-mark as "grey" (visited)
        g.getNode(start).setTag(0);//first node tag as 0
        queue.add(g.getNode(start));//add the start node to the queue
        list.add(g.getNode(start));//add the first node to the list
        while(!queue.isEmpty()){
            Collection<node_info> listOfNei=g.getV(queue.peek().getKey());//pointer to start node neighbors
            Iterator<node_info> it=listOfNei.iterator();
            while(it.hasNext()){
                node_info node=it.next();
                if(node.getInfo()=="white"){//if the first nei isnt visited
                    queue.add(node);//add him to the queue
                    list.add(node);//add him to the list
                    node.setInfo("grey");//mark him as visited
                    if(list.size()==1){//if this is the fist nei
                        node.setTag(1);
                    }
                    else{
                        node.setTag(queue.peek().getTag()+1);
                    }
                }
            }
            queue.remove();
        }
        //remark all the vertices in the graph for the next use in this function
        Iterator<node_info> it= this.graph.getV().iterator();
        while(it.hasNext()){
            node_info pointer=it.next();
            pointer.setInfo("white");
            pointer.setTag(-1);
        }
        return list;
    }
}
