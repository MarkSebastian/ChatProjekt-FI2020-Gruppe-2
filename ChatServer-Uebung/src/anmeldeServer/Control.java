package anmeldeServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

import Message.nachrichtP.LogInNachricht;
import eaB_NeverInaffDaten.ControlDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Message.nachrichtP.FehlerNachricht;

public class Control
{
	private ServerSocket serverSocket;
	protected ServerConnectionThread connectionThread;
	private ArrayList<ClientProxy> clients;
	private ControlDB controlDB;
	private ObjectOutputStream oos;
	
	public Control() 
	{
		controlDB = new ControlDB();
		
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
		connectionThread = new ServerConnectionThread(this.serverSocket, this, this.clients);
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
	
	}
	
	protected void registrieren(LogInNachricht message, Socket socket)
	{
		boolean []nameFrei;
		nameFrei = controlDB.nutzerNameFreiFragezeichen(message.getBenutzerName());
		if(nameFrei[0] == false)
		{
			System.out.println("Name nicht frei db fehler");
			try
			{
				oos = new ObjectOutputStream(socket.getOutputStream());
				FehlerNachricht dbFehler = new FehlerNachricht(true, false, false, false);
				oos.writeObject(dbFehler);
				
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else if(nameFrei[1] == false)
		{
			System.out.println("Name nicht frei");
			try
			{
				oos = new ObjectOutputStream(socket.getOutputStream());
				FehlerNachricht nameVergeben = new FehlerNachricht(false, true, false, false);
				oos.writeObject(nameVergeben);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			boolean flag;
			flag = controlDB.insert_LoginDB(message.getBenutzerName(), message.getPasswort());
			System.out.println(flag);
			if(flag == false)
			{
				try
				{
					oos = new ObjectOutputStream(socket.getOutputStream());
					FehlerNachricht dbFehler = new FehlerNachricht(true, false, false, false);
					oos.writeObject(dbFehler);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				System.out.println(flag);
			}
			else if(flag == true)
			{
				try
				{
					oos = new ObjectOutputStream(socket.getOutputStream());
					FehlerNachricht erfolgreich = new FehlerNachricht(false, false, false, false);
					oos.writeObject(erfolgreich);
					System.out.println(flag);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	protected void anmelden(LogInNachricht message, Socket socket)
	{
		boolean []flag;
		flag = controlDB.nutzernameExistent(message.getBenutzerName());
		if(flag[0] == false)
		{
			try
			{
				oos = new ObjectOutputStream(socket.getOutputStream());
				FehlerNachricht dbFehler = new FehlerNachricht(true, false, false, false);
				oos.writeObject(dbFehler);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else if(flag[1] == false)
		{
			try
			{
				oos = new ObjectOutputStream(socket.getOutputStream());
				FehlerNachricht nameNichtVorhanden = new FehlerNachricht(false, false, true, false);
				oos.writeObject(nameNichtVorhanden);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else if(flag[1] == true)
		{
			String passwort = null;
			passwort = controlDB.select_passwort(message.getBenutzerName());
			if(passwort == null)
			{
				try
				{
					oos = new ObjectOutputStream(socket.getOutputStream());
					FehlerNachricht pwFehler = new FehlerNachricht(false, false, false, true);
					oos.writeObject(pwFehler);
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(passwort != null)
			{
				if(message.getPasswort().compareTo(passwort) == 0)
				{
					try
					{
						oos = new ObjectOutputStream(socket.getOutputStream());
						FehlerNachricht erfolgreich = new FehlerNachricht(false, false, false, false);
						oos.writeObject(erfolgreich);
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}
}