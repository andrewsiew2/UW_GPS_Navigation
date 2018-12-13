package hw8;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import hw5.Edge;
import hw8.CoordinateParser.MalformedDataException;

/**
 * This class represents a text interface that can find the path beteen buildings in UW
 *
 */
public class CoordinateTool {
	
	
	/**
	 * displays the text interface and handles the inputs
	 * @throws MalformedDataException 
	 */
	public static void main(String[] args) throws MalformedDataException {
		Scanner reader = new Scanner(System.in);
		CoordinateModel gps = new CoordinateModel("campus_buildings.dat", "campus_paths.dat");
		String menu = "Menu:\n" + 
				"\t" + "r to find a route\r\n" + 
				"\t" + "b to see a list of all buildings\r\n" + 
				"\t" + "q to quit\n";
		System.out.println(menu);
		System.out.print("Enter an option ('m' to see the menu): ");
		
		while(true) {
			String input = reader.nextLine();
			
			if(input.startsWith("#") || input.equals("")) {
				System.out.println(input);
				continue;
			}
			
			if(input.equals("m")) {
				System.out.println(menu);
			}else if(input.equals("b")) {
				printBuildings(gps);
			}else if(input.equals("r")) {
				
				System.out.print("Abbreviated name of starting building: ");
				String source = reader.nextLine();
				System.out.print("Abbreviated name of ending building: ");
				String destination = reader.nextLine();
				Map<String, String> buildings = gps.getBuildingsShortNames();
				if(!(buildings.get(source) == null) && !(buildings.get(destination) == null)) {
					
					System.out.println("Path from " + buildings.get(source) + " to " + buildings.get(destination) + ":");
					Map<String, Coordinate> locations = gps.getLocations();
					List<Edge<Coordinate, Double>> list = CoordinateModel.findMinimumCostPath(gps.getGraph(), 
														locations.get(buildings.get(source)), locations.get(buildings.get(destination)));
					Edge<Coordinate, Double> start = list.remove(0);
					Coordinate prev = start.getNode();
					String result = "";
					Double total = 0.0;
					for(Edge<Coordinate, Double> edge : list) {
						result += String.format("\t" + "Walk %.0f feet " + prev.getDirection(edge.getNode())
						                        + " to " + edge.getNode() + "\n", edge.getLabel() - total);
						prev = edge.getNode();
						total = edge.getLabel();
					}
					System.out.println(result + "Total distance: " + Math.round(total) + " feet\n");
				}else {
					if(buildings.get(source) == null) {
						System.out.println("Unknown building: " + source);
					}
					if(buildings.get(destination) == null) {
						System.out.println("Unknown building: " + destination);
					}
					System.out.println();
				}
				
			}else if(input.equals("q")) {
				reader.close();
				return;
			}else {
				System.out.println("Unknown option\n");
			}
			System.out.print("Enter an option ('m' to see the menu): ");
		}
	}
	
	/** Prints the name of all the buildings with its full name and abbreviated name
	 * 
	 * @param model, the coordinate model which contains all the data
	 * @requires model != null
	 */
	public static void printBuildings(CoordinateModel model) {
		Map<String, String> buildings = model.getBuildingsShortNames();
		String allBuildings = "Buildings:\n";
		for(String name : buildings.keySet()) {
			allBuildings += "\t" + name + ": " + buildings.get(name) + "\n";
			
		}
		System.out.println(allBuildings);
	}
}
