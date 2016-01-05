package Model;

/**
 * Modele pour un effet qui est clonable
 * @author Warlot/Gasquez
 *
 */
public class Effet implements Cloneable{
	private int nbTourRestant = 0;
	private int pmRetirer = 0;
	private int bouclier = 0;
	
	/**
	 * Constructeur
	 * @param nbTourRestant tour restant a l'effet
	 * @param pmRetirer point de deplacement retirer par l'effet
	 * @param bouclier point de vie que va proteger l'effet
	 */
	public Effet(int nbTourRestant, int pmRetirer, int bouclier){
		this.nbTourRestant = nbTourRestant;
		this.pmRetirer = pmRetirer;
		this.bouclier = bouclier;
	}
	/**
	 * Cloner l'effet
	 */
	public Object clone(){
		Effet effet = null;
		try{
			effet = (Effet) super.clone();
		} catch (CloneNotSupportedException cnse){
			cnse.printStackTrace(System.err);
		}
		return effet;
	}
	
	/**
	 * Donne l'effet sous forme de String
	 */
	public String toString(){
		if(pmRetirer != 0){return " ralentissement";}
		if(bouclier != 0){return " bouclier";}
		return " pas d'effet";
	}
	
	/**
	 * Decremente le nombre de tour restant de l'effet
	 */
	public void effetTourSuivant(){
		setNbTourRestant(getNbTourRestant() - 1);
	}
	
	/**
	 * Retourne le nombre de tour restant
	 * @return int
	 */
	public int getNbTourRestant() {
		return nbTourRestant;
	}

	/**
	 * Retourne le nombre de pm retire par l'effet
	 * @return int
	 */
	public int getPmRetirer() {
		return pmRetirer;
	}
	
	/**
	 * Retourne le nombre de point de vie protege par cet effet
	 * @return int
	 */
	public int getBouclier() {
		return bouclier;
	}

	/**
	 * Fixe le nombre de pm retirer par l'effet
	 * @param pmRetirer int
	 */
	public void setPmRetirer(int pmRetirer) {
		this.pmRetirer = pmRetirer;
	}

	/**
	 * Fixe le nombre de tour restant de l'effet
	 * @param nbTourRestant int
	 */
	public void setNbTourRestant(int nbTourRestant) {
		this.nbTourRestant = nbTourRestant;
	}

	/**
	 * Fixe le nombre de vie de point protege par l'effet
	 * @param bouclier int
	 */
	public void setBouclier(int bouclier) {
		this.bouclier = bouclier;
	}
}
