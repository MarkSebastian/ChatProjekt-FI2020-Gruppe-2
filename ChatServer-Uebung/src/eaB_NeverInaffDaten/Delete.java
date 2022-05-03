package eaB_NeverInaffDaten;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.SQLException;


public class Delete
{
	private Connection con;
	private String anweisung;
	
	public Delete(Connection con)
	{
		this.con=con;
	}
	
	public void deleteLogin(String name)
	{
		anweisung="Delete from ? where Benutzername=?";
		try
		{
			PreparedStatement presta= con.prepareStatement(anweisung);
			presta.setString(1, "Login_Daten");
			presta.setString(2, name);
			presta.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
}
