/**
 * This class is used for the core of the simulation with field, cells, x, y, etc. The class deals mainly with the information
 * and sending those information to ViewController
 * Note: For all variable it's cells[y][x], but for all methods it's setCells(x, y).
 * @author Minh Chi Nguyen, Alex Stojkovic, Birger Lüers
 */
package model;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class CellularAutomaton extends Observable implements Cloneable {
	
	/** the imaginary field of the simulation, which is bigger than the real field by 2 units*/
	private Cell[][] field; 
	
	/** the real field of cells */
	private Cell[][] cells;
	
	/** timer for the simulation */
	private Timer timer;
	
	/** boolean variable checking if simulation is currently running or not */
	private boolean running;
	
	/** boolean variable for pausing simulation */
	private boolean pause;
	
	/** boolean array of check boxes around corners of the field */
	private boolean[] checkBox;
	
	/** x and y axis of the field */
	private int x, y;
	
	/** int array used for saving the rules from start window */
	private int[] rules;
	
	/**
	 * Constructor of cellular automaton creating a new timer, a new field of (x,y) big and setting the field 
	 * at the beginning all dead (0)
	 * @param x the size of the field in x axis
	 * @param y the size of the field in y axis 
	 * @param checkBox check boxes for corners of the field
	 * @param rules custom rules
	 */
	public CellularAutomaton(int x, int y, boolean[] checkBox, int[] rules) {
		timer = new Timer();
		cells = new Cell[y][x];
		this.checkBox = checkBox; 
		this.rules = rules;
		
		for (int y1 = 0; y1 < cells.length; y1++) {
			for (int x1 = 0; x1 < cells[0].length; x1++) {
				cells[y1][x1] = new Cell(0);
			}
		}
		field = new Cell[y+2][x+2];
		
	}
	
	/**
	 * This method is used to setting all cells on the field alive (1)
	 */
	public void setFieldOn() {
		
		boolean wasOn;
		
		for (int y1 = 0; y1 < cells.length; y1++) {
			for (int x1 = 0; x1 < cells[0].length; x1++) {
				
				if (cells[y1][x1].getDebug() == 0) wasOn = false;
				else wasOn = true;
				
				cells[y1][x1].setDebug(1);
				
				if (!wasOn) {
					this.x = x1;
					this.y = y1;
					setChanged();
					notifyObservers("refresh");
				}
			}
		}
	}
	
	/**
	 * This method kills all the current cells on the field (0)
	 */
	public void setFieldOff() {
		
		boolean wasOn;
		
		for (int y1 = 0; y1 < cells.length; y1++) {
			for (int x1 = 0; x1 < cells[0].length; x1++) {
				
				if (cells[y1][x1].getDebug() == 0) wasOn = false;
				else wasOn = true;
				
				cells[y1][x1].setDebug(0);
				
				if (wasOn) {
					this.x = x1;
					this.y = y1;
					setChanged();
					notifyObservers("refresh");
				}
			}
		}
	}

	/**
	 * Getter for the field simulation
	 * @return the cells on field
	 */
	public Cell[][] getCell() {
		return this.cells;
	}
	
	/**
	 * Getter for debug of a single cell
	 * @param x x axis coordinate of the cell 
	 * @param y y axis coordinate of the cell
	 * @return debug of the cell
	 */
	public int getDebug(int x, int y) {
		return this.cells[y][x].getDebug();
	}
	
	/**
	 * getter for running boolean variable
	 * @return running true or false if simulation is running
	 */
	public boolean isRunning() {
		return this.running;
	}
	
	/**
	 * setter for running boolean variable
	 * @param running setting true or false
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	/**
	 * getter for pause boolean variable
	 * @return pause true or false if simulation is running
	 */
	public boolean isPause() {
		return this.pause;
	}

	/**
	 * getter to access the x coordinate of the cell
	 * @return the x coordinate of the cell
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * getter to access the y coordinate of the cell
	 * @return the y coordinate of the cell
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * getter to access the imaginary field
	 * @return the imaginary field
	 */
	public Cell[][] getField() {
		return this.field;
	}
	
	/**
	 * Method is used to switch all Cell alive by loading a saved file
	 * @param x the coordinate of cells
	 * @param y the coordinate of cells
	 */
	public void switchCell(int x, int y) {	
		if (cells[y][x].getDebug() == 0)	cells[y][x].setDebug(1);
		else if (cells[y][x].getDebug() == 1)	cells[y][x].setDebug(0);
		
		this.x = x;
		this.y = y;
		setChanged();
		notifyObservers("refresh");
	}
	
	/**
	 * Method is used to set cells, if alive to dead, if dead to alive
	 * @param x the x coordinate of cells
	 * @param y the y coordinate of cells
	 */
	public void setCell(int x, int y) {	
		if (cells[y][x].getDebug() == 0)	cells[y][x].setDebug(1);
		else if (cells[y][x].getDebug() == 1)	cells[y][x].setDebug(0);
		
		this.x = x;
		this.y = y;
		setChanged();
	}
	
	/**
	 * Is allowed to call the RemindTask function every 200ms, in case
	 * the simulation window is set to run continuously; which however
	 * is checked in the ViewController.
	 * 
	 * @throws CloneNotSupportedException
	 */
	
	public void run() throws CloneNotSupportedException {
		timer.cancel();
		timer = new Timer();
		timer.schedule(new RemindTask(), 200);
	}
	
	/**
	 * The heart of the model.
	 * 
	 * Calculates which cell shall be born, which cell shall die and 
	 * A class, which is created in this objects run()-method and may only
	 * be executed every 200 ms in case the simulation window is set to run continuously.
	 */
	class RemindTask extends TimerTask{
        public void run() {
			try {
				CopyField();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			x = 0;
			y = 0;
			
			for (int y = 0; y < cells.length; y++) {
				for (int x = 0; x < cells[0].length; x++) {
					if (cells[y][x].getDebug() == 0) {
						BirthRules(x, y);
					} else if (cells[y][x].getDebug() == 1) {
						DyingRules(x, y);
					}
				}
			}
		}
		
	}
	

	/**
	 * The birth rule method
	 * This method is used for dead cells to check if it can be alive in the next turn
	 * by checking all the neighbour cells: from left, top left, top, top right, right,
	 * bottom right, bottom and bottom left cells around it. If the neighbour count fulfills 
	 * the rule the cell will be come alive in the next turn
	 * @param x x coordinate of the cell
	 * @param y y coordinate of the cell
	 */
	public void BirthRules(int x, int y) {
		
		this.x = x;
		this.y = y;
		int neighborCounter = 0;
		int i = x + 1;
		int j = y + 1;
				
		i--;		//links
		if(field[j][i].getDebug() == 1 && checkBox[0]) neighborCounter++;

		j--;		//oben
		if(field[j][i].getDebug() == 1 && checkBox[1]) neighborCounter++;
		
		i++;		//rechts
		if(field[j][i].getDebug() == 1 && checkBox[2]) neighborCounter++;
		
		i++;		//rechts
		if(field[j][i].getDebug() == 1 && checkBox[3]) neighborCounter++;

		j++;		//unten
		if(field[j][i].getDebug() == 1 && checkBox[4]) neighborCounter++;

		j++;		//unten
		if(field[j][i].getDebug() == 1 && checkBox[5]) neighborCounter++;

		i--;		//links
		if(field[j][i].getDebug() == 1 && checkBox[6]) neighborCounter++;

		i--;		//links
		if(field[j][i].getDebug() == 1 && checkBox[7]) neighborCounter++;
		


		if(neighborCounter == rules[0]) {
			cells[y][x].setDebug(1);		
			setChanged();
			notifyObservers("refresh");
		}						
				
		
	}
	
	/**
	 * The dying rule method
	 * This method is pretty much same as the birth rule by using the same way of checking neighbor. 
	 * For an alive cell, it checks all the neighbour cells: from left, top left, top, top right, right,
	 * bottom right, bottom and bottom left cells around it. If the neighbour count fulfills 
	 * the rule the cell will die in the next turn
	 * @param x x coordinate of the cell
	 * @param y y coordinate of the cell
	 */
	public void DyingRules(int x, int y) {

		this.x = x;
		this.y = y;
		int neighborCounter = 0;
		int i = x + 1;
		int j = y + 1;
		
		i--;		//links
		if(field[j][i].getDebug() == 1 && checkBox[0]) neighborCounter++;

		j--;		//oben
		if(field[j][i].getDebug() == 1 && checkBox[1]) neighborCounter++;
		
		i++;		//rechts
		if(field[j][i].getDebug() == 1 && checkBox[2]) neighborCounter++;
		
		i++;		//rechts
		if(field[j][i].getDebug() == 1 && checkBox[3]) neighborCounter++;

		j++;		//unten
		if(field[j][i].getDebug() == 1 && checkBox[4]) neighborCounter++;

		j++;		//unten
		if(field[j][i].getDebug() == 1 && checkBox[5]) neighborCounter++;

		i--;		//links
		if(field[j][i].getDebug() == 1 && checkBox[6]) neighborCounter++;

		i--;		//links
		if(field[j][i].getDebug() == 1 && checkBox[7]) neighborCounter++;
		

		if(neighborCounter > rules[2] || neighborCounter < rules[1]) {
			cells[y][x].setDebug(0);		
			setChanged();				
			notifyObservers("refresh");		
		}
		
	}
	
	/**
	 * The Copy Field method
	 * This method is used to clone our simulation field, not only copying the field, the method can also mirror the field
	 * when the cells go near to the border of the field. Say, if 3 cells right to the left corner of the field, their 
	 * children will be born to the far right of the field due to the mirror effect. This prevents the null point exception 
	 * error and thus making the simulation field always running continuously.
	 * @throws CloneNotSupportedException
	 */
	public void CopyField() throws CloneNotSupportedException {
		for (int y = 0; y < cells.length; y++) {
			for (int x = 0; x < cells[0].length; x++) {
				field[y+1][x+1] = cells[y][x].clone();
			}
		}
		
		
		for(int y = 0; y < cells.length; y++) {							//erste spalte == letzte cellen spalte
			field[y+1][0] = cells[y][cells[0].length-1].clone();
		}
		
		for(int y = 0; y < cells.length; y++) {
			field[y+1][field[0].length-1] = cells[y][0].clone();		//letzte spalte == erste cellen Spalte
		}
		
		for(int x = 0; x < cells[0].length; x++) {
			field[0][x+1] = cells[cells.length-1][x].clone();			//erste Zeile == letzte Zeile
		}
		
		for(int x = 0; x < cells[0].length; x++) {
			field[field.length-1][x+1] = cells[0][x].clone();				//letzte Zeile == erste Zeile
		}
		
		field[0][0] = cells[cells.length-1][cells[0].length-1].clone();			//oben-links - unten-rechts
		field[field.length-1][field[0].length-1] = cells[0][0].clone();			//unten-rechts - oben-links
		field[0][field[0].length-1] = cells[cells.length-1][0].clone();			//oben-rechts - unten-links
		field[field.length-1][0] = cells[0][cells[0].length-1].clone();			//unten-links - oben-rechts
		
	}


}
