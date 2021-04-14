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
	Piece perdant;

	protected ClientJoueurImpl() throws RemoteException {
		super();
	}

	/**
	 * Réception de la composition de l'équipe adverse
	 * 
	 * @param Equipe adverse
	 * @throws RemoteException
	 */
	public void setEquipeAdverse(Equipe equipe_adverse) throws RemoteException {
		this.equipeAdverse = equipe_adverse;
	}

	/**
	 * Mise à jour de la présence du joueur adverse par le serveur
	 * 
	 * @param booléen représentant la présence d'un adversaire
	 * @throws RemoteException
	 */
	public void setAdversairePresent(boolean adversairePresent) throws RemoteException{
		this.adversairePresent = adversairePresent;
	}
	
	/**
	 * Permet d'accéder à l'équipe adverse mise à jour par le serveur
	 * 
	 * @return Equipe représentant l'équipe adverse
	 */
	public Equipe getEquipeAdverse() {
		return this.equipeAdverse;
	}
	
	/**
	 * Permet au serveur de communiquer la pièce à déplacer et les coordonées de destination
	 * 
	 * @param Pièce à déplacer
	 * @param Destination sous forme de chaine de caractères
	 * @throws RemoteException
	 */	
	public void setDeplacement(Piece piece, String deplacement) throws RemoteException{
		this.piece = piece;
		this.deplacementJoueur = deplacement;
	}
	
	/**
	 * Permet de récupérer la pièce adverse à déplacer
	 * 
	 * @return pièce
	 */	
	public Piece getPiece() {		
		return piece;
	}
	
	/**
	 * Permet de récupérer la pièce à supprimer à la fin de la bataille
	 * 
	 * @return pièce
	 */	
	public Piece getPerdant() {		
		return perdant;
	}
	
	/**
	 * Permet de récupérer le déplacement adverse
	 * 
	 * @return chaine de caractère représentant le déplacement
	 */	
	public String getDeplacement() {		
		return deplacementJoueur;
	}
	
	/**
	 * Vérification de la présence d'un adversaire pour interrompre son attente
	 * 
	 * @return booléen à true si l'adversaire est présent, sinon false 
	 */
	public boolean isAdversairePresent() {
		return adversairePresent;
	}

	/**
	 * Mise à jour du détail de la bataille par le serveur
	 * 
	 * @param resultatBataille
	 * @param pièce gagnante
	 * @param pièce perdante
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
	 * Récupération du détail de la bataille pour un affichage sur la console
	 * 
	 * @return résulat de la bataille sous forme de chaine de caractères 
	 */
	public String getResultatBataille() {
		return resultatBataille;
	}
}
