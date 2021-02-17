package graphique;

import java.io.Serializable;

import protagonistes.Piece;

public class InterfacePlateau implements Serializable {
	
	private String plat="" ;

	/**
	 * Affichage d'un plateau vide sous forme de chaine de caractères
	 * 
	 * @return chaine de caractères représentant le plateau vide
	 */
	public String creer_Plateau () {
		
		// Numéro des colonnes sur l'axe des abscisses
		for (int i = 1 ; i < 9 ; i++) {			
			plat += " |" + i;
		}

		plat += " |\n---------------------------\n";
		
		// Ensemble des lignes précédées de leur numéro
		for (int j =1 ; j < 9 ; j++ ) {
			plat += j + "|";
			
			for (int k=1 ; k < 9 ; k++) {
				plat +=  "  |";
			}
			plat += "\n---------------------------\n";
		}
		return plat ;
	}

	/**
	 * Permet l'affichage d'une pièce du jeu sous la forme d'une lettre
	 * 
	 * @param Pièce à représenter
	 * @return lettre représentant la pièce à aficher
	 */
	public String lettre_Piece(Piece p ) {

		if (p.getType().equals("Piece")) {
			
			return "P";
			
		} else if (p.getType().equals("Dragon")) {

			return "D";
			
		} else {
			
			return "H";
		}
	}

	/**
	 * Ajout d'une pièce sous forme de lettre à la représentaion textuelle du plateau
	 * 
	 * @param piece
	 * @param row
	 * @param column
	 * @return plateau modifié sous forme de chaine de caractère
	 */
	public String ajout_Piece (Piece piece,int row , int column) {

		plat = plat.substring(0,55*row + 3*(column-1)+2  ) + lettre_Piece(piece) + plat.substring(55*row + 3*(column-1)+2 + 1) ;
		return plat;
	}

	/**
	 * Suppression d'une pièce à la représentaion textuelle du plateau
	 * 
	 * @param piece
	 * @param row
	 * @param column
	 * @return plateau modifié sous forme de chaine de caractère
	 */
	public String eliminer_Piece (Piece piece, int row ,int column ) {

		plat = plat.substring(0,55*row + 3*(column-1)+2  ) + " " + plat.substring(55*row + 3*(column-1)+2 + 1) ;
		return plat ;

	}

	/**
	 * Renvoie le plateau de jeu sous forme de chaine de caractères
	 * 
	 * @return chaine de caractères représentant le plateau
	 */
	public String getPlat() {
		return plat;
	}
	
}
