package protagonistes;

import java.io.Serializable;

import tresor.Arme;
import tresor.Armure;

public class Roi extends Homme implements Serializable{
	
	private static int VIE = 150;
	public Roi(String nom) {
		super(nom);
		vie = VIE;
		type = 3;
	}
	
	public String parler(String phrase) {
		return getNom()+" : "+phrase+"\n";
	}

	public static int getVIE() {
		return VIE;
	}
	
	
	
}
