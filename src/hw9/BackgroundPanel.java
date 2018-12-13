package hw9;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import hw5.Edge;
import hw8.Coordinate;

/**
 * This class represents the view to the Campus Path Finder
 *
 * Specification fields:
 *  @specfield img : BufferedImage // The image of campus that will be marked on
 *  		   imageWidth : int // The width of the entire image as a whole
 *  		   imageHeight : int // The height of the entire image as a whole
 *             source : Coordinate // The coordinate of the starting point of the path
 *             destination : // The coordinate of the ending point of the path
 *             view : Graphics // The graphics of the view
 *             route : List<Edge<Coordinate, Double>> // The shortest route from source to destination
 *             widthRatio : double // the ratio of the width of the image compared to its orignal size
 *             heightRatio : double // the ratio of the height of the image compared to its orignal size
 * 
 */
public class BackgroundPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private BufferedImage img;
	private int imageWidth;
	private int imageHeight;
	public Coordinate source;
	public Coordinate destination;
	private Graphics view;
	private List<Edge<Coordinate, Double>> route;
	private double widthRatio;
	private double heightRatio;
	
	/** 
	 * Constructs a view and adds it to the main interface
	 * 
	 */
    public BackgroundPanel(){
    	imageHeight = 630;
    	imageWidth = 1000;
        File f = new File("src/hw8/data/campus_map.jpg");
        try {
			img = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
        source = null;
        destination = null;
        route = null;
        widthRatio = imageWidth * 1.0 / img.getWidth();
    	heightRatio = imageHeight * 1.0 / img.getHeight();
    }
    
    /** Displays an image that may be marked. The start of the path will
     * be marked with a cyan circle, the end of the path will be marked 
     * with a red circle and the path will be marked in blue. 
	 * 
	 * @effects adds an image of campus on the main interface and additional 
	 * 			markings or path if the requirements are met.
	 * @param g, the graphics of the view
	 * 
	 */
    @Override
    public void paintComponent(Graphics g) {
      // ensure any background belonging to container is painted
      super.paintComponent(g);
      view = g;
      g.drawImage(img, 0, 0, imageWidth, imageHeight, null);
      if(source != null) {
    	  view.setColor(Color.CYAN);
    	  markCoordinate(source);
      }
      if(destination != null) {
    	  view.setColor(Color.RED);
    	  markCoordinate(destination);
      }
      if(route != null) {
    	  drawRoute();
      }
    }
    
    /** Sets the source to be a particular coordinate and marks it on the map
	 * 
	 * @requires source != null
	 * @effects adds a marking that represents the source on the map.
	 * @param source, the coordinate of the start of the path
	 * 
	 */
    public void setSource(Coordinate source) {
    	this.source = source;
    	repaint();
    }
    
    /** Sets the source to be a particular coordinate and marks it on the map
	 * 
	 * @requires route != null
	 * @effects marks the path from the source to the destination on the map
	 * @param route, the shortest path from source to destination
	 * 
	 */
    public void setRoute(List<Edge<Coordinate, Double>> route) {
    	this.route = route;
    	repaint();
    }
    
    /** Returns the source of the path
	 * 
	 * @return the source of the path
	 * 
	 */
    public Coordinate getSource() {
    	return this.source;
    }
    
    /** Returns the destination of the path
	 * 
	 * @return the destination of the path
	 * 
	 */
    public Coordinate getDestination() {
    	return this.destination;
    }
    
    /** Sets the destination to be a particular coordinate and marks it on the map
	 * 
	 * @requires destination != null
	 * @effects adds a marking that represents the destination on the map.
	 * @param destination, the coordinate of the end of the path
	 * 
	 */
    public void setDestination(Coordinate destination) {
    	this.destination = destination;
    	repaint();
    }
    
    /** Returns true if there is a source and destination chosen
     * thus allowing a path to be formed.
	 * 
	 * @return true if destination and source are both not null
	 * 
	 */
    public boolean canRouteBeDrawn() {
		return this.destination != null && this.source != null;
    }
    
    /** Resets the program by erasing all the marking on the map
	 * 
	 * @effects source, make it equate to null
	 * @effects destination, make it equate to null
	 * @effects route, make it equate to null
	 * @effects erases all markings on the map
	 * 
	 */
    public void reset() {
    	this.source = null;
    	this.destination = null;
    	this.route = null;
    	repaint();
    }
    
    /** Marks a coordinate on the map with a colored circle
	 * 
	 * @requires coordinate != null
	 * @effects marks the map of campus with a colored corcle
	 * 
	 */
    public void markCoordinate(Coordinate coordinate) {
    	double xCoordinate = coordinate.getX();
    	double yCoordinate = coordinate.getY();
    	view.fillOval((int) Math.round(xCoordinate *  widthRatio), 
    				(int) Math.round(yCoordinate *  heightRatio), 7, 7);
    }
    
    /** Marks the shortest path from source to destination in blue
	 * 
	 * @effects marks the map of the campus with a blue path
	 * @effects route is changed to null that represents no path
	 * 
	 * Note that this causes any change to the selection of buildings
	 * to erase the path.
	 */
    public void drawRoute() {
    	Coordinate start = route.remove(0).getNode();
    	for(Edge<Coordinate, Double> edge : route) {
    		Coordinate end = edge.getNode();
    		view.setColor(Color.BLUE);
    		view.drawLine((int) Math.round(start.getX() * widthRatio), (int) Math.round(start.getY() * heightRatio),
    					  (int) Math.round(end.getX() * widthRatio), (int) Math.round(end.getY() * heightRatio));
    		start = end;
    	}
    	route = null;
    }
    
}
