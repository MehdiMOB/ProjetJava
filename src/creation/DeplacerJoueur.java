package creation;

import java.io.Serializable;

import graphique.InterfacePlateau;
import jeu.Plateau;
import protagonistes.Piece;

public class DeplacerJoueur implements Serializable {

	private Piece piece;
	private InterfacePlateau interfaceplateau;
	private Plateau plateau ;
	public DeplacerJoueur(InterfacePlateau interfaceplateau,Plateau plateau , Piece piece ) {
		this.interfaceplateau = interfaceplateau;
		this.plateau= plateau ;
		this.piece=piece;
	}
	
	public void deplacement () {
		/*While x < 8 and y < 8*/
		int x= 0;
		int y =0;
		int cord_x=1+plateau.getCoordonneeX(piece);
		int cord_y= 1+plateau.getCoordonneeY(piece);
		do {
		System.out.println("Veuillez choisir le numero de déplacement suivant LIGNE");
		x = Clavier.entrerClavierInt();
		System.out.println("Veuillez choisir le numero de déplacement suivant COLONNE");
		y = Clavier.entrerClavierInt();
		} 
		while ((x > 8) || (y > 8));
		
		
		//bloc try catch 
		Piece gagnant = plateau.occuperCase(piece, x - 1, y - 1);
		plateau.libererCase(cord_x-1, cord_y-1);
		
		if (gagnant == piece) {
			interfaceplateau.eliminer_Piece(piece, cord_x,cord_y );
			interfaceplateau.ajout_Piece(piece, x, y);
		}else {
			interfaceplateau.eliminer_Piece(piece, cord_x, cord_y);
		}
	}
	/*
	public void deplacementDragon () {
		
		System.out.println("Veuillez choisir le numero de LIGNE sur laquelle vous voudriez effectuer le déplacement");
		int x = Clavier.entrerClavierInt();
		System.out.println("Veuillez choisir le numero de COLONNE sur laquelle vous voudriez effectuer le déplacement");
		int y = Clavier.entrerClavierInt();
		
		
		int cord_x=1+plateau.getCoordonneeX(piece);
		int cord_y= 1+plateau.getCoordonneeY(piece);
		//bloc try catch 
		Piece gagnant = plateau.occuperCase(piece,  x - 1, y - 1);
		if (gagnant == piece)
		{
		interfaceplateau.eliminer_Piece(piece, cord_x,cord_y );
		interfaceplateau.ajout_Piece(piece, x, y);
		}
		else {
			interfaceplateau.eliminer_Piece(piece, cord_x, cord_y);
		}
	}*/
}
