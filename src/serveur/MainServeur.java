package serveur;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.naming.NameAlreadyBoundException;

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

	public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException, NameAlreadyBoundException {
		Registry registry = null;
		// Réservation d'un port pour mettre a disposition l'objet jeu
		
		registry = LocateRegistry.createRegistry(1099);

		// Instanciation de l'objet jeu possédant les méthodes à partager
		EchecImpl echec = new EchecImpl();
		try {
			Naming.bind("rmi://localhost:1099/Echec", echec);
			System.out.println("Interface partagée");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
