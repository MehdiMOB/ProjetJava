package creation;


import java.io.Serializable;

import tresor.Armure;
import tresor.StockArmures;

public class CreerArmures implements Serializable{

	private StockArmures stockArmures ;
		public void creerArmures() {
		stockArmures = new StockArmures();
		System.out.println("Entrer le nombre de protection de l'armure");
		int protection = Clavier.entrerClavierInt();
		stockArmures.ajouterArmure(new Armure(protection));
		System.out.println("l'arme a été créée");
	}
		public StockArmures getStockArmures() {
			return stockArmures;
		}
		public void setStockArmures(StockArmures stockArmures) {
			this.stockArmures = stockArmures;
		}
}
