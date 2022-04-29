package anmeldeServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnectionThread extends Thread
{
	private ServerSocket server;
	private Control control;
	private Socket socket;
	
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
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
