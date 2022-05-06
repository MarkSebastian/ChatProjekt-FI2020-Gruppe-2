package privatChat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class PrivatChat implements Serializable
{
	private static final long serialVersionUID = 1L;
	private long hashcode;
	private PrivatChatController controller;
	private String chatName;
	private String user;
	private ArrayList<String> empfaenger;
	
	public PrivatChat(ArrayList<String> empfaenger, String chatName, String user)
	{
		this.chatName = chatName;
		this.empfaenger = empfaenger;
		this.user = user;
		starten();
		hashcode = hashCode();
	}
	
	// Leerer Konstruktor oder abfangen, dass kein PrivChat ohne Empfänger gestartet werden kann?
	public PrivatChat()
	{
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
	
	public ArrayList<String> getEmpfaenger()
	{
		return empfaenger;
	}
	
	public String getUser()
	{
		return user;
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
