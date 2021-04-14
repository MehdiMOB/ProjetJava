package commun;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface de messagerie entre deux clients du serveur de jeu d'�chec
 * 
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */


public interface Chat extends Remote{
	
	/**
	 * Getter de l'attribut name d�fini lors de la cr�ation de l'objet
	 * 
	 * 
	 * @return nom du Chat
	 * @throws RemoteException
	 */
	public String getName() throws RemoteException;
	
	/**
	 * Methode propos�e � un utilisateur distant pour qu'il puisse envoyer un message � afficher localement
	 * 
	 * @param message de type String � afficher
	 * @throws RemoteException
	 */
	public void sendMessage(String msg) throws RemoteException;
	
}
