package creation;

import java.io.Serializable;

import graphique.InterfacePlateau;
import jeu.Equipe;
import jeu.Plateau;

/**
 * Classe qui permet de positionner les différentes pièces en jeu sur un plateau 
 * et d'afficher ce dernier à l'écran
 *  
 * @author ACHIBANE, GENET, KHERFELLAH, PONS
 *
 */

public class Setup implements Serializable {
	
	public Equipe hommes ;
	public Equipe dragons ;
	public InterfacePlateau interfacePlateau ;
	public Plateau plateau;
	
	
	/**
	 * Associe un plateau à une interface et à deux équipes adverses
	 * 
	 * @param interfacePlateau
	 * @param plateau
	 * @param hommes
	 * @param dragons
	 */
	
	public Setup(InterfacePlateau interfacePlateau, Plateau plateau, Equipe hommes , Equipe dragons) {
		this.interfacePlateau=interfacePlateau;
		this.hommes = hommes;
		this.dragons = dragons ;
		this.plateau= plateau; 
	}
	
	
	public void positionner() {
		interfacePlateau.creer_Plateau();
		
		// Positionnement des pièces de l'équipe homme à partir du coin supérieur gauche
		int row = 1;
		int column = 1 ;
		
		for (int i = 0 ; i <hommes.getNbEffectif() ; i++ ) {
			interfacePlateau.ajout_Piece(hommes.getPiece(i), row, column) ;
			plateau.occuperCase(hommes.getPiece(i), row -1, column -1);
			column ++;
			if (column == 8) {
				column=1;
				row +=1;
			}
			
		}
		
		// Positionnement des pièces de l'équipe dragon à partir du coin inférieur droit
		row = 8;
		column = 8;
		for (int i = 0 ; i <dragons.getNbEffectif() ; i++) {
			interfacePlateau.ajout_Piece(dragons.getPiece(i), row, column) ;
			plateau.occuperCase(dragons.getPiece(i), row -1, column -1);
			column --;
			if (column == 1) {
				column=8;
				row -=1;
			}
		}
		
	}
	
	/**
	 * Affichage à l'écran de l'interface du plateau
	 */
	public void afficherSetup() {
		System.out.println(interfacePlateau.getPlat());
	}
	
}
