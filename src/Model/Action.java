/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controleur.Partie;

/**
 *
 * @author Gwenole Lecorve
 */
public interface Action {
    /**
     * Applique l'action sur une partie en cours
     * @param partie Partie a modifier
     */
    public void appliquer(Partie partie);
}
