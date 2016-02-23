package ucelstoeger;

import org.postgresql.ds.PGSimpleDataSource;

import com.beust.jcommander.JCommander;

import java.sql.*;

/**
 * Simple JDBC Connection
 * 
 * 
 * Vorbereitung:
 * 
 * 1. Postgres - Connector downloaden
 * 
 * 2. Config vim /etc/postgresql/9.3/main/pg_hba.conf host hinzuf�gen Hier wird
 * folgender Eintrag hinzugef�gt:
 * 
 * host schokofabrik schokouser 192.168.203.0/24 md5 (Datenbank) (User)
 * (NetzID/Subnetmask) (authentication)
 * 
 * 3. vim /etc/postgresql/9.3/main/postgresql.conf listen_addresses anpassen
 * 
 * Kommentar (#) entfernen und den Eintrag so �ndern, dass auf alle (*) IP -
 * Adressen geh�rt wird
 * 
 * listen_addresses = '*'
 * 
 * 
 * @author Johannes Ucel
 * @version 12.11.2015
 *
 */
public class ConnectDatabase {
	private String host="", database="", user="", password="";
	private int port=0;
	public ConnectDatabase(String args[]){
		if (args.length == 2) {
			// Properties file
			PropertiesFileReader pfr = new PropertiesFileReader();
			host = pfr.getHost();
			database = pfr.getDatabase();
			user = pfr.getUser();
			password = pfr.getPassword();
			port = Integer.parseInt(pfr.getPort());
		} else {
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
	            return;
	        }
		}
		System.out.println("Datenbankname:" + database);
		System.out.println("Hostname: " + host);
		System.out.println(password);
		System.out.println("Port: " + port);
		System.out.println("User: " + user);
		// Datenquelle erzeugen und konfigurieren
		PGSimpleDataSource ds = new PGSimpleDataSource();
		ds.setServerName(host); // Die Server-IP
		ds.setPortNumber(port); // Server - Port
		ds.setDatabaseName(database); // Datenbankname
		ds.setUser(user); // Datenbankuser
		ds.setPassword(password); // Datenbankpasswort
		// Verbindung herstellen
		CRUD cr = new CRUD(ds);
		cr.update(10, 1);
		cr.delete(110);
		try (Connection con = ds.getConnection();
				// Abfrage vorbereiten und ausf�hren
				Statement st = con.createStatement();
				/*
				 * Bei createStatement() ohne Paramter werden folgende
				 * Default-Werte gesetzt TYPE_FORWARD_ONLY (Nur vorw�rts im
				 * ResultSet bewegen) CONCUR_READ_ONLY (ReadyOnly ResultSet)
				 */
				ResultSet rs = st.executeQuery("select * from produkt");) {
			// Ergebnisse verarbeiten
			while (rs.next()) { // Cursor bewegen/iterieren
				String wert = rs.getString(2);
				System.out.println(wert);
			}
		} catch (

		SQLException sql)

		{ // SQLException abfangen
			sql.printStackTrace(System.err);
		}
	}
	
}