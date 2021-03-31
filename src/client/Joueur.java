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
 * Classe ex�cut�e cot� client qui lui permet de jouer en ligne � un jeu de type �checs
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */

public class Joueur {	

	private static String nomJoueur;
	private static ClientJoueurImpl clientjoueur; 
	
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, InterruptedException{


		CreerProtagoniste creation = new CreerProtagoniste();

		Equipe equipeJoueur = null;
		Equipe equipeAdversaire = null;
		clientjoueur = new ClientJoueurImpl();

		// Variables d'�change d'informations entre le serveur et le client
		String camp = null;		
		Plateau plateau;
		InterfacePlateau interfaceplateau;
		Setup setup;
		
		// Connexion au serveur et R�cup�ration de l'objet partag� par le serveur et ionstanciation
		Echec serveur = (Echec) Naming.lookup("rmi://localhost:1099/Echec");

		// Cr�ation d'un scanner sur l'interface du syst�me afin de r�cup�rer des informations saisies par l'utilisateur
		Scanner scan = new Scanner(System.in);

		// R�cup�ration du nom du Joueur
		System.out.println("Bonjour, veuillez saisir votre nom \n");
		setNomJoueur(scan.nextLine());
		
		// Cr�ation d'un serveur de messagerie et mise en partage sur le port 1100	
		Registry registry;
		try {
			registry = LocateRegistry.createRegistry(1100);
		}catch(Exception e) {
			registry = LocateRegistry.getRegistry(1100);
		}
    	
    	Chat server = new ChatImpl(getNomJoueur());
    	Naming.rebind("rmi://localhost:1100/" + getNomJoueur(), server); 

    	// R�cup�ration du camp
		camp = serveur.demarrerPartie(getNomJoueur(), getClientjoueur(), "rmi://localhost:1100/" + getNomJoueur());

		// Lancement du script de cr�ation d'�quipe et r�cup�ration de l'�quipe cr��e
		if (camp.equals("homme") || camp.equals("dragon")){
			equipeJoueur = creation.creerProtagoniste(camp);
		}else {
			System.err.println("Valeur de retour inattendue !");
		}

		// Si premier, mise en attente de la connexion de l'adversaire
		Boolean adversairePresent = getClientjoueur().isAdversairePresent();
		while(!adversairePresent) {
			System.out.print("En recherche de joueurs ...");
			Thread.sleep(5000);
			adversairePresent = getClientjoueur().isAdversairePresent();
		}
		System.out.println("");

		// Envoie de l'�quipe cr��e au serveur quand l'adversaire est connect�		
		String[] adversaire = serveur.creationEquipe(getNomJoueur(), equipeJoueur).split("%_%");
		String nomAdversaire = adversaire[1];
		
		// Mise en attente de r�ception de l'�quipe adverse
		equipeAdversaire = getClientjoueur().getEquipeAdverse();
		while(equipeAdversaire == null) {
			System.out.print("Votre adversaire est en train de composer son �quipe...");
			Thread.sleep(5000);
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

		jouer(adversaire, setup, camp, serveur);
	}		

	// D�roulement du jeu
	private static void jouer(String[] adversaire, Setup setup, String camp, Echec serveur) throws RemoteException, InterruptedException, MalformedURLException, NotBoundException {

		//int choix=0;
		int option=0;
		boolean monTour = camp.equals("homme");
		
		while (true) {
			if (setup.hommes.getNbEffectif() == 0 ){
				System.out.println("Les dragons ont gagn� !");
				break;
			}

			if (camp.equals("homme")) {
				if (monTour) {
					// Choix pi�ce � d�placer
					System.out.println("Entrer le type de protagoniste homme � contr�ler :");
					System.out.println(setup.hommes.afficherPieces());
					int numProtagoniste = Clavier.entrerClavierInt()-1;
					System.out.println("Vous avez choisi le protagoniste "+ setup.hommes.getPiece(numProtagoniste).getNom());					
					
					// D�placement de la pi�ce et mise � jour de l'interface plateau
					DeplacerJoueur homme_deplacement = new DeplacerJoueur(getNomJoueur(), setup.interfacePlateau,setup.plateau,setup.hommes.getPiece(numProtagoniste));
					homme_deplacement.deplacement();					
					afficherGrille(setup);
					monTour = false;
				} else {				
					// attente du tour et r�cup�ration du tour adverse
					attendreSonTour(adversaire, serveur);
					if (getClientjoueur().getResultatBataille() != "") {
						System.out.println(getClientjoueur().getResultatBataille().substring(0, getClientjoueur().getResultatBataille().length() -1));
					}else {
						System.out.println("R�cup�ration du coup adverse :");
					}	
					Piece pieceAdverse = getClientjoueur().getPiece();
					String[] deplacement = getClientjoueur().getDeplacement().split("%_%");					
					DeplacerJoueur deplacement_adversaire = new DeplacerJoueur(getNomJoueur(), setup.interfacePlateau, setup.plateau, pieceAdverse);
					deplacement_adversaire.deplacer(Integer.valueOf(deplacement[0]), Integer.valueOf(deplacement[1]), true);
					afficherGrille(setup);
					monTour = true;
				}
				
			} else {
				if (monTour) {	
					// Equipe Dragon
					System.out.println("Entrer le type de protagoniste dragon � contr�ler :");
					System.out.println(setup.dragons.afficherPieces());
					int numDragon = Clavier.entrerClavierInt()-1;
					System.out.println("Vous avez choisi le protagoniste "+ setup.dragons.getPiece(numDragon).getNom());
					
					DeplacerJoueur dragon_deplacement = new DeplacerJoueur(getNomJoueur(), setup.interfacePlateau,setup.plateau,setup.dragons.getPiece(numDragon));
					dragon_deplacement.deplacement();
					afficherGrille(setup);
					monTour = false;
					
				} else {
					// attente du tour et r�cup�ration du tour adverse
					attendreSonTour(adversaire, serveur);
					if (getClientjoueur().getResultatBataille() != "") {
						System.out.println(getClientjoueur().getResultatBataille().substring(0, getClientjoueur().getResultatBataille().length() -1));
					}else {
						System.out.println("R�cup�ration du coup adverse :");
					}	
					Piece pieceAdverse = getClientjoueur().getPiece();
					String[] deplacement = getClientjoueur().getDeplacement().split("%_%");					
					DeplacerJoueur deplacement_adversaire = new DeplacerJoueur(getNomJoueur(), setup.interfacePlateau, setup.plateau, pieceAdverse);
					deplacement_adversaire.deplacer(Integer.valueOf(deplacement[0]), Integer.valueOf(deplacement[1]), true);
					afficherGrille(setup);
					monTour = true;				
				}
			}
		}
		
		System.out.println("Choisir une option: ");
		System.out.println("1- Poursuivre le jeu ");
		System.out.println("2- Sauvegarder et quitter \n");
		option = Clavier.entrerClavierInt();

		if (option == 1)
		{//continue
		}
		if (option == 2){	
			// sauvegarder(setup);
			System.out.print("la sauvegarde est termin�e, vous allez quitter le jeu ");
		}	
	}

	/***
	 * Permet de joueur de chater en attendant son tour de jeu
	 * 
	 * @param adversaire : adresse du chat du joueur adverse
	 * @param serveur : serveur du jeu pour connaitre le tour du joueur
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
			System.out.println("2 - Envoyer un message � l'adversaire");
			
			choix = s.nextInt();
			if (choix == 2) {
				Scanner s2 = new Scanner(System.in);
				msg = s2.nextLine().trim();
		    	msg = "["+ getNomJoueur() + "] " + msg;		    		
		    	chatAdverse.sendMessage(msg);
			}else {
				Thread.sleep(100);
				if (!serveur.tourJoueur(getNomJoueur()) && getClientjoueur().getResultatBataille() == "") {
					System.out.println("Ce n'est pas � votre tour de jouer");
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
		if (setup.dragons.getNbEffectif() == 0) {
			System.out.println("Les hommes ont gagn� ");
		}
		if (setup.hommes.getNbEffectif() == 0) {
			System.out.println("Les dragons ont gagn� ");
		}
		
	}
}

