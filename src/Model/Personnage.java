package Model;

import java.util.*;

import javax.swing.ImageIcon;

import Exception.ExceptionPersonnage;

/**
 * Classe Personnage, represente les personnages
 Elle est clonable et joue le role d'observable
 * @author Warlot/Gasquez
 *
 */
public abstract class Personnage extends Observable implements Cloneable{
	private int vie;
        private int maxVie;
	private int pm;
	private Matrice mouvement;
	private String classe;
        private String nom;
	private ImageIcon image;
        private ImageIcon vignette;
	private List<Sort> attaque;
	private List<Effet> effet;
	private boolean dejaJoue;
	private Position position;
	private Joueur proprio = null;
        
        private static char nouveauNom = 'A';
	
	public enum creatureType{
		TERRESTRE, VOLANTE, TOUT
	}
	
	private creatureType type = creatureType.TOUT;
	
	/**
	 * Constructeur de personnage avec image
	 * @param pm nombre de point de mouvement
	 * @param vie nombre de point de vie
	 * @param mouvement gabarit de mouvement
	 * @param classe classe du personnage
	 * @param type type du personnage
	 * @param imagePath lien de l'image
	 */
	public Personnage(int pm, int vie, Matrice mouvement, String classe, creatureType type, String imagePath, String vignettePath){
		this.pm = pm;
		this.vie = vie;
                this.maxVie = vie;
		this.mouvement = mouvement;
		this.classe = classe;
                this.nom = "";
		this.image = new ImageIcon(this.getClass().getResource(imagePath));
                this.vignette = new ImageIcon(this.getClass().getResource(vignettePath));
		this.attaque = new ArrayList<Sort>();
		this.effet = new ArrayList<Effet>();
		this.dejaJoue = false;
		this.position = new Position();
		this.type = type;
	}

	/**
	 * Constructeur de personnage sans image
	 * @param pm nombre de point de mouvement
	 * @param vie nombre de point de vie
	 * @param mouvement gabarit de mouvement
	 * @param classe classe du personnage
	 * @param type type du personnage
	 */
	public Personnage(int pm, int vie, Matrice mouvement, String classe, creatureType type){
		this(pm, vie, mouvement, classe, type, "/images/large_DefaultPersonnage.png", "/images/small_DefaultPersonnage.png");
	}
        
        @Override
        public abstract Object clone();
		
        /**
         * Copie un personnage
         * @param perso le personnage a copier
         */
	public void copier(Personnage perso){
		this.pm = perso.pm;
		this.vie = perso.vie;
                this.maxVie = perso.maxVie;
		this.mouvement = perso.mouvement;
		this.classe = perso.classe;
                this.nom = perso.nom;
		this.image = perso.image;
                this.vignette = perso.vignette;
		this.attaque = perso.attaque;
		this.dejaJoue = perso.dejaJoue;
                this.position = (Position) perso.position.clone();
		this.type = perso.type;
                this.proprio = perso.proprio;
                
		//Clone les dependances (objets) non immuables (types primitifs non inclus)
		//Les attaques sont identiques pour tous les Personnage, pas de clonnage
		//Clonnage des effets
		List<Effet> listeEffetClone = new ArrayList();
		for(Effet e : perso.getEffet()) listeEffetClone.add((Effet) e.clone());
		this.setEffet(listeEffetClone);
	}
        
        /**
	 * Teste l'égalité entre deux personnages
         * @return Vrai si les personnages sont équivalents
	 */
	public boolean equals(Object obj){
		if (obj == this){
			return true;
		}
		if (!(obj instanceof Personnage)){
			return false;
		}
		Personnage perso = (Personnage) obj;
		return (this.classe.equals(perso.classe)
                    &&  this.nom.equals(perso.nom)
//                    &&  this.pm == perso.pm
//                    &&  this.vie == perso.vie
//                    &&  this.maxVie == perso.maxVie
//                    &&  this.mouvement.equals(perso.mouvement)
//                    &&  this.position.equals(perso.position)
//                    &&  this.type  == perso.type
                        );
	}
	
	/**
	 * Lance le sort et applique les effets
	 * @param sort attaque appliquee
	 * @return toString des effets de l'attaque
	 */
	public String appliquerSort(Sort sort){
		String result = getClasse();
		
		//Si l'attaque touche tous les type ou si l'attaque et le Personnage sont de meme type
		if (sort.getTypeCible() == creatureType.TOUT || sort.getTypeCible() == getType()){
			int degatInflige = sort.getDegat();
			//Si bouclier alors on reduit les degats infliges
			for(Effet o : getEffet()){
				degatInflige -= o.getBouclier();
				result += " (degat reduit)";
			}
			//Reduis les PV
			setVie(getVie() - degatInflige);
			result += " pv -" + degatInflige + ", "+ getVie() + " pv restant";
			
			//Si il y a des effets
			if (sort.hasEffet()){
				//appliquer les effets au Personnage attaque
				getEffet().add(sort.getEffetProduit());
				
				result += ", effet subie : " + sort.getEffetProduit().toString();
			}
		} else {
			result += " attaque sans effet, cible " + getType().toString() + " intouchable par cette attaque";
		}
		return result + ".";
	}
        
	/**
	 * Ajoute une attaque a la liste des attaques du Personnage
	 * @param attaque a ajouter
	 */
	protected void ajouterAttaque(Sort attaque){
		this.attaque.add(attaque);
	}
	
	/**
	 * Verifie si le Personnage est place
	 * @throws ExceptionPersonnage Exception le Personnage n'a pas ete place
	 */
	public void estPlace() throws ExceptionPersonnage{
		if (getPosition().equals(new Position())){
			throw new ExceptionPersonnage(ExceptionPersonnage.error.POSITION);
		}
	}
	/**
	 * Test si le Personnage est place
	 * @return false si il n'est pas place, true sinon
	 */
	public boolean isPlace(){
		if (getPosition().equals(new Position())){
			return false;
		}
		return true;
	}
	
	/**
	 * Test si le Personnage est en vie
	 * @return true si il est vivant, false sinon
	 */
	public boolean estVivant(){
		return (getVie() > 0);
	}
	
	/**
	 * met a jour les effets sur le Personnage
	 */
	public void effetsTourSuivant(){
		Iterator effetIterateur = getEffet().iterator();
		
		while (effetIterateur.hasNext()){
			Effet monEffet = (Effet) effetIterateur.next();
			monEffet.effetTourSuivant();
			
			if (monEffet.getNbTourRestant() <= 0){
				effetIterateur.remove();
			}
		}
	}
	
	/**
	 * Recupere les points de mouvements
	 * @return int
	 */
	public int getPm(){
		return pm;
	}
	
	/**
	 * Recupere les points de vie
	 * @return int
	 */
	public int getVie() {
		return vie;
	}
        
        /**
	 * Recupere les points de vie maximum
	 * @return int
	 */
	public int getMaxVie() {
		return maxVie;
	}

	/**
	 * Recupere la matrice de mouvement
	 * @return Matrice
	 */
	public Matrice getMouvement() {
		return mouvement;
	}

	/**
	 * Recupere la classe du Personnage
	 * @return String
	 */
	public String getClasse() {
		return classe;
	}
        
        /**
         * Recupere le nom du Personnage
         * @return String
         */
        public String getNom() {
                return nom;
        }

	/**
	 *  Retourne l'image du Personnage
	 * @return ImageIcon
	 */
	public ImageIcon getImage() {
	 	return image;
	}
        
        /**
	 *  Retourne la vignette du Personnage
	 * @return ImageIcon
	 */
	public ImageIcon getVignette() {
	 	return vignette;
	}

	/**
	 * Retourne la liste d'attaque
	 * @return liste d'attaque
	 */
	public List<Sort> getAttaques() {
		return attaque;
	}
        
        /**
         * Retourne la liste de tous les deplacement possibles pour le Personnage courant
         * <p><<b>Attention :</b> la validite des deplacement sur le plateau n'est pas verifiee (case deja occupee ou hors du plateau)</p>
         * @return une liste de deplacements
         */
        public List<Deplacement> getDeplacements() {
                List<Deplacement> l = new ArrayList();
                for (Position destination : getMouvement().getCasesAccessibles(position)) {
                    l.add(new Deplacement(position, destination));
                }
                return l;
        }

	/**
	 * Retourne la liste d'effet que subit le Personnage
	 * @return liste d'effet
	 */
	public List<Effet> getEffet() {
		return effet;
	}
        
        /**
         * Teste si le personnage courant est actuellement ralenti
         * @return Vrai si le personnage est ralenti, faux sinon
         */
        public boolean isRalenti() {
            for (Effet e : effet) {
                if (e.getPmRetirer() > 0) {
                    return true;
                }
            }
            return false;
        }
        
        /**
         * Renvoie la valeur du bouclier du personnage courant
         * @return un entier positif ou nul si aucun bouclier
         */
        public int getBouclier() {
            int bouclier = 0;
            for (Effet e : effet) {
                bouclier += e.getBouclier();
            }
            return bouclier;
        }

	/**
	 * Getter de dejaJoueur
	 * @return dejaJoue
	 */
	public boolean isDejaJoue() {
		return dejaJoue;
	}

	/**
	 * Retourne l'emplacement du Personnage
	 * @return Position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Retourne le type du Personnage
	 * @return creatureType
	 */
	public creatureType getType() {
		return type;
	}
	
	/**
	 * Retourne le joueur qui possede le Personnage
	 * @return Joueur
	 */
	public Joueur getProprio() {
		return proprio;
	}
        
        @Override
        public String toString() {
            return getClasse() + " " + getNom()
                    + " [" + getVie() + "/" + getMaxVie() + "]"
                    + " (x,y) = (" + getPosition().toString() + ")"
                    + " (" + (dejaJoue?"deja joue":"pas encore joue") + ")";
        }
	
	/**
	 * Fixe les points de vie
	 * @param vie int
	 */
	public void setVie(int vie) {
		this.vie = Math.max(0, Math.min(maxVie, vie));
	}

	/**
	 * Fixe la portee de deplacement
	 * @param mouvement nouvelle matrice de mouvement
	 */
	public void setMouvement(Matrice mouvement) {
		this.mouvement = mouvement;
	}

	/**
	 * Fixe la classe du Personnage
	 * @param classe String
	 */
	public void setClasse(String classe) {
		this.classe = classe;
	}
        
        public void setNom(String nom) {
            this.nom = nom;
        }
        
        public void setNouveauNom() {
            setNom(Character.toString(nouveauNom));
            nouveauNom++;
        }

	/**
	 * Fixe l'image de Personnage
	 * @param image ImageIcon
	 */
	public void setImage(ImageIcon image) {
		this.image = image;
	}
        
        /**
	 * Fixe la vignette de Personnage
	 * @param vignette ImageIcon
	 */
	public void setVignette(ImageIcon vignette) {
		this.vignette = vignette;
	}

	/**
	 * Fixe la liste des attaques du Personnage
	 * @param attaque liste d'attaque
	 */
	public void setAttaque(List<Sort> attaque) {
		this.attaque = attaque;
	}

	/**
	 * Fixe les effets que subit le Personnage
	 * @param effet liste d'effet
	 */
	public void setEffet(List<Effet> effet) {
		this.effet = effet;
	}

	/**
	 * Fixe si le Personnage a deja joue ou non
	 * @param dejaJoue boolean
	 */
	public void setDejaJoue(boolean dejaJoue) {
		this.dejaJoue = dejaJoue;
	}

	/**
	 * Fixe la position, protected car pour ajouter un Personnage il faut verifier que la place n'est pas deja prise, donc on restreint l'acces
	 */
	protected void setPosition(Position position) {
		this.position = position;
		//Notifie le plateau
		setChanged();
		notifyObservers();
	}

	/**
	 * Fixe le type du Personnage
	 * @param type creatureType
	 */
	public void setType(creatureType type) {
		this.type = type;
	}

	/**
	 * Fixe le joueur proprietaire du Personnage
	 * @param proprio Joueur
	 */
	public void setProprio(Joueur proprio) {
		this.proprio = proprio;
	}
}