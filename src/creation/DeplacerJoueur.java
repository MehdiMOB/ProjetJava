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
		
		deplacer(x,y);		
	}
	
	public void deplacer(int x, int y) {
		//bloc try catch 
		int cord_x= 1 + plateau.getCoordonneeX(piece);
		int cord_y= 1 + plateau.getCoordonneeY(piece);
		
		Piece gagnant = plateau.occuperCase(nomJoueur, piece, x, y);
		
		plateau.libererCase(cord_x-1, cord_y-1);
		
		if (gagnant == piece) {
			interfaceplateau.eliminer_Piece(piece, cord_x, cord_y);
			interfaceplateau.ajout_Piece(piece, x, y);
		}else {
			interfaceplateau.eliminer_Piece(piece, cord_x, cord_y);
		}
}
}
