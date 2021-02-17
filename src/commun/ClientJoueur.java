package commun;

import java.rmi.Remote;
import java.rmi.RemoteException;

import jeu.Equipe;

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
}
