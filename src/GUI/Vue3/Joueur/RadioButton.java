package GUI.Vue3.Joueur;

import javax.swing.JRadioButton;

import Model.Sort;
import Model.Personnage;

/**
 * 
 * Classe qui etend JRadioButton et qui va y ajouter les attributs Personnage et Sort et les getters/setters associes
 * @author Warlot/Gasquez
 */
public class RadioButton extends JRadioButton {
	private Personnage monPersonnage = null;
	private Sort monAttaque = null;
	
	/**
	 * Constructeur de la classe RadioButton
	 * @param text String du JRadioButton
	 */
	public RadioButton(String text){
		super(text);
	}
	
	/**
	 * Recupere le Personnage
	 * @return Personnage
	 */
	public Personnage getPersonnage(){
		return monPersonnage;
	}
	
	/**
	 * Retourne l'attaque
	 * @return Sort
	 */
	public Sort getMonAttaque(){
		return monAttaque;
	}
	
	/**
	 * Fixe l'attaque
	 * @param monAttaque nouvelle attaque
	 */
	public void setMonAttaque(Sort monAttaque){
		this.monAttaque = monAttaque;
	}
	
	/**
	 * Fixe le personnage
	 * @param monPersonnage nouveau personnage
	 */
	public void setPersonnage(Personnage monPersonnage){
		this.monPersonnage = monPersonnage;
	}
	
}