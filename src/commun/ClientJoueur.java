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
	 * R�ception de messages
	 * 
	 * @param message � afficher
	 * @throws RemoteException
	 */
	public void notification(String message) throws RemoteException;
	
	/**
	 * R�ception de la composition et de la position initiale de l'�quipe adverse
	 * 
	 * @param 0 si tout s'est bien pass�, 1 sinon
	 * @throws RemoteException
	 */
	public int equipeAdverse(Equipe equipe_adverse) throws RemoteException;
	
	/**
	 * Permet de r�veiller le joueur lors de l'arriv�e d'un adversaire ou d'une action de ce dernier
	 * 
	 * @return 0 si tout s'est bien pass�, 1 sinon
	 * @throws RemoteException
	 */
	public void arriveeAdversaire() throws RemoteException;
	
	/**
	 * Permet de se mettre en attente d'une action de la part du joueur 
	 * 
	 * @throws RemoteException
	 */		
	public void attendreAdversaire() throws RemoteException;
	
	/**
	 * Permet de r�cup�rer la piece adverse � d�placer
	 * 
	 * @throws RemoteException
	 */	
	public Piece getPiece() throws RemoteException;
	
	/**
	 * Permet de r�cup�rer le d�placement adverse
	 * 
	 * @throws RemoteException
	 */	
	public String getDeplacement() throws RemoteException;
	
	/**
	 * Permet de stoker le d�placement du joueur
	 * 
	 * @throws RemoteException
	 */	
	public void setDeplacement(Piece piece, String deplacement) throws RemoteException;
	
	public void attendreTour() throws RemoteException;
}
