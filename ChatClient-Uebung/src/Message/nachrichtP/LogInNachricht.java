package Message.nachrichtP;


public class LogInNachricht
{
	private String benutzerName;
	private String passwort;
	private boolean flag;
	
	public LogInNachricht()
	{
		
	}
	
	public LogInNachricht(String benutzerName, String passwort, boolean flag)
	{
		this.benutzerName = benutzerName;
		this.passwort = passwort;
		this.flag = flag;
	}

	
	public String getBenutzerName()
	{
		return benutzerName;
	}
	public void setBenutzerName(String benutzerName)
	{
		this.benutzerName = benutzerName;
	}
	public String getPasswort()
	{
		return passwort;
	}
	public void setPasswort(String passwort)
	{
		this.passwort = passwort;
	}
	public boolean getFlag()
	{
		return flag;
	}
	public void setFlag(boolean flag)
	{
		this.flag = flag;
	}
}
