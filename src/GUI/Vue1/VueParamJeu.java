package GUI.Vue1;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.*;

import Controleur.*;

/**
 * Classe interface qui gere le panel de parametrage du jeu
 * @author Warlot/Gasquez
 */

public class VueParamJeu extends JPanel {
	private ControleurFenetre controleur;
	private JSliderEquipeTaille sliderEquipeTaille;
	private JSliderPlateauLongueur sliderPlateauLongueur;
	private JSliderPlateauLargeur sliderPlateauLargeur;
	private JLabel labelEquipeTaille = new JLabel();
	private JLabel labelPlateauLongueur = new JLabel();
	private JLabel labelPlateauLargeur = new JLabel();
	private JSaisitJoueurGeneral saisitJoueurGerenal = new JSaisitJoueurGeneral();
	
	/**
	 * Constructeur
	 * @param controleur controleur de cette vue
	 */
	public VueParamJeu(ControleurFenetre controleur){
		this.controleur = controleur;
		setLayout(new GridLayout(0, 1, 0, 0));
				
		JPanel boxWrapper = new JPanel();
		add(boxWrapper);
		boxWrapper.setLayout(new BoxLayout(boxWrapper, BoxLayout.Y_AXIS));
		
		Component verticalGlue = Box.createVerticalGlue();
		boxWrapper.add(verticalGlue);
		JPanel flow = new JPanel();
		boxWrapper.add(flow);
		
		JPanel boxWrapped = new JPanel();
		boxWrapped.setLayout(new BoxLayout(boxWrapped, BoxLayout.Y_AXIS));
		flow.add(boxWrapped);
				
		JPanel panelEquipe = new JPanel();
		panelEquipe.setLayout(new BoxLayout(panelEquipe, BoxLayout.Y_AXIS));
		
		JPanel panelLogo = new JPanel();
		panelLogo.setLayout(new FlowLayout());
		panelLogo.add(new JLabel(new ImageIcon(this.getClass().getResource("/images/logo.png"))));
		boxWrapped.add(panelLogo);
		boxWrapped.add(saisitJoueurGerenal);
		boxWrapped.add(panelEquipe);
		
		JPanel equipePanelText = new JPanel();
		equipePanelText.setLayout(new BoxLayout(equipePanelText, BoxLayout.X_AXIS));
		panelEquipe.add(equipePanelText);
		
		JLabel equipeText = new JLabel("Choisir taille equipe : ");
		equipePanelText.add(equipeText);
		
		labelEquipeTaille.setText("3");
		equipePanelText.add(labelEquipeTaille);
		
		JLabel equipeTextBis = new JLabel(" joueurs");
		equipePanelText.add(equipeTextBis);
		
		JPanel equipePanelSlider = new JPanel();
		equipePanelSlider.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelEquipe.add(equipePanelSlider);
		
		sliderEquipeTaille = new JSliderEquipeTaille(labelEquipeTaille);
		equipePanelSlider.add(sliderEquipeTaille);
		
		JPanel panelPlateau = new JPanel();
		boxWrapped.add(panelPlateau);
		panelPlateau.setLayout(new BoxLayout(panelPlateau, BoxLayout.Y_AXIS));
		
		JPanel plateauPanelText = new JPanel();
		panelPlateau.add(plateauPanelText);
		plateauPanelText.setLayout(new BoxLayout(plateauPanelText, BoxLayout.X_AXIS));
		
		JLabel plateauText = new JLabel("Choisir taille plateau : ");
		plateauPanelText.add(plateauText);
		
		labelPlateauLongueur.setText("longueur");
		plateauPanelText.add(labelPlateauLongueur);
		
		JLabel plateauTextBis = new JLabel(" x ");
		plateauPanelText.add(plateauTextBis);
		
		labelPlateauLargeur.setText("largeur");
		plateauPanelText.add(labelPlateauLargeur);
		
		JPanel PlateauSlider = new JPanel();
		panelPlateau.add(PlateauSlider);
		
		sliderPlateauLongueur = new JSliderPlateauLongueur(labelPlateauLongueur);
		PlateauSlider.add(sliderPlateauLongueur);

		JLabel x = new JLabel("x");
		PlateauSlider.add(x);
		sliderPlateauLargeur = new JSliderPlateauLargeur(labelPlateauLargeur);
		PlateauSlider.add(sliderPlateauLargeur);
		
		JPanel panelBouton = new JPanel();
		boxWrapped.add(panelBouton);
		
		JButtonContinue buttonContinue = new JButtonContinue(this);
		panelBouton.add(buttonContinue);
	}

	public ControleurFenetre getControleur(){
		return controleur;
	}
	
	public JSlider getSliderEquipeTaille(){
		return sliderEquipeTaille;
	}
	public JSlider getSliderPlateauLongueur(){
		return sliderPlateauLongueur;
	}
	public JSlider getSliderPlateauLargeur(){
		return sliderPlateauLargeur;
	}
	public JSaisitJoueurGeneral getSaisitJoueurGeneral() {
		return saisitJoueurGerenal;
	}
}

/**
 * JPanel qui va centrer un panel
 * @author Warlot/Gasquez
 *
 */
class JSaisitJoueurGeneral extends JPanel {
	private JSaisitJoueurContener contenant;
	public JSaisitJoueurGeneral(){
		super();
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		contenant = new JSaisitJoueurContener();
		add(contenant);
	}

	/****************** GETTERS ******************/
	public JSaisitJoueur getSaisitJoueurUn(){
		return contenant.getSaisitJoueurUn();
	}
	public JSaisitJoueur getSaisitJoueurDeux(){
		return contenant.getSaisitJoueurDeux();
	}

}

/**
 * JPanel qui va ajouter deux panels sur Y
 * @author Warlot/Gasquez
 *
 */
class JSaisitJoueurContener extends JPanel{
	private JSaisitJoueur saisitJoueurUn = new JSaisitJoueur(1);
	private JSaisitJoueur saisitJoueurDeux = new JSaisitJoueur(2);
	
	public JSaisitJoueurContener(){
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(saisitJoueurUn);
		add(saisitJoueurDeux);
		saisitJoueurUn.setPreferredSize( new Dimension( 200, 20 ) );
		saisitJoueurDeux.setPreferredSize( new Dimension( 200, 20 ) );
		
	}
	
	/****************** GETTERS ******************/
	public JSaisitJoueur getSaisitJoueurUn(){
		return saisitJoueurUn;
	}
	
	public JSaisitJoueur getSaisitJoueurDeux(){
		return saisitJoueurDeux;
	}
}

/**
 * JPanel qui va saisir le nom d'un joueur
 * @author Warlot/Gasquez
 *
 */
class JSaisitJoueur extends JPanel{
	private JTextField textFieldJoueur = new JTextField();
	private JLabel labelJoueur = new JLabel("Nom Joueur :");
	public JSaisitJoueur(int numeroJoueur){
		super();
		labelJoueur.setText("Nom joueur "+ numeroJoueur +" :");
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(labelJoueur);
		add(textFieldJoueur);
	}
	
	/****************** GETTERS ******************/
	public JTextField getTextFieldJoueur(){
		return textFieldJoueur;
	}
	public JLabel getLabelJoueur(){
		return labelJoueur;
	}
}

/**
 * JSlider qui va ajouter un comportement a un JSlider.
 * Ce comportement va etre la mise a jour du texte sur le slider de la taille de l'equipe
 * @author Warlot/Gasquez
 *
 */
class JSliderEquipeTaille extends JSlider{
	private JSliderEquipeTaille monObj = this;
	private String nouvelleTaille;
	public JSliderEquipeTaille(final JLabel labelEquipeTaille ){
		super();
		this.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				//Met a jour le text au dessus du slider
				nouvelleTaille = Integer.toString(monObj.getValue());
				labelEquipeTaille.setText(nouvelleTaille);
			}
		});
		this.setValue(1);
		this.setMinimum(1);
		this.setMaximum(5);
	}
}

/**
 * JSlider qui va ajouter un comportement a un JSlider.
 * Ce comportement va etre la mise a jour du texte sur le slider de la longueur du plateau
 * @author Warlot/Gasquez
 *
 */
class JSliderPlateauLongueur extends JSlider{
	private JSliderPlateauLongueur monObj;
	public JSliderPlateauLongueur(final JLabel labelPlateauLongueur){
		super();
		monObj= this;
		this.setMaximum(10);
		this.setValue(this.getMaximum());
		this.setMinorTickSpacing(20);
		this.setMinimum(1);
		this.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				//Met a jour le text au dessus du slider
				String nouvelleTaille = Integer.toString(monObj.getValue());
				labelPlateauLongueur.setText(nouvelleTaille);
			}
		});
	}
}

/**
 * JSlider qui va ajouter un comportement a un JSlider.
 * Ce comportement va etre la mise a jour du texte sur le slider de la largeur du plateau
 * @author Warlot/Gasquez
 *
 */
class JSliderPlateauLargeur extends JSlider{
	private JSliderPlateauLargeur monObj;
	public JSliderPlateauLargeur(final JLabel labelPlateauLargeur){
		this.monObj = this;
		this.setMaximum(10);
		this.setValue(this.getMaximum());
		this.setMinorTickSpacing(20);
		this.setMinimum(1);
		this.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				//Met a jour le text au dessus du slider
				String nouvelleTaille = Integer.toString(monObj.getValue());
				labelPlateauLargeur.setText(nouvelleTaille);
			}
		});
	}
}

/**
 * JButtonContinue qui va ajouter un comportement a un JButton.
 * Ce comportement va etre le demande au controleur de passage a la vue suivante.
 * @author Warlot/Gasquez
 *
 */
class JButtonContinue extends JButton{
	public JButtonContinue(final VueParamJeu parent){
		super("Suivant");
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(parent.getSaisitJoueurGeneral().getSaisitJoueurUn().getTextFieldJoueur().getText().length()<10){
					parent.getControleur().continueParamJeu(
							parent.getSliderEquipeTaille().getValue(), 
							parent.getSliderPlateauLongueur().getValue(),
							parent.getSliderPlateauLargeur().getValue(),
							parent.getSaisitJoueurGeneral().getSaisitJoueurUn().getTextFieldJoueur().getText(),
							parent.getSaisitJoueurGeneral().getSaisitJoueurDeux().getTextFieldJoueur().getText()
							);
				}
			}
		});
	}
}