package graphique;

import java.io.Serializable;

import protagonistes.Homme;
import protagonistes.Piece;

public class InterfacePlateau implements Serializable {
	private String plat="" ;
		public InterfacePlateau() {
			
		}
public String creer_Plateau () {
		
		for (int i = 1 ; i < 9 ; i++) {
			
			plat =plat + " |"+i;
			}	
		plat = plat + " |\n---------------------------\n";
		
			for (int j =1 ; j < 9 ; j++ ) {
				
				plat = plat + j+"|";
				for (int k=1 ; k < 9 ; k++) {
					plat = plat + "  |";
				}
				plat = plat + "\n---------------------------\n";
			}
		return plat ;
	}

public String lettre_Piece(Piece p ) {
	
	if (p.getType().equals("Piece")) 
	{
		return "P";
	}
	else if (p.getType().equals("Dragon")) {
		
		return "D";
	}
	else {
		return "H";
		
	}
}

public String ajout_Piece (Piece piece,int row , int column) {
	
	plat = plat.substring(0,55*row + 3*(column-1)+2  ) + lettre_Piece(piece) + plat.substring(55*row + 3*(column-1)+2 + 1) ;
	return plat;
}


public String eliminer_Piece (Piece piece, int row ,int column ) {
	
	plat = plat.substring(0,55*row + 3*(column-1)+2  ) + " " + plat.substring(55*row + 3*(column-1)+2 + 1) ;
	return plat ;
	
	
}

public String getPlat() {
	return plat;
}
public static void main(String[] args) {
//	Piece p = new Piece("Nom",100);
//	System.out.println(creer_Plateau());
//	System.out.println(ajout_Piece(p,1,1));
//	System.out.println(ajout_Piece(p, 2, 2));
}
}
