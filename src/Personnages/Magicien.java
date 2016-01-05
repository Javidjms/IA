package Personnages;

import Model.Sort;
import Model.Effet;
import Model.Matrice;
import Model.Personnage;

/**
 * 
 * Cette classe represente le magicien.
 * Il dispose de 8 points de vie et peut se deplacer de 2 cases au sol. 
 * @author Warlot/Gasquez
 *
 */
public class Magicien extends Personnage {
	public Magicien(){
		super(2,8, new Matrice(new boolean[][]{{false, false, true, false, false}, {false, false, true, false, false}, {true, true, false, true, true}, {false, false, true, false, false}, {false, false, true, false, false}}), "Magicien", creatureType.TERRESTRE, "/images/large_Wizard.png", "/images/small_Wizard.png");

		ajouterAttaque(new Sort("Tempete", 4, 0, -1, new Matrice(new boolean[][]{{true, true, true}, {true, true, true}, {true, true, true}}), creatureType.VOLANTE, false));
		ajouterAttaque(new Sort("Tremblement de terre", 3, 0, -1, new Matrice(new boolean[][]{{true, true, true}, {true, true, true}, {true, true, true}}), creatureType.TERRESTRE, false));
		ajouterAttaque(new Sort("Boule de feu", 4, 0, 4, new Matrice(new boolean[][]{{true}}), creatureType.TOUT, false));
		ajouterAttaque(new Sort("Enchevetrement de ronces", 0, 0, 4, new Matrice(new boolean[][]{{true}}), new Effet(2,1,0), creatureType.TOUT, false));
		ajouterAttaque(new Sort("Terre marecageuse", 0, 0, -1, new Matrice(new boolean[][]{{true, true, true}, {true, true, true}, {true, true, true}}), new Effet(2,1,0), creatureType.TOUT, false));
	}
        
        @Override
        public Object clone() {
                Personnage clone = new Magicien();
                clone.copier(this);
                return clone;
        }
}
