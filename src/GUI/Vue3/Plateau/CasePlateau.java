package GUI.Vue3.Plateau;

import java.awt.Color;

import Model.Personnage;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Etend Jbutton pour instancier les cases du plateau
 * @author Warlot/Gasquez
 *
 */
public class CasePlateau extends JPanel {
	private int xPlateau = 0;
	private int yPlateau = 0;
	private Personnage monPersonnage = null;
	private Color maCouleur = null;
        JLabel typeLabel;
        JLabel vieLabel;
        JLabel nomLabel;
        JLabel imageLabel;
	
	/**
	 * Constructeur
	 * @param x position sur la largeur du plateau
	 * @param y position sur hauteur du plateau
	 */
	public CasePlateau(final int x, final int y){
		super();
		this.xPlateau = x;
		this.yPlateau = y;
                
                setLayout(new BorderLayout());
                setBorder(BorderFactory.createLineBorder(null, 1));
                
                JPanel imagePanel = new JPanel(new GridBagLayout());
                imagePanel.setOpaque(false);
                add(imagePanel, BorderLayout.CENTER);
                imageLabel = new JLabel();
                imagePanel.add(imageLabel);
                
                JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
                panel.setOpaque(false);
                add(panel, BorderLayout.NORTH);
                typeLabel = new JLabel();
                panel.add(typeLabel);
                nomLabel = new JLabel();
                panel.add(nomLabel);
                
                JPanel viePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
                viePanel.setOpaque(false);
                vieLabel = new JLabel();
                add(viePanel, BorderLayout.SOUTH);
                viePanel.add(vieLabel);
                
                setOpaque(false);

                setVisible(true);
	}
	
	
	/**
	 * Retourne la position sur l'axe x de la case sur le plateau
	 * @return int position x
	 */
	public int getXPlateau() {
		return xPlateau;
	}

	/**
	 * Retourne la position sur l'axe y de la case sur le plateau
	 * @return int position y
	 */
	public int getYPlateau() {
		return yPlateau;
	}
	
	/**
	 * Retourne le Personnage situe sur la case
	 * @return Personnage
	 */
	public Personnage getMonPersonnage() {
		return monPersonnage;
	}
	
	/**
	 * Retourne la couleur de la case
	 * @return Color
	 */
	public Color getMaCouleur() {
		return maCouleur;
	}
	
	/**
	 * Fixe la position y de la case sur le plateau
	 * @param y nouvelle position de la case sur l'axe y du plateau
	 */
	public void setYPlateau(int y) {
		this.yPlateau = y;
	}

	/**
	 * Fixe la position x de la case sur le plateau
	 * @param x nouvelle position de la case sur l'axe x du plateau
	 */
	public void setXPlateau(int x) {
		this.xPlateau = x;
	}
	
	/**
	 * Fixe le Personnage occupant la case
	 * @param monPersonnage nouveau Personnage de la case
	 */
	public void setMonPersonnage(Personnage monPersonnage) {
		this.monPersonnage = monPersonnage;
	}
	
	/**
	 * Fixe la nouvelle couleur de la case
	 * @param maCouleur nouvelle Color
	 */
	public void setMaCouleur(Color maCouleur) {
		this.maCouleur = maCouleur;
	}
        
        @Override
        public void setBackground(Color color) {
            if (color != null) {
                repaint();
                setOpaque(true);
//                super.setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 100));
                super.setBackground(color);
            }
            else {
                setOpaque(false);
                super.setBackground(color);
            }
        }
        
        /**
         * Fixe le contenu de la case en fonction d'un personnage
         * @param personnage Personnage à afficher
         */
        public void afficherPersonnage(Personnage personnage) {
                this.typeLabel.setText(personnage.getClasse());
                this.nomLabel.setText(personnage.getNom());
                this.imageLabel.setIcon(personnage.getVignette());
                String vieStr = personnage.getVie() + "/" + personnage.getMaxVie();
                if (personnage.isRalenti() || personnage.getBouclier() > 0) {
                    if (personnage.isRalenti() && personnage.getBouclier() > 0) {
                        vieStr += " [RB" + personnage.getBouclier() + "]";
                    }
                    else if (personnage.isRalenti()) {
                        vieStr += " [R]";
                    }
                    else {
                        vieStr += " [B" + personnage.getBouclier() + "]";
                    }
                }
                this.vieLabel.setText(vieStr);
        }

    void nettoyerCase() {
                this.typeLabel.setText("");
                this.nomLabel.setText("");
                this.imageLabel.setIcon(null);
                this.vieLabel.setText("");
    }
}
