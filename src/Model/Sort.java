package Model;

import Model.Personnage.creatureType;

/**
 * Modele pour une attaque
 * @author Warlot/Gasquez
 *
 */
public class Sort {
	private String nom = null;
	private int degat = 0;
	private int porteeMin = 0;
	private int porteeMax = 0;
	private Matrice zone = null;
	private Effet effetProduit = null;
	private creatureType typeCible = creatureType.TOUT;
	private boolean attaqueMultiple = false;
	
	/**
	 * Constructeur
	 * @param nom nom de l'attaque
	 * @param degat degat de l'attaque
	 * @param porteeMin portee minimum de l'attaque
	 * @param porteeMax portee maximum de l'attaque
	 * @param zone zone de l'attaque
	 * @param effetProduit effet produit par l'attaque
	 * @param typeCible type de creature que peut attendre l'attaque
	 * @param attaqueMultiple boolean pour savoir si c'est une attaque multiple ou pas
	 */
	public Sort(String nom, int degat, int porteeMin, int porteeMax, Matrice zone, Effet effetProduit, creatureType typeCible, boolean attaqueMultiple){
		this.nom = nom;
		this.degat = degat;
		this.porteeMin = porteeMin;
		this.porteeMax = porteeMax;
		this.zone = zone;
		this.effetProduit = effetProduit;
		this.attaqueMultiple= attaqueMultiple;
		this.typeCible = typeCible;
	}
	
	/**
	 * Constructeur sans effet
	 * @param nom nom de l'attaque
	 * @param degat degat de l'attaque
	 * @param porteeMin portee minimum de l'attaque
	 * @param porteeMax portee maximum de l'attaque
	 * @param zone zone de l'attaque
	 * @param typeCible type de creature que peut attendre l'attaque
	 * @param attaqueMultiple boolean pour savoir si c'est une attaque multiple ou pas
	 */
	public Sort(String nom, int degat, int porteeMin, int porteeMax, Matrice zone, creatureType typeCible, boolean attaqueMultiple){
		this(nom, degat, porteeMin, porteeMax, zone,  null, typeCible, attaqueMultiple);
	}

	/**
	 * test si l'attaque a un effet
	 * @return vrai si elle a un effet, faux sinon
	 */
	public boolean hasEffet(){
		if (getEffetProduit() != null){
			return true;
		}
		return false;
	}
	
	/**
	 * Renvoie vrai si c'est une attaque multiple et faux sinon
	 * @return boolean vrai si c'est une attaque multiple
	 */
	public boolean isAttaqueMultiple() {
		return attaqueMultiple;
	}
	
	/**
	 * Recupere l'effet produit par l'attaque
	 * @return Effet
	 */
	public Effet getEffetProduit() {
		return effetProduit;
	}
	
	/**
	 * Recupere le nom de l'attaque
	 * @return String
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * Recupere les degats de l'attaque
	 * @return int
	 */
	public int getDegat() {
		return degat;
	}
	
	/**
	 * Recupere la matrice d'effet de l'attaque
	 * @return Matrice
	 */
	public Matrice getZone() {
		return zone;
	}
        
        /**
         * Teste si le sort courant a une portee limitee
         * @return vrai si le sort a une portee limitee, faux sinon
         */
        public boolean hasPorteeLimitee() {
                return (porteeMax != -1);
        }
	
	/**
	 * Recupere la portee minimal de l'attaque
	 * @return int
	 */
	public int getPorteeMin() {
		return porteeMin;
	}
	
	/**
	 * Recupere la portee maximal de l'attaque
	 * @return int
	 */
	public int getPorteeMax() {
		return porteeMax;
	}
	
	/**
	 * retourne la portee min
	 * @return integer valeur de portee, -1 si infinie
	 */
	public String getPorteeMinString() {
		if (getPorteeMin() == -1){
			return "infini";
		}
		return Integer.toString(getPorteeMin());
	}

	/**
	 * retourne la portee max
	 * @return integer valeur de portee, -1 si infinie
	 */
	public String getPorteeMaxString() {
		if (getPorteeMax() == -1){
			return "infini";
		}
		return Integer.toString(getPorteeMax());
	}
	
	/**
	 * Retourne le type de creature attaquable
	 * @return String
	 */
	public String getTypeCibleToString() {
		if (getTypeCible() == creatureType.VOLANTE){
			return "volant";
		} else 	if (getTypeCible() == creatureType.TERRESTRE){
			return "terrestre";
		} else {return "volant et terrestre";}
		
	}
	
	/**
	 * Retourne le type de creature attaquable
	 * @return creatureType
	 */
	public creatureType getTypeCible() {
		return typeCible;
	}
        
        /**
         * Teste si le sort courant peut etre lance depuis une certaine position sur une autre position
         * @param origine Position d'origine ou serait lance le sort
         * @param cible Position cible sur laquelle lancer le sort
         * @return vrai si le sort pourrait etre lance
         */
        public boolean peutAtteindre(Position origine, Position cible) {
            return (!hasPorteeLimitee()
                    || new Matrice(porteeMin, porteeMax, false).getCasesAccessibles(origine).contains(cible));
        }
        
        /**
         * Renvoie une représentation textuelle du sort courant
         * @return une chaine de carateres
         */
        @Override
        public String toString() {
            return getNom();
        }

	/**
	 * Fixe l'effet produit par l'attaque
	 * @param effetProduit Effet
	 */
	public void setEffetProduit(Effet effetProduit) {
		this.effetProduit = effetProduit;
	}

	/**
	 * Fixe le nom de l'attaque
	 * @param nom String
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/**
	 * Fixe les degats de l'attaque
	 * @param degat int
	 */
	public void setDegat(int degat) {
		this.degat = degat;
	}

	/**
	 * Fixe la zone d'effet de l'attaque
	 * @param zone Matrice
	 */
	public void setZone(Matrice zone) {
		this.zone = zone;
	}

	/**
	 * Fixe la portee minimal de l'attaque
	 * @param porteeMin int
	 */
	public void setPorteeMin(int porteeMin) {
		this.porteeMin = porteeMin;
	}
	
	/**
	 * Fixe la portee maximal de l'attaque
	 * @param porteeMax int
	 */
	public void setPorteeMax(int porteeMax) {
		this.porteeMax = porteeMax;
	}
	
	/**
	 * Fixe si l'attaque est multiple ou non, true si oui
	 * @param attaqueMultiple boolean
	 */
	public void setAttaqueMultiple(boolean attaqueMultiple) {
		this.attaqueMultiple = attaqueMultiple;
	}

	/**
	 * Fixe le type de creature attaquable
	 * @param typeCible creatureType
	 */
	public void setTypeCible(creatureType typeCible) {
		this.typeCible = typeCible;
	}
}
