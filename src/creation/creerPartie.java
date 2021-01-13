package creation;

import java.io.Serializable;

import protagonistes.Piece;

public class creerPartie implements Serializable {
	
	private Piece stockPieces ;
	public creerPartie(Piece stockPieces) {
		this.stockPieces = stockPieces;
	}
	

}
