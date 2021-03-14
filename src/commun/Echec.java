package commun;

import java.rmi.Remote;
import java.rmi.RemoteException;

import jeu.Equipe;
import protagonistes.Piece;
import tresor.Arme;
import tresor.Armure;

/**
 * interface de jeu de plateau type �checs multijoueurs
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */

public interface Echec extends Remote {

	/**
	 * Methode qui permet au joueur de d�marrer une partie et retourne l'�quipe qui lui a �t� affect�e
	 * @param nom du joueur
	 * @return camp qui lui est affect�
	 * @throws RemoteException
	 */
	String demarrerPartie(String nomJoueur, ClientJoueur joueur) throws RemoteException;	

	/**
	 * Methode qui permet la cr�ation de l'�quipe 
	 * @param nom du joueur qui communiqie son �quipe, ensemble des pi�ces de l'�quipe sous forme de chaine de caract�res
	 * @return 0 si tout s'est bien pass�, 1 sinon
	 * @throws RemoteException
	 */
	public int creationEquipe(String nomJoueur, Equipe equipe) throws RemoteException;

	/**
	 * Permet de d�placer une pi�ce vers una case vide et de laisser son tour � l'autre joueur
	 * 
	 * @param pi�ce � d�placer, coordonn�es de la case de destination
	 * @throws RemoteException
	 */	
	void deplacerPiece(String nomJoueur, Piece occupant, int x, int y) throws RemoteException;
	
	/**
	 * Permet de lancer un combat entre 2 pi�ce de jeu
	 * 
	 * @return d�roul� de la bataille et pi�ce gagnate sous la forme d'une chaine de caract�res
	 * @throws RemoteException
	 */	
	public int bataille(Piece A , Piece B, Arme arme, Armure armure) throws RemoteException;
	
	/**
	 * Methode qui permet au joueur de sauvegarder une partie en cours sur le serveur
	 * @throws RemoteException
	 */
	void sauvegarderPartie() throws RemoteException;
	
	/**
	 * Methode qui permet au joueur de r�cup�rer une partie stock�e sur le serveur et de continuer � jouer
	 * @throws RemoteException
	 */
	void restaurerPartie() throws RemoteException;

		
	

}
