package elevator;

import java.util.LinkedList;
import java.util.Queue;

public class  Scheduler {
    public enum Direction {UP, DOWN};
    public int nbEtages;
    public LinkedList<Integer> demmandesPaliersUp = new LinkedList<>();
    public LinkedList<Integer> demmandesPaliersDown = new LinkedList<>();
    public LinkedList<Integer> demmandesCabine = new LinkedList<>();

    /**
     *
     * @param etages le nombre d'etages
     */
    public Scheduler(int etages){
        nbEtages = etages;
        for (int i = 0; i <= nbEtages; i++) {
            demmandesPaliersUp.add(-1);
            demmandesPaliersDown.add(-1);
            demmandesCabine.add(-1);
        }
    }

    /**
     *
     * @param etage l'etage source de l'apelle
     */
    public  void addDemmandesPaliersUp(int etage) {
        if(etage <= nbEtages)
            demmandesPaliersUp.set(etage, etage);
    }

    /**
     *
     * @param etage l'etage source de l'apelle
     */
    public  void addDemmandesPaliersDown(int etage) {
        if(etage <= nbEtages)
            demmandesPaliersDown.set(etage, etage);
    }

    /**
     *
     * @param etage l'etage source de l'apelle
     */
    public  void addDemmandesCabine(int etage) {
        if(etage <= nbEtages)
            demmandesCabine.set(etage, etage);
    }

    /**
     *  Cette méthde permet de calculer la requete a satisfaire.
     * @param currentEtage L'etage courent
     * @param direction la direction de la cabine
     * @return L'etage le plus prioritaire
     */
    public int schedule(int currentEtage, Direction direction){
        if (direction == Direction.UP){
            while (demmandesPaliersUp.get(currentEtage) != currentEtage && demmandesCabine.get(currentEtage) != currentEtage
                    && currentEtage < nbEtages){
                if (currentEtage < nbEtages) currentEtage ++;
            }
            if (currentEtage == nbEtages){
                while (demmandesPaliersDown.get(currentEtage) != currentEtage && demmandesCabine.get(currentEtage) != currentEtage
                        && currentEtage > 0){
                    currentEtage --;
                }
            }
            return currentEtage;
        }
        else{
            while (demmandesPaliersDown.get(currentEtage) != currentEtage && demmandesCabine.get(currentEtage) != currentEtage
                    && currentEtage > 0){
                if (currentEtage > 0) currentEtage --;

            }
            if (currentEtage == 0){
                while (demmandesPaliersUp.get(currentEtage) != currentEtage && demmandesCabine.get(currentEtage)!= currentEtage
                        && currentEtage < nbEtages){
                    currentEtage ++;
                }
            }
            return currentEtage;
        }
    }
}
