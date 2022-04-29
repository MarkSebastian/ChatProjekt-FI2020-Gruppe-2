package nachrichtP;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Nachricht implements Serializable
{	
	private static final long serialVersionUID = 1L;
	private String absender;
	private int absenderId;
	final private LocalDateTime timestamp = LocalDateTime.now();
	private String nachricht;
	private int flag;
	private ArrayList<String> empfaenger;

	public int getFlag()
	{
		return flag;
	}

	public ArrayList<String> getEmpfaenger()
	{
		return empfaenger;
	}
	
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

	public Nachricht(String nachricht)
	{
		this.absender = "Server";
		this.absenderId = 0;
		this.nachricht = nachricht;
	}

	@Override
	public String toString()
	{
		return absender + ": " + nachricht;
	}

}
