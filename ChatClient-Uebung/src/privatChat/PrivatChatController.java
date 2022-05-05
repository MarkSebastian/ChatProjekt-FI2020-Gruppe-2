package privatChat;


//AUf button chatstarten wird neues Object Privatchaterzeugt --- ArrayList mit den Teilnehmern und der gui -> gui wird über die klasse gestartet
//PrivatChat priv = new PrivatChat(PrivatChatGui gui, ArrayList<String> teilnehmer);


import javax.swing.DefaultComboBoxModel;

public class PrivatChatController
{
	private PrivatChatController controller;
	private PrivatChatGUI pgui;
	private PrivatChat privatChat;
	private DefaultComboBoxModel<String> teilnehmer;
//	private ArrayList<String>teilnehmer = new ArrayList<String>();
	
	public PrivatChatController(PrivatChat privatChat)
	{
		starten(privatChat);
	}

	// Client empfängt PrivatChat Objekt und startet eine neue GUI
	public void eingeladenenChatStarten(PrivatChat privatChat)
	{
		//PrivatChat privatChat = new PrivatChat(pgui, teilnehmer);
		
		starten(privatChat);   //du musst mehr schlafen fabi, das tut ja weh! aber richtig
	}
	
	private void starten(PrivatChat privatChat)
	{
		this.privatChat = privatChat;
		pgui = new PrivatChatGUI();
		pgui.getLblChatName().setText(privatChat.getChatName());
		teilnehmer = new DefaultComboBoxModel<String>();
		comboBoxAktualisieren();
		pgui.getListUser().setModel(teilnehmer);
	}

	
	//Combobox?
	private void comboBoxAktualisieren()
	{
		teilnehmer.removeAllElements();
		teilnehmer.addElement(privatChat.getUser());
		for (String s : privatChat.getEmpfaenger())
		{
			System.out.println(s);
			teilnehmer.addElement(s);	
		}		
	}
	
}
