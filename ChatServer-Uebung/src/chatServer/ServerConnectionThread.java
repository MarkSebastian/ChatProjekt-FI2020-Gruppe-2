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
	private int clientCount = 1;

	public ServerConnectionThread(ServerSocket server, ArrayList<Client> clients, ServerControl control)
	{
		this.server = server;
		this.clients = clients;
		this.control = control;

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
				clientCount++;
				control.akClientList();
				System.out.println("Verbindung hergestellt");
				Nachricht beitritt = new Nachricht (newClient.getName() + " ist beigetreten!", control.clientListeAbspecken());
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