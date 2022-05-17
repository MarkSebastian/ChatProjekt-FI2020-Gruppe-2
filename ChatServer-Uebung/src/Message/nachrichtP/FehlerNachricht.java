package Message.nachrichtP;

public class FehlerNachricht
{
	private boolean datenbankFehler;
	
	private boolean nutzernameVergebenFehler;
	
	private boolean nutzernameFehler;
	
	private boolean passwortFehler;

	public FehlerNachricht(boolean datenbankFehler, boolean nutzernameVergebenFehler, boolean nutzernameFehler, boolean passwortFehler)
	{
		this.datenbankFehler = datenbankFehler;
		this.nutzernameVergebenFehler = nutzernameVergebenFehler;
		this.nutzernameFehler = nutzernameFehler;
		this.passwortFehler = passwortFehler;
	}
}
