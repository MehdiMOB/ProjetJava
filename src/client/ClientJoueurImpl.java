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
	Piece perdant;

	protected ClientJoueurImpl() throws RemoteException {
		super();
	}

	/**
	 * R�ception de la composition de l'�quipe adverse
	 * 
	 * @param Equipe adverse
	 * @throws RemoteException
	 */
	public void setEquipeAdverse(Equipe equipe_adverse) throws RemoteException {
		this.equipeAdverse = equipe_adverse;
	}

	/**
	 * Mise � jour de la pr�sence du joueur adverse par le serveur
	 * 
	 * @param bool�en repr�sentant la pr�sence d'un adversaire
	 * @throws RemoteException
	 */
	public void setAdversairePresent(boolean adversairePresent) throws RemoteException{
		this.adversairePresent = adversairePresent;
	}
	
	/**
	 * Permet d'acc�der � l'�quipe adverse mise � jour par le serveur
	 * 
	 * @return Equipe repr�sentant l'�quipe adverse
	 */
	public Equipe getEquipeAdverse() {
		return this.equipeAdverse;
	}
	
	/**
	 * Permet au serveur de communiquer la pi�ce � d�placer et les coordon�es de destination
	 * 
	 * @param Pi�ce � d�placer
	 * @param Destination sous forme de chaine de caract�res
	 * @throws RemoteException
	 */	
	public void setDeplacement(Piece piece, String deplacement) throws RemoteException{
		this.piece = piece;
		this.deplacementJoueur = deplacement;
	}
	
	/**
	 * Permet de r�cup�rer la pi�ce adverse � d�placer
	 * 
	 * @return pi�ce
	 */	
	public Piece getPiece() {		
		return piece;
	}
	
	/**
	 * Permet de r�cup�rer la pi�ce � supprimer � la fin de la bataille
	 * 
	 * @return pi�ce
	 */	
	public Piece getPerdant() {		
		return perdant;
	}
	
	/**
	 * Permet de r�cup�rer le d�placement adverse
	 * 
	 * @return chaine de caract�re repr�sentant le d�placement
	 */	
	public String getDeplacement() {		
		return deplacementJoueur;
	}
	
	/**
	 * V�rification de la pr�sence d'un adversaire pour interrompre son attente
	 * 
	 * @return bool�en � true si l'adversaire est pr�sent, sinon false 
	 */
	public boolean isAdversairePresent() {
		return adversairePresent;
	}

	/**
	 * Mise � jour du d�tail de la bataille par le serveur
	 * 
	 * @param resultatBataille
	 * @param pi�ce gagnante
	 * @param pi�ce perdante
	 * @param case de destination
	 * @throws RemoteException
	 */
	public void setResultatBataille(String resultatBataille, Piece gagnant, Piece perdant, String deplacement) throws RemoteException{
		this.piece = gagnant;
		this.perdant = perdant;
		this.resultatBataille = resultatBataille;
		this.deplacementJoueur = deplacement;
	}
	
	/**
	 * R�cup�ration du d�tail de la bataille pour un affichage sur la console
	 * 
	 * @return r�sulat de la bataille sous forme de chaine de caract�res 
	 */
	public String getResultatBataille() {
		return resultatBataille;
	}
}
