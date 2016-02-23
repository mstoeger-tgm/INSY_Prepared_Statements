package ucelstoeger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgresql.ds.PGSimpleDataSource;

public class CRUD {
	private PGSimpleDataSource dbconn;
	private Connection con;
	private String updateTableSQL = "UPDATE produkt SET gewicht = ? WHERE nummer = ?";
	private String deleteTableSQL = "DELETE produkt WHERE nummer =?";

	public CRUD(PGSimpleDataSource dbconn) {
		this.dbconn = dbconn;
		try {
			con = dbconn.getConnection();

		} catch (SQLException e) {
			System.out.println("Fehler beim Verbinden zur Datenbank!");
		}
	}

	public void create(String table, String values) {

	}

	public ResultSet read(String what, String table, String where) {

		return null;
	}

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

	public void delete(int where) {
		try {
			PreparedStatement ps = con.prepareStatement(deleteTableSQL);
			ps.setInt(1, where);  
			ps.executeUpdate();
			System.out.println("Löschen erfolgreich");
		} catch (SQLException e) {
			System.err.println("Fehler beim Löschen!");
		}
	}
}
