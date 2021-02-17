 package client;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Classe qui crée un serveur Client pour mettre à disposition du jeu des services via RMI
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */

public class MainClient {

	public MainClient() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException {
		
		// Réservation d'un port pour mettre a disposition l'objet clientjoueur
		LocateRegistry.createRegistry(1098);
		
		// Instanciation de l'objet clientjoueur possédant les méthodes à partager
		ClientJoueurImpl joueur = new ClientJoueurImpl();
		
		// Liaison de l'objet jeu au port réservé
		Naming.bind("joueur", joueur);
	}

}
