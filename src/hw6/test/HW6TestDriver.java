package hw6.test;

import java.io.*;
import java.util.*;

import hw5.Edge;
import hw5.Graph;
import hw6.MarvelPaths;

/**
 * This class implements a testing driver which reads test scripts
 * from files for testing Graph.
 **/
public class HW6TestDriver {

    public static void main(String args[]) {
        try {
            if (args.length > 1) {
                printUsage();
                return;
            }

            HW6TestDriver td;

            if (args.length == 0) {
                td = new HW6TestDriver(new InputStreamReader(System.in),
                                       new OutputStreamWriter(System.out));
            } else {

                String fileName = args[0];
                File tests = new File (fileName);

                if (tests.exists() || tests.canRead()) {
                    td = new HW6TestDriver(new FileReader(tests),
                                           new OutputStreamWriter(System.out));
                } else {
                    System.err.println("Cannot read from " + tests.toString());
                    printUsage();
                    return;
                }
            }

            td.runTests();

        } catch (IOException e) {
            System.err.println(e.toString());
            e.printStackTrace(System.err);
        }
    }

    private static void printUsage() {
        System.err.println("Usage:");
        System.err.println("to read from a file: java hw5.test.HW6TestDriver <name of input script>");
        System.err.println("to read from standard in: java hw6.test.HW6TestDriver");
    }

    /** String -> Graph: maps the names of graphs to the actual graph **/
    //TODO for the student: Parameterize the next line correctly.
    //private final Map<String, _______> graphs = new HashMap<String, ________>();
    private final Map<String, Graph> graphs = new HashMap<String, Graph>();
    private final PrintWriter output;
    private final BufferedReader input;

    /**
     * @requires r != null && w != null
     *
     * @effects Creates a new HW5TestDriver which reads command from
     * <tt>r</tt> and writes results to <tt>w</tt>.
     **/
    public HW6TestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    /**
     * @effects Executes the commands read from the input and writes results to the output
     * @throws IOException if the input or output sources encounter an IOException
     **/
    public void runTests()
        throws IOException
    {
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            if ((inputLine.trim().length() == 0) ||
                (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            }
            else
            {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if (st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<String>();
                    while (st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            if (command.equals("CreateGraph")) {
                createGraph(arguments);
            } else if (command.equals("AddNode")) {
                addNode(arguments);
            } else if (command.equals("AddEdge")) {
                addEdge(arguments);
            } else if (command.equals("ListNodes")) {
                listNodes(arguments);
            } else if (command.equals("ListChildren")) {
                listChildren(arguments);
            } else if (command.equals("LoadGraph")) {
            	buildGraph(arguments);
        	} else if (command.equals("FindPath")) {
            	shortestPath(arguments);
        	} else {
                output.println("Unrecognized command: " + command);
            }
        } catch (Exception e) {
            output.println("Exception: " + e.toString());
        }
    }
    
    private void buildGraph(List<String> arguments) throws Exception {
    	if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to LoadGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        String fileName = arguments.get(1);
        buildGraph(graphName, fileName);
    }
    
    private void buildGraph(String graphName, String fileName) throws Exception {
    	graphs.put(graphName, MarvelPaths.buildGraph("src/hw6/data/" + fileName));
    	output.println("loaded graph " + graphName);
    }
    
    private void shortestPath(List<String> arguments) {
    	if (arguments.size() != 3) {
            throw new CommandException("Bad arguments to FindPath: " + arguments);
        }
        String graphName = arguments.get(0);
        String source = arguments.get(1).replace('_', ' ');
        String destination = arguments.get(2).replace('_', ' ');
        shortestPath(graphName, source, destination);
    }
    
    private void shortestPath(String graphName, String source, String destination) {
    	Graph graph = graphs.get(graphName);
    	Set<String> allNodes = graph.getAllNodes();
    	
    	if(allNodes.contains(source) && allNodes.contains(destination)) {
    		String tempNode= source;
    		String result = "path from " + source + " to " + destination + ":";
    		List<Edge> path = MarvelPaths.shortestPath(graph, source, destination);
    		if(path != null) {
    			for(Edge edge : path) {
    				result += "\n" + tempNode + " to ";
    				tempNode = (String) edge.getNode();
    				result += tempNode + " via " + edge.getLabel();
    			}
    		}else {
    			result += "\n" + "no path found";
    		}
    		output.println(result);
    	}else {
    		if(!allNodes.contains(source)) {
    			output.println("unknown character " + source);
    		}
    		if(!allNodes.contains(destination)) {
    			output.println("unknown character " + destination);
    		}
    	}
    	
    }
    
    
    private void createGraph(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        // Insert your code here.

        graphs.put(graphName, new Graph());
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to addNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        // Insert your code here.

        Graph graph = graphs.get(graphName);
        graph.addNode(nodeName);
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if (arguments.size() != 4) {
            throw new CommandException("Bad arguments to addEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
            			 String edgeLabel) {
        // Insert your code here.

         Graph graph = graphs.get(graphName);
         graph.addEdge(parentName, childName, edgeLabel);
         output.println("added edge " + edgeLabel + " from " + parentName + 
        		 		" to " + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to listNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        // Insert your code here.

    	Graph graph = graphs.get(graphName);
    	String outputNode = graphName + " contains:";
    	Set<String> setOfNodes = graph.getAllNodes();		
    	List<String> list = new ArrayList<String>();
    	for(String node : setOfNodes) {
    		list.add(node);
    	}
    	Collections.sort(list);
    	for(String name: list) {
    		outputNode += " " + name;
    	}
        output.println(outputNode);
    }

    private void listChildren(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to listChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        // Insert your code here.

    	Graph graph = graphs.get(graphName);
    	String outputChildren = "the children of " + parentName + " in " + graphName + " are:";
    	List<Edge> children = graph.getChildren(parentName);
    	for(Edge node : children) {
    		outputChildren += " " + node.getNode() + "(" + node.getLabel() + ")";
    	}
        output.println(outputChildren);
    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }
        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}
