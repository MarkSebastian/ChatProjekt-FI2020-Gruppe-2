package chatClient;

import java.io.IOException;
import Message.nachrichtP.Nachricht;
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
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class Control
{
	protected int port;
	protected Socket socket;
	// List Modells
	protected DefaultListModel<Nachricht> messages = new DefaultListModel<Nachricht>();
	protected DefaultListModel<String> clients = new DefaultListModel<String>();
	protected DefaultListModel<String> choosenClients = new DefaultListModel<String>();
	private boolean erfolgreich;
	private boolean nachrichteingegangen;

	//protected ObjectInputStream ois;
	protected ObjectOutputStream out;

	private boolean startWindow = false;

	private boolean first = true;
	private static Client client;
	private String fehlerMeldungString;
	private FehlerNachricht fehlerMessage;

	// FX
	@FXML
	private Button button_send;
	@FXML
	private TextField textField_nachricht;
	@FXML
	private VBox vbox_messages;
	@FXML
	private ScrollPane scrollpane_messages;

	public Control()
	{
		client = new Client();
		addListenerToClient();
		Kryo kryo = client.getKryo();
		kryo.register(LogInNachricht.class);
		kryo.register(FehlerNachricht.class);
	}

	public FehlerNachricht getFehlerMessage()
	{
		return fehlerMessage;
		
	}


	@FXML
	protected void sendMessage()
	{
		try
		{
			Nachricht message;
			/*
			 * if(first) { message = new
			 * Nachricht(startGui.getTextFieldUsername().getText(), false);
			 * out.writeObject(message); first = false; } else {
			 */
			String textToSend = textField_nachricht.getText();
			if (!textToSend.isEmpty())
			{

				HBox hBox = new HBox();
				hBox.setAlignment(Pos.CENTER_RIGHT);
				hBox.setPadding(new Insets(5, 5, 5, 10));

				Text text = new Text(textToSend);
				TextFlow textFlow = new TextFlow(text);

				textFlow.setStyle("-fx-color: rgb(239,242,255" + "-fx-background-color: rgb(15,125,242)"
						+ "-fx-background-radius: 20px");

				textFlow.setPadding(new Insets(5, 10, 5, 10));
				text.setFill(Color.color(0.934, 0.945, 0.996));

				hBox.getChildren().add(textFlow);
				vbox_messages.getChildren().add(hBox);

				/*
				 * message = new Nachricht(textToSend, false); out.writeObject(message);
				 * messages.addElement(message); textField_nachricht.clear();
				 */
			}

			// akList();
			// }
		}
		catch (NullPointerException e)
		{
			// gui.changeStatus("Noch nicht mit Server Verbunden!");
		}
		catch (Exception e)
		{
			System.out.println(e + "\n in sendMessage");
		}
		// this.gui.getTextFieldEingabe().setText("");

	}



	protected void getNewMessages(Nachricht n)
	{
		messages.addElement(n);

	}

	protected void login(String benutzer, String pass, boolean anmeldung)
	{
		System.out.println(benutzer + " " + pass);
		try
		{
			client.start();
			client.connect(5000, "localhost", 5555, 8008);
			client.sendTCP(new LogInNachricht(benutzer, pass, anmeldung));
			System.out.println("Erfolgreich gesendet");
			System.out.println(client.getRemoteAddressTCP());
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
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
						System.out.println("Nachricht:" + fehlerMessage);
						System.out.println("Verbindung hergestellt mit Kryo");
						connection.sendTCP(new String("Hat geklappt vom Client"));
						if (fehlerMessage.isDatenbankFehler())
						{
							fehlerMeldungString = "Die Verbindung ist fehlgeschlagen";
							erfolgreich = false;
						}
						else if (fehlerMessage.isNutzernameVergebenFehler())
						{
							System.out.println("Vor alert");
							fehlerMeldungString = "Der Nutzername ist bereits vergeben";
							erfolgreich = false;
							System.out.println("Nach alert");
						}
						else if (fehlerMessage.isNutzernameFehler())
						{
							fehlerMeldungString = "Der Nutzername ist falsch";
							erfolgreich = false;
						}
						else if (fehlerMessage.isPasswortFehler())
						{
							System.out.println("Passwort ist falsch du honk");
							fehlerMeldungString = "Das Passwort ist falsch";
							erfolgreich = false;
						}
						else
						{
							erfolgreich = true;
						}
						Thread.sleep(1);
					}
					catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						client.stop();
						e.printStackTrace();
					}
				}
				else if(object instanceof String)
				{
					System.out.println((String)object);
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
		// to do
		Parent debugParent = FXMLLoader.load(getClass().getResource("Chat.fxml"));
		return new Scene(debugParent);
	}

	public boolean getErfolgreich()
	{
		return this.erfolgreich;
	}
	
	public void setErfolgreich(boolean erfolgreich)
	{
		this.erfolgreich = erfolgreich;
	}

	public boolean isNachrichteingegangen()
	{
		return nachrichteingegangen;
	}

	public void setNachrichteingegangen(boolean nachrichteingegangen)
	{
		this.nachrichteingegangen = nachrichteingegangen;
	}
	
}
