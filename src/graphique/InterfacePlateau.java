package graphique;

import java.io.Serializable;

import protagonistes.Piece;

public class InterfacePlateau implements Serializable {
	
	private String plat="" ;

	/**
	 * Affichage d'un plateau vide sous forme de chaine de caract�res
	 * 
	 * @return chaine de caract�res repr�sentant le plateau vide
	 */
	public String creer_Plateau () {
		
		// Num�ro des colonnes sur l'axe des abscisses
		for (int i = 1 ; i < 9 ; i++) {			
			plat += " |" + i;
		}

		plat += " |\n---------------------------\n";
		
		// Ensemble des lignes pr�c�d�es de leur num�ro
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
	 * Permet l'affichage d'une pi�ce du jeu sous la forme d'une lettre
	 * 
	 * @param Pi�ce � repr�senter
	 * @return lettre repr�sentant la pi�ce � aficher
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
	 * Ajout d'une pi�ce sous forme de lettre � la repr�sentaion textuelle du plateau
	 * 
	 * @param piece
	 * @param row
	 * @param column
	 * @return plateau modifi� sous forme de chaine de caract�re
	 */
	public String ajout_Piece (Piece piece,int row , int column) {

		plat = plat.substring(0,55*row + 3*(column-1)+2  ) + lettre_Piece(piece) + plat.substring(55*row + 3*(column-1)+2 + 1) ;
		return plat;
	}

	/**
	 * Suppression d'une pi�ce � la repr�sentaion textuelle du plateau
	 * 
	 * @param piece
	 * @param row
	 * @param column
	 * @return plateau modifi� sous forme de chaine de caract�re
	 */
	public String eliminer_Piece (Piece piece, int row ,int column ) {

		plat = plat.substring(0,55*row + 3*(column-1)+2  ) + " " + plat.substring(55*row + 3*(column-1)+2 + 1) ;
		return plat ;

	}

	/**
	 * Renvoie le plateau de jeu sous forme de chaine de caract�res
	 * 
	 * @return chaine de caract�res repr�sentant le plateau
	 */
	public String getPlat() {
		return plat;
	}
	
}
