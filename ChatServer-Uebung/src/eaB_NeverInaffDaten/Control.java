package eaB_NeverInaffDaten;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Control
{
	private String verbindungslinkLoginserver;
	private String verbindungslinkDatenbank;
	private String sqlBefehl;
	private Connection verbindungLogin;
	private Connection verbindungDatenKrake;
	private SQLBaukasten baukasten;
	private Statement stellungsnahme;
	
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
	//=================================================================================================================Start Delete befehle
	public boolean delete_loginDaten(String bName)
	{
		boolean erfolg=true;
		sqlBefehl=baukasten.delete_1();
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
	//=================================================================================================================Ende Delete befehle
	//=================================================================================================================Start Insert Befehle
	public boolean insert_LoginDB(String bName, String passwort)
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
	
	public boolean insert_Client(String bName)
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
	
	public boolean insert_Loginliste(Date tStampBeginn,String iP, String accountname)
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
		insert_LoginClientZT(accountname,tStampBeginn, iP);
		return erfolg; 
	}
	
	public boolean insert_LoginClientZT(String accountname, Date tStampBeginn,String iP)
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
	
	public boolean insert_Chatroom(String chatroomName , String hashcode , String clientName)
	{
		boolean erfolg=true;
		sqlBefehl=baukasten.insert_Chatroom();
		try
		{
			PreparedStatement vorbereiteteAussage = verbindungDatenKrake.prepareStatement(sqlBefehl);
			vorbereiteteAussage.setString(1, chatroomName);
			vorbereiteteAussage.setString(2, hashcode);
			vorbereiteteAussage.executeUpdate();
		}
		catch (SQLException e)
		{
			erfolg=false;
		}
		insert_ClientChatroomZT(clientName, hashcode);
		return erfolg;
	}
	
	public boolean insert_ClientChatroomZT(String clientName, String hashcode)
	{
		boolean erfolg=true;
		sqlBefehl=baukasten.insert_ClientChatroomZT();
		try
		{
			PreparedStatement vorbereiteteAussage = verbindungDatenKrake.prepareStatement(sqlBefehl);
			vorbereiteteAussage.setString(1, clientName);
			vorbereiteteAussage.setString(2, hashcode);
			vorbereiteteAussage.executeUpdate();
		}
		catch (SQLException e)
		{
			erfolg=false;
		}
		return erfolg; 
	}
	
	public boolean insert_Nachricht(String inhalt, Date timestamp, String accountname, String hashcode)
	{
		boolean erfolg=true;
		sqlBefehl=baukasten.insert_Nachricht();
		try
		{
			PreparedStatement vorbereiteteAussage = verbindungDatenKrake.prepareStatement(sqlBefehl);
			vorbereiteteAussage.setString(1, inhalt);
			vorbereiteteAussage.setDate(2, timestamp);
			vorbereiteteAussage.setString(3, accountname);
			vorbereiteteAussage.setString(4, hashcode);
			vorbereiteteAussage.executeUpdate();
		}
		catch (SQLException e)
		{
			erfolg=false;
		}
		return erfolg; 
	}
	//=================================================================================================================Ende Insert Befehle
	
	public boolean booleanzuzruckgeben(String bName)
	{
		boolean erfolg=true;
		sqlBefehl=baukasten.select_Loginname();
		try
		{
			PreparedStatement vorbereiteteAussage = verbindungDatenKrake.prepareStatement(sqlBefehl);
			vorbereiteteAussage.setString(1, bName);
			stellungsnahme = verbindungLogin.createStatement();
			ResultSet ergebnis = stellungsnahme.executeQuery(sqlBefehl);
			ergebnis.first();
		}
		catch (SQLException e)
		{
			erfolg=false;
		}
		return erfolg; 
	}

} 
