import ex1.WGraph_Algo;
import ex1.WGraph_DS;
import ex1.weighted_graph;
import ex1.weighted_graph_algorithms;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
    @Test
    public void initTest(){
        weighted_graph g=this.graphCreator2();
        WGraph_Algo g0=new WGraph_Algo();
        g0.init(g);
        Assertions.assertEquals(5,g0.getGraph().nodeSize());
        g.removeNode(5);
        Assertions.assertEquals(4,g0.getGraph().nodeSize());

    }
    @Test
    public void getGraphTest(){
        weighted_graph_algorithms g1=this.graphCreator1();
        Assertions.assertEquals(5,g1.getGraph().nodeSize());
        Assertions.assertEquals(0,g1.getGraph().edgeSize());

    }
  @Test //at the first part of this test i will check if the graph was copied with all of his vertices and his neighbors
  //at the second part i will test that it is a deep copy, i will remove an  vertex from the copy, and test if there is
  // any change in the original graph.
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
}