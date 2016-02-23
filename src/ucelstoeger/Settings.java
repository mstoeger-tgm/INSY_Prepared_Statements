/**
 * 
 */
package ucelstoeger;

import com.beust.jcommander.*;

/**
 * Settings File for the Input - Parameters (CLI) using JCommander
 * 
 * @author Johannes Ucel
 * @version 18. Feb. 2016
 *
 */
public class Settings {

	@Parameter(names = "-h", description = "Hostname", required = true)
	private String hostname;

	@Parameter(names = "-p", description = "Portnumber", required = false)
	private int port;

	@Parameter(names = "-d", description = "Database", required = true)
	private String database;

	@Parameter(names = "-u", description = "User", required = true)
	private String user;

	@Parameter(names = "-pw", description = "Password", required = true)
	private String password;

	@Parameter(names = "--help", help = true)
	private boolean help = false;

	/**
	 * @return the hostname
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @return the database
	 */
	public String getDatabase() {
		return database;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the help
	 */
	public boolean isHelp() {
		return help;
	}

}
