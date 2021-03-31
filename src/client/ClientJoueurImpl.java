package client;

import java.rmi.RemoteException;

import java.rmi.server.UnicastRemoteObject;
import commun.ClientJoueur;
import jeu.Equipe;
import protagonistes.Piece;

/**
 * Jeu de plateau type �checs multijoueurs
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
	 * R�ception de la composition et de la position initiale de l'�quipe adverse
	 * 
	 * @throws RemoteException
	 */
	public void setEquipeAdverse(Equipe equipe_adverse) throws RemoteException {
		this.equipeAdverse = equipe_adverse;
	}

	/**
	 * Mise � jour de la pr�sence du joueur adverse par le serveur
	 * 
	 * @return bool�en repr�sentant la pr�sence d'un adversaire
	 * @throws RemoteException
	 */
	public void setAdversairePresent(boolean adversairePresent) throws RemoteException{
		this.adversairePresent = adversairePresent;
	}
	
	/**
	 * Permet d'acc�der � l'�quipe adverse mise � jour par le serveur
	 * @return Equipe repr�sentant l'�quipe adverse
	 */
	public Equipe getEquipeAdverse() {
		return this.equipeAdverse;
	}
	
	/**
	 * Permet au serveur de communiquer la pi�ce � d�placer et la destination
	 * 
	 * @throws RemoteException
	 */	
	public void setDeplacement(Piece piece, String deplacement) throws RemoteException{
		this.piece = piece;
		this.deplacementJoueur = deplacement;
	}
	
	/**
	 * Permet de r�cup�rer la piece adverse � d�placer
	 * 
	 * @throws RemoteException
	 */	
	public Piece getPiece() {		
		return piece;
	}
	
	/**
	 * Permet de r�cup�rer le d�placement adverse
	 */	
	public String getDeplacement() {		
		return deplacementJoueur;
	}
	
	/**
	 *  V�rification de la pr�sence d'un adversaire pour interrompre son attente
	 */
	public boolean isAdversairePresent() {
		return adversairePresent;
	}

	/**
	 * Mise � jour du d�tail de la bataille par le serveur
	 * 
	 * @param resultatBataille
	 * @throws RemoteException
	 */
	public void setResultatBataille(String resultatBataille) throws RemoteException{
		this.resultatBataille = resultatBataille;
	}
	
	/**
	 * R�cup�ration du d�tail de la bataille pour un affichage sur la console
	 * 
	 */
	public String getResultatBataille() {
		return resultatBataille;
	}


}
