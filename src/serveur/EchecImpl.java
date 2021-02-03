package serveur;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

import jeu.Jeu;
import testJeu.DeroulementJeu;

/**
 * Jeu de plateau type �checs multijoueurs
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */

public class EchecImpl extends UnicastRemoteObject implements Echec {


	String dragon; 
	String homme; 
	
	@Override
	public String demarrerPartie(String nomJoueur) throws RemoteException {
		// TODO Auto-generated method stub
		// il faut que le joueur appartienne � une �quipe : noire ou blanc
		if (dragon == null) {
			dragon = nomJoueur;
			return "dragon";
		}else {
			homme = nomJoueur; 
			return"homme";
		}
		
	}

	// Classe qui contient l'ensmble des m�thodes et eds r�gles du jeu
	private Jeu jeu;
	
	/**
	 * Instancie un nouveau jeu
	 * @throws RemoteException
	 */
	protected EchecImpl() throws RemoteException {
		super();
		jeu = new Jeu();
	}
		
	/**
	 * Methode qui permet la cr�ation de l'�quipe en fonction du camp choisi
	 * @param camp
	 * @return confirmation textuelle que l'�quipe a �t� cr��e
	 * @throws RemoteException
	 */	
	public String creationEquipe(int camp) throws RemoteException {
		// TODO Auto-generated method stub
		if (camp == 1) {
			return jeu.creationEquipeHomme();
		}else {
			return jeu.creationEquipeDragon();
		}
	}

	@Override
	public void jouerTour() throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void sauvegarderPartie() throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void restaurerPartie() throws RemoteException {
		// TODO Auto-generated method stub

	}

}
