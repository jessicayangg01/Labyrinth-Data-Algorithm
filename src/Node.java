//CS2210a 2020
//Jessica Yang
//Student Number: 251083596
//ID: jyang762
//Date: December 1st 2020


public class Node {
	
	// data needed for node
	private int name;
	private boolean mark;
	
	/**
	 * constructor will initialize the name of the node
	 * @param name
	 */
	public Node(int name) {
		this.name = name;
	}
	
	/**
	 * setMark method will change the current mark to the new given mark 
	 * @param mark
	 */
	public void setMark(boolean mark) {
		this.mark = mark;
	}
	
	/**
	 * getMark method will return the mark of the node
	 * @return
	 */
	public boolean getMark() {
		return mark;
	}
	
	/**
	 * getName method will return the name of the node
	 */
	public int getName() {
		return name;
	}
}
