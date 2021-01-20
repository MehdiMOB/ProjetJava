package client;

import java.rmi.Naming;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Scanner;
import serveur.Echec;

/**
 * Classe ex�cut�e cot� client qui lui permet de jouer en ligne � un jeu de type �checs
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */

public class Joueur {	

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException{
		// Cr�ation d'un scanner sur l'interface du syst�me afin de r�cup�rer des informations saisies par l'utilisateur
		Scanner scan = new Scanner(System.in);
		// R�cup�ration de l'objet partag� par le serveur 
		Echec jeu = (Echec) Naming.lookup("rmi://localhost/Echec");

		// Demande de l'action � r�aliser
		System.out.println("Choisir une �quipe");
		System.out.println("1 : Homme");
		System.out.println("2 : Dragon");		
		
		try {
			// R�cup�ration des donn�es utilisateur
			int typeEquipe = scan.nextInt();
			// Affichage du r�sultat de l'appel de la m�thode distante
			System.out.println(jeu.creationEquipe(typeEquipe));			
			
		} catch (InputMismatchException e) {
			System.err.println("Erreur de type");
			scan.nextLine();
		}		
	}	
}
