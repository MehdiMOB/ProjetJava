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
	 * Réception de la composition et de la position initiale de l'équipe adverse
	 * 
	 * @throws RemoteException
	 */
	public void setEquipeAdverse(Equipe equipe_adverse) throws RemoteException;
	
	/**
	 * Mise à jour de la présence du joueur adverse par le serveur
	 * 
	 * @return booléen représentant la présence d'un adversaire
	 * @throws RemoteException
	 */
	public void setAdversairePresent(boolean adversairePresent) throws RemoteException;
		
	/**
	 * Permet au serveur de communiquer la pièce à déplacer et la destination
	 * 
	 * @throws RemoteException
	 */	
	public void setDeplacement(Piece piece, String deplacement) throws RemoteException;	
	
	/**
	 * Mise à jour du détail de la bataille par le serveur
	 * 
	 * @param resultatBataille
	 * @throws RemoteException
	 */
	public void setResultatBataille(String resultatBataille) throws RemoteException;
	
}
