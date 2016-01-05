package Controleur;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import GUI.Vue2.LayeredPanePersonnage;
import GUI.Vue2.VueParamJoueur;
import Model.Personnage;

/**
 * Classe de controleur qui gere le parametrage de chaque joueur
 * @author Warlot/Gasquez
 */
public class ControleurParamJoueur {
	protected VueParamJoueur maVue;
	private ControleurFenetre controleurParent = null;
	//List de tous les Personnage disponible pour le jeu
	private List<Personnage> collectionPersonnages = new ArrayList<Personnage>();
	private int tailleEquipeTotal = 1;
	/**
	 * Taille courante de l'equipe
	 */
	private int tailleEquipe = 0;
	
	/**
	 * Constructeur
	 * @param maVue canel comprenant les deux panels joueur et le panel plateau
	 * @param controleurParent controleur parent
	 */
	public ControleurParamJoueur(VueParamJoueur maVue, ControleurFenetre controleurParent){
		this.maVue = maVue;
		this.controleurParent = controleurParent;
		this.collectionPersonnages = getMaPartie().getPersonnagesDisponibles();
		this.tailleEquipeTotal = getMaPartie().getTailleEquipe();
	}

	/**
	 * Ajoute un Personnage a la selection du joueur
	 * @param o Personnage a ajouter
	 */
	public void ajouterPersonnage(Personnage o){
		if (tailleEquipeTotal <= tailleEquipe) return;
		
		tailleEquipe += 1;
		getMaVue().majLabelPlaceRestante(tailleEquipeTotal, tailleEquipe);
		
		LayeredPanePersonnage monLayeredPanePersonnage = new LayeredPanePersonnage(this, o);
		monLayeredPanePersonnage.ajouterBoutonSup();
		
		getMaVue().ajouterPanelPersonnageChoisi(monLayeredPanePersonnage);	
	}

	/**
	 * Supprime un Personnage a la selection du joueur
	 * @param o Personnage a supprimer
	 */
	public void supprimerPersonnage(LayeredPanePersonnage o){
		tailleEquipe -= 1;
		getMaVue().majLabelPlaceRestante(tailleEquipeTotal, tailleEquipe);
		
		getMaVue().suprimerPanelPersonnageChoisi(o);
		
		getMaVue().updateVue();
	}
	
	/**
	 * Passe a la vue suivante et sauvegarde la selection du joueur dans l'equipe du joueur
	 */
	public void suivant(){
		//recherche les Personnage seclectionne
		List<Personnage> collectionPersonnagesJoueur = new ArrayList<Personnage>();
		//Pour chaque Personnage selectionner, on l'ajoute a la collection du joueur
		for (int i = 0; i < getMaVue().getPanelPersonnagesChoisis().getComponents().length; i += 1){
			LayeredPanePersonnage tempPanel = (LayeredPanePersonnage) getMaVue().getPanelPersonnagesChoisis().getComponents()[i];

			collectionPersonnagesJoueur.add(tempPanel.getPersonnage());
		}
		
		boolean err = false; 
		getControleurParent().continueParamJoueur(collectionPersonnagesJoueur, err);

		if (err){
			JOptionPane.showMessageDialog(getMaVue(), "Equipe non pleine.");
		}
	}
	
	/**
	 * Retourne la vue du joueur
	 * @return VueParamJoueur
	 */
	public VueParamJoueur getMaVue(){
		return maVue;
	}

	/**
	 * Retourne le controleur de la fenetre principale
	 * @return ControleurFenetre
	 */
	public ControleurFenetre getControleurParent() {
		return controleurParent;
	}
	
	/**
	 * Retourne la partie en cours
	 * @return Partie
	 */
	public Partie getMaPartie() {
		return getControleurParent().getPartie();
	}

	/**
	 * Retourne la collection de Personnage disponible en jeu
	 * @return liste de Personnage
	 */
	public List<Personnage> getCollectionPersonnages() {
		return collectionPersonnages;
	}
}
