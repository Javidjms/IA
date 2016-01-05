package GUI.Vue3;

import Controleur.ControleurFenetre;
import Controleur.ControleurPlacement;
import GUI.Vue3.Joueur.VueJoueurPlacement;
import GUI.Vue3.Plateau.VuePlateauPlacement;

import javax.swing.JButton;

import Model.Joueur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * 
 * Classe interface qui etend VueJeu et qui va gerer l'affichage lors du placement des personnages des deux joueurs
 * @author Warlot/Gasquez
 */

public class VueJeuPlacement extends VueJeu {
	
	/**
	 * Constructeur
	 * Il va instancier une partie gauche pour le joueur 1, une partie droite pour le joueur 2, et une partie centrale pour le plateau
	 * @param controleurParent controleur parent
	 * @param longueur longueur du plateau
	 * @param largeur largeur du plateau
	 * @param listJoueurs liste des joueurs
	 * @param joueurActuel joueur actif
	 */
	public VueJeuPlacement(ControleurFenetre controleurParent, int longueur, int largeur, List<Joueur> listJoueurs, Joueur joueurActuel){
		super(controleurParent, listJoueurs, joueurActuel);
		
		this.panelJoueur1 = new VueJoueurPlacement(getControleur(), listJoueurs.get(0));
		this.panelJoueur2 = new VueJoueurPlacement(getControleur(), listJoueurs.get(1));
		this.panelPlateau = new VuePlateauPlacement(getControleur(), joueurActuel, longueur, largeur);
		
		JButton boutonSuivant = new JButton("Suivant");
		boutonSuivant.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getControleur().suivant();
			}
		});

		panelOuestAdd(panelJoueur1);
		panelEstAdd(panelJoueur2);
		panelCenterAdd(panelPlateau);
		panelSudAdd(boutonSuivant);
		cacherJoueur();
		initPanelJoueurActuel();
		
		getControleur().invalideCotePlateau();
		
		getControleur().afficherPersonnages();
	}

	/****************** SETTERS ******************/
	public void setControleur(ControleurFenetre controleurParent){
		this.controleur = new ControleurPlacement(getMaVue(), controleurParent);
	}
	/****************** GETTERS ******************/
	public ControleurPlacement getControleur() {
		return (ControleurPlacement) controleur;
	}
	public VueJeuPlacement getMaVue(){
		return (VueJeuPlacement) this;
	}
	public VueJoueurPlacement getPanelJoueur1() {
		return (VueJoueurPlacement) panelJoueur1;
	}
	public VueJoueurPlacement getPanelJoueur2() {
		return (VueJoueurPlacement) panelJoueur2;
	}
	public VuePlateauPlacement getPanelPlateau() {
		return (VuePlateauPlacement) panelPlateau;
	}
	public VueJoueurPlacement getPanelJoueurActuel() {
		return (VueJoueurPlacement) panelJoueurActuel;
	}

}
