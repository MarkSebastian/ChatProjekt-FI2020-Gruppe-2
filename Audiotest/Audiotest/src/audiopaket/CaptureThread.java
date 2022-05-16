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
	//änderung git
	private Controller controllerD;
	private File file;
	private Type fileFormat;
	protected TargetDataLine tdl;
	// protected boolean stop = true;
	private byte[] ba;
	private int anzahlGelBytes;
	private boolean aufnahme;
	private ByteArrayOutputStream baOut;
	private AudioFormat audioFormat;
	private AudioInputStream ais;
	private int random;

	public CaptureThread(Controller controller)
	{
		this.controllerD = controller;
	}

	@Override
	public void run()
	{
		try
		{
			/*
			random = (int) (Math.random() * 10000000 + 10000000);
			file = new File(random + ".wav");
			fileFormat = AudioFileFormat.Type.WAVE;
			audioFormat = new AudioFormat(16000, 8, 2, true, true);
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);
			tdl = (TargetDataLine) AudioSystem.getLine(info);
			ais = new AudioInputStream(tdl);
			System.out.println("aufnahme gestartet");*/

			AudioFormat audioformat = new AudioFormat((float)11025.0,16,2,true,false);
			byte[] ba = new byte[64];
			anzahlGelBytes = 0;
			aufnahme = true;
			baOut = new ByteArrayOutputStream();
			
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioformat);
			tdl = (TargetDataLine)AudioSystem.getLine(info);
			tdl.open(audioFormat);
			//hier geplant mit audioFormat und BufferSize
			tdl.start();
			
			while(aufnahme)
			{
				
				anzahlGelBytes = tdl.read(ba, 0, ba.length);
				baOut.write(ba, 0, anzahlGelBytes);
				if(anzahlGelBytes == -1)
					break;
			}
			tdl.drain();
			tdl.stop();
			//System.out.println("aufnahme beendet");
			tdl.close();
			/*
			if(tdl == null)
			{
				System.out.println("Line is frei");
			}*/
			/*
			System.out.println("TEST");
			if (!this.isInterrupted())
			while (!isInterrupted())
			{	
				try
				{
					//geht rein
					//reset()?
					//AudioInputStream, AudioFileFormat, TargetDataLine
					AudioSystem.write(ais, fileFormat, file);
					System.out.println("Record Running");
					//hier verreckt es irgendwie 
					//1 schritt als option
					//kein signal jetzt ist zu ende, deswegen bleibt er ewig im thread hängen 
					tdl.drain();
					tdl.flush();
					
				}
				catch (NullPointerException e) {
					// TODO: handle exception
					System.out.println("E");
				}
				catch (IllegalArgumentException ex) {
					// TODO: handle exception
					System.out.println("EX");
				}
				catch (IOException exe) {
					// TODO: handle exception
					System.out.println("EXE");
				}
				if(isAlive())
				{
					//hier springt es nicht mehr rein
					System.out.println("Record-Running");
				}
			
			//}
			}*/
			/*else
			{
				System.out.println("wiedergabe beendet");
			}*/
				
		}

		catch (LineUnavailableException e )
		{
			System.out.println("Line not Available Exception");
		}

		/*catch (IOException e )
		{
			System.out.println("IOException in CaptureThread run");
		}*/

	}

}
