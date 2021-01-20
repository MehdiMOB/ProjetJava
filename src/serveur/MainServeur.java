package serveur;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Classe qui cr�e un jeu et le met � disposition via RMI
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */
public class MainServeur {

	public MainServeur() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException {
		// R�servation d'un port pour mettre a disposition l'objet jey 
		LocateRegistry.createRegistry(1099);
		// Instanciation de l'objet jeu poss�dant les m�thodexs � partager
		EchecImpl echec = new EchecImpl();
		// Liaison de l'objet jeu au port r�serv�
		Naming.bind("Echec", echec);		

	}

}
