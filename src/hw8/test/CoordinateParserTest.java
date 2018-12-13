package hw8.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import hw5.Graph;
import hw8.Coordinate;
import hw8.CoordinateParser;
import hw8.CoordinateParser.MalformedDataException;

public class CoordinateParserTest {
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	Methods
	///////////////////////////////////////////////////////////////////////////////////////
	
	// buildGraphPaths method
	@Test
	public void testBuildGraphPaths() throws MalformedDataException {
		Graph<Coordinate, Double> graph = new Graph<Coordinate, Double>();
		CoordinateParser.buildGraphPaths("src/hw8/data/test_paths.dat", graph);
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
	
	// parseBuildingNameData method
	@Test
	public void testParseBuildingNameData() throws MalformedDataException {
		Map<String, String> longToShort = new TreeMap<String, String>();
		Map<String, String> shortToLong = new TreeMap<String, String>();
		Map<String, Coordinate> locations = new TreeMap<String, Coordinate>();
		CoordinateParser.parseBuildingNameData("src/hw8/data/test_buildings.dat", longToShort, shortToLong, locations);
		Coordinate test = new Coordinate(1914.5103,	1709.8816);
		Coordinate test2 = new Coordinate(1878.3786, 1661.4083);
		Coordinate test3 = new Coordinate(1671.5499, 1258.4333);
		assertTrue(longToShort.containsKey("Bagley Hall (East Entrance)") 
				&& longToShort.containsKey("Bagley Hall (Northeast Entrance)") 
				&& longToShort.containsKey("By George"));
		assertTrue(shortToLong.containsKey("BAG") 
				&& shortToLong.containsKey("BAG (NE)") 
				&& shortToLong.containsKey("BGR"));
		assertTrue(locations.get("Bagley Hall (East Entrance)").equals(test)
				&& locations.get("Bagley Hall (Northeast Entrance)").equals(test2)
				&& locations.get("By George").equals(test3));
	}
}
