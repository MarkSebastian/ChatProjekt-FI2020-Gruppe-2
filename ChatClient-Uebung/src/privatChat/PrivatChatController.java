package privatChat;

import javax.swing.DefaultComboBoxModel;

public class PrivatChatController
{
	private PrivatChatController controller;
	private PrivatChatGUI pgui;
	private PrivatChat privatChat;
	private DefaultComboBoxModel<String> teilnehmer;
	
	public PrivatChatController(PrivatChat privatChat)
	{
		starten(privatChat);
	}

	// Client empfängt PrivatChat Objekt und startet eine neue GUI
	public void eingeladenenChatStarten(PrivatChat privatChat)
	{
		starten(privatChat);
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
