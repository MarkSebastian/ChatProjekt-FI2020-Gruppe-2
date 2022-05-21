package Message.nachrichtP;

import java.io.Serializable;

public class FehlerNachricht
{
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;

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
	
	public boolean isDatenbankFehler()
	{
		return datenbankFehler;
	}

	public boolean isNutzernameVergebenFehler()
	{
		return nutzernameVergebenFehler;
	}

	public boolean isNutzernameFehler()
	{
		return nutzernameFehler;
	}

	public boolean isPasswortFehler()
	{
		return passwortFehler;
	}
}
