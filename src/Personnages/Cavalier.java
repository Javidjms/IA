package Personnages;

import Model.Sort;
import Model.Matrice;
import Model.Personnage;
import Model.Effet;

/**
 * 
 * Cette classe represente le cavalier celeste.
 * Il dispose de 10 points de vie et peut se deplacer de 2 cases en volant y compris en diagonale.
 * @author Warlot/Gasquez
 *
 */

public class Cavalier extends Personnage {
	public Cavalier(){
		super(2,10, new Matrice(new boolean[][]{{true, true, true, true, true}, {true, true, true, true, true}, {true, true, false, true, true}, {true, true, true, true, true}, {true, true, true, true, true}}), "Cavalier", creatureType.VOLANTE, "/images/large_SkyHorseman.png", "/images/small_SkyHorseman.png");

//		ajouterAttaque(new Sort("Coup de jarnac", 1, 0, 4, new Matrice(new boolean[][]{{true}}), creatureType.TERRESTRE, true));
		ajouterAttaque(new Sort("Coup de jarnac", 2, 0, 4, new Matrice(new boolean[][]{{true}}), creatureType.TERRESTRE, false));
		ajouterAttaque(new Sort("Enchevetrement de ronces", 0, 0, 4, new Matrice(new boolean[][]{{true}}), new Effet(2, 1, 0), creatureType.TOUT, false));
		ajouterAttaque(new Sort("Nuage malefique", 3, 0, 2, new Matrice(new boolean[][]{{true}}), creatureType.TOUT, false));
	}
        
        @Override
        public Object clone() {
                Personnage clone = new Cavalier();
                clone.copier(this);
                return clone;
        }
}
