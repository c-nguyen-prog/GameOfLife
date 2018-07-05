/**
 * This class renders the view for the simulation.
 * 
 * @author Minh Chi Nguyen, Alex Stojkovic, Birger Lüers
 **/
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.util.ArrayList;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import model.Cell;

public class SimulationWindow extends Observable {
	/** File for input/output */
	private File file;
	
	/** JFrame for simulation */
	private JFrame frame;
	
	/** JPanel for simulation */
	private JPanel panelArray;
	
	/** x and y for the simulation */
	private int x,y;
	
	/** Total number of cells horizontally and total number of cells vertically, other names of x,y */
	private int TotalNumOfCellsHorz, TotalNumOfCellsVert;
	
	/** Cell Component Array */
	private Component[] CellComponentArray1D;
	
	/** a boolean variable to check if the simulation is running or not */
	private boolean isPaused;
	
	/** JButton for Start/Pause button */
	private JButton btnPause;
	/** JButton for +1 Step button */
	private JButton btnOneStep;
	
	/** JButton for save button */
	private JButton btnSave;
	
	/** JButton for load button */
	private JButton btnLoad;
	
	/** JButton for Fill all button*/
	private JButton btnFillOn;
	
	/** JButton for Clear all button */
	private JButton btnFillOff;

	/**
	 * Constructor for the simulation window
	 * boolean isPaused is being set to true, so that the simulation should also be paused when initializing.
	 * 
	 * @param width	Number of Cells horizontally
	 * @param height Number of Cells vertically
	 */
	public SimulationWindow(int width, int height) {
		this.TotalNumOfCellsHorz = width;
		this.TotalNumOfCellsVert = height;
		this.isPaused = true;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * Only a click on the Play/Pause button starts the simulation.
	 * But prior to that, saved models can be loaded or a custom field can be drawn by clicking Cells.
	 * 
	 * Cells of the world are being saved into an array of JPanel named
	 * panelArray, which has a fixed size (1000px * 700px).
	 * 
	 * 
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1037, 820);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panelArray = new JPanel();
		panelArray.setLayout(null);
		panelArray.setBounds(10, 72, 1000, 700);
		frame.getContentPane().add(panelArray);
		
		// height and width of cells are being calculated to fit into the area for the panelArray
		int height = (int) 700 / TotalNumOfCellsVert;
		int width = (int) 1000 / TotalNumOfCellsHorz;
		
		for (y = 0; y < TotalNumOfCellsVert; y++)
		{
			for ( x = 0; x < TotalNumOfCellsHorz; x++)
			{
				Cell Zelle = new Cell(0);
				Zelle.setSwingZelle(new JEditorPane());
				
				Zelle.getSwingZelle().setForeground(Color.WHITE);
				Zelle.getSwingZelle().setBounds((width*x), (height*y), width, height);
				Zelle.getSwingZelle().setBorder(new LineBorder(Color.GRAY));
				Zelle.getSwingZelle().setEditable(false);
				
				final Integer a = x;
				final Integer b = y;
				Zelle.getSwingZelle().addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent arg0) {
						updateView(a,b);
						String[] posStringArray = {a.toString(),b.toString()};
						setChanged();
						notifyObservers(posStringArray);
					}
				});
				// Cells are being added to the panelArray
				panelArray.add(Zelle.getSwingZelle());
			}
		}
		
		// Buttons
		btnPause = new JButton("Play");
		btnPause.setBounds(10, 11, 100, 50);
		
		btnOneStep = new JButton("+1 Step");
		btnOneStep.setBounds(120, 11, 100, 50);
		
		btnSave = new JButton("Save");
		btnSave.setBounds(230, 11, 100, 50);
		
		btnLoad = new JButton("Load");
		btnLoad.setBounds(340, 11, 100, 50);
		
		
		btnFillOn = new JButton("Fill All");
		btnFillOn.setBounds(800, 11, 100, 50);
		
		btnFillOff = new JButton("Clear");
		btnFillOff.setBounds(910, 11, 100, 50);
		
		// Button ActionListeners
		btnPause.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				isPaused = !isPaused;
				if (isPaused){
					btnPause.setText("Play");
				} else {
					btnPause.setText("Pause");
				}
				setChanged();
				String[] strs = {"PlayOrPause"};
				notifyObservers(strs);
			}
		});
		
		btnOneStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setChanged();
				String[] strs = {"+1Step"};
				notifyObservers(strs);
			}
		});
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setChanged();
				String[] strs = {"save"};
				notifyObservers(strs);
			}
		});
		
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setChanged();
				String[] strs = {"load"};
				notifyObservers(strs);
			}
		});
		
		btnFillOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setChanged();
				String[] strs = {"FillOn"};
				notifyObservers(strs);
			}
		});
		
		btnFillOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setChanged();
				String[] strs = {"FillOff"};
				notifyObservers(strs);
			}
		});

		// add all other Components
		frame.getContentPane().add(panelArray);
		frame.getContentPane().add(btnPause);
		frame.getContentPane().add(btnOneStep);
		frame.getContentPane().add(btnSave);
		frame.getContentPane().add(btnLoad);
		frame.getContentPane().add(btnFillOn);
		frame.getContentPane().add(btnFillOff);
		
	}
	
	/** 
	 * Switches visibility of this view from visible to invisible and vice versa.
	 */
	public void toggleVisible(){
		frame.setVisible(!frame.isVisible());
	}
	
	/**
	 * Graphically toggles life of a cell at the given x and y ordinate and sends "CellChanged" to Observers when done.
	 * 
	 * @param xToToggle the X-Component of the coordinate
	 * @param yToToggle the Y-Component of the coordinate
	 */
	public void updateView(int xToToggle, int yToToggle){
		CellComponentArray1D = panelArray.getComponents();		// 1-dimensionales array
		CellComponentArray1D[(xToToggle + TotalNumOfCellsHorz * yToToggle)].setBackground((CellComponentArray1D[xToToggle + TotalNumOfCellsHorz * yToToggle].getBackground() == Color.BLACK ? Color.WHITE : Color.BLACK));
		setChanged();
		String[] strs = {"CellChanged"};
		notifyObservers(strs);
	}
	
	/**
	 * A function needed by the controller to know whether the simulation should be continuously running or be paused.
	 * @return boolean isPaused of this class 
	 */
	public boolean getIsPaused() {
		return this.isPaused;
	}
	
	/**
	 * A function for opening files 
	 * @return input file
	 */
	private File dateiOeffnen() {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile();
		} else {
			return null;
		}
	}
	
	/**
	 * This function is used to load a file into the simulation, decoding the data from the file and then showing on the GUI.
	 * @return the result after decoding from the file
	 */
	public int[][] simulationsergebnisLaden() {
		
		File file = dateiOeffnen();
		if (file != null){
            setFile(file);

            try {
            	FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				List<String> nextLine = new ArrayList<String>();
				nextLine.add(br.readLine());
				
				int i = 0;
				//System.out.println("load 1");
				while (nextLine.get(i) != null) {
					
					nextLine.add(br.readLine());
					i++;
				}
				//System.out.println("load 2");
				String[] hold = nextLine.get(0).split(" ");
				
				int[][] result = new int[i][hold.length];
				
				for (int y = 0; y < i; y++) {
					 
					hold = nextLine.get(y).split(" ");
					for(int x = 0; x < hold.length; x++) {
						
						
						result[y][x] = Integer.parseInt(hold[x]);
						//System.out.print(result[y][x]);
					}
					//System.out.println();
				}
				br.close();
				fr.close();
				return result;
				
				
    		} catch (FileNotFoundException e) {
    			e.printStackTrace();
    			System.out.println("Data can't be read.");
    			System.out.println("Stopping simulation");
    			System.exit(0);
    		} catch (NumberFormatException e) {
    			e.printStackTrace();
       			System.exit(0);
    		} catch (IOException e) {
    			e.printStackTrace();
            }
            
	      }	
		return null;
	}
	
	/**
	 * this method is used to save the current simulation by save all cells into a double arrays 
	 * 0 0 0 0
	 * 0 1 0 1
	 * 0 1 1 1
	 * example for 4x4 field
	 * @param cell 
	 */
	public void simulationsergebnisSpeichern(Cell[][] cell) {
		
			JFileChooser fc = new JFileChooser();
			File file = null;
		
		if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
		}
		
		
		try {
			String ergebnisname = file.getAbsolutePath();
			FileWriter fw = new FileWriter(new File(ergebnisname), false);
			
			
			BufferedWriter bw = new BufferedWriter(fw);
	
			for (int y = 0; y < cell.length; y++) {
				
				for (int x = 0; x < cell[0].length; x++) {
					bw.append(cell[y][x].getDebug() + " ");
				}
				
				bw.newLine();
			}
			bw.close();
			fw.close();
		
			} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	* setter for saving file, using in save method
	*/
	public void setFile(File file) {
		this.file = file;
	}
	
	/**
	* method is used to open a saved file
	*/
	public void modelOeffnen() {
		
		File file = dateiOeffnen();
		if (file != null){
            this.file = file;
	      }
	}	
}
