package chatClient;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import Message.nachrichtP.Nachricht;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.security.PublicKey;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.DefaultListModel;

public class Control implements Runnable
{

	protected Gui gui;
	protected VerbindungsGUI startGui;
	protected GUIAudio guiAudio;
	protected int port;
	protected Socket socket;
	protected DefaultListModel<Nachricht> messages = new DefaultListModel<Nachricht>();
	protected DefaultListModel<String> clients = new DefaultListModel<String>();
	protected DefaultListModel<String> choosenClients = new DefaultListModel<String>();
	protected AudioAufnehmen audioAufnehmen;
	protected Thread read;
	private ClientConnectionThread start;
	private String audioPfad = "Juhuu";
	protected ObjectInputStream ois;
	protected ObjectOutputStream out;
	private boolean startWindow = false;

	private Color rot = new Color(255, 102, 102);
	private Color weiss = new Color(255, 255, 255, 255);

	private boolean first = true;

	public Control()
	{
		startGui = new VerbindungsGUI();
		gui = new Gui();
		setListener();
	}

	public void switchGui()
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
		// neuer Button f�r audioRecord
		this.gui.setBtnRecord(l -> sendAudio());
		this.gui.setBtnAudioStop(l -> stopAudio());

		this.startGui.addBtnVerbindenListener(l ->
		{
			start = new ClientConnectionThread(this);
			start.start();
		});
		gui.addListListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseMoved(MouseEvent e)
			{
				setToolTip();
			};
		});

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
				guiError("port");
				this.gui.changeStatus("kein korrekter Port");
			}

		}
		catch (NumberFormatException e )
		{
			guiError("port");
			this.gui.changeStatus("kein korrekter Port");
		}
		return korrekt;
	}

	protected void sendMessage()
	{
		try
		{
			Nachricht message;
			if (first)
			{
				message = new Nachricht(startGui.getTextFieldUsername().getText(), false);
				out.writeObject(message);
				first = false;
			}
			else
			{
				message = new Nachricht(this.gui.getTextFieldEingabe().getText(), false);
				out.writeObject(message);
				messages.addElement(message);
				akList();
			}
		}
		catch (NullPointerException e )
		{
			gui.changeStatus("Noch nicht mit Server Verbunden!");
		}
		catch (Exception e )
		{
			System.out.println(e + "\n in sendMessage");
		}
		this.gui.getTextFieldEingabe().setText("");

	}

	protected void sendAudio()
	{
		
		File file = new File(audioPfad);
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
		AudioFormat format = getFormat();
		/*try
		{
			AudioFormat format = setzeSoundEinstellungen();
			audioAufnehmen = new AudioAufnehmen();
			audioAufnehmen.build(format);
			
			audioAufnehmen.aufzeichneThread(audioAufnehmen);
			System.out.println("Hier vor speichern");
			WavSpeichern audioDateiSpeichern = new WavSpeichern();
			audioDateiSpeichern.speichereInWav(audioAufnehmen.getAis());
		}
		catch (Exception e )
		{
			System.out.println("Ich war kacken!");
		}*/

		
		try
		{
			Nachricht message;
			if (first)
			{
				// Konstruktor in Nachricht ändern
				message = new Nachricht(clip, false);
				out.writeObject(message);
				first = false;
			}
			else
			{
				message = new Nachricht(this.gui.getTextFieldEingabe().getText(), false);
				out.writeObject(message);
				messages.addElement(message);
				akList();
			}
		}
		catch (NullPointerException e )
		{
			gui.changeStatus("Noch nicht mit Server Verbunden!");
		}
		catch (Exception e )
		{
			System.out.println(e + "\n in sendMessage");
		}
		this.gui.getTextFieldEingabe().setText("");

	}
	

	protected void stopAudio()
	{
		audioAufnehmen.stopThread();
		audioAufnehmen.interrupt();
		
	}

	public static AudioFormat setzeSoundEinstellungen()
	{
		SoundEinstellungen settings = new SoundEinstellungen();
		AudioFormat.Encoding encoding = settings.ENCODING;
		float rate = settings.RATE;
		int channels = settings.CHANNELS;
		int sampleSize = settings.SAMPLE_SIZE;
		boolean bigEndian = settings.BIG_ENDIAN;

		return new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8) * channels, rate, bigEndian);
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
				akClientList();
			}
			getNewMessages(message);

		}
		catch (SocketException e1 )
		{
			stopClient();
		}
		catch (EOFException e2 )
		{
			stopClient();
		}
		catch (Exception e )
		{
			System.out.println(e + "\n in readMessage");
		}
	}

	protected void getNewMessages(Nachricht n)
	{
		messages.addElement(n);
		akList();
	}

	protected void akList()
	{
		this.gui.getList().setModel(messages);
	}

	protected void akClientList()
	{
		this.gui.getListUser().setModel(clients);
		this.gui.getListActiveUser().setModel(clients);

	}

	private void stopClient()
	{
		try
		{
			first = true;
			ois.close();
			out.close();
			socket.close();
		}
		catch (IOException e )
		{
			System.out.println(e + "\n in stopClient");
		}

		read.interrupt();
		start.interrupt();
		gui.changeStatus("verbindung getrennt");
		switchGui();
	}

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

	protected boolean checkUsername()
	{
		if (startGui.getTextFieldUsername().getText().isBlank())
		{
			guiError("username");
			return false;
		}
		else
		{
			return true;
		}
	}

	private void guiError(String error)
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
						catch (InterruptedException e )
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
						catch (InterruptedException e )
						{
							System.out.println(e + "\n in guiError - username");
						}
					}
				}
			}).start();
			break;
		}
	}

	private void addUserToNewChat()
	{
		try
		{
			int index = gui.getListActiveUser().getSelectedIndex();

			String selected = clients.getElementAt(index);
			choosenClients.addElement(selected);

			gui.getListChoosenUser().setModel(choosenClients);
		}
		catch (ArrayIndexOutOfBoundsException e )
		{
			System.out.println("nichts ausgew�hlt");
		}
	}

	private void entfUserFromNewChat()
	{
		try
		{
			int index = gui.getListChoosenUser().getSelectedIndex();

			choosenClients.removeElementAt(index);

			gui.getListChoosenUser().setModel(choosenClients);
		}
		catch (ArrayIndexOutOfBoundsException e )
		{
			System.out.println("nichts ausgew�hlt");
		}
	}
	
	private void getFormat()
	{
		AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
		float rate = 44
	}

	@Override
	public void run()
	{
		switchGui();
		while (!read.isInterrupted())
		{

			try
			{
				readMessage();
				Thread.sleep(1000);
			}
			catch (InterruptedException e )
			{
				read.interrupt();
			}
		}

	}

}
