package anmeldeServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.channels.NonWritableChannelException;
import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import Message.nachrichtP.FehlerNachricht;
import Message.nachrichtP.LogInNachricht;

public class ServerConnectionThread //extends Thread
{
	//private ServerSocket server;
	private Server server;
	private Control control;
	private Socket socket;
	private ArrayList<ClientProxy> clients;
	private Thread thread;
	private Kryo kryo;
	
	public ServerConnectionThread(Server server, Control control, ArrayList<ClientProxy> clients)
	{
		this.server = server;
		this.control = control;
		this.clients = clients;
		this.kryo = server.getKryo();
		kryo.register(LogInNachricht.class);
		kryo.register(FehlerNachricht.class);
		addListenerToServer();
		this.server.start();
		//thread = new Thread(this);
		//thread.start();
	}
	
	public void addListenerToServer()
	{
		server.addListener(new Listener()
		{
			public void received (Connection connection, Object object)
			{
				if(object instanceof LogInNachricht)
				{
						try
						{
							server.update(100);
							ClientProxy client = new ClientProxy(control, connection);
							clients.add(client);
							LogInNachricht nachricht = (LogInNachricht) object;
							System.out.println("Verbindung hergestellt mit Kryo");
							connection.sendTCP(new String("Hat geklappt"));
							Thread.sleep(1000);
						}
						catch (IOException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						catch (InterruptedException e)
						{
							serverStop();
							System.out.println("ConnectionThread geschlossen");
						}
				}
			}
		});
	}
	
	protected void serverStop()
	{
		server.stop();
	}
	
	/*@Override
	public void run()
	{
		while(!this.isInterrupted())
		{
			try
			{
				//server.setSoTimeout(100);
				//socket = server.accept();
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
	}*/
}
