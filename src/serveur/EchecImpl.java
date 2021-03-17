package serveur;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

import commun.ClientJoueur;
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
	ClientJoueur joueurDragon;
	String homme;
	ClientJoueur joueurHomme;
	String chatDragon;
	String chatHomme;
	String tour = "";
	
	/**
	 * Instancie un nouveau jeu
	 * @throws RemoteException
	 */
	protected EchecImpl() throws RemoteException {
	}
		
	
	@Override
	public String demarrerPartie(String nomJoueur, ClientJoueur joueur, String chatJoueur) throws RemoteException {
		
		// Le premier joueur a l'équipe dragon et se met en attente de l'adversaire
		if (dragon == null) {
			dragon = nomJoueur;
			joueurDragon = joueur;
			chatDragon = chatJoueur;
			
			return "dragon";
		}else {
		// Le deuxième joueur a l'équipe des hommes et informe le premier joueur qu'il a un nouvel adversaire	
			homme = nomJoueur;
			joueurHomme = joueur;
			chatHomme = chatJoueur;
			joueurDragon.arriveeAdversaire();			
			return"homme";
		}
		
	}

	
	/**
	 * Methode qui permet la création de l'équipe 
	 * @param nom du joueur qui communiqie son équipe, ensemble des pièces de l'équipe sous forme de chaine de caractères
	 * @return 0 si tout s'est bien passé, 1 sinon
	 * @throws RemoteException
	 */	
	public String creationEquipe(String nomJoueur, Equipe equipe) throws RemoteException {
		// TODO Auto-generated method stub
		
		int joueurReady = 0; 
		if(joueurReady > 1) {
			tour = "homme";
		}
		if (dragon.equals(nomJoueur)){
			joueurReady++;
			joueurHomme.equipeAdverse(equipe);
			return chatHomme+ "%_%" +homme;
		}else{
			joueurReady++;
			joueurDragon.equipeAdverse(equipe);
			return  chatDragon+ "%_%" +dragon; 
		}
	}
	
	public boolean tourJoueur(String nomJoueur) throws RemoteException{
		if (dragon.equals(nomJoueur)){
			return tour=="dragon";
		}else{
			
			return tour=="homme";
		}
	}
	
	@Override
	public void deplacerPiece(String nomJoueur, Piece occupant, int x, int y) throws RemoteException {
		if(tourJoueur(nomJoueur)) {
			if (dragon.equals(nomJoueur)){
				tour = "homme";
				joueurHomme.setDeplacement(occupant, x + "%_%" + y);
							
			}else{
				tour ="dragon";
				joueurDragon.setDeplacement(occupant, x + "%_%" + y);
			}
		}
		
	}
	
	/**
	 * Permet de lancer un combat entre 2 pièce de jeu
	 * 
	 * @return déroulé de la bataille et pièce gagnate sous la forme d'une chaine de caractères
	 * @throws RemoteException
	 */
	public int bataille(Piece A , Piece B, Arme arme, Armure armure) {
		
		
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


	@Override
	public void sauvegarderPartie() throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void restaurerPartie() throws RemoteException {
		// TODO Auto-generated method stub

	}


	
	


}
