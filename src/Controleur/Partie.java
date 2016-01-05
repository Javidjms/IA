package Controleur;

import Model.*;
import Controleur.ControleurPlacement.coteJeu;
import Exception.ExceptionPersonnage;
import Exception.ExceptionParamJeu;
import GUI.Fenetre;
import GUI.Vue3.Plateau.CasePlateau;

import java.util.*;

import Personnages.*;

/**
 * La classe Partie est le controleur du systeme, elle est le pivot etre le model et la vue
 * @author Scrat
 *
 */

public class Partie {
        /******************DELAI******************/
    
        private static final int DELAY = 4;
        
	/******************ATTRIBUT******************/
        
	/**
	 * Collection Personnage disponible lors de la selection des equipes
	 */
	private static List<Personnage> personnagesDisponibles = new ArrayList<Personnage>();
	
	/**
	 * Taille des equipes
	 */
	private int tailleEquipe = 1;
	
	/**
	 * Taille du plateau de jeu
	 */
	private int plateauHauteur = 3;
	private int plateauLargeur = 3;
	
	/**
	 * Collection de joueurs
	 */
	private List<Joueur> joueurs = new ArrayList<Joueur>();
	
	/**
	 * Iterateur sur la collection de joueurs
	 */
	private Iterator<Joueur> joueurIterateur;
	
	/**
	 * Joueur actuellement en jeu
	 */
	private Joueur joueurActuel = null;
        
	
	/******************CONSTRUCTEUR******************/
	/**
	 * Constructeur de la classe Partie
	 */
	public Partie(){
		new Fenetre(this);
		initJoueurs();
		initPersonnagesDisponibles();
		
		determinerOrdre();
	}
        
        /**
	 * Constructeur de la classe Partie
	 */
	public Partie(boolean creerFenetre, Joueur j1, Joueur j2) {
                if (creerFenetre) {
                    new Fenetre(this);
                }
                joueurs.add(j1);
                joueurs.add(j2);
                joueurIterateur = getJoueurs().iterator();
		//Place l'iterateur sur le premier joueur
		setJoueurActuel(joueurIterateur.next());
                
		initPersonnagesDisponibles();
                joueurSuivant();
                joueurSuivant();
	}
        
        /**
         * Clone la partie courante
         * @return une nouvelle partie
         */
        public Partie clone() {
            Partie clone = new Partie(false, this.joueurs.get(0).clone(), this.joueurs.get(1).clone());
            
            clone.tailleEquipe = this.tailleEquipe;
            clone.plateauHauteur = this.plateauHauteur;
            clone.plateauLargeur = this.plateauLargeur;
                    
            boolean found_current_player = false;
            clone.joueurIterateur = clone.joueurs.iterator();

            for (int i = 0; i < 2; i++) {
                // Verifier si on est sur le joueur courant
                if (this.joueurActuel == this.joueurs.get(i)) {
                    clone.joueurActuel = clone.joueurs.get(i);
                    found_current_player = true;
                }
                // Avancer l'iterateur si le joueur courant n'a pas encore ete atteint
                if (!found_current_player) {
                    clone.joueurIterateur.next();
                }
            }
            return clone;
        }
	
	
	/******************INITIALISATION******************/
	/**
	 * Initialtion des joueurs du jeu
	 */
	private void initJoueurs(){
		getJoueurs().add(new Joueur("Joueur 1"));
		getJoueurs().add(new Joueur("Joueur 2"));
		joueurIterateur = getJoueurs().iterator();
		//Place l'iterateur sur le premier joueur
		setJoueurActuel(joueurIterateur.next());
	}
	
	/**
	 * Reinitialise le jeu
	 */
	public void reset() {
		//Fixe le premier joueur
		determinerOrdre();
		
		//Reset les equipes
		for(Joueur o : getJoueurs()){
			o.clearEquipe();
		}
		
		//Reset le placement sur le plateau (placement)
		ControleurPlacement.setCoteJeuChoisi(coteJeu.AUCUN);
	}
	
	/**
	 * Initialisation des Personnage disponible dans le jeu
	 */
	private void initPersonnagesDisponibles(){
                getPersonnagesDisponibles().clear();
		getPersonnagesDisponibles().add(new Magicien());
		getPersonnagesDisponibles().add(new Guerrier());
		getPersonnagesDisponibles().add(new Voleur());
		getPersonnagesDisponibles().add(new Cavalier());
	}
	
	
	/**
	 * Determination du joueur qui jouera en premier(=composera son equipe)
	 */
	private void determinerOrdre(){
		int step = 3;
		int min = 0;
		int max = step * getNbJoueurs();

		int nombreAlea = (int) (min + ( Math.random() * (max - min)));
		nombreAlea = nombreAlea / step;
		
		while(getJoueurActuel() != getJoueurs().get(nombreAlea)){
			joueurSuivant();
		}
	}
	
	
	/******************JOUEUR******************/
	/**
	 * Renvoie la collection de personnages du joueur actuel
	 * @return liste de Personnage
	 */
	public List<Personnage> listerEquipeJoueur(){
		List<Personnage> tousLesPersonnagesJoueur = new ArrayList<Personnage>();
		
		for(Personnage obj : getJoueurActuel().getEquipe().getMembres()){
			if (obj.estVivant() && obj.isPlace()){
				tousLesPersonnagesJoueur.add(obj);
			}
		}
		
		return tousLesPersonnagesJoueur;
	}
        
        /**
	 * Renvoie la collection de personnages des joueurs adverses
	 * @return liste de personnages
	 */
	public List<Personnage> listerEquipesAdverses(){
		List<Personnage> personnages = new ArrayList();
		
                for (Joueur j : getJoueurs()) {
                    if (j != getJoueurActuel()) {
                        for(Personnage pers : j.getEquipe().getMembres()) {
                            if (pers.estVivant() && pers.isPlace()){
                                personnages.add(pers);
                            }
                        }
                    }
                }
		
		return personnages;
	}
	
	/**
	 * Renvoie une collection contenant tous les personnages utilises par les joueurs
	 * @return collection de personnages
	 */
	public List<Personnage> listerEquipes(){
		List<Personnage> tousLesPersonnages = new ArrayList<Personnage>();
		
		for(Joueur o : getJoueurs()){
			for(Personnage obj : o.getEquipe().getMembres()){
				if (obj.estVivant() && obj.isPlace()){
					tousLesPersonnages.add(obj);
				}
			}
		}
		
		return tousLesPersonnages;
	}
		
	/**
	 * Ajoute le Personnage en parametre au joueur actuel
	 * @param personnage Personnage a ajouter au joueur actuel
	 */
	public void ajouterPersonnageJoueur(Personnage personnage){
		getJoueurActuel().ajouterMembre((Personnage) personnage.clone());
	}
	
	/**
	 * dit au joueur actuel que tous ses Personnage sont place
	 * @throws ExceptionPersonnage si il ne sont pas tous place
	 */
	public void personnagesTousPlaces() throws ExceptionPersonnage{
		joueurActuel.personnagesTousPlaces();
	}
	
	/**
	 * Passe au joueur suivant, disponible dans l'attibut joueurActuel
	 */
	public void joueurSuivant(){
		if (getJoueurIterateur().hasNext()) {
			setJoueurActuel(getJoueurIterateur().next());
		} else {
			setJoueurIterateur(getJoueurs().iterator());
			setJoueurActuel(getJoueurIterateur().next());
		}
	}
        
        /**
         * Passe au tour suivant
         * @return Vrai s'il y a un tour suivant, faux sinon (partie terminee)
         */
        public boolean tourSuivant() {
            
            //Recupere tous les personnages qui sont encore en jeu
		List<Personnage> tousLesPersonnages = listerEquipes();
		boolean encoreDeuxAdversaire = false;
		//On regarde si il y a un personnage qui a un joueur different du premier personnage
		if (tousLesPersonnages.isEmpty()){
			encoreDeuxAdversaire = false;
		}else{
			//Si les deux joueurs sont encore sur le plateau
			for (Personnage o : tousLesPersonnages){
				if(o.getProprio() != tousLesPersonnages.get(0).getProprio()){
					encoreDeuxAdversaire = true;
					break;
				}
			}
		}
		
		//Test si il n'y a plus qu'un joueur
		if(!encoreDeuxAdversaire){
			//Si il y a un gagnant, alors on le notify
			if (!tousLesPersonnages.isEmpty()){
				signifierVictoire(tousLesPersonnages.get(0).getProprio());
			}
                        //Si il n'y a plus deux adversaire alors on fini le jeu
			return false;
		}

		//Joueur suivant
		joueurSuivant();
		
		//Recherche un personnage du joueur actuel qui n'a pas deja joue
		boolean tousPersonnagesjoueurOntJoue = true;
		for(Personnage o : listerEquipeJoueur()){
			if (!o.isDejaJoue()){
				tousPersonnagesjoueurOntJoue = false;
				break;
			}
		}
		//Si la totalite des personnages du joueur actuel a joue
		if (tousPersonnagesjoueurOntJoue){
			//Si tous les personnages du jeu ont joue
			boolean tousPFontJoue = true;
			for(Personnage o : tousLesPersonnages){
				if (!o.isDejaJoue()){
					tousPFontJoue = false;
					break;
				}
			}
			//Si tous les personnages du plateau ont joue
			if (tousPFontJoue){
				//remise a zero de l'etat aJoue de chaque personnage
				for(Personnage o : tousLesPersonnages){
					o.setDejaJoue(false);
				}
			} else {
				//Le tour de jeu repasse au meme joueur
				joueurSuivant();
			}
		}
                return true;
        }
	
	public boolean isDeplacementEnCours(){
		return getJoueurActuel().IsdeplacementEnCours();
	}
	public boolean isAttaqueEnCours(){
		return getJoueurActuel().IsAttaqueEnCours();
	}
	public boolean isPassifEnCours(){
		return getJoueurActuel().IsPassifEnCours();
	}
	
	public boolean isCaseLibre(Position maPosition){
		for(Personnage o : listerEquipes()){
			if (o.getPosition().equals(maPosition)){
				return false;
			}
		}
		return true;
	}
        
        /**
         * Test si une case est bien sur le plateau
         * @param position Position de la case sur le plateau
         * @return vrai si la case est valide, faux sinon
         */
        public boolean isCaseValide(Position position) {
            return position.getY() >= 0 && position.getY() < getPlateauHauteur()
                    && position.getX() >= 0 && position.getX() < getPlateauLargeur();
        }
	
	public List<String> lancerAttaque(){
		return getJoueurActuel().lancerAttaque();
	}
        
        /**
         * Applique un coup sur la partie courante
         * @param coup Le coup a appliquer
         */
        public void appliquerCoup(Coup coup) {
            // Chercher l'auteur dans la partie courante (car eventuellement un clone de partie)
            Personnage auteur = null;

            for (Personnage perso : listerEquipes()) {
                if (perso.equals(coup.getAuteur())) {
                    auteur = perso;
                }
            }

            //Selectionner le personnage qui joue le coup
            setPersonnageActif(auteur);
            
            //Appliquer toutes les actions du coup
            for (Action a : coup.getActions()) {
                a.appliquer(this);
            }
            
            //Indique que ce Personnage a deja joue
            getPersonnageActif().setDejaJoue(true);
            
            //Decremente de 1 tour les effets de tous les Personnage
            getPersonnageActif().effetsTourSuivant();
            
            //Fixe l'etat passif
            setEtatTourPasser();
        }
	
	/****************** GETTERS ******************/
	/**
	 * Renvoi le nom de Personnage du joueur actuel
	 * @return nom du joueur
	 */
	public String getNomJoueur(){
		return getJoueurActuel().getNom();
	}
	
	/**
	 * Donne le Personnage actif du joueurActuel
	 * @return Personnage retourne le Personnage actif
	 */
	public Personnage getPersonnageActif(){
		return getJoueurActuel().getPersonnageActif();
	}
	
	public Sort getAttaqueActif(){
		return getJoueurActuel().getAttaqueActif();
	}
	
	public List<Personnage> getPersonnagesDisponibles() {
		return personnagesDisponibles;
	}
	public int getTailleEquipe() {
		return tailleEquipe;
	}
	public int getPlateauHauteur() {
		return plateauHauteur;
	}
	public int getPlateauLargeur() {
		return plateauLargeur;
	}
        /**
         * Retourne les position du plateau
         * @return une liste de positions
         */
        public List<Position> getToutesPositions() {
            List<Position> l = new ArrayList();
            for (int i = 0; i < getPlateauHauteur(); i++) {
                for (int j = 0; j < getPlateauLargeur(); j++) {
                    l.add(new Position(i, j));
                }
            }
            return l;
        }
        
        /**
         * Retourne tous les coups possibles pour le joueur actif
         * @return une liste de coups
         */
        public synchronized List<Coup> getTousCoups() {
            List<Coup> tousCoups = new ArrayList();
            
            // Calculer toutes les cases libres
            List<Position> casesLibres = getToutesPositions();
            for (Personnage perso : listerEquipes()) {
                casesLibres.remove(perso.getPosition());
            }
            
            List<Personnage> tousPersonnages = listerEquipes();
            
//            System.out.println(Thread.currentThread().getName()+": "+"Equipe courante = " + getJoueurActuel().getEquipe().toString());
            for (Personnage pf : getJoueurActuel().listerEquipe()) {
//                System.out.println("Coup de " + pf.toString());
                if (pf.estVivant() && !pf.isDejaJoue()) {
//                    System.out.println("OK");
                    tousCoups.addAll(getTousCoupsPersonnage(pf, casesLibres, tousPersonnages));
                }
            }
//            System.out.println("-> " + tousCoups.size() + " coups");
            return tousCoups;
        }
        
        /**
         * Retourne la liste des coups possibles par un Personnage
         * @param pf le Personnage
         * @return une liste de coups
         */
        public List<Coup> getTousCoupsPersonnage(Personnage pf) {
            // Calculer toutes les cases libres
            List<Position> casesLibres = getToutesPositions();
            for (Personnage perso : listerEquipes()) {
                casesLibres.remove(perso.getPosition());
            }
            
            return getTousCoupsPersonnage(pf, casesLibres, listerEquipes());
        }
        
        /**
         * Retourne la liste des coups possibles par un Personnage
         * @param pf le Personnage
         * @param casesLibres Liste des cases libres
         * @param tousPersonnages Liste des personnages sur le plateau
         * @return une liste de coups
         */
        protected List<Coup> getTousCoupsPersonnage(Personnage pf, List<Position> casesLibres, List<Personnage> tousPersonnages) {
            List<Coup> coups = new ArrayList();
            // Coup nul
            coups.add(new Coup(pf, new ArrayList()));
            
            // Deplacements seuls
            List<Deplacement> deplacementsTheoriques = pf.getDeplacements();
            for (Deplacement d : deplacementsTheoriques) {
//                System.out.println("D�placement theorique = " + d.toString());
//                System.out.println("-> " + (isCaseValide(d.getDestination())?"valide":"pas valide") + " && " + (isCaseLibre(d.getDestination())?"libre":"pas libre"));
                if (isCaseValide(d.getDestination()) && casesLibres.contains(d.getDestination())) {
                    coups.add(new Coup(pf, d));
                }
            }
            
            // Attaques seules
//            System.out.println("Personnage = " + pf.getClasse() + " " + pf.getNom());
            for (Sort sort : pf.getAttaques()) {
                
//                System.out.println("Sort = " + sort.getNom());
                // Tester si chaque personnage est atteignable
                for (Personnage cible : tousPersonnages) {
                    
//                    System.out.println("Cible = " + cible.getClasse() + " " + cible.getNom());
                    completerCoups(coups, pf, sort, cible, tousPersonnages, casesLibres, null);
                }
            }
            return coups;
        }
        
        protected void completerCoups(List<Coup> coups, Personnage pf, Sort sort, Personnage cible, List<Personnage> tousPersonnages, List<Position> casesLibres, Coup coupDeBase) {

            // Si le personnage est atteignable
            if (sort.peutAtteindre(pf.getPosition(), cible.getPosition())) {
    //                        System.out.println("Atteignable");
                // Construire la liste des cases ciblees avec le personnage cible comme centre
                List<Position> casesCiblees = sort.getZone().getCasesAccessibles(cible.getPosition());
                casesCiblees.removeAll(casesLibres);

    //                        for (Position p : casesCiblees) {
                //                            System.out.println("Case ciblee = " + p.toString());
                //                        }
                // Chercher les personnages sur ces cases
                List<Personnage> cibles = new ArrayList();
                cibles.add(cible);

                for (Personnage cibleCollaterale : tousPersonnages) {
                    if (cibleCollaterale != cible) {
                        //                                System.out.println("Cible collaterale = " + cibleCollaterale.getClasse() + " " + cibleCollaterale.getNom());
                        if (casesCiblees.contains(cibleCollaterale.getPosition())) {
                            //                                    System.out.println("Atteignable");
                            cibles.add(cibleCollaterale);
                        }
    //                                else {
                        //                                    System.out.println("Pas atteignable");
                        //                                }
                    }
                }

                // Construire le coup correspondant
                Coup nouveauCoup;
                // Si aucun coup de base
                if(coupDeBase == null) {
                    nouveauCoup = new Coup(pf, new Attaque(sort, cibles));
                    coups.add(nouveauCoup);
                    if (sort.isAttaqueMultiple()) {
                        for (Personnage nouvelleCible : tousPersonnages) {
                            if (nouvelleCible != cible || cible.getVie() - sort.getDegat() > 0) {
                                completerCoups(coups, pf, sort, nouvelleCible, tousPersonnages, casesLibres, nouveauCoup);
                            }
                        }
                    }
                //                        System.out.println("Ajout de " + new Coup(pf, new Attaque(sort, cibles)));
                }
                else {
                    List<Action> actions = new ArrayList();
                    for (Action action : coupDeBase.getActions()) {
                        actions.add(action); // Pas de clone, �a devrait �tre bien
                    }
                    actions.add(new Attaque(sort, cibles));
                    nouveauCoup = new Coup(pf, actions);
                }
                coups.add(nouveauCoup);

            }
//            else {
//                System.out.println("Pas atteignable");
//            }
        }
        
	public List<Joueur> getJoueurs() {
		return joueurs;
	}
	public int getNbJoueurs(){
		return getJoueurs().size();
	}
	public Joueur getJoueurActuel() {
		return joueurActuel;
	}
	private Iterator<Joueur> getJoueurIterateur() {
		return joueurIterateur;
	}

	public void signifierVictoire(Joueur joueurGagnant) {
		joueurGagnant.signifierVictoire();
		
	}
        
        /**
         * Teste si la partie courante est terminee
         * @return Vrai si la partie est terminee, faux sinon
         */
        public boolean estTerminee() {
            for (Joueur j : joueurs) {
                if (j.estBattu()) { return true; }
            }
            return false;
        }
	
	/****************** SETTERS ******************/
	/**
	 * Fixe le Personnage actif du joueurActuel
	 * @param o Personnage a mettre a actif
	 */
	public void setPersonnageActif(Personnage o){
		getJoueurActuel().setPersonnageActif(o);
	}
	
	public void setAttaqueActif(Sort o){
		getJoueurActuel().setAttaqueActif(o);
	}
	
	public void setEtatTourPasser() {
		getJoueurActuel().setEtatTourPasser();
	}

	public void setEtatTourDeplacer() {
		getJoueurActuel().setEtatTourDeplacer();
	}
	
	public void setEtatTourAttaquer() {
		getJoueurActuel().setEtatTourAttaquer();
	}
	
	public boolean setPositionPersonnage(Position maPosition){
		if(!isCaseLibre(maPosition)){
			return false;
		}
		getJoueurActuel().getEquipe().setPositionPersonnage(maPosition);
		return true;
	}
	
	/**
	 * Attaque avec le sort passe en parametre sur la case dont la position est passee en parametre, ne fait rien si la case est vide
	 * @param maPosition position a attaquer
	 * @param monAttaque sort selectionne
	 */
	public void setPersonnagesAttaques(Position maPosition, Sort monAttaque){
		//Parcours de tous les Personnage en jeu
		for(Personnage o : listerEquipes()){
			if (o.getPosition().equals(maPosition)){
				getJoueurActuel().addPersonnageAttaque(o);
				break;
			}
		}
	}
	
	/**
	 * Fixe les tailles d'equipes et de plateau, leve une exception si erreur (taille negative, plateau trop petit)
	 * @param tailleEquipe taille de l'equipe
	 * @param taillePlateauLongueur longueur du plateau
	 * @param taillePlateauLargeur largeur du plateau
	 * @throws ExceptionParamJeu exception taille negative ou plateau trop petit
	 */
	public void setTailleEquipePlateau(int tailleEquipe, int taillePlateauHauteur, int taillePlateauLargeur) throws ExceptionParamJeu{
		if (tailleEquipe <= 0 || taillePlateauHauteur <= 0 || taillePlateauLargeur <= 0){
			throw new ExceptionParamJeu(ExceptionParamJeu.error.NEGATIVE);
		}
		if ((taillePlateauHauteur * taillePlateauLargeur) < (tailleEquipe * 2)){
			throw new ExceptionParamJeu(ExceptionParamJeu.error.PLATEAU);
		}
		setTailleEquipe(tailleEquipe);
		setPlateauHauteur(taillePlateauHauteur);
		setPlateauLargeur(taillePlateauLargeur);
	}
	
	
	/******************PRIVATE SETTERS******************/
	private void setPersonnagesDisponibles(List<Personnage> personnagesDisponible) {
		personnagesDisponibles = personnagesDisponible;
	}
	private void setTailleEquipe(int tailleEquipe) {
		this.tailleEquipe = tailleEquipe;
	}
	private void setPlateauHauteur(int plateauHauteur) {
		this.plateauHauteur = plateauHauteur;
	}
	private void setPlateauLargeur(int plateauLargeur) {
		this.plateauLargeur = plateauLargeur;
	}
	private void setJoueurs(List<Joueur> joueurs) {
		this.joueurs = joueurs;
	}
	private void setJoueurIterateur(Iterator<Joueur> joueurIterateur) {
		this.joueurIterateur = joueurIterateur;
	}
	private void setJoueurActuel(Joueur joueurActuel) {
		this.joueurActuel = joueurActuel;
	}
}