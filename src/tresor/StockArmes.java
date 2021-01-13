package tresor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class StockArmes implements Serializable {
	private List<Arme> armes = new ArrayList<>();
	
	public void ajouterArme(Arme arme) {
		armes.add(arme);
	}
	public void enleverArme(Arme arme) {
		armes.remove(arme);
	}
	
	public List<Arme> getArmes() {
		return armes;
	}
	
	
			
}
