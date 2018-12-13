package hw7.test;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import hw5.Edge;
import hw5.Graph;
import hw7.*;

public class MarvelPaths2Test {
	
	
	///////////////////////////////////////////////////////////////////////////////////////
	////	Methods
	///////////////////////////////////////////////////////////////////////////////////////
	
	// buildGraph method
	
	@Test
	public void testBuildAGraphOfTestingFile() throws Exception{
		Graph graph = MarvelPaths2.buildGraph("src/hw7/data/testing.tsv");
		Set<String> set = graph.getAllNodes();
		assertTrue(set.contains("BATMAN"));
		assertTrue(set.contains("SUPERMAN"));
		assertTrue(set.contains("LUTHER"));
		assertTrue(set.contains("WONDER WOMAN"));
		assertTrue(set.contains("THANOS"));
		assertTrue(set.contains("GROOT"));
		assertTrue(set.contains("SPIDERMAN"));
	}
	
	
	// LowestCostPath method
	
	@Test (expected = IllegalArgumentException.class)
	public void testShortestPathOfUnkownSource() throws Exception {
		Graph graph = MarvelPaths2.buildGraph("src/hw7/data/testing.tsv");
		List<Edge> list = MarvelPaths2.findMinimumCostPath(graph, "????", "DARKSEID");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testShortestPathOfUnkownDest() throws Exception {
		Graph graph = MarvelPaths2.buildGraph("src/hw7/data/testing.tsv");
		List<Edge> list = MarvelPaths2.findMinimumCostPath(graph, "BATMAN", "????");
	}
	
	@Test
	public void testShortestPathThatDoesntExist() throws Exception {
		Graph graph = MarvelPaths2.buildGraph("src/hw7/data/testing.tsv");
		List<Edge> list = MarvelPaths2.findMinimumCostPath(graph, "BATMAN", "DARKSEID");
		assertTrue(list == null);
	}
	
	@Test
	public void testShortestPathThatExists() throws Exception {
		Graph graph = MarvelPaths2.buildGraph("src/hw7/data/testing.tsv");
		List<Edge> list = MarvelPaths2.findMinimumCostPath(graph, "NEVERMORE", "LINA");
		Edge test = new Edge("LINA", 1.0);
		assertTrue(list.get(list.size() - 1).equals(test));
	}
	
	@Test
	public void testThatItTakesTheLowestCost() throws Exception {
		Graph graph = MarvelPaths2.buildGraph("src/hw7/data/testing.tsv");
		List<Edge> list = MarvelPaths2.findMinimumCostPath(graph, "SPECTRE", "BARA");
		Edge test = new Edge("BARA", 1.5);
		assertTrue(list.get(list.size() - 1).equals(test));
	}
	
	@Test
	public void testThatItTakesShorterPathIfCostIsEqual() throws Exception {
		Graph graph = MarvelPaths2.buildGraph("src/hw7/data/testing.tsv");
		List<Edge> list = MarvelPaths2.findMinimumCostPath(graph, "NEVERMORE", "TIDEHUNTER");
		Edge test = new Edge("TIDEHUNTER", 2.0);
		assertTrue(list.get(list.size() - 1).equals(test) && list.size() == 3);
	}
	
	@Test
	public void testThatItPointsToItself() throws Exception {
		Graph graph = MarvelPaths2.buildGraph("src/hw7/data/testing.tsv");
		List<Edge> list = MarvelPaths2.findMinimumCostPath(graph, "NEVERMORE", "NEVERMORE");
		Edge test = new Edge("NEVERMORE", 0.0);
		assertTrue(list.get(list.size() - 1).equals(test) && list.size() == 1);
	}
	
	@Test
	public void testNullIsReturnedIfNoPathsExists() throws Exception {
		Graph graph = MarvelPaths2.buildGraph("src/hw7/data/testing.tsv");
		List<Edge> list = MarvelPaths2.findMinimumCostPath(graph, "NEVERMORE", "BATMAN");
		assertTrue(list == null);
	}
}
