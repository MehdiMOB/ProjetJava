package serveur;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

import testJeu.DeroulementJeu;

/**
 * Jeu de plateau type échecs multijoueurs
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
		// il faut que le joueur appartienne à une équipe : noire ou blanc
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
	 * Methode qui permet la création de l'équipe 
	 * @param ensemble des pièces de l'équipe sous forme de chaine de caractères
	 * @return 0 si tout s'est bien passé, 1 sinon
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
