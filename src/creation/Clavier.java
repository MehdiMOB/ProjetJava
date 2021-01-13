package creation;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Clavier implements Serializable {
    private static Scanner scanner = new Scanner(System.in);

    public static int entrerClavierInt() {
        boolean attempt;
        int number=0;
    	do {
        	try {
        		attempt = false ;
        		number= scanner.nextInt();
        	}
        	catch (InputMismatchException e) {
        		scanner.next();
        		attempt = true ;
        		System.out.println("Vous devez entrer un entier");
        	}
        	
        }
    	while (attempt == true);
    	return number;
    }
 
    public static String entrerClavierString() {
    		 return scanner.next();
    }

}