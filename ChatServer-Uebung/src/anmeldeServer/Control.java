package anmeldeServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ArrayList;

import Message.nachrichtP.LogInNachricht;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Control
{
	private ServerSocket serverSocket;
	protected ServerConnectionThread connectionThread;
	private ArrayList<ClientProxy> clients; 
	
	public Control() 
	{
		
		
	}
	
	protected void Test()
	{
		System.out.println("A");
	}

	protected void anmeldeServerStarten()
	{
		try
		{
			serverSocket = new ServerSocket(5555);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("Erfolgreicher Start");
		clients = new ArrayList<ClientProxy>();
		connectionThread = new ServerConnectionThread(this.serverSocket, this);
	}
	
	protected void stoppen()
	{
		if(clients.isEmpty() == false)
		{
			for(ClientProxy c : clients)
			{
			c.stopClient();
			}
			clients.clear();
		}
		connectionThread.interrupt();
		try
		{
			serverSocket.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		System.out.println("Server gestoppt");
	}

	protected void nachrichtAnzeigen(LogInNachricht message)
	{
		System.out.println(message.getBenutzerName() + " " + message.getPasswort());
		//TO-DO: Überprüfung mit Datenbank!
	}
	
	protected void registrieren(LogInNachricht message)
	{
		
	}
	
	protected void anmelden(LogInNachricht message)
	{
		
	}
}
