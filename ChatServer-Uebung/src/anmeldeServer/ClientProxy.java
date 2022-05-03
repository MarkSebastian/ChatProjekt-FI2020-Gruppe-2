package anmeldeServer;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

import Message.nachrichtP.LogInNachricht;

public class ClientProxy implements Runnable
{
	private Control control;
	private Socket socket;
	private ObjectInputStream ois;
	private Thread thread;
	
	public ClientProxy(Control control, Socket socket)
	{
		this.control = control;
		this.socket = socket;
		startStreams();
		thread = new Thread(this);
		thread.start();
	}

	private void startStreams()
	{
		try
		{
			ois = new ObjectInputStream(this.socket.getInputStream());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public void run()
	{
		while(!thread.isInterrupted())
		{
			try
			{ 
				readMessage();
				Thread.sleep(500);
			}
			catch(InterruptedException e)
			{
				System.out.println("Verbindung mit Client getrennt");
				thread.interrupt();
			}
		}
	}

	private void readMessage()
	{
		try
		{
			LogInNachricht message = null;
			message = (LogInNachricht)ois.readObject();
			
		}
		catch (SocketException | EOFException e1)
		{
			stopClient();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void stopClient()
	{
		try
		{
			socket.close();
			ois.close();
			this.thread.interrupt();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
