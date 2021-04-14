package combat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import protagonistes.Piece;


public class Equipe implements Serializable {
	
	private String nom ;
	private List<Piece> combattants = new ArrayList<>();
	private int nbEffectif;
	public Equipe(String nom) {
		this.nom=nom;
		//Effectif est initialisé au départ
		this.nbEffectif= 0;
	}

	public void ajoutPiece(Piece piece) {
		this.combattants.add(piece);
		this.nbEffectif++;
	}
	public int getNbEffectif() {
		return nbEffectif;
	}
	public String getNom() {
		return nom;
	}
	
}

