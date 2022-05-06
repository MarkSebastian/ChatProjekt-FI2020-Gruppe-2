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


import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.text.AttributeSet.ColorAttribute;
import java.awt.event.MouseAdapter;

public class ClientControl implements Runnable
{

	protected Gui gui;
	protected VerbindungsGUI startGui;
	protected int port;
	protected Socket socket;
	protected DefaultListModel<Nachricht> messages = new DefaultListModel<Nachricht>();
	protected DefaultListModel<String> clients = new DefaultListModel<String>();
	protected DefaultListModel<String> choosenClients = new DefaultListModel<String>();
	protected ArrayList<String> teilnehmerPrivatChat = new ArrayList<String>();
	protected ArrayList<String> aktiveTeilnehmer = new ArrayList<String>();
	


	protected Thread read;
	private ClientConnectionThread start;

	protected ObjectInputStream ois;
	protected ObjectOutputStream out;

	private boolean startWindow = false;

	private Color rot = new Color(255, 102, 102);
	private Color weiss = new Color(255, 255, 255, 255);

	private boolean first = true;
	private Set<PrivatChat> privatChats;	// Funktion ob Chat doppelt muss noch geschrieben werden
	private String user;


	public ClientControl()
	{
		startGui = new VerbindungsGUI();
		gui = new Gui();
		setListener();
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

	private void neuenChatStarten()
	{
		String temp = gui.getTextFieldGruppenName().getText();
		if(temp.isEmpty() || gui.getTextFieldGruppenName().getText().equals("Gruppennamen eingeben"))
		{
			System.out.println("Bitte Chatraum benennen!");
		}
		// REMINDER: Hashset?
		else if(teilnehmerPrivatChat.size() < 1)
		{		
			System.out.println("Bitte mindestens einen User hinzufügen");
		}
		else
		{
			System.out.println(gui.getTextFieldGruppenName().getText());
			PrivatChat pc = new PrivatChat(teilnehmerPrivatChat, temp, user);
			privatChats.add(pc);	
			sendPrivatChat(pc);
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
				first = false;
			}
			else
			{
				message = new Nachricht(this.gui.getTextFieldEingabe().getText(), false);
				out.writeObject(message);
				messages.addElement(message);
				akList();
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
	
	private void sendPrivatChat(PrivatChat pc)
	{
		try
		{
			out.writeObject(pc);
		}
		catch(Exception e)
		{
			System.out.println(e + "\n in sendPrivatChat");
		}
	}

	private void readMessage()
	{
		boolean privatChatObjekt = false;
		Object o = null;

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
			System.out.println(e + "\n Test in readMessage");
		}

		if(o != null) 
		{
			try
			{
				// Wenn PrivatChat als Objekt verschickt wird -> neuen PrivatChatGUI starten
				PrivatChat pc = (PrivatChat)o;
				privatChatObjekt = true;
				privatChats.add(pc);
				pc.starten();
			}
			catch(ClassCastException e)
			{
			}
			
			if(privatChatObjekt == false)
			{
				try
				{
					// Ansonsten wird Message gelesen
					Nachricht message = (Nachricht) o;		
					System.out.println(message.toString());
					if (message.getListClients() != null)
					{
						this.clients = message.getListClients();
						akClientList();
					}
					getNewMessages(message);
				}
				catch (Exception e)
				{
				}
			}
		}		
	}

	protected void getNewMessages(Nachricht n)
	{
		messages.addElement(n);
		akList();
	}

	protected void akList()
	{
		this.gui.getList().setModel(messages);
	}

	protected void akClientList()
	{
		this.gui.getListUser().setModel(clients);
		this.gui.getListActiveUser().setModel(clients);

		// REMINDER: Passiert hier was?
		aktiveTeilnehmer.forEach(e -> clients.addElement(e));
		
		aktiveTeilnehmer.forEach(e ->	
		{
			if(e.equals(user))
			{
				aktiveTeilnehmer.remove(e);
			}
		});
	}

	private void stopClient()
	{
		try
		{
			first = true;
			ois.close();
			out.close();
			socket.close();
		}
		catch (IOException e)
		{
			System.out.println(e + "\n in stopClient");
		}

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
			int temp = 0;
			int index = gui.getListActiveUser().getSelectedIndex();
			String selected = clients.getElementAt(index);
			for (String e : teilnehmerPrivatChat)
			{
				if(e.equals(selected.substring(2)))
				{
					temp++;
				}
			}
			
			if(temp == 0)
			{
				selected = selected.substring(2);
				teilnehmerPrivatChat.add(selected);
				choosenClients.addElement(selected);
			}

			gui.getListChoosenUser().setModel(choosenClients);
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
			int index = gui.getListChoosenUser().getSelectedIndex();
			choosenClients.removeElementAt(index);
			gui.getListChoosenUser().setModel(choosenClients);
			teilnehmerPrivatChat.remove(index);
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			System.out.println("nichts ausgewählt");
		}
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
