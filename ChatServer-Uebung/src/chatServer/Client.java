package chatServer;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import Message.nachrichtP.Nachricht;

public class Client implements Runnable
{
	static ArrayList<Datei> dateien;

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
		dateien = new ArrayList<>();
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

	public void broadcastBild(BufferedImage bufferedImage) throws IOException
	{
		// OutputStream bildOut = socket.getOutputStream();

		// BufferedOutputStream bufferedBildOut = new BufferedOutputStream(bildOut);

		ImageIcon imageIcon = new ImageIcon(bufferedImage);
		Image image = imageIcon.getImage();

		// BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
		// image.getHeight(null),
		// BufferedImage.TYPE_INT_RGB);

		Graphics graphics = bufferedImage.createGraphics();
		graphics.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);
		graphics.dispose();

		// ImageIO.write(bufferedImage, "png", bufferedBildOut);
		ImageIO.write(bufferedImage, "png", out);

		// bufferedBildOut.close();
		// out.close();
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
		} catch (OptionalDataException e2)
		{
			e2.printStackTrace();
			// System.out.println("");
		} catch (SocketException | EOFException e1)
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

		broadcastBild(bufferedImage);
	}

	public void readDatei() throws IOException
	{
		DataInputStream dis = new DataInputStream(ois);

		int dateiNameLaenge = dis.readInt();

		if (dateiNameLaenge > 0)
		{
			byte[] dateiNameBytes = new byte[dateiNameLaenge];
			dis.readFully(dateiNameBytes, 0, dateiNameBytes.length);

			String dateiName = new String(dateiNameBytes);

			int dateiInhaltLaenge = dis.readInt();

			if (dateiInhaltLaenge > 0)
			{
				byte[] dateiInhaltBytes = new byte[dateiInhaltLaenge];
				dis.readFully(dateiInhaltBytes, 0, dateiInhaltLaenge);

				if (getDateiEndung(dateiName).equalsIgnoreCase("txt"))
				{
					
				}
				
				try
				{
					File datei = new File(dateiName);
					
					FileOutputStream fos = new FileOutputStream(datei);
					
					//FileInputStream fis = new FileInputStream(dateiInhaltLaenge);
					
					byte[] dateiInhalt;
					
					fos.write(dateiNameBytes);
					fos.close();
				}
				finally
				{
					
				}
			}

		}
	}
	
	public String getDateiEndung(String dateiName)
	{
		int i = dateiName.lastIndexOf('.');
		
		if(i > 0)
		{
			return dateiName.substring(i+1);
		}
		else
		{
			return "keine Dateiendung";
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
				readDatei();

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
