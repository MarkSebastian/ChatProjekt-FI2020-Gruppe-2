package eaB_NeverInaffDaten;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ControlDB
{
	private String verbindungslinkLoginserver;
	private String verbindungslinkDatenbank;
	private String sqlBefehl;
	private Connection verbindungLogin;
	private Connection verbindungDatenKrake;
	private SQLBaukasten baukasten;
	
	public ControlDB()
	{
		baukasten =new SQLBaukasten();
		verbindungslinkDatenbank="\"jdbc:ucanaccess://src/Datenkrake.accdb\""; 
		verbindungslinkLoginserver="\"jdbc:ucanaccess://src/Login_DB.accdb\"";
		
		
		try 
		{
			//verbindungLogin = DriverManager.getConnection(verbindungslinkLoginserver,"","");
			verbindungLogin = DriverManager.getConnection("jdbc:ucanaccess://src/Login_DB.accdb","","");
			//verbindungDatenKrake = DriverManager.getConnection(verbindungslinkDatenbank,"","");
			verbindungDatenKrake = DriverManager.getConnection("jdbc:ucanaccess://src/Datenkrake.accdb","","");
			
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
		sqlBefehl=baukasten.insert_Client();
		try
		{
			PreparedStatement vorbereiteteAussage = verbindungDatenKrake.prepareStatement(sqlBefehl);
			vorbereiteteAussage.setString(1, bName);
			vorbereiteteAussage.executeUpdate();
		}
		catch (SQLException e)
		{
			erfolg=false;
		}
		
		return erfolg;
	}
	
	public boolean insert(Date tStampBeginn,String iP, String accountname)
	{
		boolean erfolg=true;
		sqlBefehl=baukasten.insert_Loginliste();
		try
		{
			PreparedStatement vorbereiteteAussage = verbindungDatenKrake.prepareStatement(sqlBefehl);
			vorbereiteteAussage.setDate(1, tStampBeginn);
			//vorbereiteteAussage.setDate(2, tStampEnde);
			vorbereiteteAussage.setString(2, iP);
			vorbereiteteAussage.executeUpdate();
		}
		catch (SQLException e)
		{
			erfolg=false;
		}
		insert(accountname,tStampBeginn, iP);
		return erfolg; 
	}
	
	public boolean insert(String accountname, Date tStampBeginn,String iP)
	{
		boolean erfolg=true;
		sqlBefehl=baukasten.insert_LoginClientZT();
		try
		{
			PreparedStatement vorbereiteteAussage = verbindungDatenKrake.prepareStatement(sqlBefehl);
			vorbereiteteAussage.setString(1, accountname);
			vorbereiteteAussage.setDate(2, tStampBeginn);
			vorbereiteteAussage.setString(3, iP);
			vorbereiteteAussage.executeUpdate();
		}
		catch (SQLException e)
		{
			erfolg=false;
		}
		return erfolg; 
	}
	
	public boolean insert1(String chatroomName)
	{
		boolean erfolg=true;
		sqlBefehl=baukasten.insert_Chatroom();
		try
		{
			PreparedStatement vorbereiteteAussage = verbindungDatenKrake.prepareStatement(sqlBefehl);
			vorbereiteteAussage.setString(1, chatroomName);
			vorbereiteteAussage.executeUpdate();
		}
		catch (SQLException e)
		{
			erfolg=false;
		}
		
		return erfolg;
	}
	
	/*public boolean insert1(String chatroomName, String iP, String accountname)
	{
		boolean erfolg=true;
		sqlBefehl=baukasten.insert_Loginliste();
		try
		{
			PreparedStatement vorbereiteteAussage = verbindungDatenKrake.prepareStatement(sqlBefehl);
			vorbereiteteAussage.setDate(1, tStampBeginn);
			//vorbereiteteAussage.setDate(2, tStampEnde);
			vorbereiteteAussage.setString(2, iP);
			vorbereiteteAussage.executeUpdate();
		}
		catch (SQLException e)
		{
			erfolg=false;
		}
		insert(accountname,tStampBeginn, iP);
		return erfolg; 
	}*/
} 
