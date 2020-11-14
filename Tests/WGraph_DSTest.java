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
/*
 This test verify getNode and addNode are working
 */
    @Test
    public void getNodeaddNodeTest(){
        weighted_graph g1=this.graphCreator();
        g1.addNode(6);
        Assertions.assertEquals(6,g1.getNode(6).getKey());//add the node successfully
        g1.getNode(6).setTag(12);//setting the node6 tag
        Assertions.assertEquals(12,g1.getNode(6).getTag());//set successfully
        g1.addNode(6);//trying to add new node with the key 6. (should not allow this)
        Assertions.assertEquals(12,g1.getNode(6).getTag());//this is still the original node with the key 6
        Assertions.assertEquals(6,g1.nodeSize());//there is 6 vertices in this graph

    }
    /*
    At this test i will verify that the connect is not connecting node that is not in this graph
    and connect (2,1) (for example) is equal to connect (1,2) .
     */
    @Test
    public void connectTest(){
        weighted_graph g2=this.graphCreator();
        g2.connect(1,2,2.5);
        Assertions.assertTrue(g2.hasEdge(1,2));//connect successfully
        Assertions.assertTrue(g2.hasEdge(2,1));//connect successfully
        Assertions.assertFalse(g2.hasEdge(1,3));//should be false because i did not connect 1 with 3
        Assertions.assertFalse(g2.hasEdge(2,156));//should be false because 156 is not in the graph
        g2.connect(2,156,2);//try to connect node2 with node156 that is not in this graph
        Assertions.assertFalse(g2.hasEdge(2,156));//should be false because 156 is not in the graph
        Assertions.assertFalse(g2.hasEdge(156,2));//should be false as well
        Assertions.assertEquals(2.5,g2.getEdge(1,2));//the weight should be 2.5
        g2.connect(1,2,2.6);//change the weight of the edge from 2.5 to 2.6
        Assertions.assertEquals(2.6,g2.getEdge(1,2));//the weight should be 2.6
    }
    /*
    At this test i will verify that the method haseEdge return the correct boolean condition

     */
    @Test
    public void hasEdgeTest(){
        weighted_graph g3=this.graphCreator();
        g3.connect(1,2,5);
        g3.connect(1,7,5);
        g3.connect(2,4,0);
        g3.connect(4,5,0.1);
        Assertions.assertTrue(g3.hasEdge(1,2));//there is edge between 1,2
        Assertions.assertFalse(g3.hasEdge(1,7));//there is edge between 1,7 because 7 is not in the graph
        Assertions.assertTrue(g3.hasEdge(2,4));//weight 0 is ok
        Assertions.assertTrue(g3.hasEdge(4,5));////there is edge between 4,5
        Assertions.assertFalse(g3.hasEdge(1,8));//8 is not in this graph

    }
    /*
    At this test i will verify that the weight of the edge returned have the correct weight

     */
    @Test
    public void getEdgeTest(){
        weighted_graph g4=this.graphCreator();
        g4.connect(1,2,5);
        g4.connect(1,3,8.6);
        g4.connect(2,4,6);
        g4.connect(1,4,0);
        g4.connect(3,5,2);
        g4.connect(3,8,2);
        Assertions.assertEquals(5,g4.getEdge(1,2));//1,2 is in this graph and the weight is 5
        Assertions.assertEquals(8.6,g4.getEdge(1,3));//1,3 is in this graph and the weight is 8.6
        Assertions.assertEquals(6,g4.getEdge(2,4));//2,4 is in this graph and the weight is 6
        Assertions.assertEquals(6,g4.getEdge(4,2));//2,4 and 4,2 should be equals
        Assertions.assertEquals(0,g4.getEdge(4,1));//weight 0 is ok
        Assertions.assertEquals(0,g4.getEdge(1,4));//1,4 edge is equal to 4,1 edge
        Assertions.assertEquals(2,g4.getEdge(5,3));//the edge weight of 5,3 is 2
        g4.removeEdge(3,5);
        Assertions.assertEquals(-1,g4.getEdge(5,3));//there is no edge between 5,3
        Assertions.assertEquals(-1,g4.getEdge(8,3));//8 is not in this graph
        g4.addNode(12);
        g4.addNode(13);
        g4.connect(12,13,0.01);
        Assertions.assertEquals(0.01,g4.getEdge(12,13));
        Assertions.assertEquals(0.01,g4.getEdge(13,12));
        Assertions.assertEquals(0,g4.getEdge(1,1));//the weight of the edge from node1 to node1 is 0
    }
   /*
   This test verify that getV return all the vertices in this graph
    */
   @Test
    public void getVTest1(){
        WGraph_DS g5=(WGraph_DS)this.graphCreator();
        Assertions.assertEquals(5,g5.getV().size());
       Iterator<node_info> it=g5.getV().iterator();
       while(it.hasNext()){
           node_info pointer=it.next();
           Assertions.assertTrue(g5.equals(pointer.getKey(),pointer));//verify every vertex in this graph equal to getV vertex
       }
   }
   /*
   At this test i will compare each of node1 neighbors to the getV Collection
    */
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
   /*
   Test that every node was removed
    */
   @Test
   public void removeNodeTest(){
       WGraph_DS g7=(WGraph_DS)this.graphCreator();
   Assertions.assertEquals(5,g7.getV().size());
   Assertions.assertTrue(g7.equals(5,g7.getNode(5)));//node5 from getNode and node5 from the graph are equals
   g7.removeNode(5);
   Assertions.assertNull(g7.getNode(5));//node5 is not in the graph so getNode returned null
   Assertions.assertEquals(4,g7.getV().size());//the size of this graph is 4 (was 5 before the remove)
   g7.removeNode(1);//remove node1
   Assertions.assertEquals(3,g7.getV().size());//the size of the graph is 3
   g7.removeNode(1);//node 1 is not in this graph anymore
   Assertions.assertEquals(3,g7.getV().size());//nothing has changed
   g7.connect(2,3,1);
   g7.connect(2,4,1);
   g7.connect(3,4,1);
   Assertions.assertEquals(2,g7.getV(2).size());//node 2 have two neighbors (3,4)
   g7.removeNode(4);
   Assertions.assertEquals(1,g7.getV(2).size());//after node4 removed from this graph node 2 have 1 neighbor
   g7.removeNode(3);
   Assertions.assertEquals(0,g7.getV(2).size());//after node3 removed from this graph node 2 have 0 neighbors
   Assertions.assertEquals(0,g7.edgeSize());//there is no edge in this graph (after 3,4 removed)
   g7.connect(2,2,1);
   Assertions.assertEquals(0,g7.edgeSize());
   }
   /*
   At this test i will verify that the method removeEdge is removing the correct edge and more.
    */
 @Test
    public void removeEdgeTest(){
     WGraph_DS g8=(WGraph_DS)this.graphCreator();
     g8.connect(1,2,1);
     g8.connect(1,3,1);
     g8.connect(1,4,1);
     g8.removeEdge(1,2);
     Assertions.assertEquals(2,g8.getV(1).size());//after the edge between node1 and node2 removed node 1 have 2 neighbors(was 3)
     Assertions.assertEquals(0,g8.getV(2).size());//node 2 have zero neighbors
     g8.removeEdge(4,5);//node4 and node5 have no edge, so this method dont do anything
     g8.connect(2,1,2);
     g8.connect(2,4,2);
     g8.removeEdge(2,3);//node2 and node3 have no edge, so this method dont do anything
     Assertions.assertEquals(2,g8.getV(2).size());//node2 have two neighbors
     Assertions.assertEquals(2,g8.getV(4).size());//node4 have two negihbors
     g8.removeEdge(4,2);
     g8.removeEdge(1,2);
     g8.removeEdge(1,4);
     g8.removeEdge(1,3);
     Assertions.assertEquals(0,g8.edgeSize());//after removing all the neighbors from this graph this edgeSize should be zero
     Iterator<node_info> it =g8.getV().iterator();
     while(it.hasNext()) {
         Assertions.assertEquals(0, g8.getV(it.next().getKey()).size());
     }

 }
 /*
 Test the method's edgeSize and nodeSize
  */
  @Test
    public void nodeSizeEdgeSizeTest(){
        weighted_graph g9=this.graphCreator();
        Assertions.assertEquals(5,g9.nodeSize());//g9 start with 5 nodes
        g9.removeNode(5);
        Assertions.assertEquals(4,g9.nodeSize());//after removing node5 g9 have 4 nodes
        Assertions.assertEquals(0,g9.edgeSize());//g9 dont have any edge
        g9.connect(1,2,1);
        g9.connect(1,3,1);
        g9.connect(1,4,1);
        Assertions.assertEquals(3,g9.edgeSize());//g9 have 3 edges (node1 have 3 neighbors)
        g9.connect(1,2,5);
        Assertions.assertEquals(3,g9.edgeSize());//the weight of the edge between node1 and node2 has changed but there is still 3 edges
        g9.removeNode(1);
        Assertions.assertEquals(0,g9.edgeSize());//node 1 was removed and now there is no edges in this graph
        Assertions.assertEquals(3,g9.nodeSize());// g9 have 3 nodes

    }
    @Test
    public void ModeCounteTest(){
        weighted_graph g10=this.graphCreator();
        Assertions.assertEquals(5,g10.getMC());//g10 have 5 nodes so the MC is 5
        g10.connect(1,2,1);
        Assertions.assertEquals(6,g10.getMC());//after connecting node1 and node2 the MC should be 6
        g10.connect(1,1,1);//connect node 1 to node 1 with weight 1(not allowed in this weightd graph)
        Assertions.assertEquals(6,g10.getMC());//the MC shuold stay 6
        g10.removeEdge(1,1);//removed the "edge"
        Assertions.assertEquals(6,g10.getMC());//the MC should stay 6 because there is no edge between node1 to node1 with the weight 1
        g10.connect(2,3,1);
        Assertions.assertEquals(7,g10.getMC());//node2 and node3 was connected then the MC should be 7
        g10.connect(2,3,2);
        Assertions.assertEquals(8,g10.getMC());//node2 and node3 was reconnected then the MC should be 8
        g10.connect(2,4,2);
        g10.connect(2,5,1);
        Assertions.assertEquals(10,g10.getMC());//after node2 was connected to node4 and node 5 the MC should be 10
        g10.removeNode(2);
        Assertions.assertEquals(15,g10.getMC());//node 2 have 4 neighbors so the MC should be 10+4 and +1 to remove node2


    }

}