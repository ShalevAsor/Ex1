import ex1.WGraph_Algo;
import ex1.weighted_graph;
import ex1.weighted_graph_algorithms;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class WGraph_AlgoTest {


    public weighted_graph_algorithms graphCreator() {
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
    @Test
    public void getGraphTest(){
        weighted_graph_algorithms g1=this.graphCreator();
        Assertions.assertEquals(5,g1.getGraph().nodeSize());
    }
  
}