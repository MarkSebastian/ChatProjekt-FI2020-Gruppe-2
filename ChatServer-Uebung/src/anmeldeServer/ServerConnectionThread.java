package anmeldeServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerConnectionThread extends Thread
{
	private ServerSocket server;
	private Control control;
	private Socket socket;
	private ArrayList<ClientProxy> clients;
	
	public ServerConnectionThread(ServerSocket serverSocket, Control control)
	{
		this.server = serverSocket;
		this.control = control;
	}
	
	@Override
	public void run()
	{
		while(!this.isInterrupted())
		{
			try
			{
				socket = server.accept();
				ClientProxy client = new ClientProxy(control, socket);
				clients.add(client);
				System.out.println("Verbindung hergestellt!");
				Thread.sleep(1000);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (InterruptedException e)
			{
				control.connectionThread.interrupt();
			}
		}
	}
}
