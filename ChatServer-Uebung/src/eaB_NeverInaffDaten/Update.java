package eaB_NeverInaffDaten;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Update
{
	private String verbindungslink;
	private String verbindungLogin;
	private String sqlLogindatenUpdaten;
	private String sqlClientUpdaten;
	private String sqlLoginListeUpdaten;
	
	private String bsp="bsp";
			
			
	public Update()
	{
		verbindungslink="jdbc:ucanaccess://Datenkrake.accdb";
		verbindungLogin="jdbc:ucanaccess://Login_DB.accdb";
		sqlLogindatenUpdaten="Update Login_Daten set Benutzername=? where Benutzername=?";
		sqlClientUpdaten="Update Client set Benutzername=? where Benutzername=?";
		sqlLoginListeUpdaten="Update Loginliste set timestamp_ende=? where timestamp=?";
		
	}
	private void loginupdaten()
	{
		try (Connection verbindung = DriverManager.getConnection(verbindungLogin,"","") )
		{
			
			PreparedStatement vorbereiteteAussage = verbindung.prepareStatement(sqlLogindatenUpdaten);
			vorbereiteteAussage.setString(1,bsp);
			vorbereiteteAussage.execute();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	private void updaten()
	{
		try (Connection verbindung = DriverManager.getConnection(verbindungslink,"","") )
		{
			
		}
	}
}
