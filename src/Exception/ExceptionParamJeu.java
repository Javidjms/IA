package Exception;

/**
* La classe ExceptionParamJeu definie les erreurs des parametres du jeu, taille negative, plateau trop petit
* @author Warlot/Gasquez
*
*/

public class ExceptionParamJeu extends Exception{
	public enum error{
		NEGATIVE, PLATEAU
	}
	
	private error explication = null;
	
	/**
	 * Constructeur de l'erreur de parametrage du plateau
	 * @param type error type
	 */
	public ExceptionParamJeu(error type){
		this.explication = type;
	}
	
	/**
	 * Retourne l'erreur produite
	 * @return error
	 */
	public error getExplication(){
		return explication;
	}
}

