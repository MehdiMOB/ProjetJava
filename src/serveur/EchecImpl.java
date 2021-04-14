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
 * Jeu de plateau type échecs multijoueurs
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
	String tour = "";				// Tour du joueur en cours enregistré sous forme de chaine de caractères
	int joueurReady = 0; 			// Variable permettant de lancer la partie dès que plus d'un joueur est connecté 
	
	/**
	 * Instancie un nouveau jeu
	 * @throws RemoteException
	 */
	protected EchecImpl() throws RemoteException {
	}
		
	
	/**
	 * Méthode qui permet au joueur de démarrer une partie et retourne l'équipe qui lui a été affectée
	 *
	 * @param Nom du joueur sous forme de chaine de caractères qui permettra de l'identifier lors d'échange de messages 
	 * @param Interface de méthodes utilisables par le serveur pour mettre à jour coté client les variables du jeu
	 * @param Interface de méthodes utilisables par le joueur adverse pour échanger des messages dans la console
	 * @return camp qui lui est affecté sous forme de chaine de caractères
	 * @throws RemoteException
	 */
	public String demarrerPartie(String nomJoueur, ClientJoueur joueur, String chatJoueur) throws RemoteException {
		
		// Le premier joueur connecté a l'équipe dragon et se met en attente de l'adversaire
		if (dragon == "") {
			System.out.println("Connexion du joueur : " + nomJoueur);
			dragon = nomJoueur;
			joueurDragon = joueur;
			chatDragon = chatJoueur;
			return "dragon";
		}else {
		// Le deuxième joueur connecté a l'équipe des hommes et informe le premier joueur de son arrivée	
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
	 * Echange des équipes et du chat entre les joueurs
	 *  
	 * @param nom du joueur qui communique son équipe, ensemble des pièces de l'équipe sous forme de chaine de caractères
	 * @return adresse du chat adverse et nom de l'adversaire
	 * @throws RemoteException
	 */	
	public String creationEquipe(String nomJoueur, Equipe equipe) throws RemoteException {
		
		// Une fois que 2 joueurs sont connectés, la partie peut commencer et les tours vont alterner
		if(joueurReady > 0) {
			System.out.println("La partie peut commencer");
			setTour("homme");
		}
		// Echange des équipes, des chats et du nom des joueurs
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
	 * @param nomJoueur sous forme de chaine de carcatère identifiant le joueur
	 * @return booléen qui indique si c'est au joueur de jouer
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
	 * Permet d'échanger de déplacement d'une pièce vers une case vide et de laisser son tour à l'autre joueur
	 * 
	 * @param identifiant du joueur souhaitant déplacer une pièce
	 * @param pièce à déplacer
	 * @param coordonnées de la case de destination
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
	 * Permet de lancer un combat entre 2 pièces de jeu et de récompenser le gagnant
	 * Une fois le combat terminé, met à jour la pièce gagnante via l'interface ClientJoueur
	 * Puis renvoie le déroulé de la bataille et la case dans laquelle elle a eu lieu
	 * 
	 * @param Pièce attaquant
	 * @param Pièce défenseur
	 * @param Arme 
	 * @param Armure
	 * @param Coordonnées de la case où a lieu la bataille
	 * @return Déroulé de la bataille et pièce gagnante sous la forme d'une chaine de caractères
	 * @throws RemoteException
	 */	
	public String bataille(Piece A , Piece B, Arme arme, Armure armure, int x, int y) throws RemoteException {		
		System.out.println("La bataille commence ...");
		String histoire = "";					// Le déroulé de la bataille est enregistré sous forme 
												// de chaine de caractères pour être ensuite communiquée aux joueurs

		Random r = new Random();     			// Les coups sont échangés de manière aléatoire
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
		// Conclusions de la bataille et communication du vainqueur (attaquant ou défenseur)
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
		
		// Envoi du résultat de la bataille au défenseur, l'attaquant le récupère par retour de la méthode
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
		System.out.println("La bataille est terminée");
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
	 * @return le tour sous forme de chaine de caractères
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
	 * Permet au joueur de se déconnecter du serveur et au serveur de pouvoir accueillir un nouveau joueur
	 * 
	 * @param nomJoueur
	 * @throws RemoteException
	 */
	public void deconnexion(String nomJoueur) throws RemoteException{
		
		if (dragon.equals(nomJoueur)){
			dragon = "";
			joueurDragon = null;
			chatDragon = "";
			System.out.println(nomJoueur + " a quitté le serveur");
		}else {
			homme = "";
			joueurHomme = null;
			chatHomme = "";	
			System.out.println(nomJoueur + " a quitté le serveur");
		}		
	}
}
