package GUI.Vue3.Joueur;

import javax.swing.*;

import Controleur.AbstractControleurJeu;
import Model.Joueur;

/**
 * JPanel abstrait qui permet d'instancier une vue des parties joueurs en combat et deplacement
 * @author Warlot/Gasquez
 *
 */
public abstract class VueJoueur extends JPanel{
	protected AbstractControleurJeu controleur;
	private Joueur monJoueur;
	
	/**
	 * Constructeur
	 * @param controleur controleur de cette vue
	 * @param monJoueur joueur associe a la vue
	 */
	public VueJoueur(AbstractControleurJeu controleur, Joueur monJoueur){
		this.controleur = controleur;
		this.monJoueur = monJoueur;
	}

	/**
	 * Retourne la specialisation de AbstractControleurJeu
	 * @return AbstractControleurJeu
	 */
	public abstract AbstractControleurJeu getControleur();
	
	/**
	 *  Retourne le joueur
	 * @return Joueur
	 */
	public Joueur getMonJoueur() {
		return monJoueur;
	}
	
}

