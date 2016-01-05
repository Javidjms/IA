package Controleur;

import GUI.Vue3.VueJeu;
import GUI.Vue3.VueJeuPlacement;
import GUI.Vue3.Joueur.VueJoueurPlacement;
import Model.Personnage;
import Model.Position;

/**
 * Classe controleur de placement
 * @author Warlot/Gasquez
 */

public class ControleurPlacement extends AbstractControleurJeu{
	public enum coteJeu {
		DROIT, GAUCHE, AUCUN
	}
	//Cote choisit par le joueur
	private static coteJeu coteJeuChoisi = coteJeu.AUCUN;
	
	/**
	 * Constructeur
	 * @param vueGeneraleJeu panel comprenant les deux panels joueur et le panel plateau
	 * @param controleurParent controleur parent
	 */
	public ControleurPlacement(VueJeu vueGeneraleJeu, ControleurFenetre controleurParent){
		super(vueGeneraleJeu, controleurParent);
	}
	
	/**
	 * Enleve la partie du plateau selectionner par le joueur.
	 */
	public void invalideCotePlateau(){
		if (ControleurPlacement.coteJeuChoisi == coteJeu.GAUCHE){
			getVue().getPanelPlateau().disableMoitieGaucheMap();
		} else if(ControleurPlacement.coteJeuChoisi == coteJeu.DROIT){
			getVue().getPanelPlateau().disableMoitieDroiteMap();
		}
	}

	/**
	 * Enregistre dans la partie le Personnage selectionner
	 * @param o Personnage selectionner
	 */
	public void selectionnerPersonnage(Personnage o){
		getPartie().setPersonnageActif(o);
	}

	/**
	 * On place le Personnage sur le plateau
	 * @param maPosition position choisie par le joueur
	 */
	public void placerPersonnage(Position maPosition){
		//Si aucun Personnage selectionne alors on ne fait rien
		if (getPartie().getPersonnageActif() == null) return;

		//Affecter la position au Personnage actuel
		if (getPartie().setPositionPersonnage(maPosition) == false){
			return;
		}
		
		//Si premier placement
		if (ControleurPlacement.coteJeuChoisi == coteJeu.AUCUN){
			//On invalde l'autre moitie du plateau
			if (maPosition.getX() >= getVue().getPanelPlateau().getLargeur() / 2){
				ControleurPlacement.coteJeuChoisi = coteJeu.DROIT;
				getVue().getPanelPlateau().disableMoitieGaucheMap();
			} else {
				ControleurPlacement.coteJeuChoisi = coteJeu.GAUCHE;
				getVue().getPanelPlateau().disableMoitieDroiteMap();
			}
		}
		
		VueJoueurPlacement panelJoueurActuel = getVue().getPanelJoueurActuel();
		
		Position oldPosition = getPartie().getPersonnageActif().getPosition();
		
		if (oldPosition.equals(new Position())){
			//Si position non initialise (vide)
			oldPosition.setX(maPosition.getX());
			oldPosition.setY(maPosition.getY());
		} else {
			oldPosition.setX(oldPosition.getX());
			oldPosition.setY(oldPosition.getY());
		}

		panelJoueurActuel.indiquerPersonnagePlace(
				getPartie().getPersonnageActif()
				);

		getVue().getPanelPlateau().getCase(maPosition).afficherPersonnage(getPartie().getPersonnageActif());
		getVue().getPanelPlateau().afficherLabelCaseDefaut();
		getVue().getPanelPlateau().afficherCouleurPlateau();
	}
	
	/**
	 * Passe a la vue suivante
	 */
	public void suivant(){
		getControleurParent().continueGeneralJeu();
	}
	
	/**
	 * Renvoie la vue courante
	 */
	public VueJeuPlacement getVue(){
		return (VueJeuPlacement) vue;
	}

	/**
	 * Renvoie le cote du plateau choisit
	 * @return the coteJeuChoisi
	 */
	public static coteJeu getCoteJeuChoisi() {
		return coteJeuChoisi;
	}
	/****************** SETTERS ******************/
	/**
	 * Modifie le cote du plateau choisit
	 * @param coteJeuChoisi the coteJeuChoisi to set
	 */
	public static void setCoteJeuChoisi(coteJeu coteJeuChoisi) {
		ControleurPlacement.coteJeuChoisi = coteJeuChoisi;
	}
}
