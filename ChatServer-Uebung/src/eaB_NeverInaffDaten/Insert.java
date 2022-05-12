package eaB_NeverInaffDaten;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Insert
{
	//Bekommt die benötigten Daten für Insert-Anweisungen und schreibt die Daten auf die Datenbank
	private Connection conKrake;
	private Connection conLogin;
	private String anweisung;
	
	public Insert(Connection conKrake, Connection conLogin)
	{
		this.conKrake=conKrake;
		this.conLogin= conLogin;
	}
	
	/*public void InsertLogin(String name,String passwort)
	{
		//Insert für neuen User
		//Schreibt User sowohl auf die Login DB als auch auf die Client Tabelle in Datenkrake
		
		anweisung="Insert into Login_Daten(Benutzername,Passwort) values(?,?)";
		try
		{
			PreparedStatement presta= conLogin.prepareStatement(anweisung);
			presta.setString(1, name);
			presta.setString(2, passwort);
			presta.executeUpdate();
			anweisung="Insert into Client(Benutzername) values ?";
			presta=conKrake.prepareStatement(anweisung);
			presta.setString(1, name);
			presta.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void InsertLoginzeit(String nutzer, LocalDateTime datumbeginn, LocalDateTime datumende, String ip  )
	{
		anweisung="Insert into Login_Daten(Benutzername,Passwort) values(?,?)";
		try
		{
			PreparedStatement presta= conLogin.prepareStatement(anweisung);
			presta.setString(1, name);
			presta.setString(2, passwort);
			presta.executeUpdate();
			anweisung="Insert into Client(Benutzername) values ?";
			presta=conKrake.prepareStatement(anweisung);
			presta.setString(1, name);
			presta.executeUpdate();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
