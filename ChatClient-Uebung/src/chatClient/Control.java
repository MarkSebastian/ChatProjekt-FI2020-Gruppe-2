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

public class Control implements Runnable
{

	//protected Gui gui;
	//protected VerbindungsGUI startGui;
	protected int port;
	protected Socket socket;
	//List Modells
	protected DefaultListModel<Nachricht> messages = new DefaultListModel<Nachricht>();
	protected DefaultListModel<String> clients = new DefaultListModel<String>();
	protected DefaultListModel<String> choosenClients = new DefaultListModel<String>();
	
	protected Thread read;
	private ClientConnectionThread start;

	protected ObjectInputStream ois;
	protected ObjectOutputStream out;

	private boolean startWindow = false;
	
	private boolean first = true;
	
	
	
	//FX
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

	}
	
	
	private void initFX()
	{
		/*vbox_messages.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				scrollpane_messages.setVvalue((Double) newValue);
			}
		});
		
		button_send.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sendMessage();
			}
		});*/
	}

	/*public void switchGui()
	{
		Rectangle r;
		if (startWindow)
		{
			r = gui.getFrmBounds();
			startGui.setFrmBounds(r);
			startGui.hide(false);
			gui.hide(true);
			startWindow = false;
		}
		else
		{
			r = startGui.getFrmBounds();
			gui.setFrmBounds(r);
			gui.hide(false);
			startGui.hide(true);
			startWindow = true;
		}
	}

	private void setListener()
	{
		this.gui.addEingabeListener(l -> sendMessage());
		this.gui.addBtnStopListener(l -> stopClient());

		this.startGui.addBtnVerbindenListener(l ->{start = new ClientConnectionThread(this);start.start();});
		gui.addListListener(new MouseMotionAdapter()
		{@Override public void mouseMoved(MouseEvent e){setToolTip();};});
		
		this.gui.addAddListner(l -> addUserToNewChat());
		this.gui.addEntfListner(l -> entfUserFromNewChat());
	}

	protected boolean checkPort()
	{
		boolean korrekt = false;
		try
		{
			int tempPort = startGui.getPort();

			if (tempPort >= 1024 && tempPort <= 49151)
			{
				port = tempPort;
				korrekt = true;
				this.gui.changeStatus("korrekter Port");
			} 
			else
			{
				//guiError("port");
				this.gui.changeStatus("kein korrekter Port");
			}

		} 
		catch (NumberFormatException e)
		{
			//guiError("port");
			//this.gui.changeStatus("kein korrekter Port");
		}
		return korrekt;
	}
	*/
	@FXML
	protected void sendMessage()
	{
		try
		{
			Nachricht message;
			/*if(first)
			{
				message = new Nachricht(startGui.getTextFieldUsername().getText(), false);
				out.writeObject(message);
				first = false;
			}
			else
			{*/
				String textToSend = textField_nachricht.getText();
				if(!textToSend.isEmpty())
				{
			 	
				HBox hBox = new HBox();
				hBox.setAlignment(Pos.CENTER_RIGHT);
				hBox.setPadding(new Insets(5, 5, 5, 10));
				
				Text text = new Text(textToSend);
				TextFlow textFlow = new TextFlow(text);
				
				textFlow.setStyle("-fx-color: rgb(239,242,255" +
						"-fx-background-color: rgb(15,125,242)" +
						"-fx-background-radius: 20px");	
				
				textFlow.setPadding(new Insets(5,10,5,10));
				text.setFill(Color.color(0.934, 0.945, 0.996));
				
				hBox.getChildren().add(textFlow);
				vbox_messages.getChildren().add(hBox);
				
				/*message = new Nachricht(textToSend, false);
			 	out.writeObject(message);
				messages.addElement(message);
				textField_nachricht.clear();*/
				}
				
				//akList();
			//}
		} 
		catch (NullPointerException e)
		{
			//gui.changeStatus("Noch nicht mit Server Verbunden!");
		} 
		catch (Exception e)
		{
			System.out.println(e + "\n in sendMessage");
		}
		//this.gui.getTextFieldEingabe().setText("");

	}

	private void readMessage()
	{
		try
		{

			Nachricht message = (Nachricht) ois.readObject();

			System.out.println(message.toString());
			if (message.getListClients() != null)
			{
				this.clients = message.getListClients();
				//akClientList();
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
	}

	protected void getNewMessages(Nachricht n)
	{
		messages.addElement(n);
		//akList();
	}
/*
	protected void akList()
	{
		this.gui.getList().setModel(messages);
	}

	protected void akClientList()
	{
		this.gui.getListUser().setModel(clients);
		this.gui.getListActiveUser().setModel(clients);
	}*/

	private void stopClient()
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
		//gui.changeStatus("verbindung getrennt");
		//switchGui();
	}
/*
	private void setToolTip()
	{
		int index = gui.hoveredItem();
		if (index != -1)
		{
			Nachricht n = messages.getElementAt(index);
			String time = n.getTimestamp().getDayOfMonth() + "." + n.getTimestamp().getMonthValue() + "."
					+ n.getTimestamp().getYear() + " | " + n.getTimestamp().getHour() + ":"
					+ n.getTimestamp().getMinute() + " Uhr";
			gui.getList().setToolTipText("<html> Absender-ID: " + n.getAbsenderId() + "<br> Absender: "
					+ n.getAbsender() + "<br> Versandt: " + time + "</html>");
		}

	}
	*/
	/*protected boolean checkUsername()
	{
		if(startGui.getTextFieldUsername().getText().isBlank())
		{
			//guiError("username");
			return false;
		}
		else
		{
			return true;
		}
	}*/

	/*private void guiError(String error)
	{
		switch (error)
		{
			case "port":
				new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						for (int j = 0; j < 6; j++)
						{
							if (j % 2 == 0)
							{
								startGui.changePortColor(rot);
							} 
							else
							{
								startGui.changePortColor(weiss);
							}

							try
							{
								Thread.sleep(125);
							} 
							catch (InterruptedException e)
							{
								System.out.println(e + "\n in guiError - port");
							}
						}
					}
				}).start();
				break;
			
			case "username":
				new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						for (int j = 0; j < 6; j++)
						{
							if (j % 2 == 0)
							{
								startGui.changeUsernameColor(rot);
							} 
							else
							{
								startGui.changeUsernameColor(weiss);
							}

							try
							{
								Thread.sleep(125);
							} 
							catch (InterruptedException e)
							{
								System.out.println(e + "\n in guiError - username");
							}
						}
					}
				}).start();
				break;
		}
	}*/
	
	/*private void addUserToNewChat()
	{
		try
		{
			int index = gui.getListActiveUser().getSelectedIndex();
			
			String selected = clients.getElementAt(index);
			choosenClients.addElement(selected);
			
			gui.getListChoosenUser().setModel(choosenClients);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("nichts ausgewählt");
		}
	}*/
	
	/*private void entfUserFromNewChat()
	{
		try
		{
			int index = gui.getListChoosenUser().getSelectedIndex();
			
			choosenClients.removeElementAt(index);
			
			gui.getListChoosenUser().setModel(choosenClients);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("nichts ausgewählt");
		}
	}
<<<<<<< HEAD
	*/

	
	protected void login(String benutzer, String pass, boolean anmeldung)
	{
		System.out.print(benutzer + " " + pass);
		try
		{
			out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(new LogInNachricht(benutzer, pass, anmeldung));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void startConnect()
	{
		try
		{
			if(socket == null)
			{
				socket = new Socket("localhost",5555);
			}
			else if(socket != null)
			{
				if(socket.isConnected() == false)
				{
					socket = new Socket("localhost", 5555);
				}
			}
			
			//ois = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			Thread.sleep(2000);
		}
		catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	
	protected Scene erfolgreicherLogin() throws IOException
	{
		Parent debugParent = FXMLLoader.load(getClass().getResource("Debug.fxml"));
		return new Scene(debugParent);
	}
	
	protected void empfangeNachrichtVomAnmeldeServer() throws IOException, ClassNotFoundException
	{
		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		FehlerNachricht fehler = (FehlerNachricht) ois.readObject();
		
		if (fehler.isDatenbankFehler())
		{
			
		}
		else if (fehler.isNutzernameVergebenFehler()) 
		{
			
		}
		else if (fehler.isNutzernameFehler()) 
		{
			
		}
		else if (fehler.isPasswortFehler()) 
		{
			
		}
	}

	@Override
	public void run()
	{
		while (!read.isInterrupted())
		{
			try
			{
				empfangeNachrichtVomAnmeldeServer();
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				read.interrupt();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
	}
}
