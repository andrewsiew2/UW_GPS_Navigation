package hw5;

/**
 * This class represents a Node that would be put in a Graph.
 *
 * Specification fields:
 *  @specfield children : HashMap<Node, ArrayList<String>> // The collection that store all the nodes 
 *  														  children and its associated Labels
 *  @specfield name   : String // The name of the Node which gives its identity
 *
 * Abstract Function:
 *  Every node must have a name
 *  
 * Representation Invariant: 
 *  name of the node cannot be null
 *  
 *  
 */
public class Node {
	/** The Name of this Node */
	private final String name;
	
	/**
	 * @effects Constructs a new Node with no children.
	 * @param name, the String that is associated with that Node
	 * @throws IllegalArgumentException if the name is null
	 */
	public Node(String name) {
		if(name == null) {
			throw new IllegalArgumentException("Name cannot be null!");
		}
		this.name = name;
		checkRep();
	}
	
	/** Standard equality operation.
    *	@param node, The object to be compared for equality.
    *	@throws IllegalArgumentException if the node is null
    *	@return true if and only if the nodes contains the same name
	*/
	public boolean equals(Node node) {
		if(node == null) {
			throw new IllegalArgumentException("Node cannot be null!");
		}
		checkRep();
		return this.name.equals(node.name);
	}
	
	/**Returns the name of the String
	 * 
	 * @return the name of the String
	 *  
	 */
	public String getName() {
		checkRep();
		return this.name;
	}
	
	/**
	 * Checks that the representation invariant holds (if any).
     **/
	public void checkRep() {
		if(this.name == null) {
			throw new IllegalArgumentException("Name cannot be null!");
		}
	}

	/**Returns a string representation of this Node
	 * 
	 * @return a String representation of this node
	 *         such that: ("name")
	 */
	public String toString() {
		checkRep();
		return "(" + this.getName() + ")";
	}
}
