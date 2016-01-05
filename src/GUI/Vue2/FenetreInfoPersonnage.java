package GUI.Vue2;

import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import GUI.Vue3.Joueur.RadioButton;
import Model.Sort;
import Model.Personnage;

/**
 * Classe interface qui gere l'affichage de la fenetre d'informations lors du choix des Personnage par les joueurs
 * @author Warlot/Gasquez
 */
public class FenetreInfoPersonnage extends JFrame {
	private JLabel labelClasse = new JLabel();
	private JLabel labelImage = new JLabel();
	private JLabel labelVie = new JLabel();
	private JLabel labelPm = new JLabel();
	private JLabel labelType = new JLabel();
	private JPanel panelAttaque = new JPanel();
	private JPanel panelAttaqueWrapped = new JPanel();
	private JPanel panelDetail = new JPanel();
	private JPanel panelDetailWrapped = new JPanel();
	
	/**
	 * Constructeur
	 * @param monPersonnage le Personnage dont on veut les informations
	 */
	public FenetreInfoPersonnage(Personnage monPersonnage){
	getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		panelAttaqueWrapped.setLayout(new BoxLayout(panelAttaqueWrapped, BoxLayout.X_AXIS));
		panelDetailWrapped.setLayout(new BoxLayout(panelDetailWrapped, BoxLayout.X_AXIS));
		
		labelClasse.setText(monPersonnage.getClasse());
		labelClasse.setHorizontalAlignment(JLabel.CENTER);

		labelImage.setIcon(monPersonnage.getImage());
		labelImage.setBounds(0, 0, 100, 100);
		labelImage.setHorizontalAlignment(JLabel.CENTER);
		
		labelVie.setText(Integer.toString((monPersonnage.getVie())) + " points de vie");
		labelVie.setHorizontalAlignment(JLabel.CENTER);
		
		labelPm.setText(Integer.toString((monPersonnage.getPm())) + " points de mouvement");
		labelPm.setHorizontalAlignment(JLabel.CENTER);
		
		labelType.setText("Type  " + monPersonnage.getType().toString());
		labelType.setHorizontalAlignment(JLabel.CENTER);
		
		panelAttaque.setLayout(new BoxLayout(panelAttaque, BoxLayout.Y_AXIS));
		panelAttaque.add(new JLabel("Liste des attaques :"));
		ButtonGroup monGroup = new ButtonGroup();
		for (Sort o : monPersonnage.getAttaques()){
			RadioButton radioAttaque = new RadioButton(o.getNom());
			radioAttaque.setMonAttaque(o);
			radioAttaque.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					RadioButton radioSelected = (RadioButton) e.getSource();
					
					if(radioSelected.isSelected()){
						chargerDetailAttaque(radioSelected.getMonAttaque());
					}
				}
			});
			
			monGroup.add(radioAttaque);
			panelAttaque.add(radioAttaque);
			(monGroup.getElements()).nextElement().setSelected(true);
		}
		panelAttaqueWrapped.add(Box.createHorizontalGlue());
		panelAttaqueWrapped.add(panelAttaque);
		panelAttaqueWrapped.add(Box.createHorizontalGlue());
		
		panelDetail.setLayout(new BoxLayout(panelDetail, BoxLayout.Y_AXIS));
		panelDetail.add(new JLabel("coucou, je suis une attaque"));
		panelDetailWrapped.add(Box.createHorizontalGlue());
		panelDetailWrapped.add(panelDetail);
		panelDetailWrapped.add(Box.createHorizontalGlue());
		
		//Premiere ligne
		JPanel panelLigne1 = new JPanel();
		panelLigne1.setLayout(new GridLayout(0, 2));
		
		panelLigne1.add(labelClasse);
		panelLigne1.add(labelImage);
		
		//Deuxieme ligne
		JPanel panelLigne2 = new JPanel();
		panelLigne2.setLayout(new GridLayout(0, 3));
		panelLigne2.add(labelVie);
		panelLigne2.add(labelPm);
		panelLigne2.add(labelType);
		
		//Troisieme ligne
		JPanel panelLigne3 = new JPanel();
		panelLigne3.setLayout(new GridLayout(0, 2));
		panelLigne3.add(panelAttaqueWrapped);
		panelLigne3.add(panelDetailWrapped);
	
		getContentPane().add(panelLigne1);
		getContentPane().add(panelLigne2);
		getContentPane().add(panelLigne3);
		
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
	}
	
	
	/**
	 * Affiche les informations sur l'attaque selectionner
	 * @param monAttaque attaque selectionne
	 */
	public void chargerDetailAttaque(Sort monAttaque){
		panelDetail.removeAll();
		panelDetail.revalidate();
		
		panelDetail.add(new JLabel("Degat : " + Integer.toString(monAttaque.getDegat())));
		panelDetail.add(new JLabel("Portee maximum : " + monAttaque.getPorteeMaxString()));
		panelDetail.add(new JLabel("Portee minimum : " + monAttaque.getPorteeMinString()));
		if(monAttaque.isAttaqueMultiple()){
			panelDetail.add(new JLabel("Cette attaque est multiple"));
		} else {
			panelDetail.add(new JLabel("Cette attaque n'est pas multiple"));
		}
		panelDetail.add(new JLabel("Creature cible : " + monAttaque.getTypeCibleToString()));
	}
}
