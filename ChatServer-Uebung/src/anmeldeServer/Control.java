package anmeldeServer;

import java.io.IOException;
import java.net.ServerSocket;

public class Control
{
	private ServerSocket serverSocket;
	private ServerConnectionThread connectionThread;
	
	public Control()
	{
		anmeldeServerStarten();
	}

	private void anmeldeServerStarten()
	{
		try
		{
			serverSocket = new ServerSocket(5555);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		connectionThread = new ServerConnectionThread();
	}
}
