package jeu;

import protagonistes.Piece;
import tresor.Arme;
import tresor.Armure;
import tresor.StockArmes;
import tresor.StockArmures;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import commun.Echec;
import protagonistes.Homme;


public class Plateau implements Serializable {
	
	
	private Case[][] lesCases;
	private Bataille bataille ;
	private Equipe equipeHommes ;
	private Equipe equipeDragons;
	private Arme arme;
	private Armure armure;
	Echec serveur;
	
	
	public Plateau(Equipe equipeHommes,Equipe equipeDragons, Arme arme, Armure armure) throws MalformedURLException, RemoteException, NotBoundException {
		this.serveur = (Echec) Naming.lookup("rmi://localhost/Echec");
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
		if (lesCases[x][y].getPiece() == null) {
			return " Case Vide";
		}
		return "Le joueur "+lesCases[x][y].getPiece().getType()+" a gagné\n";

	}
	
	
	 /**
	  * Methode qui vérifie si la case de destination est vide, occupée par un allié ou un adversaire
	  * Dans ce dernier cas elle déclenche un combat
	  * 
	  * 
	  * @param nomJoueur : permer de s'identifier auprès du serveur
	  * @param occupant : pièce à positionner dans la case
	  * @param x
	  * @param y
	  * @return Piece occupant la case
	  */
	
	public Piece occuperCase(String nomJoueur, Piece occupant, int x , int y) {
		// Cas de déplacement sipmle vers une case vide 		
		if (lesCases[x][y].isVide()) {
			
			lesCases[x][y].setOccupant(occupant);
			// Envoi du déplacement au joueur adverse uniquement si c'est son tour de jeu, sinon c'est un déplacement sur le plateau local
			if (nomJoueur != "") {
					try {
						this.serveur.deplacerPiece(nomJoueur, occupant, x+1, y+1);						
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			
			return occupant;
			
		} else {
			// Cas où la case de destination est occupée
			System.out.println("La case est déjà occupée par " + lesCases[x][y].getPiece().getType());
			// Si la case est occupée par un adversaire il y a combat
			if (occupant.getEquipe() != lesCases[x][y].getPiece().getEquipe()) {
				System.out.println("FIGHT !!");			
				Arme arme = new Arme(10);
				Armure armure = new Armure(5);
				String resultatBataille = "";
				// Récupération du détail de la bataille calculée par le serveur et affichage
				try {
					resultatBataille = serveur.bataille(occupant, lesCases[x][y].getPiece(), arme, armure, x+1, y+1);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				System.out.println(resultatBataille.substring(0, resultatBataille.length() -1));
				
				// Récupération du gagnant
				String resultat = resultatBataille.substring(resultatBataille.length() -1);
				// Suppression de la pièce perdante de son équipe et position de la pièce gagnante dans la case
				if (resultat.equals("1")) {
					lesCases[x][y].getPiece().getEquipe().supprimerPiece(lesCases[x][y].getPiece());
					lesCases[x][y].setOccupant(occupant);
				} else {
					occupant.getEquipe().supprimerPiece(occupant);
					lesCases[x][y].setOccupant(lesCases[x][y].getPiece());
				}				
			}
			// Renvoi l'occupant de la case
			return lesCases[x][y].getPiece();
		}
	}

	public void libererCase(int x, int y) {
		lesCases[x][y].libererOccupant();

	}
	

	public int getCoordonneeX(Piece piece) {

		for (int i = 0 ; i < 8 ; i ++) {
			for (int y = 0 ; y < 8 ; y ++) {
				if (lesCases[i][y].contenir(piece)) {
					return i;					
				}
			}
		}
		return 0;
	}
	
	public int getCoordonneeY(Piece piece) {
		
		for (int x = 0 ; x<8 ; x++) {
			for (int i= 0 ; i<8 ; i++ ) {
				if (lesCases[x][i].contenir(piece)) {
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
