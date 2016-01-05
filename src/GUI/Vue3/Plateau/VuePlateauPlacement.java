package GUI.Vue3.Plateau;

import java.awt.event.MouseEvent;

import Controleur.*;
import Model.Joueur;
import Model.Position;

/**
 * Etend VuePlateau pour instancier le plateau lors du placement
 * @author Warlot/Gasquez
 *
 */
public class VuePlateauPlacement extends VuePlateau {
	/**
	 * Constructeur
	 * @param controleur controleur de la vue
	 * @param joueurActuel joueur actuel
	 * @param longueur longueur du plateau
	 * @param largeur largeur du plateau
	 */
	public VuePlateauPlacement(ControleurPlacement controleur, Joueur joueurActuel, int longueur, int largeur){
		super(controleur, joueurActuel, longueur, largeur);
	}
	
	/**
	 * Affiche le personnage a la position cliquee
	 * @param e evenement genere lors du clique sur une case
	 */
	protected void actionClickCase(MouseEvent e) {
		CasePlateau maCase = (CasePlateau) e.getSource();
		
		if (!maCase.isEnabled()) return;
		
		getControleur().placerPersonnage(new Position(maCase.getXPlateau(), maCase.getYPlateau()));
		getControleur().afficherPersonnages();
	}
	
	@Override
	protected void actionEnteredCase(MouseEvent e) {}

	@Override
	protected void actionExitedCase(MouseEvent e) {}

	/****************** GETTERS ******************/
	public ControleurPlacement getControleur() {
		return (ControleurPlacement) controleur;
	}
}
