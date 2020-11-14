import ex1.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class WGraph_AlgoTest {


    public weighted_graph_algorithms graphCreator1() {
        weighted_graph graph = new WGraph_DS();
        int i = 1, size = 5;
        while (i<=size) {
            graph.addNode(i);
            i++;
        }
        weighted_graph_algorithms g=new WGraph_Algo();
        g.init(graph);
        return g;
    }
    public weighted_graph graphCreator2() {
        weighted_graph graph = new WGraph_DS();
        int i = 1, size = 5;
        while (i<=size) {
            graph.addNode(i);
            i++;
        }
        return graph;
    }
    /*
    At this test i will init g to g0 , i will remove node from g and make sure that the node removed
    from g0.
     */
    @Test
    public void initTest(){
        weighted_graph g=this.graphCreator2();
        WGraph_Algo g0=new WGraph_Algo();
        g0.init(g);
        Assertions.assertEquals(5,g0.getGraph().nodeSize());
        g.removeNode(5);
        Assertions.assertEquals(4,g0.getGraph().nodeSize());

    }
    /*
    At this test i will verify that the graph return from the method getGraph
    it is the correct graph
     */
    @Test
    public void getGraphTest(){
        weighted_graph_algorithms g1=this.graphCreator1();
        Assertions.assertEquals(5,g1.getGraph().nodeSize());
        Assertions.assertEquals(0,g1.getGraph().edgeSize());

    }
    /*
    At the first part of this test i will check if the graph was copied with all of his vertices and his neighbors
    at the second part i will test that it is a deep copy, i will remove an  vertex from the copy, and test if there is
    any change in the original graph.
     */
  @Test
    public void copyTest(){
        weighted_graph_algorithms g2=this.graphCreator1();
        weighted_graph g3=g2.copy();
        //part one
        Assertions.assertEquals(g2.getGraph().nodeSize(),g3.nodeSize());
        Assertions.assertEquals(g2.getGraph().edgeSize(),g3.edgeSize());
        int i=1,index =0,size=g3.nodeSize();
      while(index<size){
          Assertions.assertEquals(g3.getV(i).size(),g2.getGraph().getV(i).size());
          index++;
      }
      //part two
      g3.removeNode(1);
      Assertions.assertNotEquals(g3.nodeSize(),g2.getGraph().nodeSize());
      g3.addNode(6);
      g3.connect(2,3,1);
      g3.connect(4,6,1);
      Assertions.assertNotEquals(g3.getV(2).size(),g2.getGraph().getV(2).size());
      Assertions.assertNotEquals(g3.getV(6).size(),g2.getGraph().getV(6).size());


  }
  /*
  This test verify that g4 is connected or not with different cases
   */
  @Test
    public void isConnectedTest(){
        weighted_graph g4=new WGraph_DS();
      weighted_graph_algorithms tGraph=new WGraph_Algo();
        int i=1,size=500;
        while(i<=size){
        g4.addNode(i);
        i++;}
        int index=0,numOfNodes=g4.nodeSize();
        while(index<numOfNodes-1){
            g4.connect(index+1,index+2,1);
            index++;
        }
       tGraph.init(g4);

        Assertions.assertTrue(tGraph.isConnected());//basic connected graph with 500 nodes 499 edges
      weighted_graph g5=new WGraph_DS();
      int i2=150,size2=50150;//graph with 50000 nodes
      g5.addNode(149);
      while(i2<=size2){
          g5.addNode(i2);
          g5.connect(i2,i2-1,1);
          i2++;
      }
      i2=150;
      while(i2<size2) {
          g4.connect(i2, i2 + 4, 2);
          i2 += 3;
      }
      Assertions.assertTrue(tGraph.isConnected());
      tGraph.getGraph().removeEdge(149,150);
      Assertions.assertFalse(tGraph.isConnected());
  }
  /*
  Test for shortestPathDist, make sure this method return the correct distance in a different cases
   */
  @Test
    public void shortestPathDistTest(){
    weighted_graph g6=new WGraph_DS();
    weighted_graph_algorithms g7=new WGraph_Algo();
    g7.init(g6);
    g6.addNode(1);
    g6.addNode(2);
    g6.addNode(3);
    g6.connect(1,2,1);
    g6.connect(1,3,8);
    g6.connect(2,3,2);
    Assertions.assertEquals(3,g7.shortestPathDist(1,3));//simple test the sortestPathDist:1-->2-->3(0+1+2=3)
      Assertions.assertEquals(3,g7.shortestPathDist(3,1));//this should return the same dist
    g6.addNode(4);
    g6.addNode(5);
    g6.connect(4,5,0.1);
    Assertions.assertEquals(3,g7.shortestPathDist(1,3));//the shortest path between (1,3) is still 3
    g6.connect(2,4,0.3);
    g6.connect(5,3,0.5);
    Assertions.assertEquals(1.9,g7.shortestPathDist(1,3),0.00000001);//shortest path is:1-->2-->4-->5-->3
      Assertions.assertEquals(1.9,g7.shortestPathDist(3,1),0.00000001);//this should return the same dist
    g6.removeEdge(1,2);
    g6.removeEdge(1,3);
    Assertions.assertEquals(-1,g7.shortestPathDist(1,3));//there is no path between (1,3)
    Assertions.assertEquals(-1,g7.shortestPathDist(1,18));//18 is not in this graph
      Assertions.assertEquals(-1,g7.shortestPathDist(18,1));//should return the same -1
      Assertions.assertEquals(0,g7.shortestPathDist(1,1));//the shortestPathDist between (1,1) is 0

}
/*
   Test for shortestPath, make sure this method return the correct list in a different cases
 */
  @Test
    public void shortestPathTest(){
        weighted_graph_algorithms g8=this.graphCreator1();
        g8.getGraph().connect(1,2,1);
      g8.getGraph().connect(1,3,0.5);
      g8.getGraph().connect(3,5,10);
      g8.getGraph().connect(2,4,2);
      g8.getGraph().connect(4,5,1);
      List<node_info> myList=new LinkedList<>();
      myList.add(0,g8.getGraph().getNode(1));
      myList.add(1,g8.getGraph().getNode(2));
      myList.add(2,g8.getGraph().getNode(4));
      myList.add(3,g8.getGraph().getNode(5));
     // List<node_info> list=g8.shortestPath(1,5);
      Assertions.assertTrue(myList.equals(g8.shortestPath(1,5)));//the shortest path should be:1-->2-->4-->5
      weighted_graph g9=this.graphCreator2();
      g9.connect(1,2,0.1);
      g9.connect(2,3,0.2);
      g9.connect(3,4,14);
      g9.connect(4,5,2.5);
      g9.connect(2,4,0.3);
      g8.init(g9);
      myList.clear();
      myList.add(0,g9.getNode(1));
      myList.add(1,g9.getNode(2));
      myList.add(2,g9.getNode(4));
      myList.add(3,g9.getNode(5));
     /// System.out.println(g8.shortestPath(1,5).size());
      Assertions.assertTrue(myList.equals(g8.shortestPath(1,5)));//the shortest path should be:1-->2-->4-->5
      g9.removeEdge(4,5);
      Assertions.assertNull(g8.shortestPath(1,5));//should return null because there is no path between (1,5)
      weighted_graph g10=new WGraph_DS();
      g10.addNode(1);
      g10.addNode(2);
      g8.init(g10);
      Assertions.assertNull(g8.shortestPath(1,2));//should return null because there is no path between (1,2)
      Assertions.assertNull(g8.shortestPath(1,66));//node66 is not in the graph then this method should return null
      myList.clear();
      myList.add(0,g10.getNode(1));
      Assertions.assertTrue(myList.equals(g8.shortestPath(1,1)));//the shortest path between (1,1) is: 1

  }
  @Test
    public void comprehensiveTest(){
        List<node_info> mylist=new LinkedList<>();
        weighted_graph_algorithms graph=this.graphCreator1();
        graph.getGraph().connect(1,2,1);
        Assertions.assertEquals(1,graph.getGraph().edgeSize());
        Assertions.assertEquals(6,graph.getGraph().getMC());
        graph.getGraph().removeEdge(1,2);
      Assertions.assertEquals(0,graph.getGraph().edgeSize());
      Assertions.assertEquals(7,graph.getGraph().getMC());
        graph.getGraph().connect(1,2,1);
        graph.getGraph().connect(1,3,2);
        Assertions.assertFalse(graph.isConnected());
        Assertions.assertEquals(-1,graph.shortestPathDist(1,5));
        Assertions.assertNull(graph.shortestPath(1,5));
        graph.getGraph().connect(2,4,10.5);
        graph.getGraph().connect(3,4,3);
        Assertions.assertEquals(5,graph.shortestPathDist(1,4));
        mylist.add(graph.getGraph().getNode(1));
      mylist.add(graph.getGraph().getNode(3));
      mylist.add(graph.getGraph().getNode(4));
      Assertions.assertTrue(mylist.equals(graph.shortestPath(1,4)));
      Assertions.assertFalse(graph.isConnected());
      graph.getGraph().connect(4,5,2);
      Assertions.assertTrue(graph.isConnected());
      graph.getGraph().removeNode(4);
      Assertions.assertEquals(0,graph.getGraph().getV(5).size());
      Assertions.assertEquals(0,graph.getGraph().getEdge(1,1));

  }
}