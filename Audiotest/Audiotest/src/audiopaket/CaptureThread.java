package audiopaket;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.Buffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Calendar;
import java.util.HashSet;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFileFormat.Type;
import javax.swing.JOptionPane;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class CaptureThread extends Thread
{
	// protected byte[] tempBuffer = new byte[10240];
	// protected boolean bool = false;
	// protected boolean stopaudioCapture = false;
	// protected ByteArrayOutputStream bos;

	private ControllerD controllerD;
	private File file;
	private Type fileFormat;
	protected TargetDataLine tdl;
	// protected boolean stop = true;
	private AudioFormat audioFormat;
	private AudioInputStream ais;
	private int random;

	public CaptureThread(ControllerD controllerD)
	{
		this.controllerD = controllerD;
	}

	@Override
	public void run()
	{
		try
		{
			random = (int) (Math.random() * 10000000 + 10000000);
			file = new File(random + ".wav");
			fileFormat = AudioFileFormat.Type.WAVE;
			audioFormat = new AudioFormat(16000, 8, 2, true, true);
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
			tdl = (TargetDataLine) AudioSystem.getLine(info);
			ais = new AudioInputStream(tdl);
			System.out.println("aufnahme gestartet");

			tdl.open(audioFormat);
			tdl.start();
			System.out.println("TEST");
			while (!isInterrupted())
			{
				AudioSystem.write(ais, fileFormat, file);

				System.out.println("Record-Running");

			}
			System.out.println("aufnahme beendet");
			tdl.stop();
			tdl.close();
		}
		/*
		 * if (!this.isInterrupted()) { tdl.open(audioFormat); tdl.start();
		 * AudioSystem.write(ais, fileFormat, file);
		 * System.out.println("Record Running"); } else {
		 * System.out.println("wiedergabe beendet"); tdl.stop(); tdl.close(); } }
		 */

		catch (LineUnavailableException e )
		{
			System.out.println("Line not Available Exception");
		}

		catch (IOException e )
		{
			System.out.println("IOException in CaptureThread run");
		}

	}

}
