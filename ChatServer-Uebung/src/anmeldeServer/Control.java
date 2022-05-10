package anmeldeServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ArrayList;

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
		connectionThread = new ServerConnectionThread(this.serverSocket, this);
	}
	
	private void stoppen()
	{
		for(ClientProxy c : clients)
		{
			c.stopClient();
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
		clients.clear();
		System.out.println("Server gestoppt");
	}

	
}
