package Model;

import Controleur.Partie;

/**
 * Classe qui modelise un deplacement d'un personnage
 * @author Gwenole Lecorve
 */
public class Deplacement implements Action {
    private final Position origine;
    private final Position destination;
    
    public Deplacement(Position origine, Position cible) {
        this.origine = origine;
        this.destination = cible;
    }
    
    public Position getOrigine() {
        return origine;
    }
    
    public Position getDestination() {
        return destination;
    }
    
    public String toString() {
        return "Deplacement de " + getOrigine().toString() + " vers " + getDestination().toString();
    }

    @Override
    public void appliquer(Partie partie) {
        // Chercher la cible dans la partie (car eventuellement un clone de la partie)
        for (Personnage perso : partie.listerEquipes()) {
            if (perso.getPosition().equals(origine)) {
                perso.setPosition(destination);
            }
        }
    }
}
