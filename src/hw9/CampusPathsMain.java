package hw9;

import java.awt.*;
import javax.swing.*;
import hw8.CoordinateModel;
import hw8.CoordinateParser.MalformedDataException;
/**
 * 
 * This class is the main interface of the Campus Path Finder.
 * The Campus Path Finder can find the shortest path between
 * buildings in UW. For buildings to be included their data must be included 
 * in the files.
 * 
 */
public class CampusPathsMain {
	/**  The main interface which will display the entire program
	 * @throws MalformedDataException when the data in the files do not meet a certain format
	 * */
	public static void main(String[] args) throws MalformedDataException {
		CoordinateModel model = new CoordinateModel("campus_buildings.dat", "campus_paths.dat");
	    JFrame frame = new JFrame("Campus Path Finder");
	    frame.setPreferredSize(new Dimension(1300,768));
	    BackgroundPanel panel = new BackgroundPanel();
	    panel.setPreferredSize(new Dimension(1300,620));
	    JPanel controller = new Controller(model, panel);
	    frame.add(panel,BorderLayout.CENTER);
	    frame.add(controller,BorderLayout.NORTH);
	    frame.pack();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	  }
	
}
