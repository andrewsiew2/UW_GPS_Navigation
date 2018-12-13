package hw5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * This class represents a directed labeled multigraph which uses Nodes of the Node class
 * The graph is mutable
 *
 * Specification fields:
 *  @specfield graph : HashMap<Node, HashMap<Node, String>> // The list which contains all the nodes in the graph
 *
 * Abstract Invariant:
 * No two nodes contained in the graph is the same node (basically no duplicates)
 * There are no duplicate edges.
 *  
 * Representation Invariant: 
 * There is no null Nodes in the graph.
 * The graph itself isn't null.
 * Labels of edges must not be null.
 * 
 */
public class Graph<T, E extends Comparable<E>> {
	
	/** Holds all the Nodes in this Graph and its corresponding edges and labels*/
	private final HashMap<T, HashSet<Edge<T,E>>> graph;
	private final boolean TESTING = false; 
	/**
	 * @effects Constructs a new Graph with no Nodes.
	 */
	public Graph() {
		graph = new HashMap<T, HashSet<Edge<T,E>>>();
		checkRep();
	}
	
	/** Add passed node into the graph if it doesn't already exist
	 * 	and returns true if an add was successful.
	 * 
	 * @param node, the node that is to be added into this graph
     * @effects adds a node to the graph, if the node already exists,
     * 			do nothing.
     * @throws an IllegalArgumentException if node is null	
     * @return true if the add was successful and false if the node
     * 			already exists
     */
	public boolean addNode(T node) {
		checkRep();
		if(node == null) {
			throw new IllegalArgumentException("Node cannot be null!");
		}
		if(graph.containsKey(node)) {
			checkRep();
			return false;
		}
		graph.put(node, new HashSet<Edge<T,E>>());
		checkRep();
		return true;
	}
	
	/**Adds an edge from source to destination with label if
	 * it doesn't exist already. And returns true if it was successful
	 * 
	 * @param source, the node that is the parent
	 * 		  destination, the child node of the parent
	 * 		  label, the label of the edge 
     * @effects adds an edge from source to destination with a label
     * 			but does nothing if that edge exists already.
     * @throws an IllegalArgumentException if any of the parameters is null
     * 		   an IllegalArgumentException if source or destination is not 
     * 		   a node contained in the graph.
     * @return true if the add was successful and false if it already exists
     */
	public boolean addEdge(T source, T destination, E label) {
		checkRep();
		if(source == null || destination == null || label == null ||
		   !graph.containsKey(source) || !graph.containsKey(destination)) {
			throw new IllegalArgumentException("Source, Destination and Label cannot be null."
											+ "Source and Destination need to be contained in the graph!");
		}
		
		HashSet<Edge<T,E>> allEdges = graph.get(source);
		Edge<T,E> edge = new Edge<T,E>(destination, label);
		if(!(allEdges.contains(edge))) {
			allEdges.add(edge);
			checkRep();
			return true;
		}
		checkRep();
		return false;
	}
	
	/**
     * @return the number of Nodes in this graph
     */
	public int numberOfNodes() {
		checkRep();
		return this.graph.size();
	}

	/**Removes an edge from source to destination with label if
	 * it exists. And returns true if the removal was successful.
	 * 
	 * @param source, the node that is the parent
	 * 		  destination, the child node of the parent
	 * 		  label, the label of the edge 
     * @effects adds an edge from source to destination with a label
     * 			but does nothing if that edge doesn't  exists.
     * @throws an IllegalArgumentException if any of the parameters is null
     * 		   an IllegalArgumentException if source or destination is not 
     * 		   a node contained in the graph.
     * @return true if edge was successfully removed, false it doesn't exist
     */
	public boolean removeEdge(T source, T destination, E label) {
		checkRep();
		if(source == null || destination == null || label == null ||
		   !graph.containsKey(source) || !graph.containsKey(destination)) {
			throw new IllegalArgumentException("Source, Destination and Label cannot be null."
											+ "Source and Destination need to be contained in the graph!");
		}
		
		HashSet<Edge<T, E>> allEdges = graph.get(source);
		Edge<T, E> edge = new Edge<T, E>(destination, label);
		if(!(allEdges.contains(edge))) {
			checkRep();
			return false;
		}
		allEdges.remove(edge);
		checkRep();
		return true;
	}
	
	/**Returns all the nodes of the graph in a set
     * @return a set of nodes in the graph
     */
	public Set<T> getAllNodes() {
		checkRep();
		return this.graph.keySet(); 
	}
	
	/**Returns all the edges of a node
	 * 
	 * @param node, the node that may have child nodes
     * @throws an IllegalArgumentException the node is null 
     * @return a map of edge with their corresponding labels
     */
	public List<Edge<T, E>> getChildren(T node) {
		checkRep();
		if(node == null || !graph.containsKey(node)) {
			throw new IllegalArgumentException("nodes cannot be null! and must be an existing node");
		}
		List<Edge<T, E>> allEdges = new ArrayList<Edge<T, E>>(graph.get(node));
		Collections.sort(allEdges);
		checkRep();
		return allEdges;
	}
	
	/**
	 * Checks that the representation invariant holds (if any).
     **/
	public void checkRep() {
		if(TESTING) {
			// checks if graph is null
			if(graph == null) {
				throw new IllegalArgumentException("Graph cannot be Null!");
			}
			Set<T> allNodes = this.getAllNodes();
			// checks if Nodes are null
			if(allNodes.contains(null)) {
				throw new IllegalArgumentException("Nodes cannot be Null!");
			}
			
			for(T parent : allNodes) {
				List<Edge<T, E>> childNodes = this.getChildren(parent);
				if(childNodes.contains(null)) {
					throw new IllegalArgumentException("Edges cannot be null!");
				}
				
			}
		}
	}
	
}
