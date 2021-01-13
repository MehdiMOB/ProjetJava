package protagonistes;

import java.io.Serializable;

import tresor.Arme;
import tresor.Armure;

public class Homme extends Piece implements Serializable {
	protected String nom;
	private static int VIE = 100;

		
	public Homme(String nom) {
		super(nom, 100);
		this.nom = nom;
		vie = VIE;
		type = 1;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public static int getVIE() {
		return VIE;
	}
	
	
	
}
