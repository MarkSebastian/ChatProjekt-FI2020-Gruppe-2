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
	private String sqlLochen;
	private String sqlTestbefehl;
	private Statement stellungsnahme;
	private String stringTest="Wah";
	private String stringTest2="Guh";
	
	private int intTest;
	private String a="Insert ";
	private String b="into ";
	private String c="Test ";
	private String d="(Id,Feld1) ";
	private String e="Values ";
	private String f="( ";
	private String g="?,?";
	private String h=")";
	

	
	public Control()
	{
		verbindungslink="jdbc:ucanaccess://Testdatenbank.accdb";
		sqlBefehl="Select * from Test;"; //Auslesen
		sqlAnweisung="Insert into Test (ID, Feld1) values (?,?)";//Insert into
		sqlTestbefehl=a+b+c+d+e+f+g+h; 
		sqlHochDatum="Update Test set Feld1=? where Feld1=?";//Update
		sqlLochen="Delete from Test where Feld1=?";//Daten Löschen
		
		
		try (Connection verbindung = DriverManager.getConnection(verbindungslink,"","") )
		{
			System.out.println("verbindungaufgebaut");
			PreparedStatement vorbereiteteAussage4 = verbindung.prepareStatement(sqlTestbefehl);
			vorbereiteteAussage4.setInt(1, intTest);
			vorbereiteteAussage4.setString(2, "KlapptDas?");
			vorbereiteteAussage4.executeUpdate();
			//=======================================================================================Löschen
			PreparedStatement vorbereiteteAussage3 = verbindung.prepareStatement(sqlLochen);
			vorbereiteteAussage3.setString(1, stringTest);
			vorbereiteteAussage3.execute();
			//=======================================================================================Löschenende
			//=======================================================================================Update
			PreparedStatement vorbereiteteAussage2 = verbindung.prepareStatement(sqlHochDatum);
			vorbereiteteAussage2.setString(1,stringTest);
			vorbereiteteAussage2.setString(2,stringTest2);
			vorbereiteteAussage2.execute();
			//=======================================================================================Update ende
			//=======================================================================================Insert into
			PreparedStatement vorbereiteteAussage = verbindung.prepareStatement(sqlAnweisung);
			vorbereiteteAussage.setInt(1, intTest);
			vorbereiteteAussage.setString(2, "");
			vorbereiteteAussage.executeUpdate();
			//=======================================================================================insert into ende
			//=======================================================================================Auslesen
			stellungsnahme = verbindung.createStatement();
			ResultSet ergebnis = stellungsnahme.executeQuery(sqlBefehl);
			while (ergebnis.next())
			{
				System.out.println(ergebnis.getInt("ID")+" "+ergebnis.getString("Feld1"));	
				//System.out.println(ergebnis.getString("Feld1"));
			}
			//=======================================================================================Ende Auslesen
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
	} 
}
