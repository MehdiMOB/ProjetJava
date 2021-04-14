package commun;

import java.rmi.Remote;
import java.rmi.RemoteException;

import jeu.Equipe;
import protagonistes.Piece;
import tresor.Arme;
import tresor.Armure;

/**
 * Interface de jeu de plateau type �checs multijoueurs
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */

public interface Echec extends Remote {

	/**
	 * M�thode qui permet au joueur de d�marrer une partie et retourne l'�quipe qui lui a �t� affect�e
	 *
	 * @param Nom du joueur sous forme de chaine de caract�res qui permettra de l'identifier lors d'�change de messages 
	 * @param Interface de m�thodes utilisables par le serveur pour mettre � jour cot� client les variables du jeu
	 * @param Interface de m�thodes utilisables par le joueur adverse pour �changer des messages dans la console
	 * @return camp qui lui est affect� sous forme de chaine de caract�res
	 * @throws RemoteException
	 */
	String demarrerPartie(String nomJoueur, ClientJoueur joueur, String chatJoueur) throws RemoteException;	

	/**
	 * Echange des �quipe et du chat entre les joueurs
	 *  
	 * @param nom du joueur qui communique son �quipe, ensemble des pi�ces de l'�quipe sous forme de chaine de caract�res
	 * @return adresse du chat adverse et nom de l'adversaire
	 * @throws RemoteException
	 */
	public String creationEquipe(String nomJoueur, Equipe equipe) throws RemoteException;
	
	/**
	 * Permet de connaitre si c'est au joueur de joueur
	 * 
	 * @param nomJoueur sous forme de chaine de carcat�re identifiant le joueur
	 * @return bool�en qui indique si c'est au joueur de jouer
	 * @throws RemoteException
	 */
	public boolean tourJoueur(String nomJoueur) throws RemoteException;
	
	/**
	 * Permet d'�changer de d�placement d'une pi�ce vers une case vide et de laisser son tour � l'autre joueur
	 * 
	 * @param identifiant du joueur souhaitant d�placer une pi�ce
	 * @param pi�ce � d�placer
	 * @param coordonn�es de la case de destination
	 * @throws RemoteException
	 */	
	void deplacerPiece(String nomJoueur, Piece occupant, int x, int y) throws RemoteException;
	
	/**
	 * Permet de lancer un combat entre 2 pi�ces de jeu et de r�compenser le gagnant
	 * Une fois le combat termin�, met � jour la pi�ce gagnante via l'interface ClientJoueur
	 * Puis renvoie le d�roul� de la bataille et la case dans laquelle elle a eu lieu
	 * 
	 * @param Pi�ce attaquant
	 * @param Pi�ce d�fenseur
	 * @param Arme 
	 * @param Armure
	 * @param Coordonn�es de la case o� a lieu la bataille
	 * @return D�roul� de la bataille et pi�ce gagnante sous la forme d'une chaine de caract�res
	 * @throws RemoteException
	 */	
	public String bataille(Piece A , Piece B, Arme arme, Armure armure, int x, int y) throws RemoteException;
	
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
	
	/**
	 * Permet au joueur de se d�connecter du serveur
	 * 
	 * @param nomJoueur
	 * @throws RemoteException
	 */
	void deconnexion(String nomJoueur) throws RemoteException;
}
