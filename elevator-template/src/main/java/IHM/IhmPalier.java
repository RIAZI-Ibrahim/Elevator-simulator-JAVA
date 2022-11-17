package IHM;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;

import static elevator.IElevator.State.STOP;

import java.awt.BorderLayout;
import javax.swing.border.MatteBorder;

import elevator.ControlCommand;
import elevator.ElevatorSimulator;
import elevator.IElevator;
import elevator.IElevator.State;
import elevator.Request;
import elevator.Scheduler;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class IhmPalier extends Thread{
	public  ControlCommand cr = Main.cr;
	public pType t;
	public static boolean go = true;
	public int etage;
	public static enum pType {Premier, Dernier, Milieux}
	public JFrame frmAscenceur;
	public static IhmCabin cabine = new IhmCabin();
	public void run() {
		synchronized(this.getClass()) {initialize(t, etage);}
	}
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					IhmPalier window = new IhmPalier();
					window.frmAscenceur.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public IhmPalier(pType t, int e) {
		this.t = t;
		etage = e;
		initialize(t, e);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize(pType t, int etage) {
		frmAscenceur = new JFrame();
		frmAscenceur.setTitle("Palier");
		frmAscenceur.getContentPane().setBackground(Color.LIGHT_GRAY);
		frmAscenceur.getContentPane().setForeground(Color.DARK_GRAY);
		
		JPanel panel = new JPanel();
		frmAscenceur.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel portGauche = new JPanel();
		portGauche.setBackground(new Color(30, 144, 255));
		portGauche.setBorder(new MatteBorder(3, 3, 3, 3, (Color) new Color(0, 0, 139)));
		portGauche.setBounds(182, 65, 160, 278);
		panel.add(portGauche);
		
		JPanel portDroit = new JPanel();
		portDroit.setBorder(new MatteBorder(3, 3, 3, 3, (Color) new Color(0, 0, 139)));
		portDroit.setBackground(new Color(30, 144, 255));
		portDroit.setBounds(341, 65, 160, 278);
		panel.add(portDroit);
		/*
		JButton[] buttonEtages = new JButton[12];
		int h = 372;
		
		for(int i = 0; i < 12; i++) {
			buttonEtages[i] = new JButton(Integer.toString(i));
			buttonEtages[i].setForeground(Color.WHITE);
			buttonEtages[i].setBackground(Color.BLACK);
			buttonEtages[i].setBounds(10, h, 85, 21);
			panel.add(buttonEtages[i]);
			h -= 31;
			buttonEtages[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					etage = Integer.parseInt(this.getText());
				}
			});
		}
		*/
		synchronized (this.getClass()) {
		if (t != pType.Dernier) {
		JButton btnUp = new JButton("Up");
		btnUp.setForeground(Color.WHITE);
		btnUp.setBackground(Color.BLACK);
		btnUp.setBounds(511, 200, 85, 21);
		panel.add(btnUp);
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Request request = new Request(Request.Type.PALIER, etage, Scheduler.Direction.UP);
				cr.addRequest(cr.scheduler, request);
				try {
					cr.checkAndprocess();
					while(cabine.frmCabine.isVisible())  wait();
					System.out.println(cr.elevator.getState());
					System.out.print(cr.openOrder);
					if (cr.openOrder) {
						frmAscenceur.setVisible(false);
					    Thread.sleep(500);
					    if(cabine.frmCabine.isVisible()) {
							cabine.frmCabine.setVisible(false);
							cabine.frmCabine.setVisible(true);
						}
						else if(!cabine.frmCabine.isVisible()) cabine.frmCabine.setVisible(true);
					}
				
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		}
		if (t != pType.Premier) {
		JButton btnDown = new JButton("DOWN");
		btnDown.setForeground(Color.WHITE);
		btnDown.setBackground(Color.BLACK);
		btnDown.setBounds(511, 231, 85, 21);
		panel.add(btnDown);
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Request request = new Request(Request.Type.PALIER, etage, Scheduler.Direction.DOWN);
				cr.addRequest(cr.scheduler, request);
				try {
					cr.checkAndprocess();
					while(cabine.frmCabine.isVisible()) wait();
					//cr.addRequest(cr.scheduler, request);
					if (cr.openOrder) {
						frmAscenceur.setVisible(false);
						Thread.sleep(500);
						if(cabine.frmCabine.isVisible()) {
							cabine.frmCabine.setVisible(false);
							cabine.frmCabine.setVisible(true);
						}
					else if(!cabine.frmCabine.isVisible()) cabine.frmCabine.setVisible(true);
					}
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		}
		}
		
		JButton btnReset = new JButton("RESET");
		btnReset.setForeground(Color.WHITE);
		btnReset.setBackground(Color.BLACK);
		btnReset.setBounds(511, 262, 85, 21);
		panel.add(btnReset);
		
		JLabel etageL = new JLabel("Etage N� : " + Integer.toString(etage));
		etageL.setForeground(Color.RED);
		etageL.setBackground(Color.BLACK);
		etageL.setBounds(511, 63, 125, 33);
		panel.add(etageL);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new MatteBorder(2, 2, 2, 2, (Color) Color.RED));
		panel_1.setBackground(Color.BLACK);
		panel_1.setBounds(303, 20, 80, 33);
		panel.add(panel_1);
		panel_1.setLayout(null);
		String Direction;
		if(cr.elevator.getState().toString() == "UP") Direction = "UP";
		if(cr.elevator.getState().toString() != "DOWN") Direction = "UP";
		else Direction = "STOP";
		JLabel etageCabineL = new JLabel("  " + Integer.toString(cr.currentEtage) + "     " + Direction);
		etageCabineL.setForeground(Color.RED);
		etageCabineL.setBounds(0, 0, 80, 33);
		panel_1.add(etageCabineL);
		
		frmAscenceur.setBounds(100, 100, 739, 488);
		frmAscenceur.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
