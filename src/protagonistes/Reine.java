package protagonistes;

import java.io.Serializable;

import tresor.Arme;
import tresor.Armure;

public class Reine extends Homme implements Serializable {
	private static int VIE = 150;

	public Reine(String nom) {
		super(nom);

		vie = VIE;
		type = 4;
	}
	
	public String parler(String phrase) {
		return getNom()+" : "+phrase+"\n";
	}

	public static int getVIE() {
		return VIE;
	}

	
}
