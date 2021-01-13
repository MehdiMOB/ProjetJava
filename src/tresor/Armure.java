package tresor;

import java.io.Serializable;

public class Armure implements Serializable {
	
	private int protection;
	
	public Armure(int protection) {
		this.protection = protection;
		}
	
	public int getProtection() {
		return protection;
	}

	public void setProtection(int protection) {
		this.protection = protection;
	}
	
	
	

}
