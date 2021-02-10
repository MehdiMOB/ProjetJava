package serveur;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

import commun.Client_joueur;
import commun.Echec;
import jeu.Equipe;
import protagonistes.Piece;
import testJeu.DeroulementJeu;
import tresor.Arme;
import tresor.Armure;

/**
 * Jeu de plateau type échecs multijoueurs
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */

public class EchecImpl extends UnicastRemoteObject implements Echec {


	String dragon;
	Client_joueur joueurDragon;
	String homme;
	Client_joueur joueurHomme;
	
	@Override
	public String demarrerPartie(String nomJoueur, Client_joueur joueur) throws RemoteException {
		// TODO Auto-generated method stub
		// il faut que le joueur appartienne à une équipe : noire ou blanc
		if (dragon == null) {
			dragon = nomJoueur;
			joueurDragon = joueur;
			return "dragon";
		}else {
			homme = nomJoueur;
			joueurHomme = joueur;
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
	 * @param nom du joueur qui communiqie son équipe, ensemble des pièces de l'équipe sous forme de chaine de caractères
	 * @return 0 si tout s'est bien passé, 1 sinon
	 * @throws RemoteException
	 */	
	public int creationEquipe(String nomJoueur, Equipe equipe) throws RemoteException {
		// TODO Auto-generated method stub
		if (dragon == nomJoueur){
			return joueurHomme.equipeAdverse(equipe);
		}else if (homme == nomJoueur) {
			return joueurDragon.equipeAdverse(equipe);
		}else {
			return 1;
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
	
	@Override
	public String bataille(Piece A , Piece B, Arme arme, Armure armure) {
		
		Random r = new Random();
		while (!A.estMort()&& !B.estMort()) {
		if (r.nextBoolean()) {
			if (B.getArme().getDegat() == 0) {
			A.subirAttaque(10);
			}else {
				A.subirAttaque(B.getArme().getDegat());
			}
		}
		else {
			if(A.getArme().getDegat() == 0) {
				B.subirAttaque(10);
			}else {
				B.subirAttaque(A.getArme().getDegat());	
			}
		}
		}
		if (A.estMort()) {
			B.renforceVie();
			B.gagnerTresor(arme,armure);
			
			return 2;
		}
		else {
			A.renforceVie();
			A.gagnerTresor(arme,armure);
			return 1;
		}
	
	}

}
