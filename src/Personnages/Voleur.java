package Personnages;

import Model.Sort;
import Model.Matrice;
import Model.Personnage;
import Model.Effet;

/**
 * 
 * Cette classe represente le voleur.
 * Il dispose de 8 points de vie et peut se deplacer de 2 cases en diagonale ou de 3 cases (sans diagonale) au sol.
 * @author Warlot/Gasquez
 *
 */
public class Voleur extends Personnage {
	public Voleur(){
		super(2,8, new Matrice(new boolean[][]{{false, false, false, true, false, false, false}, {false, true, false, true, false, true, false}, {false, false, true, true, true, false, false}, {true, true, true, false, true, true, true}, {false, false, true, true, true, false, false}, {false, true, false, true, false, true, false}, {false, false, false, true, false, false, false}}), "Voleur", creatureType.TERRESTRE, "/images/large_Ninja.png", "/images/small_Ninja.png");
		
		ajouterAttaque(new Sort("Instinct d'esquive", 0, 0, 0, new Matrice(new boolean[][]{{true}}), new Effet(2,0,3), creatureType.TERRESTRE, false));
//		ajouterAttaque(new Sort("Coup de jarnac", 1, 0, 4, new Matrice(new boolean[][]{{true}}), creatureType.TERRESTRE, true));
		ajouterAttaque(new Sort("Coup de jarnac", 2, 0, 4, new Matrice(new boolean[][]{{true}}), creatureType.TERRESTRE, false));
		ajouterAttaque(new Sort("Fiole de poison", 2, 0, 2, new Matrice(new boolean[][]{{true}}), creatureType.TOUT, false));
	}
        
        @Override
        public Object clone() {
                Personnage clone = new Voleur();
                clone.copier(this);
                return clone;
        }
}
