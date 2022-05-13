package chatServer;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.net.Socket;
import java.net.SocketException;

import javax.imageio.ImageIO;

import Message.nachrichtP.Nachricht;

public class Client implements Runnable
{

	private Control control;

	private String name = "unbekannt";
	private int id = 0;

	private Thread read;

	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream out;

	// private InputStream bildIn;
	// private BufferedInputStream bufferedBildIn;

	private boolean first = true;

	public Client(int id, Socket socket, Control control) throws IOException
	{
		this.id = id;
		this.socket = socket;
		this.control = control;
		startStreams();
		readMessage();
		read = new Thread(this);
		read.start();
	}

	private void startStreams()
	{
		try
		{
			out = new ObjectOutputStream(this.socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());

			// bildIn = socket.getInputStream();
			// bufferedBildIn = new BufferedInputStream(bildIn);
		} catch (Exception e)
		{
			System.out.println(e + "\n in startStreams function");
		}
	}

	protected void sendMessage(Nachricht message)
	{
		try
		{
			out.writeObject(message);
		} catch (Exception e)
		{
			System.out.println(e + "\n in sendMessage function");
		}

	}

	protected void readMessage()
	{
		try
		{
			Nachricht message = null;
			
			message = (Nachricht) ois.readObject();
			if (first)
			{
				name = message.getNachricht();
				first = false;
			} else
			{
				message.setAbsender(name);
				message.setAbsenderId(id);

				System.out.println(message);
				control.getNewMessages(message);
				control.broadcastMessage(message, this);
			}
		}
		catch(OptionalDataException e2)
		{
			e2.printStackTrace();
			//System.out.println("");
		}
		catch (SocketException | EOFException e1)
		{
			control.closeClient(this);
			stopClient();
		} catch (Exception e)
		{
			System.out.println(e + "\n in readMessage (Server) function");
		}
	}

	public void readBildMessage() throws IOException
	{
		// BufferedImage bufferedImage = ImageIO.read(bufferedBildIn);
		BufferedImage bufferedImage = ImageIO.read(ois);

		// bufferedBildIn.close();
		// ois.close();
		if (bufferedImage != null)
		{
			control.setImage(bufferedImage);
		}
	}

	@Override
	public void run()
	{

		while (!read.isInterrupted())
		{
			try
			{
				readMessage();
				readBildMessage();

				Thread.sleep(500);
			} catch (InterruptedException | IOException e)
			{
				System.out.println(name + "verbindung getrennt");
				control.broadcastMessage(
						new Nachricht("verbindung mit " + name + " getrennt!", control.clientListeAbspecken()));
				read.interrupt();
			}
		}

	}

	public String getName()
	{
		return name;
	}

	public int getId()
	{
		return id;
	}

	public Socket getSocket()
	{
		return socket;
	}

	public void stopClient()
	{

		try
		{
			socket.close();
			out.close();
			ois.close();
			this.read.interrupt();
		} catch (IOException e)
		{
			System.out.println(e + "\n in stopClient function");
		}
	}

	@Override
	public String toString()
	{
		return id + " " + name;
	}
}
