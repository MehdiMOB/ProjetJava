package creation;

import java.io.Serializable;

import graphique.InterfacePlateau;
import jeu.Plateau;
import protagonistes.Piece;

/**
 * Permet de saisir les coordonn�es de destination de la pi�ce, 
 * et de modifier les coordonn�es de la pi�ce dans le plateau et dans l'interface
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
	 * R�cup�ration de l'ensemble des param�tres 
	 * 
	 * 
	 * @param interfaceplateau, interface de jeu en cours
	 * @param plateau, plateau du jeu en cours
	 * @param piece, piece � d�placer
	 */
	public DeplacerJoueur(String nomJoueur, InterfacePlateau interfaceplateau, Plateau plateau ,Piece piece ) {
		this.nomJoueur = nomJoueur;
		this.interfaceplateau = interfaceplateau;
		this.plateau = plateau ;
		this.piece = piece;
	}
	
	/**
	 * Mise � jour du plateau et de l'interface, r�initialisation de la case de d�part
	 * et modification de la case d'arriv�e
	 */
	public void deplacement() {
		/* V�rification que les coordonn�es sont bien dans le plateau */
		int x = 0;
		int y = 0;
		int cord_x= 1 + plateau.getCoordonneeX(piece);
		int cord_y= 1 + plateau.getCoordonneeY(piece);
		do {
			System.out.println("Veuillez choisir le numero de d�placement suivant LIGNE");
			x = Clavier.entrerClavierInt();
			System.out.println("Veuillez choisir le numero de d�placement suivant COLONNE");
			y = Clavier.entrerClavierInt();
		} while ((x > 8) || (y > 8));
		
		deplacer(x,y, false);		
	}
	
	/**
	 * Permet de d�placer une pi�ce et de lib�rer la case initiale
	 * 
	 * @param x 
	 * @param y
	 * @param local : permet de diff�rencier le d�placement du joueur � transmettre via le serveur distant
	 * et le d�placement de l'adversaire � effectuer seulement en local
	 */
	public void deplacer(int x, int y, boolean local) {


		int cord_x= 1 + plateau.getCoordonneeX(piece);
		int cord_y= 1 + plateau.getCoordonneeY(piece);

		plateau.libererCase(cord_x-1, cord_y-1);
		
		// D�placement de la pi�ce et r�cup�ration de la pi�ce qui occupe la case
		Piece gagnant;
		
		if (local) {
			gagnant = plateau.occuperCase("", piece, x-1, y-1);
		}else {
			gagnant = plateau.occuperCase(nomJoueur, piece, x-1, y-1);
		}		
		
		// Mise � jour de l'interface plateau affich�e sur la console du joueur 
		if (gagnant == piece) {
			interfaceplateau.eliminer_Piece(piece, cord_x, cord_y);
			interfaceplateau.ajout_Piece(piece, x, y);
		}else {
			interfaceplateau.eliminer_Piece(piece, cord_x, cord_y);
		}
	}
}
