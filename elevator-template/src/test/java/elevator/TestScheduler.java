package elevator;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestScheduler {
    Scheduler sh = new Scheduler(7);
    @Test
    void testAddDemmandesPaliersUp(){
        sh.addDemmandesPaliersUp(1);
        sh.addDemmandesPaliersUp(3);
        assertEquals(sh.demmandesPaliersUp, Arrays.asList(-1, 1, -1, 3, -1, -1, -1, -1));
    }
    @Test
    void testAddDemmandesPaliersDown(){
        sh.addDemmandesPaliersDown(0);
        sh.addDemmandesPaliersDown(2);
        assertEquals(sh.demmandesPaliersDown, Arrays.asList(0, -1, 2, -1, -1, -1, -1, -1));
    }
    @Test
    void testAddDemmandesCabine(){
        sh.addDemmandesCabine(0);
        sh.addDemmandesCabine(2);
        assertEquals(sh.demmandesCabine, Arrays.asList(0, -1, 2, -1, -1, -1, -1, -1));
    }
    @Test
    void testSchedule() {
        assertEquals(0, sh.schedule(3, Scheduler.Direction.UP));
        sh.addDemmandesCabine(4);
        sh.addDemmandesPaliersUp(1);
        sh.addDemmandesPaliersDown(6);
        assertEquals(6, sh.schedule(6, Scheduler.Direction.UP));
        assertEquals(6, sh.schedule(5, Scheduler.Direction.UP));
        assertEquals(4, sh.schedule(2, Scheduler.Direction.UP));
        assertEquals(1, sh.schedule(0, Scheduler.Direction.UP));
        assertEquals(1, sh.schedule(3, Scheduler.Direction.DOWN));
        assertEquals(4, sh.schedule(5, Scheduler.Direction.DOWN));
        assertEquals(6, sh.schedule(7, Scheduler.Direction.DOWN));
    }
}
