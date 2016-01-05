
package GUI.Vue4;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controleur.ControleurFenetre;
import Model.Joueur;

/**
 * Vue qui etend JPanel et qui contient la vue de fin du jeu
 * @author Warlot/Gasquez
 *
 */
public class VueFinJeu extends JPanel{
	protected ControleurFenetre controleur;
	
	/**
	 * Constructeur
	 * Fixe son contenue au centre verticalement
	 * @param controleur controleur de la vue
	 */
	public VueFinJeu(ControleurFenetre controleur){
		super();
		this.controleur = controleur;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Component verticalGlueHaut = Box.createVerticalGlue();
		Component verticalGlueBas = Box.createVerticalGlue();
		AlignementX alignementX = new AlignementX(controleur);
		add(verticalGlueHaut);
		add(alignementX);
		add(verticalGlueBas);
	}
}

/**
 * Classe qui permet de centrer son contenue horizontalement
 * @author Warlot/Gasquez
 *
 */
class AlignementX extends JPanel{
	protected ControleurFenetre controleur;
	/**
	 * Constructeur
	 * @param controleur controleur de la vue
	 */
	public AlignementX(ControleurFenetre controleur){
		super(); 
		this.controleur = controleur ;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		Contenue contenue = new Contenue(controleur);
		Component horizontalGlueGauche = Box.createHorizontalGlue();
		Component horizontalGlueDroite = Box.createHorizontalGlue();
		add(horizontalGlueGauche);
		add(contenue);
		add(horizontalGlueDroite);
	}
}

/**
 * Class qui etend JPanel et qui contient tous ce que l'on veut afficher
 * @author Warlot/Gasquez
 *
 */
class Contenue extends JPanel{

	/**
	 * Constructeur
	 * @param controleur Controleur de la vue
	 */
	public Contenue(ControleurFenetre controleur){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		Joueur joueurGagnant = null;
		boolean unJoueurEstGagnant = false;
		List<Joueur> tousLesJoueurs = controleur.getPartie().getJoueurs();
		
		if(controleur.getPartie().listerEquipes().isEmpty() != true) {
			unJoueurEstGagnant = true;
			joueurGagnant = controleur.getPartie().listerEquipes().get(0).getProprio();
		} else {
			unJoueurEstGagnant = false;
		}
		
		JButtonRetry jbuttonRetry = new JButtonRetry(controleur);
                JButton jbuttonRetrySameTeam = new JButton("Recommencer avec les m\u00EAmes param\\u00E8tres");
                jbuttonRetrySameTeam.addActionListener(
                    new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            
                        }
                    }
                );
		JLabel jLabelAffichage = new JLabel("Fin du jeu");
		JLabel jLabelEspace1 = new JLabel(" ");
		JLabel jLabelEspace2 = new JLabel(" ");
		JLabel jLabelEspace3 = new JLabel(" ");
		JLabel jLabelScore= new JLabel("Les scores sont de :");
		JLabel jLabelJoueurGagnant = new JLabel("test");
		JLabel jLabelScoreJ1= new JLabel(tousLesJoueurs.get(0).getNom() +" : "+ tousLesJoueurs.get(0).getNombreVictoire());
		JLabel jLabelScoreJ2= new JLabel(tousLesJoueurs.get(1).getNom() +" : "+ tousLesJoueurs.get(1).getNombreVictoire());
		if(unJoueurEstGagnant == false){jLabelJoueurGagnant = new JLabel("Personne n'a gagn\u00E9, essayez encore !");} else{
			jLabelJoueurGagnant = new JLabel("Bravo a "+ joueurGagnant.getNom()+" d'avoir gagn\u00E9 !");
		}
		
		add(jLabelAffichage);
		add(jLabelEspace1);
		add(jLabelJoueurGagnant);
		add(jLabelEspace2);
		add(jLabelScore);
		add(jLabelScoreJ1);
		add(jLabelScoreJ2);
		add(jLabelEspace3);
		add(jbuttonRetry);
	}
}

/**
 * Classe qui etend JButton pour faire un button pour recommencer la partie
 * @author Warlot/Gasquez
 *
 */
class JButtonRetry extends JButton{
	private ControleurFenetre controleur;
	public JButtonRetry(ControleurFenetre controleur){
		super("Recommencer");
		this.controleur = controleur;
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getControleur().nouvellePartie();
			}
		});
	}	
	/****************** GETTERS ******************/
	public ControleurFenetre getControleur(){
		return this.controleur;
	}
}