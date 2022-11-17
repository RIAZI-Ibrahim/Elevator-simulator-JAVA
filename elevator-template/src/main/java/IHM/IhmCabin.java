package IHM;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import IHM.IhmPalier.pType;
import elevator.ControlCommand;
import elevator.Request;
import elevator.Scheduler;

public class IhmCabin {
	public  ControlCommand cr = Main.cr;
	public JFrame frmCabine;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IhmCabin window = new IhmCabin();
					window.frmCabine.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public IhmCabin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCabine = new JFrame();
		frmCabine.setTitle("Cabine");
		frmCabine.setBounds(100, 100, 739, 488);
		frmCabine.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		frmCabine.getContentPane().add(panel, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel_1.setBorder(new MatteBorder(5, 5, 5, 5, (Color) new Color(0, 0, 255)));
		panel_1.setBounds(228, 10, 235, 431);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel Appel = new JLabel("");
		Appel.setBounds(85, 10, 50, 48);
		panel_1.add(Appel);
		Appel.setIcon(new ImageIcon("/Icons/AppelUrg.png"));
		//Achanger selon a direction
		JLabel directionUP = new JLabel("");
		directionUP.setForeground(Color.BLACK);
		directionUP.setIcon(new ImageIcon("/Icons/UpDown.png"));
		directionUP.setBounds(145, 55, 55, 55);
		panel_1.add(directionUP);
		
		JLabel directionDOWN = new JLabel("");
		directionDOWN.setIcon(new ImageIcon("/Icons/Down.png"));
		directionDOWN.setForeground(Color.BLACK);
		directionDOWN.setBounds(145, 73, 55, 55);
		panel_1.add(directionDOWN);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(85, 68, 50, 48);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel Ecran = new JLabel("New label");
		Ecran.setBounds(0, 0, 50, 48);
		panel_2.add(Ecran);
		Ecran.setBackground(Color.WHITE);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(20, 127, 195, 294);
		panel_1.add(panel_3);
		panel_3.setLayout(null);
		
		JButton btnAppelUrg = new JButton("");
		btnAppelUrg.setBackground(Color.CYAN);
		btnAppelUrg.setForeground(Color.white);
		btnAppelUrg.setIcon(new ImageIcon("/Icons/Phone.png"));
		btnAppelUrg.setBounds(70, 245, 52, 44);
		panel_3.add(btnAppelUrg);
		JButton[] buttonEtages = new JButton[12];
		int width = 10;
		int h = 191;
		int j = 0;
		for(int i = 0; i < 12; i++) {
			buttonEtages[i] = new JButton(Integer.toString(i));
			buttonEtages[i].setForeground(Color.WHITE);
			buttonEtages[i].setBackground(Color.CYAN);
			buttonEtages[i].setBounds(width, h, 52, 44);
			panel_3.add(buttonEtages[i]);
			j++;
			width += 62;
			if (j%3 == 0 && i != 0) {
		    	 h -= 54;
		    	 width = 10;
			}
		}
		for(int i = 0; i < 12; i++) {
		synchronized (this.getClass()) {
				
			switch(i){
			case 0:
				buttonEtages[0].addActionListener((ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Request request = new Request(Request.Type.CABINE, cr.currentEtage, 0);
						cr.addRequest(cr.scheduler, request);
						try {
							Main.paliers[request.getPaliersSource()].go = false;
							cr.checkAndprocess();
							Main.paliers[request.getPaliersSource()].go = false;
							if (cr.openOrder) {
								frmCabine.setVisible(false);
								Thread.sleep(500);
								if(Main.paliers[cr.currentEtage].frmAscenceur.isVisible()) {
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(false);
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(true);
								}
								else if(!Main.paliers[cr.currentEtage].frmAscenceur.isVisible())
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(true);
								Main.paliers[request.getPaliersSource()].go = true;
							}	
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				break;
			case 1:
				buttonEtages[1].addActionListener((ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Request request = new Request(Request.Type.CABINE, cr.currentEtage, 1);
						cr.addRequest(cr.scheduler, request);
						try {
							Main.paliers[request.getPaliersSource()].go = false;
							cr.checkAndprocess();
							Main.paliers[request.getPaliersSource()].go = false;
							if (cr.openOrder) {
								frmCabine.setVisible(false);
							
								Thread.sleep(500);
								if(Main.paliers[cr.currentEtage].frmAscenceur.isVisible()) {
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(false);
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(true);
								}
								else if(!Main.paliers[cr.currentEtage].frmAscenceur.isVisible())
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(true);
								Main.paliers[request.getPaliersSource()].go = true;
							}
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				break;
			case 2:
				buttonEtages[2].addActionListener((ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Request request = new Request(Request.Type.CABINE, cr.currentEtage, 2);
						cr.addRequest(cr.scheduler, request);
						try {
							Main.paliers[request.getPaliersSource()].go = false;
							cr.checkAndprocess();
							Main.paliers[request.getPaliersSource()].go = false;
							if (cr.openOrder) {
								frmCabine.setVisible(false);
								
								Thread.sleep(500);
								if(Main.paliers[cr.currentEtage].frmAscenceur.isVisible()) {
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(false);
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(true);
								}
								else if(!Main.paliers[cr.currentEtage].frmAscenceur.isVisible())
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(true);
								Main.paliers[request.getPaliersSource()].go = true;
							}
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				break;
			case 3:
				buttonEtages[3].addActionListener((ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Request request = new Request(Request.Type.CABINE, cr.currentEtage, 3);
						cr.addRequest(cr.scheduler, request);
						try {
							Main.paliers[request.getPaliersSource()].go = false;
							cr.checkAndprocess();
							Main.paliers[request.getPaliersSource()].go = false;
							if (cr.openOrder) {
								frmCabine.setVisible(false);
								
								Thread.sleep(500);
								if(Main.paliers[cr.currentEtage].frmAscenceur.isVisible()) {
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(false);
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(true);
									
								}
								else if(!Main.paliers[cr.currentEtage].frmAscenceur.isVisible())
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(true);
								Main.paliers[request.getPaliersSource()].go = true;
							}
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				break;
			case 4:
				buttonEtages[4].addActionListener((ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Request request = new Request(Request.Type.CABINE, cr.currentEtage, 4);
						cr.addRequest(cr.scheduler, request);
						try {
							Main.paliers[request.getPaliersSource()].go = false;
							cr.checkAndprocess();
							Main.paliers[request.getPaliersSource()].go = false;
							if (cr.openOrder) {
								frmCabine.setVisible(false);
								Thread.sleep(500);
								if(Main.paliers[cr.currentEtage].frmAscenceur.isVisible()) {
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(false);
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(true);
								}
								else if(!Main.paliers[cr.currentEtage].frmAscenceur.isVisible())
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(true);
								Main.paliers[request.getPaliersSource()].go = true;
							}
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				break;
			case 5:
				buttonEtages[5].addActionListener((ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Request request = new Request(Request.Type.CABINE, cr.currentEtage, 5);
						cr.addRequest(cr.scheduler, request);
						try {
							Main.paliers[request.getPaliersSource()].go = false;
							cr.checkAndprocess();
							Main.paliers[request.getPaliersSource()].go = false;
							if (cr.openOrder) {
								frmCabine.setVisible(false);
								
								Thread.sleep(500);
								if(Main.paliers[cr.currentEtage].frmAscenceur.isVisible()) {
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(false);
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(true);
								}
								else if(!Main.paliers[cr.currentEtage].frmAscenceur.isVisible())
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(true);
								Main.paliers[request.getPaliersSource()].go = true;
							}
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				break;
			case 6:
				buttonEtages[6].addActionListener((ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Request request = new Request(Request.Type.CABINE, cr.currentEtage, 6);
						cr.addRequest(cr.scheduler, request);
						try {
							Main.paliers[request.getPaliersSource()].go = false;
							cr.checkAndprocess();
							Main.paliers[request.getPaliersSource()].go = false;
							if (cr.openOrder) {
								frmCabine.setVisible(false);
								
								Thread.sleep(500);
								if(Main.paliers[cr.currentEtage].frmAscenceur.isVisible()) {
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(false);
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(true);
								}
								else if(!Main.paliers[cr.currentEtage].frmAscenceur.isVisible())
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(true);
								Main.paliers[request.getPaliersSource()].go = true;
							}
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				break;
			case 7:
				buttonEtages[7].addActionListener((ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Request request = new Request(Request.Type.CABINE, cr.currentEtage, 7);
						cr.addRequest(cr.scheduler, request);
						try {
							Main.paliers[request.getPaliersSource()].go = false;
							cr.checkAndprocess();
							Main.paliers[request.getPaliersSource()].go = false;
							if (cr.openOrder) {
								frmCabine.setVisible(false);
								
								Thread.sleep(500);
								if(Main.paliers[cr.currentEtage].frmAscenceur.isVisible()) {
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(false);
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(true);
								}
								else if(!Main.paliers[cr.currentEtage].frmAscenceur.isVisible())
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(true);
								Main.paliers[request.getPaliersSource()].go = true;
							}
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				break;
			case 8:
				buttonEtages[8].addActionListener((ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Request request = new Request(Request.Type.CABINE, cr.currentEtage, 8);
						cr.addRequest(cr.scheduler, request);
						try {
							Main.paliers[request.getPaliersSource()].go = false;
							cr.checkAndprocess();
							Main.paliers[request.getPaliersSource()].go = false;
							if (cr.openOrder) {
								frmCabine.setVisible(false);
								Thread.sleep(500);
								if(Main.paliers[cr.currentEtage].frmAscenceur.isVisible()) {
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(false);
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(true);
								}
								else if(!Main.paliers[cr.currentEtage].frmAscenceur.isVisible())
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(true);
								Main.paliers[request.getPaliersSource()].go = true;
							}
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				break;
			case 9:
				buttonEtages[9].addActionListener((ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Request request = new Request(Request.Type.CABINE, cr.currentEtage, 9);
						cr.addRequest(cr.scheduler, request);
						try {
							Main.paliers[request.getPaliersSource()].go = false;
							cr.checkAndprocess();
							Main.paliers[request.getPaliersSource()].go = false;
							if (cr.openOrder) {
								frmCabine.setVisible(false);
								Thread.sleep(500);
								if(Main.paliers[cr.currentEtage].frmAscenceur.isVisible()) {
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(false);
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(true);
								}
								else if(!Main.paliers[cr.currentEtage].frmAscenceur.isVisible())
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(true);
								Main.paliers[request.getPaliersSource()].go = true;
							}
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				break;
			case 10:
				buttonEtages[10].addActionListener((ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Request request = new Request(Request.Type.CABINE, cr.currentEtage, 10);
						cr.addRequest(cr.scheduler, request);
						try {
							Main.paliers[request.getPaliersSource()].go = false;
							cr.checkAndprocess();
							Main.paliers[request.getPaliersSource()].go = false;
							if (cr.openOrder) {
								frmCabine.setVisible(false);
								Thread.sleep(500);
								if(Main.paliers[cr.currentEtage].frmAscenceur.isVisible()) {
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(false);
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(true);
								}
								else if(!Main.paliers[cr.currentEtage].frmAscenceur.isVisible())
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(true);
								Main.paliers[request.getPaliersSource()].go = true;
							}
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				break;
			case 11:
				buttonEtages[11].addActionListener((ActionListener) new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Request request = new Request(Request.Type.CABINE, cr.currentEtage, 11);
						cr.addRequest(cr.scheduler, request);
						try {
							Main.paliers[request.getPaliersSource()].go = false;
							cr.checkAndprocess();
							if (cr.openOrder) {
								frmCabine.setVisible(false);
								Thread.sleep(500);
								if(Main.paliers[cr.currentEtage].frmAscenceur.isVisible()) {
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(false);
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(true);
								}
								else if(!Main.paliers[cr.currentEtage].frmAscenceur.isVisible())
									Main.paliers[cr.currentEtage].frmAscenceur.setVisible(true);
								Main.paliers[request.getPaliersSource()].go = true;
							}
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				break;
			}
		}
		}
	}
}
