package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.sun.swing.internal.plaf.synth.resources.synth;

import commun.ClientJoueur;
import jeu.Equipe;

/**
 * Jeu de plateau type �checs multijoueurs
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */

public class ClientJoueurImpl extends UnicastRemoteObject implements ClientJoueur {

	Equipe equipeAdverse;
	boolean adversairePresent = false;

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
	
	public int arriveeAdversaire() throws RemoteException {
		// TODO Auto-generated method stub
		adversairePresent = true;
		
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
	 * Permet d'acc�er � l'�quipe adverse mis � jour par le serveur
	 * @return Equipe repr�sentant l'�quipe adverse
	 */
	public Equipe getEquipeAdverse() {
		return equipeAdverse;
	}
	
	public synchronized void attendreAdversaire() {
		while (!adversairePresent){
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

}
