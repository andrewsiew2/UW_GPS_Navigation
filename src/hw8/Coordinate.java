package hw8;

/**
 * This class represents a coordinate in a 2D plane which contains a x coordinate and y coordinate
 *
 * Specification fields:
 *  @specfield x : Double // The x-coordinate of this coordinate
 *  		   y : Double // The y-coordinate of this coordinate
 *
 * Abstract Invariant:
 * The x and y coordinates can be represented a point in a 2d graph such that
 * a point/coordinate in a graph is (x,y)
 *  
 * Representation Invariant: 
 * x != null
 * y != null
 * 
 */
public class Coordinate implements Comparable<Coordinate>{
	private final boolean TESTING = false;
	private Double x;
	private Double y;
	
	/**
	 * 
	 * @requires x != null and y != null
	 * @effects Constructs a new Coordinate with an x-coordinate and y-coordinate.
	 */
	public Coordinate(Double x, Double y) {
		this.x = x;
		this.y = y;
		checkRep();
	}
	
	/** Returns the direction when travelling from this coordinate to the other cooerdinate
	 * 
	 * @param destination, the other coordinate which is the destination
	 * @requires destination != null
     * @return the direction of from this coordinate to the other coordinate
     */
	public String getDirection(Coordinate destination) {
		checkRep();
		Double xMovement = this.x - destination.getX();
		Double yMovement = this.y - destination.getY();
		Double angle = Math.atan2(yMovement, xMovement);
		if(3 * Math.PI / 8 <= angle && angle <= 5 * Math.PI / 8) {
			checkRep();
			return "N";
		}else if(Math.PI / 8 < angle && angle < 3 * Math.PI / 8) {
			checkRep();
			return "NW";
		}else if(-Math.PI / 8 <= angle && angle <= Math.PI / 8) {
			checkRep();
			return "W";
		}else if(-3 * Math.PI / 8 < angle && angle < -Math.PI / 8) {
			checkRep();
			return "SW";
		}else if (-5 * Math.PI / 8 <= angle && angle <= -3 * Math.PI / 8) {
			checkRep();
			return "S";
		}else if(-7 * Math.PI / 8 < angle && angle < -5 * Math.PI / 8) {
			checkRep();
			return "SE";
		}else if(7 * Math.PI / 8 <= angle && angle <= -7 * Math.PI / 8) {
			checkRep();
			return "E";
		}else {
			checkRep();
			return "NE";
		}
	}
	
	/** Returns the x-coordinate
     * @return the x-coordinate from this coordinate
     */
	public Double getX() {
		checkRep();
		return x;
	}
	
	/** Returns the y-coordinate
     * @return the y-coordinate from this coordinate
     */
	public Double getY() {
		checkRep();
		return y;
	}
	
	/** Returns the hashcode of this coordinate
     * @return the hashcode of this coordinate
     */
	public int hashCode() {
		checkRep();
		return y.hashCode() + x.hashCode();
	}
	
	/** Returns true if this coordinate has equivalent x-coordinates and y-coordinates
	 * 
	 * @param o, the other Coordinate this coordinate is being comapred to
	 * @requires o != null
     * @return true if this coordinate has equivalent x-coordinates and y-coordinates
     */
	public boolean equals(Object o) {
		checkRep();
		if(!(o instanceof Coordinate)) {
			checkRep();
			return false;
		}
		Coordinate other = (Coordinate) o;
		checkRep();
		return other.getX().equals(this.getX()) && other.getY().equals(this.getY());
	}
	
	/** Returns an int that is used to compare coordinates
	 * 
	 * @param other, the other Coordinate this coordinate is being compared to
	 * @requires other != null
     * @return an int that is used to compare coordinates
     */
	@Override
	public int compareTo(Coordinate other) {
		checkRep();
		return this.getX().compareTo(other.getX());
	}
	
	/** Returns a string representation of this coordinate
	 * 
     * @return a string representation of this coordinate
     */
	public String toString() {
		checkRep();
		return String.format("(%.0f, %.0f)", x, y);
	}
	
	/**
	 * Checks that the representation invariant holds (if any).
     **/
	public void checkRep() {
		if(TESTING) {
			if(this.x == null || this.y == null) {
				throw new IllegalArgumentException("X and Y cannot be null");
			}
		}
	}
}
