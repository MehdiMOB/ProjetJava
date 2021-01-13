package tresor;

import java.io.Serializable;

public class Arme implements Serializable{
	private int degat;	 
	
	public Arme(int degat) {
		this.degat = degat;
	}

	public int getDegat() {
		return degat;
	}

	public void setDegat(int degat) {
		this.degat = degat;
	}
	
	
	

	
}
