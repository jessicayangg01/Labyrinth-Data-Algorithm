//CS2210a 2020
//Jessica Yang
//Student Number: 251083596
//ID: jyang762
//Date: December 1st 2020

import java.util.Iterator;
import java.util.Stack;

public class Graph implements GraphADT {
	
	private Node nodes[];	// an array storing all the nodes
	private Edge edges[][];  // an adjacency matrix storing all the edges
	
	/**
	 * Constructor takes the number of nodes and initializes the nodes and edges of the graph
	 * @param n
	 */
	public Graph(int n) {
		nodes = new Node[n];
		edges = new Edge[n][n];

		// stores all the nodes named numerically
		for (int i=0; i < n; i++){
			nodes[i] = new Node(i);
		}
	}
	
	/**
	 * Method insertEdge will store an edge in the graph given two nodes and the type
	 */
	public void insertEdge(Node nodeu, Node nodev, int type) throws GraphException {
		// checks that both of the nodes are within the range of the nodes array (check if they exist)
		if (nodeu.getName()>=0 && nodev.getName()>=0 && nodeu.getName()<nodes.length && nodev.getName()<nodes.length){
			// checks to make sure that there isn't already an edge stored for these two nodes
			if (edges[nodeu.getName()][nodev.getName()] == null && edges[nodev.getName()][nodeu.getName()] == null){
				// stores the edges in the edge matrix
				edges[nodeu.getName()][nodev.getName()] = new Edge(nodeu, nodev, type);
				edges[nodev.getName()][nodeu.getName()] = new Edge(nodev, nodeu, type);
			}
			else throw new GraphException("Error: Invalid insert node. Edge already exists.");
		} else throw new GraphException("Error: Invalid insert node. Node does not exist");		
	}
	
	/**
	 * Method insertEdge will store an edge in the graph given two nodes, the type and a string label
	 */
	public void insertEdge(Node nodeu, Node nodev, int type, String label) throws GraphException {
		// checks that both of the nodes are within the range of the nodes array (check if they exist)
		if (nodeu.getName()>=0 && nodev.getName()>=0 && nodeu.getName()<nodes.length && nodev.getName()<nodes.length){
			// checks to make sure that there isn't already an edge stored for these two nodes
			if (edges[nodeu.getName()][nodev.getName()] == null && edges[nodev.getName()][nodeu.getName()] == null){
				// stores the edges in the edge matrix
				edges[nodeu.getName()][nodev.getName()] = new Edge(nodeu, nodev, type, label);
				edges[nodev.getName()][nodeu.getName()] = new Edge(nodev, nodeu, type, label);
			}
			else throw new GraphException("Error: Invalid insert node. Edge already exists.");
		} else throw new GraphException("Error: Invalid insert node. Node does not exist");		
	}
	
	/**
	 * getNode method will return a node from the node array given a name
	 */
	public Node getNode(int name) throws GraphException{
		if (name >= 0 && name < nodes.length) return nodes[name]; 
		else throw new GraphException("Error: Node does not exist."); // throws an error if the node is not found
	}
	
	/**
	 * incidentEdges method will return an iterator containing all of the edges corresponding to a node
	 */
	public Iterator<Edge> incidentEdges(Node u) throws GraphException {
		Stack<Edge> incidentEdges = new Stack<Edge>();
		if (getNode(u.getName()) == null) return null; // will throw the exception if node does not exist
		
		// will go through every edge in the row of the node in the edge matrix
		for(int i = 0; i< nodes.length; i++) {
			// if an edge is found then add the edge onto the iterator 
			if (edges[u.getName()][i] != null) incidentEdges.push(edges[u.getName()][i]);
		}
		return incidentEdges.iterator();
	}
	
	/**
	 * method getEdge will return the edge associated with two given nodes
	 */
	public Edge getEdge(Node u, Node v) throws GraphException {
		// checks to see if both of the nodes exist (if they are within the node array)
		if (u.getName()>=0 && u.getName()<nodes.length && v.getName()>=0 && v.getName()<nodes.length) {
			// if there is an edge in the edge matrix between the two nodes given, return the edge
			if (edges[u.getName()][v.getName()]!= null) return edges[u.getName()][v.getName()];
			else throw new GraphException("Error: no edge found between the two nodes"); // otherwise throw an error
		}
		else throw new GraphException("Error: Node does not exist");
		
	}
	
	/**
	 * areAdjacent method will check if two nodes have an edge connecting them
	 */
	public boolean areAdjacent (Node u, Node v) throws GraphException {
		if (getEdge(u, v)!= null) return true; // if getEdge() returns an edge, then they are adjacent
		else return false; // otherwise they are not
		
	}

	

	
	
}