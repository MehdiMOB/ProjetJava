package creation;

import java.io.Serializable;

import graphique.InterfacePlateau;
import jeu.Equipe;
import jeu.Plateau;

/**
 * Classe qui permet de positionner les diff�rentes pi�ces en jeu sur un plateau 
 * et d'afficher ce dernier � l'�cran
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
	 * Associe un plateau � une interface et � deux �quipes adverses
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
		
		// Positionnement des pi�ces de l'�quipe homme � partir du coin sup�rieur gauche
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
		
		// Positionnement des pi�ces de l'�quipe dragon � partir du coin inf�rieur droit
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
	 * Affichage � l'�cran de l'interface du plateau
	 */
	public void afficherSetup() {
		System.out.println(interfacePlateau.getPlat());
	}
	
}
