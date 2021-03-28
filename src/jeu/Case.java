package jeu;

import java.io.Serializable;

import protagonistes.Piece;

public class Case implements Serializable {

	int coordonnee_x;
	int coordonnee_y;
	private Piece occupant ;
	
	public Case(int coordonnee_x, int coordonnee_y) {
		this.coordonnee_x = coordonnee_x;
		this.coordonnee_y = coordonnee_y;
	}

	public int getCoordonnee_x() {
		return coordonnee_x;
	}

	public int getCoordonnee_y() {
		return coordonnee_y;
	}

	public Boolean contenir(Piece piece) {
		
		return !this.isVide() && piece.getNom().compareTo(occupant.getNom()) == 0 ;
	}
	
	public Piece getPiece() {
		return occupant;
	}
	
	public boolean isVide() {
		
		return (occupant == null );
	}
	public void setOccupant(Piece occupant) {
		this.occupant = occupant;
	}
	
	
	public void libererOccupant() {
		this.occupant = null; 
	}
}
