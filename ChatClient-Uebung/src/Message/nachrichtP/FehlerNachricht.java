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

	protected boolean isDatenbankFehler()
	{
		return datenbankFehler;
	}

	protected boolean isNutzernameVergebenFehler()
	{
		return nutzernameVergebenFehler;
	}

	protected boolean isNutzernameFehler()
	{
		return nutzernameFehler;
	}

	protected boolean isPasswortFehler()
	{
		return passwortFehler;
	}
}
