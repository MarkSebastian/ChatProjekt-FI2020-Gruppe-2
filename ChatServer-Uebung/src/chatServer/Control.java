package chatServer;
//this was done by tobi und überarbeitet von robin

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

import Message.nachrichtP.LogInNachricht;
import Message.nachrichtP.Nachricht;
import anmeldeServer.Register;
import eaB_NeverInaffDaten.CouncilOfData;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class Control
{
	private CouncilOfData controlDB;
	private static Server server;
	private Connection[] connections;
	
	public Control() 
	{
		try
		{
			controlDB = new CouncilOfData();
			server = new Server();
			Register.register(server.getKryo());
			server.bind(8008);
			server.start();
			addListenerToServer();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	protected void stoppen()
	{
		server.stop();
		server.close();
		System.out.println("Server gestoppt");
	}
	
	public void addListenerToServer()
	{
		server.addListener(new Listener()
		{
			public void received (Connection connection, Object object)
			{
				if(object instanceof Nachricht)
				{
					System.out.println("Hab ne Nachricht bekommen <3");
						try
						{
							Nachricht message = (Nachricht)object;
							connections = server.getConnections();
							for (Connection connection2 : connections)
							{
								connection2.sendTCP(message);
							}
							Thread.sleep(1000);
						}
						catch (InterruptedException e)
						{
							server.stop();
							System.out.println("ConnectionThread geschlossen");
						}
						/*catch (IOException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}*/
				}
			}
		});
	}
}