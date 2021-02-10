package commun;

import java.rmi.Remote;
import java.rmi.RemoteException;

import jeu.Equipe;
import protagonistes.Piece;
import tresor.Arme;
import tresor.Armure;

/**
 * interface de jeu de plateau type échecs multijoueurs
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */

public interface Echec extends Remote {
	
	/**
	 * Methode qui permet la création de l'équipe 
	 * @param nom du joueur qui communiqie son équipe, ensemble des pièces de l'équipe sous forme de chaine de caractères
	 * @return 0 si tout s'est bien passé, 1 sinon
	 * @throws RemoteException
	 */
	public int creationEquipe(String nomJoueur, Equipe equipe) throws RemoteException;
	
	/**
	 * 
	 * @throws RemoteException
	 */	
	public String bataille(Piece A , Piece B, Arme arme, Armure armure) throws RemoteException;
	
	/**
	 * Methode qui permet au joueur de sauvegarder une partie en cours sur le serveur
	 * @throws RemoteException
	 */
	void sauvegarderPartie() throws RemoteException;
	
	/**
	 * Methode qui permet au joueur de récupérer une partie stockée sur le serveur et de continuer à jouer
	 * @throws RemoteException
	 */
	void restaurerPartie() throws RemoteException;	
	
	/**
	 * Methode qui permet au joueur de démarrer une partie et retourne l'équipe qui lui a été affectée
	 * @param nom du joueur
	 * @return camp qui lui est affecté
	 * @throws RemoteException
	 */
	String demarrerPartie(String nomJoueur, Client_joueur joueur) throws RemoteException;	

}
