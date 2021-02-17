package commun;

import java.rmi.Remote;
import java.rmi.RemoteException;

import jeu.Equipe;

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
}
