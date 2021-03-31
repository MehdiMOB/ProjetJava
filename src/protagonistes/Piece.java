package protagonistes;

import java.io.Serializable;
import java.util.Random;

import creation.NombreAleatoire;
import jeu.Equipe;
import tresor.Arme;
import tresor.Armure;

public class Piece implements Serializable {
	
	protected int vie;
	protected int type; 
	protected Equipe equipe;
	protected String nom;
	protected Armure armure;
	protected Arme arme;
	
	
	
	public Piece(String nom, int vie ) {
	
		this.vie = vie;
		this.nom = nom;
		arme = new Arme(0);
		armure = new Armure(0);
	}

	public void restaurer_vie() {
	}
	
	public String getType() {
		
		switch (type)
		{
			case 1 : return "Homme"; 
			case 2 : return "Dragon";
			case 3 : return "Roi"; 
			case 4 : return "Reine";
			case 5 : return "Heros";
			default : return "Piece";
		}
		
	}
	public void rejoindreEquipe(Equipe equipe) {
		
		this.equipe = equipe;
	}
	
	public Equipe getEquipe() {
		return equipe;
	}
	
	public String subirAttaque(int degat) {
		
		if(this.armure.getProtection() > degat ) {
			this.armure.setProtection(this.armure.getProtection() - degat);
		}else {
			this.vie = this.vie - degat + this.armure.getProtection() ;	
		}
		if (vie < 0) {
			vie = 0;
		}
		
		return getType()+ " a subit une attaque !\n Vie restante : " + vie + "\n";
		
		
	}
	
	public String renforceVie() {
		if(type == 1) {
			vie = Homme.getVIE();
		}else if(type == 2) {
			vie = Dragon.getVIE();
		}else if(type == 3) {
			vie = Roi.getVIE();
		}else if(type == 4) {
			vie = Reine.getVIE();
		}else if(type == 5) {
			vie = Heros.getVIE();
		}
			
		return "le joueur " + getType() + " a gagné " + "et retrouve sa vie de " + vie + "\n";
	}
	
	public void quitterEquipe() {
		this.equipe= null;
	}
	
	public boolean estMort() {
		return (vie <= 0);
	}
	
	public String ajouterArmure(Armure armure) {
		this.armure.setProtection(armure.getProtection());
		return "le joueur " + getType() + " possède desormais une armure de " + this.armure.getProtection() + " de points de protection \n";
	}
	
	public String ajouteArme(Arme arme) {

		this.arme.setDegat(arme.getDegat());
		return "le joueur " + getType() + " possède desormais une arme, qui fera subir des degats de " + this.arme.getDegat() + " à ses prochains adversaires \n";
	}

	public String gagnerTresor(Arme arme,Armure armure) {
		if(NombreAleatoire.nombreAleatoire() < 50) {
			return ajouterArmure(armure);
		}else {
			return ajouteArme(arme);
		}
	}
	
	public Arme getArme() {
		return arme;
	}
	
	public Armure getArmure() {
		return armure;
	}
	
	public String getNom() {
		return nom;
	}
}
