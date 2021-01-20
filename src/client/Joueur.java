package client;

import java.rmi.Naming;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Scanner;
import serveur.Echec;

/**
 * Classe exécutée coté client qui lui permet de jouer en ligne à un jeu de type échecs
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */

public class Joueur {	

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException{
		// Création d'un scanner sur l'interface du système afin de récupérer des informations saisies par l'utilisateur
		Scanner scan = new Scanner(System.in);
		// Récupération de l'objet partagé par le serveur 
		Echec jeu = (Echec) Naming.lookup("rmi://localhost/Echec");

		// Demande de l'action à réaliser
		System.out.println("Choisir une équipe");
		System.out.println("1 : Homme");
		System.out.println("2 : Dragon");		
		
		try {
			// Récupération des données utilisateur
			int typeEquipe = scan.nextInt();
			// Affichage du résultat de l'appel de la méthode distante
			System.out.println(jeu.creationEquipe(typeEquipe));			
			
		} catch (InputMismatchException e) {
			System.err.println("Erreur de type");
			scan.nextLine();
		}		
	}	
}
