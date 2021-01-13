package combat;

import java.io.Serializable;

import protagonistes.Dragon;
import protagonistes.Homme;

public class Partie implements Serializable {
	private Equipe equipeHommes ;
	private Equipe equipeDragons;
	
	public Partie() {
		
		
	}
	
	public void ajouterHommes(Equipe hommes) {
		this.equipeHommes=hommes;
		
	}
	public void ajouterDragons(Equipe dragons) {
		
		this.equipeDragons=dragons;
	}

}
