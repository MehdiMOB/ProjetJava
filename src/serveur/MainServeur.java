package serveur;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Classe qui crée un jeu et le met à disposition via RMI
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */
public class MainServeur {

	public MainServeur() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException {
		// Réservation d'un port pour mettre a disposition l'objet jey 
		LocateRegistry.createRegistry(1099);
		// Instanciation de l'objet jeu possédant les méthodexs à partager
		EchecImpl echec = new EchecImpl();
		// Liaison de l'objet jeu au port réservé
		Naming.bind("Echec", echec);		

	}

}
