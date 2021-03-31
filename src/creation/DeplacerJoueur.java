package creation;

import java.io.Serializable;

import graphique.InterfacePlateau;
import jeu.Plateau;
import protagonistes.Piece;

/**
 * Permet de saisir les coordonnées de destination de la pièce, 
 * et de modifier les coordonnées de la pièce dans le plateau et dans l'interface
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */

public class DeplacerJoueur implements Serializable {

	private String nomJoueur;
	private Piece piece;
	private InterfacePlateau interfaceplateau;
	private Plateau plateau ;
	
	
	/**
	 * Récupération de l'ensemble des paramètres 
	 * 
	 * 
	 * @param interfaceplateau, interface de jeu en cours
	 * @param plateau, plateau du jeu en cours
	 * @param piece, piece à déplacer
	 */
	public DeplacerJoueur(String nomJoueur, InterfacePlateau interfaceplateau, Plateau plateau ,Piece piece ) {
		this.nomJoueur = nomJoueur;
		this.interfaceplateau = interfaceplateau;
		this.plateau = plateau ;
		this.piece = piece;
	}
	
	/**
	 * Mise à jour du plateau et de l'interface, réinitialisation de la case de départ
	 * et modification de la case d'arrivée
	 */
	public void deplacement() {
		/* Vérification que les coordonnées sont bien dans le plateau */
		int x = 0;
		int y = 0;
		int cord_x= 1 + plateau.getCoordonneeX(piece);
		int cord_y= 1 + plateau.getCoordonneeY(piece);
		do {
			System.out.println("Veuillez choisir le numero de déplacement suivant LIGNE");
			x = Clavier.entrerClavierInt();
			System.out.println("Veuillez choisir le numero de déplacement suivant COLONNE");
			y = Clavier.entrerClavierInt();
		} while ((x > 8) || (y > 8));
		
		deplacer(x,y, false);		
	}
	
	/**
	 * Permet de déplacer une pièce et de libérer la case initiale
	 * 
	 * @param x 
	 * @param y
	 * @param local : permet de différencier le déplacement du joueur à transmettre via le serveur distant
	 * et le déplacement de l'adversaire à effectuer seulement en local
	 */
	public void deplacer(int x, int y, boolean local) {


		int cord_x= 1 + plateau.getCoordonneeX(piece);
		int cord_y= 1 + plateau.getCoordonneeY(piece);

		plateau.libererCase(cord_x-1, cord_y-1);
		
		// Déplacement de la pièce et récupération de la pièce qui occupe la case
		Piece gagnant;
		
		if (local) {
			gagnant = plateau.occuperCase("", piece, x-1, y-1);
		}else {
			gagnant = plateau.occuperCase(nomJoueur, piece, x-1, y-1);
		}		
		
		// Mise à jour de l'interface plateau affichée sur la console du joueur 
		if (gagnant == piece) {
			interfaceplateau.eliminer_Piece(piece, cord_x, cord_y);
			interfaceplateau.ajout_Piece(piece, x, y);
		}else {
			interfaceplateau.eliminer_Piece(piece, cord_x, cord_y);
		}
	}
}
