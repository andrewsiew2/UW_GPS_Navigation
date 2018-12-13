package hw5;

/**
 * This class represents an edge which contains a node name and a its label
 *
 * Specification fields:
 *  @specfield node : String // The name of the destination node
 *			   label : String // The name of the label 
 *
 * Abstract Invariant:
 * An edge represents a link with the label linking to the destination node name
 *  
 * Representation Invariant: 
 * node != null && label != null
 * 
 */
public class Edge<T,E extends Comparable<E>> implements Comparable<Edge<T, E>> {
	
	private T node;
	private E label;
	
	/**
	 * @effects Constructs a new Edge with a label and the destination node name.
	 */
	public Edge(T node, E label) {
		this.node = node;
		this.label = label;
		checkRep();
	}
	
	/** Returns the destination node's name
	 * 
     * @return the destination node's name
     */
	public T getNode() {
		checkRep();
		return this.node;
	}
	
	/** Returns the label
	 * 
     * @return the label
     */
	public E getLabel() {
		checkRep();
		return this.label;
	}
	
	/** Standard equality check between edges
	 * 
	 * @param o, the Edge to be compared
     * @throws an IllegalArgumentException if Edge is null	
     * @return true if the label and node name is the same
     */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object o) {
		checkRep();
		if(o == null) {
			throw new IllegalArgumentException();
		}
		if(!(o instanceof Edge)) {
			checkRep();
			return false;
		}
		Edge other = (Edge) o;
		checkRep();
		return this.node.equals(other.node) && this.label.equals(other.label);
	}
	
	@Override
	public int compareTo(Edge<T, E> obj2) {
    	return this.getLabel().compareTo(obj2.getLabel());
    }
	
	/** Returns a unique hashcode based on its fields
	 * 	
     * @return an integer that is the summation of the fields hashcodes
     */
	@Override
	public int hashCode() {
		return this.label.hashCode() + this.node.hashCode();
	}
	
	/**
	 * Checks that the representation invariant holds (if any).
     **/
	public void checkRep() {
		if(this.node == null || this.label == null) {
			throw new IllegalArgumentException();
		}
	}
	
}
