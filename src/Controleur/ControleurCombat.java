package Controleur;

import java.util.ArrayList;
import java.util.List;

import GUI.Vue3.VueJeu;
import GUI.Vue3.VueJeuCombat;
import GUI.Vue3.Joueur.VueJoueurCombat;
import Model.Sort;
import Model.Effet;
import Model.Matrice;
import Model.Personnage;
import Model.Position;

/**
 * Classe controleur de combat (deroulement d'un tour de jeu)
 * @author Warlot/Gasquez
 *
 */

public class ControleurCombat extends AbstractControleurJeu {
        /**
         * Nombre maximal d'actions par tour d'un personnage (1 ou 2)
         */
        static private final boolean UNE_SEULE_ACTION_PAR_TOUR = true;
	//Case cliquable, sauvegarde du status du plateau, utile lors des actions de souris (click, entered, exited)
	private List<Position> caseClickable = new ArrayList<Position>();
	
	//Etat du tour, attaque multiple, attaque, deplacement
	private boolean coupsRestant = true;
	private boolean attaque = true;
	private boolean deplacement = true;
	
	/**
	 * Constructeur du tour de jeu
	 * @param maVue panel comprenant les deux panels joueur et le panel plateau
	 * @param controleurParent controleur de la fenetre
	 */
	public ControleurCombat(VueJeu maVue, ControleurFenetre controleurParent){
		super(maVue, controleurParent);
		
		getPartie().setEtatTourPasser();
                getPartie().getJoueurActuel().getEquipe().clearPersonnageActif();
	}
	
	/**
	 * Clique sur la case du plateau
	 * @param maPosition emplacement de la case clique
	 */
	public void actionClick(Position maPosition){
		//Si case non cliquable
		if (!caseClickable.contains(maPosition)){
			return;
		}
		
		//Si le tous a demarre
		if (getPartie().isDeplacementEnCours() || getPartie().isAttaqueEnCours()){
			//Verouille le choix des Personnage (le tour demarre)
			((VueJoueurCombat) getVue().getPanelJoueurActuel()).verouillerChoixPersonnage();
		}
	
		if (getPartie().isDeplacementEnCours() && deplacement){
			//si deplacement alors on se deplace
			if (getPartie().setPositionPersonnage(maPosition)){
				deplacement = false;
                                if (UNE_SEULE_ACTION_PAR_TOUR) {
                                    //empecher toute attaque
                                    ((VueJoueurCombat) getVue().getPanelJoueurActuel()).verouillerChoixAttaque();
                                    attaque = false;
                                }
			}			
			
			//empecher le choix d'un autre deplacement
			((VueJoueurCombat) getVue().getPanelJoueurActuel()).verouillerChoixDeplacement();
			
			//Reset couleur plateau
			getVue().getPanelPlateau().afficherPlateauParDefaut();
                        
                        majConsole(getPartie().getPersonnageActif() + " se déplace en " + maPosition + ".");
			
		} else if (getPartie().isAttaqueEnCours() && attaque){
			//Sinon si attaque alors on attaque la case
			
			//Fixer les Personnage attaque par l'attaque en cours
			List<Position> caseAccessible = getListCaseAttaqueZone(maPosition);
			
			for (Position maPos : caseAccessible){	
				getPartie().setPersonnagesAttaques(
						maPos,
						getAttaqueActif()
						);
			}

			((VueJoueurCombat) getVue().getPanelJoueurActuel()).verouillerChoixAttaque();

                        if (UNE_SEULE_ACTION_PAR_TOUR) {
                            //empecher tout deplacement
                            ((VueJoueurCombat) getVue().getPanelJoueurActuel()).verouillerChoixDeplacement();
                            deplacement = false;
                        }
                        
			//si attaque multiple
			if (getAttaqueActif().isAttaqueMultiple()){
				if (!coupsRestant){
					attaque = false;
				} else {
					coupsRestant = false;
				}
			} else {
				attaque = false;
			}
			
			//Applique l'attaque et recuperer le resultat
			List<String> resultatAttaque = getPartie().lancerAttaque();
			
			//Met a jour la console
			if (resultatAttaque.isEmpty()){
				majConsole("Attaque sans effet sur les personnages.");
			}else{
				for (String o : resultatAttaque){
					if (attaque){
						o += " 1 attaque restante.";
					}
					majConsole(o);
				}
			}
		} else {
			//Sinon ne rien faire
		}		
	}
	
	/**
	 * Action effectue lors de l'entree de la sourie sur une case
	 * @param maPosition case clique
	 */
	public void actionEntered(Position maPosition){
		//Si case non cliquable
		if (!caseClickable.contains(maPosition)){
			return;
		}
		
		if (getPartie().isDeplacementEnCours() && deplacement){
			afficherCaseDeplacementAutorise(maPosition);
		} else if (getPartie().isAttaqueEnCours() && attaque){
			afficherZoneAttaque(maPosition);
		}
	}
	
	/**
	 * Action effectue lors de la sortie de la sourie sur une case
	 * @param maPosition case clique
	 */
	public void actionExited(Position maPosition){
		//Si case non cliquable
		if (!caseClickable.contains(maPosition)){
			return;
		}
		
		if (getPartie().isDeplacementEnCours() || getPartie().isAttaqueEnCours()){
			getVue().getPanelPlateau().afficherCouleurPlateau();
		}
	}

	/**
	 * Affiche le label en parametre dans le panel sud
	 * @param texte Texte a afficher
	 */
	public void majConsole(String texte){
		getVue().majConsole(texte);
	}
	
	/**
	 * Design pattern observateur/observe, ajoute l'observateur plateau sur les Personnage
	 */
	public void observePersonnages(){
		for(Personnage o : getPartie().listerEquipes()){
			o.addObserver(
				getVue().getPanelPlateau()
			);
		}
	}

	/**
	 * Passe au tour de jeu suivant et fixe l'etat passif aupres du model
	 */
	public void passerSonTour() {
		//Indique que ce Personnage a deja joue
		getPersonnageActif().setDejaJoue(true);

		//decremente de 1 tour les effets de tous les Personnage
		getPersonnageActif().effetsTourSuivant();
		
		//Fixe l'etat passif
		getPartie().setEtatTourPasser();
                
                if (attaque && deplacement) {
                    majConsole(getPartie().getPersonnageActif() + " passe son tour.");
                }
		
		//Passe au tour suivant
		getControleurParent().tourSuivant();
	}
        
	/**
	 * Affiche la comme autorise
	 * @param maPosition emplacement de la case concerne
	 */
	public void afficherCaseDeplacementAutorise(Position maPosition){
		//Affichage temporaire, donc pas d'enregistrement et seulement un affichage
		getVue().getPanelPlateau().caseCible(maPosition);
	}
	
	/**
	 * Affiche la portee du deplacement du Personnage actif
	 */
	public void afficherPorteeDeplacement(){
		Personnage o = getPartie().getPersonnageActif();
		
		Position positionPersonnage = o.getPosition();
		Matrice mouvementPersonnage = o.getMouvement();
		
		//Si effet alors on les appliques(reduction de la portee de deplacement)
		int malusDeplacement = 0;
		for(Effet oEffet : o.getEffet()){
			if (oEffet.getPmRetirer() > 0){
				malusDeplacement += oEffet.getPmRetirer();
				mouvementPersonnage = mouvementPersonnage.reduireZone(malusDeplacement);
			}
		}
		
		//Recuperer les positions des cases cible
		List<Position> caseAccessible = mouvementPersonnage.getCasesAccessibles(positionPersonnage);

		List<Position> caseAccessibleDispo = new ArrayList();
                List<Position> caseInaccessibleDispo = getPartie().getToutesPositions();
                caseInaccessibleDispo.removeAll(caseAccessible);
		
		//Enlever les cases occupÃ©s par les Personnage
		for (Position maPosition : caseAccessible){
			if (getPartie().isCaseLibre(maPosition)){
				caseAccessibleDispo.add(maPosition);
			}
		}

		//Sauvegarde des cases cliquables
		this.caseClickable = caseAccessibleDispo;
		
		for (Position maPosition : caseInaccessibleDispo){
				//Enregistrer les cases
				getVue().getPanelPlateau().caseNonCiblable(maPosition);
		}
		//Afficher les cases
		getVue().getPanelPlateau().afficherCouleurPlateau();
	}
	
	/**
	 * Affiche la portee de l'attaque selectionne, du Personnage selectionne
	 */
	public void afficherPorteeAttaque(){
		Personnage o = getPartie().getPersonnageActif();
		Sort oA = getPartie().getAttaqueActif();
		
		Position positionPersonnage = o.getPosition();
		int porteeMin = oA.getPorteeMin();
		int porteeMax = oA.getPorteeMax();
		
                this.caseClickable.clear();
		if (porteeMax != - 1){
			Matrice porteePersonnage = new Matrice(porteeMin, porteeMax, false);
			
			List<Position> caseAccessible = porteePersonnage.getCasesAccessibles(positionPersonnage);
                        List<Position> caseInaccessible = getPartie().getToutesPositions();
                        caseInaccessible.removeAll(caseAccessible);

			//Filtrage des cases avec un personnage dessus
                        //Sauvegarde des cases cliquables
                        for (Position p : caseAccessible) {
                            if (!getPartie().isCaseLibre(p)) {
                                this.caseClickable.add(p);
                            }
                        }
			
			for (Position maPosition : caseInaccessible){
				//Enregistrer les cases
				getVue().getPanelPlateau().caseNonCiblable(maPosition);
			}
		} else {
			//Toutes les cases occupées sont attaquables
			//Sauvegarde des cases cliquables
                        for (Position p : getPartie().getToutesPositions()) {
                            if (!getPartie().isCaseLibre(p)) {
                                this.caseClickable.add(p);
                            }
			}
			
			//Enregistrer les cases
			getVue().getPanelPlateau().toutesLesCasesCiblable();
		}
		//Afficher les cases
		getVue().getPanelPlateau().afficherCouleurPlateau();
	}
	
	/**
	 * Affiche la zone d'effet de l'attaque
	 * @param positionCase emplacement de l'epicentre de l'attaque
	 */
	public void afficherZoneAttaque(Position positionCase){
		List<Position> caseAccessible = getListCaseAttaqueZone(positionCase);
		
		for (Position maPosition : caseAccessible){
			//Affichage temporaire, donc pas d'enregistrement et seulement un affichage
			getVue().getPanelPlateau().caseCible(maPosition);
		}
	}
	
	/**
	 * Donne toutes les cases atteintes par la zone d'attaque, l'epicentre est donne par l'emplacement fourni en parametre
	 * @param positionCase emplacement du centre de la zone d'attaque
	 * @return retourne la liste des cases touchables par la zone d'attaque
	 */
	public List<Position> getListCaseAttaqueZone(Position positionCase){
		Sort oA = getPartie().getAttaqueActif();
		Matrice attaquePersonnage = oA.getZone();
		
		List<Position> caseAccessible = attaquePersonnage.getCasesAccessibles(positionCase);
		
		return caseAccessible;
	}
	
	/**
	 * Retourne le  Personnage actif
	 * @return Personnage
	 */
	public Personnage getPersonnageActif(){
		return getPartie().getPersonnageActif();
	}

	/**
	 * Fixe le Personnage actif
	 * @param o nouveau Personnage actif
	 */
	public void setAttaqueActif(Sort o){
		getPartie().setAttaqueActif(o);
	}
	
	/**
	 * 	Retourne l'attaque active
	 * @return Sort
	 */
	public Sort getAttaqueActif(){
		return getPartie().getAttaqueActif();
	}

	/**
	 * Retourne la liste d'attaque du Personnage actif
	 * @return liste d'attaque
	 */
	public List<Sort> getAttaque(){
		return this.getPersonnageActif().getAttaques();
	}
	
	/**
	 * Retourne la liste de Personnage de l'equipe du joueur actuel
	 * @return liste de Personnage
	 */
	public List<Personnage> getMonEquipe(){
		return getPartie().listerEquipeJoueur();
	}
	
	@Override
	public VueJeuCombat getVue() {
		return (VueJeuCombat) vue;
	}
	
	/**
	 * Fixe le Personnage fournie en tant que Personnage actif aupres du model et change la couleur de la case
	 * @param o Personnage actif
	 */
	public void setPersonnageActif(Personnage o){
		getPartie().setPersonnageActif(o);
		
		getPartie().setEtatTourPasser();
		getVue().getPanelPlateau().afficherPlateauParDefaut();
		getVue().getPanelPlateau().enregistrerCouleurPersonnagesJoueur();
	}
	
	/**
	 * Affiche les deplacements autorises sur la carte et fixe l'etat deplacement aupres du model
	 */
	public void setDeplacer() {
		getPartie().setEtatTourDeplacer();
		
		//Reset couleur plateau
		getVue().getPanelPlateau().afficherPlateauParDefaut();
		
		//Afficher portee deplacement
		afficherPorteeDeplacement();
	}
	
	/**
	 * Affiche la portee de l'attaque et fixe l'etat attaque aupres du model
	 * @param monAttaque attaque en cours
	 */
	public void setAttaquer(Sort monAttaque) {
		setAttaqueActif(monAttaque);
		getPartie().setEtatTourAttaquer();
		
		//Reset couleur plateau
		getVue().getPanelPlateau().afficherPlateauParDefaut();
		
		//Afficher portee attaque
		afficherPorteeAttaque();
	}
}
