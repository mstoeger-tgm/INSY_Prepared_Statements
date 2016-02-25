package ucelstoeger;

import org.postgresql.ds.PGSimpleDataSource;
import com.beust.jcommander.JCommander;

/**
 * JDBC Prepared Statements
 * 
 * @author Johannes Ucel, Michael Stoeger
 * @version 24.2.2015
 *
 */
public class InitConnect {
	private String host = "", database = "", user = "", password = "";
	private int port = 0;
	private CRUD cr;

	/**
	 * Liest Daten von CLI oder Properties File und erzeugt damit ein neues CRUD
	 * Objekt
	 * 
	 * @param args
	 *            <--- Kommandozeilenparameter
	 */
	public InitConnect(String args[]) {

		//Properties - File über das Argument -f 
		if (args.length == 2 && args[0].equals("-f")) {
			// Properties file
			PropertiesFileReader pfr = new PropertiesFileReader();
			pfr.getPropertiesFromFile(args[1]);
			host = pfr.getHost();
			database = pfr.getDatabase();
			user = pfr.getUser();
			password = pfr.getPassword();
			port = Integer.parseInt(pfr.getPort());
		} else{
			// CLI only
			Settings settings = new Settings();
			JCommander cmd = new JCommander(settings, args);
			host = settings.getHostname();
			port = settings.getPort();
			database = settings.getDatabase();
			user = settings.getUser();
			password = settings.getPassword();
			if (port == 0)
				port = 5432; // PostgreSQL Default Port
			if (settings.isHelp()) {
				cmd.usage();
				System.out.println("-f for Properties - File");
				return;
			}
		}
		// Datenquelle erzeugen und konfigurieren
		PGSimpleDataSource ds = new PGSimpleDataSource();
		ds.setServerName(host); // Die Server-IP
		ds.setPortNumber(port); // Server - Port
		ds.setDatabaseName(database); // Datenbankname
		ds.setUser(user); // Datenbankuser
		ds.setPassword(password); // Datenbankpasswort
		// Verbindung herstellen
		cr = new CRUD(ds);
	}

	/**
	 * Gibt das CRUD Objekt zurueck
	 * 
	 * @return CRUD
	 */
	public CRUD getCr() {
		return cr;
	}

}