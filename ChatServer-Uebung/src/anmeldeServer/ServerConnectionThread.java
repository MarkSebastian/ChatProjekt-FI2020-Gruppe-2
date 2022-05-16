package anmeldeServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class ServerConnectionThread extends Thread
{
	private ServerSocket server;
	private Control control;
	private Socket socket;
	private ArrayList<ClientProxy> clients;
	private Thread thread;
	
	public ServerConnectionThread(ServerSocket serverSocket, Control control, ArrayList<ClientProxy> clients)
	{
		this.server = serverSocket;
		this.control = control;
		this.clients = clients;
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run()
	{
		while(!this.isInterrupted())
		{
			try
			{
				server.setSoTimeout(100);
				socket = server.accept();
				ClientProxy client = new ClientProxy(control, socket);
				clients.add(client);
				System.out.println("Verbindung hergestellt!");
				Thread.sleep(1000);
			}
			catch (SocketTimeoutException e)
			{
				
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (InterruptedException e)
			{
				control.connectionThread.interrupt();
				System.out.println("ConnectionThread geschlossen");
			}
		}
	}
}
