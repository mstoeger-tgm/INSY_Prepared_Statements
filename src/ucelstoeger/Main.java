package ucelstoeger;

/**
 * Zentrale Main - Klasse, wo die CRUD - Methoden aufgerufen werden
 * 
 * @author Johannes Ucel, Michael Stöger
 * @version 25. Feb. 2016
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InitConnect init = new InitConnect(args);
		CRUD cr = init.getCr();
		cr.read(3);
	}
}
