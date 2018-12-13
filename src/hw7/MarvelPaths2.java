package hw7;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
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
public class MarvelPaths2 {
	
	private final static String FILEPATH = "src/hw7/data/";
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
			Graph<String, Double> graph1 = buildGraph(fileName);
			boolean run = true;
			while(run) {
				System.out.println("Please type in a Marvel character as a source (Remember to use _ for spaces) :");
				String source = reader.next();
				System.out.println("Please type in a Marvel character as a destination (Remember to use _ for spaces) :");
				String destination = reader.next();
				source = source.replace("_", " ");
				destination = destination.replace("_", " ");
				List<Edge<String, Double>> path = findMinimumCostPath(graph1, source, destination);
				for(Edge<String, Double> edge : path) {
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
	
	/** Builds a weighted graph from data
	 * @param filename, the name for the file that data is stored in
	 * @requires filename != null and the file of the given name exists
	 * @return a Graph that contains data of the given file 
	 */
	public static Graph<String, Double> buildGraph(String fileName) throws Exception {
		checkRep();
		Graph<String, Double> graph = new Graph<String, Double>();
		Set<String> characters = new HashSet<String>();
		HashMap<String, List<String>> books = new HashMap<String, List<String>>();
		MarvelParser2.parseData(fileName, characters, books);
		HashMap<String, HashMap<String, Integer>> common = new HashMap<String, HashMap<String, Integer>>();
		// adds characters as Nodes
		for(String character : characters) {
			graph.addNode(character);
			common.put(character, new HashMap<String, Integer>());
		}
		
		// populates commons
		for(String book : books.keySet()) {
			List<String> listOfCharacters = books.get(book);
			for(int i = 0; i < listOfCharacters.size(); i++) {
				HashMap<String, Integer> link1 = common.get(listOfCharacters.get(i)); 
				for(int j = i + 1; j < listOfCharacters.size(); j++) {
					HashMap<String, Integer> link2 = common.get(listOfCharacters.get(j)); 
					if(!link1.containsKey(listOfCharacters.get(j))) {
						link1.put(listOfCharacters.get(j), 0);
					}
					if(!link2.containsKey(listOfCharacters.get(i))) {
						link2.put(listOfCharacters.get(i), 0);
					}
					link1.put(listOfCharacters.get(j), link1.get(listOfCharacters.get(j)) + 1);
					link2.put(listOfCharacters.get(i), link2.get(listOfCharacters.get(i)) + 1);
				}
			}
		}
		
		// adds the edges to each Node
		// assumes characters are included as nodes
		// connect to ways a -> b b -> a
		for(String character : common.keySet()) {
			HashMap<String, Integer> link = common.get(character);
			for(String linkedCharacter : link.keySet()) {
				int links = link.get(linkedCharacter);
				graph.addEdge(character, linkedCharacter, 1.0/links);
			}
		}
		checkRep();
		return graph;
	}
	
	/** Finds the minimum cost path. If there are multiple minimum costs paths it will return the 
	 *  Path which is shorter.
	 * @param <T>
	 * @param graph, the graph that stores all the data
	 * 		  start, the starting node
	 * 		  end, the destination node
	 * @requires graph != null, start != null, end != null
	 * @throws a IllegalArgumentException is the start or end node is not contained in the graph
	 * @return a list of edge which represents the path to take to get to the end with the least cost
	 * 		   returns a null if no path is found
	 */
	public static <T> List<Edge<T, Double>> findMinimumCostPath(Graph<T, Double> graph, T start, T end){
		checkRep();
		Set<T> set = graph.getAllNodes();
		if(!set.contains(start) || !set.contains(end)) {
			throw new IllegalArgumentException("start node and end node must be contained in the graph");
		}
		PathComparator<T> comparator = new PathComparator<T>();
		PriorityQueue<List<Edge<T, Double>>> active = new PriorityQueue<List<Edge<T, Double>>>(comparator);
		HashSet<T> finished = new HashSet<T>();
		List<Edge<T, Double>> initial = new ArrayList<Edge<T, Double>>();
		initial.add(new Edge<T, Double>(start, 0.0));
		active.add(initial);
		while(!active.isEmpty()) {
			List<Edge<T, Double>> minPath = active.poll();
			Edge<T, Double> minPathLastEdge = minPath.get(minPath.size() - 1);
			T minDest = minPathLastEdge.getNode();
			
			if(minDest.equals(end)) {
				checkRep();
				return minPath;
			}
			
			if(!finished.contains(minDest)) {
				for(Edge<T, Double> edge : graph.getChildren(minDest)) {
					if(!finished.contains(edge.getNode())) {
						List<Edge<T, Double>> newPath = new ArrayList<Edge<T, Double>>();
						newPath.addAll(minPath);
						newPath.add(new Edge<T, Double>(edge.getNode(),edge.getLabel() + minPathLastEdge.getLabel()));
						active.add(newPath);
					}
				}
				finished.add(minDest);
			}
		}
		checkRep();
		return null;
	}
	
	/** 
	 * A Comparator Class which orders Edge based on the Nodes name then then the Label
	 * if the Nodes name are the same.
	 * @param <T>
	 */
	public static class PathComparator<T> implements Comparator<List<Edge<T, Double>>> {
	    public int compare(List<Edge<T, Double>> obj1, List<Edge<T, Double>> obj2) {
	    	Double cost1 = obj1.get(obj1.size() - 1).getLabel();
	    	Double cost2 = obj2.get(obj2.size() - 1).getLabel();
	    	if(cost1.equals(cost2)) {
	    		return obj1.size() - obj2.size();
	    	}
	        return cost1.compareTo(cost2);
	    }
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