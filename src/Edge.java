//CS2210a 2020
//Jessica Yang
//Student Number: 251083596
//ID: jyang762
//Date: December 1st 2020

public class Edge {
	
	// all the data needed to store an edge
	private Node u;
	private Node v;
	private int type;
	private String label;
	
	/**
	 * Constructor creates an edge
	 * @param u
	 * @param v
	 * @param type
	 */
	public Edge (Node u, Node v, int type) {
		this.u = u;
		this.v = v;
		this.type = type;
	}
	
	/**
	 * Constructor creates an edge with a string label
	 * @param u
	 * @param v
	 * @param type
	 * @param label
	 */
	public Edge (Node u, Node v, int type, String label) {
		this.u = u;
		this.v = v;
		this.type = type;
		this.label = label;
	}
	
	/**
	 * firstEndPoint returns the first node in the edge
	 * @return
	 */
	public Node firstEndpoint() {
		return u;
	}
	
	/**
	 * secondEndPoint returns the second node in the edge
	 * @return
	 */
	public Node secondEndpoint() {
		return v;
	}
	
	/**
	 * getType returns the type of the edge
	 * @return
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * setType sets the type of the edge the new given type
	 * @param newType
	 */
	public void setType(int newType) {
		type = newType;
	}
	
	/**
	 * getLabel returns the label of the edge
	 * @return
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * setLabel sets a new label for the edge
	 * @param newLabel
	 */
	public void setLabel(String newLabel) {
		label = newLabel;
	}
}
