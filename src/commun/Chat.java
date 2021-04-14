package commun;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface de messagerie entre deux clients du serveur de jeu d'échec
 * 
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */


public interface Chat extends Remote{
	
	/**
	 * Getter de l'attribut name défini lors de la création de l'objet
	 * 
	 * 
	 * @return nom du Chat
	 * @throws RemoteException
	 */
	public String getName() throws RemoteException;
	
	/**
	 * Methode proposée à un utilisateur distant pour qu'il puisse envoyer un message à afficher localement
	 * 
	 * @param message de type String à afficher
	 * @throws RemoteException
	 */
	public void sendMessage(String msg) throws RemoteException;
	
}
