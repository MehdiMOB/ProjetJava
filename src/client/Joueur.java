package client;

import java.rmi.Naming;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
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
		 
		// Variables d'échange d'information entre le serveur et le client
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
		System.out.println("Bonjour, veuillez saisir votre nom");
		nomJoueur = scan.nextLine();
		
		camp = serveur.demarrerPartie(nomJoueur, clientjoueur); 
		
		
		if (camp == "homme" || camp == "dragon"){
			equipeJoueur = creation.creerProtagoniste(camp);
		}else {
			System.err.println("Valeur de retour inattendue !");
		}
		
		// Envoie de l'équipe créée au serveur
		int success = serveur.creationEquipe(nomJoueur, equipeJoueur);
		
		if (success != 0) {
			System.err.println("Valeur de retour inattendue !");
		}
		
		// Mise en attente de réception de l'équipe adverse
		try {
			clientjoueur.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		equipeAdversaire = clientjoueur.getEquipeAdverse();
		
		// Affichage du plateau
		interfaceplateau = new InterfacePlateau();
		
		if (camp == "homme") {
			plateau = new Plateau(equipeJoueur, equipeAdversaire, null, null);
			setup = new Setup(interfaceplateau,plateau,equipeJoueur, equipeAdversaire);
		} else {
			plateau = new Plateau(equipeAdversaire, equipeJoueur, null, null);
			setup = new Setup(interfaceplateau, plateau, equipeAdversaire, equipeJoueur);
		}
		
		
		setup.positionner();
		setup.afficherSetup();
		
		boucle(setup);
	}		
		// Déroulement du jeu
		private static void boucle(Setup setup) {

			int choix=0;
			int option=0;
			while (true) {
			if (setup.hommes.getNbEffectif()== 0 )
			{
				
				System.out.println("Les dragons ont gagné !");
				break;
			}

			
				System.out.println("Entrer le type de protagoniste homme à contrôler :");
				System.out.println(setup.hommes.afficherPieces());
				int numProtagoniste = Clavier.entrerClavierInt()-1;
				System.out.println("Vous avez choisi le protagoniste "+ setup.hommes.getPiece(numProtagoniste).getNom());
				
				DeplacerJoueur homme_deplacement = new DeplacerJoueur(setup.interfacePlateau,setup.plateau,setup.hommes.getPiece(numProtagoniste));
				homme_deplacement.deplacement();
				
				setup.afficherSetup();
				
				if (setup.dragons.getNbEffectif() == 0) {
					System.out.println("Les hommes ont gagné ");
					break;
				}
				System.out.println("Entrer le type de protagoniste dragon à contrôler :");
				System.out.println(setup.dragons.afficherPieces());
				int numDragon = Clavier.entrerClavierInt()-1;
				System.out.println("Vous avez choisi le protagoniste "+ setup.dragons.getPiece(numDragon).getNom());
				DeplacerJoueur dragon_deplacement = new DeplacerJoueur(setup.interfacePlateau,setup.plateau,setup.dragons.getPiece(numDragon));
				dragon_deplacement.deplacement();
				setup.afficherSetup();
		
				
				System.out.println("Choisir une option: ");
				System.out.println("1- Poursuivre le jeu ");
				System.out.println("2- Sauvegarder et quitter \n");
				option = Clavier.entrerClavierInt();
				
				if (option == 1)
				{//continue
				}
				if (option == 2)
				{	
					sauvgarder(setup);
					System.out.print("la sauvegarde est terminé, vous allez quitté le jeu ");
					break;
					
				}
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

