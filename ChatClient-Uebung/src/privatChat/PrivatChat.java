package privatChat;

import java.util.ArrayList;

public class PrivatChat
{
	private int flag;
	private PrivatChatController controller;
	private String chatName;
	private ArrayList<String> empfaenger;
	
	public PrivatChat(ArrayList<String> empfaenger)
	{
		this.empfaenger = empfaenger;
		starten();
	}
	
	public PrivatChat()
	{
		starten();
	}
	
	public void starten()
	{
		controller = new PrivatChatController();
	}
}
