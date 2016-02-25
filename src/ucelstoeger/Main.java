package ucelstoeger;

/**
 * Zentrale Main - Klasse, wo die CRUD - Methoden aufgerufen werden
 * 
 * @author Johannes Ucel, Michael St�ger
 * @version 25. Feb. 2016
 *
 */
public class Main {

	/**
	 * Programmstart Fuehrt Befehle auf CRUD Objekt aus
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0)
			System.out.println("Bitte verwenden sie -f FILENAME f�r ein Property - File bzw. siehe --help");
		else {
			InitConnect init = new InitConnect(args);
			CRUD cr = init.getCr();
			cr.read(3);
			cr.update(100, 3);
			cr.dataFiller(10000);
			cr.delete(1000);
		}
	}
}
