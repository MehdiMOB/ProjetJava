package client;

import java.rmi.RemoteException;

import java.rmi.server.UnicastRemoteObject;
import commun.ClientJoueur;
import jeu.Equipe;
import protagonistes.Piece;

/**
 * Jeu de plateau type échecs multijoueurs
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */

public class ClientJoueurImpl extends UnicastRemoteObject implements ClientJoueur {

	Equipe equipeAdverse = null;
	boolean adversairePresent = false;
	String deplacementJoueur;
	Piece piece = null;
	Boolean tour = false;
	String resultatBataille = "";

	protected ClientJoueurImpl() throws RemoteException {
		super();
	}

	/**
	 * Réception de la composition et de la position initiale de l'équipe adverse
	 * 
	 * @throws RemoteException
	 */
	public void setEquipeAdverse(Equipe equipe_adverse) throws RemoteException {
		this.equipeAdverse = equipe_adverse;
	}

	/**
	 * Mise à jour de la présence du joueur adverse par le serveur
	 * 
	 * @return booléen représentant la présence d'un adversaire
	 * @throws RemoteException
	 */
	public void setAdversairePresent(boolean adversairePresent) throws RemoteException{
		this.adversairePresent = adversairePresent;
	}
	
	/**
	 * Permet d'accéder à l'équipe adverse mise à jour par le serveur
	 * @return Equipe représentant l'équipe adverse
	 */
	public Equipe getEquipeAdverse() {
		return this.equipeAdverse;
	}
	
	/**
	 * Permet au serveur de communiquer la pièce à déplacer et la destination
	 * 
	 * @throws RemoteException
	 */	
	public void setDeplacement(Piece piece, String deplacement) throws RemoteException{
		this.piece = piece;
		this.deplacementJoueur = deplacement;
	}
	
	/**
	 * Permet de récupérer la piece adverse à déplacer
	 * 
	 * @throws RemoteException
	 */	
	public Piece getPiece() {		
		return piece;
	}
	
	/**
	 * Permet de récupérer le déplacement adverse
	 */	
	public String getDeplacement() {		
		return deplacementJoueur;
	}
	
	/**
	 *  Vérification de la présence d'un adversaire pour interrompre son attente
	 */
	public boolean isAdversairePresent() {
		return adversairePresent;
	}

	/**
	 * Mise à jour du détail de la bataille par le serveur
	 * 
	 * @param resultatBataille
	 * @throws RemoteException
	 */
	public void setResultatBataille(String resultatBataille) throws RemoteException{
		this.resultatBataille = resultatBataille;
	}
	
	/**
	 * Récupération du détail de la bataille pour un affichage sur la console
	 * 
	 */
	public String getResultatBataille() {
		return resultatBataille;
	}


}
