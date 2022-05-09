package privatChat;


import java.io.Serializable;

//AUf button chatstarten wird neues Object Privatchaterzeugt --- ArrayList mit den Teilnehmern und der gui -> gui wird über die klasse gestartet
//PrivatChat priv = new PrivatChat(PrivatChatGui gui, ArrayList<String> teilnehmer);


import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import Message.nachrichtP.Nachricht;
import chatClient.ClientControl;

public class PrivatChatController implements Serializable
{
	private static final long serialVersionUID = 1L;
	private PrivatChatGUI pgui;
	private PrivatChat privatChat;
	private DefaultComboBoxModel<String> teilnehmer = new DefaultComboBoxModel<String>();
	private DefaultListModel<Nachricht> nachrichten = new DefaultListModel<Nachricht>();	
	
	public PrivatChatController(PrivatChat privatChat)
	{
		starten(privatChat);
	}
	
	private void starten(PrivatChat privatChat)
	{
		this.privatChat = privatChat;
		pgui = new PrivatChatGUI();
		pgui.getLblChatName().setText(privatChat.getPcs().getChatName());
		addListener();
		setModel();
		comboBoxAktualisieren();
	}

	private void addListener()
	{
		this.pgui.setTextFieldNachrichtListener(l -> sendMessage());		
	}
	
	private void setModel()
	{
		pgui.getListChat().setModel(nachrichten);
		pgui.getListUser().setModel(teilnehmer);
	}	

	private void comboBoxAktualisieren()
	{
		teilnehmer.removeAllElements();
		teilnehmer.addElement(privatChat.getPcs().getUser());
		privatChat.getPcs().getEmpfaenger().forEach(s -> teilnehmer.addElement(s));
	}

	private void sendMessage()
	{
		String s = pgui.getTextFieldNachricht().getText();
		Nachricht n = new Nachricht(s, privatChat.getPcs().getEmpfaenger(), privatChat.getPcs().getHashcode());
		privatChat.getCc().sendMessagePrivatChat(n);
		nachrichten.addElement(n);
		this.pgui.getTextFieldNachricht().setText("");
	}	
	
	public void receiveMessage(Nachricht n)
	{
		nachrichten.addElement(n);
	}
}
