package creation;

import java.io.Serializable;
import java.util.Random;

public class NombreAleatoire implements Serializable {
	
	
	
	public static int nombreAleatoire() {
		// g�n�ration d'un nombre >= 0 et < 100
	    Random r = new Random();
	    int n = r.nextInt(100);
	    return n;
	}
}
