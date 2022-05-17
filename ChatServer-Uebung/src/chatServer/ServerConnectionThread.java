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
	private ArrayList<Client> clients;
	private ArrayList<String> empfaenger = new ArrayList<String>();
	private int clientCount = 1;

	public ServerConnectionThread(ServerSocket server, ServerControl control)
	{
		this.server = server;
		this.clients = new ArrayList<Client>();
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
				empfaenger.add(newClient.getName());
				clientCount++;
				control.akClientList();		
				System.out.println("Verbindung hergestellt");
				Nachricht beitritt = new Nachricht (newClient.getName() + " ist beigetreten!", empfaenger);
				System.out.println("Broadcast Server Empfänger");
				empfaenger.forEach(e -> System.out.println(e));
				control.broadcastMessage(beitritt);
				Thread.sleep(1000);
			} catch(SocketException e1)
			{
				control.connect.interrupt();
			}
			catch (Exception e)
			{
				System.out.println(e + "\n in run of ServerConnectionThread");
			}
		}
	}
}