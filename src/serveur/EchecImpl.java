package serveur;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

import commun.Chat;
import commun.ClientJoueur;
import commun.Echec;
import jeu.Equipe;
import protagonistes.Piece;
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
	int joueurReady = 0; 
	
	/**
	 * Instancie un nouveau jeu
	 * @throws RemoteException
	 */
	protected EchecImpl() throws RemoteException {
	}
		
	
	/**
	 * Methode qui permet au joueur de démarrer une partie et retourne l'équipe qui lui a été affectée
	 * @param string 
	 * @param nom du joueur
	 * @return camp qui lui est affecté
	 * @throws RemoteException
	 */
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
			joueurDragon.setAdversairePresent(true);
			joueurHomme.setAdversairePresent(true);			
			return"homme";
		}
		
	}

	
	/**
	 *  Echange des équipe et du chat entre les joueurs
	 *  
	 * @param nom du joueur qui communique son équipe, ensemble des pièces de l'équipe sous forme de chaine de caractères
	 * @return 0 si tout s'est bien passé, 1 sinon
	 * @throws RemoteException
	 */	
	public String creationEquipe(String nomJoueur, Equipe equipe) throws RemoteException {
		
		if(joueurReady > 0) {
			setTour("homme");
		}
		if (dragon.equals(nomJoueur)){
			joueurReady++;
			joueurHomme.setEquipeAdverse(equipe);	
			return chatHomme + "%_%" + homme;
		}else{
			joueurReady++;
			joueurDragon.setEquipeAdverse(equipe);
			return  chatDragon + "%_%" + dragon; 
		}
	}
	
	/**
	 * Permet de savoir si c'est au joueur de joueur
	 * 
	 * @param nomJoueur
	 * @return booléen qui indique si c'est au joueur de jouer
	 * @throws RemoteException
	 */
	public boolean tourJoueur(String nomJoueur) throws RemoteException {
		
		if (dragon.equals(nomJoueur)){
			return getTour() == "dragon";
		}else{			
			return getTour() == "homme";
		}
	}
	
	/**
	 * Permet de déplacer une pièce vers une case vide et de laisser son tour à l'autre joueur
	 * 
	 * @param pièce à déplacer, coordonnées de la case de destination
	 * @throws RemoteException
	 */	
	public void deplacerPiece(String nomJoueur, Piece occupant, int x, int y) throws RemoteException {
		if(tourJoueur(nomJoueur)) {
			if (dragon.equals(nomJoueur)){				
				joueurHomme.setDeplacement(occupant, x + "%_%" + y);
				setTour("homme");							
			}else{				
				joueurDragon.setDeplacement(occupant, x + "%_%" + y);
				setTour("dragon");
			}
		}		
	}
	
	/**
	 * Permet de lancer un combat entre 2 pièces de jeu
	 * 
	 * @return déroulé de la bataille et pièce gagnate sous la forme d'une chaine de caractères
	 * @throws RemoteException
	 */
	public String bataille(Piece A , Piece B, Arme arme, Armure armure, int x, int y) throws RemoteException {		
		String histoire = "";

		Random r = new Random();
		while (!A.estMort()&& !B.estMort()) {
			if (r.nextBoolean()) {
				if (B.getArme().getDegat() == 0) {
					histoire += A.subirAttaque(10);
				}else {
					histoire += A.subirAttaque(B.getArme().getDegat());
				}
			}else {
				if(A.getArme().getDegat() == 0) {
					histoire += B.subirAttaque(10);
				}else {
					histoire += B.subirAttaque(A.getArme().getDegat());	
				}
			}
		}
		Piece gagnant = null;
		if (A.estMort()) {
			gagnant = B;
			histoire += B.renforceVie();
			histoire += B.gagnerTresor(arme, armure);
			histoire += "2";
		} else {
			gagnant = A;
			histoire += A.renforceVie();
			histoire += A.gagnerTresor(arme, armure);			
			histoire += "1";
		}
		
		try {
			if (getTour() == "dragon") {
				joueurHomme.setResultatBataille(histoire);
				joueurHomme.setDeplacement(gagnant, x + "%_%" + y);
			}else {
				joueurDragon.setResultatBataille(histoire);
				joueurDragon.setDeplacement(gagnant, x + "%_%" + y);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return histoire;
	}


	@Override
	public void sauvegarderPartie() throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void restaurerPartie() throws RemoteException {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Permet de connaitre le tour en cours et donc le joueur
	 * @return le tour sous forme de chaine de caractères
	 */
	private String getTour() {
		return tour;
	}

	/**
	 * Permet de changer le tour de jeu
	 * @param tour
	 */
	private void setTour(String tour) {
		this.tour = tour;
	}

	/**
	 * 
	 * @param tour
	 * @return
	 */
	private String getJoueurTour(String tour) {
		if (tour == "dragon") {
			return dragon;
		}else {
			return homme;
		}		
	}
}
