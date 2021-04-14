package serveur;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import commun.ClientJoueur;
import commun.Echec;
import jeu.Equipe;
import protagonistes.Piece;
import tresor.Arme;
import tresor.Armure;

/**
 * Jeu de plateau type �checs multijoueurs
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */

public class EchecImpl extends UnicastRemoteObject implements Echec {


	String dragon = "";				// Nom du joueur qui joue les dragons
	ClientJoueur joueurDragon;		// Interface du joueur qui joue les dragons
	String homme= "";				// Nom du joueur qui joue les hommes
	ClientJoueur joueurHomme;		// Interface du  joueur qui joue les hommes
	String chatDragon;				// Adresse des interfaces de chat des joueurs
	String chatHomme;
	String tour = "";				// Tour du joueur en cours enregistr� sous forme de chaine de caract�res
	int joueurReady = 0; 			// Variable permettant de lancer la partie d�s que plus d'un joueur est connect� 
	
	/**
	 * Instancie un nouveau jeu
	 * @throws RemoteException
	 */
	protected EchecImpl() throws RemoteException {
	}
		
	
	/**
	 * M�thode qui permet au joueur de d�marrer une partie et retourne l'�quipe qui lui a �t� affect�e
	 *
	 * @param Nom du joueur sous forme de chaine de caract�res qui permettra de l'identifier lors d'�change de messages 
	 * @param Interface de m�thodes utilisables par le serveur pour mettre � jour cot� client les variables du jeu
	 * @param Interface de m�thodes utilisables par le joueur adverse pour �changer des messages dans la console
	 * @return camp qui lui est affect� sous forme de chaine de caract�res
	 * @throws RemoteException
	 */
	public String demarrerPartie(String nomJoueur, ClientJoueur joueur, String chatJoueur) throws RemoteException {
		
		// Le premier joueur connect� a l'�quipe dragon et se met en attente de l'adversaire
		if (dragon == "") {
			System.out.println("Connexion du joueur : " + nomJoueur);
			dragon = nomJoueur;
			joueurDragon = joueur;
			chatDragon = chatJoueur;
			return "dragon";
		}else {
		// Le deuxi�me joueur connect� a l'�quipe des hommes et informe le premier joueur de son arriv�e	
			System.out.println("Connexion du joueur : " + nomJoueur);
			homme = nomJoueur;
			joueurHomme = joueur;
			chatHomme = chatJoueur;
			joueurDragon.setAdversairePresent(true);
			joueurHomme.setAdversairePresent(true);			
			return"homme";
		}
		
	}

	
	/**
	 * Echange des �quipes et du chat entre les joueurs
	 *  
	 * @param nom du joueur qui communique son �quipe, ensemble des pi�ces de l'�quipe sous forme de chaine de caract�res
	 * @return adresse du chat adverse et nom de l'adversaire
	 * @throws RemoteException
	 */	
	public String creationEquipe(String nomJoueur, Equipe equipe) throws RemoteException {
		
		// Une fois que 2 joueurs sont connect�s, la partie peut commencer et les tours vont alterner
		if(joueurReady > 0) {
			System.out.println("La partie peut commencer");
			setTour("homme");
		}
		// Echange des �quipes, des chats et du nom des joueurs
		if (dragon.equals(nomJoueur)){
			joueurReady++;
			joueurHomme.setEquipeAdverse(equipe);	
			return chatHomme + "%_%" + homme;
		}else{
			joueurReady++;
			joueurDragon.setEquipeAdverse(equipe);
			return  chatDragon + "%_%" + dragon; 
		}
	}
	
	/**
	 * Permet de connaitre si c'est au joueur de joueur
	 * 
	 * @param nomJoueur sous forme de chaine de carcat�re identifiant le joueur
	 * @return bool�en qui indique si c'est au joueur de jouer
	 * @throws RemoteException
	 */
	public boolean tourJoueur(String nomJoueur) throws RemoteException {
		
		if (dragon.equals(nomJoueur)){
			return getTour() == "dragon";
		}else{			
			return getTour() == "homme";
		}
	}
	
	/**
	 * Permet d'�changer de d�placement d'une pi�ce vers une case vide et de laisser son tour � l'autre joueur
	 * 
	 * @param identifiant du joueur souhaitant d�placer une pi�ce
	 * @param pi�ce � d�placer
	 * @param coordonn�es de la case de destination
	 * @throws RemoteException
	 */		
	public void deplacerPiece(String nomJoueur, Piece occupant, int x, int y) throws RemoteException {
		if(tourJoueur(nomJoueur)) {
			if (dragon.equals(nomJoueur)){				
				joueurHomme.setDeplacement(occupant, x + "%_%" + y);
				setTour("homme");							
			}else{				
				joueurDragon.setDeplacement(occupant, x + "%_%" + y);
				setTour("dragon");
			}
		}		
	}
	
	/**
	 * Permet de lancer un combat entre 2 pi�ces de jeu et de r�compenser le gagnant
	 * Une fois le combat termin�, met � jour la pi�ce gagnante via l'interface ClientJoueur
	 * Puis renvoie le d�roul� de la bataille et la case dans laquelle elle a eu lieu
	 * 
	 * @param Pi�ce attaquant
	 * @param Pi�ce d�fenseur
	 * @param Arme 
	 * @param Armure
	 * @param Coordonn�es de la case o� a lieu la bataille
	 * @return D�roul� de la bataille et pi�ce gagnante sous la forme d'une chaine de caract�res
	 * @throws RemoteException
	 */	
	public String bataille(Piece A , Piece B, Arme arme, Armure armure, int x, int y) throws RemoteException {		
		System.out.println("La bataille commence ...");
		String histoire = "";					// Le d�roul� de la bataille est enregistr� sous forme 
												// de chaine de caract�res pour �tre ensuite communiqu�e aux joueurs

		Random r = new Random();     			// Les coups sont �chang�s de mani�re al�atoire
		while (!A.estMort()&& !B.estMort()) {
			if (r.nextBoolean()) {
				if (B.getArme().getDegat() == 0) {
					histoire += A.subirAttaque(10);
				}else {
					histoire += A.subirAttaque(B.getArme().getDegat());
				}
			}else {
				if(A.getArme().getDegat() == 0) {
					histoire += B.subirAttaque(10);
				}else {
					histoire += B.subirAttaque(A.getArme().getDegat());	
				}
			}
		}
		Piece gagnant = null;
		Piece perdant = null;
		// Conclusions de la bataille et communication du vainqueur (attaquant ou d�fenseur)
		if (A.estMort()) {
			gagnant = B;
			perdant = A;
			histoire += B.renforceVie();
			histoire += B.gagnerTresor(arme, armure);
			histoire += "2";
		} else {
			gagnant = A;
			perdant = B;
			histoire += A.renforceVie();
			histoire += A.gagnerTresor(arme, armure);			
			histoire += "1";
		}
		
		// Envoi du r�sultat de la bataille au d�fenseur, l'attaquant le r�cup�re par retour de la m�thode
		try {
			if (getTour() == "dragon") {
				joueurHomme.setResultatBataille(histoire, gagnant, perdant, x + "%_%" + y);
				setTour("homme");
			}else {
				joueurDragon.setResultatBataille(histoire, gagnant, perdant, x + "%_%" + y);
				setTour("dragon");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		System.out.println("La bataille est termin�e");
		return histoire;
	}


	@Override
	public void sauvegarderPartie() throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void restaurerPartie() throws RemoteException {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Permet de connaitre le tour en cours et donc le joueur
	 * @return le tour sous forme de chaine de caract�res
	 */
	private String getTour() {
		return tour;
	}

	/**
	 * Permet de changer le tour de jeu
	 * @param tour
	 */
	private void setTour(String tour) {
		this.tour = tour;
	}

	
	/**
	 * Permet au joueur de se d�connecter du serveur et au serveur de pouvoir accueillir un nouveau joueur
	 * 
	 * @param nomJoueur
	 * @throws RemoteException
	 */
	public void deconnexion(String nomJoueur) throws RemoteException{
		
		if (dragon.equals(nomJoueur)){
			dragon = "";
			joueurDragon = null;
			chatDragon = "";
			System.out.println(nomJoueur + " a quitt� le serveur");
		}else {
			homme = "";
			joueurHomme = null;
			chatHomme = "";	
			System.out.println(nomJoueur + " a quitt� le serveur");
		}		
	}
}
