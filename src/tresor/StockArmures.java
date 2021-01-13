package tresor;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class StockArmures implements Serializable {
	private List<Armure> armures = new ArrayList<>();
	
	public void ajouterArmure(Armure armure) {
		armures.add(armure);
	}
	public void enleverArmure(Armure armure) {
		armures.remove(armure);
	}
	public List<Armure> getArmures() {
		return armures;
	}
	
	
			
}