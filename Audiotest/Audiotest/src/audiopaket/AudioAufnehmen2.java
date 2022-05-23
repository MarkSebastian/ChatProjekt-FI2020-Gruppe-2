package audiopaket;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.AudioFileFormat.Type;

public class AudioAufnehmen2 extends Thread
{
	private Controller controller;
	private ControllerM controllerM;
	private int random;
	private File file;
	private AudioFormat format;
	private Type fileFormat;
	private AudioFormat audioFormat;
	protected TargetDataLine tdl;
	private AudioInputStream ais;
	private int frameSizeInBytes;
	private int bufferLengthInFrames;
	private ByteArrayOutputStream out = new ByteArrayOutputStream();
	private ByteArrayInputStream bais;
	private Thread thread;
	private AudioInputStream audioStream;
	//das ist das übergebene
	private AudioFormat format2;
	private AudioAufnehmen2 audioAufnehmen2;
	

	//gui und GuiAudio sind auch threads
	public AudioInputStream getAis()
	{
		return ais;
	}

	public void setAis(AudioInputStream ais)
	{
		this.ais = ais;
	}

	public AudioAufnehmen2(ControllerM controllerM)
	{
		this.controllerM = controllerM;
	}

	public AudioAufnehmen2()
	{
		// TODO Auto-generated constructor stub
	}

	public TargetDataLine getTdl()
	{
		return tdl;
	}

	public void setTdl(TargetDataLine tdl)
	{
		this.tdl = tdl;
	}

	/*public void starteRun()
	{
		thread = new Thread(this);
		thread.setName("Aufzeichnen");
		thread.start();
	}*/
	
	public void aufzeichneThread(AudioAufnehmen2 audioAufnehmen2)
	{
		thread = new Thread(this);
		thread.setName("Aufzeichnen");
		System.out.println("In AudioAufnehmen2 Thread Name "+thread.getName());
		thread.start();
	}
	
	public void stopThread()
	{
		thread = null;
	}

	//threads in einzelnen methoden
	
	@Override
	public void run()
	{
		try (final ByteArrayOutputStream out = new ByteArrayOutputStream();
				final TargetDataLine tdl = getTargetDataLineForRecord();)
		{
			System.out.println(audioFormat+" is das auch thread ?");
			int frameSizeInBytes = audioFormat.getFrameSize();
			int bufferLengthInFrames = tdl.getBufferSize() / 8;

			System.out.println("Hi");
			final int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;
			schreibeOutputStream(out, tdl, frameSizeInBytes, bufferLengthInBytes);
			this.ais = new AudioInputStream(tdl);
			setAis(convertToAudioIStream(out, frameSizeInBytes));
			// ob wir die line hier brauchen wissen wir nicht
			ais.reset();
		}
		catch (Exception e )
		{
			e.printStackTrace();
		}

	}
	
	public AudioFormat build(AudioFormat format)
	{
		this.format = format;
		System.out.println("das is in build "+format);
		System.out.println(this.format + "Das ist this");
		return this.format;
	}

	public void schreibeOutputStream(final ByteArrayOutputStream out, final TargetDataLine line, int frameSizeInBytes,
			final int bufferLengthInBytes) throws IOException
	{
		final byte[] data = new byte[bufferLengthInBytes];
		int numBytesRead;

		System.out.println("1");
		tdl.start();// start capturing
		System.out.println("2");
		// hier könnte das problem liegen
		// while (this.currentThread() != null)
		while (thread != null)
		{
			if ((numBytesRead = line.read(data, 0, bufferLengthInBytes)) == -1)
			{
				break;
			}
			out.write(data, 0, numBytesRead);
			System.out.println("1010");
		}
		System.out.println("Nach while");
	}

	public AudioInputStream convertToAudioIStream(final ByteArrayOutputStream out, int frameSizeInBytes)
	{
		byte audioBytes[] = out.toByteArray();
		bais = new ByteArrayInputStream(audioBytes);
		AudioInputStream audioStream = new AudioInputStream(bais, audioFormat, audioBytes.length / frameSizeInBytes);
		long milliseconds = (long) ((ais.getFrameLength() * 1000) / audioFormat.getFrameRate());
		// brauchen wir vllt
		// duration = milliseconds / 1000.0;
		return audioStream;
	}

	/**
	 * Closes the target data line to finish capturing and recording
	 */
	public void finish()
	{
		tdl.stop();
		tdl.close();
		System.out.println("Finished");
	}

	// Methode von Git
	public TargetDataLine getTargetDataLineForRecord()
	{
		TargetDataLine line;
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		if (!AudioSystem.isLineSupported(info))
		{
			return null;
		}
		try
		{
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format, line.getBufferSize());
		}
		catch (final Exception ex )
		{
			return null;
		}
		System.out.println(line);
		return line;
	}

}
