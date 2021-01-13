package creation;

import java.io.Serializable;

import graphique.InterfacePlateau;
import jeu.Equipe;
import jeu.Plateau;

public class Setup implements Serializable {
	public Equipe hommes ;
	public Equipe dragons ;
	public InterfacePlateau interfacePlateau ;
	public Plateau plateau;
	
	public Setup(InterfacePlateau interfacePlateau,Plateau plateau,Equipe hommes , Equipe dragons) {
		this.interfacePlateau=interfacePlateau;
		this.hommes = hommes;
		this.dragons = dragons ;
		this.plateau= plateau; 
	}
	
	public void positionner() {
		//interfacePlateau = new InterfacePlateau();
		interfacePlateau.creer_Plateau();
		int row = 1;
		int column = 1 ;
		for (int i = 0 ; i <hommes.getNbEffectif() ; i++ )
		{
		interfacePlateau.ajout_Piece(hommes.getPiece(i), row, column) ;
		plateau.occuperCase(hommes.getPiece(i), row -1, column -1);
		column ++;
		if (column == 8) {
			column=1;
			row +=1;
		}
		
		}
		row = 8;
		column = 8;
		for (int i = 0 ; i <dragons.getNbEffectif() ; i++ )
		{
			interfacePlateau.ajout_Piece(dragons.getPiece(i), row, column) ;
			plateau.occuperCase(dragons.getPiece(i), row -1, column -1);
			column --;
			if (column == 1) {
				column=8;
				row -=1;
			}
		}
		
	}
	
	public void afficherSetup() {
		System.out.println(interfacePlateau.getPlat());
	}
	
}
