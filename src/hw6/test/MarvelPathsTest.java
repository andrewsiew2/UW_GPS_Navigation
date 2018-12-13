package hw6.test;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import hw5.*;
import hw6.MarvelPaths;

public class MarvelPathsTest {
	
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	Methods
	///////////////////////////////////////////////////////////////////////////////////////
	
	// buildGraph method
	
	@Test
	public void testBuildAGraphOfAWrongPath() throws Exception{
		Graph graph = MarvelPaths.buildGraph("src/hw6/data/testing.tsv");
		Set<String> set = graph.getAllNodes();
		assertTrue(set.contains("BATMAN"));
		assertTrue(set.contains("SUPERMAN"));
		assertTrue(set.contains("LUTHER"));
		assertTrue(set.contains("WONDER WOMAN"));
		assertTrue(set.contains("THANOS"));
		assertTrue(set.contains("GROOT"));
		assertTrue(set.contains("SPIDERMAN"));
	}
	
	
	// shortestPath method
	
	@Test (expected = IllegalArgumentException.class)
	public void testShortestPathOfUnkownSource() throws Exception {
		Graph graph = MarvelPaths.buildGraph("src/hw6/data/testing.tsv");
		List<Edge> list = MarvelPaths.shortestPath(graph, "????", "DARKSEID");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testShortestPathOfUnkownDest() throws Exception {
		Graph graph = MarvelPaths.buildGraph("src/hw6/data/testing.tsv");
		List<Edge> list = MarvelPaths.shortestPath(graph, "BATMAN", "????");
	}
	
	@Test
	public void testShortestPathThatDoesntExist() throws Exception {
		Graph graph = MarvelPaths.buildGraph("src/hw6/data/testing.tsv");
		List<Edge> list = MarvelPaths.shortestPath(graph, "BATMAN", "DARKSEID");
		assertTrue(list == null);
	}
	
	@Test
	public void testShortestPathThatExists() throws Exception {
		Graph graph = MarvelPaths.buildGraph("src/hw6/data/testing.tsv");
		List<Edge> list = MarvelPaths.shortestPath(graph, "BATMAN", "SUPERMAN");
		assertTrue(list.contains(new Edge("SUPERMAN", "28")));
	}
	
	@Test
	public void testShortestPathThatIsABitLongerAndIsOrderedExists() throws Exception {
		Graph graph = MarvelPaths.buildGraph("src/hw6/data/testing.tsv");
		List<Edge> list = MarvelPaths.shortestPath(graph, "DARKSEID", "GROOT");
		assertTrue(list.contains(new Edge("THANOS", "9000")) || list.contains(new Edge("GROOT", "1")));
	}
}
