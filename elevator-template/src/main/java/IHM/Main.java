package IHM;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import IHM.IhmPalier.pType;
import elevator.ControlCommand;
import elevator.ElevatorSimulator;
import elevator.Scheduler;

public class Main {
	public static ControlCommand cr = new ControlCommand(new Scheduler(11), new ElevatorSimulator(11, true), 0);
	public static IhmPalier[] paliers = new IhmPalier[12];
	public static void main(String[] args) throws InterruptedException {
		Etage etages = new Etage();
		for(int i = 0; i < 12; i++) {
			if (i == 0) {
				paliers[0] = new IhmPalier(pType.Premier, 0);
				paliers[0].start();
			}
			if (i == 11) {
				paliers[11] = new IhmPalier(pType.Dernier, 11);
				paliers[11].start();
			}
			else if(i > 0 && i < 11) {
				paliers[i] = new IhmPalier(pType.Milieux, i);
				paliers[i].start();
			}
		}
		for(int i = 0; i < 12; i++) {
			paliers[i].join();
		}
		etages.frame.setVisible(true);
		//while()
		
		/*for (int i = 0; i < 12; i++) {
			if (i == 0) paliers[i] = new IhmPalier(cr, pType.Premier);
			if (i == 11) paliers[i] = new IhmPalier(cr, pType.Dernier);
			else if (i > 0 && i < 11) paliers[i] = new IhmPalier(cr, pType.Milieux);
			
		}*/
		//paliers[0].frmAscenceur.setVisible(true);

	}

}
