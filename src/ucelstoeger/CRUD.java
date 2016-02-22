package ucelstoeger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgresql.ds.PGSimpleDataSource;

public class CRUD {
	private PGSimpleDataSource dbconn;
	private Connection con;
	public CRUD(PGSimpleDataSource dbconn){
		this.dbconn=dbconn;
		try {
			con = dbconn.getConnection();
		} catch (SQLException e) {
			System.out.println("Fehler beim Verbinden zur Datenbank!");
		}
	}
	public void create(String table, String values){
		
	}
	public ResultSet read(String what, String table, String where){
		
		return null;
	}
	public void update(String table, String values, String where){
		
	}
	public void delete(String table, String where){
		
	}
}
