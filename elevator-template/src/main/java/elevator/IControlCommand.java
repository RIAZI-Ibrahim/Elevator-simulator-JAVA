package elevator;

public interface IControlCommand {
    public void addRequest(Scheduler sh, Request request);
    public void controlElevator(int dist, String order) throws InterruptedException;
}
