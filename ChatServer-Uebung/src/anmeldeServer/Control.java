package anmeldeServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Control extends Application
{
	private ServerSocket serverSocket;
	protected ServerConnectionThread connectionThread;
	private ArrayList<ClientProxy> clients;
	
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

	@Override
	public void start(Stage stage) throws Exception
	{
		try
		{
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.load(getClass().getResource("AnmeldeSeverGui.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			stage.setTitle("Anmelde Server");
			stage.setScene(scene);
			stage.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
}
