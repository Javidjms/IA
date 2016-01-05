package Model;

import Controleur.Partie;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui modelise un deplacement d'un Personnage
 * @author Gwenole Lecorve
 */
public class Attaque implements Action {
    private Sort sort;
    private List<Personnage> cibles;
    
    /**
     * Constructeur
     * @param sort Sort a lancer
     * @param cible Personnage vise
     */
    public Attaque(Sort sort, Personnage cible) {
        this.sort = sort;
        this.cibles = new ArrayList();
        cibles.add(cible);
    }
    
    /**
     * Constructeur
     * @param sort Sort a lancer
     * @param cibles Personnages vises
     */
    public Attaque(Sort sort, List<Personnage> cibles) {
        this.sort = sort;
        this.cibles = cibles;
    }
    
    /**
     * Ajoute une cible a l'attaque courante
     * @param cible Personnage a ajouter
     */
    public void addCible(Personnage cible) {
        this.cibles.add(cible);
    }
    
    /**
     * Renvoie le sort de l'attaque
     * @return un sort
     */
    public Sort getSort() {
        return sort;
    }
    
    /**
     * Renvoie le personnage vise
     * @return un personnage
     */
    public Personnage getCible() {
        return cibles.get(0);
    }
    
    /**
     * Renvoie la liste des personnages qui seront attaques lors de l'attaque
     * @return une liste de personnages
     */
    public List<Personnage> getPersonnagesAttaques() {
        return cibles;
    }
    
    @Override
    public String toString() {
        String str = getSort().toString() + " sur " + getCible().toString();
        for (int i = 1 ; i < cibles.size(); i++) {
            str +=  " + " + cibles.get(i).toString();
        }
        return str;
    }

    @Override
    public void appliquer(Partie partie) {
        for (Personnage cible : cibles) {
            // Chercher la cible dans la partie (car eventuellement un clone de la partie)
            for (Personnage perso : partie.listerEquipes()) {
                if (cible.equals(perso)) {
                    perso.appliquerSort(getSort());
                }
            }
        }
    }
}
