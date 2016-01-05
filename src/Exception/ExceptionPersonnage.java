package Exception;

/**
* La classe ExceptionPersonnage definie les erreurs des personnages
* @author Warlot/Gasquez
*
*/

public class ExceptionPersonnage extends Exception {
	public enum error{
		POSITION
	}
	
	private error explication = null;
	
	/**
	 * Constructeur
	 * @param type error type
	 */
	public ExceptionPersonnage(error type){
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
