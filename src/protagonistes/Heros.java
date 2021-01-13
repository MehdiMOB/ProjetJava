package protagonistes;

import java.io.Serializable;

import tresor.Arme;
import tresor.Armure;

public class Heros extends Homme implements Serializable {
	private static int VIE = 120;
	public Heros(String nom) {
		super(nom);
		
		vie = VIE;
		type = 5;
	}
	
	public String parler(String phrase) {
		return getNom()+" : "+phrase+"\n";
	}

	public static int getVIE() {
		return VIE;
	}
	
	
}
