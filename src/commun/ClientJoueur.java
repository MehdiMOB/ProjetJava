package commun;

import java.rmi.Remote;
import java.rmi.RemoteException;

import jeu.Equipe;
import protagonistes.Piece;

/**
 * interface de jeu de plateau type �checs multijoueurs
 * Fonctionalit�s cot� utilisateur permettant la r�ception de messages de la part du serveur
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */

public interface ClientJoueur extends Remote {
	
	/**
	 * R�ception de la composition et de la position initiale de l'�quipe adverse
	 * 
	 * @throws RemoteException
	 */
	public void setEquipeAdverse(Equipe equipe_adverse) throws RemoteException;
	
	/**
	 * Mise � jour de la pr�sence du joueur adverse par le serveur
	 * 
	 * @return bool�en repr�sentant la pr�sence d'un adversaire
	 * @throws RemoteException
	 */
	public void setAdversairePresent(boolean adversairePresent) throws RemoteException;
		
	/**
	 * Permet au serveur de communiquer la pi�ce � d�placer et la destination
	 * 
	 * @throws RemoteException
	 */	
	public void setDeplacement(Piece piece, String deplacement) throws RemoteException;	
	
	/**
	 * Mise � jour du d�tail de la bataille par le serveur
	 * 
	 * @param resultatBataille
	 * @throws RemoteException
	 */
	public void setResultatBataille(String resultatBataille) throws RemoteException;
	
}
