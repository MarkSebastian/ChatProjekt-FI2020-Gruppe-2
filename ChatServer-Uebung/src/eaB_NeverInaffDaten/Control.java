package eaB_NeverInaffDaten;

public class Control
{
	private String verbindungslinkLoginserver;
	private String verbindungslinkDatenbank;
	private String sqlBefehl;
	
	public Control()
	{
		verbindungslinkLoginserver="\"jdbc:ucanaccess://src/Login_DB.accdb\"";
		verbindungslinkDatenbank="\"jdbc:ucanaccess://src/Datenkrake.accdb\"";
		
	}
}
