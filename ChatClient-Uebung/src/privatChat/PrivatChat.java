package privatChat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import chatClient.ClientControl;

public class PrivatChat implements Serializable
{
	private static final long serialVersionUID = 1L;
	private long hashcode;
	private PrivatChatController controller;
	private String chatName;
	private String user;
	private ArrayList<String> empfaenger;
	private ClientControl cc;
	private PrivatChatSenden pcs;

	public PrivatChat(ArrayList<String> empfaenger, String chatName, String user, ClientControl cc)
	{
		// Umschreiben
		this.chatName = chatName;
		this.empfaenger = empfaenger;
		this.user = user;
		
		this.cc = cc;
		this.pcs = new PrivatChatSenden(chatName, user, empfaenger);
		starten();
		hashcode = hashCode();
	}
	
	// Leerer Konstruktor oder abfangen, dass kein PrivChat ohne Empf�nger gestartet werden kann?
	public PrivatChat(ClientControl cc)
	{
		this.cc = cc;
		starten();
	}
	
	public void starten()
	{
		controller = new PrivatChatController(this);		
	}	
	
	public String getChatName()
	{
		return chatName;
	}
	
	public PrivatChatController getController()
	{
		return controller;
	}

	public ArrayList<String> getEmpfaenger()
	{
		return empfaenger;
	}
	
	public long getHashcode()
	{
		return hashcode;
	}

	public ClientControl getCc()
	{
		return cc;
	}
	
	public PrivatChatSenden getPcs()
	{
		return pcs;
	}

	public String getUser()
	{
		return user;
	}
	
	public void setCc(ClientControl cc)
	{
		this.cc = cc;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(chatName, controller, empfaenger);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrivatChat other = (PrivatChat) obj;
		return Objects.equals(chatName, other.chatName) && Objects.equals(controller, other.controller)
				&& Objects.equals(empfaenger, other.empfaenger);
	}
	
	
}
