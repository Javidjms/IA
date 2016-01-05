package GUI.Vue3.Plateau;

import java.awt.event.MouseEvent;

import Controleur.ControleurCombat;
import Model.Joueur;
import Model.Position;

/**
 * Etend VuePlateau pour instancier le plateau lors du combat
 * @author Warlot/Gasquez
 *
 */
public class VuePlateauCombat extends VuePlateau {
	/**
	 * Constructeur
	 * @param controleur controleur de cette vue
	 * @param joueurActuel joueur actuel
	 * @param longueur longueur du plateau
	 * @param largeur largeur du plateau
	 */
	public VuePlateauCombat(ControleurCombat controleur, Joueur joueurActuel, int longueur, int largeur){
		super(controleur, joueurActuel, longueur, largeur);
	}
	
	//Modifie le comportement lors du clique sur une case
	@Override
	protected void actionClickCase(MouseEvent e) {
		//On recupere la source de l'evenement
		CasePlateau maCase = (CasePlateau) e.getSource();
		//On recupere les coordonnees de la case qui a ete cliquee
		getControleur().actionClick(
				new Position(
						maCase.getXPlateau(),
						 maCase.getYPlateau())
				);
	}
	
	//Modifie le comportement lorsque l'on rentre dans une case
	@Override
	protected void actionEnteredCase(MouseEvent e) {
		//On recupere la source de l'evenement
		CasePlateau maCase = (CasePlateau) e.getSource();
		//On recupere les coordonnees de la case qui a ete cliquee
		getControleur().actionEntered(
				new Position(
						maCase.getXPlateau(), maCase.getYPlateau())
				);
		
	}
	
	//Modifie le comportement lorsque l'on sort dans une case
	@Override
	protected void actionExitedCase(MouseEvent e) {
		//On recupere la source de l'evenement
		CasePlateau maCase = (CasePlateau) e.getSource();
		//On recupere les coordonnees de la case qui a ete cliquee
		getControleur().actionExited(
				new Position(
						maCase.getXPlateau(), maCase.getYPlateau())
				);
	}
	
	/****************** GETTERS ******************/
	@Override
	public ControleurCombat getControleur() {
		return (ControleurCombat) controleur;
	}

}
