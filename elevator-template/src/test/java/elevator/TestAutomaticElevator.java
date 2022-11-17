package elevator;

import static elevator.IElevator.State.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestAutomaticElevator {

	@Test
	public void testAutomaticElevator() throws Exception {
		// 3 étages en mode automatique
		var e = new ElevatorSimulator(3, false);
		// activer la montée
		e.up();
		// surveiller l'évolution de l'ascenseur
		while (e.getState() == UP) {
			System.out.printf("level = %3.2f\n", e.getLevel());
			Thread.sleep(100);
		}
		e.stopSimulator();

		// l'ascenseur est au 3ème
		assertEquals(3.0, e.getLevel());
		// l'ascenseur est en erreur
		assertEquals(ERROR, e.getState());
		// les étapes
		assertEquals("-S0-U0-U1-U2-U3-E3", e.getEvents());
	}
	@Test
	public void testAutomaticElevatorPasApas() throws Exception {
		var e = new ElevatorSimulator(3, true);
		// activer la montée
		e.up();
		// surveiller l'évolution de l'ascenseur
		double etage = e.getLevel()*15;
		while (e.getState() == UP) {
			e.oneStep();
			System.out.printf("level = %3.2f\n", e.getLevel());
			Thread.sleep(100);
		}
		e.stopSimulator();

		// l'ascenseur est au 3ème
		assertEquals(3.0, e.getLevel());
		// l'ascenseur est en erreur
		assertEquals(ERROR, e.getState());
		// les étapes
		assertEquals("-S0-U0-U1-U2-U3-E3", e.getEvents());

	}
	@Test
	public void testAutomaticElevatorPasApasO() throws Exception {
		var e = new ElevatorSimulator(3, true);
		// activer la montée
		e.up();
		// surveiller l'évolution de l'ascenseur.
		double etage = 0;
		while (e.getState() == UP) {
			e.oneStep();
			//System.out.printf("level = %3.2f\n", elevator.getLevel());
			if(e.getAndResetStageSensor()){
				System.out.println("Palier ==>" +  ++etage);
			}
			if (etage == 2) e.stopNext();
			Thread.sleep(100);
		}
		e.stopSimulator();
		// l'ascenseur est en door
		assertEquals(DOOR, e.getState());
		// les étapes
		assertEquals("-S0-U0-U1-U2-O3", e.getEvents());

	}
	@Test
	public void testAscenceurAuPremier() throws Exception {
		var e = new ElevatorSimulator(3, true);
		//Ouvrir la port
		if(e.getState() == STOP ) e.openDoor();
		assertEquals(DOOR, e.getState());
		//Fermer la port
		while (e.getState() != STOP) {
			e.oneStep();
		}
		assertEquals(STOP, e.getState());
		// activer la montée
		e.up();
		e.stopNext();
		// Faire monter l'ascenseur au premier.
		while (e.getState() == UP) {
			e.oneStep();
			Thread.sleep(100);
		}
		e.stopSimulator();

		// l'ascenseur est en door
		assertEquals(DOOR, e.getState());
		// les étapes
		assertEquals("-S0-O0-S0-U0-O1", e.getEvents());
	}
	@Test
	public void testErrorReset() throws Exception {
		var e = new ElevatorSimulator(3, true);
		// activer la montée
		e.up();
		// surveiller l'évolution de l'ascenseur.
		double etage = 0;
		while (e.getState() == UP) {
			e.oneStep();
			Thread.sleep(100);
		}
		e.stopSimulator();
		// l'ascenseur est au 1ère
		assertEquals(3.0, e.getLevel());
		// l'ascenseur est en error
		assertEquals(ERROR, e.getState());
		//Reinsalisation de l'ascenceur.
		e.reset();
		//assertEquals(0.0, elevator.getLevel());
		assertEquals("-S0-U0-U1-U2-U3-E3-R3", e.getEvents());
	}
	@Test
	public void testArretUrgence() throws Exception {
		// 3 étages en mode automatique
		var e = new ElevatorSimulator(3, true);
		// activer la montée
		e.up();
		// surveiller l'évolution de l'ascenseur.
		double etage = 0;
		while (e.getState() == UP) {
			e.oneStep();
			//System.out.printf("level = %3.2f\n", elevator.getLevel());
			if(e.getAndResetStageSensor()){
				System.out.println("Palier ==>" +  ++etage);
			}
			if (etage == 2) e.stopNext();
			if (etage > 1) e.halt();
			Thread.sleep(100);
		}
		e.stopSimulator();
		// l'ascenseur est en door
		assertEquals(ERROR, e.getState());
		// les étapes
		assertEquals("-S0-U0-U1-U2-E2", e.getEvents());
	}
	@Test
	public void testGetAndResetFloorButton() throws Exception {
		PanelSimulator p = new PanelSimulator(3);
		assertThat(p.getAndResetFloorButton(2)).isEqualTo(false);
		p.pressFloorButton(2);
		assertThat(p.getAndResetFloorButton(2)).isEqualTo(true);
	}

	@Test
	public void testGetAndResetStopButton() throws Exception {
		PanelSimulator p = new PanelSimulator(3);
		assertThat(p.getAndResetStopButton()).isEqualTo(false);
		p.pressStopButton();
		assertThat(p.getAndResetStopButton()).isEqualTo(true);
	}

	@Test
	public void testGetAndResetInitButton() throws Exception {
		PanelSimulator p = new PanelSimulator(3);
		assertThat(p.getAndResetInitButton()).isEqualTo(false);
		p.pressInitButton();
		assertThat(p.getAndResetInitButton()).isEqualTo(true);
	}

	@Test
	public void testGetAndResetUpButton() throws Exception {
		PanelSimulator p = new PanelSimulator(3);
		assertThat(p.getAndResetUpButton(1)).isEqualTo(false);
		p.pressUpButton(1);
		assertThat(p.getAndResetUpButton(1)).isEqualTo(true);
	}

	@Test
	public void testGetAndResetDownButton() throws Exception {
		PanelSimulator p = new PanelSimulator(3);
		assertThat(p.getAndResetDownButton(0)).isEqualTo(false);
		p.pressDownButton(0);
		assertThat(p.getAndResetDownButton(0)).isEqualTo(true);
	}

	@Test
	public void testPressFloorButton() throws Exception {
		PanelSimulator p = new PanelSimulator(3);
		assertThat(p.getAndResetFloorButton(2)).isEqualTo(false);
		assertThat(p.getAndResetButtonsSensor()).isEqualTo(false);
		p.pressFloorButton(2);
		assertThat(p.getAndResetFloorButton(2)).isEqualTo(true);
		assertThat(p.getAndResetButtonsSensor()).isEqualTo(true);
	}

	@Test
	public void testPressStopButton() throws Exception {
		PanelSimulator p = new PanelSimulator(3);
		assertThat(p.getAndResetStopButton()).isEqualTo(false);
		assertThat(p.getAndResetButtonsSensor()).isEqualTo(false);
		p.pressStopButton();
		assertThat(p.getAndResetStopButton()).isEqualTo(true);
		assertThat(p.getAndResetButtonsSensor()).isEqualTo(true);
	}

	@Test
	public void testPressInitButton() throws Exception {
		PanelSimulator p = new PanelSimulator(3);
		assertThat(p.getAndResetInitButton()).isEqualTo(false);
		assertThat(p.getAndResetButtonsSensor()).isEqualTo(false);
		p.pressInitButton();
		assertThat(p.getAndResetInitButton()).isEqualTo(true);
		assertThat(p.getAndResetButtonsSensor()).isEqualTo(true);
	}

	@Test
	public void testPressUpButton() throws Exception {
		PanelSimulator p = new PanelSimulator(3);
		assertThat(p.getAndResetUpButton(1)).isEqualTo(false);
		assertThat(p.getAndResetButtonsSensor()).isEqualTo(false);
		p.pressUpButton(1);
		assertThat(p.getAndResetUpButton(1)).isEqualTo(true);
		assertThat(p.getAndResetButtonsSensor()).isEqualTo(true);
	}

	@Test
	public void testPressDownButton() throws Exception {
		PanelSimulator p = new PanelSimulator(3);
		assertThat(p.getAndResetDownButton(0)).isEqualTo(false);
		assertThat(p.getAndResetButtonsSensor()).isEqualTo(false);
		p.pressDownButton(0);
		assertThat(p.getAndResetDownButton(0)).isEqualTo(true);
		assertThat(p.getAndResetButtonsSensor()).isEqualTo(true);
	}

	@Test
	public void testgetAndResetButtonsSensor() throws Exception {
		PanelSimulator p = new PanelSimulator(3);
		assertThat(p.getAndResetButtonsSensor()).isEqualTo(false);
		p.pressUpButton(1);
		assertThat(p.getAndResetButtonsSensor()).isEqualTo(true);
	}
//Test les lumière
@Test
	public void testSetFloorLight() throws Exception{
	PanelSimulator p = new PanelSimulator(3);
	assertThat(p.getFloorLight(2)).isEqualTo(false);
	p.setFloorLight(2, true);
	assertThat(p.getFloorLight(2)).isEqualTo(true);
	}
	@Test
	public void testSetDwonLight() throws Exception{
		PanelSimulator p = new PanelSimulator(3);
		assertThat(p.getDownLight(2)).isEqualTo(false);
		p.setDownLight(2, true);
		assertThat(p.getDownLight(2)).isEqualTo(true);
	}
	@Test
	public void testSetUpLight() throws Exception{
		PanelSimulator p = new PanelSimulator(3);
		assertThat(p.getUpLight(2)).isEqualTo(false);
		p.setUpLight(2, true);
		assertThat(p.getUpLight(2)).isEqualTo(true);
	}
	@Test
	public void testGetAndResetOutputIndicator() throws Exception{
		PanelSimulator p = new PanelSimulator(3);
		assertThat(p.getAndResetOutputIndicator()).isEqualTo(false);
		p.setFloorLight(2, true);
		assertThat(p.getAndResetOutputIndicator()).isEqualTo(true);
	}

}
