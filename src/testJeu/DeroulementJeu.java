package testJeu;

import graphique.InterfacePlateau;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import creation.*;
import jeu.Equipe;
import jeu.Plateau;
import tresor.Arme;
import tresor.Armure;
import tresor.StockArmes;
import tresor.StockArmures;

public class DeroulementJeu {
	
	private static Equipe hommes ;
	private static Equipe dragons ;
	private static StockArmes arme;
	private static StockArmures armure;
	private static InterfacePlateau interfaceplateau;
	private static Plateau plateau ;
	
	public static void main(String[] args) {
		init();
	}
	
	
	private static void init() {
		
		int choix=0;
		int option=0;
		CreerArmes nouvellesArmes = null;
		CreerArmures nouvellesArmures = null;
	
		System.out.println("Veuillez faire un choix : \n 1- Démarrer une nouvelle partie \n2-Poursuivre");
		choix = Clavier.entrerClavierInt();
		//Setup Environnement
		if (choix == 1 ) {
			System.out.println("Nom equipe homme");
			String nomEquipeHommes = Clavier.entrerClavierString();
			hommes = new Equipe(nomEquipeHommes);
			System.out.println("nom equipe dragon");
			String nomEquipeDragons = Clavier.entrerClavierString();
			dragons = new Equipe(nomEquipeDragons);
		
		CreerProtagoniste nouveauxProtagonistes= new CreerProtagoniste();
		nouveauxProtagonistes.creerProtagoniste();
		
		nouvellesArmes = new CreerArmes(); 
		nouvellesArmes.creerArmes();
		nouvellesArmures = new CreerArmures();
		nouvellesArmures.creerArmures();
		
		System.out.println(hommes.getNbEffectif());
		
		Arme arme = nouvellesArmes.getStockArmes().getArmes().get(0);
		Armure armure = nouvellesArmures.getStockArmures().getArmures().get(0);
		
		Plateau plateau = new Plateau(hommes,dragons, arme, armure);
		interfaceplateau = new InterfacePlateau();
		Setup setup = new Setup(interfaceplateau,plateau,hommes,dragons);
		setup.positionner();
		setup.afficherSetup();
		
		boucle(setup);
	}else {
		//lancer la restauration
		boucle(restauration());
	}
		
	}
	
	private static void boucle(Setup setup) {

		int choix=0;
		int option=0;
		while (true) {
		if (setup.hommes.getNbEffectif()== 0 )
		{
			
			System.out.println("Les dragons ont gagné !");
			break;
		}

		
			System.out.println("Entrer le type de protagoniste homme à contrôler :");
			System.out.println(setup.hommes.afficherPieces());
			int numProtagoniste = Clavier.entrerClavierInt()-1;
			System.out.println("Vous avez choisi le protagoniste "+ setup.hommes.getPiece(numProtagoniste).getNom());
			DeplacerJoueur homme_deplacement = new DeplacerJoueur(setup.interfacePlateau,setup.plateau,setup.hommes.getPiece(numProtagoniste));
			homme_deplacement.deplacement();
			setup.afficherSetup();
			if (setup.dragons.getNbEffectif() == 0) {
				System.out.println("Les hommes ont gagné ");
				break;
			}
			System.out.println("Entrer le type de protagoniste dragon à contrôler :");
			System.out.println(setup.dragons.afficherPieces());
			int numDragon = Clavier.entrerClavierInt()-1;
			System.out.println("Vous avez choisi le protagoniste "+ setup.dragons.getPiece(numDragon).getNom());
			DeplacerJoueur dragon_deplacement = new DeplacerJoueur(setup.interfacePlateau,setup.plateau,setup.dragons.getPiece(numDragon));
			dragon_deplacement.deplacement();
			setup.afficherSetup();
	
			
			System.out.println("Choisir une option: ");
			System.out.println("1- Poursuivre le jeu ");
			System.out.println("2- Sauvegarder et quitter \n");
			option = Clavier.entrerClavierInt();
			
			if (option == 1)
			{//continue
			}
			if (option == 2)
			{	
				sauvgarder(setup);
				System.out.print("la sauvegarde est terminé, vous allez quitté le jeu ");
				break;
				
			}
		}	
	}
	
	
	
    public static void sauvgarder(Setup setup)
    {
    	

        File fileSup = new File("D://Projet_Java/partie.ser");

        fileSup.delete();
    	
    	
    	System.out.print("Sauvegarde en cours ... \n");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("D://Projet_Java/partie.ser"), true)))
        {
            oos.writeObject(setup);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
     
   
        public static Setup restauration()
        {
        	Setup setup = null;
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("D://Projet_Java/partie.ser"))))
            {
                setup = ((Setup)ois.readObject());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            return setup;
        }
     
	}
	
