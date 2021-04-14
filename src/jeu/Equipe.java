package jeu;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import protagonistes.Dragon;
import protagonistes.Heros;
import protagonistes.Homme;
import protagonistes.Piece;

/**
 * Représentation de l'ensemble des pièces d'un joueur sous forme de liste
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */

public class Equipe implements Serializable {
	
	private String nom ;
	private List<Piece> combattants = new ArrayList<>();
	private int nbEffectif;
	
	public Equipe(String nom) {
		this.nom = nom;
		// L' effectif est initialisé à 0 au départ
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
	
	/**
	 *  Retourne la pièce à la position demandée ou la première cette position n'existe pas
	 * @param i
	 * @return
	 */	
	public Piece getPiece (int i) {
		if (i < combattants.size()) {
			return this.combattants.get(i);
		}else {
			return this.combattants.get(0);
		}
	}
	
	public int getNbEffectif() {
		return nbEffectif;
	}
	
	public boolean getSidePiece(Piece piece1,Piece piece2) {
		return (combattants.contains(piece1) && combattants.contains(piece2));
	}
	
	/**
	 * Méthode qui permet de représenter une pièce sous forme de chaine de caractères
	 * 
	 * @return type et nom de la pièce
	 */	
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
	 * Méthode qui permet de représenter l'état de l'équipe sous forme de chaine de caractères
	 * 
	 * @return chaine de caractères
	 */	
	public String toString() {
		return "";
	}		
}

	
