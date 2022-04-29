package test;

import java.sql.Connection;

public class Control
{
	private String verbindungslink;
	private Connection verbindung;
	private String sqlBefehl;
	//Bitte
	
	public Control()
	{
		verbindungslink="jdbc:odbc:Testdatenbank";
		
	}
}
