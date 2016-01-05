package Model;

import java.util.List;

import Exception.ExceptionPersonnage;
import java.awt.Color;
/**
 * 
 * La classe Joueur represente virtuellement le participant, chaque joueur est caracterise par son nom et son equipe
 * @author Warlot/Gasquez/**
 * 
 * La classe Joueur represente virtuellement le participant, chaque joueur est caracterise par son nom et son equipe
 * @author Warlot/Gasquez
 *
 */

public class Joueur {
	private static int value = 1;
	private String nom = "Non defini";
	private Equipe equipe = null;
	private int nombreVictoire = 0;
        private Color couleur;
        
        private static final Color couleurs[] = {new Color(0, 130, 255), Color.RED, Color.MAGENTA, Color.ORANGE};
        private static int couleurIterateur = -1;
        
        private static Color getCouleurSuivante() {
            couleurIterateur = (couleurIterateur + 1) % couleurs.length;
            return couleurs[couleurIterateur];
        }
	
	/******************CONSTRUCTEUR******************/
	/**
	 * Constructeur de la classe Joueur
	 * @param nom nom du joueur
	 */
	public Joueur(String nom){
		setEquipe(new Equipe());
		setNom(nom);
                couleur = getCouleurSuivante();
	}
        
        /**
         * Clone en profondeur l'objet courant
         * @return un nouveau joueur
         */
        public Joueur clone() {
            Joueur clone = new Joueur(this.nom);
            clone.equipe = (Equipe) this.equipe.clone();
            clone.nombreVictoire = this.nombreVictoire;
            clone.couleur = this.couleur;
            return clone;
        }

	/**
	 * Instancie une nouvelle equipe
	 */
	public void clearEquipe(){
		setEquipe(new Equipe());
	}
	
	/**
	 * Lance l'attaque active sur les Personnage cible
	 * @return String
	 */
	public List<String> lancerAttaque(){
		return getEquipe().lancerAttaque();
	}
	
	/**
	 * Donne l'equipe du joueur
	 * @return collection de Personnage composant l'equipe du joueur
	 */
	public List<Personnage> listerEquipe(){
		return getEquipe().getMembres();
	}
	
	/**
	 * Ajoute un Personnage a l'equipe du joueur
	 * @param personnage Personnage a ajouter
	 */
	public void ajouterMembre(Personnage personnage){
		personnage.setProprio(this);
		getEquipe().ajouterPersonnage(personnage);
	}
	
	/**
	 * Verifie si tous les Personnage de l'equipe ont ete place
	 * @throws ExceptionPersonnage exeption si un des Personnage n'a pas ete place
	 */
	public void personnagesTousPlaces() throws ExceptionPersonnage{
		getEquipe().personnagesTousPlaces();
	}
	
	/**
	 * Retourne vrai si l'action courrante est le deplacement, faux sinon
	 * @return booleen
	 */
	public boolean IsdeplacementEnCours(){
		return getEquipe().isDeplacementEnCours();
	}
	
	/**
	 * Retourne vrai si l'action courrante est l'attaque, faux sinon
	 * @return booleen
	 */
	public boolean IsAttaqueEnCours(){
		return getEquipe().isAttaqueEnCours();
	}
	
	/**
	 * Retourne vrai si l'action courrante est nulle, faux sinon
	 * @return booleen
	 */
	public boolean IsPassifEnCours(){
		return getEquipe().isPassifEnCours();
	}
        
        @Override
        public boolean equals(Object obj) {
            if (obj == this){
                    return true;
            }
            if (!(obj instanceof Joueur)){
                    return false;
            }

            Joueur temp = (Joueur) obj;
            return (this.hashCode() == temp.hashCode());
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 59 * hash + (this.nom != null ? this.nom.hashCode() : 0);
            hash = 59 * hash + (this.couleur != null ? this.couleur.hashCode() : 0);
            return hash;
        }
	
	/**
	 * Incremente le nombre de victoire
	 */
	public void signifierVictoire() {
		nombreVictoire += 1;
	}
	
	/**
	 * Fixe le Personnage actif du joueurActuel
	 * @param o nouveau Personnage actif
	 */
	public void setPersonnageActif(Personnage o){
		getEquipe().setPersonnageActif(o);
	}

	/**
	 * Fixe le Personnage actif du joueur
	 * @param o Personnage actif
	 */
	public void setAttaqueActif(Sort o){
		getEquipe().setAttaque(o);
	}
	
	/**
	 * Fixe l'etat du tour en mode passif
	 */
	public void setEtatTourPasser() {
		getEquipe().setEtatTourPassif();
	}

	/**
	 * Fixe l'etat du tour en mode deplacement
	 */
	public void setEtatTourDeplacer() {
		getEquipe().setEtatTourDeplacement();
	}
	
	/**
	 * Fixe l'etat du tour en mode attaque
	 */
	public void setEtatTourAttaquer() {
		getEquipe().setEtatTourAttaque();
	}
	
	/**
	 * Ajoute un Personnage a la liste des Personnage attaque
	 * @param monPersonnage Personnage cible
	 */
	public void addPersonnageAttaque(Personnage monPersonnage){
		getEquipe().addPersonnageAttaque(monPersonnage);
	}
	
	/**
	 * Donne le Personnage actif du joueurActuel
	 * @return Personnage actif
	 */
	public Personnage getPersonnageActif(){
		return getEquipe().getPersonnageActif();
	}
	
	/**
	 * Retourne le Personnage actif
	 * @return Personnage actif du joueur
	 */
	public Sort getAttaqueActif(){
		return getEquipe().getAttaque();
	}
		
	/**
	 * Retourne le nom du joueur
	 * @return String nom
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * Retourne l'equipe du joueur
	 * @return Equipe
	 */
	public Equipe getEquipe() {
		return equipe;
	}
        
        /**
         * Teste si le joueur courant a perdu la partie courante
         */
        public boolean estBattu() {
            return equipe.estBattue();
        }
	
	/**
	 * Retourne le nombre de victoire
	 * @return int
	 */
	public int getNombreVictoire(){
		return nombreVictoire;
	}
	
	/**
	 * Fixe le nom du joueur
	 * @param nom String
	 */
	public void setNom(String nom) {
		if (nom.trim().isEmpty()){
			nom = "J" + value;
			Joueur.value += 1;
		}
		this.nom = nom;
	}
	
	/**
	 * Fixe l'equipe du joueur
	 * @param equipe Equipe
	 */
	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}
	
	/**
	 * Fixe le nombre de victoire
	 * @param nombreVictoire int
	 */
	public void setNombreVictoire(int nombreVictoire){
		this.nombreVictoire = nombreVictoire;
	}

    /**
     * @return the couleur
     */
    public Color getCouleur() {
        return couleur;
    }
}
