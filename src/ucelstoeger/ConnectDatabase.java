package ucelstoeger;

import org.postgresql.ds.PGSimpleDataSource;
import java.sql.*;

/**
 * Simple JDBC Connection
 * 
 * 
 * Vorbereitung: 
 * 
 * 1. Postgres - Connector downloaden
 * 
 * 2. Config vim /etc/postgresql/9.3/main/pg_hba.conf host hinzufügen
 * Hier wird folgender Eintrag hinzugefügt: 
 * 
 * host schokofabrik schokouser 192.168.203.0/24 md5
 * 		(Datenbank)	 (User)		(NetzID/Subnetmask) (authentication)
 * 
 * 3. vim /etc/postgresql/9.3/main/postgresql.conf  listen_addresses anpassen
 * 
 * Kommentar (#) entfernen und den Eintrag so ändern, dass auf alle (*) IP - Adressen gehört wird
 * 
 * listen_addresses = '*'  
 * 
 * 
 * @author Johannes Ucel
 * @version 12.11.2015
 *
 */
public class ConnectDatabase {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Datenquelle erzeugen und konfigurieren
		PGSimpleDataSource ds = new PGSimpleDataSource();
		ds.setServerName("192.168.203.162"); //Die Server-IP
		ds.setDatabaseName("schokofabrik"); //Datenbankname
		ds.setUser("schokouser"); //Datenbankuser
		ds.setPassword("schokouser"); //Datenbankpasswort
		/*	
		 * 	Früher:
		 * try {
			Connection con = ds.getConnection();
			// Abfrage vorbereiten und ausführen
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from produkt");
			while (rs.next()) { // Cursor bewegen/iterieren
				String wert = rs.getString(2);
				System.out.println(wert);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			rs.close(); 
			st.close(); 
			con.close();
		} 
		 * 	Im finally hätte man kein Zugriff auf rs, st, con (müssten static oder anders definiert sein)
		 * 
		 * 	Neu seit Java 1.7 AutoCloseable
		 * 	Man erspart sich das "finally", da automatisch geclosed wird.
		 * 	==> Man erstellt die Connection, Statement, ResultSet in der runden Klammer vom try-Block und kann diese dann normal weiterverwenden
		 *
		 */
		// Verbindung herstellen
		try (Connection con = ds.getConnection();
				// Abfrage vorbereiten und ausführen
				Statement st = con.createStatement();
				/*
				 * Bei createStatement() ohne Paramter werden folgende Default-Werte gesetzt
				 * TYPE_FORWARD_ONLY (Nur vorwärts im ResultSet bewegen)
				 * CONCUR_READ_ONLY (ReadyOnly ResultSet)
				 */
				ResultSet rs = st.executeQuery("select * from produkt");) { //Select Query wird nicht auf Syntax überprüft 
			// Ergebnisse verarbeiten
			while (rs.next()) { // Cursor bewegen/iterieren
				String wert = rs.getString(2);
				System.out.println(wert);
			}
		} catch (SQLException sql) { //SQLException abfangen
			sql.printStackTrace(System.err);
		}
	}
}