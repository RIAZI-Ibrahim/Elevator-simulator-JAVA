package elevator;

public class Request {
    public static enum Type {CABINE, PALIER};

    private Scheduler.Direction direction;
    private Type type;
    private int paliersSource;
    private int etageDist;

    /**
     *
     * @param t Le type de la requete
     * @param ps le palier source de l'apelle
     * @param direction la direction souhaité
     */
    public Request(Type t, int ps, Scheduler.Direction direction){
        if (t == Type.PALIER){
            this.type = t;
            this.paliersSource = ps;
            this.etageDist = ps;
            this.direction = direction;
        }
    }

    /**
     *
     * @param t Le type de la requete
     * @param ps le palier source de l'apelle
     * @param ed l'etage distination
     */
    public Request(Type t,int ps, int ed){
        if (t == Type.CABINE){
            this.type = t;
            this.paliersSource = ps;
            this.etageDist = ed;
        }
    }

    public int getPaliersSource() {
        return paliersSource;
    }

    public void setPaliersSource(int paliersSource) {
        this.paliersSource = paliersSource;
    }

    public int getEtageDist() {
        return etageDist;
    }

    public void setEtageDist(int etageDist) {
        this.etageDist = etageDist;
    }
    public Scheduler.Direction getDirection() {
        return direction;
    }

    public Type getType() {
        return type;
    }
}
