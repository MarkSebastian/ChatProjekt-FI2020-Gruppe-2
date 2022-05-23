package chatServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import Message.nachrichtP.Nachricht;

public class ServerConnectionThread extends Thread
{

	private ServerControl control;
	private ServerSocket server;
	private Socket socket;
	private ArrayList<Client> clients = new ArrayList<Client>();
	private ArrayList<String> empfaenger = new ArrayList<String>();
	private int clientCount = 1;
	private String test = "";

	public ServerConnectionThread(ServerSocket server, ServerControl control)
	{
		this.server = server;
		this.control = control;
	}

	public ArrayList<Client> getClients()
	{
		return clients;
	}

	public ArrayList<String> getEmpfaenger()
	{
		return empfaenger;
	}

	@Override
	public void run()
	{
		while (!this.isInterrupted())
		{
			
			try
			{
				socket = server.accept();
				Client newClient = new Client(clientCount, socket, control);
				clients.add(newClient);				
				akStringArrayEmpfaenger();
				clientCount++;
				control.akClientList();
				System.out.println("Verbindung hergestellt");
				Nachricht beitritt = new Nachricht(newClient.getName() + " ist beigetreten!", empfaenger);
				control.broadcastMessage(beitritt);
				Thread.sleep(1000);
			}
			catch (SocketException e1)
			{
				control.connect.interrupt();
			}
			catch (Exception e)
			{
				System.out.println(e + "\n in run of ServerConnectionThread");
			}
		}
	}

	protected void akStringArrayEmpfaenger()
	{
		empfaenger = new ArrayList<String>();
		clients.forEach(c -> empfaenger.add(c.getName()));
	}
}