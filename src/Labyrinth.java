//CS2210a 2020
//Jessica Yang
//Student Number: 251083596
//ID: jyang762
//Date: December 1st 2020

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Stack;

public class Labyrinth {
	
	// stores the initial graph and keys given
	private Graph graph;
	private int[] keys;
	
	// start and end node
	private int start;
	private int end;

	private Stack<Node> path = new Stack<Node>(); // stores the path
	
	private Edge check = null; // stores current node
	private Node uncheck = null; // stores the node to be removed
	private int lastKeyUsed = 0; // stores the last key used
	private boolean done = false; // stores whether or not the path has been found
	
	/**
	 * Constructor takes the input file and creates the graph and stores the value of the keys
	 * @param inputFile
	 * @throws LabyrinthException
	 */
	public Labyrinth(String inputFile) throws LabyrinthException {

		try {
			
			BufferedReader input = new BufferedReader(new FileReader(inputFile)); // set up read file
			String line = input.readLine(); // ignore first line
			
			// stores the width and height of the graph and creates graph of that size
			line = input.readLine();
			int width = Integer.parseInt(line);
			line = input.readLine();
			int length = Integer.parseInt(line);
			graph = new Graph(width*length);
			
			// stores the key given into an array
			line = input.readLine();
			keys = new int[line.length()-1];
			for(int i = 0; i<line.length(); i= i+2) {
				// skips every other word and adds the number of keys allocated for that location
				keys[i/2] = Integer.parseInt(String.valueOf(line.charAt(i))); 
			}
			
			// goes through the rest of the lines to store the data of the edges
			int lineNumber = 0;
			line = input.readLine();
			do { 
				for(int i = 0; i<line.length(); i++) { // loops through each char

					char currentChar = line.charAt(i); // stores the char in current char
					
					// stores the start and end nodes
					if (currentChar == 's') start = (lineNumber/2)*(width)+i/2; 
					else if (currentChar == 'x') end = (lineNumber/2)*(width)+i/2;
					
					// if the current char is a corridor or is a door
					else if (currentChar == 'c' || Character.isDigit(currentChar)) {
						int type;
						if(currentChar == 'c' ) type = -1; // set the key type as -1 if it is a corridor since it does not need a key
						else type = Integer.parseInt(String.valueOf(currentChar)); // set the key type as the number given if it is a door
						if(lineNumber%2 == 1) { // if the corridor or door is between two nodes vertically, store the edge as so
							graph.insertEdge(graph.getNode(((lineNumber-1)/2)*(width)+i/2), graph.getNode(((lineNumber+1)/2)*(width)+i/2), type);
						}
						else { // if the corridor or door is between the two nodes horizontally, store the edge as so
							graph.insertEdge(graph.getNode((lineNumber/2)*(width)+(i+1)/2), graph.getNode((lineNumber/2)*(width)+(i-1)/2), type); 
						}
					}
				}
				lineNumber ++;
				line = input.readLine(); // goes to next line
			} while(line!= null);
			
			input.close();

		} catch (Exception e) {
			throw new LabyrinthException("Error: input file error.");
		} 


	}

	/**
	 * Method Graph will return the graph 
	 * @return graph
	 * @throws LabyrinthException
	 */
	public Graph getGraph() throws LabyrinthException {
		if (graph == null) throw new LabyrinthException("Error: Graph is empty"); // throws an exception if graph is empty
		else return graph;
	}

	/**
	 * Method solve will get the path from recursive function getPath()
	 * @return returns the Iterator storing nodes for the path from start to finish
	 */
	public Iterator<Node> solve(){
		try {
			Iterator<Edge> incident = graph.incidentEdges(graph.getNode(start)); // stores all relative edges from the start
			path.add(graph.getNode(start)); // add the start onto the path

			path = findPath(incident); // goes through method findPath which will return the path from start to finish
			if(path!=null) return path.iterator(); // returns the path
			else return null; // returns null if path does not exist
		} 
		// Catch an exception if the graph is not proper
		catch (GraphException e) {
			System.out.println("Error: Exception caught");
		}
		return null;
	}
	
	/**
	 * Method findPath recursively finds the path from start to finish
	 * @param incident contains the edges extending from a node
	 * @return path of nodes from start to finish
	 */
	public Stack<Node> findPath(Iterator<Edge> incident) {
		try {
			do{ // will loop through until there are no more edges left in the iterator
				
				if(!incident.hasNext()) { // if there are no more edges left in the iterator
					uncheck = path.pop(); // take out the last node from the path
					uncheck.setMark(false); // set its mark to false 
					
					if(path.isEmpty()) { // if there is no solution/ path
						path = null; // set path to null
						done = true; // we are finished recursing, so set done to true
						return null;
					}
					
					// returns any key back that has been used to get to that node
					int keyUndo = graph.getEdge(path.peek(), uncheck).getType(); 
					if (keyUndo >=0) { // if its a corridor then do not return a key
						keys[lastKeyUsed]++; // add the key back to the array
					}
					return path; // returns path without the last node
				}
				
				
				
				// stores the next edge in check
				check = incident.next();
								
				// if we've reached the end
				if(check.firstEndpoint().getName() == end) {
					done = true; // set done as true so that the recursive function will continue to return the path
					return path; 
				}
				
				// mark the first checkpoint 
				check.firstEndpoint().setMark(true);

				// if the second checkpoint is not marked yet (has not been visited by this path)
				if(!check.secondEndpoint().getMark()) {
					int keyNeeded = check.getType(); // get the key type
					
					// if it is a corridor 
					if(keyNeeded < 0) {
						// add the node to the path and recurse through its edges to continue finding the path
						path.push(check.secondEndpoint());
						check.secondEndpoint().setMark(true);
						path = findPath(graph.incidentEdges(path.peek()));
					}
					
					// if it is a door
					else {
						// goes through the keys to check if we have a key for this door
						for(int i = keyNeeded; i<keys.length; i++) { 
							// if there is a key 
							if(keys[i]>=1) {
								// use the key and store it as the last key used in case we need to backtrack
								keys[i]--;
								lastKeyUsed = i;
								
								// add the node to the path and recurse through its edges to continue finding the path
								path.push(check.secondEndpoint());
								check.secondEndpoint().setMark(true);
								path = findPath(graph.incidentEdges(path.peek()));
								break;
							}
						}

					}
					
					
				}
				
				if (done) return path; // if we have found the exit and marked done, continues to return path through every recursion 
			}while(check.getType() != end);
		} 
		// Catch an exception if the graph is not proper
		catch (GraphException e) {
			System.out.println("Error: Exception caught");
		}
		// returns null if the path does not exist
		return path;
	}
	

//	private void printPath() {
//		System.out.println("HERE IS THE PATH");
//		int i =0;
//		while(i<path.size()) {
//			System.out.println(path.get(i).getName());
//			i++;
//		}
//	}
}

