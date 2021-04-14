package commun;

import java.rmi.Remote;
import java.rmi.RemoteException;

import jeu.Equipe;
import protagonistes.Piece;
import tresor.Arme;
import tresor.Armure;

/**
 * Interface de jeu de plateau type échecs multijoueurs
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */

public interface Echec extends Remote {

	/**
	 * Méthode qui permet au joueur de démarrer une partie et retourne l'équipe qui lui a été affectée
	 *
	 * @param Nom du joueur sous forme de chaine de caractères qui permettra de l'identifier lors d'échange de messages 
	 * @param Interface de méthodes utilisables par le serveur pour mettre à jour coté client les variables du jeu
	 * @param Interface de méthodes utilisables par le joueur adverse pour échanger des messages dans la console
	 * @return camp qui lui est affecté sous forme de chaine de caractères
	 * @throws RemoteException
	 */
	String demarrerPartie(String nomJoueur, ClientJoueur joueur, String chatJoueur) throws RemoteException;	

	/**
	 * Echange des équipe et du chat entre les joueurs
	 *  
	 * @param nom du joueur qui communique son équipe, ensemble des pièces de l'équipe sous forme de chaine de caractères
	 * @return adresse du chat adverse et nom de l'adversaire
	 * @throws RemoteException
	 */
	public String creationEquipe(String nomJoueur, Equipe equipe) throws RemoteException;
	
	/**
	 * Permet de connaitre si c'est au joueur de joueur
	 * 
	 * @param nomJoueur sous forme de chaine de carcatère identifiant le joueur
	 * @return booléen qui indique si c'est au joueur de jouer
	 * @throws RemoteException
	 */
	public boolean tourJoueur(String nomJoueur) throws RemoteException;
	
	/**
	 * Permet d'échanger de déplacement d'une pièce vers une case vide et de laisser son tour à l'autre joueur
	 * 
	 * @param identifiant du joueur souhaitant déplacer une pièce
	 * @param pièce à déplacer
	 * @param coordonnées de la case de destination
	 * @throws RemoteException
	 */	
	void deplacerPiece(String nomJoueur, Piece occupant, int x, int y) throws RemoteException;
	
	/**
	 * Permet de lancer un combat entre 2 pièces de jeu et de récompenser le gagnant
	 * Une fois le combat terminé, met à jour la pièce gagnante via l'interface ClientJoueur
	 * Puis renvoie le déroulé de la bataille et la case dans laquelle elle a eu lieu
	 * 
	 * @param Pièce attaquant
	 * @param Pièce défenseur
	 * @param Arme 
	 * @param Armure
	 * @param Coordonnées de la case où a lieu la bataille
	 * @return Déroulé de la bataille et pièce gagnante sous la forme d'une chaine de caractères
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
	
	/**
	 * Permet au joueur de se déconnecter du serveur
	 * 
	 * @param nomJoueur
	 * @throws RemoteException
	 */
	void deconnexion(String nomJoueur) throws RemoteException;
}
