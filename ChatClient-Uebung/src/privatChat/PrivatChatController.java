package privatChat;

public class PrivatChatController
{
	private PrivatChatController controller;
	private PrivatChatGUI pgui;
	private PrivatChat privatChat;
	
	public PrivatChatController()
	{
		pgui = new PrivatChatGUI();
	}

	// Client empf�ngt PrivatChat Objekt und startet eine neue GUI
	public void eingeladenenChatStarten(PrivatChat privatChat)
	{
		this.privatChat = privatChat;
		pgui = new PrivatChatGUI();
	}
	
}
