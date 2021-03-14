package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.sun.swing.internal.plaf.synth.resources.synth;

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

	Equipe equipeAdverse;
	boolean adversairePresent = false;
	String deplacementJoueur;
	Piece piece = null;

	protected ClientJoueurImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * R�ception de messages
	 * 
	 * @param message � afficher
	 * @throws RemoteException
	 */
	@Override
	public void notification(String message) throws RemoteException {
		// TODO Auto-generated method stub

	}

	/**
	 * R�ception de la composition et de la position initiale de l'�quipe adverse
	 * 
	 * @param objet Equipe repr�sentant l'�quipe adverse
	 * @return 0 si tout s'est bien pass�, 1 sinon
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

}
