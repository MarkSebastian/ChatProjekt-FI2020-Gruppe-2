package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Control
{
	private String verbindungslink;
	private String sqlBefehl;
	private Statement stellungsnahme;
	
	public Control()
	{
		verbindungslink="jdbc:ucanaccess://src/Testdatenbank.accdb";
		sqlBefehl="Select * from Test;"; 
		
		try (Connection verbindung = DriverManager.getConnection(verbindungslink,"","") )
		{
			System.out.println("verbindungaufgebaut");
			stellungsnahme = verbindung.createStatement();
			ResultSet ergebnis = stellungsnahme.executeQuery(sqlBefehl);
			while (ergebnis.next())
			{
				System.out.println(ergebnis.getString("Feld1"));
				System.out.println(ergebnis.getInt("ID"));
				
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	} 
}
