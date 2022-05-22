package chatClient;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import Message.nachrichtP.Nachricht;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.channels.NonWritableChannelException;

import Message.nachrichtP.FehlerNachricht;
import Message.nachrichtP.LogInNachricht;
import javax.swing.DefaultListModel;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class Control
{

	// protected Gui gui;
	// protected VerbindungsGUI startGui;
	protected int port;
	protected Socket socket;
	// List Modells
	protected DefaultListModel<Nachricht> messages = new DefaultListModel<Nachricht>();
	protected DefaultListModel<String> clients = new DefaultListModel<String>();
	protected DefaultListModel<String> choosenClients = new DefaultListModel<String>();
	private boolean erfolgreich;

	//protected ObjectInputStream ois;
	protected ObjectOutputStream out;

	private boolean startWindow = false;

	private boolean first = true;
	private static Client client;
	private String fehlerMeldungString;

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
		//read = new Thread(this);
		client = new Client();
		addListenerToClient();
		Kryo kryo = client.getKryo();
		kryo.register(LogInNachricht.class);
		kryo.register(FehlerNachricht.class);
	}

	private void initFX()
	{
		/*
		 * vbox_messages.heightProperty().addListener(new ChangeListener<Number>() {
		 * 
		 * @Override public void changed(ObservableValue<? extends Number> observable,
		 * Number oldValue, Number newValue) { scrollpane_messages.setVvalue((Double)
		 * newValue); } });
		 * 
		 * button_send.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent event) { sendMessage(); } });
		 */
	}

	/*
	 * public void switchGui() { Rectangle r; if (startWindow) { r =
	 * gui.getFrmBounds(); startGui.setFrmBounds(r); startGui.hide(false);
	 * gui.hide(true); startWindow = false; } else { r = startGui.getFrmBounds();
	 * gui.setFrmBounds(r); gui.hide(false); startGui.hide(true); startWindow =
	 * true; } }
	 * 
	 * private void setListener() { this.gui.addEingabeListener(l -> sendMessage());
	 * this.gui.addBtnStopListener(l -> stopClient());
	 * 
	 * this.startGui.addBtnVerbindenListener(l ->{start = new
	 * ClientConnectionThread(this);start.start();}); gui.addListListener(new
	 * MouseMotionAdapter() {@Override public void mouseMoved(MouseEvent
	 * e){setToolTip();};});
	 * 
	 * this.gui.addAddListner(l -> addUserToNewChat()); this.gui.addEntfListner(l ->
	 * entfUserFromNewChat()); }
	 * 
	 * protected boolean checkPort() { boolean korrekt = false; try { int tempPort =
	 * startGui.getPort();
	 * 
	 * if (tempPort >= 1024 && tempPort <= 49151) { port = tempPort; korrekt = true;
	 * this.gui.changeStatus("korrekter Port"); } else { //guiError("port");
	 * this.gui.changeStatus("kein korrekter Port"); }
	 * 
	 * } catch (NumberFormatException e) { //guiError("port");
	 * //this.gui.changeStatus("kein korrekter Port"); } return korrekt; }
	 */
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

	/*private void readMessage()
	{
		try
		{

			Nachricht message = (Nachricht) ois.readObject();

			System.out.println(message.toString());
			if (message.getListClients() != null)
			{
				this.clients = message.getListClients();
				// akClientList();
			}
			getNewMessages(message);

		}
		catch (SocketException e1)
		{
			stopClient();
		}
		catch (EOFException e2)
		{
			stopClient();
		}
		catch (Exception e)
		{
			System.out.println(e + "\n in readMessage");
		}
	}*/

	protected void getNewMessages(Nachricht n)
	{
		messages.addElement(n);
		// akList();
	}
	/*
	 * protected void akList() { this.gui.getList().setModel(messages); }
	 * 
	 * protected void akClientList() { this.gui.getListUser().setModel(clients);
	 * this.gui.getListActiveUser().setModel(clients); }
	 */

	/*private void stopClient()
	{
		try
		{
			first = true;
			ois.close();
			out.close();
			socket.close();
		}
		catch (IOException e)
		{
			System.out.println(e + "\n in stopClient");
		}

		read.interrupt();
		start.interrupt();
		// gui.changeStatus("verbindung getrennt");
		// switchGui();
	}*/
	/*
	 * private void setToolTip() { int index = gui.hoveredItem(); if (index != -1) {
	 * Nachricht n = messages.getElementAt(index); String time =
	 * n.getTimestamp().getDayOfMonth() + "." + n.getTimestamp().getMonthValue() +
	 * "." + n.getTimestamp().getYear() + " | " + n.getTimestamp().getHour() + ":" +
	 * n.getTimestamp().getMinute() + " Uhr";
	 * gui.getList().setToolTipText("<html> Absender-ID: " + n.getAbsenderId() +
	 * "<br> Absender: " + n.getAbsender() + "<br> Versandt: " + time + "</html>");
	 * }
	 * 
	 * }
	 */
	/*
	 * protected boolean checkUsername() {
	 * if(startGui.getTextFieldUsername().getText().isBlank()) {
	 * //guiError("username"); return false; } else { return true; } }
	 */

	/*
	 * private void guiError(String error) { switch (error) { case "port": new
	 * Thread(new Runnable() {
	 * 
	 * @Override public void run() { for (int j = 0; j < 6; j++) { if (j % 2 == 0) {
	 * startGui.changePortColor(rot); } else { startGui.changePortColor(weiss); }
	 * 
	 * try { Thread.sleep(125); } catch (InterruptedException e) {
	 * System.out.println(e + "\n in guiError - port"); } } } }).start(); break;
	 * 
	 * case "username": new Thread(new Runnable() {
	 * 
	 * @Override public void run() { for (int j = 0; j < 6; j++) { if (j % 2 == 0) {
	 * startGui.changeUsernameColor(rot); } else {
	 * startGui.changeUsernameColor(weiss); }
	 * 
	 * try { Thread.sleep(125); } catch (InterruptedException e) {
	 * System.out.println(e + "\n in guiError - username"); } } } }).start(); break;
	 * } }
	 */

	/*
	 * private void addUserToNewChat() { try { int index =
	 * gui.getListActiveUser().getSelectedIndex();
	 * 
	 * String selected = clients.getElementAt(index);
	 * choosenClients.addElement(selected);
	 * 
	 * gui.getListChoosenUser().setModel(choosenClients); }
	 * catch(ArrayIndexOutOfBoundsException e) {
	 * System.out.println("nichts ausgew�hlt"); } }
	 */

	/*
	 * private void entfUserFromNewChat() { try { int index =
	 * gui.getListChoosenUser().getSelectedIndex();
	 * 
	 * choosenClients.removeElementAt(index);
	 * 
	 * gui.getListChoosenUser().setModel(choosenClients); }
	 * catch(ArrayIndexOutOfBoundsException e) {
	 * System.out.println("nichts ausgew�hlt"); } } <<<<<<< HEAD
	 */

	protected void login(String benutzer, String pass, boolean anmeldung)
	{
		System.out.print(benutzer + " " + pass);
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
						client.update(100);
						FehlerNachricht fehler = (FehlerNachricht) object;
						System.out.println("Verbindung hergestellt mit Kryo");
						connection.sendTCP(new String("Hat geklappt vom Client"));
						if (fehler.isDatenbankFehler())
						{
							fehlerMeldungString = "Die Verbindung ist fehlgeschlagen";
							erfolgreich = false;
						}
						else if (fehler.isNutzernameVergebenFehler())
						{
							System.out.println("Vor alert");
							fehlerMeldungString = "Der Nutzername ist bereits vergeben";
							erfolgreich = false;
							System.out.println("Nach alert");
						}
						else if (fehler.isNutzernameFehler())
						{
							fehlerMeldungString = "Der Nutzername ist falsch";
							erfolgreich = false;
						}
						else if (fehler.isPasswortFehler())
						{
							fehlerMeldungString = "Das Passwort ist falsch";
							erfolgreich = false;
						}
						else
						{
							erfolgreich = true;
						}
						Thread.sleep(1000);
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
			Parent SignUpParent = FXMLLoader.load(getClass().getResource("SignUpGUI.fxml"));
			return new Scene(SignUpParent);
		}
		else
		{
			Parent SignUpParent = FXMLLoader.load(getClass().getResource("LoginGUI.fxml"));
			return new Scene(SignUpParent);
		}
	}

	// protected Scene erfolgreicherLogin() throws IOException
	{
		// to do
		// Parent debugParent = FXMLLoader.load(getClass().getResource("Client.fxml"));
		// return new Scene(debugParent);
	}

	public boolean getErfolgreich()
	{
		return this.erfolgreich;
	}
	
	public void setErfolgreich(boolean erfolgreich)
	{
		this.erfolgreich = erfolgreich;
	}
	
}
