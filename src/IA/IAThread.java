package IA;

import Controleur.Partie;
import Model.Coup;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe qui execute la recherche d'un coup par une intelligence artificielle
 *
 * @author Gwenole Lecorve
 */
public class IAThread extends Thread {

    /**
     * Joueur artificiel
     */
    private final AbstractIA ia;

    /**
     * Partie en cours
     */
    private final Partie partie;
    
    /**
     * Service d'execution du thread
     */
    private final ExecutorService executor;

    /**
     * Coup choisi a l'issu de la recherche
     */
    private Coup coupChoisi;

    /**
     * Constructeur
     * @param ia Joueur artificiel
     * @param partie Partie en cours
     * @param executor Service d'execution du thread
     */
    public IAThread(AbstractIA ia, Partie partie, ExecutorService executor) {
        this.ia = ia;
        this.partie = partie;
        this.executor = executor;
        this.coupChoisi = null;
    }
    
    public Coup getCoupChoisi() {
        return coupChoisi;
    }

    /**
     * Lance la recherche d'un nouveau coup dans un thread separe
     */
    @Override
    public void run() {
        try {
            coupChoisi = ia.getCoup(partie);
            System.out.println(Thread.currentThread().getName()+": "+"Coup memorise");
        }
        catch (Exception ex) {
            Logger.getLogger(Partie.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(Thread.currentThread().getName()+": "+"Interrompu");
        }
        finally {
            executor.shutdown();
//            System.out.println(Thread.currentThread().getName()+": "+"Fin de l'executor");
        }
    }

}
