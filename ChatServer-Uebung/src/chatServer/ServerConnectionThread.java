package chatServer;

import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import Message.nachrichtP.Nachricht;

public class ServerConnectionThread extends Thread
{

	private Control control;
	private ServerSocket server;
	private Socket socket;
	//oder leerer socket für datagramm
	private DatagramSocket ds;
	private ArrayList<Client> clients;
	private int clientCount = 1;

	public ServerConnectionThread(ServerSocket server, ArrayList<Client> clients, Control control)
	{
		this.server = server;
		this.clients = clients;
		this.control = control;

	}
	
	public ServerConnectionThread(DatagramSocket ds,ServerSocket server, ArrayList<Client> clients, Control control)
	{
		this.ds = ds;
		this.server = server;
		this.clients = clients;
		this.control = control;

	}

	//datagram socket erstellen da wo audio erstellt wird
	
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