/** This observer class receives information from other classes like SimulationWindow, CellularAutomaton, StartWindow, etc
 *  Using those Informations, this class makes the rules between windows and the core of program.   
 *  The class has rights to access all Methods in other classes
 *  @author Minh Chi Nguyen, Alex Stojkovic, Birger Lüers
 */
package control;

import java.util.*;
import model.CellularAutomaton;
import view.SimulationWindow;
import view.StartWindow;

public class ViewController implements Observer {
	/** variable for starting window */
	private StartWindow startWindow;
	
	/** variable for simulation window */
	private SimulationWindow simulationWindow;
	
	/** variable for cellular automaton */
	private CellularAutomaton automaton;

	/**
	 * Constructor for ViewController, creating the GUI, add an observer and make it visible
	 */
	public ViewController() {
		startWindow = new StartWindow();
		startWindow.addObserver(this);
		startWindow.toggleVisible();
	}

	/**
	 * The update function
	 * 
	 * @param arg0	the input Object
	 * @param arg1	status reports
	 * 				with startWindow always String
	 * 				with simulationWindow always String-array
	 */
	
	public void update(Observable arg0, Object arg1)
	{
		// abfragen fuer meldungen von Fenstern
		// startwindow
		if (arg0 == this.startWindow)
		{
			switch ((String) arg1)
			{
				case "start":
					System.out.println("switch fur start ausgefuhrt!");
					startWindow.toggleVisible();
					simulationWindow = new SimulationWindow(startWindow.getCellWidth(), startWindow.getCellHeight());
					
					automaton = new CellularAutomaton(startWindow.getCellWidth(), startWindow.getCellHeight(), startWindow.getCheckBox(), startWindow.getRules());
					automaton.addObserver(this);
					
					// baue Simulationsfenster
					simulationWindow.toggleVisible();
					startWindow.deleteObserver(this);
					simulationWindow.addObserver(this);
					break;
				default:
					System.out.println("\nunimplemented!!\n");
					break;

			}
		}
		
		// simulationsindow
		// empfaengt ein String Array, laenge muss geprfueft werden
		if (arg0 == this.simulationWindow)
		{
			String[] strs = (String[])arg1;
			if (strs.length > 1)
			{	// string-array ist (mindestens) 2-dimensional; geklickte Position liegt vor! -nicht nur eine simple Statusmeldung
				// gibt aus, an welcher stelle im Fenster geklickt wurde
				automaton.setCell(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]));
			} else {
				// enumeriere simple Statusmeldungen (per switch)
				switch ( strs[0] )
				{
				case "PlayOrPause":
					System.out.println("start geklickt, isPaused: " + simulationWindow.getIsPaused() );
				case "CellChanged":
					if (simulationWindow.getIsPaused() == false){
						try {
							automaton.run();
						} catch (CloneNotSupportedException e1) {
							e1.printStackTrace();
						}
					}
					break;
				case "+1Step":
					try {
						automaton.run();
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
					break;
				
				case "save" :
					simulationWindow.simulationsergebnisSpeichern(automaton.getCell());
					break;
				case "load" :
					int[][] temp = simulationWindow.simulationsergebnisLaden();
					
					if(temp != null) {
						automaton.setFieldOff();
						
						for(int y = 0; y < temp.length; y++) {
							
							for(int x = 0; x < temp[0].length; x++) {
								
								if(temp[y][x] == 1) automaton.switchCell(x, y);
							
							}
						}
					}
					
					break;
					
				case "FillOn":
					automaton.setFieldOn();
					break;
				case "FillOff":
					automaton.setFieldOff();
					break;
				default:
					System.out.println("\nunimplemented!!\n");
					break;
				}
			}
		}
		
		// Cellular automaton
		// sie soll das Feld refreshen und auf schwarz/weiß setzen je nachdem was die Farbe vorher ist.
		if (arg0 == this.automaton) {
			
			switch ((String) arg1) {
				case "refresh":
					simulationWindow.updateView(automaton.getX(), automaton.getY());
			}

		}
	}

}
