/**
 * This class is dedicated for each cell in the simulation with properties like debug, constructor
 * @author Minh Chi Nguyen, Alex Stojkovic, Birger Lüers
 */

package model;

import javax.swing.JEditorPane;
public class Cell implements Cloneable {
	
	/** Variable for each cell if it's on or off (0/1) */
	private int debug;
	
	/** JEditorPane swing for each cell */
	private JEditorPane SwingZelle;
	
	/**
	 * Getter for debug for other classes to access
	 * @return debug
	 */
	public int getDebug() {
		return this.debug;
	}
	
	/**
	 * setter for debug
	 * @param debug debug variable for each cell
	 */
	public void setDebug(int debug) {
		this.debug = debug;
	}
	
	/**
	 * Constructor for Cell, creating a Cell with debug 0 or 1
	 * @param debug debug, int variable 
	 */
	public Cell(int debug) {
		this.debug = debug;
	}
	
	/**
	 * Method to clone cells
	 */
	public Cell clone() throws CloneNotSupportedException{
		Cell c = new Cell(this.debug);
		
		return c;
	}
	
	/**
	 * Setter for swing Cell with JEditorPane
	 * @param jEditorPane
	 */
	public void setSwingZelle(JEditorPane jEditorPane) {
		SwingZelle = jEditorPane;
	}
	
	/**
	 * Getter for swing Cell 
	 * @return SwingZelle swing Cell
	 */
	public JEditorPane getSwingZelle() {
		return SwingZelle;
	}

}
