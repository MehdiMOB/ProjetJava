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

	protected ClientJoueurImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public synchronized void attendreAdversaire() {
		while (equipeAdverse == null){
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Permet de r�veiller le joueur lors de l'arriv�e d'un adversaire
	 * 
	 * @return 0 si tout s'est bien pass�, 1 sinon
	 * @throws RemoteException
	 */
	public void arriveeAdversaire() throws RemoteException {
		// TODO Auto-generated method stub
		adversairePresent = true;
		
		synchronized(this) {
			try {
				this.notify();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
		
	/**
	 * Permet d'acc�der � l'�quipe adverse mise � jour par le serveur
	 * @return Equipe repr�sentant l'�quipe adverse
	 */
	public Equipe getEquipeAdverse() {
		return this.equipeAdverse;
	}
		
	
	/**
	 * Permet de r�cup�rer la piece adverse � d�placer
	 * 
	 * @throws RemoteException
	 */	
	public Piece getPiece() throws RemoteException{		
		return piece;
	}
	
	/**
	 * Permet de r�cup�rer le d�placement adverse
	 * 
	 * @throws RemoteException
	 */	
	public String getDeplacement() throws RemoteException{		
		return deplacementJoueur;
	}
	
	/**
	 * Permet de stoker le d�placement du joueur
	 * 
	 * @throws RemoteException
	 */	
	public void setDeplacement(Piece piece, String deplacement) throws RemoteException{
		this.piece = piece;
		this.deplacementJoueur = deplacement;
	}


	@Override
	public void setEquipeAdverse(Equipe equipe_adverse) throws RemoteException {
		this.equipeAdverse = equipe_adverse;
	}


}
