package elevator;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testContolCommand {
    ControlCommand cr = new ControlCommand(new Scheduler(8), new ElevatorSimulator(8, true), 0);
    @Test
    void testAddRequest(){
        Request request = new Request(Request.Type.PALIER, 3, Scheduler.Direction.UP);
        cr.addRequest(cr.scheduler, request);
        Request request1 = new Request(Request.Type.PALIER, 4, Scheduler.Direction.UP);
        cr.addRequest(cr.scheduler, request1);
        Request request2 = new Request(Request.Type.PALIER, 6, Scheduler.Direction.UP);
        cr.addRequest(cr.scheduler, request2);
        assertEquals(cr.scheduler.demmandesPaliersUp, Arrays.asList(-1, -1, -1, 3, 4, -1, 6, -1, -1));
        Request request3 = new Request(Request.Type.PALIER, 3, Scheduler.Direction.DOWN);
        cr.addRequest(cr.scheduler, request3);
        Request request4 = new Request(Request.Type.PALIER, 4, Scheduler.Direction.DOWN);
        cr.addRequest(cr.scheduler, request4);
        Request request5 = new Request(Request.Type.PALIER, 6, Scheduler.Direction.DOWN);
        cr.addRequest(cr.scheduler, request5);
        assertEquals(cr.scheduler.demmandesPaliersDown, Arrays.asList(-1, -1, -1, 3, 4, -1, 6, -1, -1));

        Request request6 = new Request(Request.Type.CABINE, 2,3);
        cr.addRequest(cr.scheduler, request6);
        Request request7 = new Request(Request.Type.CABINE, 4,4);
        cr.addRequest(cr.scheduler, request7);
        Request request8 = new Request(Request.Type.CABINE, 7,6);
        cr.addRequest(cr.scheduler, request8);
        assertEquals(cr.scheduler.demmandesCabine, Arrays.asList(-1, -1, 2, -1, 4, -1, -1, 7, -1));
    }

    @Test
    void testControlElevator() throws InterruptedException {
        cr.controlElevator(6, "up");
        assertEquals(IElevator.State.STOP, cr.elevator.getState());
        assertEquals(6, cr.currentEtage);

        cr.controlElevator(2, "down");
        assertEquals(IElevator.State.STOP, cr.elevator.getState());
        assertEquals(2, cr.currentEtage);

        cr.controlElevator(0, "open");
        assertEquals(IElevator.State.STOP, cr.elevator.getState());
    }
    @Test
    void testCheckAndprocess02() throws InterruptedException {
        cr.controlElevator(6, "up");
        assertEquals(6, cr.currentEtage);
        Request request01 = new Request(Request.Type.PALIER, 2, Scheduler.Direction.UP);
        cr.addRequest(cr.scheduler, request01);
        cr.checkAndprocess();
    }
    @Test
    void testCheckAndprocess03() throws InterruptedException {
        cr.controlElevator(6, "up");
        assertEquals(6, cr.currentEtage);
        Request request01 = new Request(Request.Type.PALIER, 6, Scheduler.Direction.DOWN);
        cr.addRequest(cr.scheduler, request01);
        cr.checkAndprocess();
    }

    @Test
    void testCheckAndprocess04() throws InterruptedException {
        cr.controlElevator(6, "up");
        assertEquals(6, cr.currentEtage);
        Request request01 = new Request(Request.Type.PALIER, 7, Scheduler.Direction.DOWN);
        cr.addRequest(cr.scheduler, request01);
        cr.checkAndprocess();
    }

    @Test
    void testCheckAndprocess05() throws InterruptedException {
        cr.controlElevator(6, "up");
        assertEquals(6, cr.currentEtage);
        Request request01 = new Request(Request.Type.PALIER, 5, Scheduler.Direction.UP);
        cr.addRequest(cr.scheduler, request01);
        cr.checkAndprocess();
    }
    @Test
    void testCheckAndprocess() throws InterruptedException {
        Request request01 = new Request(Request.Type.PALIER, 5, Scheduler.Direction.UP);
        //Request request011 = new Request(Request.Type.CABINE, 5,8);
        cr.addRequest(cr.scheduler, request01);
        //cr.addRequest(cr.scheduler, request011);
        Request request03 = new Request(Request.Type.PALIER, 3, Scheduler.Direction.DOWN);
        //Request request033 = new Request(Request.Type.CABINE, 3,2);
        cr.addRequest(cr.scheduler, request03);
        //cr.addRequest(cr.scheduler, request033);
        Request request02 = new Request(Request.Type.PALIER, 7, Scheduler.Direction.DOWN);
        //Request request022 = new Request(Request.Type.CABINE, 7,5);
        cr.addRequest(cr.scheduler, request02);
        //cr.addRequest(cr.scheduler, request022);
        Request request04 = new Request(Request.Type.PALIER, 0, Scheduler.Direction.UP);
        //Request request044 = new Request(Request.Type.CABINE, 0,4);
        cr.addRequest(cr.scheduler, request04);
        //cr.addRequest(cr.scheduler, request044);
        //cr.checkAndprocess();
        Request request011 = new Request(Request.Type.CABINE, 5,8);
        cr.addRequest(cr.scheduler, request011);
        Request request033 = new Request(Request.Type.CABINE, 3,2);
        cr.addRequest(cr.scheduler, request033);
        Request request022 = new Request(Request.Type.CABINE, 7,5);
        cr.addRequest(cr.scheduler, request022);
        Request request044 = new Request(Request.Type.CABINE, 0,4);
        cr.addRequest(cr.scheduler, request044);
        cr.checkAndprocess();
    }

}
