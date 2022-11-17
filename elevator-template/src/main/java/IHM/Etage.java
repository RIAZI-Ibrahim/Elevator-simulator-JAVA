package IHM;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.border.MatteBorder;

import IHM.IhmPalier.pType;
import elevator.ControlCommand;

import javax.swing.JButton;

public class Etage{
	public  ControlCommand cr = Main.cr;
	public JFrame frame;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Etage window = new Etage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	/**
	 * Create the application.
	 */
	public Etage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 739, 488);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		JButton[] buttonEtages = new JButton[12];
		int h = 369;
		int i;
		for(i = 0; i < 12; i++) {
			buttonEtages[i] = new JButton(Integer.toString(i));
			buttonEtages[i].setForeground(Color.WHITE);
			buttonEtages[i].setBackground(Color.BLACK);
			buttonEtages[i].setBounds(327, h, 85, 21);
			panel.add(buttonEtages[i]);
			h -= 31;
		}
		for(i = 0; i < 12; i++) {
			switch(i){
			case 0:
				buttonEtages[0].addActionListener((ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.paliers[0].frmAscenceur.setVisible(true);
					}
				});
				break;
			case 1:
				buttonEtages[1].addActionListener((ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.paliers[1].frmAscenceur.setVisible(true);
					}
				});
				break;
			case 2:
				buttonEtages[2].addActionListener((ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.paliers[2].frmAscenceur.setVisible(true);
					}
				});
				break;
			case 3:
				buttonEtages[3].addActionListener((ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.paliers[3].frmAscenceur.setVisible(true);
					}
				});
				break;
			case 4:
				buttonEtages[4].addActionListener((ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.paliers[4].frmAscenceur.setVisible(true);					}
				});
				break;
			case 5:
				buttonEtages[5].addActionListener((ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.paliers[5].frmAscenceur.setVisible(true);
					}
				});
				break;
			case 6:
				buttonEtages[6].addActionListener((ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.paliers[6].frmAscenceur.setVisible(true);
					}
				});
				break;
			case 7:
				buttonEtages[7].addActionListener((ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.paliers[7].frmAscenceur.setVisible(true);
					}
				});
				break;
			case 8:
				buttonEtages[8].addActionListener((ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.paliers[8].frmAscenceur.setVisible(true);
					}
				});
				break;
			case 9:
				buttonEtages[9].addActionListener((ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.paliers[9].frmAscenceur.setVisible(true);
					}
				});
				break;
			case 10:
				buttonEtages[10].addActionListener((ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.paliers[10].frmAscenceur.setVisible(true);
					}
				});
				break;
			case 11:
				buttonEtages[11].addActionListener((ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Main.paliers[11].frmAscenceur.setVisible(true);
					}
				});
				break;
			}
		}
	}

}
