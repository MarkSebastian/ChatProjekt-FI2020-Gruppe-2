package anmeldeServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

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
		anmeldeServerStarten();
		guiStarten();
	}
	
	private void guiStarten()
	{
		try
		{
			FXMLLoader fxmlLoader = new FXMLLoader();
			Scene scene = new Scene(fxmlLoader.load());
			Stage stage = new Stage();
			stage.setTitle("Anmelde Server");
			stage.setScene(scene);
			stage.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
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
}
