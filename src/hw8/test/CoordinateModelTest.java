package hw8.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import hw5.Edge;
import hw5.Graph;
import hw8.Coordinate;
import hw8.CoordinateModel;

public class CoordinateModelTest {

	///////////////////////////////////////////////////////////////////////////////////////
	////	Constructor
	///////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testConstructor() throws Exception {
		new CoordinateModel("test_buildings.dat", "test_paths.dat");
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	Methods
	///////////////////////////////////////////////////////////////////////////////////////
	
	//getBuildingsShortNames method
	@Test
	public void testGetBuildingsShortNames() throws Exception {
		CoordinateModel test = new CoordinateModel("test_buildings.dat", "test_paths.dat");
		Map<String, String> tester = test.getBuildingsShortNames();
		assertTrue(tester.containsKey("BAG (NE)"));
		assertTrue(tester.containsKey("BAG"));
		assertTrue(tester.containsKey("BGR"));
		assertTrue(tester.get("BAG (NE)").equals("Bagley Hall (Northeast Entrance)"));
		assertTrue(tester.get("BAG").equals("Bagley Hall (East Entrance)"));
		assertTrue(tester.get("BGR").equals("By George"));		
	}
	
	//getBuildingsLongNames method
	@Test
	public void testGetBuildingsLongNames() throws Exception {
		CoordinateModel test = new CoordinateModel("test_buildings.dat", "test_paths.dat");
		Map<String, String> tester = test.getBuildingsLongNames();
		assertTrue(tester.containsKey("Bagley Hall (East Entrance)"));
		assertTrue(tester.containsKey("Bagley Hall (Northeast Entrance)"));
		assertTrue(tester.containsKey("By George"));
		assertTrue(tester.get("Bagley Hall (East Entrance)").equals("BAG"));
		assertTrue(tester.get("Bagley Hall (Northeast Entrance)").equals("BAG (NE)"));
		assertTrue(tester.get("By George").equals("BGR"));		
	}
	
	//getLocations method
	@Test
	public void testGetLocations() throws Exception {
		CoordinateModel test = new CoordinateModel("test_buildings.dat", "test_paths.dat");
		Map<String, Coordinate> tester = test.getLocations();
		assertTrue(tester.containsKey("Bagley Hall (East Entrance)"));
		assertTrue(tester.containsKey("Bagley Hall (Northeast Entrance)"));
		assertTrue(tester.containsKey("By George"));
		Coordinate tester1 = new Coordinate(1914.5103,1709.8816);
		Coordinate tester2 = new Coordinate(1878.3786,1661.4083);
		Coordinate tester3 = new Coordinate(1671.5499,1258.4333);
		assertTrue(tester.get("Bagley Hall (East Entrance)").equals(tester1));
		assertTrue(tester.get("Bagley Hall (Northeast Entrance)").equals(tester2));
		assertTrue(tester.get("By George").equals(tester3));		
	}
	
	//getGraph method
	@Test
	public void testGetGraph() throws Exception {
		CoordinateModel testModel = new CoordinateModel("test_buildings.dat", "test_paths.dat");
		Graph<Coordinate, Double> graph = testModel.getGraph();
		Coordinate test = new Coordinate(1.0,1.0);
		Coordinate test3 = new Coordinate(2.3,23.4);
		Coordinate test2 = new Coordinate(3.4,2.4);
		Coordinate test4 = new Coordinate(2.4,23.4);
		Coordinate test5 = new Coordinate(2.3,222.22);
		assertTrue(graph.getAllNodes().contains(test));
		assertTrue(graph.getAllNodes().contains(test3));
		assertFalse(graph.addEdge(test, test2, 4.2));
		assertFalse(graph.addEdge(test, test4, 22.3));
		assertFalse(graph.addEdge(test3, test5, 22.1));		
	}
	
	//getGraph method
	@Test
	public void testFindMinimumPath() throws Exception {
		CoordinateModel testModel = new CoordinateModel("test_buildings.dat", "test_paths.dat");
		Graph<Coordinate, Double> graph = testModel.getGraph();
		Coordinate source = new Coordinate(1.0,1.0);
		Coordinate destination = new Coordinate(2.3,23.4);
		List<Edge<Coordinate, Double>> path = CoordinateModel.findMinimumCostPath(graph, source, destination);
		assertTrue(path.get(path.size() - 1).getNode().equals(destination));
		assertTrue(path.get(path.size() - 1).getLabel().equals(5.2));
	}
	
}
