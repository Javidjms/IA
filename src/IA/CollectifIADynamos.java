package IA;

import Controleur.Partie;
import Model.Action;
import Model.Attaque;
import Model.Coup;
import Model.Deplacement;
import Model.Joueur;
import Model.Personnage;
import Model.Position;
import Model.Sort;
import Model.Personnage.creatureType;
import Personnages.Guerrier;
import Personnages.Magicien;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * 
 * Notre IA CollectifIADynamos
 */
public class CollectifIADynamos extends AbstractIA {
	private int nbAppelRec =0;


	public CollectifIADynamos(String nom) {
	        super(nom);
	    }
	
	class CoupSelectionne{
		 private Coup CoupSelectionne;
		 private double utilite;
		
		 public CoupSelectionne(){
			 utilite = Integer.MIN_VALUE;
		 }
		 
		public Coup getCoupSelectionne() {
			return CoupSelectionne;
		}
		public void setCoupSelectionne(Coup coupSelectionne) {
			CoupSelectionne = coupSelectionne;
		}
		public double getUtilite() {
			return utilite;
		}
		public void setUtilite(double utilite) {
			this.utilite = utilite;
		}	 
	 } 
	
	 /**
	  * @param p Partie == > le tour actuel que l'ia doit analyser 
	  * @return Le coup joué par l'ia
	  */
	 public Coup getCoup(Partie p) {
		nbAppelRec =0;
	    Partie Part = p.clone(); // Clonage de la partie
	    int nbIA=nbPersonnages(p,true); // Nombre de perso vivant de notre IA
	    int nbADV=nbPersonnages(p,false); // Nombre de perso vivant de notre ADVERSAIRE
	    int profondeur =1; 
	    
	    while(!p.estTerminee()){ //&& profondeur <6){
	    	Coup ProchainCoup = alphaBetaPremier(Part,profondeur,nbIA,nbADV); // AlphaBeta avec profondeur
		    this.memoriseCoup(ProchainCoup); // memorisation
		    profondeur++;
		    System.out.println(ProchainCoup);
		    System.out.println("Profondeur : "+profondeur);
		    System.out.println("Nb appel : "+nbAppelRec);
	    }
		return getCoupMemorise();
	}
	 /****************************************************************************
	  * 				         	ALGORITHME									 *
	  ****************************************************************************/
	 
	 /**
	  * Algorithme AlphaBetaPremier qui renvoie le coup � jouer
	  * @param p partie en cours
	  * @param profondeur profondeur 
	  * @param nbIA nb de perso vivant de l'IA
	  * @param nbADV nb de perso vivant de l'ADV
	  * @return le coup � �ffectuer
	  */
	 private Coup alphaBetaPremier(Partie p,int profondeur,int nbIA,int nbADV){		//Racine de l'arborescence A/B, retourne le meilleur coup	 
		 /**
		 * Classe CoupSelectionn�: permet de m�moris� le coup ET son utilit�e
		 */	
		 
		 
		 nbAppelRec++; //on incremente le compteur d'appel
		 
		 CoupSelectionne CoupSelect = new CoupSelectionne();
		 	boolean joueurIA=true; 
		 	double a = Integer.MIN_VALUE;							//Alpha = -l'infini
		 	double b = Integer.MAX_VALUE;							// Beta = +L'infini	
		    List<Coup> ListedesCoups;
		    //On recupere la liste de coups et on verifie bien que la taille de la liste est non nulle
		    do{ 
				ListedesCoups= p.getTousCoups();
			}while(ListedesCoups.size() ==0);
		  
		    List<Coup> ListedesCoups2 =contientAttaquesurADV(ListedesCoups,p); // Nouvelle liste de coup contenant uniquement les attaques contre l'ADV
		    List<Coup> ListedesCoups3 =filtrageMeilleurCoup(ListedesCoups2,p);   //Contient au max les 5 meilleur coups de listeCoup2 
		    int j=0;											//Iterateur
		    joueurIA=false;  					//determinera si Min ou Max
			double Nouva = a;			
			while((a<b) && (j < ListedesCoups3.size())){ 
				Partie pclone = p.clone();	
				pclone.appliquerCoup(ListedesCoups3.get(j));							//on applique le coup a la partie
				pclone.tourSuivant(); 												//On donne la main au joueur d'après
				pclone.joueurSuivant();												//On passe au joueur suivant 
				Nouva = Math.max(a, this.alphaBeta(pclone,a,b,profondeur-1,joueurIA,nbIA,nbADV));	//on calcul le meilleur coup				 
				if( Nouva != a){													//Si on trouve un Meilleur coup 
					if(CoupSelect.getUtilite() < Nouva){
						CoupSelect.setCoupSelectionne(ListedesCoups3.get(j));	//On sauvegarde le coup
						CoupSelect.setUtilite(Nouva);						//et son utilitée
						}
					a = Nouva; 
				}				 
				j++; // On passe au coup suivant
			}			
			return CoupSelect.getCoupSelectionne();
	 	}
	 
	 

	/**		Fonction qui calcule l'utilitée de ce noeud.
	 * 
	 * @param p			Clone de la partie pour ce noeud
	 * @param a			Alpha
	 * @param b			Beta
	 * @param profond	Profondeur restante a explorée
	 * @param joueurIA	True/False, "Est-ce a l'ia de jouer?"
	 * @param nbIA 		Nombre de personnage de l'equipe de l'Ia
	 * @param nbADV 	Nombre de personnage de l'équipe adverse
	 * @return a ou b, Utilitée max/min du noeud
	 */
	 private double alphaBeta(Partie p,double a,double b,int profondeurMax, boolean joueurIA,int nbIA,int nbADV){
		 nbAppelRec++; 						//on incremente le compteur d'appel
		 
		 List<Coup> ListedesCoups;
		 
		 
		 if(profondeurMax <= 0){			// Si profondeur maximum est atteinte
			  	double util=this.utilite(p,nbIA,nbADV);
			  	return util;	 
		 }else{	 
			do{ 
				ListedesCoups= p.getTousCoups();
			}while(ListedesCoups.size() ==0);
			int j=0;
			List<Coup> ListedesCoups2 =contientAttaquesurADV(ListedesCoups,p); 		// Nouvelle liste de coup contenant uniquement les attaques contre l'ADV
			List<Coup> ListedesCoups3 =filtrageMeilleurCoup(ListedesCoups2,p); 
			if(joueurIA){					//technique pour determiner Min ou Max	
				joueurIA=false;				//prochain tour: le joueur joue				
				while((a<b) && (j < ListedesCoups3.size())){
					 Partie pclone = p.clone();						
					 pclone.appliquerCoup(ListedesCoups3.get(j));					//on applique le coup j a la partie			 
					if(pclone.tourSuivant() && !pclone.estTerminee()){				//On donne la main au joueur d'après
						pclone.joueurSuivant(); 									// On passe au joueur suivant
						a = Math.max(a, alphaBeta(pclone,a,b,profondeurMax-1,joueurIA,nbIA,nbADV));	//on calcul le meilleur coup
					 }else{					//si partie terminee
						 double util=this.utilite(pclone,nbIA,nbADV);
						 return util;
					 }
					 j++;					 //On passe au coup d'après 
				 }
				 return a; 					//On retourne l'utilitée maximale calculée
			}else{							//Joueur enemi
				joueurIA=true;				//prochain tour: l'ia joue	
				while((a<b) && (j < ListedesCoups3.size())){
					 Partie pclone = p.clone();
					 pclone.appliquerCoup(ListedesCoups3.get(j));					//on applique le coup a la partie
					 if(pclone.tourSuivant() && !pclone.estTerminee()){				//On donne la main au joueur d'après
						 pclone.joueurSuivant(); 									// On passe au joueur suivant
						 b = Math.min(b, alphaBeta(pclone,a,b,profondeurMax-1,joueurIA,nbIA,nbADV));	//on calcul le meilleur coup
					 }else{					//si partie terminee
						 double util=this.utilite(pclone,nbIA,nbADV);
						 return util;
					 }
					 j++;					//On passe au coup d'après	 
				}	
				return b; 					//On retourne l'utilitée minimale calculée	 
			}
		 } 
	 }
	  
	 
	 
	 
	 /****************************************************************************
	  * 				         	FONCTIONS									 *
	  ****************************************************************************/

	/**
	 * Calcul l'utilit� d'un coup
	 * @param p partie en cours
	 * @param oldnbIA ancien nb de perso vivant de notre IA
	 * @param oldnbADV ancien nb de perso vivant de notre ADV*
	 * 
	 * @return Double utilite du noeud
	 */
	private double utilite(Partie p,int oldnbIA,int oldnbADV){
 			double uti;
 			float pvMonEquipe = 0;
 			float pvEquipeEnemie = 0;
 			List<Personnage> monEquipe;
 			List<Personnage> equipeEnemie;
 			int newnbIA;
 			int newnbADV;
 			
 			if(p.getJoueurActuel().equals(this)){	 // Si le joueur actuelle est notre IA
 				monEquipe = p.listerEquipeJoueur();
 	 			equipeEnemie = p.listerEquipesAdverses();
 	 			newnbIA =nbPersonnages(p,true);
 	 			newnbADV =nbPersonnages(p,false);
 			}
 			else{ 									// Si le joueur actuelle est l'ADV 
 				equipeEnemie = p.listerEquipeJoueur();
 				monEquipe = p.listerEquipesAdverses();
 				newnbIA =nbPersonnages(p,false);
 	 			newnbADV =nbPersonnages(p,true);
 			}
 			
 										///////Calcul des pdv Utilit� 1
 			pvMonEquipe = this.calculPdvEquipe(monEquipe) + 1/10000;
 			pvEquipeEnemie = this.calculPdvEquipe(equipeEnemie) + 1/10001; //on ajoute 1/10000 pour ne pas faire une division par 0
 			uti =  pvMonEquipe / pvEquipeEnemie;
 			uti = Math.log(uti)*100;
 			 
 										///////BONUS SI KO   Utilit� 2
 			
 			uti -= 50* (oldnbIA-newnbIA); 		// -50 pt pour chaqu'un de nos persos KO
 			uti += 100* (oldnbADV-newnbADV);	 //+100 pt pour chaque persos KO de l'adversaire
 			
 										//////Contraintes sur le ralenti Utilit� 3//
 			for(Personnage perso:equipeEnemie){
 				if(perso.isRalenti()){
 					uti -=5;
 				}
 			}
 			for(Personnage perso:equipeEnemie){
 				if(perso.isRalenti()){
 					uti +=5;
 				}
 			}
 		return  uti;
 	}

	

	/**
	  * 	Calcul les pdv totale d'une �quipe
	  * @param monEquipe
	  * 
	  * @return Int pdv de l'�quipe
	  */ 	
	private int calculPdvEquipe(List<Personnage> monEquipe) {
		int i;
		int res=0;
		for(i=0; i< monEquipe.size(); i++){
			res = res + monEquipe.get(i).getVie();
		}
		return res;
	}
	
	
	/**Clone
	 * Renvoi la liste des coups qui contient les attaques visant uniquement l'ADV 
	 * @param Listcoups ancienne liste des coups possibles
	 * @param p partie en cours
	 * 
	 * @return List<Coup> Liste des coup sans les attaques sur sois-meme
	 */
	private List<Coup> contientAttaquesurADV(List<Coup> Listcoups,Partie p) {
		Joueur jactuel = p.getJoueurActuel();
		List<Coup> Listcoups2=new ArrayList<Coup>();
		for(Coup c : Listcoups ){
			for (Action a : c.getActions()) {
	            if (a instanceof Attaque) {
	                if (!((Attaque) a).getCible().getProprio().equals(jactuel)) {
	                	Listcoups2.add(c);
	                }
	            }
	            else{
	            	Listcoups2.add(c);
	            }
	        }
        }
		return Listcoups2;
        
    }
	
	
	/**
	 * Renvoi le nombre de perso vivant du joueurIA
	 * @param p partie en cours
	 * @param joueurIA true => notre IA sinon false => ADV
	 * 
	 * @return Int nb perso vivant
	 */
	private int nbPersonnages(Partie p,boolean joueurIA) {
		int nb=0;
		List<Personnage> equipe;
		if(joueurIA){
			equipe = p.listerEquipeJoueur();
		}
		else{
			equipe = p.listerEquipesAdverses();
		}
		for(Personnage j: equipe){
			if(j.estVivant())nb++;
		}
        return nb;
    }
	 	
	/**
	 * Retourne les 5 meilleurs coup jugée de la listeCoup
	 * @param listedesCoups2	ListeCoup sans attaques sursois-meme
	 * @param p					Partie actuelle
	 * 
	 * @return	List<Coup>		Liste des meileurs coup de taile 5 (d'apres l'utilitée)
	 */
	private List<Coup> filtrageMeilleurCoup(List<Coup> listedesCoups2, Partie p) {
		
	     TreeMap<Double, Coup> listCoupFiltreTrie = new TreeMap<Double,Coup>();
	     ArrayList<Coup> listCoupRetour = new ArrayList<Coup>();
		
		Coup coupActuel;
		int ite = 0;							//iterateur
		int taille = listedesCoups2.size();		//nb de coup disponnible avant
		
		while(ite < taille){					
			coupActuel = listedesCoups2.get(ite);
			double utilite = calculHeuristique(p,coupActuel);
			if(listCoupFiltreTrie.containsKey(utilite)){
				listCoupFiltreTrie.put(utilite+ite*0.00001, coupActuel);		//on ajoute dans la liste le coup suivant son utilitee+0.0010 (clef deja existante)
			}else{
				listCoupFiltreTrie.put(utilite, coupActuel);				//on ajoute dans la liste le coup suivant son utilitee
			}
			ite++;
		}
		
		for(int i = 0; i<10 && i< listCoupFiltreTrie.size();i++){
			Double plusGrandeClefActuel = listCoupFiltreTrie.lastKey();				//meilleur coup, (plus grande utilitee, donc lastKey
			listCoupRetour.add(listCoupFiltreTrie.remove(plusGrandeClefActuel));	//On stock ce coup, le retire du TreeMap
		}
		
		return listCoupRetour;
	}
	 
	
	/**
	 * 	Fonction de calcul d'heuristique: Prediction des meilleures branches
	 * @param p		Etat actuel de la partie
	 * @param coup	Coup que l'on eux evaluer
	 * 
	 * @return	Double	Valeur de l'utilite estimee
	 */
	private double calculHeuristique(Partie p,Coup coup){
		// Calcul des Degats - des effets - du d�placement possible - s'il est attaquable
		double h=0;
		float pvMonEquipe = 0;
		float pvEquipeEnemie = 0;
		List<Personnage> monEquipe;
		List<Personnage> equipeEnemie;
		boolean joueurIA;
		if(p.getJoueurActuel().equals(this)){ // Si le joueur actuelle est notre IA
				joueurIA = true;
				monEquipe = p.listerEquipeJoueur();
	 			equipeEnemie = p.listerEquipesAdverses();
			}
			else{ // Si le joueur actuelle est l'ADV 
				joueurIA = false;
				equipeEnemie = p.listerEquipeJoueur();
				monEquipe = p.listerEquipesAdverses();
			}
		
		Action action = coup.getActions().get(0);
		if(action instanceof Attaque){		//Phase d'attaque
			List<Personnage> cibles =((Attaque) action).getPersonnagesAttaques();
			int nbcible = cibles.size();
			Sort sort = ((Attaque) action).getSort();
			int degat = sort.getDegat();
			creatureType type =sort.getTypeCible();
			boolean hasEffect = sort.hasEffet();
			boolean multipleAttack = sort.isAttaqueMultiple();
			pvMonEquipe = this.calculPdvEquipe(monEquipe) + 1/10000;
 			pvEquipeEnemie = this.calculPdvEquipe(equipeEnemie) + 1/10001; //on ajoute 1/10000 pour ne pas faire une division par 0
			if(joueurIA){
				h =  pvMonEquipe / (pvEquipeEnemie-degat*nbcible);
	 			h = Math.log(h)*100;
	 			h +=(hasEffect?25:0)*nbcible;
	 			h +=(multipleAttack?20:0);
	 			for(Personnage perso:cibles){
	 				if(perso.getVie()<=degat && !perso.isDejaJoue()){	//Bonus si le perso meurt et qu'il n'a pas encore jouer
	 					h+=200;
	 				}
	 				else if(perso.getVie()<=degat){		//Bonus moindre si le perso meurt
	 					h+=100;
	 				}
	 				
	 				if(perso.getType()==type){		//Bonus si l'attaque est effective (attaque air sur perso air)
	 					h+=50;
	 				}
	 				else{							//Sinon nope (cad si l'attaque n'acheve pas, et surtout si l'attaque est inefective..
	 					h-=100;
	 				}
	 				if(perso instanceof Magicien){
	 					h+=200;
	 				}
	 				if(perso instanceof Guerrier){
	 					h+=100;
	 				}
	 			}
			}
			else{
				h =  (pvMonEquipe-degat*nbcible) / pvEquipeEnemie;
	 			h = Math.log(h)*100;
	 			h -=(hasEffect?25:0)*nbcible;
	 			for(Personnage perso:cibles){
	 				if(perso.getVie()<=degat && !perso.isDejaJoue()){ //si l'un de nos perso qui n'a pas joué meurt
	 					h-=200;
	 				}
	 				else if(perso.getVie()<=degat){	//si l'un de nos perso meurt
	 					h-=100;
	 				}
	 				if(perso.getType()==type){		//Si une attaque sur un de nos perso est effective
	 					h-=50;
	 				}
	 				else{							//sinon, l'adversaire se plante en nous attaquant!
	 					h+=100;
	 				}
	 				if(perso instanceof Magicien){
	 					h-=300;
	 				}
	 				if(perso instanceof Guerrier){
	 					h-=100;
	 				}
	 			}
			}
			//h+=(degat*10+(hasEffect?10:0))*nbcible;
		}
		else{				//Phase de deplacement
			Position deplacementOrigine = ((Deplacement) action).getOrigine();
			Position deplacementDestination = ((Deplacement) action).getDestination();
			int distance = calculDistance(deplacementOrigine,deplacementDestination);
			h=distance*10;
			for(Personnage perso:monEquipe){
				if(!perso.equals(this)){
					if(calculDistance(deplacementDestination,perso.getPosition())<2){
						h-=10;
					}
				}
			}
			
			for(Personnage perso:equipeEnemie){
				int oldDistance =calculDistance(deplacementOrigine,perso.getPosition());
				int newDistance =calculDistance(deplacementDestination,perso.getPosition());
				/*if(calculDistance(deplacementDestination,perso.getPosition())>3){
					h+=15;
				}
				else{
					h-=30;
				}
				if(oldDistance>newDistance && !(perso instanceof Guerrier)){
					h+=10*(newDistance+1);
				}
				else if(oldDistance>newDistance && (perso instanceof Guerrier)){
					h-=30*(newDistance+1);
				}*/
				if(oldDistance>newDistance && !(perso instanceof Guerrier)){
					h+=100/(newDistance+1);
				}
				else if(oldDistance>newDistance && (perso instanceof Guerrier)){
					h-=150/(newDistance+1);
				}
				if(coup.getAuteur() instanceof Guerrier){
					if(oldDistance>newDistance){
						h+=newDistance*10;//h+=(oldDistance-newDistance)*5;
					}
					
				}
			}
		}
		return h;
		
	}
	
	
	/**
	 * 	Evalue la distance entre 2 positions
	 * @param p1	Position 1
	 * @param p2	Position 2
	 * 
	 * @return	Int La "distance 2" entre les 2 positions
	 */
	public int calculDistance(Position p1,Position p2){
		int x1 = p1.getX();
		int y1 = p1.getY();
		int x2 = p2.getX();
		int y2 = p2.getY();
		int distance =  Math.abs((x1-x2) +(y1-y2));
		return distance;
		
	}
	
}
