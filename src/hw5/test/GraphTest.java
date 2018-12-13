package hw5.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import hw5.*;

public final class GraphTest {

	private String A = "A";
	private String B = "B";
	private String C = "C";
	private String nullNode = null;
	
	private Graph graph = new Graph();
	
	
    ///////////////////////////////////////////////////////////////////////////////////////
    ////	Constructor
    ///////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testGraphSizeWhenConstructor() {
		assertEquals(0, graph.numberOfNodes());
	}

	///////////////////////////////////////////////////////////////////////////////////////
	////	Methods
	///////////////////////////////////////////////////////////////////////////////////////
	
	// addNode method
	@Test
	public void testAddingANode() {
		assertTrue(graph.addNode(A));
	}
	
	@Test
	public void testAddingADucplicateNode() {
		graph.addNode(A);
		assertFalse(graph.addNode(A));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddingANull() {
		graph.addNode(nullNode);
	}
	
	// addEdge method
	@Test
	public void testAddingAnEdge() {
		graph.addNode(A);
		graph.addNode(B);
		assertTrue(graph.addEdge(A, B, "some Label"));
	}
	
	@Test
	public void testAddingADuplicateEdge() {
		graph.addNode(A);
		graph.addNode(B);
		graph.addEdge(A, B, "some Label");
		assertFalse(graph.addEdge(A, B, "some Label"));
	}
	
	@Test
	public void testAddingSameEdgeWithDifferentLabel() {
		graph.addNode(A);
		graph.addNode(B);
		graph.addEdge(A, B, "some Label");
		assertTrue(graph.addEdge(A, B, "different Label"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddingWhenSourceIsNotInGraph() {
		graph.addNode(B);
		graph.addEdge(A, B, "some Label");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddingWhenDestinationIsNotInGraph() {
		graph.addNode(A);
		graph.addEdge(A, B, "some Label");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddingWhenLabelIsNull() {
		graph.addNode(A);
		graph.addNode(B);
		graph.addEdge(A, B, null);
	}
	
	// numberOfNodes method
	@Test
	public void testNumberOfNodesWhenThereisOneNode() {
		graph.addNode(A);
		assertEquals(1, graph.numberOfNodes());
	}
	@Test
	public void testNumberOfNodesAfterAddingSameNode() {
		graph.addNode(A);
		graph.addNode(A);
		assertEquals(1, graph.numberOfNodes());
	}
	@Test
	public void testNumberOfNodesWhenThereareTwoNodes() {
		graph.addNode(A);
		graph.addNode(B);
		assertEquals(2, graph.numberOfNodes());
	}
	@Test
	public void testNumberOfNodesWhenThereareTwoNodesWithEdges() {
		graph.addNode(A);
		graph.addNode(B);
		graph.addEdge(A, B, "some Label");
		assertEquals(2, graph.numberOfNodes());
	}

	// removeEdge method
	
	@Test
	public void testDeletingOneEdge() {
		graph.addNode(A);
		graph.addNode(B);
		graph.addEdge(A, B, "some label");
		assertTrue(graph.removeEdge(A, B, "some label"));
	}
	
	@Test
	public void testDeletingOneEdgeThatDoesNotExist() {
		graph.addNode(A);
		graph.addNode(B);
		graph.addEdge(A, B, "some label");
		assertFalse(graph.removeEdge(A, B, "wrong label"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDeletingEdgeWhenSourceDoesNotExist() {
		graph.addNode(B);
		graph.removeEdge(A, B, "some label");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDeletingEdgeWhenDestinationDoesNotExist() {
		graph.addNode(A);
		graph.removeEdge(A, B, "some label");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testDeletingEdgeWhenLabelIsNull() {
		graph.addNode(A);
		graph.addNode(B);
		graph.addEdge(A, B, "some label");
		graph.removeEdge(A, B, null);
	}
	
	// getAllNodes method
	
	@Test
	public void testGetAllNodesWithNoNodes() {
		assertTrue(graph.getAllNodes().isEmpty());
	}
	
	@Test
	public void testGetAllNodesWithNodes() {
		graph.addNode(A);
		graph.addNode(B);
		graph.addNode(C);
		Set<String> set = graph.getAllNodes();
		assertTrue(set.contains(A));
		assertTrue(set.contains(B));
		assertTrue(set.contains(C));
		assertEquals(3, set.size());
	}
	
	@Test
	public void testGetAllNodesWithNodesAndSomeEdges() {
		graph.addNode(A);
		graph.addNode(B);
		graph.addNode(C);
		graph.addEdge(A, C, "some label");
		graph.addEdge(B, C, "some label");
		graph.addEdge(A, B, "some label");
		Set<String> set = graph.getAllNodes();
		assertTrue(set.contains(A));
		assertTrue(set.contains(B));
		assertTrue(set.contains(C));
		assertEquals(3, set.size());
	}
	
	// getChildren method
	
	@Test
	public void testGetChildrenForNodeWhichHasChildren() {
		graph.addNode(A);
		graph.addNode(B);
		graph.addNode(C);
		graph.addEdge(A, C, "some label");
		graph.addEdge(A, B, "some label");
		List<Edge> set = graph.getChildren(A);
		Edge firstEdge = new Edge(C,"some label");
		Edge secondEdge = new Edge(B,"some label");
		Edge thirdEdge = new Edge(B, "wrong Label");
		boolean firstEdgeisContained = false;
		boolean secondEdgeisContained = false;
		boolean thirdEdgeisContained = false;
		for(Edge edge : set) {
			if(firstEdge.equals(edge)) {
				firstEdgeisContained = true;
			}
			if(secondEdge.equals(edge)) {
				secondEdgeisContained = true;
			}
			if(thirdEdge.equals(edge)) {
				thirdEdgeisContained = true;
			}
		}
		assertTrue(firstEdgeisContained);
		assertTrue(secondEdgeisContained);
		assertFalse(thirdEdgeisContained);
	}
	
	@Test
	public void testGetChildrenForNodeWhichHasNoChildren() {
		graph.addNode(A);
		List<Edge> set = graph.getChildren(A);
		assertTrue(set.isEmpty());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetChildForNodeThatIsNotContained() {
		graph.getChildren(A);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetChildForNull() {
		graph.getChildren(nullNode);
	}
	
}