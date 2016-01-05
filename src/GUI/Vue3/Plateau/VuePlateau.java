package GUI.Vue3.Plateau;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import Controleur.AbstractControleurJeu;
import GUI.Vue3.JImagePanel;
import Model.Joueur;
import Model.Personnage;
import Model.Position;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Classe abstraite qui etend JPanel et qui joue le role d'Observer dans le modele Observateur/Observer
 * @author Warlot/Gasquez
 *
 */
public abstract class VuePlateau extends JImagePanel implements Observer{
	protected AbstractControleurJeu controleur;
	private Joueur joueurActuel = null;
	private CasePlateau[][] map;
	private int hauteur = 0;
	private int largeur = 0;
        
        private static final String BACKGROUND_PICTURE = "/images/GrassBackground.jpg";
        
	/**
	 * Constructeur
	 * Instancie le plateau
	 * @param controleur controleur de cette vue
	 * @param joueurActuel joueur actuel
	 * @param hauteur hauteur du plateau
	 * @param largeur largeur du plateau
	 */
	public VuePlateau(AbstractControleurJeu controleur, Joueur joueurActuel, int hauteur, int largeur) {
            try {
                setImage(ImageIO.read(this.getClass().getResource(BACKGROUND_PICTURE)));
            } catch (IOException ex) {
                Logger.getLogger(VuePlateau.class.getName()).log(Level.SEVERE, null, ex);
            }
		this.controleur = controleur;
		this. joueurActuel = joueurActuel;
		this.hauteur = hauteur;
		this.largeur = largeur;
		
		map = new CasePlateau[hauteur][largeur];
		setLayout(new GridLayout(hauteur, largeur));
                
		//Creer le plateau
		for (int i = 0; i < hauteur; i += 1){
			for (int j = 0; j < largeur; j += 1){
				CasePlateau bouton = new CasePlateau(j, i);
				bouton.addMouseListener(new MouseListener() {
					
					@Override
					public void mouseReleased(MouseEvent e) {}
					
					@Override
					public void mousePressed(MouseEvent e) {}
					
					@Override
					public void mouseExited(MouseEvent e) {
						actionExitedCase(e);
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						actionEnteredCase(e);
						
					}
					
					@Override
					public void mouseClicked(MouseEvent e) {
						actionClickCase(e);
					}
				});
				
				map[i][j] = bouton;
				add(bouton);
			}
		}
		
		//Afficher les Personnage qui sont sur le plateau
		afficherLabelCaseDefaut();
                
	}
	
	protected abstract void actionClickCase(MouseEvent e);
	
	protected abstract void actionEnteredCase(MouseEvent e);
	
	protected abstract void actionExitedCase(MouseEvent e);
	
	/**
	 * permet d'enlever la selection sur la moitier gauche du plateau
	 */
	public void disableMoitieGaucheMap(){
		for (int i = 0; i < getMap().length; i += 1){
			for (int j = 0; j < getLargeur() / 2; j += 1){
				getMap()[i][j].setEnabled(false);
			}
		}
	}

	/**
	 * permet d'enlever la selection sur la moitier droite du plateau
	 */
	public void disableMoitieDroiteMap(){
		for (int i = 0; i < getMap().length; i += 1){
			for (int j = getLargeur() / 2; j < getMap()[0].length; j += 1){
				getMap()[i][j].setEnabled(false);
			}
		}
	}
	
	/**
	 * Affiche les couleurs des cases du plateau
	 */
	public void afficherCouleurPlateau(){
		//Set couleur
		enregistrerCouleurPersonnagesJoueur();
		
		//Apply couleur
		for (int i = 0; i < getMap().length; i += 1){
			for (int j = 0; j < getMap()[0].length; j += 1){
				getMap()[i][j].setBackground(
						getMap()[i][j].getMaCouleur()
						);
			}
		}
	}

	/**
	 * Change la couleur des cases des Personnage, allie et ennemie
	 */
	public void enregistrerCouleurPersonnagesJoueur(){
		for(Personnage o : getControleur().getPartie().listerEquipes()){
			Position oPos = o.getPosition();
			Color oCouleur = null;
                        if (o == getControleur().getPartie().getPersonnageActif()){
                                oCouleur = new Color(0, 250, 0);
                        }
                        else {
                                Color c = o.getProprio().getCouleur();
				if (o.isDejaJoue()){
					oCouleur = new Color(Math.min(255, c.getRed()+120),
                                                Math.min(255, c.getGreen()+120),
                                                Math.min(255, c.getBlue()+120));
				} else {
					oCouleur = c;
				}
			}
			getMap()[oPos.getY()][oPos.getX()].setMaCouleur(oCouleur);
		}
	}

	/**
	 * Donne toutes les cases comme ciblable
	 */
	public void toutesLesCasesCiblable(){
//		for (int i = 0; i < getMap().length; i += 1){
//			for (int j = 0; j < getMap()[0].length; j += 1){
//				caseNonCiblable(new Position(j, i));
//			}
//		}
	}
	
	/**
	 * affiche la case ciblable
	 * (Couleur memorisee)
	 * @param maPosition position du Personnage courant
	 */
	public void caseNonCiblable(Position maPosition){
		if (maPosition.getX() < 0 || maPosition.getX() > largeur - 1)	return;
		if (maPosition.getY() < 0 || maPosition.getY() > hauteur - 1)	return;

		getMap()[maPosition.getY()][maPosition.getX()].setMaCouleur(
				new Color(40, 40, 40)
			);
	}
	
	/**
	 * affiche la case ciblee
	 * (Couleur non memorisee)
	 * @param maPosition position du Personnage courant
	 */
	public void caseCible(Position maPosition){
		if (maPosition.getX() < 0 || maPosition.getX() > largeur - 1)	return;
		if (maPosition.getY() < 0 || maPosition.getY() > hauteur - 1)	return;
		
		getMap()[maPosition.getY()][maPosition.getX()].setBackground(
				new Color(100, 100, 100)
			);
	}

	/**
	 * Initialise la couleur du plateau
	 */
	public void afficherPlateauParDefaut(){
		for (int i = 0; i < getMap().length; i += 1){
			for (int j = 0; j < getMap()[0].length; j += 1){
				getMap()[i][j].setMaCouleur(null);
			}
		}
		afficherCouleurPlateau();
	}
	
	/**
	 * Initialise la couleur de la case
	 * @param maPosition case que l'on veu initialise
	 */
	public void caseDefaut(Position maPosition){
		getMap()[maPosition.getY()][maPosition.getX()].setBackground(null);
	}
	
	/**
	 * Recherche les labels des boutons par defaut du plateau
	 */
	public void afficherLabelCaseDefaut(){
		//Affiche les cases comme vides
		for (int i = 0; i < getMap().length; i += 1){
			for (int j = 0; j < getMap()[0].length; j += 1){
				//Afficher la position de la case sur le label du bouton
				//majLabelCase(new Position(j, i),i + " " + j);
				//Affiche un blanc sur la label du bouton
				getCase(new Position(j, i)).nettoyerCase();
			}
		}
		//Affiche les Personnage
		afficherLabelCasePersonnages();
	}
	
	/**
	 * Recherche les labels des boutons des personnage du plateau
	 */
	public void afficherLabelCasePersonnages(){
            Position undefinedPosition = new Position();
		for(Personnage o : getControleur().getPartie().listerEquipes()){
			if (!o.getPosition().equals(undefinedPosition)){
				//Position Personnage defini
				getCase(o.getPosition()).afficherPersonnage(o);
			}
		}
	}
	
        /**
         * Recupere le plateau
         * @return le plateau
         */
	public CasePlateau[][] getMap() {
		return map;
	}
        
        /**
         * Recupere une case du plateau
         * @param pos La position de la case a recuperer
         * @return une case du plateau
         */
        public CasePlateau getCase(Position pos) {
            return map[pos.getY()][pos.getX()];
        }
	
	/**
	 * Methode redefinie pour le design patterns Observer/Observable
	 */
	public void update(Observable o, Object arg){
		//Recharge les labels des boutons
		afficherLabelCaseDefaut();
	}

	
	public abstract AbstractControleurJeu getControleur();
	
	public Joueur getJoueurActuel() {
		return joueurActuel;
	}
	
	public int getHauteur() {
		return hauteur;
	}
	
	public int getLargeur() {
		return largeur;
	}
	
	
	public void setJoueurActuel(Joueur joueurActuel) {
		this.joueurActuel = joueurActuel;
	}

	private void setHauteur(int hauteur) {
		this.hauteur = hauteur;
	}
	
	private void setLargeur(int largeur) {
		this.largeur = largeur;
	}
}
