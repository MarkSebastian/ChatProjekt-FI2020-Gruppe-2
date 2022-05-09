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
	private ArrayList<Client> clients = new ArrayList<Client>();
	private DefaultListModel<Client> clientListe = new DefaultListModel<Client>();
	private ArrayList<String>empfaenger = new ArrayList<String>();

	
	public ServerControl()
	{
		gui = new Gui();
		messages.clear();
		this.gui.getList().setModel(messages);
		addListener();
		printAddress();
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
		connect = new ServerConnectionThread(server, clients, this);
		connect.start();
	}

	private void clearLists()
	{
		clients.clear();
		messages.clear();
	}

	private void sendMessage()
	{
		Nachricht message = new Nachricht(this.gui.getTextNachrichtenEingabe().getText(), true);
		if (!clients.isEmpty())
		{
			broadcastMessage(message);
		}

	}

	protected void getNewMessages(Nachricht n)
	{
		messages.addElement(n);
	}

	protected void broadcastMessage(Nachricht n, Client from)
	{
		clients.forEach(e ->
		{
			if (e != from)
				e.sendMessage(n);
		});
	}
	
	// PrivatChatObjekt hat ArrayList mit Empfängern, nur an diese Liste wird der PrivatChat geschickt
	protected void broadcastPrivatChat(PrivatChatSenden pcs)
	{
		clients.forEach(e -> pcs.getEmpfaenger().forEach(s ->
		{
			if(!pcs.getUser().equals(e)) 
				if (e.getName().equals(s))
					e.sendMessage(pcs);
			}));
	}

	// Nachricht hat eine ArrayList mit den empfaengern des privatChats, die
	// Nachricht wird auch nur an diese Clients weitergeleitet
	protected void broadcastPrivatMessage(Nachricht n)
	{
		clients.forEach(e -> n.getEmpfaenger().forEach(s ->
		{
			System.out.println(e);
			if (e.getName().equals(s))
			{
				e.sendMessage(n);				
				System.out.println("Broadcast message: " + n + "\nan " + e);
			}
		}));
	}


	protected void broadcastMessage(Nachricht n)
	{
		messages.addElement(n);

		clients.forEach(e -> e.sendMessage(n));

		this.gui.getTextNachrichtenEingabe().setText("");
	}

	protected void closeClient(Client c)
	{
		clients.remove(c);
		akClientList();
	}

	private void stoppen()
	{
		for (Client c : clients)
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
		clients.clear();
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

	protected DefaultListModel<String> clientListeAbspecken()
	{
		DefaultListModel<String> nl = new DefaultListModel<String>();
		//empfaenger.forEach(e -> );
		for (Client c : clients)
		{
			String s = c.getId() + " " + c.getName();
			nl.addElement(s);
		}

		return nl;
	}
	
	
	protected ArrayList<String> listeFuellen()
	{
		ArrayList<String> temp = new ArrayList<String>();
		
		return temp;
	}

	protected void akClientList()
	{
		empfaenger.clear();
		clients.forEach(e -> empfaenger.add(e.getName()));
		clientListe.clear();

		for (Client c : clients)
		{
			clientListe.addElement(c);
		}

		this.gui.getListUser().setModel(clientListe);
	}

}