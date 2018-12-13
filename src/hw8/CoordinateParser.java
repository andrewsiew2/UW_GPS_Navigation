package hw8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hw5.Graph;
import hw6.MarvelParser.MalformedDataException;

/**
 * This class is a parser 
 * 
 */
public class CoordinateParser {
	/**
     * A checked exception class for bad data files
     */
    @SuppressWarnings("serial")
    public static class MalformedDataException extends Exception {
        public MalformedDataException() { }

        public MalformedDataException(String message) {
            super(message);
        }

        public MalformedDataException(Throwable cause) {
            super(cause);
        }

        public MalformedDataException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
    
    /** Parses building data and stores it in the parameters
	 * 
	 * @param filename, the path to the file that contains the data of buildings
	 * 		  longToShortNamePairs, will store the full name of buildings which is linked to its abbreviated version
	 * 		  shortToLongNamePairs, will store the abbreviated name of buildings which is linked to its full version
	 *	 	  locations, will store the full name of the buildings and is linked to its coordinates
	 * @requires file with data to be of a specific format such that:
	 * 			[abbreviated name] (tab) [full name] (tab) [x-coordinate] (tab) [y-coordinate]
	 * 			Also cannot contain duplicates
	 * @throws a MalformedDataException if there is a line of data that didn't match the specific format
	 * @effect longToShortNamePairs, stores the full name of buildings which is linked to its abbreviated version
	 * 		   shortToLongNamePairs, stores the abbreviated name of buildings which is linked to its full version
	 *	 	   locations, stores the full name of the buildings and is linked to its coordinates
     */
    public static void parseBuildingNameData(String filename, Map<String, String> longToShortNamePairs,
    		Map<String, String> shortToLongNamePairs, Map<String, Coordinate> locations) throws MalformedDataException {

	    BufferedReader reader = null;
	    try {
	        reader = new BufferedReader(new FileReader(filename));

	        String inputLine;
	        while ((inputLine = reader.readLine()) != null) {
	
	            // Ignore comment lines.
	            if (inputLine.startsWith("#")) {
	                continue;
	            }
	
	            // Parse the data, stripping out quotation marks and throwing
	            // an exception for malformed lines.
	            inputLine = inputLine.replace("\"", "");
	            String[] tokens = inputLine.split("\t");
	            if (tokens.length != 4) {
	                throw new MalformedDataException("Line should contain exactly one tab: "
	                                                 + inputLine);
	            }
	            String shortName = tokens[0];
	            String fullName = tokens[1];
	            Double xCoordinate = Double.parseDouble(tokens[2]);
	            Double yCoordinate = Double.parseDouble(tokens[3]);
	
	            // maps long name version to its short name version
	            if(!longToShortNamePairs.containsKey(fullName)) {
	            	longToShortNamePairs.put(fullName, shortName);
	            }
	           
	            // maps long name version to its short name version
	            if(!shortToLongNamePairs.containsKey(shortName)) {
	            	shortToLongNamePairs.put(shortName, fullName);
	            }
	            
	            // maps long version name to its coordinates
	            if(!locations.containsKey(fullName)) {
	            	locations.put(fullName, new Coordinate(xCoordinate, yCoordinate));
	            }
	            
	            
	        }
	    } catch (IOException e) {
	        System.err.println(e.toString());
	        e.printStackTrace(System.err);
	    } finally {
	        if (reader != null) {
	            try {
	                reader.close();
	            } catch (IOException e) {
	                System.err.println(e.toString());
	                e.printStackTrace(System.err);
	            }
	        }
	    }
    }
    
    /** Parses paths data and stores it in a graph
	 * 
	 * @param filename, the path to the file that contains the data of paths
	 * 		  graph, will contain nodes of coordinates which are linked to each other by distance
	 * @requires file with data to be of a specific format such that:
	 * 			\\ A source coordinate will be of the form
	 * 			[x-coordinate],[y-coordinate]
	 * 			\\ The coordinate that the source is linked to will be of the form
	 * 			(tab) [x-coordinate],[y-coordinate]: [distance] 
	 * 			Also cannot contain duplicates
	 * @throws a MalformedDataException if there is a line of data that didn't match the specific format
	 * @effect graph, populates with nodes and edges of coordinates which are linked to each other by distance
     */
    public static void buildGraphPaths(String filename, Graph<Coordinate, Double> graph)
    		throws MalformedDataException {
	    BufferedReader reader = null;
	    Coordinate source = null;
	    try {
	        reader = new BufferedReader(new FileReader(filename));
	
	        String inputLine;
	        while ((inputLine = reader.readLine()) != null) {
	
	            // Ignore comment lines.
	            if (inputLine.startsWith("#")) {
	                continue;
	            }
	
	            inputLine = inputLine.replace("\"", "");
	            String[] tokens = inputLine.replace("\t", "").split(": ");
	            String[] moreTokens = tokens[0].split(",");
	            Coordinate coordinate = new Coordinate(Double.parseDouble(moreTokens[0]), Double.parseDouble(moreTokens[1]));
	            Set<Coordinate> allNodes = graph.getAllNodes();
	            
	            if(tokens.length == 1) {
	            	if(!allNodes.contains(coordinate)) {
	            		graph.addNode(coordinate);
	            	}
	            	source = coordinate;
	            }else if (tokens.length == 2) {
	            	if(source == null) {
	            		throw new MalformedDataException("Line should contain exactly one tab: "
	                            + inputLine);
	            	}
	            	if(!allNodes.contains(coordinate)) {
	            		graph.addNode(coordinate);
	            	}
	            	graph.addEdge(source, coordinate, Double.parseDouble(tokens[1]));
	            }else {
	            	throw new MalformedDataException("Line should contain exactly one tab: "
                            + inputLine);
	            }
	        }
	    } catch (IOException e) {
	        System.err.println(e.toString());
	        e.printStackTrace(System.err);
	    } finally {
	        if (reader != null) {
	            try {
	                reader.close();
	            } catch (IOException e) {
	                System.err.println(e.toString());
	                e.printStackTrace(System.err);
	            }
	        }
	    }
	  }
}
