package client;

import java.rmi.Naming;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

import commun.ClientJoueur;
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

	
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, InterruptedException{

		//
		CreerProtagoniste creation = new CreerProtagoniste();

		Equipe equipeJoueur = null;
		Equipe equipeAdversaire = null;
		ClientJoueurImpl clientjoueur = new ClientJoueurImpl(); 

		// Variables d'�change d'informations entre le serveur et le client
		String camp = null;
		String nomJoueur;
		Plateau plateau;
		InterfacePlateau interfaceplateau;
		Setup setup;

		// Cr�ation d'un scanner sur l'interface du syst�me afin de r�cup�rer des informations saisies par l'utilisateur
		Scanner scan = new Scanner(System.in);
		
		
		// R�cup�ration de l'objet partag� par le serveur et ionstanciation
		Echec serveur = (Echec) Naming.lookup("rmi://localhost:1099/Echec");


		// R�cup�ration du camp 
		System.out.println("Bonjour, veuillez saisir votre nom \n");
		nomJoueur = scan.nextLine();
		
		Chat server = new Chat(nomJoueur);	
    	
    	Naming.rebind("rmi://localhost:1099/"+nomJoueur, server); //

		camp = serveur.demarrerPartie(nomJoueur, clientjoueur, "rmi://localhost:1099/"+nomJoueur); 



		// Lancement du script de cr�ation d'�quipe et r�cup�ration de l'�quipe cr��e
		if (camp.equals("homme") || camp.equals("dragon")){
			equipeJoueur = creation.creerProtagoniste(camp);
		}else {
			System.err.println("Valeur de retour inattendue !");
		}

		// Si premier, mise en attente de la connexion de l'adversaire
		if (camp.equals("dragon")) {
			System.out.println("En recherche de joueurs ...");
			clientjoueur.attendreAdversaire();
		}


		// Envoie de l'�quipe cr��e au serveur quand adversaire connect�		
		String[] adversaire = serveur.creationEquipe(nomJoueur, equipeJoueur).split("%_%");
		ChatInterface chat = (ChatInterface) Naming.lookup(adversaire[0]);
		String nomAdversaire = adversaire[1];
		
		// Mise en attente de r�ception de l'�quipe adverse
		clientjoueur.attendreEquipeAdverse();


		equipeAdversaire = clientjoueur.getEquipeAdverse();

		// Affichage du plateau
		interfaceplateau = new InterfacePlateau();

		if (camp.equals("homme")) {
			plateau = new Plateau(equipeJoueur, equipeAdversaire, null, null);
			setup = new Setup(nomJoueur, interfaceplateau,plateau,equipeJoueur, equipeAdversaire);
		} else {
			plateau = new Plateau(equipeAdversaire, equipeJoueur, null, null);
			setup = new Setup(nomJoueur, interfaceplateau, plateau, equipeAdversaire, equipeJoueur);
		}


		setup.positionner();
		setup.afficherSetup();

		jouer(clientjoueur, nomJoueur, adversaire[1], setup, camp, (Chat) chat, serveur);
	}		

	// D�roulement du jeu
	private static void jouer(ClientJoueur joueur, String nomJoueur, String adversaire, Setup setup, String camp, Chat chat, Echec serveur) throws RemoteException, InterruptedException {

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
					DeplacerJoueur homme_deplacement = new DeplacerJoueur(nomJoueur, setup.interfacePlateau,setup.plateau,setup.hommes.getPiece(numProtagoniste));
					homme_deplacement.deplacement();					
	
					setup.afficherSetup();
	
					if (setup.dragons.getNbEffectif() == 0) {
						System.out.println("Les hommes ont gagn� ");
						break;
					}
					monTour = false;
				} else {
					// Mise en attente d'une action du joueur adverse
					System.out.println("attente");
					
					joueur.attendreTour();
					Scanner s = new Scanner(System.in);
					String msg;
					
					int choix;
					do {
					System.out.println("Que voulez-vous faire ?");
					System.out.println("1 - Jouer un tour");
					System.out.println("2 - Envoyer un message � l'adversaire");
					
					choix = s.nextInt();
					if (choix == 2) {
						msg=s.nextLine().trim();
				    	msg="["+adversaire+"] "+msg;		    		
			    		chat.sendMessage(msg);
					}else {
						Thread.sleep(100);
						if (!serveur.tourJoueur(nomJoueur)) {
							System.out.println("Ce n'est pas votre tour de jouer");
						}
					}
					}while (choix==1 && serveur.tourJoueur(nomJoueur));
					
					
					System.out.println("r�cup�ration coup adverse");
					// Boolean pouvoirJouer = joueur.getTour();
					Piece pieceAdverse = joueur.getPiece();
					String[] deplacement = joueur.getDeplacement().split("%_%");					
					DeplacerJoueur deplacement_adversaire = new DeplacerJoueur(nomJoueur, setup.interfacePlateau, setup.plateau, pieceAdverse);
					deplacement_adversaire.deplacer(Integer.valueOf(deplacement[0]), Integer.valueOf(deplacement[1]));
					setup.afficherSetup();
					monTour = true;
				}
				
			} else {
				if (monTour) {	
					// Equipe Dragon
					System.out.println("Entrer le type de protagoniste dragon � contr�ler :");
					System.out.println(setup.dragons.afficherPieces());
					int numDragon = Clavier.entrerClavierInt()-1;
					System.out.println("Vous avez choisi le protagoniste "+ setup.dragons.getPiece(numDragon).getNom());
					DeplacerJoueur dragon_deplacement = new DeplacerJoueur(nomJoueur, setup.interfacePlateau,setup.plateau,setup.dragons.getPiece(numDragon));
					dragon_deplacement.deplacement();
					setup.afficherSetup();
					monTour = false;
					
				} else {
					// Mise en attente d'une action du joueur adverse
					joueur.attendreTour();
					
					Scanner s = new Scanner(System.in);
					String msg;
					
					int choix;
					do {
					System.out.println("Que voulez-vous faire ?");
					System.out.println("1 - Jouer un tour");
					System.out.println("2 - Envoyer un message � l'adversaire");
					
					choix = s.nextInt();
					if (choix == 2) {
						msg=s.nextLine().trim();
				    	msg="["+adversaire+"] "+msg;		    		
			    		chat.sendMessage(msg);
					}else {
						Thread.sleep(100);
						if (!serveur.tourJoueur(nomJoueur)) {
							System.out.println("Ce n'est pas votre tour de jouer");
						}
					}
					}while (choix==1 && serveur.tourJoueur(nomJoueur));
					
					
					
					Piece pieceAdverse = joueur.getPiece();
					String[] deplacement = joueur.getDeplacement().split("%_%");					
					DeplacerJoueur deplacement_adversaire = new DeplacerJoueur(nomJoueur, setup.interfacePlateau, setup.plateau, pieceAdverse);
					deplacement_adversaire.deplacer(Integer.valueOf(deplacement[0]), Integer.valueOf(deplacement[1]));
					setup.afficherSetup();
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

	//		try {
	//			// R�cup�ration des donn�es utilisateur
	//			int typeEquipe = scan.nextInt();
	//			// Affichage du r�sultat de l'appel de la m�thode distante
	//			System.out.println(jeu.creationEquipe(typeEquipe));			
	//			
	//		} catch (InputMismatchException e) {
	//			System.err.println("Erreur de type");
	//			scan.nextLine();
	//		}		
}

