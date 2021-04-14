package commun;

import java.rmi.Remote;
import java.rmi.RemoteException;

import jeu.Equipe;
import protagonistes.Piece;

/**
 * Interface de jeu de plateau type échecs multijoueurs
 * Fonctionnalités coté utilisateur permettant la mise à jour d'informations par le serveur
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */

public interface ClientJoueur extends Remote {
	
	/**
	 * Réception de la composition de l'équipe adverse
	 * 
	 * @param Equipe adverse
	 * @throws RemoteException
	 */
	public void setEquipeAdverse(Equipe equipe_adverse) throws RemoteException;
	
	/**
	 * Mise à jour de la présence du joueur adverse par le serveur
	 * 
	 * @param booléen représentant la présence d'un adversaire
	 * @throws RemoteException
	 */
	public void setAdversairePresent(boolean adversairePresent) throws RemoteException;
		
	/**
	 * Permet au serveur de communiquer la pièce à déplacer et la destination
	 *
	 * @param Pièce à déplacer
	 * @param Destination sous forme de chaine de caractères
	 * @throws RemoteException
	 */	
	public void setDeplacement(Piece piece, String deplacement) throws RemoteException;	
	
	/**
	 * Mise à jour du détail de la bataille par le serveur
	 * 
	 * @param resultatBataille
	 * @throws RemoteException
	 */
	public void setResultatBataille(String resultatBataille, Piece gagnant, Piece perdant, String deplacement) throws RemoteException;
	
}
