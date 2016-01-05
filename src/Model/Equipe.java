package Model;

import java.util.*;

import Exception.ExceptionPersonnage;

/**
 * Modele pour une equipe
 * @author Warlot/Gasquez
 *
 */
public class Equipe {
	private List<Personnage> membres = new ArrayList<Personnage>();
	
	//Info sur le tour de jeu
	//Indique l'etat en cours, attaque, deplacement, rien (passif)
	private enum etatTour{
		DEPLACEMENT, ATTAQUE, PASSIF
	}
	private etatTour monEtatTour = etatTour.PASSIF;
	
	private Sort attaque = null;
	private List<Personnage> personnagesAttaques = null;
	private Personnage personnageActif = null;
	
	/**
	 * Constructeur
	 */
	public Equipe(){
		personnagesAttaques = new ArrayList<Personnage>();
	}
        
        /**
         * Clone l'equipe courante
         * @return une nouvelle equipe
         */
        public Equipe clone() {
                Equipe clone = new Equipe();
                clone.monEtatTour = this.monEtatTour;
                for (Personnage pers : this.membres) {
                    Personnage clone_pers = (Personnage) pers.clone();
                    clone.membres.add(clone_pers);
                    if (pers == this.personnageActif) {
                        clone.personnageActif = clone_pers;
                    }
                }
                for (Personnage pers : this.personnagesAttaques) {
                    clone.personnagesAttaques.add((Personnage) pers.clone());
                }
                return clone;
        }
	
	/**
	 * Lance l'attaque sur les personnages cibles
	 * @return String : le resultat de l'attaque
	 */
	public List<String> lancerAttaque(){
		List<String> resultatAttaque = new ArrayList<String>();
		for (Personnage o : getPersonnagesAttaques()){
			resultatAttaque.add(o.appliquerSort(getAttaque()));
		}
		getPersonnagesAttaques().clear();
		return resultatAttaque;
	}
	
	/**
	 * Ajoute un Personnage a l'equipe
	 * @param personnage Personnage que l'on souhaite ajouter
	 */
	public void ajouterPersonnage(Personnage personnage){
		getMembres().add(personnage);
	}
		
	/**
	 * Indique que tous les personnages sont places
	 * @throws ExceptionPersonnage exception de placement
	 */
	public void personnagesTousPlaces() throws ExceptionPersonnage{
		for(Personnage o : membres){
			o.estPlace();
		}
	}

	/**
	 * Teste si l'etat en cours est deplacement
	 * @return boolean vrai si l'etat est deplacement, faux sinon
	 */
	public boolean isDeplacementEnCours(){
		if (getEtatTour() == getEtatTour().DEPLACEMENT){
			return true;
		}
		return false;
	}
	
	/**
	 * Teste si l'etat en cours est attaque
	 * @return boolean vrai si l'etat est attaque, faux sinon
	 */
	public boolean isAttaqueEnCours(){
		if (getEtatTour() == getEtatTour().ATTAQUE){
			return true;
		}
		return false;
	}

	/**
	 * Teste si l'etat en cours est passif
	 * @return boolean vrai si l'etat est passif, faux sinon
	 */
	public boolean isPassifEnCours(){
		if (getEtatTour() == getEtatTour().PASSIF){
			return true;
		}
		return false;
	}
	
	/**
	 * Reset le Personnage attaquer
	 */
	public void clearPersonnagesAttaques(){
		personnagesAttaques.clear();
	}
	
	/**
	 * Reset l'attaque selectionner
	 */
	public void clearAttaque(){
		setAttaque(null);
	}

	/**
	 * Oublie le personnage actif
	 */
	public void clearPersonnageActif(){
		setPersonnageActif(null);
	}
	
	/**
	 * Retourne les personnage composant l'equipe
	 * @return liste de Personnage
	 */
	public List<Personnage> getMembres() {
		return membres;
	}

	/**
	 * Retourne l'attaque active
	 * @return Sort
	 */
	public Sort getAttaque() {
		return attaque;
	}
	
	/**
	 * Retourne la liste des personnages attaques par l'attaque en cours
	 * @return liste de Personnage
	 */
	public List<Personnage> getPersonnagesAttaques() {
		return personnagesAttaques;
	}
	
	/**
	 * Retourne le personnage actif
	 * @return Personnage
	 */
	public Personnage getPersonnageActif() {
		return personnageActif;
	}
	
	/**
	 * Retourne l'etat du tour de jeu
	 * @return etatTour
	 */
	etatTour getEtatTour() {
		return monEtatTour;
	}
        
        /**
         * Teste si l'équipe courante est battue, c'est-a-dire si tous les personnages sont elimines
         * @return Vrai si l'equipe courante a perdu, faux sinon
         */
        public boolean estBattue() {
            boolean unPersoVivant = false;
            for (Personnage pers : membres) {
                unPersoVivant |= pers.estVivant();
            }
            return !unPersoVivant;
        }
        
        @Override
        public String toString() {
            String str = "";
            for (Personnage pers : membres) {
                str += pers.toString() + "\n";
            }
            return str;
        }
	
	
	/**
	 * Affecte une position au personnage actif
	 * @param maPosition position a affectee
	 */
	public void setPositionPersonnage(Position maPosition){
		getPersonnageActif().setPosition(maPosition);
	}
	
	/**
	 * Fixe les personnages composant l'equipe
	 * @param pFsEquipe nouvelle liste des Personnage de l'equipe
	 */
	private void setMembres(List<Personnage> pFsEquipe) {
		membres = pFsEquipe;
	}

	/**
	 * Fixe l'attaque courrante
	 * @param attaque nouvelle attaque
	 */
	public void setAttaque(Sort attaque) {
		this.attaque = attaque;
	}

	/**
	 * Ajoute un personnage a la liste des personnages attaques
	 * @param personnageAttaque nouveau personnage attaque
	 */
	public void addPersonnageAttaque(Personnage personnageAttaque) {
		this.personnagesAttaques.add(personnageAttaque);
	}

	/**
	 * Fixe le personnage actif
	 * @param personnageActif nouveau personnage actif
	 */
	public void setPersonnageActif(Personnage personnageActif) {
		this.personnageActif = personnageActif;
	}
	
	/**
	 * Fixe l'etat du tour sur deplacement
	 */
	public void setEtatTourDeplacement() {
		this.monEtatTour = monEtatTour.DEPLACEMENT;
	}

	/**
	 * Fixe l'etat du tour sur attaque
	 */
	public void setEtatTourAttaque() {
		this.monEtatTour = monEtatTour.ATTAQUE;
	}

	/**
	 * Fixe l'etat du tour sur passif
	 */
	public void setEtatTourPassif() {
		this.monEtatTour = monEtatTour.PASSIF;
	}
}
