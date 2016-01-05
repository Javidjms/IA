package GUI.Vue2;

import java.util.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Controleur.ControleurFenetre;
import Controleur.ControleurParamJoueur;
import Model.Personnage;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * 
 * Classe interface qui gere le parametrage des Personnage par les joueurs
 * @author Warlot/Gasquez
 */
public class VueParamJoueur extends JPanel {
	private static final long serialVersionUID = 1L;
	private ControleurParamJoueur controleur;
	
	private JLabel labelNomJoueur = new JLabel();
	private JLabel panelEquipeSelectionnerLabelPersonnagesRestants = new JLabel("X places");
	private JLabel panelEquipeSelectionnableLabelPersonnageTotal = new JLabel("X disponibles");
	
	private JPanel panelTousPersonnages = new JPanel();
	private JPanel panelPersonnagesChoisis = new JPanel();
	private JButton boutonSuivant;
	
	/**
	 * Constructeur
	 * @param controleurParent controleur parent
	 * @param nomJ nom du joueur
	 */
	public VueParamJoueur(ControleurFenetre controleurParent, final String nomJ){
		this.controleur = new ControleurParamJoueur(this, controleurParent);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		this.labelNomJoueur.setText("Joueur : " + nomJ);
		labelNomJoueur.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		Component horizontalStrut = Box.createHorizontalStrut(40);
		Component verticalStrut = Box.createVerticalStrut(40);	
		
		JPanel panelCorps = new JPanel();
		panelCorps.setLayout(new BoxLayout(panelCorps, BoxLayout.Y_AXIS));
		
		JPanel panelGeneral = new JPanel();
		panelGeneral.setAlignmentX(Component.LEFT_ALIGNMENT);
		panelGeneral.setLayout(new BoxLayout(panelGeneral, BoxLayout.Y_AXIS));
		
		JPanel panelEquipeSelectionner = new JPanel();
		panelEquipeSelectionner.setLayout(new BoxLayout(panelEquipeSelectionner, BoxLayout.Y_AXIS));
		
		JPanel panelEquipeSelectionnerLabelHautDeCadre = new JPanel();
		panelEquipeSelectionnerLabelHautDeCadre.setLayout(new BoxLayout(panelEquipeSelectionnerLabelHautDeCadre, BoxLayout.X_AXIS));
		
		JLabel panelEquipeSelectionnerLabelTitre = new JLabel("Composer votre \u00E9quipe : ");
		panelEquipeSelectionnerLabelTitre.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelEquipeSelectionnerLabelTitre.setHorizontalAlignment(SwingConstants.CENTER);
		
		panelPersonnagesChoisis.setBorder(new LineBorder(new Color(0, 0, 0)));
		JScrollPane panelScrollPersonnagesChoisis = new JScrollPane(panelPersonnagesChoisis);
		
		JPanel panelEquipeSelectionnable = new JPanel();
		panelEquipeSelectionnable.setLayout(new BoxLayout(panelEquipeSelectionnable, BoxLayout.Y_AXIS));
		
		JPanel panelEquipeSelectionnableHautDeCadre = new JPanel();
		panelEquipeSelectionnableHautDeCadre.setLayout(new BoxLayout(panelEquipeSelectionnableHautDeCadre, BoxLayout.X_AXIS));
		
		JLabel panelEquipeSelectionnableLabelTitre = new JLabel("Tous les personnages : ");
		panelEquipeSelectionnableLabelTitre.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panelTousPersonnages.setBorder(new LineBorder(new Color(0, 0, 0)));
		JScrollPane panelScrollTousPersonnages = new JScrollPane(panelTousPersonnages);
		
		//Lister les types de Personnage dispo, un clique dessus ouvre une fenetre donnat ses carac
		JPanel panelPersonnagesDispos = new JPanel();
		panelPersonnagesDispos.setLayout(new BoxLayout(panelPersonnagesDispos, BoxLayout.X_AXIS));
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		
		boutonSuivant = new JButton("Suivant");
		boutonSuivant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getControleur().suivant();
			}
		});
		boutonSuivant.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Component verticalStrut_1 = Box.createVerticalStrut(40);
		Component horizontalStrut_1 = Box.createHorizontalStrut(40);
		
		add(horizontalStrut);
		add(panelCorps);
		panelCorps.add(verticalStrut);
		panelCorps.add(labelNomJoueur);
		panelCorps.add(panelGeneral);
		panelGeneral.add(panelEquipeSelectionner);
		panelEquipeSelectionner.add(panelEquipeSelectionnerLabelHautDeCadre);
		panelEquipeSelectionnerLabelHautDeCadre.add(panelEquipeSelectionnerLabelTitre);
		panelEquipeSelectionnerLabelHautDeCadre.add(panelEquipeSelectionnerLabelPersonnagesRestants);
		panelEquipeSelectionner.add(panelScrollPersonnagesChoisis);
		panelGeneral.add(panelEquipeSelectionnable);
		panelEquipeSelectionnable.add(panelEquipeSelectionnableHautDeCadre);
		panelEquipeSelectionnableHautDeCadre.add(panelEquipeSelectionnableLabelTitre);
		panelEquipeSelectionnableHautDeCadre.add(panelEquipeSelectionnableLabelPersonnageTotal);
		panelEquipeSelectionnable.add(panelScrollTousPersonnages);
		listerPersonnagesDisponibles();
		for(final Personnage o : getControleur().getMaPartie().getPersonnagesDisponibles()){
			JLabel labelPersonnage = new JLabel();
			labelPersonnage.setText("Info : " + o.getClasse());
			
			JLabel labelEspace = new JLabel("       ");
			
			labelPersonnage.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			
			labelPersonnage.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {}
				
				@Override
				public void mousePressed(MouseEvent e) {}
				
				@Override
				public void mouseExited(MouseEvent e) {}
				
				@Override
				public void mouseEntered(MouseEvent e) {}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					new FenetreInfoPersonnage(o);
				}
			});
			
			panelPersonnagesDispos.add(labelPersonnage);
			panelPersonnagesDispos.add(labelEspace);
		}
		panelEquipeSelectionnable.add(panelPersonnagesDispos);
		panelEquipeSelectionnable.add(rigidArea);
		panelEquipeSelectionnable.add(boutonSuivant);
		panelCorps.add(verticalStrut_1);
		add(horizontalStrut_1);
		
	}

	/**
	 * Raffrechie l'element graphique JPanel
	 */
	public void updateVue(){
		repaint();
	}
	
	/**
	 * Affiche tous les Personnage disponible
	 */
	private void listerPersonnagesDisponibles(){
		List<Personnage> collectionPersonnages = getControleur().getCollectionPersonnages();
		
		panelEquipeSelectionnableLabelPersonnageTotal.setText(Integer.toString(collectionPersonnages.size()) + " disponibles");
		
		for(Personnage o : collectionPersonnages){
			LayeredPanePersonnage monLayeredPanePersonnage = new LayeredPanePersonnage(controleur, o);
			monLayeredPanePersonnage.ajouterBoutonAjout();
			
			panelTousPersonnages.add(monLayeredPanePersonnage);
		}
	}
	
	/**
	 * Ajoute le panel du Personnage selectionner dans la selection du joueur
	 * @param o panel du Personnage selectionner
	 */
	public void ajouterPanelPersonnageChoisi(LayeredPanePersonnage o){
		panelPersonnagesChoisis.add(o);	
	}
	
	/**
	 * Supprime le panel du Personnage selectionner dans la selection du joueur
	 * @param o panel du Personnage selectionner
	 */
	public void suprimerPanelPersonnageChoisi(LayeredPanePersonnage o){
		(o.getParent()).remove(o);
	}
	
	/**
	 * Mise a jour du JLabel de la place restante dans l'equipe
	 * @param tailleEquipeTotal integer indiquant taille total de l'equipe 
	 * @param tailleEquipe integer indiquant la taille courante de l'equipe
	 */
	public void majLabelPlaceRestante(int tailleEquipeTotal, int tailleEquipe){
		panelEquipeSelectionnerLabelPersonnagesRestants.setText(Integer.toString(tailleEquipeTotal - tailleEquipe) + " places");
	}
	
	/****************** GETTERS ******************/
	private ControleurParamJoueur getControleur(){
		return controleur;
	}

	public JPanel getPanelTousPersonnages() {
		return panelTousPersonnages;
	}

	public JPanel getPanelPersonnagesChoisis() {
		return panelPersonnagesChoisis;
	}
}

/**
 * classe abstraite qui etend un JLabel pour configuerer des comportements
 * @author Warlot/Gasquez
 */
abstract class LabelBouton extends JLabel{

	protected ControleurParamJoueur controleur = null;
	
	protected int boutonImageWidth = 30;
	protected int boutonImageHeight = 30;
	
	public LabelBouton(ControleurParamJoueur controleur){
		super();
		this.controleur = controleur;
		
		setVisible(false);
	}
	
	public void mouseExited(MouseEvent e){
		setVisible(false);
	}
	
	public  void mouseEntered(MouseEvent e){
		setVisible(true);
	}
	
	public abstract void mouseClicked(MouseEvent e);
}

/**
 * 
 * Classe qui etend LabelBouton pour ajouter un bouton ajouter.
 * @author Warlot/Gasquez
 */
class LabelBoutonAjouter extends LabelBouton{
	public LabelBoutonAjouter(ControleurParamJoueur controleur){
		super(controleur);

		setBounds(0, 20, boutonImageWidth, boutonImageHeight);
		setIcon(new ImageIcon(this.getClass().getResource("/images/Add.png")));
	}
	
	/**
	 * Lors du click sur ce bouton, on ajoute le Personnage
	 */
	public void mouseClicked(MouseEvent e){
		LayeredPanePersonnage parent = (LayeredPanePersonnage) e.getComponent();

		if (parent instanceof LayeredPanePersonnage){
			controleur.ajouterPersonnage(
					parent.getPersonnage()
					);
		}
	}
}

/**
 * 
 * Classe qui etend LabelBouton pour ajouter un bouton supprimer.
 * @author Warlot/Gasquez
 */
class LabelBoutonSupprimer extends LabelBouton{
	public LabelBoutonSupprimer(ControleurParamJoueur controleur){
		super(controleur);

		setBounds(0, 20, boutonImageWidth, boutonImageHeight);
		setIcon(new ImageIcon(this.getClass().getResource("/images/Sup.png")));
	}
	
	/**
	 * Lors du click sur ce bouton, on supprime le Personnage
	 */
	public void mouseClicked(MouseEvent e){
		Component parent = e.getComponent();

		if (parent instanceof LayeredPanePersonnage){
			controleur.supprimerPersonnage(
					(LayeredPanePersonnage) parent
					);
		}
	}
}



