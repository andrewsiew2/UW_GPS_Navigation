package hw5.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import hw5.*;

public final class NodeTest {

	private Node A = new Node("A");
	private Node nullNode = null;
	
    ///////////////////////////////////////////////////////////////////////////////////////
    ////	Constructor
    ///////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testNodeConstructor() {
		new Node("node");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInsertingNullNodeConstructor() {
		new Node(null);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	Methods
	///////////////////////////////////////////////////////////////////////////////////////

	// Equals Method
	@Test
	public void testTwoSameNodesEquals() {
		assertTrue(A.equals(new Node("A")));
	}
	
	@Test
	public void testTwoDifferentNodesEquals() {
		assertFalse(A.equals(new Node("B")));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNodeWithNullEquals() {
		A.equals(nullNode);
	}
	
	// getName Method
	@Test
	public void testGettingName() {
		assertEquals("A", A.getName());
	}
	
	// toString Method
	public void testStringRepresentationOfNode() {
		assertEquals("(A)", A.toString());
	}
	
}
