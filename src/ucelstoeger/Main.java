package ucelstoeger;

public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InitConnect init = new InitConnect(args);
		CRUD cr = init.getCr();
		cr.dataFiller(5);
	}
}
