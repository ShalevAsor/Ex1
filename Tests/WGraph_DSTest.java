import ex1.node_info;
import ex1.*;
import ex1.weighted_graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ex1.WGraph_DS;
import java.util.Iterator;

class WGraph_DSTest {

    public weighted_graph graphCreator() {
        weighted_graph graph = new WGraph_DS();
        int i = 1, size = 5;
        while (i<=size) {
         graph.addNode(i);
         i++;
        }
        return graph;
    }

    @Test
    public void getNodeaddNodeTest(){
        weighted_graph g1=this.graphCreator();
        g1.addNode(6);
        Assertions.assertEquals(6,g1.getNode(6).getKey());
        Assertions.assertEquals(6,g1.nodeSize());

    }
    @Test
    public void connectTest(){
        weighted_graph g2=this.graphCreator();
        g2.connect(1,2,2.5);
        Assertions.assertTrue(g2.hasEdge(1,2));
        Assertions.assertTrue(g2.hasEdge(2,1));
        Assertions.assertFalse(g2.hasEdge(1,3));
        Assertions.assertFalse(g2.hasEdge(2,156));
    }
    @Test
    public void hasEdgeTest(){
        weighted_graph g3=this.graphCreator();
        g3.connect(1,2,5);
        g3.connect(1,7,5);
        g3.connect(2,4,0);
        g3.connect(4,5,0.1);
        Assertions.assertTrue(g3.hasEdge(1,2));
        Assertions.assertFalse(g3.hasEdge(1,7));
        Assertions.assertTrue(g3.hasEdge(2,4));
        Assertions.assertTrue(g3.hasEdge(4,5));
        Assertions.assertFalse(g3.hasEdge(1,8));
    }
    @Test
    public void getEdgeTest(){
        weighted_graph g4=this.graphCreator();
        g4.connect(1,2,5);
        g4.connect(1,3,8.6);
        g4.connect(2,4,6);
        g4.connect(1,4,0);
        g4.connect(3,5,2);
        g4.connect(3,8,2);
        Assertions.assertEquals(5,g4.getEdge(1,2));
        Assertions.assertEquals(8.6,g4.getEdge(1,3));
        Assertions.assertEquals(6,g4.getEdge(2,4));
        Assertions.assertEquals(6,g4.getEdge(4,2));
        Assertions.assertEquals(0,g4.getEdge(4,1));
        Assertions.assertEquals(0,g4.getEdge(1,4));
        Assertions.assertEquals(2,g4.getEdge(5,3));
        Assertions.assertEquals(-1,g4.getEdge(8,3));
        g4.addNode(12);
        g4.addNode(13);
        g4.connect(12,13,0.01);
        Assertions.assertEquals(0.01,g4.getEdge(12,13));
        Assertions.assertEquals(0.01,g4.getEdge(13,12));
    }

   @Test
    public void getVTest1(){
        WGraph_DS g5=(WGraph_DS)this.graphCreator();
        Assertions.assertEquals(5,g5.getV().size());
       Iterator<node_info> it=g5.getV().iterator();
       while(it.hasNext()){
           node_info pointer=it.next();
           Assertions.assertTrue(g5.equals(pointer.getKey(),pointer));
       }
   }
   @Test
    public void getVTest2(){
       WGraph_DS g6=(WGraph_DS)this.graphCreator();
       g6.connect(1,2,1);
       g6.connect(1,3,5);
       g6.connect(1,6,2);
       g6.connect(1,3,0);
       g6.connect(1,5,2);
       g6.connect(1,4,0);
       Assertions.assertEquals(4,g6.getV(1).size());
       Iterator<node_info> it= g6.getV(1).iterator();
       node_info pointer = g6.getNode(1);
       while(it.hasNext()){
           pointer=it.next();
           if(pointer.getKey()==3){
               break;
           }
       }
       Assertions.assertTrue(g6.equals(3,pointer));

   }
   @Test
   public void removeNodeTest(){
       WGraph_DS g7=(WGraph_DS)this.graphCreator();
   Assertions.assertEquals(5,g7.getV().size());
   g7.removeNode(5);
   Assertions.assertEquals(4,g7.getV().size());
   g7.removeNode(1);
   Assertions.assertEquals(3,g7.getV().size());
   g7.removeNode(1);
   Assertions.assertEquals(3,g7.getV().size());
   g7.connect(2,3,1);
   g7.connect(2,4,1);
   g7.connect(3,4,1);
   Assertions.assertEquals(2,g7.getV(2).size());
   g7.removeNode(4);
   Assertions.assertEquals(1,g7.getV(2).size());
   g7.removeNode(3);
   Assertions.assertEquals(0,g7.getV(2).size());
   Assertions.assertEquals(0,g7.edgeSize());
   g7.connect(2,2,1);
   Assertions.assertEquals(0,g7.edgeSize());
   }
 @Test
    public void removeEdgeTest(){
     WGraph_DS g8=(WGraph_DS)this.graphCreator();
     g8.removeEdge(16,2);
     g8.connect(1,2,1);
     g8.connect(1,3,1);
     g8.connect(1,4,1);
     g8.removeEdge(1,2);
     Assertions.assertEquals(2,g8.getV(1).size());
     Assertions.assertEquals(0,g8.getV(2).size());
     g8.removeEdge(4,5);
     g8.connect(2,1,2);
     g8.connect(2,4,2);
     g8.removeEdge(2,3);
     Assertions.assertEquals(2,g8.getV(2).size());
     Assertions.assertEquals(2,g8.getV(4).size());
     g8.removeEdge(4,2);
     g8.removeEdge(1,2);
     g8.removeEdge(1,4);
     g8.removeEdge(1,3);
     Assertions.assertEquals(0,g8.edgeSize());
     Iterator<node_info> it =g8.getV().iterator();
     while(it.hasNext()) {
         Assertions.assertEquals(0, g8.getV(it.next().getKey()).size());
     }

 }
  @Test
    public void nodeSizeEdgeSizeTest(){
        weighted_graph g9=this.graphCreator();
        Assertions.assertEquals(5,g9.nodeSize());
        g9.removeNode(5);
        Assertions.assertEquals(4,g9.nodeSize());
        Assertions.assertEquals(0,g9.edgeSize());
        g9.connect(1,2,1);
        g9.connect(1,3,1);
        g9.connect(1,4,1);
        Assertions.assertEquals(3,g9.edgeSize());
        g9.connect(1,2,5);
        Assertions.assertEquals(3,g9.edgeSize());
        g9.removeNode(1);
        Assertions.assertEquals(0,g9.edgeSize());

    }
    @Test
    public void ModeCounteTest(){
        weighted_graph g10=this.graphCreator();
        Assertions.assertEquals(5,g10.getMC());
        g10.connect(1,2,1);
        Assertions.assertEquals(6,g10.getMC());
        g10.connect(1,1,1);
        Assertions.assertEquals(6,g10.getMC());
        g10.removeEdge(1,1);
        Assertions.assertEquals(6,g10.getMC());
        g10.connect(2,3,1);
        Assertions.assertEquals(7,g10.getMC());
        g10.connect(2,4,2);
        g10.connect(2,5,1);
        g10.removeNode(2);
        Assertions.assertEquals(14,g10.getMC());


    }

}