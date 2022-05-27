package chatServer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import Message.nachrichtP.Nachricht;
import privatChat.PrivatChatSenden;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

public class ServerControl
{

	private Gui gui;
	private ServerSocket server;
	protected ServerConnectionThread connect;
	protected DefaultListModel<Nachricht> messages = new DefaultListModel<Nachricht>();
	private DefaultListModel<Client> clientListe = new DefaultListModel<Client>();

	public ServerControl()
	{
		gui = new Gui();
		messages.clear();
		this.gui.getList().setModel(messages);
		addListener();
		setModel();
		printAddress();
	}

	public ServerConnectionThread getConnect()
	{
		return connect;
	}

	private void addListener()
	{
		this.gui.addBtnStartListener(l ->
		{
			this.gui.showStoppen();
			starten();
		});
		this.gui.addBtnStoppenListener(l ->
		{
			stoppen();
			this.gui.showStart();
		});
		this.gui.addPortEingabeListener(l -> this.gui.showStart());
		this.gui.addNewNachrichtListener(l -> sendMessage());
		gui.addListListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseMoved(MouseEvent e)
			{
				setToolTip();
			};
		});
	}

	private void setModel()
	{
		this.gui.getListUser().setModel(clientListe);
	}

	private void printAddress()
	{
		try
		{
			InetAddress adress = InetAddress.getLocalHost();
			String lh = adress.getHostAddress();
			this.gui.titelAendern(lh);

		}
		catch (UnknownHostException e1)
		{
			System.out.println(e1 + "\n in printAddress");
		}
	}

	private void starten()
	{
		clearLists();

		String port = this.gui.getTextFieldPortNr().getText();

		try
		{
			server = new ServerSocket(Integer.parseInt(port));
		}
		catch (Exception e)
		{
			System.out.println(e + "\n in starten");
		}
		System.out.println("Server gestartet!");
		connect = new ServerConnectionThread(server, this);
		connect.start();
	}

	private void clearLists()
	{
		clientListe.clear();
		messages.clear();
	}

	private void sendMessage()
	{
		Nachricht message = new Nachricht(this.gui.getTextNachrichtenEingabe().getText(), true);
		if (!connect.getClients().isEmpty())
		{
			broadcastMessage(message);
		}

	}

	protected void getNewMessages(Nachricht n)
	{
		messages.addElement(n);
	}
		
	protected void broadcastMessage(Nachricht n)
	{
		messages.addElement(n);
		connect.getClients().forEach(e -> e.sendMessage(n));
		this.gui.getTextNachrichtenEingabe().setText("");
	}

	protected void broadcastMessage(Nachricht n, Client from)
	{
		connect.getClients().forEach(e ->
		{
			if (e != from)
				e.sendMessage(n);
		});
	}

	// PrivatChatObjekt hat ArrayList mit Empfängern, nur an diese Liste wird der
	// PrivatChat geschickt
	protected void broadcastPrivatChat(PrivatChatSenden pcs)
	{
		connect.getClients().forEach(e -> pcs.getEmpfaenger().forEach(s ->
		{
			if (!pcs.getUser().equals(e.getName()))
				if (e.getName().equals(s))
					e.sendMessage(pcs);
		}));
	}

	// Nachricht hat eine ArrayList mit den empfaengern des privatChats, die
	// Nachricht wird auch nur an diese Clients weitergeleitet
	protected void broadcastPrivatMessage(Nachricht n)
	{
		System.out.println(n.getAbsender());
		connect.getClients().forEach(e -> n.getEmpfaenger().forEach(s ->
		{
			if (e.getName().equals(s))
				e.sendMessage(n);
		}));
	}

	protected void closeClient(Client c)
	{
		connect.getClients().remove(c);
		connect.getEmpfaenger().remove(c.getName());
		akClientList();
	}

	private void stoppen()
	{
		for (Client c : connect.getClients())
		{
			c.stopClient();
		}
		connect.interrupt();
		try
		{
			server.close();
		}
		catch (IOException e)
		{
			System.out.println(e + "\n in stoppen");
		}
		connect.getClients().clear();
		akClientList();
		System.out.println("Server gestoppt.");
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



	protected ArrayList<String> listeFuellen()
	{
		ArrayList<String> temp = new ArrayList<String>();
		return temp;
	}

	protected void akClientList()
	{
		clientListe.clear();
		connect.getClients().forEach(c -> clientListe.addElement(c));
	}

}