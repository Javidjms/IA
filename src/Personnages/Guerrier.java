package Personnages;

import Model.Sort;
import Model.Matrice;
import Model.Personnage;

/**
 * 
 * Cette classe represente le guerrier.
 * Il dispose de 12 points de vie et peut se deplacer de 2 cases au sol.
 * @author Warlot/Gasquez
 *
 */
public class Guerrier extends Personnage {
	public Guerrier() {
		super(2,12, new Matrice(new boolean[][]{{false, false, true, false, false}, {false, false, true, false, false}, {true, true, false, true, true}, {false, false, true, false, false}, {false, false, true, false, false}}), "Guerrier", creatureType.TERRESTRE, "/images/large_Warrior.png", "/images/small_Warrior.png");
		
		ajouterAttaque(new Sort("Coup de taille", 4, 0, 0, new Matrice(new boolean[][]{{false, true, false}, {true, false, true}, {false, true, false}}), creatureType.TERRESTRE, false));
		ajouterAttaque(new Sort("Baliste de feu", 6, 4, 4, new Matrice(new boolean[][]{{true}}), creatureType.TOUT, false));
//		ajouterAttaque(new Sort("Coup de jarnac", 1, 0, 4, new Matrice(new boolean[][]{{true}}), creatureType.TERRESTRE, true));
		ajouterAttaque(new Sort("Coup de jarnac", 2, 0, 4, new Matrice(new boolean[][]{{true}}), creatureType.TERRESTRE, false));
	}
        
        @Override
        public Object clone() {
                Personnage clone = new Guerrier();
                clone.copier(this);
                return clone;
        }
}
