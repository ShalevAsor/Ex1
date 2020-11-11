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
  
}