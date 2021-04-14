package commun;

import java.rmi.Remote;
import java.rmi.RemoteException;

import jeu.Equipe;
import protagonistes.Piece;

/**
 * Interface de jeu de plateau type �checs multijoueurs
 * Fonctionnalit�s cot� utilisateur permettant la mise � jour d'informations par le serveur
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */

public interface ClientJoueur extends Remote {
	
	/**
	 * R�ception de la composition de l'�quipe adverse
	 * 
	 * @param Equipe adverse
	 * @throws RemoteException
	 */
	public void setEquipeAdverse(Equipe equipe_adverse) throws RemoteException;
	
	/**
	 * Mise � jour de la pr�sence du joueur adverse par le serveur
	 * 
	 * @param bool�en repr�sentant la pr�sence d'un adversaire
	 * @throws RemoteException
	 */
	public void setAdversairePresent(boolean adversairePresent) throws RemoteException;
		
	/**
	 * Permet au serveur de communiquer la pi�ce � d�placer et la destination
	 *
	 * @param Pi�ce � d�placer
	 * @param Destination sous forme de chaine de caract�res
	 * @throws RemoteException
	 */	
	public void setDeplacement(Piece piece, String deplacement) throws RemoteException;	
	
	/**
	 * Mise � jour du d�tail de la bataille par le serveur
	 * 
	 * @param resultatBataille
	 * @throws RemoteException
	 */
	public void setResultatBataille(String resultatBataille, Piece gagnant, Piece perdant, String deplacement) throws RemoteException;
	
}
