package jeu;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import protagonistes.Dragon;
import protagonistes.Heros;
import protagonistes.Homme;
import protagonistes.Piece;


public class Equipe implements Serializable {
	
	private String nom ;
	private List<Piece> combattants = new ArrayList<>();
	private int nbEffectif;
	public Equipe(String nom) {
		this.nom=nom;
		//Effectif est initialis� � 0 au d�part
		this.nbEffectif= 0;
	}

	public void ajoutPiece(Piece piece) {
		this.combattants.add(piece);
		piece.rejoindreEquipe(this);
		this.nbEffectif++;
	}
	public void supprimerPiece(Piece piece) {
		combattants.remove(piece);
		piece.quitterEquipe();
		this.nbEffectif--;
	}
	public Piece getPiece (int i ) {
		return this.combattants.get(i);
	}
	
	
	public int getNbEffectif() {
		return nbEffectif;
	}
	public boolean getSidePiece(Piece piece1,Piece piece2) {
		return (combattants.contains(piece1) && combattants.contains(piece2));
	}
	
	public String afficherPieces() {
		String chaine = "";
		int i = 1;
		for (Piece piece : combattants) {
			chaine += "- " + i + " - "+piece.getType() +" "+ piece.getNom() + "\n";
			i++;
		}
		return chaine;
	}

	/**
	 * Methode qui permet de repr�senter l'�tat de l'�quipe sous forme de chaine de caract�res
	 * 
	 * @return chaine de caract�res
	 */	
	public String toString() {
		return "";
	}
	
		
}

	
