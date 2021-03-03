package client;

import java.rmi.Naming;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import commun.Echec;
import creation.Clavier;
import creation.CreerProtagoniste;
import creation.DeplacerJoueur;
import creation.Setup;
import graphique.InterfacePlateau;
import jeu.Equipe;
import jeu.Plateau;

/**
 * Classe exécutée coté client qui lui permet de jouer en ligne à un jeu de type échecs
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */

public class Joueur {	

	
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException{

		//
		CreerProtagoniste creation = new CreerProtagoniste();

		Equipe equipeJoueur = null;
		Equipe equipeAdversaire = null;
		ClientJoueurImpl clientjoueur = new ClientJoueurImpl(); 

		// Variables d'échange d'informations entre le serveur et le client
		String camp = null;
		String nomJoueur;
		Plateau plateau;
		InterfacePlateau interfaceplateau;
		Setup setup;

		// Création d'un scanner sur l'interface du système afin de récupérer des informations saisies par l'utilisateur
		Scanner scan = new Scanner(System.in);

		// Récupération de l'objet partagé par le serveur et ionstanciation
		Echec serveur = (Echec) Naming.lookup("rmi://localhost/Echec");


		// Récupération du camp 
		System.out.println("Bonjour, veuillez saisir votre nom \n");
		nomJoueur = scan.nextLine();

		camp = serveur.demarrerPartie(nomJoueur, clientjoueur); 



		// Lancement du script de création d'équipe et récupération de l'équipe créée
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


		// Envoie de l'équipe créée au serveur quand adversaire connecté		
		int success = serveur.creationEquipe(nomJoueur, equipeJoueur);

		Boolean test = success != 0;

		if (success != 0) {
			System.err.println("Valeur de retour inattendue !");
		}

		// Mise en attente de réception de l'équipe adverse
		clientjoueur.attendreEquipeAdverse();


		equipeAdversaire = clientjoueur.getEquipeAdverse();

		// Affichage du plateau
		interfaceplateau = new InterfacePlateau();

		if (camp.equals("homme")) {
			plateau = new Plateau(equipeJoueur, equipeAdversaire, null, null);
			setup = new Setup(interfaceplateau,plateau,equipeJoueur, equipeAdversaire);
		} else {
			plateau = new Plateau(equipeAdversaire, equipeJoueur, null, null);
			setup = new Setup(interfaceplateau, plateau, equipeAdversaire, equipeJoueur);
		}


		setup.positionner();
		setup.afficherSetup();

		jouer(setup, camp);
	}		

	// Déroulement du jeu
	private static void jouer(Setup setup, String camp) {

		int choix=0;
		int option=0;
		boolean monTour = camp.equals("homme");
		
		while (true) {
			if (setup.hommes.getNbEffectif() == 0 ){

				System.out.println("Les dragons ont gagné !");
				break;
			}

			if (camp.equals("homme")) {
				if (monTour) {
					// Choix pièce à déplacer
					System.out.println("Entrer le type de protagoniste homme à contrôler :");
					System.out.println(setup.hommes.afficherPieces());
					int numProtagoniste = Clavier.entrerClavierInt()-1;
					System.out.println("Vous avez choisi le protagoniste "+ setup.hommes.getPiece(numProtagoniste).getNom());
					
					// Déplacement
					DeplacerJoueur homme_deplacement = new DeplacerJoueur(setup.interfacePlateau,setup.plateau,setup.hommes.getPiece(numProtagoniste));
					homme_deplacement.deplacement();
					
	
					setup.afficherSetup();
	
					if (setup.dragons.getNbEffectif() == 0) {
						System.out.println("Les hommes ont gagné ");
						break;
					}
					monTour = false;
				}	
				
			} else {
				if (monTour) {	
					// Equipe Dragon
					System.out.println("Entrer le type de protagoniste dragon à contrôler :");
					System.out.println(setup.dragons.afficherPieces());
					int numDragon = Clavier.entrerClavierInt()-1;
					System.out.println("Vous avez choisi le protagoniste "+ setup.dragons.getPiece(numDragon).getNom());
					DeplacerJoueur dragon_deplacement = new DeplacerJoueur(setup.interfacePlateau,setup.plateau,setup.dragons.getPiece(numDragon));
					dragon_deplacement.deplacement();
					setup.afficherSetup();
					monTour = false;
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
			System.out.print("la sauvegarde est terminée, vous allez quitter le jeu ");
		}	
	}

	//		try {
	//			// Récupération des données utilisateur
	//			int typeEquipe = scan.nextInt();
	//			// Affichage du résultat de l'appel de la méthode distante
	//			System.out.println(jeu.creationEquipe(typeEquipe));			
	//			
	//		} catch (InputMismatchException e) {
	//			System.err.println("Erreur de type");
	//			scan.nextLine();
	//		}		
}

