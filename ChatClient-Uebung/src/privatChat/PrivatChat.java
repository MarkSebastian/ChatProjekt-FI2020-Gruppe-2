package privatChat;

import java.util.ArrayList;
import java.util.Objects;

public class PrivatChat 
{
	private long hashcode;
	private PrivatChatController controller;
	private String chatName;
	private ArrayList<String> empfaenger;
	
	public PrivatChat(ArrayList<String> empfaenger, String chatName)
	{
		this.chatName = chatName;
		this.empfaenger = empfaenger;
		hashcode = hashCode();
		starten();
	}
	
	// Leerer Konstruktor oder abfangen, dass kein PrivChat ohne Empf�nger gestartet werden kann?
	public PrivatChat()
	{
		starten();
	}
	
	public void starten()
	{
		controller = new PrivatChatController();		
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
