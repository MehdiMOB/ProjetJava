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
 * Classe ex�cut�e cot� client qui lui permet de jouer en ligne � un jeu de type �checs
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
		 
		// Variables d'�change d'information entre le serveur et le client
		String camp, equipe = null;
		
		
		// Cr�ation d'un scanner sur l'interface du syst�me afin de r�cup�rer des informations saisies par l'utilisateur
		Scanner scan = new Scanner(System.in);
		
		// R�cup�ration de l'objet partag� par le serveur et ionstanciation
		Echec serveur = (Echec) Naming.lookup("rmi://localhost/Echec");

		
		// R�cup�ration du camp 
		System.out.println("Bonjour, veuillez saisir votre nom");
		String nomJoueur = scan.nextLine();
		
		camp = serveur.demarrerPartie(nomJoueur); 
		
		
		if (camp == "homme" || camp == "dragon"){
			equipe = creation.creerProtagoniste(camp);
		}else {
			System.err.println("Valeur de retour inattendue !");
		}
		
		// Envoie de l'�quipe cr��e au serveur
		int success = serveur.creationEquipe(equipe);
		
		if (success != 0) {
			System.err.println("Valeur de retour inattendue !");
		}
		// R�ception de l'�quipe adverse
		
		
		// Affichage du plateau
		
	
		
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
}
