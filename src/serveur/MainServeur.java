package serveur;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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
		//System.setSecurityManager(new RMISecurityManager());
		Registry registry = null;
		// Réservation d'un port pour mettre a disposition l'objet jeu
		try {
			registry = LocateRegistry.getRegistry(1099);
		} catch (RemoteException e) {
			registry = LocateRegistry.createRegistry(1099);
		}
		// Instanciation de l'objet jeu possédant les méthodes à partager
		EchecImpl echec = new EchecImpl();
		// Liaison de l'objet jeu au port réservé
		try {
			Naming.bind("rmi://localhost:1099/Echec", echec);
		} catch (AlreadyBoundException e) {
			Naming.rebind("rmi://localhost:1099/Echec", echec);
		}	

	}

}
