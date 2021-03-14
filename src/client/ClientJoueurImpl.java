package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.sun.swing.internal.plaf.synth.resources.synth;

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

	Equipe equipeAdverse;
	boolean adversairePresent = false;
	String deplacementJoueur;
	Piece piece = null;

	protected ClientJoueurImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * Réception de messages
	 * 
	 * @param message à afficher
	 * @throws RemoteException
	 */
	@Override
	public void notification(String message) throws RemoteException {
		// TODO Auto-generated method stub

	}

	/**
	 * Réception de la composition et de la position initiale de l'équipe adverse
	 * 
	 * @param objet Equipe représentant l'équipe adverse
	 * @return 0 si tout s'est bien passé, 1 sinon
	 * @throws RemoteException
	 */
	@Override	
	public int equipeAdverse(Equipe equipe_adverse) throws RemoteException {
		// TODO Auto-generated method stub
		equipeAdverse = equipe_adverse;
		synchronized(this) {
			try {
				this.notify();
				return 0;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return 1;
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
		return equipeAdverse;
	}
	
	/**
	 * Permet de se mettre en attente d'une action de la part du joueur 
	 * 
	 * @throws RemoteException
	 */	
	public synchronized void attendreAdversaire() throws RemoteException{
		while (!adversairePresent){
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Permet de se mettre en attente d'une action de la part du joueur 
	 * 
	 * @throws RemoteException
	 */	
	public synchronized void attendreTour() throws RemoteException{
		try {
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	public synchronized void attendreEquipeAdverse() {
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

}
