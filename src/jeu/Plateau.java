package jeu;


import protagonistes.Piece;
import tresor.Arme;
import tresor.Armure;
import tresor.StockArmes;
import tresor.StockArmures;

import java.io.Serializable;

import protagonistes.Homme;


public class Plateau implements Serializable {
	
	
	private Case[][] lesCases;
	private Bataille bataille ;
	private Equipe equipeHommes ;
	private Equipe equipeDragons;
	private Arme arme;
	private Armure armure;
	
	
	public Plateau(Equipe equipeHommes,Equipe equipeDragons, Arme arme, Armure armure) {
		this.equipeDragons= equipeDragons;
		this.equipeHommes=equipeHommes;
		this.arme=arme;
		this.armure=armure;
		lesCases = new Case[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				lesCases[i][j] = new Case(i, j);
			}

		}
	}

	public String getSpecifique(int x, int y) {
		if (lesCases[x][y].contenir() == null) {
			return " Case Vide";
		}
		return "Le joueur "+lesCases[x][y].contenir().getType()+" a gagn�\n";

	}
	
	public Piece occuperCase(Piece occupant, int x , int y) {
//		int x = occupant.getCoordonnee_x();
//		int y = occupant.getCoordonnee_y();
		if (lesCases[x][y].isVide()) {
			lesCases[x][y].setOccupant(occupant);
			return occupant;
		} else {
			System.out.println("La case est d�j� occup�e par " + lesCases[x][y].contenir().getType());
			if (occupant.getEquipe() != lesCases[x][y].contenir().getEquipe()) {
				System.out.println("FIGHT !!");
				bataille = new Bataille(occupant,lesCases[x][y].contenir());
				int resultat = bataille.derouler(occupant, lesCases[x][y].contenir(), arme, armure);
				if (resultat == 1 ) {
					lesCases[x][y].contenir().getEquipe().supprimerPiece(lesCases[x][y].contenir());
					lesCases[x][y].setOccupant(occupant);
				}
				else {
					occupant.getEquipe().supprimerPiece(occupant);
					lesCases[x][y].setOccupant(lesCases[x][y].contenir());
				}
				
			}
			return lesCases[x][y].contenir();
		}
	}

	public void libererCase(int x, int y) {
		lesCases[x][y].libererOccupant();

	}
	

	public int getCoordonneeX(Piece piece) {
		
		for (int i = 0 ; i<8 ; i++) {
			for (int y = 0 ; y < 8 ; y ++) {
			if (lesCases[i][y].contenir()==piece) {
				return i;
			}
			}
		}
		return 0;
	}
	
	public int getCoordonneeY(Piece piece) {
		
		for (int x = 0 ; x<8 ; x++) {
			for (int i= 0 ; i<8 ; i++ ) {
			if (lesCases[x][i].contenir()==piece) {
				return i;
			}
			}
		}
		return 0;
	}
	
	
	
	
//	public void ajouterHommes(Equipe hommes) {
//		this.equipeHommes=hommes;
//		
//	}
//	public void ajouterDragons(Equipe dragons) {
//		
//		this.equipeDragons=dragons;
//	}
}
