package IA;

import Controleur.Partie;
import Model.Action;
import Model.Attaque;
import Model.Coup;
import Model.Deplacement;
import Model.Joueur;
import Model.Personnage;
import Model.Personnage.creatureType;
import Model.Position;
import Model.Sort;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * Notre IA CollectifIADynamos
 */
public class CollectifIADynamos2 extends AbstractIA {
	 public CollectifIADynamos2(String nom) {
	        super(nom);
	    }
	 
	 /**
	  * @param p Partie == > le tour actuel que l'ia doit analyser 
	  * @return Le coup jouÃ© par l'ia
	  */
	 public Coup getCoup(Partie p) {
	    Partie Part = p.clone(); // Clonage de la partie
	    int nbIA=nbPersonnages(p,true); // Nombre de perso vivant de notre IA
	    int nbADV=nbPersonnages(p,false); // Nombre de perso vivant de notre ADVERSAIRE
	    int profondeur =0; 
	    if(nbIA+nbADV<=2){ // Si le nombre de perso total vivant est inferieur à 2 alors : 
	    	profondeur=2; // on augmente la profondeur à 2
	    }
	    else{
	    	profondeur=1; // Sinon elle reste à 1
	    }
	    
	    Coup ProchainCoup = alphaBetaPremier(Part,profondeur,nbIA,nbADV); // AlphaBeta avec profondeur (Init à 1)
	    this.memoriseCoup(ProchainCoup); // 1ère memorisation
	    ProchainCoup = alphaBetaPremier(Part,profondeur+1,nbIA,nbADV); // AlphaBeta avec profondeur (Init à 2)
	    this.memoriseCoup(ProchainCoup); // 2ème memorisation
	    if(ProchainCoup!=null){ //Ce cas arrivera rarement mais évite les Null Pointeurs
	    	System.out.println("Le coup choisi par notre IA  est: "+ ProchainCoup.toString());
	    }
		return ProchainCoup;
	    }
	 
	 
	 /**
	  * Algorithme AlphaBetaPremier qui renvoie le coup à jouer
	  * @param p partie en cours
	  * @param profondeur profondeur 
	  * @param nbIA nb de perso vivant de l'IA
	  * @param nbADV nb de perso vivant de l'ADV
	  * @return le coup à éffectuer
	  */
	 private Coup alphaBetaPremier(Partie p,int profondeur,int nbIA,int nbADV){		//Racine de l'arborescence A/B, retourne le meilleur coup	 
		 /**
		 * Classe CoupSelectionné: permet de mémorisé le coup ET son utilitée
		 */	
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
		   int j=0;											//Iterateur
		    joueurIA=false;  					//determinera si Min ou Max
			double Nouva = a;			
			//System.out.println(ListedesCoups2.size());
			//System.out.println(ListedesCoups2);
			while((a<b) && (j < ListedesCoups2.size())){ 
				Partie pclone = p.clone();	
				pclone.appliquerCoup(ListedesCoups2.get(j));							//on applique le coup a la partie
				pclone.tourSuivant(); 												//On donne la main au joueur d'aprÃ¨s
				pclone.joueurSuivant();												//On passe au joueur suivant 
				Nouva = Math.max(a, this.alphaBeta(pclone,a,b,profondeur-1,joueurIA,nbIA,nbADV));	//on calcul le meilleur coup				 
				if( Nouva != a){													//Si on trouve un Meilleur coup 
					if(CoupSelect.getUtilite() < Nouva){
						CoupSelect.setCoupSelectionne(ListedesCoups2.get(j));	//On sauvegarde le coup
						CoupSelect.setUtilite(Nouva);						//et son utilitÃ©e
						}
					a = Nouva; 
				}
				pclone=null;
				j++; // On passe au coup suivant
			}	
			return CoupSelect.getCoupSelectionne();
	 	}
	 
	 
	 
	 
	/**		Fonction qui calcule l'utilitÃ©e de ce noeud.
	 * 
	 * @param p			Clone de la partie pour ce noeud
	 * @param a			Alpha
	 * @param b			Beta
	 * @param profond	Profondeur restante a explorÃ©e
	 * @param joueurIA	True/False, "Est-ce a l'ia de jouer?"
	 * @return a ou b, UtilitÃ©e max/min du noeud
	 */
	 private double alphaBeta(Partie p,double a,double b,int profondeurMax, boolean joueurIA,int nbIA,int nbADV){
		 List<Coup> ListedesCoups;
		 if(profondeurMax <= 0){			// Si profondeur maximum est atteinte
			  	double util=this.utilite(p,nbIA,nbADV);
			  	return util;	 
		 }else{	 
			do{ 
				ListedesCoups= p.getTousCoups();
			}while(ListedesCoups.size() ==0);
			int j=0;
			List<Coup> ListedesCoups2 =contientAttaquesurADV(ListedesCoups,p); // Nouvelle liste de coup contenant uniquement les attaques contre l'ADV
			if(joueurIA){		//technique pour determiner Min ou Max	
				joueurIA=false;			//prochain tour: le joueur joue				
				while((a<b) && (j < ListedesCoups2.size())){
					Partie pclone = p.clone();						
					 pclone.appliquerCoup(ListedesCoups2.get(j));	//on applique le coup j a la partie			 
					if(pclone.tourSuivant() && !pclone.estTerminee()){		//On donne la main au joueur d'aprÃ¨s
						pclone.joueurSuivant(); // On passe au joueur suivant
						a = Math.max(a, alphaBeta(pclone,a,b,profondeurMax-1,joueurIA,nbIA,nbADV));	//on calcul le meilleur coup
					 }else{		//si partie terminee
						 double util=this.utilite(pclone,nbIA,nbADV);
						 return util;
					 }
					 j++; //On passe au coup d'aprÃ¨s
				 }
				
				 return a; //On retourne l'utilitÃ©e maximale calculÃ©e
			}else{				//Joueur enemi
				joueurIA=true;			//prochain tour: l'ia joue	
				while((a<b) && (j < ListedesCoups2.size())){
					Partie pclone = p.clone();
					pclone.appliquerCoup(ListedesCoups2.get(j));	//on applique le coup a la partie
					 if(pclone.tourSuivant() && !pclone.estTerminee()){						//On donne la main au joueur d'aprÃ¨s
						 pclone.joueurSuivant(); // On passe au joueur suivant
						 b = Math.min(b, alphaBeta(pclone,a,b,profondeurMax-1,joueurIA,nbIA,nbADV));	//on calcul le meilleur coup
					 }else{		//si partie terminee
						 double util=this.utilite(pclone,nbIA,nbADV);
						 return util;
					 }
					 j++;	//On passe au coup d'aprÃ¨s	
				}
				return b; //On retourne l'utilitÃ©e minimale calculÃ©e	 
			}
		 } 
	 }
	  
	 

	/**
	 * Calcul l'utilité d'un coup
	 * @param p partie en cours
	 * @param oldnbIA ancien nb de perso vivant de notre IA
	 * @param oldnbADV ancien nb de perso vivant de notre ADV
	 * @return
	 */
	private double utilite(Partie p,int oldnbIA,int oldnbADV){
 			double uti;
 			float pvMonEquipe = 0;
 			float pvEquipeEnemie = 0;
 			List<Personnage> monEquipe;
 			List<Personnage> equipeEnemie;
 			int newnbIA;
 			int newnbADV;
 			
 			if(p.getJoueurActuel().equals(this)){ // Si le joueur actuelle est notre IA
 				monEquipe = p.listerEquipeJoueur();
 	 			equipeEnemie = p.listerEquipesAdverses();
 	 			newnbIA =nbPersonnages(p,true);
 	 			newnbADV =nbPersonnages(p,false);
 			}
 			else{ // Si le joueur actuelle est l'ADV 
 				equipeEnemie = p.listerEquipeJoueur();
 				monEquipe = p.listerEquipesAdverses();
 				newnbIA =nbPersonnages(p,false);
 	 			newnbADV =nbPersonnages(p,true);
 			}
 			
 			//Calcul des pdv U1
 			pvMonEquipe = this.calculPdvEquipe(monEquipe) + 1/10000;
 			pvEquipeEnemie = this.calculPdvEquipe(equipeEnemie) + 1/10001; //on ajoute 1/10000 pour ne pas faire une division par 0
 			uti =  pvMonEquipe / pvEquipeEnemie;
 			uti = Math.log(uti)*100;
 			 
 			//BONUS SI KO   U2
 			
 			uti -= 50* (oldnbIA-newnbIA); // -50 pt pour chaqu'un de nos persos KO
 			uti += 100* (oldnbADV-newnbADV); //+100 pt pour chaque persos KO de l'adversaire
 			
 			//Contraintes sur le ralenti U3
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
	  * 	Calcul les pdv totale d'une équipe
	  * @param monEquipe
	  * @return Int pdv de l'équipe
	  */ 	
	private int calculPdvEquipe(List<Personnage> monEquipe) {
		int i;
		int res=0;
		for(i=0; i< monEquipe.size(); i++){
			res = res + monEquipe.get(i).getVie();
		}
		return res;
	}
	
	
	/**
	 * HEURISTIQUE 1
	 * Renvoi la liste des coups qui contient les attaques visant uniquement l'ADV 
	 * @param Listcoups ancienne liste des coups possibles
	 * @param p partie en cours
	 * @return nouvelle liste des coups
	 */
	private List<Coup> contientAttaquesurADV(List<Coup> Listcoups,Partie p) {
		Joueur jactuel = p.getJoueurActuel();
		LinkedList<Coup> Listcoups2=new LinkedList<Coup>();
		for(Coup c : Listcoups ){
			for (Action a : c.getActions()) {
	            if (a instanceof Attaque) {
	                if (!((Attaque) a).getCible().getProprio().equals(jactuel)) {
	                	Listcoups2.addFirst(c);
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
	 * @return nb perso vivant
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
	 	
	private double calculHeuristique(Partie p,Coup coup){
		// Calcul des Degats - des effets - du déplacement possible - s'il est attaquable
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
		if(action instanceof Attaque){
			List<Personnage> cibles =((Attaque) action).getPersonnagesAttaques();
			int nbcible = cibles.size();
			Sort sort = ((Attaque) action).getSort();
			int degat = sort.getDegat();
			creatureType type =sort.getTypeCible();
			boolean hasEffect = sort.hasEffet();
			pvMonEquipe = this.calculPdvEquipe(monEquipe) + 1/10000;
 			pvEquipeEnemie = this.calculPdvEquipe(equipeEnemie) + 1/10001; //on ajoute 1/10000 pour ne pas faire une division par 0
			if(joueurIA){
				h =  pvMonEquipe / (pvEquipeEnemie-degat*nbcible);
	 			h = Math.log(h)*100;
	 			h +=(hasEffect?10:0)*nbcible;
	 			for(Personnage perso:cibles){
	 				if(perso.getVie()<=degat && !perso.isDejaJoue()){
	 					h+=200;
	 				}
	 				else if(perso.getVie()<=degat){
	 					h+=100;
	 				}
	 				
	 				if(perso.getType()==type){
	 					h+=50;
	 				}
	 				else{
	 					h-=200;
	 				}
	 			}
			}
			else{
				h =  (pvMonEquipe-degat*nbcible) / pvEquipeEnemie;
	 			h = Math.log(h)*100;
	 			h -=(hasEffect?10:0)*nbcible;
	 			for(Personnage perso:cibles){
	 				if(perso.getVie()<=degat && !perso.isDejaJoue()){
	 					h-=200;
	 				}
	 				else if(perso.getVie()<=degat){
	 					h-=100;
	 				}
	 				if(perso.getType()==type){
	 					h-=50;
	 				}
	 				else{
	 					h+=200;
	 				}
	 			}
			}
			//h+=(degat*10+(hasEffect?10:0))*nbcible;
		}
		else{
			Position deplacementOrigine = ((Deplacement) action).getOrigine();
			Position deplacementDestination = ((Deplacement) action).getDestination();
			pvMonEquipe = this.calculPdvEquipe(monEquipe) + 1/10000;
 			pvEquipeEnemie = this.calculPdvEquipe(equipeEnemie) + 1/10001; //on ajoute 1/10000 pour ne pas faire une division par 0
			h =  pvMonEquipe / pvEquipeEnemie;
 			h = Math.log(h)*100;
		}
		return h;
		
	}
	
}
