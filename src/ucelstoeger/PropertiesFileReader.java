package ucelstoeger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.InvalidParameterException;
import java.util.ArrayList;
/**
 * Klasse zum Einlesen des Properties File
 * @author Michael Stoeger
 * @version 25.2.2016
 */
public class PropertiesFileReader {
	private String host = "", port = "5432", database = "", user = "", password = "";
	/**
	 * Liest Einstellungen aus einer Datei
	 * @param pathToFile
	 */
	public void getPropertiesFromFile(String pathToFile) {
		// Read properties file
		BufferedReader br = null;
		ArrayList<String> lines = new ArrayList<String>();
		try {
			String tmp = "";
			br = new BufferedReader(new FileReader(pathToFile));
			while ((tmp = br.readLine()) != null) {
				lines.add(tmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Extract properties from lines
		for (String e : lines) {
			if (e.contains("host")) {
				host = e.substring(e.indexOf('=') + 1);
				break;
			}
		}
		for (String e : lines) {
			if (e.contains("port")) {
				port = e.substring(e.indexOf('=') + 1);
				break;
			}
		}
		for (String e : lines) {
			if (e.contains("database")) {
				database = e.substring(e.indexOf('=') + 1);
				break;
			}
		}
		for (String e : lines) {
			if (e.contains("user")) {
				user = e.substring(e.indexOf('=') + 1);
				break;
			}
		}
		for (String e : lines) {
			if (e.contains("password")) {
				password = e.substring(e.indexOf('=') + 1);
				break;
			}
		}
		if (host == "" || database == "" || user == "" || password == "") {
			throw new InvalidParameterException("Nicht alle benoetigten Parameter angegeben");
		}
	}

	/**
	 * @return host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @return port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @return database
	 */
	public String getDatabase() {
		return database;
	}

	/**
	 * @return user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

}
