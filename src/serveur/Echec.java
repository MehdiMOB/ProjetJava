package serveur;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * interface de jeu de plateau type �checs multijoueurs
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */

public interface Echec extends Remote {
	/**
	 * Methode qui permet la cr�ation de l'�quipe en fonction du camp choisi
	 * @param camp
	 * @return confirmation textuelle que l'�quipe a �t� cr��e
	 * @throws RemoteException
	 */	
	String creationEquipe(int camp) throws RemoteException;
	
	/**
	 * Methode qui permet au joueur de d�placer un pion
	 * @throws RemoteException
	 */	
	void jouerTour() throws RemoteException;
	
	/**
	 * Methode qui permet au joueur de sauvegarder une partie en cours sur le serveur
	 * @throws RemoteException
	 */
	void sauvegarderPartie() throws RemoteException;
	
	/**
	 * Methode qui permet au joueur de r�cup�rer une partie stock�e sur le serveur et de continuer � jouer
	 * @throws RemoteException
	 */
	void restaurerPartie() throws RemoteException;	

}
