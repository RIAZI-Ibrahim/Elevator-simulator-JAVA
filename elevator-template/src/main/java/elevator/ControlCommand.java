package elevator;

import java.util.Iterator;
import java.util.LinkedList;

import static elevator.IElevator.State.*;

public class ControlCommand implements IControlCommand {


    public Scheduler scheduler;
    public IElevator elevator;
    public boolean openOrder = false;
    public int currentEtage;
    LinkedList<Request> requests = new LinkedList<>();

    /**
     *
     * @param sh Notre ordennanceur
     * @param e notre ascenceur
     * @param currentEtage l'etage courant
     */
    public ControlCommand (Scheduler sh, IElevator e, int currentEtage){
        this.scheduler = sh;
        this.elevator = e;
        this.currentEtage = currentEtage;
    }

    /**
     * Cette methde permet d'ajouter une requete a la liste d'attente
     * @param sh L'ordonnonceur
     * @param request la requete a ajouter
     */
    @Override
    public void addRequest(Scheduler sh, Request request) {
        requests.add(request);
        if (request.getDirection() == Scheduler.Direction.UP && request.getType() == Request.Type.PALIER)
            sh.addDemmandesPaliersUp(request.getPaliersSource());
        if (request.getDirection() == Scheduler.Direction.DOWN && request.getType() == Request.Type.PALIER)
            sh.addDemmandesPaliersDown(request.getPaliersSource());
        if (request.getType() == Request.Type.CABINE)
            sh.addDemmandesCabine(request.getPaliersSource());
    }

    /**
     * Cette methode permet de surveiller l'ascenceur
     * @param dist l'etage distination
     * @param order l'ordre a faire (monter / descendre / ouvrire les portes / fermer les portes
     * @throws InterruptedException
     */
    @Override
    public void controlElevator(int dist, String order) throws InterruptedException {
        ElevatorSimulator e = (ElevatorSimulator) this.elevator;
        switch (order){
            case "up":
                e.up();
                // surveiller l'évolution de l'ascenseur.
                while (e.getState() == UP) {
                    e.oneStep();
                    if(e.getAndResetStageSensor()){
                        System.out.println("Palier ==>" +  ++this.currentEtage);
                    }
                    if (this.currentEtage == dist - 1) e.stopNext();
                    Thread.sleep(100);
                }
                while (e.getState() != STOP) {
                    e.oneStep();
                }
                openOrder = true;
                e.stopSimulator();
                System.out.println(e.getState());
                break;
            case "down":
                while (e.getState() != STOP) {
                    e.oneStep();
                }
                e.down();
                // surveiller l'évolution de l'ascenseur.
                while (e.getState() == DOWN) {
                    e.oneStep();
                    if(e.getAndResetStageSensor()){
                        System.out.println("Palier ==>" +  -- this.currentEtage);
                    }
                    if (this.currentEtage == dist + 1) e.stopNext();
                    Thread.sleep(100);
                }
                while (e.getState() != STOP) {
                    e.oneStep();
                }
                openOrder = true;
                e.stopSimulator();
                System.out.println(e.getState());
                break;
            case "open":
                e.openDoor();
            case "close":
                while (e.getState() != STOP) {
                    e.oneStep();
                }
        }
    }

   /* public void checkAndprocess(Request newRequest) throws InterruptedException {
        if (newRequest != null){
            this.addRequest(this.scheduler, newRequest);
        }
        else{
            this.checkAndprocess();
        }
    }*/

    /**
     * Cette methde permet de defiler la requete satisfé de la liste d'attente
     * @param request la requete a defiler
     */
    public void satiesfied (Request request){
    	if (request != null) {
    		if (request.getDirection() == Scheduler.Direction.UP && request.getType() == Request.Type.PALIER)
            	this.scheduler.demmandesPaliersUp.set(request.getPaliersSource(), -1);
        	if (request.getDirection() == Scheduler.Direction.DOWN && request.getType() == Request.Type.PALIER)
        		this.scheduler.demmandesPaliersDown.set(request.getPaliersSource(), -1);
        	if (request.getType() == Request.Type.CABINE)
            	this.scheduler.demmandesCabine.set(request.getPaliersSource(), -1);
        	requests.remove(request);
    	}
    }

    /**
     *
     * @throws InterruptedException
     */
    public void checkAndprocess() throws InterruptedException {
        int nexFloor;
        boolean changed = false;
        boolean satisfied = false;
        Request curentRequest;
        Request temp;
        Iterator<Request> i = this.requests.iterator();
        while (!this.requests.isEmpty()) {
            //System.out.println("re:" + this.requests.peek().getPaliersSource());
            //Regle 01 == addRequests
        /*if (elevator.getState() == IElevator.State.UP && curentRequest.getEtageDist() > this.currentEtage
                  && newRequest.getEtageDist() > this.currentEtage){
                  this.elevator.up();
                this.requests.add(newRequest);
            }*/
            //Regle 02
            curentRequest = this.requests.peek();
            System.out.println(curentRequest.getEtageDist() +"//"+ curentRequest.getPaliersSource());
            if (elevator.getState() == IElevator.State.STOP && curentRequest.getEtageDist() > currentEtage) {
                //this.elevator.up();
                nexFloor = this.scheduler.schedule(currentEtage, Scheduler.Direction.UP);
                System.out.println("============" + nexFloor);
                if(curentRequest.getType() == Request.Type.PALIER){
                    if (nexFloor == curentRequest.getPaliersSource()){
                        if (currentEtage < nexFloor)
                            this.controlElevator(nexFloor, "up");
                        else if (currentEtage > nexFloor) this.controlElevator(nexFloor, "down");
                        else {
                            this.satiesfied(curentRequest);
                            satisfied = true;
                            System.out.println("da");
                        }
                    }
                    else if((nexFloor != curentRequest.getPaliersSource()) && this.getRequestBySource(nexFloor) != null) {
                        curentRequest = this.getRequestBySource(nexFloor);
                        changed = true;
                    }
                }else {
                    if (nexFloor == curentRequest.getPaliersSource()) {
                        this.controlElevator(curentRequest.getEtageDist(), "up");
                        this.satiesfied(curentRequest);
                        satisfied = true;

                    } else if ((nexFloor != curentRequest.getPaliersSource()) && this.getRequestBySource(nexFloor) != null) {
                        curentRequest = this.getRequestBySource(nexFloor);
                        changed = true;
                    }
                }
            }

            //Regle 03
            if (elevator.getState() == IElevator.State.STOP && curentRequest.getEtageDist() < currentEtage) {
                //this.elevator.down();
                nexFloor = this.scheduler.schedule(currentEtage, Scheduler.Direction.DOWN);
                System.out.println("============" + nexFloor);
                if (curentRequest.getType() == Request.Type.PALIER) {
                    if (nexFloor == curentRequest.getPaliersSource()) {
                        if (currentEtage > nexFloor)
                            this.controlElevator(nexFloor, "down");
                        else if (currentEtage < nexFloor) this.controlElevator(nexFloor, "up");
                        else {
                            this.satiesfied(curentRequest);
                            satisfied = true;
                        }
                    }
                    else if((nexFloor != curentRequest.getPaliersSource()) && this.getRequestBySource(nexFloor) != null) {
                        curentRequest = this.getRequestBySource(nexFloor);
                        changed = true;
                    }
                }else {
                    if (nexFloor == curentRequest.getPaliersSource()){
                        this.controlElevator(curentRequest.getEtageDist(), "down");
                        this.satiesfied(curentRequest);
                        satisfied = true;
                    }
                    else if((nexFloor != curentRequest.getPaliersSource()) && this.getRequestBySource(nexFloor) != null) {
                        curentRequest = this.getRequestBySource(nexFloor);
                        changed = true;
                    }
                }
            }
            //Regle 04
            if (elevator.getState() == IElevator.State.STOP && curentRequest.getEtageDist() == currentEtage) {
                System.out.println("============ OpenDoor");
                //this.elevator.openDoor();
                this.controlElevator(0, "open");
                if (changed) this.satiesfied(curentRequest);
                else this.satiesfied(this.requests.peek());
                satisfied = true;
            }
            //Regle 05
            if (elevator.getState() == DOOR && curentRequest.getEtageDist() == currentEtage ){
            	openOrder = true;
                this.controlElevator(0, "close");
            }
            if(!satisfied) {
                /*this.requests.set(this.requests.indexOf(curentRequest),this.requests.peek());
                this.requests.set(0, curentRequest);*/
                this.requests.remove(curentRequest);
                this.requests.addFirst(curentRequest);
            }
            satisfied = false;
        }
    }

    public Request getRequestBySource(int dist){
        for (Request request : this.requests) {
            if (request.getPaliersSource() == dist) return request;
        }
        return null;
    }

}
