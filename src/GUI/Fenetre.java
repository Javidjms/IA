package GUI;

import java.util.*;

import javax.swing.*;

import Controleur.*;

/**
 * Classe interface qui gere la fenetre generale
 * @author Warlot/Gasquez
 */
public class Fenetre  extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private Partie partie;
	private ControleurFenetre controleur;
	
	/**
	 * Constructeur
	 * @param maPartie la partie
	 */
	public Fenetre(Partie maPartie){
		super("PhantasticasBellum");
		setLocale(Locale.FRENCH);
		
		this.partie = maPartie;
		this.controleur = new ControleurFenetre(this);
		
		controleur.initVue();
		this.setIconImage(new ImageIcon(this.getClass().getResource("/images/logo.png")).getImage());
		
		this.setSize(850, 650);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// 		this.setResizable(false);
	}
        
        /**
	 * Constructeur par défaut
	 * <b> Attention :</b> La partie associée à la fenêtre doit être définie plus tard
	 */
	public Fenetre(){
		super("PhantasticasBellum");
		setLocale(Locale.FRENCH);
		
		this.setIconImage(new ImageIcon(this.getClass().getResource("/images/logo.png")).getImage());
		
		this.setSize(850, 650);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// 		this.setResizable(false);
	}
	
	/**
	 * Permet de changer de vue
	 * @param panelNew nouveau panel a ajouter
	 */
	public void naviguer(JPanel panelNew){
		this.getContentPane().removeAll();
		this.getContentPane().revalidate();
		this.getContentPane().add(panelNew);
	}


	/**
	 * Retourne la partie en cours
	 * @return objet Partie
	 */
	public Partie getPartie() {
		return partie;
	}
	
	/**
	 * Retourne le controleur de la fenetre principale
	 * @return objet ControleurFenetre
	 */
	public ControleurFenetre getControleur() {
		return controleur;
	}
        
        /**
         * Définit la partie associée à la fenêtre courante
         * @param maPartie La partie à associer
         */
        public void setPartie(Partie maPartie) {
            this.partie = maPartie;
            this.controleur = new ControleurFenetre(this);
		
            controleur.initVue();
        }
}
