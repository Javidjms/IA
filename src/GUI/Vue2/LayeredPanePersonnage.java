package GUI.Vue2;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import Controleur.ControleurParamJoueur;
import Model.Personnage;

/**
 * 
 * Classe interface qui comprend nom, Personnage, image, bouton ajout/suppression
 * @author Warlot/Gasquez
 */
public class LayeredPanePersonnage extends JLayeredPane{
	private ControleurParamJoueur controleur = null;
	private Personnage personnage = null;
	
	private int PersonnageImageHeight = 150;
	private int PersonnageNameHeight = 20;
	private int LayeredPanePersonnageHeight = PersonnageNameHeight + PersonnageImageHeight;
	
	/**
	 * Constructeur
	 * @param controleur controleur de l'interface
	 * @param monPersonnage Personnage selectionner
	 */
	public LayeredPanePersonnage(ControleurParamJoueur controleur, Personnage monPersonnage){
		super();
		this.controleur = controleur;
		
		setLayout(null);
		setPreferredSize(new Dimension(monPersonnage.getImage().getIconWidth(), LayeredPanePersonnageHeight));
		setPersonnage(monPersonnage);
		
		JLabel labelNom = new JLabel();
		labelNom.setBounds(0, 0, monPersonnage.getImage().getIconWidth(), PersonnageNameHeight);
		labelNom.setText(monPersonnage.getClasse());
		labelNom.setHorizontalAlignment(JLabel.CENTER);

		JLabel labelImage = new JLabel(monPersonnage.getImage());
		labelImage.setBounds(0, PersonnageNameHeight, monPersonnage.getImage().getIconWidth(), PersonnageImageHeight);
		
		add(labelNom, new Integer(0), 0);
		add(labelImage, new Integer(0), 0);
	}
	
	/**
	 * Ajoute un bouton d'ajout
	 */
	public void ajouterBoutonAjout(){
		LabelBoutonAjouter monLabelBouton = new LabelBoutonAjouter(controleur);
		ajouterBouton(monLabelBouton);
	}
	
	/**
	 * Ajoute un bouton de suppression
	 */
	public void ajouterBoutonSup(){
		LabelBoutonSupprimer monLabelBouton = new LabelBoutonSupprimer(controleur);
		ajouterBouton(monLabelBouton);
	}
	
	/**
	 * affiche et configue les boutons
	 * @param monLabelBouton bouton a afficher
	 */
	public void ajouterBouton(final LabelBouton monLabelBouton){
		add(monLabelBouton, new Integer(1), 0);
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				
				monLabelBouton.mouseExited(e);
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				
				monLabelBouton.mouseEntered(e);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				monLabelBouton.mouseClicked(e);
			}
		});
	}
	
	/**
	 * Retourne le Personnage concerne par le panel
	 * @return Personnage contenu dans le panel
	 */
	public Personnage getPersonnage(){
		return personnage;
	}
	
	/**
	 * Fixe le Personnage concerne par le panel
	 * @param monPersonnage nouveau Personnage contenu par le panel
	 */
	public void setPersonnage(Personnage monPersonnage){
		this.personnage = monPersonnage;
	}
}