package eaB_NeverInaffDaten;

public class Control
{
	private String verbindungslinkLoginserver;
	private String verbindungslinkDatenbank;
	private String sqlBefehl;
	
	public Control()
	{
		verbindungslinkLoginserver="\"jdbc:ucanaccess://Login_DB.accdb\"";
		verbindungslinkDatenbank="\"jdbc:ucanaccess://Datenkrake.accdb\"";
		
	}
}
