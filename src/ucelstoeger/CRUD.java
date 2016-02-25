package ucelstoeger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.ds.PGSimpleDataSource;

/**
 * Baut Verbindung zur Datenbank auf und fuehrt CRUD Befehle aus
 * 
 * @author Michael Stoeger, Johannes Ucel
 * @version 23.2.2016
 */
public class CRUD {
	private Connection con;
	// Definieren der PreparedStatements
	private final String updateTableSQL = "UPDATE produkt SET gewicht = ? WHERE nummer = ?";
	private final String deleteTableSQL = "DELETE FROM produkt WHERE nummer = ?";
	private final String insertTableSQL = "INSERT INTO produkt VALUES (?,?,?)";
	private final String readTableSQL = "SELECT * FROM produkt WHERE nummer = ?";

	/**
	 * Konstruktor Baut Verbindung zur Datenbank
	 * 
	 * @param PGSimpleDataSource
	 */
	public CRUD(PGSimpleDataSource dbconn) {
		try {
			con = dbconn.getConnection();
		} catch (SQLException e) {
			System.err.println("Fehler beim Verbinden zur Datenbank!");
		}
	}

	/**
	 * Fuegt Daten in Tabelle als Values ein Folgende Syntax (nummer,
	 * bezeichnung, gewicht)
	 * 
	 * @param nummer
	 *            - Ist ein Integerwert (und PK)
	 * @param bezeichnung
	 *            - Eine Produktbeschreibung als String
	 * @param gewicht
	 *            - Ist das Gewicht als Integerwert
	 */
	public void create(int nummer, String bezeichnung, int gewicht) {
		try {
			PreparedStatement ps = con.prepareStatement(insertTableSQL);
			// Erstes "?" mit einem Integerwert (Nummer) ersetzen
			ps.setInt(1, nummer);
			// Zweites "?" mit der Produktbeschreibung ersetzen
			ps.setString(2, bezeichnung);
			// Drittes "?" mit einem Integerwert (Gewicht) ersetzen
			ps.setInt(3, gewicht);
			// PreparedStatement durchführen
			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Fehler beim Inserten");
		}
	}

	/**
	 * Liest aus der Datenbank die Zeile mit der gewünschten Nummer aus
	 * 
	 * @param nummer
	 *            - Die gewünschte Nummer (Integerwert)
	 */
	public void read(int nummer) {
		try {
			PreparedStatement ps = con.prepareStatement(readTableSQL);
			// Ersetzt die Nummer (Integerwert)
			ps.setInt(1, nummer);
			// Für die Ausgabe der gewünschten Zeile wird ein ResultSet erzeugt
			ResultSet tmp = ps.executeQuery();
			while (tmp.next()) {
				System.out.print("Nummer: " + tmp.getInt(1));
				System.out.print(", Bezeichnung: " + tmp.getString(2));
				System.out.print(", Gewicht: " + tmp.getInt(3));
			}
		} catch (Exception e) {
			System.err.println("Fehler beim Lesen");
		}
	}

	/**
	 * Veraendert bzw. Updated Daten in der Datenbank
	 * 
	 * @param gewicht
	 *            - Das gewünschte Gewicht als Integerwert
	 * @param where
	 *            - Die Produktnummer (Integerwert)
	 */
	public void update(int gewicht, int where) {
		try {
			PreparedStatement ps = con.prepareStatement(updateTableSQL);
			ps.setInt(1, gewicht);
			ps.setInt(2, where);
			ps.executeUpdate();
			System.out.println();
			System.out.println("Update erfolgreich!");
			read(where);
		} catch (SQLException e) {
			System.err.println("Fehler beim Updaten!");
		}
	}

	/**
	 * Loescht Daten aus der Datenbank
	 * 
	 * @param where
	 *            - Gibt die gewünschte Produktnummer an
	 */
	public void delete(int where) {
		try {
			PreparedStatement ps = con.prepareStatement(deleteTableSQL);
			ps.setInt(1, where);
			ps.executeUpdate();
			System.out.println("Loeschen erfolgreich");
		} catch (SQLException e) {
			System.err.println("Fehler beim Loeschen!");
			e.printStackTrace();
		}
	}

	/**
	 * Generiert Zufallsdaten in der "produkt" Tabelle
	 * 
	 * @param count
	 *            <- Wie viele Zeilen
	 */
	public void dataFiller(int count) {
		int maxnummer = 0;
		try {
			Statement tmp = con.createStatement();
			// Holt sich die aktuelle Maxnummer, um zukünftige Fehler mit der
			// falschen Produktnummer zu verhindern
			ResultSet tmp2 = tmp.executeQuery("SELECT max(nummer) FROM produkt;");
			while (tmp2.next()) {
				maxnummer = (tmp2.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Fehler beim Lesen der Maxnummer!");
		}
		int begin = maxnummer + 1;
		for (int i = 0; i < count; i++, begin++)
			create(begin, "TEST" + begin, begin + 1);

	}
}
