package commun;

import java.rmi.Remote;
import java.rmi.RemoteException;

import jeu.Equipe;
import protagonistes.Piece;

/**
 * interface de jeu de plateau type échecs multijoueurs
 * Fonctionalités coté utilisateur permettant la réception de messages de la part du serveur
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */

public interface ClientJoueur extends Remote {

	/**
	 * Réception de messages
	 * 
	 * @param message à afficher
	 * @throws RemoteException
	 */
	public void notification(String message) throws RemoteException;
	
	/**
	 * Réception de la composition et de la position initiale de l'équipe adverse
	 * 
	 * @param 0 si tout s'est bien passé, 1 sinon
	 * @throws RemoteException
	 */
	public int equipeAdverse(Equipe equipe_adverse) throws RemoteException;
	
	/**
	 * Permet de réveiller le joueur lors de l'arrivée d'un adversaire ou d'une action de ce dernier
	 * 
	 * @return 0 si tout s'est bien passé, 1 sinon
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
	 * Permet de récupérer la piece adverse à déplacer
	 * 
	 * @throws RemoteException
	 */	
	public Piece getPiece() throws RemoteException;
	
	/**
	 * Permet de récupérer le déplacement adverse
	 * 
	 * @throws RemoteException
	 */	
	public String getDeplacement() throws RemoteException;
	
	/**
	 * Permet de stoker le déplacement du joueur
	 * 
	 * @throws RemoteException
	 */	
	public void setDeplacement(Piece piece, String deplacement) throws RemoteException;
	
	public void attendreTour() throws RemoteException;
}
