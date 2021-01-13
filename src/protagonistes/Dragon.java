package protagonistes;

import java.io.Serializable;

import tresor.Arme;
import tresor.Armure;

public class Dragon extends Piece implements Serializable {

	private String nom;
	private static int VIE = 100;

	public Dragon(String nom) {
		super(nom, 100);
		this.nom = nom;
		type =2;
	}
	
	
	public String getNom() {
		return nom;
	}



	public void setNom(String nom) {
		this.nom = nom;
	}



	public String parler(String phrase) {
		return getNom()+" : "+phrase+"\n";
	}


	public static int getVIE() {
		// TODO Auto-generated method stub
		return VIE;
	}

	

}
