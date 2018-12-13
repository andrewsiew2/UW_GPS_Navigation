package hw8;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import hw5.Edge;
import hw5.Graph;
import hw7.MarvelPaths2;
import hw8.CoordinateParser.MalformedDataException;

/**
 * This class represents a coordinate model that has information of the coordinates and buildings in 
 * the read files 
 *
 * Specification fields:
 *  @specfield longToShortbuildingName : Map<String, String> // contains the names of the full names
 *  														 // of the nodes in the graph which is linked to its abbreviated names 
 *  		   shortToLongbuildingName : Map<String, String> // contains the names of the abbreviated names 
 *  														 // of the nodes in the graph which is link to its full names
 *  		   locations : Map<String, Coordinate> // contains the names of the full names
 *  											   // of the nodes in the graph which is linked to its coordinates
 *             graph : Graph<Coordinate, Double> // contains the nodes of the full names and will be linked to a list of Edges
 *  
 * Representation Invariant: 
 * longToShortbuildingName != null
 * shortToLongbuildingName != null
 * locations != null
 * graph != null
 * 
 */
public class CoordinateModel extends MarvelPaths2{
	private final static String FILEPATH = "src/hw8/data/";
	private final static boolean TESTING = false; 
	private static Map<String, String> longToShortbuildingName;
	private static Map<String, String> shortToLongbuildingName;
	private static Map<String, Coordinate> locations; 
	private static Graph<Coordinate, Double> graph;

	/** Builds a Coordinate Model which populates data
	 * 
	 * @param buildingDataFileName, the name of the file that contains data of the buildings
	 * 		  pathDataFileName, the name of the file that contains data of the paths
	 * @throws MalformedDataException if the data in the file don't meet the format
	 * @requires buildingDataFileName != null and pathDataFileName != null
	 * @effect longToShortbuildingName is populated with full names and is ordered
	 * 		   shortToLongbuildingName is populated with abbreviated names and is ordered
	 * 		   locations is populated with Strings which are linked to their coordinates
	 * 		   graph is populated with data of nodes and edges
	 * 
	 */ 
	public CoordinateModel(String buildingDataFileName, String pathDataFileName) throws MalformedDataException {
		longToShortbuildingName = new TreeMap<String, String>();
		shortToLongbuildingName = new TreeMap<String, String>();
		locations = new HashMap<String, Coordinate>();
		graph = new Graph<Coordinate, Double>();
		CoordinateParser.parseBuildingNameData(FILEPATH + buildingDataFileName, longToShortbuildingName,
											shortToLongbuildingName , locations);
		CoordinateParser.buildGraphPaths(FILEPATH + pathDataFileName, graph);
		checkRep();
	}
	
	/** Returns the abbreviated names of the buildings which are linked to its full name
     * @return the abbreviated names of the buildings which are linked to its full name
     */
	public Map<String, String> getBuildingsShortNames() {
		checkRep();
		return shortToLongbuildingName;
	}
	
	/** Returns the full names of the buildings which are linked to its abbreviated name
     * @return the full names of the buildings which are linked to its abbreviated name
     */
	public Map<String, String> getBuildingsLongNames() {
		checkRep();
		return longToShortbuildingName;
	}
	
	/** Returns the full names of the buildings which are linked to its coordinate
     * @return the full names of the buildings which are linked to its coordinate
     */
	public Map<String, Coordinate> getLocations(){
		checkRep();
		return locations;
	}
	
	/** Returns the graph
     * @return the graph
     */
	public Graph<Coordinate, Double> getGraph(){
		checkRep();
		return graph;
	}
	
	/** Finds the minimum cost path. If there are multiple minimum costs paths it will return the 
	 *  Path which is shorter.
	 * @param graph, the graph that stores all the data
	 * 		  start, the starting node
	 * 		  end, the destination node
	 * @requires graph != null, start != null, end != null
	 * @throws a IllegalArgumentException is the start or end node is not contained in the graph
	 * @return a list of edge which represents the path to take to get to the end with the least cost
	 * 		   returns a null if no path is found
	 */
	public static List<Edge<Coordinate, Double>> findMinimumCostPath(Graph<Coordinate, Double> graph, 
																Coordinate start, Coordinate end){
		return MarvelPaths2.findMinimumCostPath(graph, start, end);
		
	}
	
	/**
	 * Checks that the representation invariant holds (if any).
     **/
	public static void checkRep() {
		if(TESTING) {
			if(longToShortbuildingName == null || shortToLongbuildingName == null || locations == null
					|| graph == null) {
				throw new IllegalArgumentException();
			}
		}
	}
}
