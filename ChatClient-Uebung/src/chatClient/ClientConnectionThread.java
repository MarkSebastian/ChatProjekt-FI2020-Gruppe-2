package chatClient;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

public class ClientConnectionThread extends Thread
{
	private ClientControl control;
	private Gui gui;

	public ClientConnectionThread(ClientControl control)
	{
		this.control = control;
		this.gui = control.gui;
	}

	protected void startStreams()
	{
		try
		{
			control.out = new ObjectOutputStream(control.socket.getOutputStream());
			control.ois = new ObjectInputStream(control.socket.getInputStream());
		}
		catch (Exception e)
		{
			System.out.println(e + "\n in startStreams");
		}

	}

	@Override
	public void run()
	{
		if (control != null)
		{
			control.messages.clear();
			control.akList();
			if (control.checkPort() && control.checkUsername())
			{
				boolean customIP = control.startGui.getCustomIp();
				try
				{
					if (customIP == true)
					{
						control.socket = new Socket(control.startGui.getIP(), control.port);
					}
					else
					{
						control.socket = new Socket("localhost", control.port);
					}
					gui.changeStatus("socket gestartet");
					startStreams();

					control.sendMessage();

					control.read = new Thread(control);
					control.read.start();
				}
				catch (ConnectException e)
				{
					this.gui.changeStatus("Zielserver wurde nicht gefunden");
					System.out.println(e);
				}
				catch (Exception e)
				{
					System.out.println(e + "\n in run of ServerConnectionThread");
				}
			}
		}
	}

}
