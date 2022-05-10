package eaB_NeverInaffDaten;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Control
{
	private String verbindungslinkLoginserver;
	private String verbindungslinkDatenbank;
	private String sqlBefehl;
	private Connection verbindungLogin;
	private Connection verbindungDatenKrake;
	private SQLBaukasten baukasten;
	
	public Control()
	{
		baukasten =new SQLBaukasten();
		verbindungslinkLoginserver="\"jdbc:ucanaccess://src/Login_DB.accdb\"";
		verbindungslinkDatenbank="\"jdbc:ucanaccess://src/Datenkrake.accdb\"";
		
		try 
		{
			verbindungLogin = DriverManager.getConnection(verbindungslinkLoginserver,"","");
			verbindungDatenKrake = DriverManager.getConnection(verbindungslinkDatenbank,"","");
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		
	}
	public boolean insert(String bName, String passwort)
	{
		boolean erfolg=true;
		sqlBefehl=baukasten.insert_LoginDB();
		try
		{
			PreparedStatement vorbereiteteAussage = verbindungLogin.prepareStatement(sqlBefehl);
			vorbereiteteAussage.setString(1, bName);
			vorbereiteteAussage.setString(2, passwort);
			vorbereiteteAussage.executeUpdate();
		}
		catch (SQLException e)
		{
			erfolg=false;
		}
		
		return erfolg;
	}
	public boolean insert(String bName)
	{
		boolean erfolg=true;
		sqlBefehl=baukasten.insert_LoginDB();
		try
		{
			PreparedStatement vorbereiteteAussage = verbindungLogin.prepareStatement(sqlBefehl);
			vorbereiteteAussage.setString(1, bName);
			vorbereiteteAussage.executeUpdate();
		}
		catch (SQLException e)
		{
			erfolg=false;
		}
		
		return erfolg;
	}
	public boolean insert(String bName, String passwort,String )
	{
		boolean erfolg=true;
		sqlBefehl=baukasten.insert_Loginliste();
		try
		{
			PreparedStatement vorbereiteteAussage = verbindungLogin.prepareStatement(sqlBefehl);
			vorbereiteteAussage.setString(1, bName);
			vorbereiteteAussage.setString(2, passwort);
			vorbereiteteAussage.executeUpdate();
		}
		catch (SQLException e)
		{
			erfolg=false;
		}
		
		return erfolg;
	}
}
