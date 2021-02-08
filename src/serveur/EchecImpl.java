package serveur;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

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


	/**
	 * Instancie un nouveau jeu
	 * @throws RemoteException
	 */
	protected EchecImpl() throws RemoteException {
	}
		
	/**
	 * Methode qui permet la cr�ation de l'�quipe 
	 * @param ensemble des pi�ces de l'�quipe sous forme de chaine de caract�res
	 * @return 0 si tout s'est bien pass�, 1 sinon
	 * @throws RemoteException
	 */	
	public int creationEquipe(String equipe) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
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
