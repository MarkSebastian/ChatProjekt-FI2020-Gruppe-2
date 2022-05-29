package Message.nachrichtP;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.sound.sampled.Clip;
import javax.swing.DefaultListModel;

public class Nachricht implements Serializable
{

	private static final long serialVersionUID = 1L;
	private String absender;
	private int absenderId;
	final private LocalDateTime timestamp = LocalDateTime.now();
	private Clip clip;
	private String nachricht;
	
	private DefaultListModel<String> activeClients = null;

	public String getAbsender()
	{
		return absender;
	}

	public void setAbsender(String absender)
	{
		this.absender = absender;
	}

	public int getAbsenderId()
	{
		return absenderId;
	}

	public void setAbsenderId(int id)
	{
		this.absenderId = id;
	}

	public LocalDateTime getTimestamp()
	{
		return timestamp;
	}

	public String getNachricht()
	{
		return nachricht;
	}

	
	public DefaultListModel<String> getListClients()
	{
		return activeClients;
	}

	
	public Nachricht(String nachricht, boolean isServer)
	{
		if (isServer == true)
		{
			this.absender = "Server";
		}
		else
		{
			this.absender = "Ich";
		}
		this.absenderId = 0;
		this.nachricht = nachricht;
	}

	public Nachricht(String nachricht, DefaultListModel<String> clientListe)
	{
		this.absender = "";
		this.absenderId = 0;
		this.nachricht = nachricht;
		this.activeClients = clientListe;
	}

	@Override
	public String toString()
	{
		if (absender == "")
		{
			return nachricht;
		}
		else
		{
			return absender + ": " + nachricht;
		}
	}
}