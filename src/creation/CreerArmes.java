package creation;

import java.io.Serializable;

import tresor.Arme;
import tresor.StockArmes;

public class CreerArmes implements Serializable {
 private StockArmes stockArmes;
 
	public void creerArmes() {
		stockArmes = new StockArmes();
		System.out.println("Création des armes \n Veuillez entrer le nombre de dégats qu'elle pourra causer");
		int degat = Clavier.entrerClavierInt();
		stockArmes.ajouterArme(new Arme(degat));
		System.out.println("l'arme a été créée");
	}
	
	public StockArmes getStockArmes() {
		return stockArmes;
	}
	public void setStockArmes(StockArmes stockArmes) {
		this.stockArmes = stockArmes;
	}
	
}
