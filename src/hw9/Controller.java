package hw9;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import hw5.Edge;
import hw8.Coordinate;
import hw8.CoordinateModel;

/**
 * This class represents the controller to the Campus Path Finder
 *
 * Specification fields:
 *  @specfield model : CoordinateModel // The Campus Path Finder model which contains 
 *  									  the data needed for this application to work
 *  		   view : BackgroundPanel // The Campus Path Finder View which handles the display
 *  									 of this application
 *  		   coordinates : Map<String, Coordinate> // The data of buildings with their 
 *  													corresponding coordinates
 *             mainPanel : JPanel // The main panel that will contain all the components,
 *             						 to be inserted into the main frame
 * 
 */
public class Controller extends JPanel {

	private static final long serialVersionUID = 1L;
	private CoordinateModel model;
	private BackgroundPanel view;
    private Map<String, Coordinate> coordinates;
    private JPanel mainPanel;
    
    /** Constructs a controller and adds it to the main interface
	 * 
	 * @requires model != null and view != null
	 * @effects adds the main panel to the main interface
	 * @param model, the model which holds all the data
	 * @param view, the viewer which handles the display of the campus
	 * 
	 */
	public Controller(CoordinateModel model, BackgroundPanel view) {
		this.model = model;
		this.view = view;
		this.coordinates = model.getLocations();
		this.mainPanel = new JPanel();
		insertButtons();
		insertComboBox();
	    add(mainPanel, BorderLayout.CENTER);
	}
	
	/** Gets data from the model and populates the combo boxes with data,
	 * then adds it to the main panel.
	 * 
	 * @effects mainPanel, adds combo boxes into the main panel
	 */
	public void insertComboBox() {
		Map<String, String> buildingNames = model.getBuildingsLongNames();
		String[] buildings = buildingNames.keySet().toArray(new String[0]);
		ComboBoxListener comboBoxListener = new ComboBoxListener();
		JPanel sourcePanel = new JPanel();
		JPanel destPanel = new JPanel();
		JLabel sourceLabel = new JLabel("This is where you start -->");
		JLabel destLabel = new JLabel("This is where you will arrive -->");
		JComboBox<String> source = new JComboBox<String>(buildings);
		JComboBox<String> destination = new JComboBox<String>(buildings);
		source.setActionCommand("Source");
		source.addActionListener(comboBoxListener);
		destination.setActionCommand("Destination");
		destination.addActionListener(comboBoxListener);
		sourcePanel.add(sourceLabel);
		sourcePanel.add(source);
		destPanel.add(destLabel);
		destPanel.add(destination);
		mainPanel.add(sourcePanel);
		mainPanel.add(destPanel);
	}
	
	/** Creates the find path button and reset button, gives them funtionality
	 * and adds them to the main panel.
	 * 
	 * @effects mainPanel, adds buttons to the main panel
	 */
	public void insertButtons() {
		ButtonListener buttonListener = new ButtonListener();
		JButton findPath = new JButton("Find Path");
		findPath.setActionCommand("Find Path");
		findPath.addActionListener(buttonListener);
		JButton reset = new JButton("Reset");
		reset.setActionCommand("Reset");
		reset.addActionListener(buttonListener);
		mainPanel.add(findPath);
		mainPanel.add(reset);
	}
	
	/** 
	 * An event listener that handles the event of a button being clicked.
	 * It may reset the whole application or find the shortest route and
	 * display it, depending on which button is clicked.
	 * 
	 */
	public class ButtonListener implements ActionListener {
	    /**
	     * Process button clicks by turning the simulation on and off
	     * @param e the event created by the button click
	     */
	   public void actionPerformed(ActionEvent e) {
		   
		   // This check ensure there is a source and destination chosen
		   if(e.getActionCommand().equals("Reset")) {
			   view.reset();
		   }else if(e.getActionCommand().equals("Find Path")) {
			   if(view.canRouteBeDrawn()) {
				   List<Edge<Coordinate, Double>> path = CoordinateModel.findMinimumCostPath(model.getGraph(), 
						   									view.getSource(), view.getDestination());
				   view.setRoute(path);
			   }
		   }
	   }
	 }
	
	/** 
	 * An event listener that handles the event of an element of any of the
	 * combo boxes and marks it on the map of campus.
	 * 
	 * Note that any marked path will be erased on change of the selection of 
	 * a combo box.
	 */
	public class ComboBoxListener implements ActionListener {
	    /**
	     * Process button clicks by turning the simulation on and off
	     * @param e the event created by the button click
	     */
	   public void actionPerformed(ActionEvent e) {
		   @SuppressWarnings("unchecked")
		   JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
		   if(e.getActionCommand().equals("Source")) {
			   view.setSource(coordinates.get(comboBox.getSelectedItem().toString()));
		   }else if(e.getActionCommand().equals("Destination")) {
			   view.setDestination(coordinates.get(comboBox.getSelectedItem().toString()));
		   }
	   }
	 }
}
