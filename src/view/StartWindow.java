package view;
/**
 * @author Birger Lueers
 * @version 0.3
 */
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class StartWindow extends Observable {

	private JFrame frame;
	private JTextField txtFeldBreite;
	private JTextField txtFeldHoehe;
	private boolean[] checkBox = new boolean[8];
	private JCheckBox chkBxW;
	private JCheckBox chkBxNW;
	private JCheckBox chkBxN;
	private JCheckBox chkBxNE;
	private JCheckBox chkBxE;
	private JCheckBox chkBxSE;
	private JCheckBox chkBxS;
	private JCheckBox chkBxSW;
	private JTextField txtMin;
	private JTextField txtMax;
	private JTextField txtNewLife;
	private int[] rules = new int[3];
	
	
	

	public StartWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		for (int i = 0; i < checkBox.length; i++) {
			checkBox[i] = true;
		}
		
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 329);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblButtonCaption = new JLabel("Zu checkende Ecken:");
		lblButtonCaption.setBounds(10, 54, 196, 14);
		frame.getContentPane().add(lblButtonCaption);

		JLabel lblNewLabel = new JLabel("~ Game Of Life~");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblNewLabel.setBounds(10, 11, 564, 32);
		frame.getContentPane().add(lblNewLabel);
				
				chkBxN = new JCheckBox("");
				chkBxN.setSelected(true);
				chkBxN.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						checkBox[2] = chkBxN.isSelected();
						
					}
				});
				chkBxN.setHorizontalAlignment(SwingConstants.CENTER);
				chkBxN.setBounds(35, 75, 23, 23);
				frame.getContentPane().add(chkBxN);
				
						chkBxNE = new JCheckBox("");
						chkBxNE.setSelected(true);
						chkBxNE.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								checkBox[3] = chkBxNE.isSelected();

							}
						});
						chkBxNE.setHorizontalAlignment(SwingConstants.CENTER);
						chkBxNE.setBounds(60, 75, 23, 23);
						frame.getContentPane().add(chkBxNE);
				
						chkBxE = new JCheckBox("");
						chkBxE.setSelected(true);
						chkBxE.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								checkBox[4] = chkBxE.isSelected();

							}
						});
						chkBxE.setHorizontalAlignment(SwingConstants.CENTER);
						chkBxE.setBounds(60, 101, 23, 23);
						frame.getContentPane().add(chkBxE);
				
						chkBxSE = new JCheckBox("");
						chkBxSE.setSelected(true);
						chkBxSE.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								checkBox[5] = chkBxSE.isSelected();

							}
						});
						chkBxSE.setHorizontalAlignment(SwingConstants.CENTER);
						chkBxSE.setBounds(60, 127, 23, 23);
						frame.getContentPane().add(chkBxSE);
				
				chkBxS = new JCheckBox("");
				chkBxS.setSelected(true);
				chkBxS.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						checkBox[6] = chkBxS.isSelected();

					}
				});
				chkBxS.setHorizontalAlignment(SwingConstants.CENTER);
				chkBxS.setBounds(35, 127, 23, 23);
				frame.getContentPane().add(chkBxS);
		
				chkBxSW = new JCheckBox("");
				chkBxSW.setSelected(true);
				chkBxSW.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						checkBox[7] = chkBxSW.isSelected();

					}
				});
				chkBxSW.setHorizontalAlignment(SwingConstants.CENTER);
				chkBxSW.setBounds(10, 127, 23, 23);
				frame.getContentPane().add(chkBxSW);

		chkBxW = new JCheckBox("");
		chkBxW.setSelected(true);
		chkBxW.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkBox[0] = chkBxW.isSelected();
			}
		});
		chkBxW.setHorizontalAlignment(SwingConstants.CENTER);
		chkBxW.setBounds(10, 101, 23, 23);
		frame.getContentPane().add(chkBxW);

		chkBxNW = new JCheckBox("");
		chkBxNW.setSelected(true);
		chkBxNW.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkBox[1] = chkBxNW.isSelected();

			}
		});
		chkBxNW.setHorizontalAlignment(SwingConstants.CENTER);
		chkBxNW.setBounds(10, 75, 23, 23);
		frame.getContentPane().add(chkBxNW);
		
		JLabel lblRegeln = new JLabel("Regeln:");
		lblRegeln.setBounds(335, 54, 89, 14);
		frame.getContentPane().add(lblRegeln);
		
				JButton btnStart = new JButton("Start!");
				btnStart.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setChanged();
						System.out.println("start geklickt");
						//rules = {txtNewLife.gett,txtMin,txtMax};
						rules[0] = Integer.parseInt(txtNewLife.getText());
						rules[1] = Integer.parseInt(txtMin.getText());
						rules[2] = Integer.parseInt(txtMax.getText()); 
						
						notifyObservers("start");
					}
				});
				btnStart.setBounds(485, 257, 89, 23);
				frame.getContentPane().add(btnStart);
				
				JLabel lblFeldgre = new JLabel("Feldgr\u00F6\u00DFe (quadratische Zellen bei 10:7)");
				lblFeldgre.setBounds(10, 157, 272, 14);
				frame.getContentPane().add(lblFeldgre);
				
				txtFeldBreite = new JTextField();
				txtFeldBreite.setHorizontalAlignment(SwingConstants.RIGHT);
				txtFeldBreite.setText("100");
				txtFeldBreite.setBounds(10, 182, 40, 20);
				frame.getContentPane().add(txtFeldBreite);
				txtFeldBreite.setColumns(10);
				
				txtFeldHoehe = new JTextField();
				txtFeldHoehe.setHorizontalAlignment(SwingConstants.RIGHT);
				txtFeldHoehe.setText("70");
				txtFeldHoehe.setColumns(10);
				txtFeldHoehe.setBounds(86, 182, 40, 20);
				frame.getContentPane().add(txtFeldHoehe);
				
				JLabel lblX = new JLabel("X");
				lblX.setHorizontalAlignment(SwingConstants.CENTER);
				lblX.setBounds(60, 185, 16, 14);
				frame.getContentPane().add(lblX);
				
				JLabel lblZellen = new JLabel("Zellen");
				lblZellen.setBounds(136, 185, 45, 14);
				frame.getContentPane().add(lblZellen);
				
				txtMin = new JTextField();
				txtMin.setHorizontalAlignment(SwingConstants.RIGHT);
				txtMin.setText("2");
				txtMin.setBounds(362, 101, 30, 20);
				frame.getContentPane().add(txtMin);
				txtMin.setColumns(10);
				
				JLabel lblSterbenBei = new JLabel("\u00DCberleben zwischen");
				lblSterbenBei.setBounds(362, 77, 164, 21);
				frame.getContentPane().add(lblSterbenBei);
				
				JLabel lblUnd = new JLabel("und");
				lblUnd.setBounds(402, 101, 40, 21);
				frame.getContentPane().add(lblUnd);
				
				txtMax = new JTextField();
				txtMax.setText("3");
				txtMax.setColumns(10);
				txtMax.setBounds(441, 101, 30, 20);
				frame.getContentPane().add(txtMax);
				
				JLabel lblNachbarn = new JLabel("belebten Nachbarzellen");
				lblNachbarn.setBounds(362, 127, 164, 21);
				frame.getContentPane().add(lblNachbarn);
				
				txtNewLife = new JTextField();
				txtNewLife.setText("3");
				txtNewLife.setBounds(362, 207, 86, 20);
				frame.getContentPane().add(txtNewLife);
				txtNewLife.setColumns(10);
				
				JLabel lblNewLabel_1 = new JLabel("Neues Leben bei genau");
				lblNewLabel_1.setBounds(362, 182, 164, 14);
				frame.getContentPane().add(lblNewLabel_1);
				
				JLabel lblNewLabel_2 = new JLabel("Nachbarn");
				lblNewLabel_2.setBounds(362, 232, 196, 14);
				frame.getContentPane().add(lblNewLabel_2);
	}
	
	public void toggleVisible(){
		frame.setVisible(!frame.isVisible());
	}

	public int getCellHeight() {
		return new Integer(this.txtFeldHoehe.getText());
	}

	public int getCellWidth() {
		return new Integer(this.txtFeldBreite.getText());
	}

	public boolean[] getCheckBox() {
		return this.checkBox;
	}

	public int[] getRules() {
		return this.rules;
	}
}
