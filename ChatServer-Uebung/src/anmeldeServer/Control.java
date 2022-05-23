package anmeldeServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.ZipEntry;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import Message.nachrichtP.LogInNachricht;
import eaB_NeverInaffDaten.CouncilOfData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Message.nachrichtP.FehlerNachricht;

public class Control
{
	private static Server server;
	private CouncilOfData controlDB;
	
	public Control() 
	{
		controlDB = new CouncilOfData();

	}

	protected void anmeldeServerStarten()
	{
		try
		{
			server = new Server();
			Register.register(server.getKryo());
			server.bind(5555,8008);
			server.start();
			addListenerToServer();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("Erfolgreicher Start");
	}
	
	protected void stoppen()
	{
		server.stop();
		server.close();
		System.out.println("Server gestoppt");
	}

	protected void registrieren(LogInNachricht message, Connection connection)
	{
		boolean []nameFrei;
		nameFrei = controlDB.nutzerNameFreiFragezeichen(message.getBenutzerName());
		if(nameFrei[0] == false)
		{
			System.out.println("Name nicht frei db fehler");
			FehlerNachricht dbFehler = new FehlerNachricht(true, false, false, false);
			connection.sendTCP(dbFehler);
			
		}
		else if(nameFrei[1] == false)
		{
			System.out.println("Name nicht frei");
			FehlerNachricht nameVergeben = new FehlerNachricht(false, true, false, false);
			connection.sendTCP(nameVergeben);
		}
		else
		{
			boolean flag;
			flag = controlDB.insert_LoginDB(message.getBenutzerName(), message.getPasswort());
			System.out.println(flag);
			if(flag == false)
			{
				FehlerNachricht dbFehler = new FehlerNachricht(true, false, false, false);
				connection.sendTCP(dbFehler);
				System.out.println(flag);
			}
			else if(flag == true)
			{
				FehlerNachricht erfolgreich = new FehlerNachricht(false, false, false, false);
				connection.sendTCP(erfolgreich);
				System.out.println(flag);
			}
		}
	}
	
	protected void anmelden(LogInNachricht message, Connection connection)
	{
		boolean []flag;
		flag = controlDB.nutzernameExistent(message.getBenutzerName());
		if(flag[0] == false)
		{
			FehlerNachricht dbFehler = new FehlerNachricht(true, false, false, false);
			connection.sendTCP(dbFehler);
		}
		else if(flag[1] == false)
		{
			FehlerNachricht nameNichtVorhanden = new FehlerNachricht(false, false, true, false);
			connection.sendTCP(nameNichtVorhanden);
		}
		else if(flag[1] == true)
		{
			String passwort = null;
			passwort = controlDB.select_passwort(message.getBenutzerName());
			if(passwort == null)
			{
				FehlerNachricht pwFehler = new FehlerNachricht(false, false, false, true);
				connection.sendTCP(pwFehler);
			}
			else if(passwort != null)
			{
				if(message.getPasswort().compareTo(passwort) == 0)
				{
					FehlerNachricht erfolgreich = new FehlerNachricht(false, false, false, false);
					connection.sendTCP(erfolgreich);
				}
			}
		}
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
							//server.update(500);
							LogInNachricht message = (LogInNachricht) object;
							if (message.getFlag() == true)
							{
								registrieren(message, connection);
							}
							else 
							{
								anmelden(message, connection);
							}
							System.out.println("Verbindung hergestellt mit Kryo");
							connection.sendTCP(new String("Hat geklappt vom Server"));
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