 package client;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Classe qui cr�e un serveur Client pour mettre � disposition du jeu des services via RMI
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */

public class MainClient {

	public MainClient() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException {
		
		// R�servation d'un port pour mettre a disposition l'objet clientjoueur
		LocateRegistry.createRegistry(1098);
		
		// Instanciation de l'objet clientjoueur poss�dant les m�thodes � partager
		ClientJoueurImpl joueur = new ClientJoueurImpl();
		
		// Liaison de l'objet jeu au port r�serv�
		Naming.bind("joueur", joueur);
	}

}
