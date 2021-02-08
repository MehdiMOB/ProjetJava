package client;

import java.rmi.Naming;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Scanner;

import creation.CreerProtagoniste;
import serveur.Echec;

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

		 Equipe joueur;
		 Equipe adversaire;
		 
		// Variables d'échange d'information entre le serveur et le client
		String camp, equipe = null;
		
		
		// Création d'un scanner sur l'interface du système afin de récupérer des informations saisies par l'utilisateur
		Scanner scan = new Scanner(System.in);
		
		// Récupération de l'objet partagé par le serveur et ionstanciation
		Echec serveur = (Echec) Naming.lookup("rmi://localhost/Echec");

		
		// Récupération du camp 
		System.out.println("Bonjour, veuillez saisir votre nom");
		String nomJoueur = scan.nextLine();
		
		camp = serveur.demarrerPartie(nomJoueur); 
		
		
		if (camp == "homme" || camp == "dragon"){
			equipe = creation.creerProtagoniste(camp);
		}else {
			System.err.println("Valeur de retour inattendue !");
		}
		
		// Envoie de l'équipe créée au serveur
		int success = serveur.creationEquipe(equipe);
		
		if (success != 0) {
			System.err.println("Valeur de retour inattendue !");
		}
		// Réception de l'équipe adverse
		
		
		// Affichage du plateau
		
	
		
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
}
