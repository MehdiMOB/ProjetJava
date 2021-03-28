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
	 * Permet de réveiller le joueur lors de l'arrivée d'un adversaire
	 * 
	 * @return 0 si tout s'est bien passé, 1 sinon
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
	 * Permet d'accéder à l'équipe adverse mise à jour par le serveur
	 * @return Equipe représentant l'équipe adverse
	 */
	public Equipe getEquipeAdverse() {
		return this.equipeAdverse;
	}
		
	
	/**
	 * Permet de récupérer la piece adverse à déplacer
	 * 
	 * @throws RemoteException
	 */	
	public Piece getPiece() throws RemoteException{		
		return piece;
	}
	
	/**
	 * Permet de récupérer le déplacement adverse
	 * 
	 * @throws RemoteException
	 */	
	public String getDeplacement() throws RemoteException{		
		return deplacementJoueur;
	}
	
	/**
	 * Permet de stoker le déplacement du joueur
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
