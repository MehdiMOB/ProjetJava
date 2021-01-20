package jeu;

import java.io.Serializable;
import java.security.ProtectionDomain;
import java.util.Random;
import protagonistes.Piece;
import tresor.Arme;
import tresor.Armure;

public class Bataille implements Serializable {
	protected Piece A;
	protected Piece B;


public Bataille(Piece A , Piece B) {
	this.A=A;
	this.B=B;
	
}

public int derouler(Piece A , Piece B, Arme arme, Armure armure) {
	Random r = new Random();
	while (!A.estMort()&& !B.estMort()) {
	if (r.nextBoolean()) {
		if (B.getArme().getDegat() == 0) {
		A.subirAttaque(10);
		}else {
			A.subirAttaque(B.getArme().getDegat());
		}
	}
	else {
		if(A.getArme().getDegat() == 0) {
			B.subirAttaque(10);
		}else {
			B.subirAttaque(A.getArme().getDegat());	
		}
	}
	}
	if (A.estMort()) {
		B.renforceVie();
		B.gagnerTresor(arme,armure);
		
		return 2;
	}
	else {
		A.renforceVie();
		A.gagnerTresor(arme,armure);
		return 1;
	}
	
}







}
