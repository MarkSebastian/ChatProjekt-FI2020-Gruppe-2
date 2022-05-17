package chatServer;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import Message.nachrichtP.Nachricht;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Control
{

	protected Gui gui;
	private ServerSocket server;
	protected ServerConnectionThread connect;
	protected DefaultListModel<Nachricht> messages = new DefaultListModel<Nachricht>();
	private ArrayList<Client> clients = new ArrayList<Client>();
	private DefaultListModel<Client> clientListe = new DefaultListModel<Client>();
	
	public Control()
	{
		gui = new Gui();
		messages.clear();
		akList();
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
		gui.addListListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
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

		} catch (UnknownHostException e1)
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
		} catch (Exception e)
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
		akList();
	}

	private void sendMessage()
	{
		Nachricht message = new Nachricht(this.gui.getTextNachrichtenEingabe().getText(), true);
		if(clients.isEmpty()==false)
		{
			broadcastMessage(message);
		}
	}
	
	protected void getNewMessages(Nachricht n)
	{
		messages.addElement(n);
		akList();
	}

	protected void broadcastMessage(Nachricht n, Client from)
	{
		for (Client c : clients)
		{
			if (c != from)
			{
				c.sendMessage(n);
			}
		}
	}
	
	protected void broadcastMessage(Nachricht n)
	{
		messages.addElement(n);
		akList();
		
		for (Client c : clients)
		{
				c.sendMessage(n);
		}
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
		} catch (IOException e)
		{
			System.out.println(e + "\n in stoppen");
		}
		clients.clear();
		akClientList();
		System.out.println("Server gestoppt.");
	}

	protected void akList()
	{
		this.gui.getList().setModel(messages);
	}
	
	private void setToolTip()
	{
		int index = gui.hoveredItem();
		if(index != -1)
		{
			Nachricht n = messages.getElementAt(index);
			String time = n.getTimestamp().getDayOfMonth() + "." + n.getTimestamp().getMonthValue() + "." + n.getTimestamp().getYear() + " | " +
							n.getTimestamp().getHour() + ":" + n.getTimestamp().getMinute() + " Uhr";
			gui.getList().setToolTipText("<html> Absender-ID: " + n.getAbsenderId() +
										"<br> Absender: " + n.getAbsender() +
										"<br> Versandt: " + time + "</html>");
		}
		
	}
	
	protected DefaultListModel<String> clientListeAbspecken()
	{
		DefaultListModel<String> nl = new DefaultListModel<String>();
		
		for(Client c : clients)
		{
			String s = c.getId() + " " + c.getName();
			nl.addElement(s);
		}
		
		return nl;
	}
	
	protected void akClientList()
	{
		clientListe.clear();
		
		for(Client c : clients)
		{
			clientListe.addElement(c);
		}
		
		this.gui.getListUser().setModel(clientListe);
	}
	
	public void setImage(BufferedImage img)
	{
		ImageIcon icon = new ImageIcon(img);
		gui.getLblBildLabel().setIcon(icon);
	}

}