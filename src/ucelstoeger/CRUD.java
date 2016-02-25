package ucelstoeger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgresql.ds.PGSimpleDataSource;
/**
 * Baut Verbindung zur Datenbank auf und fuehrt CRUD Befehle aus
 * @author Michael Stoeger, Johannes Ucel
 * @version 23.2.2016
 */
public class CRUD {
	private PGSimpleDataSource dbconn;
	private Connection con;
	private final String updateTableSQL = "UPDATE produkt SET gewicht = ? WHERE nummer = ?";
	private final String deleteTableSQL = "DELETE produkt WHERE nummer =?";
	private final String insertTableSQL = "INSERT INTO ? VALUES (?)";
	private final String readTableSQL = "SELECT ? FROM ? WHERE ?";
	/**
	 * Konstruktor
	 * Baut Verbindung zur Datenbank
	 * @param PGSimpleDataSource
	 */
	public CRUD(PGSimpleDataSource dbconn) {
		this.dbconn = dbconn;
		try {
			con = dbconn.getConnection();
		} catch (SQLException e) {
			System.err.println("Fehler beim Verbinden zur Datenbank!");
			e.printStackTrace();
		}
	}
	/**
	 * Fuegt Daten in Tabelle ein
	 * Values sind in der Form: value1, value2, value3   einzutragen
	 * @param table
	 * @param values
	 */
	public void create(String table, String values) {
		try{
			PreparedStatement ps = con.prepareStatement(readTableSQL);
			ps.setString(1, table);
			ps.setString(2, values);
			ps.executeQuery();
		}catch(Exception e){
			System.err.println("Fehler beim Insert");
		}
	}
	/**
	 * Gibt Daten aus der Datenbank als Resultset zurueck
	 * @param what
	 * @param table
	 * @param where
	 * @return Ergebnis der Query als ResultSet
	 */
	public ResultSet read(String what, String table, String where) {
		if(!(allowedString(what)||allowedString(table)||allowedString(where)))
			return null;
		try{
			PreparedStatement ps = con.prepareStatement(readTableSQL);
			ps.setString(1, what);
			ps.setString(2, table);
			ps.setString(3, where);
			return ps.executeQuery();
		}catch(Exception e){
			System.err.println("Fehler beim Lesen");
		}
		return null;
	}

	/**
	 * Veraendert Daten in der Datenbank
	 * @param value
	 * @param where
	 */
	public void update(int value, int where) {
		try {
			PreparedStatement ps = con.prepareStatement(updateTableSQL);
			ps.setInt(1, value); 
			ps.setInt(2, where); 
			ps.executeUpdate(); 
			System.out.println("Update erfolgreich!");
		} catch (SQLException e) {
			System.err.println("Fehler beim Updaten!");
		}
	}

	/**
	 * Loescht Daten aus der Datenbank
	 * @param where
	 */
	public void delete(int where) {
		try {
			PreparedStatement ps = con.prepareStatement(deleteTableSQL);
			ps.setInt(1, where);  
			ps.executeQuery();
			System.out.println("Loeschen erfolgreich");
		} catch (SQLException e) {
			System.err.println("Fehler beim Loeschen!");
		}
	}
	/**
	 * Generiert Zufallsdaten in die "produkt" Tabelle
	 * @param count <- Wie viele Zeilen
	 */
	public void dataFiller(int count){
		ResultSet maxnumber = read("max(nummer)","produkt", "true");
		String tmp ="";
		try {
			maxnumber.next();
			tmp = maxnumber.getString(1);
		} catch (SQLException e) {
			System.err.println("Fehler beim Auswerten");
		}
		int begin = Integer.parseInt(tmp)+1;
		for(int i=0;i<count;++i,++begin){
			String values = Integer.toString(begin)+","+"TEST"+",1";
			create("produkt", values);
		}
	}
	public boolean allowedString(String toCheck){
		final String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPPQRSTUVWXYZ1234567890_()";
		for(int i=0;i<toCheck.length();++i){
			if(allowedChars.indexOf(toCheck.charAt(i))==-1){
				return false;
			}
		}
		return true;
	}
}
