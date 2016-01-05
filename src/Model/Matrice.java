package Model;

import java.util.ArrayList;
import java.util.List;


/**
 * Classe Matrice, represente les gabarits de deplacement et d'attaque
 * Le nombre de colonne et de ligne doit etre impaire
 * La case du centre represente la position du personnage
 * @author Warlot/Gasquez
 *
 */
public class Matrice {
	private int ligne = 0;
	private int colonne = 0;
	private boolean[][] maMatrice = null;
	/**
	 * Cree un gabarit (Matrice) et met tout a faux
	 * @param ligne nombre de ligne
	 * @param colonne nombre de colonne
	 */
	public Matrice(int ligne, int colonne){
		if (ligne < 1) ligne = 1;
		if (colonne < 1) colonne = 1;

		this.ligne = ligne;
		this.colonne = colonne;
		this.maMatrice = new boolean[ligne][colonne];
		
		for (int row = 0; row < ligne; row += 1){
			for (int col = 0; col < colonne; col += 1){
				this.maMatrice[row][col] = false;
			}
		}
	}
	
	/**
	 * Creer une matrice a partir du tableau passe en parametre
	 * @param maMatrice tableau de boolean, doit etre de taille impaire, de dimension deux avec longueur dim 1 = longueur dim 2
	 */
	public Matrice(boolean[][] maMatrice){
		this.ligne = maMatrice.length;
		this.colonne = maMatrice[0].length;
		this.maMatrice = maMatrice;
	}
	
	/**
	 * Cree un gabarit (Matrice) de porteeMin a porteeMax
	 * @param porteeMin portee minimum
	 * @param porteeMax portee maximum
	 * @param portee ce parametre sert a differentier ce constructeur avec Matrice(int, int);
	 */
	public Matrice(int porteeMin, int porteeMax, boolean portee){
		if (porteeMin < 0) porteeMin = 0;
		if (porteeMax < 0) porteeMax = 0;
		
		this.ligne = porteeMax * 2 + 1;
		this.colonne = porteeMax * 2 + 1;
		
		this.maMatrice = new boolean[this.ligne][this.colonne];
		
		int milieu = (this.ligne - 1) / 2;
		
		for (int i = 0; i < this.ligne; i += 1){
			for (int j = 0; j < this.colonne; j += 1){
				if (distanceEntreDeuxCases(i, j, milieu, milieu) <= porteeMax
						&& distanceEntreDeuxCases(i, j, milieu, milieu) >= porteeMin){
					setCase(i, j, true);
				} else {
					setCase(i, j, false);
				}
			}
		}
	}
	
	/**
	 * Reduit le gabarit de la matrice
	 * @param malusPortee nombre de reduction que recrevra le gabarit
	 * @return le nouveau gabarit
	 */
	public Matrice reduireZone(int malusPortee){
		Position milieu = new Position(
				getColonne() / 2,
				getLigne() / 2
				);
		Matrice nouvelleZone = new Matrice(getLigne(), getColonne());
		
		for (int y = 0; y < getLigne(); y += 1){
			for (int x = 0; x < getColonne(); x += 1){
				//si case centre ou si portee totalement reduite alors on ne fait rien
				if (milieu.equals(new Position(x, y)) || distanceEntreDeuxCases(x, y, milieu.getX(), milieu.getY()) <= malusPortee) continue;
				
				//si deplacement possible
				if (getCase(y, x)){
					if (x - milieu.getX() == 0){
						//case vertical
						if(y - milieu.getY() < 0){
							//vertical superieur
							nouvelleZone.setCase(y + malusPortee, x, true);
						} else {
							//vertival inferieur
							nouvelleZone.setCase(y - malusPortee, x, true);
						}
					} else if(y - milieu.getY() == 0){
						//case horizontal
						if(x - milieu.getX() < 0){
							//horizontal gauche
							nouvelleZone.setCase(y, x + malusPortee, true);
						} else {
							//horizontal droite
							nouvelleZone.setCase(y, x - malusPortee, true);
						}
					}  else if(Math.abs(y - milieu.getY()) == Math.abs(x - milieu.getX()) && Math.abs(y - milieu.getY()) > malusPortee){
						//case diagonale
						if (x - milieu.getX() > 0
								&& y - milieu.getY() < 0){
							//diagonale superieur droite
							nouvelleZone.setCase(y + malusPortee, x - malusPortee, true);
						} else if (x - milieu.getX() < 0
								&& y - milieu.getY() < 0){
							//diagonale superieur gauche
							nouvelleZone.setCase(y + malusPortee, x + malusPortee, true);
						} else if (x - milieu.getX() > 0
								&& y - milieu.getY() > 0){
							//diagonale inferieur droite
							nouvelleZone.setCase(y - malusPortee, x - malusPortee, true);
						} else {
							//diagonale inferieur gauche
							nouvelleZone.setCase(y - malusPortee, x + malusPortee, true);
						}
					} else {
						//autre
						//Inutile ici car le jeu ne traite pas ces cas
					}
					
				}
			}
		}
		
		return nouvelleZone;
	}
	
	/**
	 * Calcul la distance entre deux cases
	 * @param case1x position x de la premiere case
	 * @param case1y position y de la premiere case
	 * @param case2x position x de la deuxieme case
	 * @param case2y position y de la deuxieme case
	 * @return integer, la distance entre deux cases
	 */
	private int distanceEntreDeuxCases(int case1x, int case1y, int case2x, int case2y){
		return Math.abs(case1x - case2x) + Math.abs(case1y - case2y);
	}
        
        /**
	 * liste les cases accessibles
	 * @param maPosition position courante
	 * @return collection de position de case
	 */
	public List<Position> getCasesAccessibles(Position maPosition){
		return getCasesAccessibles(maPosition, new ArrayList());
	}
	
	/**
	 * liste les cases accessibles
	 * @param maPosition position courante
	 * @return collection de position de case
	 */
	public List<Position> getCasesAccessibles(Position maPosition, List<Effet> effets){
		List<Position> caseAccessible = new ArrayList<Position>();
		
                int pmMalusCumules = 0;
                for (Effet e : effets) {
                    pmMalusCumules += e.getPmRetirer();
                }
                
		int ligneOffset = ((getLigne() - 1) / 2 - pmMalusCumules);
		int colonneffset = ((getColonne() - 1) / 2 - pmMalusCumules);
		
		for (int i = -ligneOffset; i <= ligneOffset; i += 1){
			for (int j = -colonneffset; j <= colonneffset; j += 1){
				Position temp = new Position(
						maPosition.getX() + j,
						maPosition.getY() + i);
				if (getCase(i + ligneOffset, j + colonneffset)){
					caseAccessible.add(temp);
				}
			}
		}
		
		return caseAccessible;
	}
	
	/**
	 * Retourne le nombre de ligne de la matrice
	 * @return nombre de ligne
	 */
	public int getLigne() {
		return ligne;
	}

	/**
	 * Retourne le nombre de colonne de la matrice
	 * @return nombre de colonne
	 */
	public int getColonne() {
		return colonne;
	}
	
	/**
	 * Retourne la matrice, tableau de boolean a deux dimensions
	 * @return tableau de boolean a deux dimensions
	 */
	public boolean[][] getMaMatrice() {
		return maMatrice;
	}

	/**
	 * Retourne le boolean a la ligne y colonne x
	 * @param y ligne
	 * @param x colonne
	 * @return booleen
	 */
	public boolean getCase(int y, int x){
		return maMatrice[y][x];
	}
	
	/**
	 * Fixe la valeur du boolean a l'emplacement ligne y colonne x
	 * @param y colonne
	 * @param x ligne
	 * @param value valeur booleenne
	 */
	public void setCase(int y, int x, boolean value){
		maMatrice[y][x] = value;
	}

	/**
	 * Fixe le nombre de ligne de la matrice
	 * @param ligne nombre de ligne
	 */
	public void setLigne(int ligne) {
		this.ligne = ligne;
	}
	
	/**
	 * Fixe le nombre de colonne de la matrice
	 * @param colonne nombre de colonne
	 */
	public void setColonne(int colonne) {
		this.colonne = colonne;
	}

	/**
	 * Fixe la matrice avec celle passe en parametre
	 * @param maMatrice matrice de booleen
	 */
	public void setMaMatrice(boolean[][] maMatrice) {
		this.maMatrice = maMatrice;
		setLigne(maMatrice.length);
		if (maMatrice.length != 0) {
			setColonne(maMatrice[0].length);
		} else {
			setColonne(0);
		}
	}
}
