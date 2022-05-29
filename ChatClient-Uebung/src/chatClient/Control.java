package chatClient;

import java.io.IOException;
import Message.nachrichtP.Nachricht;
import chatClient.Register;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.ObjectOutputStream;
import java.net.Socket;
import Message.nachrichtP.FehlerNachricht;
import Message.nachrichtP.LogInNachricht;
import javax.swing.DefaultListModel;
import org.objenesis.instantiator.basic.NewInstanceInstantiator;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class Control
{
	private boolean erfolgreich;
	private boolean nachrichteingegangen;	
	private static Client client;
	private String fehlerMeldungString;
	private FehlerNachricht fehlerMessage;
	private String clientNameString;
	private String empfangeneNachrichString;
	private Boolean chatNachrichtEmpfangen;
	
	public Control()
	{
		client = new Client();
		addListenerToClient();
		Register.register(client.getKryo());
	}

	protected FehlerNachricht getFehlerMessage()
	{
		return fehlerMessage;
	}

	protected void sendMessage(String nachricht)
	{
		client.sendTCP(new Nachricht(this.clientNameString, nachricht));
	}

	protected void getNewMessages(Nachricht n)
	{

	}

	protected void login(String benutzer, String pass, boolean anmeldung)
	{
		System.out.println(benutzer + " " + pass);
		try
		{
			client.start();
			client.connect(5000, "localhost", 5555, 8008);
			client.sendTCP(new LogInNachricht(benutzer, pass, anmeldung));
			clientNameString = benutzer;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public String getFehlerMeldungString()
	{
		return this.fehlerMeldungString;
	}
	
	protected void addListenerToClient()
	{
		client.addListener(new Listener()
		{
			public void received(Connection connection, Object object)
			{
				if (object instanceof FehlerNachricht) 
				{
					try
					{
						nachrichteingegangen = true;
						client.update(1);
						fehlerMessage = (FehlerNachricht) object;
						connection.sendTCP(new String("Hat geklappt vom Client"));
						if (fehlerMessage.isDatenbankFehler())
						{
							fehlerMeldungString = "Die Verbindung ist fehlgeschlagen";
							erfolgreich = false;
							clientNameString = null;
						}
						else if (fehlerMessage.isNutzernameVergebenFehler())
						{
							fehlerMeldungString = "Der Nutzername ist bereits vergeben";
							erfolgreich = false;
							clientNameString = null;
						}
						else if (fehlerMessage.isNutzernameFehler())
						{
							fehlerMeldungString = "Der Nutzername ist falsch";
							erfolgreich = false;
							clientNameString = null;
						}
						else if (fehlerMessage.isPasswortFehler())
						{
							System.out.println("Passwort ist falsch du honk");
							fehlerMeldungString = "Das Passwort ist falsch";
							erfolgreich = false;
							clientNameString = null;
						}
						else
						{
							erfolgreich = true;
						}
						Thread.sleep(1);
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
					catch (InterruptedException e)
					{
						client.stop();
						e.printStackTrace();
					}
				}
				else if(object instanceof String)
				{
					System.out.println((String)object);
				}
				else if(object instanceof Nachricht)
				{
					empfangeneNachrichString = ((Nachricht)object).getNachricht();
					chatNachrichtEmpfangen = true;
				}
			}
		});
	}

	protected Scene changeScene(boolean singin) throws IOException
	{
		if (singin == true)
		{
			Parent SignUpParent = FXMLLoader.load(getClass().getResource("Registrieren.fxml"));
			return new Scene(SignUpParent);
		}
		else
		{
			Parent SignUpParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
			return new Scene(SignUpParent);
		}
	}

	protected Scene erfolgreicherLogin() throws IOException
	{
		Parent debugParent = FXMLLoader.load(getClass().getResource("Chat.fxml"));
		return new Scene(debugParent);
	}

	protected boolean getErfolgreich()
	{
		return this.erfolgreich;
	}
	
	protected void setErfolgreich(boolean erfolgreich)
	{
		this.erfolgreich = erfolgreich;
	}

	protected boolean isNachrichtEingegangen()
	{
		return nachrichteingegangen;
	}

	protected void setNachrichteingegangen(boolean nachrichteingegangen)
	{
		this.nachrichteingegangen = nachrichteingegangen;
	}
	
	protected boolean isChatNachrichtEingegangen()
	{
		return this.chatNachrichtEmpfangen;
	}
	
	protected String getEmpfangeneNachrichString()
	{
		return this.empfangeneNachrichString;
	}
}
