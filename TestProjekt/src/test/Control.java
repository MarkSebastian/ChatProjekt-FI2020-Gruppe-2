package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Control
{
	private String verbindungslink;
	private String sqlBefehl;
	
	public Control()
	{
		verbindungslink="jdbc:ucanaccess://src/Testdatenbank.accdb";
		sqlBefehl="Select * from Test;"; 
		
		try (Connection verbindung = DriverManager.getConnection(verbindungslink,"","") )
		{
			System.out.println("verbindungaufgebaut");
			System.out.println(verbindung.prepareStatement(sqlBefehl));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	} 
}
