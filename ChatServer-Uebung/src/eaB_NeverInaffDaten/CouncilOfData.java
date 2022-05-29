package eaB_NeverInaffDaten;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CouncilOfData
{
	private String verbindungslinkLoginserver;
	private String verbindungslinkDatenbank;
	private String sqlBefehl;
	private Connection verbindungLogin;
	private Connection verbindungDatenKrake;
	private SQLBaukasten baukasten;
	
	public CouncilOfData()
	{
		baukasten =new SQLBaukasten();
		verbindungslinkLoginserver="jdbc:ucanaccess://src/Login_DB.accdb";//link zur Loginserver DB
		verbindungslinkDatenbank="jdbc:ucanaccess://src/Datenkrake.accdb";//link zur Haupt Datenbank
		
		try 
		{
			verbindungLogin = DriverManager.getConnection(verbindungslinkLoginserver,"","");//Verbindungsaufbau LoginserverDB
			verbindungDatenKrake = DriverManager.getConnection(verbindungslinkDatenbank,"","");//Verbindungsaufbau HaupDB
			System.out.println("verbindung mit datenbanken aufgebaut");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	public void schliesenDATENBANK() throws SQLException//<----------Methode zum schliesen der DB-Connection
	{
		verbindungLogin.close();
		verbindungDatenKrake.close();
	}
	//=================================================================================================================Start Delete befehle
	public boolean delete_loginDaten(String bName)//löscht die Logindaten o7
	{
		boolean erfolg=true;
		sqlBefehl=baukasten.delete_loginDaten();
		try
		{
			PreparedStatement vorbereiteteAussage = verbindungLogin.prepareStatement(sqlBefehl);
			vorbereiteteAussage.setString(1, bName);
			vorbereiteteAussage.executeUpdate();
		}
		catch (SQLException e)
		{
			erfolg=false;//datenbankfehler
		}
		
		return erfolg;
	}
	//=================================================================================================================Ende Delete befehle
	//=================================================================================================================Start Insert Befehle
	public boolean insert_LoginDB(String bName, String passwort)//registrieren (/*,*/
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
			erfolg=false;//datenbankfehler
		}
		
		return erfolg;
	}
	
	public boolean insert_Client(String bName)//benutzername in clienttabelle einfügen
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
			erfolg=false;//datenbankfehler
		}
		
		return erfolg;
	}
	
	public boolean insert_Loginliste(Date tStampBeginn,String iP, String accountname)//anmeldung in die loginliste einfügen
	{
		boolean erfolg=true;
		sqlBefehl=baukasten.insert_Loginliste();
		try
		{
			PreparedStatement vorbereiteteAussage = verbindungDatenKrake.prepareStatement(sqlBefehl);
			vorbereiteteAussage.setDate(1, tStampBeginn);
			vorbereiteteAussage.setString(2, iP);
			vorbereiteteAussage.executeUpdate();
		}
		catch (SQLException e)
		{
			erfolg=false;//datenbankfehler
		}
		insert_LoginClientZT(accountname,tStampBeginn, iP);//automatischer aufruf für ZuordnungsTabelle
		return erfolg; 
	}
	
	public boolean insert_LoginClientZT(String accountname, Date tStampBeginn,String iP)//ZT ZuordnungsTabelle -->fügt den entstanden client der ZuordnungsTabelle hinzu
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
			erfolg=false;//datenbankfehler
		}
		return erfolg; 
	}
	
	public boolean insert_Chatroom(String chatroomName , String hashcode , String clientName)//registrieren eines chatrooms
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
			erfolg=false;//datenbankfehler
		}
		insert_ClientChatroomZT(clientName, hashcode);//automatischer aufruf für ZuordnungsTabelle
		return erfolg;
	}
	
	public boolean insert_ClientChatroomZT(String clientName, String hashcode)//hinzufügen des chatrooms in die ZuordnungsTabelle
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
			erfolg=false;//datenbankfehler
		}
		return erfolg; 
	}
	
	public boolean insert_Nachricht(String inhalt, Date timestamp, String accountname, String hashcode, String typ)//nachricht archivieren inhalt=entweder der string inhalt oder der dateipfad 
	//(!!!Typ zur festlegung welcher dateityp A=Audio, B=Bild, D=Datei, N=Nachricht(String)<--Default wert)
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
			vorbereiteteAussage.setString(5, typ);
			vorbereiteteAussage.executeUpdate();
		}
		catch (SQLException e)
		{
			erfolg=false;//datenbankfehler
		}
		return erfolg; 
	}
	//=================================================================================================================Ende Insert Befehle
	//=================================================================================================================Start Update Befehle
	public boolean update_Benutzername(String bNameNeu, String bNameAlt)//nutzername ändern
	{
		boolean erfolg=true;
		sqlBefehl=baukasten.update_Benutzername();
		try
		{
			PreparedStatement vorbereiteteAussage = verbindungDatenKrake.prepareStatement(sqlBefehl);
			vorbereiteteAussage.setString(1, bNameNeu);
			vorbereiteteAussage.setString(2, bNameAlt);
			vorbereiteteAussage.executeUpdate();
		}
		catch (SQLException e)
		{
			erfolg=false;//datenbankfehler
		}
		return erfolg; 
	}
	
	public boolean update_timestamp(String bName)//login mit end-timestamp versehen
	{
		boolean erfolg=true;
		sqlBefehl=baukasten.update_timestamp();
		try
		{
			PreparedStatement vorbereiteteAussage = verbindungDatenKrake.prepareStatement(sqlBefehl);
			vorbereiteteAussage.setString(1, bName);
			vorbereiteteAussage.executeUpdate();
		}
		catch (SQLException e)
		{
			erfolg=false;//datenbankfehler
		}
		return erfolg; 
	}
	
	public boolean update_client_pic(String profilbildPfad, String bName)//profilbild updaten zuerst pfad zum neuen profilbild dann nutzername
	{
		boolean erfolg=true;
		sqlBefehl=baukasten.update_client_pic();
		try
		{
			PreparedStatement vorbereiteteAussage = verbindungDatenKrake.prepareStatement(sqlBefehl);
			vorbereiteteAussage.setString(1, profilbildPfad);
			vorbereiteteAussage.setString(2, bName);
			vorbereiteteAussage.executeUpdate();
		}
		catch (SQLException e)
		{
			erfolg=false;//datenbankfehler
		}
		return erfolg; 
	}
	//=================================================================================================================Ende Update Befehle
	//=================================================================================================================Select anfang
	public boolean[] nutzerNameFreiFragezeichen(String bName)//überprüfen ob der name noch frei ist
	{
		boolean erfolg[]=new boolean[2];
		erfolg[1]=true;
		erfolg[0]=true;
		sqlBefehl=baukasten.select_Loginname();
		try
		{
			PreparedStatement vorbereiteteAussage = verbindungLogin.prepareStatement(sqlBefehl);
			vorbereiteteAussage.setString(1, bName);
			ResultSet ergebnis = vorbereiteteAussage.executeQuery();
			if (ergebnis.next())
			{
				erfolg[1]=false;//Name bereits vergeben
			}
			
		}
		catch (SQLException e)
		{
			erfolg[0]=false;//datenbankfehler
		}
		return erfolg; 
	}
	
	public boolean[] chatroomNameFreiFragezeichen(String bName)//überpfrüfen ob der chatroomname noch frei ist
	{
		boolean erfolg[]=new boolean[2];
		erfolg[1]=true;
		erfolg[0]=true;
		sqlBefehl=baukasten.select_hashcode();
		try
		{
			PreparedStatement vorbereiteteAussage = verbindungDatenKrake.prepareStatement(sqlBefehl);
			vorbereiteteAussage.setString(1, bName);
			ResultSet ergebnis = vorbereiteteAussage.executeQuery();
			if (ergebnis.next())
			{
				erfolg[1]=false;//Name bereits vergeben
			} 
		}
		catch (SQLException e)
		{
			erfolg[0]=true;//datenbankfehler
		}
		return erfolg; 
	} 
	
	public ResultSet profilBildSelect(String bName)//anhand des client namens wird das dazugehörige pb geladen (pb=profil bild)
	{
		boolean erfolg=true;
		ResultSet ruckgabe = null;
		sqlBefehl=baukasten.select_client_pic();
		try
		{
			PreparedStatement vorbereiteteAussage = verbindungDatenKrake.prepareStatement(sqlBefehl);
			vorbereiteteAussage.setString(1, bName);
			ResultSet ergebnis = vorbereiteteAussage.executeQuery();	
			ruckgabe=ergebnis;
		}
		catch (SQLException e)
		{
			erfolg=false;//datenbankfehler
		}
		return ruckgabe; 
	} 
	
	public ResultSet chatroomNamenVonUserSelecten(String bName)//anhand des client namens sämmtliche ihm zugeordnete chatrooms ausgeben
	{
		boolean erfolg=true;
		ResultSet ruckgabe = null;
		sqlBefehl=baukasten.select_chatroomnamen_by_client();
		try
		{
			PreparedStatement vorbereiteteAussage = verbindungDatenKrake.prepareStatement(sqlBefehl);
			vorbereiteteAussage.setString(1, bName);
			ResultSet ergebnis = vorbereiteteAussage.executeQuery();	
			ruckgabe=ergebnis;
		}
		catch (SQLException e)
		{
			erfolg=false;//datenbankfehler
		}
		return ruckgabe; 
	} 
	
	public String select_passwort(String bName)//gibt das passwort zum gegebenen nutzernamen zurück
	{
		
		boolean erfolg=true;
		String passwort =null;
		sqlBefehl=baukasten.select_passwort();
		try
		{
			PreparedStatement vorbereiteteAussage = verbindungLogin.prepareStatement(sqlBefehl);
			vorbereiteteAussage.setString(1, bName);
			ResultSet ergebnis = vorbereiteteAussage.executeQuery();
			ergebnis.next();
			passwort=ergebnis.getString(1);
		}
		catch (SQLException e)
		{
			return passwort;//datenbankfehler
		}
		return passwort; 
	} 
	
	public boolean[] nutzernameExistent(String bName)//<------nutzt die frei abfrage um zu bestimmen ob der name existiert
	{
		boolean[] a=nutzerNameFreiFragezeichen(bName);
		if (a[1])
		{
			a[1]=false;//name existiert nicht
		}
		else
		{
			a[1]=true;//name existiert
		}
		return a;
	}
	
	public ResultSet select_Nachrichten_von_Chatroom(String bName)//gibt die nachrichten eines chatrooms zurück
	{
		boolean erfolg=true;
		ResultSet ruckgabe = null;
		sqlBefehl=baukasten.select_nachricht_in_chatroom();
		try
		{
			PreparedStatement vorbereiteteAussage = verbindungDatenKrake.prepareStatement(sqlBefehl);
			vorbereiteteAussage.setString(1, bName);
			ResultSet ergebnis = vorbereiteteAussage.executeQuery();	
			ruckgabe=ergebnis;
		}
		catch (SQLException e)
		{
			return ruckgabe;//datenbankfehler
		}
		return ruckgabe; 
	}
	//=================================================================================================================Select ende
}