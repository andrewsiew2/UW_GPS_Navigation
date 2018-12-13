package hw8.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import hw8.Coordinate;

public class CoordinateTest {
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	Constructor
	///////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstructor() {
		new Coordinate(0.0, 0.0);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	Methods
	///////////////////////////////////////////////////////////////////////////////////////
	
	//getX method
	@Test
	public void testGetX() {
		Coordinate test = new Coordinate(3.56, 5.23);
		assertTrue(test.getX() == 3.56);
	}
	
	//getY method
	@Test
	public void testGetY() {
		Coordinate test = new Coordinate(3.56, 5.23);
		assertTrue(test.getY() == 5.23);
	}
	
	//hashCode method
	@Test
	public void testhashCode() {
		Double x = 3.56;
		Double y = 5.23;
		Coordinate test = new Coordinate(3.56, 5.23);
		assertTrue(test.hashCode() == (x.hashCode() + y.hashCode()));
	}
	
	//hashCode method
	@Test
	public void testEquals() {
		Coordinate test = new Coordinate(3.56, 5.23);
		Coordinate test2 = new Coordinate(3.56, 5.23);
		assertTrue(test.equals(test2));
	}
	
	@Test
	public void testEqualsForFalse() {
		Coordinate test = new Coordinate(3.562, 5.23);
		Coordinate test2 = new Coordinate(3.56, 5.23);
		assertFalse(test.equals(test2));
	}
	
	// compareTo method
	@Test
	public void testCompareTo() {
		Coordinate test = new Coordinate(3.56, 5.23);
		Coordinate test2 = new Coordinate(6.56, 4.23);
		assertTrue(test.compareTo(test2) == test.getX().compareTo(test2.getX()));
	}
	
	// stringTo method
	@Test
	public void testStringTo() {
		Coordinate test = new Coordinate(3.56, 5.23);
		assertTrue(test.toString().equals("(4, 5)"));
	}
	
	// getdirection method
	@Test
	public void testIfOnTheBoundaryLine() {
		Coordinate test = new Coordinate(0.0, 0.0);
		Coordinate test2 = new Coordinate(0.0, 5.0);
		assertTrue(test.getDirection(test2).equals("S"));
	}
	
	@Test
	public void testIfNonBoundaryLine() {
		Coordinate test = new Coordinate(0.0, 0.0);
		Coordinate test2 = new Coordinate(0.1, 5.1);
		assertTrue(test.getDirection(test2).equals("S"));
	}
}
