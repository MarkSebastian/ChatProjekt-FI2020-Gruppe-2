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
				empfaenger.add(newClient.getName());
				clientCount++;
				control.akClientList();		
				System.out.println("Verbindung hergestellt");
				System.out.println("Broadcast Server ArrayEmpfänger");
				empfaenger.forEach(e -> System.out.println(e));
				System.out.println("Boardcast Server ArrayClients");
				clients.forEach(e -> System.out.println(e));
				Nachricht beitritt = new Nachricht (newClient.getName() + " ist beigetreten!", empfaenger);
				System.out.println("Nachricht beitritt:");
				beitritt.getEmpfaenger().forEach(n -> System.out.println(n));
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