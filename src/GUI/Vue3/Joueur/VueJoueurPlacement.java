package GUI.Vue3.Joueur;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.*;

import Controleur.ControleurPlacement;
import Model.Joueur;
import Model.Personnage;

/**
 * 
 * Classe interface qui etend VueJoueur et qui gere la partie Joueur (droite et gauche) lors du placement des Personnage sur le plateau
 * @author Warlot/Gasquez
 */
public class VueJoueurPlacement extends VueJoueur {
	private ButtonGroup radioGroup;
	
	/**
	 * Constructeur
 Va instancier selon l'axe Y : la liste des Personnage
	 * @param controleur controleur de cette interface
	 * @param monJoueur joueur associe a ce panel
	 */
	public VueJoueurPlacement(ControleurPlacement controleur, Joueur monJoueur){
		super(controleur, monJoueur);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		radioGroup = new ButtonGroup();
		for (Personnage o : monJoueur.listerEquipe()){
			
			RadioButton radio = new RadioButton(o.getClasse());
			radio.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					RadioButton monRadioButton = (RadioButton) e.getSource();
					
					getControleur().selectionnerPersonnage(monRadioButton.getPersonnage());
				}
			});
			radio.setPersonnage(o);
			radioGroup.add(radio);
			
			add(radio);
		}
	}
	
	/**
	 * Indique qu'un personnage est place
	 * @param o Personnage dont on veut indiquer qu'il est placer
	 */
	public void indiquerPersonnagePlace(Personnage o){
		 for (Enumeration<AbstractButton> buttons = radioGroup.getElements(); buttons.hasMoreElements();) {
			 AbstractButton button = buttons.nextElement();
			 
			 if (button.isSelected()){
				 button.setForeground(Color.GREEN);
				 return;
			 }
		 }
	}
	
	/****************** GETTERS ******************/
	public ControleurPlacement getControleur() {
		return (ControleurPlacement) controleur;
	}
}
