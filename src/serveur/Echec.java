package serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * interface de jeu de plateau type échecs multijoueurs
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */

public interface Echec extends Remote {
	/**
	 * Methode qui permet la création de l'équipe en fonction du camp choisi
	 * @param camp
	 * @return confirmation textuelle que l'équipe a été créée
	 * @throws RemoteException
	 */	
	String creationEquipe(int camp) throws RemoteException;
	
	/**
	 * Methode qui permet au joueur de déplacer un pion
	 * @throws RemoteException
	 */	
	void jouerTour() throws RemoteException;
	
	/**
	 * Methode qui permet au joueur de sauvegarder une partie en cours sur le serveur
	 * @throws RemoteException
	 */
	void sauvegarderPartie() throws RemoteException;
	
	/**
	 * Methode qui permet au joueur de récupérer une partie stockée sur le serveur et de continuer à jouer
	 * @throws RemoteException
	 */
	void restaurerPartie() throws RemoteException;	

}
