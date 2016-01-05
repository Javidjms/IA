package GUI.Vue3.Joueur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controleur.ControleurCombat;
import Model.Sort;
import Model.Joueur;
import Model.Personnage;
import Model.Personnage.creatureType;
import javax.swing.JSeparator;

/**
 * 
 * Classe interface qui etend VueJoueur qui va contenir les elements propre au panel des Joueurs (droite et gauche)
 * @author Warlot/Gasquez
 */

public class VueJoueurCombat extends VueJoueur {
	private boolean personnageLock = false;
	private PanelPersonnageNom panelPersonnageNom = null;
	private PanelPersonnageCaracteristique panelPersonnageCaracteristique = null;
	private PanelActionsNom panelActionsNom = null;
	private PanelActionsCaracteristique panelActionsCaracteristique = null;
	
	/**
	 * Constructeur
 Va instancier selon l'axe Y : la liste des Personnage, les caracteristiques des Personnage, la liste des actions puis les caracterisques de cette action
	 * @param controleur controleur de cette interface
	 * @param monJoueur joueur associe a ce panel
	 */
	public VueJoueurCombat(ControleurCombat controleur, Joueur monJoueur){
		super(controleur, monJoueur);
                
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		panelPersonnageNom = new PanelPersonnageNom(this);
		panelPersonnageCaracteristique = new PanelPersonnageCaracteristique(this);
		panelActionsNom = new PanelActionsNom(this);
		panelActionsCaracteristique = new PanelActionsCaracteristique(this);
		
		add(panelPersonnageNom);
                add(new JSeparator());
		add(panelPersonnageCaracteristique);
                add(new JSeparator());
		add(panelActionsNom);
                add(new JSeparator());
		add(panelActionsCaracteristique);

	}

	/**
	 * Verrouille un Personnage
	 */
	public void verouillerChoixPersonnage(){
		if (personnageLock) return;
		
		personnageLock = true;
		panelPersonnageNom.verouillerChoixPersonnage();
	}
	
	/**
	 * Verrouille le deplacement
	 */
	public void verouillerChoixDeplacement(){
		panelActionsNom.verouillerChoixDeplacement();
	}
	
	/**
	 * Verouille l'attaque
	 */
	public void verouillerChoixAttaque(){
		panelActionsNom.verouillerChoixAttaque();
	}
	
	/**
	 * test si un Personnage est verrouiller
	 * @return the personnageLock boolean vrai si il est verrouille, faux sinon
	 */
	public boolean isPersonnageLock() {
		return personnageLock;
	}

	/****************** GETTERS ******************/
	@Override
	public ControleurCombat getControleur() {
		return (ControleurCombat) controleur;
	}

	public PanelPersonnageNom getPanelPersonnageNom() {
		return panelPersonnageNom;
	}

	public PanelPersonnageCaracteristique getPanelPersonnageCaracteristique() {
		return panelPersonnageCaracteristique;
	}
	

	public PanelActionsNom getPanelActionsNom() {
		return panelActionsNom;
	}

	public PanelActionsCaracteristique getPanelActionsCaracteristique() {
		return panelActionsCaracteristique;
	}
	/****************** SETTERS ******************/
	/**
	 * met a jour l'attribut qui defini si un Personnage est verrouille
	 * @param personnageLock boolean qui va mettre l'etat a vrai ou faux
	 */
	public void setPersonnageLock(boolean personnageLock) {
		personnageLock = personnageLock;
	}
	
	public void setPanelPersonnageNom(PanelPersonnageNom panelPersonnageNom) {
		this.panelPersonnageNom = panelPersonnageNom;
	}

	public void setPanelPersonnageCaracteristique(PanelPersonnageCaracteristique panelPersonnageCaracteristique) {
		this.panelPersonnageCaracteristique = panelPersonnageCaracteristique;
	}

	public void setPanelActionsNom(PanelActionsNom panelActionsNom) {
		this.panelActionsNom = panelActionsNom;
	}

	public void setPanelActionsCaracteristique(PanelActionsCaracteristique panelActionsCaracteristique) {
		this.panelActionsCaracteristique = panelActionsCaracteristique;
	}
}

/**
 *
 * Panel qui affiche la liste des personnages disponibles
 * @author Warlot/Gasquez
 */
class PanelPersonnageNom extends JPanel{
	private static final long serialVersionUID = 1L;
	private VueJoueurCombat parent = null;
	private JLabel labelGeneral;
	ButtonGroup radioGroup = null;
	
	/**
	 * Constructeur
 Va instancier des radios buttons ainsi que des labels avec la classe du Personnage
	 * @param parent vue parente
	 */
	public PanelPersonnageNom(VueJoueurCombat parent){
		super();
		this.parent = parent;
		labelGeneral = new JLabel("Liste des personnages :");
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(labelGeneral);
		radioGroup = new ButtonGroup();
		//Liste les Personnage de l'equipe
		for (Personnage o : getParent().getControleur().getMonEquipe()){			
			RadioButton radio = new RadioButton(o.getClasse() + " " + o.getNom() + " (" + o.getVie() + "/" + o.getMaxVie() + " PV)");
			radio.setPersonnage(o);
			
			if (o.isDejaJoue()){
				radio.setEnabled(false);
			}
                        else if (parent.getControleur().getPersonnageActif() == o) {
                                radio.setSelected(true);
                        }
			
			radio.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					RadioButton monRadioButton = (RadioButton) e.getSource();
					
					getParent().getPanelPersonnageCaracteristique().afficher(monRadioButton.getPersonnage());
					getParent().getPanelActionsNom().afficher(monRadioButton.getPersonnage());
				}
			});

			radioGroup.add(radio);
			add(radio);
		}
	}

	/**
	 * Verouille le chois du Personnage
	 */
	public void verouillerChoixPersonnage(){
		for (Enumeration<AbstractButton> buttons = radioGroup.getElements(); buttons.hasMoreElements();) {
			 AbstractButton button = buttons.nextElement();
			 
			 button.setEnabled(false);
		 }
	}
	
	/****************** GETTERS ******************/
	public VueJoueurCombat getParent(){
		return parent;
	}
}

/**
 * Panel qui contient les caracteristiques propre au Personnage selectionner
 * @author Warlot/Gasquez
 *
 */
class PanelPersonnageCaracteristique extends JPanel{
	private static final long serialVersionUID = 1L;
	private VueJoueurCombat parent = null;
	
	private JLabel labelPv;
	private JLabel labelEffet;
	private JLabel labelPa;
	private JLabel labelPm;
	private JLabel labelGeneral;
	
	/**
	 * Constructeur
 Initialise tout les labels necessaire pour l'affichage des caracterisques d'un Personnage
	 * @param parent vue parente
	 */
	public PanelPersonnageCaracteristique(VueJoueurCombat parent){
		super();
		this.parent = parent;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		labelPv = new JLabel("PV : ");
		labelEffet = new JLabel("Effet courant : ");
		labelPm = new JLabel("PM : ");
		labelGeneral = new JLabel("Caracteristiques du personnage :");
		add(labelGeneral);
		add(labelPv);
		add(labelEffet);
		add(labelPm);
	}
	
	/**
	 * Affiche les informations du Personnage
	 * @param o le Personnage dont on veux afficher les informations
	 */
	public void afficher(Personnage o){
		getParent().getControleur().setPersonnageActif(o);
		this.getLabelPv().setText("PV : " + o.getVie());
		this.getLabelPm().setText("PM : " + o.getPm());
		if(o.getEffet().isEmpty()){ 
			this.getLabelEffet().setText("Effet courant : Aucun");
		} else {
			this.getLabelEffet().setText("Effet courant :" + (o.getEffet()).toString() + " " + (o.getEffet()).get(0).getNbTourRestant() + " tour restant(s)");
		}
	}

	/****************** GETTERS ******************/
	public VueJoueurCombat getParent(){
		return parent;
	}
	
	public JLabel getLabelPv() {
		return labelPv;
	}
	
	public JLabel getLabelEffet() {
		return labelEffet;
	}
	
	public JLabel getLabelPm(){
		return labelPm;
	}
	
	public JLabel getLabelPa() {
		return labelPa;
	}

	/****************** SETTERS ******************/
	public void setLabelPv(JLabel labelPv) {
		this.labelPv = labelPv;
	}

	public void setLabelEffet(JLabel labelEffet) {
		this.labelEffet = labelEffet;
	}

	public void setLabelPa(JLabel labelPa) {
		this.labelPa = labelPa;
	}
}

/**
 * Panel qui contient la listes des actions possibles du personnage
 * @author Warlot/Gasquez
 *
 */
class PanelActionsNom extends JPanel{
	private VueJoueurCombat parent = null;
	private ButtonGroup radioGroup = null;
	private JLabel labelGeneral;
	/**
	 * Constructeur
	 * Va initialiser l'affiche de la listes d'actions 
	 * @param parent vue parente
	 */
	public PanelActionsNom(VueJoueurCombat parent){
		super();
		this.parent= parent;
		labelGeneral = new JLabel("Listes des actions :");
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(labelGeneral);
	}
	
	/**
	 * Verrouille l'attaque selectionner
	 */
	public void verouillerChoixAttaque(){
		for (Enumeration<AbstractButton> buttons = radioGroup.getElements(); buttons.hasMoreElements();) {
			 AbstractButton button = buttons.nextElement();
			 
			 if (button.getName() == "attaque"){
				 button.setEnabled(false);
			 }
		 }
	}
	
	/**
	 * Verrouille le deplacement selectionner
	 */
	public void verouillerChoixDeplacement(){
		for (Enumeration<AbstractButton> buttons = radioGroup.getElements(); buttons.hasMoreElements();) {
			 AbstractButton button = buttons.nextElement();
			 
			 if (button.getName() == "deplacement"){
				 button.setEnabled(false);
				 break;
			 }
		 }
	}
	
	/**
	 * affiche la listes des actions possibles par un personnage 
	 * @param monPersonnage personnage dont on veux lister les actions possible
	 */
	public void afficher(Personnage monPersonnage){
		this.removeAll();
		this.revalidate();
		// Ajout du label "liste des actions :"
		add(labelGeneral);
		RadioButton radioPasser = new RadioButton("Terminer son tour");
		radioPasser.setName("passer");
		radioPasser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getParent().getControleur().passerSonTour();
			}
		});
		RadioButton radioDeplacement = new RadioButton("Faire un deplacement");
		radioDeplacement.setName("deplacement");
		
		if (monPersonnage.isDejaJoue() || !monPersonnage.estVivant()){
			radioDeplacement.setEnabled(false);
		}
		
		radioDeplacement.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getParent().getControleur().setDeplacer();
				getParent().getPanelActionsCaracteristique().afficheDeplacement();
			}
		});
		
		radioGroup = new ButtonGroup();
		radioGroup.add(radioPasser);
		radioGroup.add(radioDeplacement);
		
		add(radioPasser);
		add(radioDeplacement);
		
		//affiche toute les attaques disponibles
		for(Sort o : monPersonnage.getAttaques()){
			
			RadioButton radio = new RadioButton(o.getNom());
			radio.setMonAttaque(o);
			radio.setName("attaque");

			if (monPersonnage.isDejaJoue() || !monPersonnage.estVivant()){
				radio.setEnabled(false);
			}
			
			radio.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					RadioButton monRadioButton = (RadioButton) e.getSource();
					
					getParent().getControleur().setAttaqueActif(monRadioButton.getMonAttaque());
					getParent().getControleur().setAttaquer(monRadioButton.getMonAttaque());
					getParent().getPanelActionsCaracteristique().affiche(monRadioButton.getPersonnage());
				}
			});
			radioGroup.add(radio);
			add(radio);
		}
		
		
		
	}

	/****************** GETTERS ******************/
	public VueJoueurCombat getParent(){
		return parent;
	}
}

/**
 * Panel qui contient les details de l'action selectionner
 * @author Warlot/Gasquez
 *
 */
class PanelActionsCaracteristique extends JPanel{
	private VueJoueurCombat parent = null;
	public JLabel 	labelEffet;
	public JLabel 	labelGeneral;
	public JLabel 	labelDegat;
	public JLabel   labelAttaqueMultiple;
	public JLabel 	labelType;
	public JLabel 	labelTypeAttaquable;
	
	/**
	 * Constructeur
	 * Va initialiser l'affichage des caracteristiques de l'action selectionner
	 * @param parent vue parente
	 */
	public PanelActionsCaracteristique(VueJoueurCombat parent){
		super();
		this.parent = parent;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		labelGeneral = new JLabel("Details action : ");
		labelEffet = new JLabel("");
		labelDegat = new JLabel("");
		labelAttaqueMultiple = new JLabel("");
		labelType = new JLabel("");
		labelTypeAttaquable = new JLabel("");
		add(labelGeneral);
	}
	/**
	 * Affiche le deplacement
	 */
	public void afficheDeplacement(){
		this.removeAll();
		this.revalidate();
		add(labelGeneral);
		add(labelEffet);
		this.getLabelEffet().setText("Vous avez la posibilite de faire un deplacement.");
	}
	
	/**
	 * affiche l'ensemble des caracterisques selon le Personnage selectionne
	 * @param PersonnageSelectionne Personnage dont on a besoin de questionner le model
	 */
	public void affiche(Personnage PersonnageSelectionne){
		this.removeAll();
		this.revalidate();
		add(labelGeneral);
		add(labelEffet);
		add(labelDegat);
		add(labelAttaqueMultiple);
		add(labelType);
		add(labelTypeAttaquable);
		Sort attack = getParent().getControleur().getAttaqueActif();
		if(attack.getEffetProduit()==null){ 
			this.getLabelEffet().setText("Cette attaque n'a pas d'effet");
		} else {
			this.getLabelEffet().setText("Elle a un effet " + (attack.getEffetProduit()).toString());
		}
		this.getLabelDegat().setText("Elle fera " + attack.getDegat() + " degats.");
		this.getLabelType().setText("Elle ciblera les creatures de type : "+attack.getTypeCibleToString() + ".");
		
		String creatureAttaquable = "Exemple : ";
		for(Personnage o : parent.getControleur().getControleurParent().getPartie().getPersonnagesDisponibles()){
			if (attack.getTypeCible() ==  creatureType.TOUT || o.getType() == attack.getTypeCible()){
				creatureAttaquable += o.getClasse() + " ";
			}
		}
		getLabelTypeAttaquable().setText(creatureAttaquable);
		
		if(attack.isAttaqueMultiple()){this.getLabelAttaqueMultiple().setText("C'est une attaque multiple : elle est lancable plusieurs fois.");}
		else{this.getLabelAttaqueMultiple().setText("Ce n'est pas une attaque multiple");}		
	}
	
	/****************** GETTERS ******************/
	public JLabel getLabelEffet(){
		return labelEffet;
	}

	public JLabel getLabelGeneral(){
		return labelGeneral;
	}
	
	public JLabel getLabelDegat(){
		return labelDegat;
	}
	
	public JLabel getLabelAttaqueMultiple(){
		return labelAttaqueMultiple;
	}
	
	public JLabel getLabelType(){
		return labelType;
	}
	
	public JLabel getLabelTypeAttaquable(){
		return labelTypeAttaquable;
	}
	
	public VueJoueurCombat getParent(){
		return parent;
	}
}