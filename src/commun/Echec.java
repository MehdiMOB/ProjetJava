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
	 * Methode qui permet au joueur de démarrer une partie et retourne l'équipe qui lui a été affectée
	 * @param string 
	 * @param nom du joueur
	 * @return camp qui lui est affecté
	 * @throws RemoteException
	 */
	String demarrerPartie(String nomJoueur, ClientJoueur joueur, String chatJoueur) throws RemoteException;	

	/**
	 * Echange des équipe et du chat entre les joueurs
	 *  
	 * @param nom du joueur qui communique son équipe, ensemble des pièces de l'équipe sous forme de chaine de caractères
	 * @return 0 si tout s'est bien passé, 1 sinon
	 * @throws RemoteException
	 */
	public String creationEquipe(String nomJoueur, Equipe equipe) throws RemoteException;
	
	/**
	 * Permet de savoir si c'est au joueur de joueur
	 * 
	 * @param nomJoueur
	 * @return booléen qui indique si c'est au joueur de jouer
	 * @throws RemoteException
	 */
	public boolean tourJoueur(String nomJoueur) throws RemoteException;
	
	/**
	 * Permet de déplacer une pièce vers une case vide et de laisser son tour à l'autre joueur
	 * 
	 * @param pièce à déplacer, coordonnées de la case de destination
	 * @throws RemoteException
	 */	
	void deplacerPiece(String nomJoueur, Piece occupant, int x, int y) throws RemoteException;
	
	/**
	 * Permet de lancer un combat entre 2 pièces de jeu
	 * 
	 * @return déroulé de la bataille et pièce gagnate sous la forme d'une chaine de caractères
	 * @throws RemoteException
	 */	
	public String bataille(Piece A , Piece B, Arme arme, Armure armure, int x, int y) throws RemoteException;
	
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
}
