package hw5.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import hw5.*;

public final class EdgeTest {

	private Edge edge = new Edge("A", "some label");
	
    ///////////////////////////////////////////////////////////////////////////////////////
    ////	Constructor
    ///////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testNodeConstructor() {
		new Edge("node", "some label");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInsertingNullNodeConstructor() {
		new Edge(null, "some label");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInsertingNullLabelConstructor() {
		new Edge("A", null);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	Methods
	///////////////////////////////////////////////////////////////////////////////////////

	// Equals Method
	@Test
	public void testTwoSameNodesEquals() {
		assertTrue(edge.equals(new Edge("A", "some label")));
	}
	
	@Test
	public void testTwoDifferentNodesEquals() {
		assertFalse(edge.equals(new Edge("A", "wrong label")));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNodeWithNullEquals() {
		edge.equals(null);
	}
	
	// getNode Method
	@Test
	public void testGettingName() {
		assertEquals("A", edge.getNode());
	}
	
	// getLabel Method
	@Test
	public void testGettingLabel() {
		assertEquals("some label", edge.getLabel());
	}
}
