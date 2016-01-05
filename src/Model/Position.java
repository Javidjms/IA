package Model;
/**
 * Classe pour definir une position
 * Definie par un attribut x (position horizontal) et un attribut y (position verticale)
 * @author Warlot/Gasquez
 *
 */
public class Position {
	private int x = 0;
	private int y = 0;
	
	/**
	 * Constructeur
	 * @param x position suivant l'axe x
	 * @param y position suivant l'axe y
	 */
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	/**
	 * Constructeur de coordonnee -1, -1
	 */
	public Position(){
		this(-1, -1);
	}

	/**
	 * Teste l'égalité entre deux instances de la classe Position
	 */
	public boolean equals(Object obj){
		if (obj == this){
			return true;
		}
		if (!(obj instanceof Position)){
			return false;
		}
		Position Temp = (Position) obj;
		return (this.getX() == Temp.getX() && this.getY() == Temp.getY());
	}
        
        /**
         * Clone la position courante
         */
        @Override
        public Object clone() {
            return new Position(this.x, this.y);
        }
	
	/** 
	 * Retourne la position x et y
	 * @return String position
	 */
	public String toString(){
		return "Position : " + getX() +  " " + getY();
	}
	
	/**
	 * Retourne la position sur l'axe x
	 * @return int x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Retourne la position sur l'axe y
	 * @return int y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Fixe la position sur l'axe y
	 * @param y nouvelle position y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Fixe la position sur l'axe x
	 * @param x nouvelle position x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Fixe les positions x et y
	 * @param x int
	 * @param y int
	 */
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
