/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui modelise un coup, c'est-a-dire une ou plusieurs actions faites par un Personnage lors de son tour
 * @author Gwenole Lecorve
 */
public class Coup {
    private Personnage auteur;
    private List<Action> actions;
    
    /**
     * Constructeur par defaut
     */
    public Coup() {
        this.auteur = null;
        this.actions = null;
    }
    
    /**
     * Constructeur avec valeurs
     * @param auteur L'auteur du coup
     * @param action L'action effectuee
     */
    public Coup(Personnage auteur, Action action) {
        this.auteur = auteur;
        this.actions = new ArrayList();
        actions.add(action);
    }
    
    /**
     * Constructeur avec valeurs
     * @param auteur L'auteur du coup
     * @param actions L'action effectuee
     */
    public Coup(Personnage auteur, List<Action> actions) {
        this.auteur = auteur;
        this.actions = actions;
    }

    /**
     * @return the auteur
     */
    public Personnage getAuteur() {
        return auteur;
    }

    /**
     * @param auteur the auteur to set
     */
    public void setAuteur(Personnage auteur) {
        this.auteur = auteur;
    }

    /**
     * @return the action
     */
    public List<Action> getActions() {
        return actions;
    }

    /**
     * @param action the action to set
     */
    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
    
    /**
     * Ajoute une action au coup courant
     * @param action 
     */
    public void addAction(Action action) {
        this.actions.add(action);
    }
    
    @Override
    public String toString() {
        String str = "Coup ";
        str += "par " + getAuteur().toString() + "\n";
        for (int i = 0; i < actions.size(); i++) {
            Action a = actions.get(i);
            str += "\t- " + a.toString();
            if (i <= actions.size()) {
                str += "\n";
            }
        }
        return str;
    }
    
}
