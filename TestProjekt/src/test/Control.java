package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Control
{
	private String verbindungslink;
	private String sqlBefehl;
	private String sqlAnweisung;
	private String sqlHochDatum;
	private Statement stellungsnahme;
	private int intTest;
	private String stringTest="Wah";
	private String stringTest2="Guh";
	
	public Control()
	{
		verbindungslink="jdbc:ucanaccess://src/Testdatenbank.accdb";
		sqlBefehl="Select * from Test;"; //Auslesen
		sqlAnweisung="Insert into Test (ID, Feld1) values (?,?)";//Insert into
		sqlHochDatum="Update Test set Feld1=? where Feld1=?";//Update
		
		
		try (Connection verbindung = DriverManager.getConnection(verbindungslink,"","") )
		{
			System.out.println("verbindungaufgebaut");
			//=======================================================================================Update
			PreparedStatement vorbereiteteAussage2 = verbindung.prepareStatement(sqlHochDatum);
			vorbereiteteAussage2.setString(1,stringTest);
			vorbereiteteAussage2.setString(2,stringTest2);
			vorbereiteteAussage2.execute();
			//=======================================================================================Update ende
			//=======================================================================================Insert into
			PreparedStatement vorbereiteteAussage = verbindung.prepareStatement(sqlAnweisung);
			vorbereiteteAussage.setInt(1, intTest);
			vorbereiteteAussage.setString(2, stringTest);
			vorbereiteteAussage.executeUpdate();
			//=======================================================================================insert into ende
			//=======================================================================================Auslesen
			stellungsnahme = verbindung.createStatement();
			ResultSet ergebnis = stellungsnahme.executeQuery(sqlBefehl);
			while (ergebnis.next())
			{
				System.out.println(ergebnis.getInt("ID"));	
				System.out.println(ergebnis.getString("Feld1"));
			}
			//=======================================================================================Ende Auslesen
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	} 
}
