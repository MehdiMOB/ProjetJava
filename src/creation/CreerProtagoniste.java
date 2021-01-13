package creation;

import java.io.Serializable;

import jeu.Equipe;
import protagonistes.*;

public class CreerProtagoniste implements Serializable {
	private Equipe hommes ;
	private Equipe dragons ;
	
	public CreerProtagoniste(Equipe hommes , Equipe dragons) {
		this.hommes=hommes;
		this.dragons=dragons;
	}
	
	public void creerProtagoniste() {
		System.out.println("Cr�ation de l'�quipe Homme : \n");
		int i = 1 ;
		while (i<5 && i > 0)
		{	
			System.out.println("choisir le type d'homme que vous voulez cr�er :\n1-Roi\n2-Heros\n3-Reine\n4-Homme\nAutre-OK");
			i = Clavier.entrerClavierInt();
			switch (i) {
				case 1 : creerRoi(); break;
				case 2 : creerHeros(); break;
				case 3 : creerReine();break;
				case 4 : creerHomme(); break;
				default : break;
			}
		}
		
		
		System.out.println("Cr�ation de l'�quipe Dragon : \n");
		i = 1;
		while (i==1)
		{	System.out.println("1-Ajouter un dragon\nAutre-OK");
			i = Clavier.entrerClavierInt();
			if (i==1 ) {
				creerDragon();
			}
			
		}
		
	}

	private void creerRoi() {
		System.out.println("Entrer le nom du roi : \n");
		String nom = Clavier.entrerClavierString();
		hommes.ajoutPiece(new Roi(nom));
		
	}
	
	private void creerReine() {
		System.out.println("Entrer le nom de la reine : \n");
		String nom = Clavier.entrerClavierString();
		hommes.ajoutPiece(new Reine(nom));
		
	}
	
	private void creerDragon() {
		System.out.println("Entrer le nom du dragon : \n");
		String nom = Clavier.entrerClavierString();
		dragons.ajoutPiece(new Dragon(nom));
		
	}
	
	private void creerHeros() {
		System.out.println("Entrer le nom du h�ros : \n");
		String nom = Clavier.entrerClavierString();
		hommes.ajoutPiece(new Heros(nom));
		
	}

	private void creerHomme() {
		System.out.println("Entrer le nom de l'homme : \n");
		String nom = Clavier.entrerClavierString();
		hommes.ajoutPiece(new Homme(nom));
		
	}
}
