package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Control
{
	private String verbindungslink;
	private String sqlBefehl;
	//Bitte
	
	
	public Control()
	{
		verbindungslink="jdbc:ucanaccess:Testdatenbank";
		sqlBefehl="Select * from Test;"; 
		
		try (Connection verbindung = DriverManager.getConnection(verbindungslink) )
		{
			System.out.println(verbindung.prepareStatement(sqlBefehl));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	} 
}
