package chatClient;

import java.awt.Color;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import Message.nachrichtP.Nachricht;
import privatChat.PrivatChat;
import privatChat.PrivatChatSenden;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.text.AttributeSet.ColorAttribute;
import java.awt.event.MouseAdapter;

/////////////////////////////////////////////////////////////////////////////////////////////////
// TO-DO´s Client-Control:
//
// -> ArrayListe von Usern PrivatChat füllt sich teilweise doppelt?
//
// -> readMessage gibt Exception beim disconnect vom User aus (Programm geht aber trotzdem..?)
//    ....versucht mit Login boolean zu lösen, klappt aber grade noch nicht..
//
/////////////////////////////////////////////////////////////////////////////////////////////////

public class ClientControl implements Runnable, Serializable
{
	private boolean first = true;
	private boolean nachrichtPrivat = false;
	private boolean startWindow = false;
	private boolean login = false;
	private ClientConnectionThread start;
	private Color rot = new Color(255, 102, 102);
	private Color weiss = new Color(255, 255, 255, 255);
	protected Gui gui;
	protected int port;
	protected ObjectInputStream ois;
	protected ObjectOutputStream out;
	private Set<PrivatChat> privatChats;
	protected Socket socket;
	private String user;
	protected Thread read;
	protected VerbindungsGUI startGui;

	// Nachrichten Globaler Chat
	private ArrayList<Nachricht> messagesArray = new ArrayList<Nachricht>();
	protected DefaultListModel<Nachricht> messages = new DefaultListModel<Nachricht>();
	// User Globaler Chat
	protected ArrayList<String> aktiveClients = new ArrayList<String>();
	protected DefaultListModel<String> clients = new DefaultListModel<String>();
	// User möglich für PC
	protected ArrayList<String> teilnehmerPrivatChat = new ArrayList<String>();
	private DefaultListModel<String> clientsPC = new DefaultListModel<String>();
	// Auswahl User PC
	private ArrayList<String> auswahlClientsPC = new ArrayList<String>();
	protected DefaultListModel<String> choosenClients = new DefaultListModel<String>();

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ClientControl()
	{
		startGui = new VerbindungsGUI();
		gui = new Gui();
		setListener();
		setModel();
		privatChats = new HashSet<PrivatChat>();
	}

	public void switchGui()
	{
		Rectangle r;
		if (startWindow)
		{
			r = gui.getFrmBounds();
			startGui.setFrmBounds(r);
			startGui.hide(false);
			gui.hide(true);
			startWindow = false;
		}
		else
		{
			r = startGui.getFrmBounds();
			gui.setFrmBounds(r);
			gui.hide(false);
			startGui.hide(true);
			startWindow = true;
		}
	}

	private void setListener()
	{
		this.gui.addEingabeListener(l -> sendMessage());
		this.gui.addBtnStopListener(l -> stopClient());

		this.startGui.addBtnVerbindenListener(l ->
		{
			start = new ClientConnectionThread(this);
			start.start();
		});
		gui.addListListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseMoved(MouseEvent e)
			{
				setToolTip();
			};
		});

		this.gui.addAddListner(l -> addUserToNewChat());
		this.gui.addEntfListner(l -> entfUserFromNewChat());

		this.gui.setBtnNeuerChatActionListener(l -> neuenChatStarten());
		gui.setTextFieldGruppenNamenListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				gui.getTextFieldGruppenName().setText("");
				gui.getTextFieldGruppenName().setText(null);
				gui.getTextFieldGruppenName().setForeground(Color.black);
			}
		});
	}

	private void setModel()
	{
		this.gui.getList().setModel(messages);
		this.gui.getListUser().setModel(clients);
		this.gui.getListActiveUser().setModel(clientsPC);
		this.gui.getListChoosenUser().setModel(choosenClients);
	}

	public String getUser()
	{
		return user;
	}

	private void neuenChatStarten()
	{
		boolean schonVorhanden = false;
		String temp = gui.getTextFieldGruppenName().getText();
		if (temp.isEmpty() || gui.getTextFieldGruppenName().getText().equals("Gruppennamen eingeben"))
		{
			System.out.println("Bitte Chatraum benennen!");
		}
		else if (teilnehmerPrivatChat.size() < 1)
		{
			System.out.println("Bitte mindestens einen User hinzufügen");
		}
		else
		{
			PrivatChatSenden pcs = new PrivatChatSenden(temp, user, teilnehmerPrivatChat);
			for (PrivatChat chat : privatChats)
			{
				if (chat.getPcs().getHashcode() == pcs.hashCode())
				{
					schonVorhanden = true;
					System.out.println("PrivatChat schon vorhanden");
				}
			}
			if (schonVorhanden == false)
			{
				PrivatChat pc = new PrivatChat(teilnehmerPrivatChat, temp, user, this);
				privatChats.add(pc);
				sendPrivatChat(pc);
			}
		}
	}

	protected boolean checkPort()
	{
		boolean korrekt = false;
		try
		{
			int tempPort = startGui.getPort();

			if (tempPort >= 1024 && tempPort <= 49151)
			{
				port = tempPort;
				korrekt = true;
				this.gui.changeStatus("korrekter Port");
			}
			else
			{
				guiError("port");
				this.gui.changeStatus("kein korrekter Port");
			}

		}
		catch (NumberFormatException e)
		{
			guiError("port");
			this.gui.changeStatus("kein korrekter Port");
		}
		return korrekt;
	}

	protected boolean checkUsername()
	{
		if (startGui.getTextFieldUsername().getText().isBlank())
		{
			guiError("username");
			return false;
		}
		else
		{
			return true;
		}
	}

	protected void sendMessage()
	{
		try
		{
			Nachricht message;
			if (first)
			{
				user = startGui.getTextFieldUsername().getText();
				message = new Nachricht(user, false);
				out.writeObject(message);
				login = true;
				first = false;
			}
			else
			{
				message = new Nachricht(this.gui.getTextFieldEingabe().getText(), false);
				out.writeObject(message);
				messagesArray.add(message);
				akNachrichtenGlobal();
			}
		}
		catch (NullPointerException e)
		{
			gui.changeStatus("Noch nicht mit Server Verbunden!");
		}
		catch (Exception e)
		{
			System.out.println(e + "\n in sendMessage");
		}
		this.gui.getTextFieldEingabe().setText("");
	}

	private void readMessage()
	{
		boolean privatChatObjekt = false;
		
		Object o = new Object();
		
		if(login)
		{
			try
			{
				o = ois.readObject();
			}
			catch (SocketException e1)
			{
				stopClient();
			}
			catch (EOFException e2)
			{
				stopClient();
			}
			catch (Exception e)
			{
				System.out.println(e + "\n in readMessage");
			}

			if (o != null)
			{
				// Wenn empfangenes Objekt PrivatChat ist, wird ein neuer PrivatChat erstellt
				privatChatObjekt = privatChatStarten(o);

				// Ansonsten wird nachrichtEmpfangen() aufgerufen
				if (privatChatObjekt == false)
				{
					try
					{
						Nachricht n = (Nachricht)o;
						nachrichtEmpfangen(n);
					}
					catch (ClassCastException e)
					{
						System.out.println(e + "\n in readMessage");
					}
				}
			}
		}		
	}
		

	private void nachrichtEmpfangen(Nachricht n)
	{
		nachrichtPrivat = false;
		try
		{
			System.out.println("Test, kommen in nachrichtEmpfangen an!");
			// Liste mit PrivatChats wird durchlaufen, wenn PrivatChat mit richtigem
			// Hashcode vorhanden, wird die Nachricht im PrivatChat angezeigt
			privatChats.forEach(pc ->
			{
				if (n.getHashcode() == pc.getPcs().getHashcode())
				{
					pc.getController().receiveMessage(n);
					nachrichtPrivat = true;
				}
			});

			// Ansonsten wird die Nachricht im GlobalenChat angezeigt
			if (nachrichtPrivat == false)
			{
				if (n.getEmpfaenger() != null)
				{
					aktiveClients = new ArrayList<String>();
					n.getEmpfaenger().forEach(e -> aktiveClients.add(e));
					akClients();
				}
				getNewMessages(n);
			}
		}
		catch (ClassCastException e)
		{
			System.out.println(e + "\n in nachrichtEmpfangen");
		}
	}

	private boolean privatChatStarten(Object o)
	{
		boolean objektIstPrivatChat = false;
		boolean schonVorhanden = false;
		PrivatChatSenden pcs;
		PrivatChat pc;
		try
		{
			pcs = (PrivatChatSenden) o;
			pcs.setUser(this.user);
			for (PrivatChat chat : privatChats)
			{
				if (chat.getPcs().getHashcode() == pcs.getHashcode())
				{
					schonVorhanden = true;
					System.out.println("PrivatChat schon vorhanden");
				}
			}
			if (schonVorhanden == false)
			{
				pc = new PrivatChat(pcs.getEmpfaenger(), pcs.getChatName(), pcs.getUser(), this);
				privatChats.add(pc);
			}
			objektIstPrivatChat = true;
		}
		catch (ClassCastException e)
		{
		}
		return objektIstPrivatChat;
	}

	private void sendPrivatChat(PrivatChat pc)
	{
		try
		{
			out.writeObject(pc.getPcs());
		}
		catch (Exception e)
		{
			System.out.println(e + "\n in sendPrivatChat");
		}
	}

	public void sendMessagePrivatChat(Nachricht n)
	{
		try
		{
			out.writeObject(n);
		}
		catch (IOException e)
		{
			System.out.println(e + "\nin sendMessagePrivatChat");
		}
	}

	protected void getNewMessages(Nachricht n)
	{
		messagesArray.add(n);
		akNachrichtenGlobal();
	}

	protected void akNachrichtenGlobal()
	{
		messages.clear();
		messagesArray.forEach(n -> messages.addElement(n));
	}

	private void akClients()
	{
		clients.removeAllElements();
		clientsPC.removeAllElements();
		choosenClients.removeAllElements();

		// aktiveClients sortieren
		aktiveClients.sort((s1, s2) -> s1.compareTo(s2));

		// DefaultListModel User globaler Chat füllen
		aktiveClients.forEach(e -> clients.addElement(e));

		// Alle Elemente ungleich man selbst werden auswahlClientsPC hinzugefügt
		aktiveClients.forEach(e ->
		{
			if (!e.equals(user))
			{
				auswahlClientsPC.add(e);
			}
		});
		// DefaultListModel User PC füllen
		auswahlClientsPC.forEach(e -> clientsPC.addElement(e));

		// DefaultListModel User PC ausgewählt füllen
		teilnehmerPrivatChat.forEach(t -> choosenClients.addElement(t));
	}

	private void stopClient()
	{
		try
		{
			messagesArray.clear();
			aktiveClients.clear();
			teilnehmerPrivatChat.clear();
			auswahlClientsPC.clear();

			first = true;
			ois.close();
			out.close();
			socket.close();
		}
		catch (IOException e)
		{
			System.out.println(e + "\n in stopClient");
		}

		login = false;
		read.interrupt();
		start.interrupt();
		gui.changeStatus("verbindung getrennt");
		
		switchGui();
	}

	private void setToolTip()
	{
		int index = gui.hoveredItem();
		if (index != -1)
		{
			Nachricht n = messages.getElementAt(index);
			String time = n.getTimestamp().getDayOfMonth() + "." + n.getTimestamp().getMonthValue() + "."
					+ n.getTimestamp().getYear() + " | " + n.getTimestamp().getHour() + ":"
					+ n.getTimestamp().getMinute() + " Uhr";
			gui.getList().setToolTipText("<html> Absender-ID: " + n.getAbsenderId() + "<br> Absender: "
					+ n.getAbsender() + "<br> Versandt: " + time + "</html>");
		}
	}

	private void guiError(String error)
	{
		switch (error)
		{
		case "port":
			new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					for (int j = 0; j < 6; j++)
					{
						if (j % 2 == 0)
						{
							startGui.changePortColor(rot);
						}
						else
						{
							startGui.changePortColor(weiss);
						}
						try
						{
							Thread.sleep(125);
						}
						catch (InterruptedException e)
						{
							System.out.println(e + "\n in guiError - port");
						}
					}
				}
			}).start();
			break;

		case "username":
			new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					for (int j = 0; j < 6; j++)
					{
						if (j % 2 == 0)
						{
							startGui.changeUsernameColor(rot);
						}
						else
						{
							startGui.changeUsernameColor(weiss);
						}
						try
						{
							Thread.sleep(125);
						}
						catch (InterruptedException e)
						{
							System.out.println(e + "\n in guiError - username");
						}
					}
				}
			}).start();
			break;
		}
	}

	private void addUserToNewChat()
	{
		try
		{
			String selected = (String) gui.getListActiveUser().getSelectedValue();
			System.out.println("Add: " + selected);
			System.out.println("Vorher:");
			System.out.println(teilnehmerPrivatChat);
			System.out.println(auswahlClientsPC);
			teilnehmerPrivatChat.add(selected);
			auswahlClientsPC.remove(selected);
			System.out.println("Nachher:");
			System.out.println(teilnehmerPrivatChat);
			System.out.println(auswahlClientsPC);
			akPrivatChats();
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			System.out.println("nichts ausgewählt");
		}
	}

	private void entfUserFromNewChat()
	{
		try
		{
			String selected = (String) gui.getListChoosenUser().getSelectedValue();
			System.out.println("Remove : " + selected);
			teilnehmerPrivatChat.remove(selected);
			auswahlClientsPC.add(selected);
			akPrivatChats();
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			System.out.println("nichts ausgewählt");
		}
	}

	private void akPrivatChats()
	{
		// DefaulListModels clearen
		clientsPC.removeAllElements();
		choosenClients.removeAllElements();

		// ArrayListen sortieren
		try
		{
			auswahlClientsPC.sort((s1, s2) -> s1.compareTo(s2));
			teilnehmerPrivatChat.sort((s1, s2) -> s1.compareTo(s2));
		}
		catch(NullPointerException e)
		{
			System.out.println(e + "\n in akPrivatChats");
		}
		
		// DefaultListModels füllen
		auswahlClientsPC.forEach(s -> clientsPC.addElement(s));
		System.out.println("ClientsPC:");
		System.out.println(clientsPC);
		teilnehmerPrivatChat.forEach(s -> choosenClients.addElement(s));
		System.out.println("ChoosenClients:");
		System.out.println(choosenClients);
		
	}
	

	@Override
	public void run()
	{
		switchGui();
		while (!read.isInterrupted())
		{
			try
			{
				readMessage();
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				read.interrupt();
			}
		}

	}

}
