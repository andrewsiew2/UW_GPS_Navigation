package hw6;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import hw5.Edge;
import hw5.Graph;

/**
 * This class represents a tool kit that can construct a graph from data and return the shortest paths
 * of nodes in that graph
 *
 * Specification fields:
 *  @specfield FILEPATH : String // The path in the repository at where the graph data in stored
 *  		   reader   : Scanner // Holds the inputs of the user
 *
 * Abstract Invariant:
 * Paths returned in from this class are the shortest paths
 * Graphs returned from this class can be multi-directional
 *  
 * Representation Invariant: 
 * FILEPATH must be an existing directory
 * 
 */
public class MarvelPaths {
	
	private final static String FILEPATH = "src/hw6/data/";
	private final static boolean TESTING = false; 
	private static Scanner reader;
	
	/** The main interface
	 * @requires inputed filename to be correct
	 * 			 inputed node name to contain _ instead of a space
	 */
	public static void main(String[] args) throws Exception {
		reader = new Scanner(System.in);
		System.out.println("Name of file : ");
		String fileName = FILEPATH + reader.next();
		File file = new File(fileName);
		if(file.exists() && !file.isDirectory()) {
			System.out.println("You have selected " + fileName);
			Graph<String, String> graph = buildGraph(fileName);
			boolean run = true;
			while(run) {
				System.out.println("Please type in a Marvel character as a source (Remember to use _ for spaces) :");
				String source = reader.next();
				System.out.println("Please type in a Marvel character as a destination (Remember to use _ for spaces) :");
				String destination = reader.next();
				source = source.replace("_", " ");
				destination = destination.replace("_", " ");
				List<Edge<String, String>> path = shortestPath(graph, source, destination);
				for(Edge<String, String> edge : path) {
					System.out.println("Go to Book " + edge.getLabel() + " and you will see " + edge.getNode());
				}
				System.out.print("Would you like to continue? Type Stop if not");
				if(reader.next().equals("Stop")) {
					run = false;
				}
			}
		}else {
			System.out.println("There is no file in with this path : " + fileName);
			return;
		}
		System.out.println("Thank you for using my Graph!");
		return;
	}
	
	/** Builds a graph from data
	 * @param filename, the name for the file that data is stored in
	 * @requires filename != null and the file of the given name exists
	 * @return a Graph that contains data of the given file 
	 */
	public static Graph<String, String> buildGraph(String fileName) throws Exception {
		checkRep();
		Graph<String, String> graph = new Graph<String, String>();
		Set<String> characters = new HashSet<String>();
		HashMap<String, List<String>> books = new HashMap<String, List<String>>();
		MarvelParser.parseData(fileName, characters, books);
		
		// adds characters as Nodes
		for(String character : characters) {
			graph.addNode(character);
		}
		
		// adds the edges to each Node
		// assumes characters are included as nodes
		// connect to ways a -> b b -> a
		for(String book : books.keySet()) {
			List<String> characterList = books.get(book);
			for(int i = 0; i < characterList.size(); i++) {
				String node1 = characterList.get(i);
				for(int j = i + 1; j < characterList.size(); j++) {
					String node2 = characterList.get(j);
					graph.addEdge(node1, node2, book);
					graph.addEdge(node2, node1, book);
				}
			}
		}
		checkRep();
		return graph;
	}
	
	/** 
	 * A Comparator Class which orders Edge based on the Nodes name then then the Label
	 * if the Nodes name are the same.
	 */
	public static class EdgeComparator implements Comparator<Edge<String, String>> {
	    public int compare(Edge<String, String> obj1, Edge<String, String> obj2) {
	    	int order = obj1.getNode().compareTo(obj2.getNode());
	    	if(order == 0) {
	    		return obj1.getLabel().compareTo(obj2.getLabel());
	    	}
	        return order;
	    }
	}
	
	/** Builds a graph from data
	 * @param graph, the graph that contains the data
	 * 		  start, the source node
	 * 		  destination, the destination node
	 * @requires filename != null and the file of the given name exists
	 * 			 graph != null
	 * @throws an IllegalArgumentException if start and end is not contained in
	 * 		   the graph
	 * @return a list containing edges which represents the path to take
	 */
	public static List<Edge<String, String>> shortestPath(Graph<String, String> graph, String start, String end){
		checkRep();
		Set<String> set = graph.getAllNodes();
		if(!set.contains(start) || !set.contains(end)) {
			throw new IllegalArgumentException("nodes must be contained in the graph");
		}
		HashMap<String, List<Edge<String, String>>> path = new HashMap<String, List<Edge<String, String>>>();
		Queue<String> worklist = new LinkedList<String>();
		worklist.add(start);
		path.put(start, new ArrayList<Edge<String, String>>());
		EdgeComparator comparator = new EdgeComparator();
		while(worklist.peek() != null) {
			String node = worklist.remove();
			if(node.equals(end)) {
				checkRep();
				return path.get(node);
			}
			List<Edge<String, String>> temp = graph.getChildren(node);
			List<Edge<String, String>> allEdges = new ArrayList<Edge<String, String>>(temp);
			Collections.sort(allEdges, comparator);
			for(Edge<String, String> edge : allEdges) {
				if(!path.containsKey(edge.getNode())) {
					List<Edge<String, String>> newPath = new ArrayList<Edge<String, String>>();
					newPath.addAll(path.get(node));
					newPath.add(edge);
					path.put(edge.getNode(), newPath);
					worklist.add(edge.getNode());
				}
			}
		}
		checkRep();
		return null;
	}
	
	/**
	 * Checks that the representation invariant holds (if any).
     **/
	public static void checkRep() {
		if(TESTING) {
			File directory = new File(FILEPATH);
			if(directory.isDirectory()) {
				throw new IllegalArgumentException("There must be a valid directory where the data is stored");
			}
		}
	}
}
