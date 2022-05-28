package Message.nachrichtP;
//CLIENT

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

public class Nachricht implements Serializable
{	
	private static final long serialVersionUID = -2962890154987449386L;
	private long hashcode;
	private String absender;
	private int absenderId;
	final private LocalDateTime timestamp = LocalDateTime.now();
	private String nachricht;
	private ArrayList<String> empfaenger = new ArrayList<String>();

	public Nachricht(String nachricht, boolean isServer)
	{
		if(isServer == true)
		{
		this.absender = "Server";
		}
		else
		{
			this.absender = "Ich";
		}
		this.absenderId = 0;
		this.nachricht = nachricht;
		this.hashcode = 0;
	}
	
	public Nachricht(String nachricht, ArrayList<String> empfaenger, boolean isServer)
	{
		if(isServer == true)
		{
		this.absender = "Server";
		}
		else
		{
			this.absender = "Ich";
		}
		this.absenderId = 0;
		this.nachricht = nachricht;
		this.empfaenger = empfaenger;
		this.hashcode = 0;
	}
	
	public Nachricht(String nachricht, ArrayList<String> empfaenger)
	{
		this.absender = "";
		this.nachricht = nachricht;
		this.empfaenger = empfaenger;
		this.hashcode = 0;
	}
	
	public Nachricht(String absender, String nachricht, ArrayList<String> empfaenger, long hashcode)
	{
		this.absender = absender;
		this.nachricht = nachricht;
		this.empfaenger = empfaenger;
		empfaenger.remove(absender);
		this.hashcode = hashcode;
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

	public long getHashcode()
	{
		return hashcode;
	}

	@Override
	public String toString()
	{
		if(absender == "")
		{
			return nachricht;
		}
		else
		{
		return absender + ": " + nachricht;
		}
	}
	
}