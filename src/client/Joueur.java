package client;

import java.rmi.Naming;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import commun.Chat;
import commun.Echec;
import creation.Clavier;
import creation.CreerProtagoniste;
import creation.DeplacerJoueur;
import creation.Setup;
import graphique.InterfacePlateau;
import jeu.Equipe;
import jeu.Plateau;
import protagonistes.Piece;

/**
 * Classe exécutée coté client qui lui permet de jouer en ligne à un jeu de type échecs
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */

public class Joueur {	

	private static String nomJoueur;
	private static ClientJoueurImpl clientjoueur; 
	
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, InterruptedException{

		// Création d'un scanner sur l'interface du système afin de récupérer des informations saisies par l'utilisateur
		Scanner scan = new Scanner(System.in);

		// Récupération du nom du Joueur
		System.out.println("Bonjour, veuillez saisir votre nom \n");
		setNomJoueur(scan.nextLine());
		
		// Création d'un serveur de messagerie et mise en partage sur le port 1100	
		Registry registry;
		try {
			registry = LocateRegistry.createRegistry(1100);
		}catch(Exception e) {
			registry = LocateRegistry.getRegistry(1100);
		}
    	
		// Communication du chat
    	Chat server = new ChatImpl(getNomJoueur());
    	Naming.rebind("rmi://localhost:1100/" + getNomJoueur(), server); 
    	
    	jouerPartie();

	}
	
	public static void jouerPartie() throws RemoteException, MalformedURLException, NotBoundException {
		
		// Connexion au serveur et Récupération de l'objet partagé par le serveur et instanciation
		Echec serveur = (Echec) Naming.lookup("rmi://localhost:1099/Echec");
		
		CreerProtagoniste creation = new CreerProtagoniste();

		Equipe equipeJoueur = null;
		Equipe equipeAdversaire = null;
		clientjoueur = new ClientJoueurImpl();

		// Variables d'échange d'informations entre le serveur et le client
		String camp = null;		
		Plateau plateau;
		InterfacePlateau interfaceplateau;
		Setup setup;
		
		
    	// Récupération du camp
		camp = serveur.demarrerPartie(getNomJoueur(), getClientjoueur(), "rmi://localhost:1100/" + getNomJoueur());

		// Lancement du script de création d'équipe et récupération de l'équipe créée
		if (camp.equals("homme") || camp.equals("dragon")){
			equipeJoueur = creation.creerProtagoniste(camp);
		}else {
			System.err.println("Valeur de retour inattendue !");
		}

		// Si premier, mise en attente de la connexion de l'adversaire
		Boolean adversairePresent = getClientjoueur().isAdversairePresent();
		while(!adversairePresent) {
			System.out.print("En recherche de joueurs ...");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			adversairePresent = getClientjoueur().isAdversairePresent();
		}
		System.out.println("");

		// Envoie de l'équipe créée au serveur quand l'adversaire est connecté		
		String[] adversaire = serveur.creationEquipe(getNomJoueur(), equipeJoueur).split("%_%");
//		String nomAdversaire = adversaire[1];
		
		// Mise en attente de réception de l'équipe adverse
		equipeAdversaire = getClientjoueur().getEquipeAdverse();
		while(equipeAdversaire == null) {
			System.out.print("Votre adversaire est en train de composer son équipe...");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			equipeAdversaire = getClientjoueur().getEquipeAdverse();
		}

		// Affichage du plateau
		interfaceplateau = new InterfacePlateau();

		if (camp.equals("homme")) {
			plateau = new Plateau(equipeJoueur, equipeAdversaire, null, null);
			setup = new Setup(getNomJoueur(), interfaceplateau,plateau,equipeJoueur, equipeAdversaire);
		} else {
			plateau = new Plateau(equipeAdversaire, equipeJoueur, null, null);
			setup = new Setup(getNomJoueur(), interfaceplateau, plateau, equipeAdversaire, equipeJoueur);
		}

		setup.positionner();
		setup.afficherSetup();

		try {
			jouer(adversaire, setup, camp, serveur);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}		

	// Déroulement du jeu
	private static void jouer(String[] adversaire, Setup setup, String camp, Echec serveur) throws RemoteException, InterruptedException, MalformedURLException, NotBoundException {

		int option=0;
		boolean monTour = camp.equals("homme");
		
		while (true) {
			
			if (setup.hommes.getNbEffectif() == 0 ){
				System.out.println("Les dragons ont gagné !");
				break;
			}
			
			if (setup.dragons.getNbEffectif() == 0 ){
				System.out.println("Les hommes ont gagné !");
				break;
			}

			if (camp.equals("homme")) {
				if (monTour) {
					// Choix de la pièce à déplacer
					deplacement(setup.hommes, setup.interfacePlateau, setup.plateau);
					afficherGrille(setup);
					monTour = false;
				} else {				
					// attente du tour et récupération du tour adverse
					attendreSonTour(adversaire, serveur);
					// Récupération du coup adverse
					Piece pieceAdverse = getClientjoueur().getPiece();
					String[] deplacement = getClientjoueur().getDeplacement().split("%_%");
					if (getClientjoueur().getResultatBataille() != "") {
						// En cas de bataille, récupération des détails et suppression de la pièce éliminée
						System.out.println(getClientjoueur().getResultatBataille().substring(0, getClientjoueur().getResultatBataille().length() -1));
						Piece piecePerdante = getClientjoueur().getPerdant();
						DeplacerJoueur eliminerPiece = new DeplacerJoueur(getNomJoueur(), setup.interfacePlateau, setup.plateau, piecePerdante);
						eliminerPiece.eliminerPiece();
						
					}else {
						System.out.println("Récupération du coup adverse :");
					}
					// Déplacement en local de la pièce adverse ou du gagnant de la bataille
					DeplacerJoueur deplacement_adversaire = new DeplacerJoueur(getNomJoueur(), setup.interfacePlateau, setup.plateau, pieceAdverse);
					deplacement_adversaire.deplacer(Integer.valueOf(deplacement[0]), Integer.valueOf(deplacement[1]), true);
					afficherGrille(setup);
					monTour = true;
				}
				
			} else {
				if (monTour) {	
					// Equipe Dragon
					deplacement(setup.dragons, setup.interfacePlateau, setup.plateau);
					afficherGrille(setup);
					monTour = false;	
					
				} else {
					// Attente du tour et récupération du tour adverse
					attendreSonTour(adversaire, serveur);
					// Récupération du coup adverse
					Piece pieceAdverse = getClientjoueur().getPiece();
					String[] deplacement = getClientjoueur().getDeplacement().split("%_%");
					if (getClientjoueur().getResultatBataille() != "") {
						// En cas de bataille, récupération des détails et suppression de la pièce éliminée
						System.out.println(getClientjoueur().getResultatBataille().substring(0, getClientjoueur().getResultatBataille().length() -1));
						Piece piecePerdante = getClientjoueur().getPerdant();
						DeplacerJoueur eliminerPiece = new DeplacerJoueur(getNomJoueur(), setup.interfacePlateau, setup.plateau, piecePerdante);
						eliminerPiece.eliminerPiece();
					}else {
						System.out.println("Récupération du coup adverse :");
					}
					// Déplacement en local de la pièce adverse ou du gagnant de la bataille
					DeplacerJoueur deplacement_adversaire = new DeplacerJoueur(getNomJoueur(), setup.interfacePlateau, setup.plateau, pieceAdverse);
					deplacement_adversaire.deplacer(Integer.valueOf(deplacement[0]), Integer.valueOf(deplacement[1]), true);
					afficherGrille(setup);
					monTour = true;				
				}
			}
		}
		serveur.deconnexion(nomJoueur);
		System.out.println("Choisir une option: ");
		System.out.println("1- Commencer une nouvelle partie");
		System.out.println("2- Quitter \n");
		option = Clavier.entrerClavierInt();

		if (option == 1){
			jouerPartie();
		} else {
			System.out.println("Merci d'avoir joué avec nous :) ");
		}
	}

	/***
	 * Permet de joueur de chater en attendant son tour de jeu
	 * 
	 * @param adversaire: adresse du chat du joueur adverse
	 * @param serveur: serveur du jeu pour connaitre le tour du joueur
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 * @throws InterruptedException
	 */
	private static void attendreSonTour(String[] adversaire, Echec serveur) throws MalformedURLException, RemoteException, NotBoundException, InterruptedException  {
		
		// Mise en attente d'une action du joueur adverse
		Chat chatAdverse = (Chat) Naming.lookup(adversaire[0]);
		Scanner s = new Scanner(System.in);
		String msg;
		
		int choix;
		do {
			System.out.println("Que voulez-vous faire ?");
			System.out.println("1 - Jouer un tour");
			System.out.println("2 - Envoyer un message à l'adversaire");
			
			choix = s.nextInt();
			if (choix == 2) {
				Scanner s2 = new Scanner(System.in);
				msg = s2.nextLine().trim();
		    	msg = "["+ getNomJoueur() + "] " + msg;		    		
		    	chatAdverse.sendMessage(msg);
			}else {
				Thread.sleep(100);
				if (!serveur.tourJoueur(getNomJoueur()) && getClientjoueur().getResultatBataille() == "") {
					System.out.println("Ce n'est pas à votre tour de jouer");
				}
			}
		} while(!serveur.tourJoueur(getNomJoueur()) && getClientjoueur().getResultatBataille() == "");
	}

	public static String getNomJoueur() {
		return nomJoueur;
	}

	public static void setNomJoueur(String nom) {
		nomJoueur = nom;
	}

	private static ClientJoueurImpl getClientjoueur() {
		return clientjoueur;
	}
	
	private static void afficherGrille(Setup setup) {
		setup.afficherSetup();
	}
	
	private static void deplacement(Equipe equipe, InterfacePlateau interfacePlateau, Plateau plateau) {
		System.out.println("Entrer le type de protagoniste dragon à contrôler :");
		System.out.println(equipe.afficherPieces());
		int num = Clavier.entrerClavierInt()-1;
		System.out.println("Vous avez choisi le protagoniste "+ equipe.getPiece(num).getNom());
		
		DeplacerJoueur dragon_deplacement = new DeplacerJoueur(getNomJoueur(), interfacePlateau, plateau, equipe.getPiece(num));
		dragon_deplacement.deplacement();
	}
}

