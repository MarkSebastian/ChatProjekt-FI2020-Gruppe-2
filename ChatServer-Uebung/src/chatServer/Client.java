package chatServer;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import Message.nachrichtP.Nachricht;
import privatChat.PrivatChatSenden;

public class Client implements Runnable
{
	private ServerControl control;
	private String name = "unbekannt";
	private int id = 0;
	private Thread read;
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream out;	
	private boolean first = true;

	public Client(int id, Socket socket, ServerControl control)
	{
		this.id = id;
		this.socket = socket;
		this.control = control;
		startStreams();
		readMessage();
		read = new Thread(this);
		read.start();
	}

	private void startStreams()
	{
		try
		{
			out = new ObjectOutputStream(this.socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (Exception e)
		{
			System.out.println(e + "\n in startStreams function");
		}
	}

	protected void sendMessage(Object o)
	{
		try
		{
			out.writeObject(o);
		} catch (Exception e)
		{
			System.out.println(e + "\n in sendMessage function");
		}
	}

	protected void readMessage()
	{
		boolean privatChatObjekt = false;
		Nachricht message = null;
		PrivatChatSenden pcs = null;	
		Object o = null;
		
		// Versuch Objekt einzulesen
		try
		{		
			o = ois.readObject();			
		}			
		catch (SocketException | EOFException e1)
		{
			control.closeClient(this);
			stopClient();
		} catch (Exception e)
		{
			System.out.println(e + "\n in readMessage function");
		}
		
		if(o != null)
		{
			// Wenn eingelesen, Test ob Objekt PrivatChat ist
			try
			{
				pcs = (PrivatChatSenden)o;
				privatChatObjekt = true;					
			}
			catch(ClassCastException e)
			{	
			}
			
			// Wenn Objekt PrivatChat ist, PrivatChat an Teilnehmer des PrivatChats schicken
			if(privatChatObjekt == true)
			{
				control.broadcastPrivatChat(pcs);
			}
			
			// Ansonsten wird Objekt als Nachricht eingelesen
			else
			{
				message = (Nachricht)o;
				if(first)
				{
					name = message.getNachricht();
					first = false;
				}
				else
				{
					message.setAbsender(name);
					message.setAbsenderId(id);								
					// Wenn ArrayList empfaenger von message leer ist, wird die Nachricht an den Globalen Chat versendet
					if(message.getEmpfaenger().isEmpty())
					{
						control.getNewMessages(message);
						control.broadcastMessage(message, this);						
					}						
					// Ansonsten wird die Nachricht nur an die Teilnehmer des privaten Chats verschickt
					else
					{
						control.broadcastPrivatMessage(message);
					}						
				}
			}				
		}
	}

	@Override
	public void run()
	{

		while (!read.isInterrupted())
		{
			try
			{
				readMessage();

				Thread.sleep(500);
			} catch (InterruptedException e)
			{
				System.out.println(name + " verbindung getrennt");
				control.broadcastMessage(new Nachricht("verbindung mit " + name + " getrennt!", control.clientEntfernen(name)));
				System.out.println("Verbindung geschlossen, Empfänger:");
				for (String s : control.getConnect().getEmpfaenger())
				{
					System.out.println(s);
				}
				System.out.println("Verbindung geschlossen, Clients:");
				for (Client c : control.getConnect().getClients())
				{
					System.out.println(c.getName());
				}
				read.interrupt();
			}
		}

	}

	public String getName()
	{
		return name;
	}

	public int getId()
	{
		return id;
	}

	public Socket getSocket()
	{
		return socket;
	}

	public void stopClient()
	{

		try
		{
			socket.close();
			out.close();
			ois.close();
			this.read.interrupt();
		} catch (IOException e)
		{
			System.out.println(e+ "\n in stopClient function");
		}
	}

	@Override
	public String toString()
	{
		return id + " " + name; 
	}
}
